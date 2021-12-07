###  JVM
 + 栈和堆的区别。调用函数时，临时变量存在栈，对象存在堆中
   1. 栈的作用是配合执行程序，提供执行程序的必须内存空间。栈是存储程序执行时的临时数据。每个线程有自己的栈和程序指针
   2. 应用通过堆存储数据（申请，回收，托管）
 + 类从编译到执行的过程
   1. 编译器将.java源文件编译为.class字节码文件。
   2. ClassLoader 将字节码转换为JVM中的Class对象
   3. JVM利用Class对象实例化为Robot对象
 1. classloader:依据特定格式，加载class文件到内存
   + ClassLoader负责通过将Class文件里的二进制数据流装载进系统，然后交给Java虚拟机进行连接、初始化等操作。
   + 种类
       1. BootStrapClassLoader ：加载核心库 
       2. ExtClassLoader ： 加载扩展库
       3. AppClassLoader:   加载程序所在目录
       4. 自定义ClassLoader : 定制化加载 
   + 双亲委派机制 。委托父类加载（把缓存设置到父类，父子之间信任关系传递，子Class Loader委托父Class Loader完成工作）
       1. 自底向上检查类是否已经加载
       2. 自顶向下尝试加载类 
       1. BootstrpClassLoader
       2. ExtensinClassLoader
       3. AppClassLoader
       4. CustomClassLoader
 2. Execution Engine: 对命令进行解析 
 3. Native Interface ：融合不同开发语言的原生库为java所用
 4. Runtime data Area： jvm 内存运行模型
    + 线程私有：程序计数器，虚拟机栈，本地方法栈
       1. 程序计数器
          1. 当前线程所执行的字节码行号指示器
          2. 改变计数器的值来选取下一条需要执行的字节码指令
          3. 和线程是一对一的关系即线程私有
          4. 对Java方法计数，如果是Native方法则计数器值为undefined
          5. 不会发生内存泄漏
       2. Java虚拟机栈
          1. Java方法执行的内存模型
          2. 包含多个栈帧
             1. 局部变量表：包含方法执行过程中的所有变量
             2. 操作栈：入栈、出栈、复制、交换、产生消费变量
             3. 动态链接
             4. 返回地址
    +  线程共享：MetaSpace,Java堆 
       + MetaSpace 和 PermGen的对比 及 优势 
           1. 元空间使用本地内存，而永久代使用的是jvm的内存
           2. 字符串常量池存在永久代中，容易出现性能问题和内存溢出
           3. 类和方法的信息的大小难以确定，给永久代的大小制定带来困难
           4. 永久代会为GC带来不必要的确定性
           5. 方便hotspot 与其他JVM 如Jrockit的集成
       + Java堆（heap）
           1. 对象实例的分配区域
           2. GC管理的主要区域
   + JVM 三大性能调优参数 -Xms -Xmx -Xss的含义
        1. -Xss : 规定了每个线程虚拟机栈的大小
        2. -Xms : 堆的初始值
        3. -Xmx : 堆能达到的最大值
   + Java内存模型JMM
        1. 主内存
            1. 存储java实例对象
            2. 包括成员变量、类信息、常量、静态变量等
            3. 属于数据共享的区域，多线程并发操作时会引发线程安全问题
        2. 工作内存
            1. 存储当前方法的所有本地变量信息，本地变量对其他线程不可见
            2. 字节码行号指示器，natvie方法信息
            3. 属于线程私有数据区域，不存在线程安全问题
   + Java内存模型中堆和栈的区别—内存分配策略
        1. 静态存储：编译时确定每个数据目标在运行时的存储空间要求
        2. 栈式存储：数据区要求在编译时未知，运行时模块入口前确定
        3. 堆式存储：编译时或者运行时或运行时模块入口都无法确定，动态分配
   + Java内存模型中堆和栈的区别
        1. 联系：引用对象、数组时，栈里定义变量保存堆中目标的首地址
        2. 区别
            1. 管理方式：栈自动释放，堆需要GC
            2. 空间大小：栈比堆小
            3. 碎片相关：栈产生的碎片远小于堆
            4. 分配方式：栈支持静态和动态分配，而堆仅支持动态分配
            5. 效率：栈的效率比堆高
   + 元空间、堆、线程独占部分空间的联系—内存角度
        1. 元空间：Class: hellword - method:sayHello - field:name  Class:System
        2. Java堆：Object : String ('test') Object : HelloWorld
        3. 线程独占：Parameter reference: test to String object 。 Variable reference : hw to helloword object .Local Variables: a with 1 lineNo
   + 垃圾回收机制      
        + 判定对象是否为垃圾的算法
            1. 引用计数算法 ：通过判断对象的引用数量决定对象是否可以被回收，被引用+1，完成引用-1
            2. 可达性算法：通过判断对象的引用链是否可达来决定对象是否可以被回收
        + 垃圾回收算法
            1. 标记-清除算法
                1. 标记：从根基和进行扫描，对存货的对象进行标记
                2. 清除：对堆内存从头到尾进行线性遍历，回收不可达对象内存
            2. 复制算法: 解决碎片化的问题，顺序分配内存简单高效，适用于对象存活低的场景
                1. 分为对象面和空闲面
                2. 对象在对象面上创建
                3. 存活的对象被从对象面复制到空闲面
                4. 将对象面所有对象内存清除 
            3. 标记-整理算法（双色标记，三色标记（增加一种颜色，不确定集合中存在元素，Sweep无法进行））：避免内存的不连续行，不用设置两块内存互换，适用于对象存活率高的场景。Mark:标记，Sweep：清除，Mutation:相对于GC而言，程序执行变更
                1. 标记： 从根集合进行扫描，对存活得对象进行标记
                2. 清除： 移动所有存活的对象，且按照内存地址次序依次排列，然后将末端内存地址以后的内存全部回收
            4. 分代收集算法
                1. Minor GC : 年轻代（尽快收集生命周期短的对象），采用复制算法
                    1. 收集器分类
                        1. Serial 收集器 。复制算法，单线程收集。简单高效，Client模式下默认运行的年轻收集器
                        2. ParNew收集器。 复制算法，单核执行效率不如Serial。
                        3. Parallel Scavenge 收集器。 复制算法。多线程收集，更关注系统的吞吐量。Server模式下默认的年轻收集器
                2. Full GC : 老年代（经历一次Minor次数依然存活的对象)，采用标记清理或标记整理算法
                    1. 触发条件
                        1. 老年代空间不足
                        2. 永久代空间不足
                        3. CMS GC出现promotion failed，cocurrent mode failure
                        4. Minor GC晋升到老年代的平均大小 大于老年代的剩余空间
                        5. 调用System.gc()
                    2. 收集器分类
                        1. Serial Old 收集器 。标记整理算法。单线程收集，进行垃圾收集时，Client模式下默认的老年代收集器
                        2. Parallel Old 收集器。标记整理算法。 多线程收集，吞吐量优先。
                        3. CMS 收集器。（标记 清除算法。Root Tracing算法）
                        4. Garbage First 收集器。复制+标记-整理算法。并行并发。分代收集。空间整合。可预测的停顿。
                            1. 将整个Java堆内存划分成多个大小相等的Region
                            2. 年轻代和老年代不再物理隔离 
                        5. G1 了解哪个区域最空，帮助最快回收最多内存。
                        6. zcc: 最大延迟时间几个ms，暂停时间不会随着堆大西哦，存活对象数目增肌。
            5. 常用的调优参数
                1. -XX:SuviorRatio:Eden和Survivor的比值：默认8：1
                2. -XX：NewRation:老年代和年轻代内存大小的比例
                3. -XX:MaxTenuringThreshold：对象从年轻晋升到老生代经过GC次数的最大阀值
        + GC的三个指标
            1. 吞吐量: 程序工作时间占比。GC没有占用的CPU时间。GCTimeRatio
                1. 给更大的内存
                2. 更改GC算法
            2.  Latency 指GC造成的造成停顿（STW）的时间
            3.  FootPrint 指最终应用对内存的需求。
   + Object的finalize()方法的作用是否与C++的析构函数作用相同
        1. 与C++的析构函数不同，析构函数调用确定，而它的是不确定的。
        2. 将未被引用的对象放置于F-Queue队列
        3. 方法执行随时可能会被终止
        4. 给予对象最后一次重生的机会
   + Java中的强引用，软引用，弱引用，虚引用有什么用
        1. 强引用
        2. 软引用：可以用来实现高速缓存
        3. 弱引用: 适用于偶尔被使用且不影响垃圾收集的对象
        4. 虚拟用：跟踪对象被垃圾回收器回收的活动，起到哨兵作用
 + 从Object 对象看 JVM 内存布局
      1. 所有的引用在栈中
      2. 所有的对象实例数据在Heap中
          1. 方法区，Method Area
             1. MetaSpace （类的信息）
             2. CodeCache(编译后的代码)
             3. ConstatnPool(常量)
          2. Native Memory
             1. Object 
             2. Buffer
 + 5个GC实战场景的解决方案
    1. 网络阻塞 导致OutofMemory
        1. 现象： 节点内存溢出：
        2. 原因： 写操作太多，导致集群同步数据网络压力上升。多节点积压排队的网络请求
        3. 方案： 降低集群写操作负担
    2. 堆外内存溢出
        1. 现象： nioChannel 提示outofmemory. GC不频繁。堆各个生代压力不大
        2. 原因： 太多网络谅解导致堆外内存溢出
        3. 方案： 调整堆外映射内存的大小
    3. 进程崩溃
        1. 现象： JVM进程崩溃，崩溃前大量的socket拒绝服务日志
        2. 原因: Socket 连接挤压导致崩溃 -大量socket文件
        3. 方案： 外部请求使用异步队列
    4. 从ES 调优看GC优化策略
        1. heap内存设置成一样防止缓存增加heap空间导致GC频繁
        2. 关闭swap区域防止缺页中断 sudo swap off -a 
        
    
             

 
    
            