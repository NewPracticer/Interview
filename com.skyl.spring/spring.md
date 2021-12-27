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