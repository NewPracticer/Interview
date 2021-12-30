### Spring

#### SpingIOC 
+ IOC 原理
    1. 依赖倒置原则（DI） -(思路)》 
        2. 控制反转（IOC） 第三方容器（IOC Container）-（方法）》 
                3. 依赖注入（DI）(把底层类作为参数传递给上层类，实现上层对下层的控制)。Set注入，接口注入，注解注入，构造器注入。
        3. IOC Container的优势：避免在各处使用new来创建类，并且可以做到统一维护。创建实例的时候不需要了解其中细节。
+ SpringIOC
   +  SpringIOC容器流程
      1. 读取Bean配置信息
      2. 根据Bean注册表实例化Bean
      3. 将Bean实例放到Spring容器中
      4. 使用Bean
   + Spring IOC支持的功能
      1. 依赖注入
      2. 依赖检查
      3. 自动装配
      4. 支持集合
      5. 指定初始化方法和销毁方法
      6. 支持回调方法
   + Spring IOC 核心接口
      1. BeanFactory
        1. 提供IOC的配置机制
        2. 包含Bean的各种定义，便于实例化Bean
        3. 建立Bean之间的依赖关系
        4. Bean生命周期的控制
      2. ApplicationContext 的功能
        1. BeanFactory ：能够管理，装配Bean
        2. ResourcePatternResolver ： 能够加载资源文件
        3. MessageSource：能够实现国际化等功能
        4. ApplicationEvenPublisher：能够注册监听器，实现监听机制
      3. ApplicationContext 与 Beanfactory的比较
        1. BeanFactory 是Spring框架的基础设置，面向Spring。
        2. ApplicationContext面向使用Spring框架的开发者
      4. BeanDefinition 主要用来描述Bean的定义
      5. BeanDefinitionRegistry ：提供向IOC容器注册BeanDefinition对象的方法
   + getBean方法的代码逻辑
        1. 转换beanName
        2. 从缓存中加载实例
        3. 实例化Bean
        4. 检测parentBeanFactory
        5. 初始化依赖的Bean
        6. 创建Bean
   + Spring Bean的作用域
      1. singleton: Spring的默认作用域，容器里拥有唯一的Bean实例
      2. prototype: 针对每个getBean请求，容器都会创建一个Bean实例
      3. request: 会为每个HTTP请求创建一个Bean实例
      4. session: 会为每个session创建一个Bean实例
      5. globalSession： 会为每个全局http session创建一个Bean实例，该作用域仅对portlet有效。
   + SpringBean的生命周期
     + 创建周期
        1. 实例化Bean
        2. Aware(注入Bean ID,BeanFactory和AppCtx)
        3. BeanPostProcessor postProcessBeforeInitialization
        4. InitializingBean,afterPropertiesSet
        5. 定制的Bean init方法
        6. BeanPostProcessor , postProcessAfterInitialization
        7. Bean初始化完毕
     + 销毁周期
        1. 若实现了DisposableBean接口，则会调用destory方法
        2. 若配置了desty-method属性，则会调用其配置的销毁方法
+ SpringAOP
   1. 关注点分离：不同的问题交给不同的部分去解决
     1. 面向切面编程AOP正式此种技术的体现
     2. 通用化功能代码的实现，对应的就是所谓的切面
     3. 业务功能代码和切面代码分开后，架构变得高内聚低耦合 
   2. AOP的三种植入方式
     1. 编译时植入：需要特殊的Java编译器，如AspectJ
     2. 类加载时植入：需要特殊的Java编译器，如AspectJ和AspectWekz
     3. 运行时植入：Spring采用的方式，通过动态代理的方式，实现简单。
   3.  主要名词概念
     1. Aspect ：通用功能的代码实现
     2. Target： 被植入Aspect的对象
     3. Join Point:可以作为切入点的机会，所有方法都可以作为切入点
     4. PointCut：Aspect 实际被应用在Join Point ,支持正则
     5. Advice ：类里的方法以及这个方法如何植入到目标方法的方式 
     6. Weaing：Aop的实现过程
   4. AOP的实现：JdkProxy 和Cglib。代理模式：接口+真是实现类+代理类
     1. 由AopProxyFactory 根据AdvisedSupport对象的配置来决定
     2. 默认策略如果目标类是接口，则用JDKproxy来实现，否则用后者
     3. JDKProxy的核心：invocationHandler 接口和Proxy类。通过Java内部的反射机制实现。反射在生成类的过程中比较高效
     4. Cglib: 以继承的方式动态生成目标类的代理。借助ASM实现。ASM在生成类后的执行过程比较高效
   5. Spring里的代理模式的实现
     1. 真实实现类的逻辑包含在了getBean方法里
     2. getBean方法返回的实际上是Proxy的实例
     3. Proxy实例是Spring采用JDK Proxy 和 CGLIB 动态生成的 
+ Springboot 启动
  1. 创建SpringApplicaiton
  2. 初始化构造器Initializer 和 listener
  3. 调用run方法
  4. 开启定时器stopwatch
  5. 根据SpringApplicationRunListener 以及参数来启动环境
  6. 准备环境配置，注意ConfigFileApplicationListener 是用来加载properties文件的打印banner
  7. 创建容器上下文
  8. prepare准备容器上下文，执行之前的initializer初始化，并load在主bean
  9. refresh容器上下文，里面的onRresh可以启动web server
  10. afterRefresh 执行初四化完成后要做的操作
  11. listener启动完成
  12. 关闭计时器
  13. 打印启动时间
#### Springboot 避坑指南
+ Springboot 配置文件
   1. Springboot使用一个全局的配置文件，且配置文件名是固定的，配置文件的作用是用来修改Springboot自动配置的默认值
   2. 可以使用applicaiton.properties格式，也可以使用application.yml格式
   3. 由于YAML格式紧凑且可读性高，所以Springboot支持并推荐使用YAML格式的配置文件
   4. 由于两种配置文件同时存在的时候，默认优先使用.properties配置文件
+ Springboot配置文件优先级加载顺序
   1. file:./config/ -> 优先级最高
   2. file:./ 优先级第二（项目根路径下）
   3. classpath:/config/ -> 优先级第三（项目 resources/config 下）
   4. classpath:/ -> (项目resources根目录下)
+ Springboot 多环境配置
   1. 多环境下使用spring.profile.active可以指定配置文件
   2. 使用占位符，在启动命令中指定配置文件 
+ Springboot中编写定时任务
   1. 两个注释 EnableScheduling Scheduled
   2. Scheduled 注释。 fixDelay。fixedRate。initalDelay。cron。
   3. 配置文件中指定定时任务线程数 spring.task.scheduling.pool.size 。
   4. 自定义定时任务的线程池。编写ScheduleConfig
+ Springboot的异步任务
   1. EnableAsync
   2. Async
+ Spring Bean的默认名称生成策略导致的空指针
   1. 定义一个Bean，Spring生成的Bean名称是把第一个字母变成小写，其他不变
   2. 如果长度大于1，并且前两个字母都是大写，则是什么都不改变。
+ 使用@Autowired注解，但仍然出现空指针
   1. 不理解Spring的自动装配机制，错误的使用new是很常见。
       1. 属性对象虽然注入了，但当前类没有标记为Springbean
       2. 当前类标记为SpringBean, 且属性对象也注入了，但是使用了new 去获取类对象
   2. 没有理解Spring默认的包扫描机制，扫描不到顶的Bean
       1. Spring默认的包扫描机制是当前包以及子包下的所有目录，但是你却把Bean定在主包外面。
   3. Spring的componentScan注解。可以自主的制定Spring的扫描范围。
+ Spring 获取上下文
   1. Spring容器的核心是负责管理对象，且并不只是帮我们创建对象，它负责了对象整个生命周期的管理-创建-装配-销毁 
   2. 应用上下文可以认为是Spring容器的一种实现，也就是用于操作容器的容器类对象。把需要管理的对象放入容器中，取得容器中的Bean
   3. Spring核心是容器，但是容器并不唯一
        1. 不常用的容器实现。BeanFactory。最简单的容器，提供基本的DI功能
        2. 高级实现。继承BeanFactory派生的应用上下文-ApplicationContext。解析配置文件，注册管理Bean
   4. 获取应用上下文的四种方式
        1.  ApplicationContextInitializer：容器创建完成之后的回调
        2.  ApplicationListener ： 观察者模式的典型应用。
        3.  Springboot启动程序的返回 
        4.  ApplicationContextAware ：Spring的Aware接口。Aware可以理解为感觉的，注意到的，感知到的。
+ 多线程下Spring bean的数据
   1. Spring提供的bean scope :默认为单例Bean时。处理多次请求时，Spring容器只会实例化一个Bean，也就是说Bean是公共的 。 
   2. 自主制定的原型模式Bean。对于每一次请求，Spring容器都会实例化新的Bean，这个Bean是独占的。
+ 多个可用Bean异常
   1. @Autowired。属于Spring框架。默认使用类型（byType）进行注入
   2. @Qualifier。结果@Autowired一起使用，自动注入策略由byType变成byName
   3. @Resoure：JavaEE自带的注释。默认按byName自动注入
   4. @Primary：存在多个相同类型的Bean，则@Primary用于定义首选项
+ SpringBean 循环依赖。使用三级缓存策略解决循环依赖
   1. 一级缓存。用户存放完全初始化好的Bean
   2. 二级缓存。存放原始的Bean对象，尚未填充属性，用于解决循环依赖
   3. 三级缓存。存放bean工厂对象，用于解决循环依赖。
+ BeanFactoryPostProcessor 和 BeanPostProcessor
   1. 关于BeanPostProcessor特性
        1. Bean 后置处理器
        2. 两个回调方法 
            1. postProcessBeforeInitialization。每一个Bean对象初始化之前回调。
            2. postProcessAfterInitialization。每一个Bean对象初始化之后回调
        3. 在Bean实例化之后执行
   2. BeanFactoryPostProcessor
        1. BeanFactoryPostProcessor是在Spring容器加载Bean定义XML文件之后，Bean实例化之前执行
        2. BeanFactoryPostProcessor的执行顺序在BeanPostProcessor之前
        3. BeanFactoryPostProcessor 与 关于BeanPostProcessor 都是服务于Bean的生命周期中的，只是使用场景和作用略有不同。
+ Spring transactionnal注解但没有生效
   1. 主动捕获了异常，导致事务不能回滚
   2. 不是unchecked异常，事务不能回滚
   3. unchecked异常可以回滚
   4. 指定rollbackFor，事务可以回滚
   5. 同一个类中，一个不标注事务的方法去调用了标注事务的方法，事务会失效
        