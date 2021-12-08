### LucenceAndEs
Lucence和ES的搜索
####  Lucence
+ 中文全文索引
   + 能处理中文的AnaLyzer
      1. 庖丁
      2. 盘库
      3. IK
   + 分词
      1. 构造索引
      2. 构造查询
+ Lucence的评分机制
   + 结果 <TF - IDF> 文档ID
   + 词频 (TF) 词在文档中出现的频率
   + 逆文档频率：词在所有文档中出现的频率越低说明词越重要
#### ES 
+ ES
    + 读: Primary|Replica
    + 写：Primary| Replica转发Primary
+ Filebeat :收集日志到ES
+ Kibana ： 基于ES数据的可视化工具