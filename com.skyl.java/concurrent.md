#### 进程 线程 并发 
   + 进程与线程。  进程是资源分配的最小单位，是程序执行的副本，是一个线程的容器，线程是CPU调度的最小单位，轻量级的进程
        + 区别
            1.  线程不能看作独立应用，进程可以看作独立应用
            2.  进程有独立的地址空间，相互不影响，线程只是进程的不同执行路径
            3.  线程没有独立的地址空间，多进程的程序比多线程程序健壮
            4.  进程的切换比线程的切换开销大。
        + Java中两者的关系
            1. Java对操作系统提供的功能进行封装，包括进程和线程
            2. 运行一个程序会产生一个进程，进程包含至少一个线程
            3. 每个进程对应一个JVM实例，多个线程共享JVM里的堆
            4. Java采用单线程编程模型，程序会自动创建主线程
            5. 主线程可以创建子线程，原则上要后于子线程完成执行
        + 资源分配
            1. 线程：计算资源（CPU）
            2. 进程：内存、文件
   + 并发与并行
      1. 并发：在某一个时间段内多个任务执行
      2. 并行：指任务同时执行
   + 线程切换
      1. 保存当前线程的状态（CPU寄存器的值）
      2. 重新执行调度程序，重新选择其他线程执行
   + Thread中start 和 run方法的区别
        1. 调用start（）方法会创建一个新的子线程启动
        2. run（）方法只是Thread中的一个普通方法的调用
   + Thread 和Runnabale是什么关系
        1. Thread是实现了Runnble接口的类，使得run支持多线程
        2. 因类的单一继承原则，推荐多使用Runnable接口
   + 如何实现处理线程的返回值
        1. 主线程等待法，直到等到子线程返回结果
        2. 使用Thread类的join()阻塞当前线程以等待子线程处理完毕
        3. 通过callble接口实现：通过FutureTask Or 线程池获取
   + 线程的状态
        1. 新建 new ： 创建后尚未启动的线程状态
        2. 运行（Runnable） :包含Running 和Ready
        3. 无限期等待 waiting：不会被分配CPU执行时间，需要显式被唤醒
        4. 限期等待  timed wating : 在一定时间后由系统自动唤醒
        5. 阻塞 blocked: 等待获取排他锁
        6. 结束 teminated： 已终止线程的状态，线程已经结束执行
   + sleep 和wait的区别
     1. sleep 是Thread类的方法，wait是object类中定义的方法
     2. sleep方法可以在任何地方使用
     3. wait方法只能在synchronized 方法 或 synchronized块中使用
     4. Thread.sleep只会让出CPU，不会导致锁行为的改变
     5. Object.wait不仅让出CPU，还会释放已经占有的同步资源锁
   + notify 和 notifyall的区别
     1. notifyall会让所有处于等待池的线程全部进入锁池去竞争获取锁的机会
     2. notify只会随机选取一个处于等待池中的线程进入锁池去竞争获取锁的机会
   + yield。调用Thread.yield()函数时，会给线程调度器一个当前线程愿意让出CPU使用的暗示。
   + interrupt 函数。调用此函数，通知线程应该中断了。
     1. 如果线程处于被阻塞状态，那么线程将立即退出被阻塞状态，并抛出一个异常
     2. 如果线程处于正常活动状态，那么会将该线程的中断标志设置为true。被设置中断标志的线程将继续正常运行，不受影响。
   + 临界区。 两个线程发生竞争的区域（访问共享的资源）。
   + 解决线程竞争的方法
     1. 减少竞争。ThreadLocal
     2. 实现原子操作。CAS(Compare And Swap 或者 Compare And Set）是一种常见的实现原子操作的方法,—其实就是由硬件直接将CAS实现为一个CPU指令。
     3. TAS 操作 Test-And-Set 可以看作一个CAS的特例。
     4. 互斥。 
     5. Java的CAS操作。
   + 同步：
     1. 执行同步。多个参与方，汇合（互相等待），执行后续操作
     2. 数据同步。多份数据保持一致。缓存和存储的同步，不同机房订单数据的同步。
     + Java同步的五层结构
        1. 开发工具层：提供开箱即用的同步工具(Synchronizer)
        2. 开发框架层：提供开发同步工具的框架[AQS](https://pdai.tech/md/java/thread/java-thread-x-lock-AbstractQueuedSynchronizer.html)（AQS不仅仅用于开发同步工具，还用于的Non-Blocking，也称为Lock-Free的工具）
            1. 中断。 interrupt 
            2. 超时获取。 
            3. tryAcquire（非阻塞版获取锁）
            4. release(释放锁)
            5. acquire（阻塞版获取锁）
            6. Condition
            7. instate(CAS)
            8. CLH队列：CAS等待队列。条件等待队列
        3. JVM层 
        4. Java Native Interface（JNI）层（用于调用本地操作系统API） 
        5. 操作系统层
     + 信号量。允许N个线程同时进入临界区。 控制并发量，进行流量控制。
     + CycleBarrier。多个线程在一个屏障上互相等待，直到所有线程都到达了，再执行一个同步程序。
        1. 解决多个线程协作（通信）处理任务的问题
     + CountDownLatch。 相当于只有一代CycleBarrier。实现了一套屏障机制
     + Phaser 
        + 屏障部分领域语言
            1. 屏障： 合作的线程在屏障上等待。然后进行同步点。
            2. 同步点：通过屏障后执行的同步代码
            3. 合作方数量：就是互相等待的线程数量，只有当等待数量达到parties，才会进入同步点
            4. 到来：代表一个线程到达屏障，并等待，每次有线程到来，+1
            5. 到达数量：等待的线程数量
            6. 等待： 代表线程在barrier等待
            7. 进步： 一个线程通过屏障，称为进步，代表工作有进度
            8. 开动\下一个阶段：到来的线程数量 = parties，开始进入同步点
            9. 阶段：类似CycleBarrier的代，每次完成一次开动，phase number 加 1 
        + per线程能力
            1. arrive （到达）：在屏障上等待其他合作方，到达线程数+1
            2. watiAdvance（等待进步）：在屏障上等待其他线程，数量够了就进入同步点
            3. register（注册）： 相当于声明自己是一个合作方，将parties 增 1
            4. deregister（注销）： 相当于注销自己，parties 减 1 
      + Exchanger 利用Exchanger 在生产者，消费者交换Buffer 。线程间交换数据。
   + 互斥锁
     1. 特性
        1. 互斥性：即在同一时间只允许一个线程持有某个对象锁，通过这种特性，来实现多线程的协调机制，这样在同一个时间内只有一个线程对需要同步的代码块进行访问
        2. 可见性：必须确保在锁被释放之前，对共享变量所做的修改，对于随后获得该锁的另一个线程是可见的，否则另一个线程可能是在本地缓存的某个副本上继续操作，可能引起不一致
     2. 根据获取的锁分类：获取对象锁和获取类锁
        1. 获取对象锁的两种用法
            1. 同步代码块（synchronized(this),synchronized(类实例对象)），锁是小括号（）中的实例对象
            2. 同步非静态方法（synchronized method）,锁是当前对象的实例对象
        2. 获取类锁的两种方法
            1. 同步代码块（synchronzed(类.class)）,锁是小括号（）中的类对象（class 对象）。
            2. 同步静态方法（synchronized static method ）,锁是当前对象的类对象（class 对象）。
     3. 四种状态
        1. 无锁
        2. 偏向锁 （更偏向之前获取过锁的线程，省去再次进入的锁申请）：减少同一线程获取锁的代价。大多数情况下，锁不存在多线程竞争，总是由同一线程多次获得
        3. 轻量级锁 （由偏向锁升级）： 偏向锁在运行一个线程进入同步块的情况下，当第二个线程加入锁争用的时候，偏向锁就会升级为轻量级锁。
        4. 重量级锁 : 系统级别的锁。由entrySet到 waitingSet
     4. synchronized 和 reentrantlock的相似点
        1. 临界区保护（提供锁/解锁能力）
        2. 可重入 （抢占锁的可以多次执行lock）
        3. 都提供线程间写作
        4. 提供锁的升级
            1. synchonized: 使用monitor。偏向锁-》轻量级锁-》重量级锁
            2. reentrantlock : AQS 。CAS竞争-》休眠-》排队竞争
        5. 提供等待队列
            1. synchonized: 使用monitor,EntrySet和 WaitSet
            2. reentrantlock: AQS 。 CLH队列。
     4. synchronized 和 reentrantlock的区别
        1. Reentrantlock 实现比 synchronized 更细粒度的控制。调用lock后，必须调用unlock释放锁。
        2. Reentrantlock 公平性，倾向于将锁赋予等待时间最久的线程。synchronized 是非公平锁
        3. synchronized 是关键字，Reentrantlock 是类
        4. Reentrantlock 可以对锁的等待时间进行设置，避免死锁
        5. Reentrantlock 可以获取各种锁的信息
        6. Reentrantlock 可以灵活地实现多路通知
        7. 机制：sync 操作Mark Word ，lock 调用unsafe类的park（）方法
        8. reentrantlock 基于AQS ，synchronized基于Monitor
        9. reentrantlock 响应线程中断。synchronized不响应
     5. synchronized 和 volatile的区别
        1. volatile 本质是告诉JVM 当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取；synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住直到该线程完成变量操作为止
        2. volatile 仅能使用在变量级别；synchronized 则可以使用在变量、方法和类级别
        3. volatile 仅能实现变量的修改可见性，不能保证原子性，而synchronized则可以保证变量比修改的的可见性和原子性
        4. volatile 不会造成线程的阻塞：synchronized可能会造成线程的阻塞
        5. volatile 标记的变量不会被编译优化；synchronized标记的变量可以被编译器优化
     6. synchronized 缺点
        1. 缺少高并发处理能力：比如 tryLock 的逻辑（不能实现Non-Blocking的算法）
        2. 缺少获取锁的timeout
        3. 缺少打断当前执行线程的能力（可以打断在Blocking的线程，但是不能打断在执行while循环的线程）
        4. 使用不够灵活，不能跨语句块
        5. 性能不够优化，早期还没有轻量级锁
     7. synchronized 设计
        1. 先用自旋锁，因为自旋锁开销最小，只需要用少量的CPU指令
        2. 如果自旋锁获取不成功，就让线程休眠。
        3. 线程休眠后，需要信号唤醒。
        4. 每次一个线程执行完成，就唤醒一个新的线程。新的线程就会重新尝试自旋获取锁。
   + 自旋锁和自适应自旋锁
      1. 自旋锁
        1. 许多情况下，共享数据的锁定状态持续时间较短，切换线程不值得
        2. 通过让线程执行忙循环等待锁的释放，不让出CPU
        3. 缺点：若锁被其他线程长时间占用，会带来许多性能上的开销
      2. 自适应自旋锁
        1. 自旋的次数不再固定
        2. 由前一次在同一个锁上的自旋时间及锁的拥有者的状态来决定
   + Monitor
     1. 每个Java对象被创建后，就为它生成 一个关联的Monitor对象
     2. 线程上锁，其实是抢占Object的Monitor对象。
     3. 在Monitor对象中增加对线程的引用
     4. 如果有多个线程，同时竞争Monitor。
     5. 让多个线程先进行自旋竞争，失败的都休眠。在Monitor中，需要一个等待集合。
   + 锁消除: JIT编译时，对运行上下文进行扫描，去除不可能存在竞争的锁。‘
   + 锁粗化：通过扩大加锁的范围，避免反复加锁和解锁
   + CAS。（线程安全程序的首选）
      1. 特性
        1. 支持原子更新操作，适用于计数器，序列发生器等场景
        2. 属于乐观锁机制，
        3. CAS操作失败时，由开发者决定是否继续尝试，还是执行别的操作
        4. unsafe类虽提供CAS服务，但因能够操纵任意内存地址读写而有隐患
        5. java9 以后，可以使用variable handle api 来替代unsafe.
      2. 缺点
        1. 若循环时间过长，则开销很大
        2. 只能保证一个共享变量的原子操作
        3. ABA 问题 。增加版本号。 解决：AtmoicStampleReference，它的版本是整数。 AtomicMarkableReference ,它使用了 casPair 直接将(值，版本)作为一个整体更新
   + Java线程池 。 降低资源消耗，提供线程的可管理性
      + Fork/Join框架。work-stealing算法：某个线程从其他队列窃取任务来执行
         1. 把大任务分割成若个小人物并行执行，最终汇总每个小任务结果后得到大任务结果的框架。 
      + 三个Executor接口
         1. Executor: 运行新任务的简单接口，将任务提交爱哦和任务执行细节解耦
         2. ExecutorService: 具备管理执行器和任务周期的方法，提交任务机制更完善
         3. ScheduledExecutorService: 支持Future和 定期执行任务。
      + handler ： 线程池的饱和策略
         1. AbortPolicy:直接抛出异常，这是默认策略
         2. CallerRunPolicy:用调用者所在的线程来执行任务
         3. DiscardOldestPolicy:丢弃队列中最靠前的任务，并执行当前任务
         4. DiscardPolicy : 直接丢弃任务
         5. 实现RejectedExecutionHandler接口的自定义Handler
      + 线程池的状态
         1. RUNNING:能接受新提交的任务，并且也能处理阻塞队列中的任务
         2. SHUTDOWN:不再接受新提交的任务，但可以处理存量任务
         3. STOP : 不再接受新提交的任务，也不处理存量任务
         4. TIDING： 所有的任务都已终止。
         5. TERMINATED : teminated()方法执行后进入该状态
      + 线程池大小的选定
         1. CPU 密集型： 线程数 = 按照核数 或者 核数+1 设定
         2. I/O 密集型 ： 线程数 = CPU核数（1 + 平均等待时间/平均工作时间）
   + 并发工具类
       + 闭锁 CountDownLatch  让主线程等待一组事件发生后继续执行
       + 栅栏 CyclicBarrier  阻塞当前线程，等待其他线程。
       + 信号量 Semaphore 控制某个资源可以被同时访问的线程个数 
       + 交换器 Exchanger  两个线程到达同步点后，相互交换数据
   + JVM 锁的竞争流程
      1. 线程先用自旋锁竞争进入EntrySet，竞争进入EntrySet只需要少量的cas操作
      2. 用链表实现EntrySet，新线程进入EntrySet只需要两条指令，第一步创建一个节点，指向EntrySet的第一个元素。第二步，用cas操作将EntrySet的尾部指向新的节点。多数线程都可以进入EntrySet
      3. 进入EntrySet之后，如果当前没有持有者（没有线程再执行）。就让这个线程去竞争
      4. 如果既没有持有者，WaitSet也是空的，那么就不存在竞争，可以考虑直接让线程执行
      5. Java在每个对象的头部，增加了几个标识位，记录这个资源的所有者，如果这个位没有被占领，那么就可以考虑直接执行这个线程。这个方法称为偏向锁(BiasLock)
      6. 有线程占领了偏向锁，那么说明有多个线程在竞争，就升级为轻量级锁（Light WeightedLock）
      7. 轻量级锁，会先观察目前Monitor 中有没有持有者(Owner)——正在临界区的线程。如果没有，就直接去竞争Owner
      8. 轻量级锁的特点是会先进行自旋，如果若干次自旋后，比如10次，还是无法获取锁，就加入EntrySet，其实就是采用重量级锁。
      9. 一个持有锁的线程离开临界区后，会重新进入WaitSet
      10. 如果这个线程休眠，也会进入WaitSet。
      11. WaitSet和EntrySet中的线程，当持有线程休眠或者离开时，会一起再次竞争Owner，这个时候不再使用偏向锁和轻量级锁，称为锁的不可降级。
      12.  一旦升级为重量级锁，不会再使用轻量级或者偏向锁。 重量级锁，就是利用操作系统API提供的互斥能力进行竞争。
   + 内存一致性
      1. 线性一致: 任何时刻都一致
      2. 弱一致： 部分时刻一致 。 需要同步元语。锁，信号量，volatie
      3. 没有一致： 无法确定何时一致
      + 不一致的原因
        1. 分级缓存策略以及CPU的多核心，使得跨CPU的线程，使得读取到的不是最新版本
        2. CPU 指令重排。
        3. 并发环境
      + 解决方案 volatile （确保语义上对变量的读、写操作顺序被观察到）
        1. 对 volatile变量的读写不会被重排到对它后续的读写之后（阻止指令重排）
        2. 保证写入的值可以马上同步到CPU缓存中（写入后要求CPU马上刷新缓存）
        3. 保证读取到最新版本的数据
        4. volatile变量读写时会增加内存屏障
        5. 保证对volatile的操作happens-before另一个操作
      + happens- before （如果事件A应该在事件B之前发生，那么观察到的结果也是如此。取保有序性和可见性）
        1. 单线程规则：单线程对内存的访问符合happens-before规则
        2. Monitor规则： synchronized对锁的释放，happens-before对锁的获取
        3. volatile规则： volatile变量的操作happens-before对它的后续操作，并且周围指令不会重排
        4. Thread start 规则： start调用前的操作 happens-before线程内的程序
        5. Thread join 规则： 线程的最后一条指令 happens-before join后的第一条指令
        6. happens-before 传递性：如果A happens-before B, B happens-before C ,那么 A happens-before C 
   + 线程安全的数据结构(java.util.concurrent下的数据结构)
      + BlockingQueue
        1. 抛异常： add/remove
        2. 非阻塞： offer/pull
        3. 阻塞： put/take
      + ThreadLocal
   + 并发设计思路总结
      + 锁： 互斥 
      + 减少锁的范围 
      + Obstruction Free 
        + 线程间相互隔离 （一个线程的延迟不影响其他线程进步）
        + 不要求一定有线程进步，线程最终可以进步 
        + 嵌套的CAS 
      + Lock Free (在LockFree的基础上。保证所有线程同时进步)
        + 线程之间相互隔离（一个线程的隔离，阻塞、故障）不会影响其他线程，同一时刻至少有一个线程可以进步
        + CLH 队列: 线程通过CAS竞争加入CLH队列
        + SynchrounousQueue ：线程竞争实现transfer操作（双向队列，双向栈）
      + Wait Free
        + 在Lock Free基础上，保证所有线程同时进步
        + Obstruction Free  -》 Lock Free -》 Wait Free
        + CopyOnWrite的读线程 
      + LockLess 和 LockFree区别 
        + LockLess ： 不用锁的算法通常都是LockLess,线程之间可能会互相影响：例如：阻塞队列，本质还是在排队
   + CAP 理论 （不可兼得）
      + Consistency 一致性。总是能读到最新的数据。库存在B更新，但是A、B 、C 都能读到最新的库存
         +  Mysql的Consistency是指事物前后的状态。CAP的C 指的是分布式节点读取到的状态 
         +  如何让所有节点都观察到更新
            1. 所有节点都暂停服务，直接数据同步到所有节点
            2. 暂时不允许用户在数据尚未更新的节点读取数据
      + Avaliability 可用性。所有请求都收到一个非错误的返回。和一致性的冲突：数据同步到所有节点需要时间
         + 如何实现CA 
            1. 单机
            2. 只允许读写一部分
      + Partition tolerance 分区容错。网络分区。
      + Mysql
         + 默认情况下是CA 
         + 如果P发生
            1. CP: 牺牲可用性（Partition后剩余部分数据完整），作为一个完整集群
            2. AP: 牺牲一致性（Partition后两部分同时工作，允许数据冗余）
      + 最终一致性（Eventual Consistency）
         1. 在可以接受的范围内达到一致
         2. BASE：基本可用、软状态、最终一致  
      
      
        
       