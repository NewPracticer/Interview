### Java部分
+ 平台无关性
    1. Java源码首先被编译成字节码，再由不同平台的JVM进行解析，Java语言在不同的平台上运行时不需要进行重新编译，Java虚拟机在执行字节码的时候，把字节码转换成具体平台上的机器指令
+ 类从编译到执行的过程
    1. 编译器将Robot.java源文件编译为Robot.class字节码文件。
    2. ClassLoader 将字节码转换为JVM中的Class<Robot>对象
    3. JVM利用Class<Robot>对象实例化为Robot对象
+ JVM
    1. classloader:依据特定格式，加载class文件到内存
        + ClassLoader负责通过将Class文件里的二进制数据流装载进系统，然后交给Java虚拟机进行连接、初始化等操作。
        + 种类
            1. BootStrapClassLoader ：加载核心库 
            2. ExtClassLoader ： 加载扩展库
            3. AppClassLoader:   加载程序所在目录
            4. 自定义ClassLoader : 定制化加载 
        + 双亲委派机制
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
                3. 类和方法的信息的大小你难以确定，给永久代的大小制定带来困难
                4. 永久代会为GC带来不必要的确定性
                5. 方便hotspot 与其他JVM 如Jrockit的集成
            + Java堆（heap）
                1. 对象实例的分配区域
                2. GC管理的主要区域
    + JVM 三大性能调优参数 -Xms -Xmx -Xss的含义
        1. -Xss : 规定了每个线程虚拟机栈的大小
        2. -Xms : 堆的初始值
        3. -Xmx : 堆能达到的最大值
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
        
       