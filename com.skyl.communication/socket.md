###Socket
+ Socket
  1. 具有管道文件的性质，先进先出，具有文件描述符。
  2. 编程接口(Stub)： Server Socket File 和 Client Socket File
+ I/O 多路复用
  + 单个线程处理多个Socket 
     + 核心问题：内核分发消息
     + select模型：线程维护一个Socket FD的列表
        1. 周期性的遍历检查是否有FD变动
        2. 缺陷： 1024数组个数的限制。遍历轮询触发到真正有信号的socket连接 
     + epoll模型：内核维护一个高效的二叉搜索树
        1. 已知某个FD发生变化的时候，可以快速知道哪个线程需要处理。
        2. 优点： 没有1024数组个数的限制。异步回调的方式去执行handler操作
  + BIO/NIO/AIO
     + BIO
        1. API设计：操作会阻塞线程。例如：等待socket连接。
        2. 原理：利用CPU中断。阻塞的线程进入中断，将执行权限交给其他线程。
            1. 优点：阻塞时不会占用系统资源；程序好理解
            2. 缺点：高并发场景需要较高的线程数量（资源占用），线程切换有成本
     + NIO 
        1. API设计：操作不会阻塞线程,读不到就返回null
        2. 原理:由专门的数据结构负责统计发生的I/O，再允许程序监听变更
            1. I/O变更发生后，记录在某个特定数据结构上，比如epoll的红黑树
            2. 线程可以在数据结构上注册自己关注的（文件描述符，消息类型等），
            3. 通常给线程提供一个方法可以一次性获取自己关注的事件。如果拿到：则是一个事件的集合。如果没拿到，则返回null
     + AIO 
        1. API 设计：异步编程
        2. 原理：原理：利用线程池技术、协程技术，调度所有Future的计算。通常结合epoll和directmemory技术   
     + N(new)IO 。 本质是堆外的缓冲区。优化点：减少一次拷贝
        1. DirectMapping。Block API vs Non-Blocking API
        2. epoll模型比较适合Non-Blocking(事件驱动）
+ https 加密 
  1. Client端 ： ca public key 解码验证证书。
  2. Client端 : 发送随机摘要到 Server端
  3. Server端 : 接收到public key 。使用private解码。发送ACK到 Client端。
  4. Client端与Server端 AES对称加密 通讯
+ http2.0 
  1. 二进制传输
  2. 多路复用
  3. 服务端推送  
          
    