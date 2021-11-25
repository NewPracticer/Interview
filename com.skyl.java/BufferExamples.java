import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BufferExamples {

    /**
     * 缓冲区中文处理（缓存大小设置导致中文乱码）
     */
    public void test_chinese(){
        var raw = "长坂桥头杀气生，横枪立马眼圆睁。一声好似轰雷震，独退曹家百万兵。";
        var charset = StandardCharsets.UTF_8;
        var bytes = charset.encode(raw).array();
        var bytes2 = Arrays.copyOfRange(bytes, 0, 11);

        var bbuf = ByteBuffer.allocate(12);
        var cbuf = CharBuffer.allocate(12);

        bbuf.put(bytes2);
        bbuf.flip();

        charset.newDecoder().decode(bbuf, cbuf, true);
        cbuf.flip();

        var tmp = new char[cbuf.length()];
        if(cbuf.hasRemaining()){
            cbuf.get(tmp);
            System.out.println("here:" + new String(tmp));
        }

        System.out.format("limit-pos=%d\n", bbuf.limit() - bbuf.position());
        //读取剩余的未正确解析的buff  重新拷贝到buff中
        Arrays.copyOfRange(bbuf.array(), bbuf.position(), bbuf.limit());
    }

    final ForkJoinPool pool = ForkJoinPool.commonPool();

    /**
     * 大文件读取
     */
    class CountTask implements Callable<HashMap<String, Integer>> {
        private final long start;
        private final long end;
        private final String fileName;

        public CountTask(String fileName, long start, long end) {
            this.start = start;
            this.end = end;
            this.fileName = fileName;
        }

        @Override
        public HashMap<String, Integer> call() throws Exception {
            var channel = new RandomAccessFile(this.fileName, "rw").getChannel();
            // [start, end] -> Memory
            // Device -> Kernel Space -> UserSpace(buffer) -> Thread
            var mbuf = channel.map(
                    FileChannel.MapMode.READ_ONLY,
                    this.start,
                    this.end - this.start
            );
            var str = StandardCharsets.US_ASCII.decode(mbuf).toString();
            return countByString(str);
        }
    }

    public void run(String fileName, long chunkSize) throws ExecutionException, InterruptedException {
        var file = new File(fileName);
        var fileSize = file.length();

        long position = 0;

        var startTime = System.currentTimeMillis();
        var tasks = new ArrayList<Future<HashMap<String, Integer>>>();
        while(position < fileSize) {
            var next = Math.min(position + chunkSize, fileSize);
            var task = new CountTask(fileName, position, next);
            position = next;
            var future = pool.submit(task);
            tasks.add(future);
        }
        System.out.format("split to %d tasks\n", tasks.size());

        var totalMap = new HashMap<String, Integer>();
        for(var future: tasks) {
            var map = future.get();
            for(var entry : map.entrySet()) {
                incKey(entry.getKey(), totalMap, entry.getValue());
            }
        }

        System.out.println("time:" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("total:" + totalMap.size());

        System.out.println(totalMap.get("ababb"));
    }

    public void count() throws ExecutionException, InterruptedException {
        var counter = new BufferExamples();
        System.out.println("processors:" + Runtime.getRuntime().availableProcessors());
        counter.run("word", 1024*1024*20);
    }


}