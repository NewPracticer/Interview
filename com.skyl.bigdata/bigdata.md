### 大数据
+ MapReduce
  1. Map
    1. Map输入：Hello World Bye World 
    2. Map输出： Hello 1 World 1 Bye 1 World 1
  2. Reduce 
    1. Reduce输入： Hello 1,1  World 1,1 Bye 1,1  World 1,1
    2. Reduce输出： Hello : 1;World:2;Bye:1
+ Hive
   + 可以写sql语句般的使用map reduce 应用.优雅的实现了join操作
+ Spark 为什么会比Map Reduce 更快
   + Reduce 和Map 的接洽
   + 数据依赖内存优先，而非HDFS优先
+ Spark家族
   + SparkSQL : 通过SparkSQL语句操作Spark的DataFrame or DataSet结构，或者类似JDBC的连接数据源。
   + Spark Streaming ： 窗口式的流式处理 。 和Flink的区别： Flink是真流式。
   + Spark Mlib ：机器学习框架