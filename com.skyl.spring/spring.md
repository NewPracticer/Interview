### Spring

#### SpingIOC 
+ IOC 原理
    1. 依赖倒置原则（DI） -(思路)》 
        2. 控制反转（IOC） 第三方容器（IOC Container）-（方法）》 
            3. 依赖注入（DI）(把底层类作为参数传递给上层类，实现上层对下层的控制)。Set注入，接口注入，注解注入，构造器注入。
        3. IOC Container的优势：避免在各处使用new来创建类，并且可以做到统一维护。创建实例的时候不需要了解其中细节。
+ SpringIOC
   1. SpringIOC容器流程
      1. 读取Bean配置信息
      2. 根据Bean注册表实例化Bean
      3. 将Bean实例放到Spring容器中
      4. 使用Bean
   2. Spring IOC支持的功能
      1. 依赖注入
      2. 依赖检查
      3. 自动装配
      4. 支持集合
      5. 指定初始化方法和销毁方法
      6. 支持回调方法
   3. Spring IOC 核心接口
      1. BeanFactory
      2. ApplicationContext
      3. BeanDefinition 主要用来描述Bean的定义
      4. BeanDefinitionRegistry ：提供向IOC容器注册BeanDefinition对象的方法