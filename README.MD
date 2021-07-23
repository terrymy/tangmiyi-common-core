公共模块-项目规范
===

| 名称 | 版本 | 说明 |
| --- | --- | --- |
| spring-cloud-alibaba | 2.2.3.RELEASE(Hoxton.SR8) | 微服务核心框架 |
| nacos | 1.4.1 | 注册中心配置中心 |
| seata | 1.4.0 | 分布式事务 |
| jetcahce | 2.5.14 | 分布式缓存框架 |
| gateway | 2.2.5.RELEASE | 微服务网关 |
| feign | 2.2.5.RELEASE | 服务通讯 |
| swagger | 3.0.0 | 接口文档 |
| xxl-job | 2.2.0 | 分布式任务调度 |
| tk-mybatis | 2.1.5 | ORM框架 |
| spring-booot | 2.3.2.RELEASE | spring框架 |
| elasticsearch | 2.3.2.RELEASE(7.11.1) | 搜索引擎 |
| sentinel | 2.2.3.RELEASE | 断路由降级容错 |
| prometheus | 2.24.1 | 监控日志 |
| rocketmq | 4.3.2 | 消息队列 |

一、工程流程规约
---
### 1.设计开发
1.1.需求文档，设计文档必须获得评审一致通过，并沉淀下来

1.2.开发设计文档模版必须包含三部分，数据库表结构，系统间交互时序图，api接口，可参考《开发设计文档模版》，其他类目指标可根据实际情况另行补充

1.3.任何需求和设计变更必须由负责人同步更新文档，并知会给项目相关人员

1.4.设计上满足性能(IO吞吐量，平均响应延迟)的前提下，简单容易扩展是首选

1.5.接口隔离，单一职责，对修改关闭，对拓展开放

1.6.对象的拓展多用组合，少用继承

1.7.面向事件驱动编程，监听模式(发短信，邮件)，发布订阅，观察者

### 2.应用分层
2.1.controller层，主要是对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等

2.2.service层，具体业务处理，统一规范有接口层和实现类

2.3.dao层，数据访问层，与底层 MySQL、Oracle、Hbase、OB 等进行数据交互

2.4.orm框架统一使用tkmybatis，不允许使用其他框架，分页插件统一使用pagehelper

2.5.client工程用来提供各个服务feign的调用，打成jar包并deploy到私有仓库，feign之间的调用用ResultBeanUtils抽象工具类，错误的都都传交给ResultBeanUtils来处理

### 3.实体枚举模型
3.1.DO（Data Object）：此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象

3.2.DTO（Data Transfer Object）：数据传输对象，Service向外传输的对象

3.3.Result：显示层对象，通常是 Web 向模板渲染引擎层传输的返回对象

3.4.Param：数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输

3.5.Enum：枚举类型，数据库所有表示yes or no意义的字段统一用CommonEnabledEnum枚举定义，枚举字段用@JsonEnumConvert统一输出(注意用此枚举字段要定义成Integer，枚举对象字段限定命名为code和desc)

### 4.微服务工程结构模块
4.1.可参考bbs-demo工程，zeekrlife-common-core和zeekrlife-common-base不要放在工程服务外层的pom里，包命名规范：com.zeekrlife.micro.服务名称.模块名称

4.2.Model模块:存放dto，param，result，enums，pom引用依赖zeekrlife-common-core

4.3.Client模块：对其他子服务提供feign接口，pom引用依赖model模块

4.4.Dao模块：存放mapper，do，mybatis.xml文件，pom引用依赖model模块，引用依赖zeekrlife-common-base

4.5.Service模块：存放controller，service，config等等业务逻辑，pom引用依赖client，dao模块

4.6.另外约定app端的接口不允许调用eaf服务的接口，统一由zeekrlife-eaf-provide这个服务来提供

4.7.zeekrlife-micro-common所有服务通用的依赖，有好的通用设计放在此工程分享，充实沉淀我们的"武器库"，master开新分支修改测试后提交merge request合并请求

4.8.为促进团队技术分享和氛围，不定期举行代码互相评审，评审记录模板《代码评审-XXX功能（评审人～被评审人）》

二、编程规约
---
### 1.命名风格
1.1类名使用 UpperCamelCase 风格，但以下情形例外：DO/BO/DTO/VO/AO/等

1.2.所有编程相关的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式

1.3.方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase风格

1.4.常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长

1.5.所有编程相关的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式

1.6.抽象类命名使用Abstract，异常类命名使用Exception结尾，工具类Utils结尾

1.7.不允许任何魔法值（即未经预先定义的常量）直接出现在代码中

### 2.OOP规约
2.1.所有的覆写方法，必须加@Override注解

2.2不能使用过时的类或方法

2.3.所有整型包装类对象之间值的比较，全部使用equals方法比较

2.4.任何货币金额，均以BigDecimal

2.5.定义数据对象 DO 类时，属性类型要与数据库字段类型相匹配

2.6.定义 DO/DTO/VO 等 POJO 类时，不要设定任何属性默认值

2.7.灵活应用Java8 StreamUtils工具类

2.8.避免用 Apache Beanutils 进行属性的 copy，建议使用Spring核心包bean下面的BeanUtils进行替代

2.9.对于精度技术不用 float 或 double，要用BigDecimal 或者 FloatDecimal

2.10.所有子服务都依赖common-core，公共的组件和工具都写在里头

2.11.所有controller加上@ServiceLogAop做公共日志输出，高并发或者不想输出日志方法单独注解@ServiceLogAop(logInfoOutput = false)，返回统一用ResultBean来接，否则无法打印日志，接口返回报错

2.12.common-core的pom不允许随意引入无用的依赖

2.13.不允许直接new一个线程，统一使用线程池工具类ThreadPoolUtils

2.14.对外响应api接口枚举不允许返回数据库字段含义，直接返回枚举对应描述信息

2.15.已无用或者弃用的api和方法请注释注解或者直接删掉

### 3.异常处理
3.1.异常处理原则请注意如果业务异常，业务方有能力处理自己处理掉，不要直接try cache(Exception)，否则无法补获公共异常

3.2.系统里有BusinessException业务异常，抛出会发送钉钉告警，如果不想钉钉告警，请抛出DisableNoticeBusinessException

3.3.系统做了国际化，提示信息码统一维护到各个服务的常量池，写到resources/i18n，不允许直接在代码里写中文提示信息，否则报500错误信息

3.4.关于错误提示信息msg需要使用占位符案例可以参考bbs-demo工程里的案例

三、MySQL数据库规约
---
### 1.建表规约
1.1.表名、字段名必须使用小写字母或数字，禁止出现数字开头，禁止两个下划线中间只出现数字

1.2.主键索引名为 pk_字段名，唯一索引名为 uk_字段名，普通索引名则为 idx_字段名

1.3.小数类型为 decimal，禁止使用 float 和 double

1.4.表必备字段：主键id, deleted, created_at, updated_at，主键id可用根据表实际业务用自增或者雪花算法，created_by和updated_by可根据实际业务需要添加

1.5.单表行数超过 500 万行或者单表容量超过 2GB，才推荐进行分库分表

1.6.使用DECIMAL(19,4)存储货币

1.7.单表索引建议控制在5个以内，建立组合索引，必须把区分度高的字段放在前面 

1.8.必须使用InnoDB存储引擎，字符集统一使用utf8mb4

1.9.所有字段均不允许为null(特殊业务需要设置为null的需要说明)，必须有默认值，必须有注释，字典字段类型均为tinyint(2)

1.10.所有时间类型字段均为DATETIME默认值为CURRENT_TIMESTAMP

1.11.所有测试环境的ddl语句全部收回，由技术负责人统一执行

1.12.sql脚本统一放在对应微服务的src/main/sql目录下，命名规范：时间_微服务名称，例如20210220_platform_manage.sql

### 2.SQL语句规约
2.1.不要使用 count(列名)或 count(常量)来替代 count(*)

2.2.禁止在WHERE条件的属性上使用函数或者表达式

2.3.禁止使用存储过程，存储过程难以调试和扩展，更没有移植性

2.4.在表查询中，一律不要使用 * 作为查询的字段列表，需要哪些字段必须明确写明

2.5.不允许直接拿 HashMap 与 Hashtable 作为查询结果集的输出

2.6.禁止使用小数存储货币，使用整数，小数容易导致钱对不上

2.7.禁止负向查询，以及%开头的模糊查询 a.负向查询条件：NOT、!=、<>、!<、!>、NOT IN、NOT LIKE等，会导致全表扫描; b.%开头的模糊查询，会导致全表扫描 

2.8.禁止使用OR条件，必须改为IN查询

四、redis规约
---
### 1.缓存设置
1.1.关于分布式缓存已集成jetcache，开发使用可参考 https://github.com/alibaba/jetcache/wiki/Home_CN 注意:如果参数是对象，那么需要保证参数对象的属性在方法在业务代里没有被修改，否则会存在缓存无法命中的问题

1.2.关于分布式锁已集成redission，可用@RedissonLock注解在方法上按输入参数和实际业务判断是否跳过不处理，是否锁整个方法，码块可参考@RedissonLock此注解写法

1.3.统一使用jetcache作为缓存客户端，支持本地缓存(一级缓存)和远程缓存(二级缓存)，支持方法注解@Cached缓存方式和手动api调用方式，注解方式为主，手动api调用为辅，手动缓存命名规则为"服务名称:服务模块:业务归属唯一标识ID"

1.4.对于需要redis客户端命令的原生操作，jetcache支持Lettuce异步非阻塞，统一用RedisCommands

五、其他
---
### 1.关于MQ
1.1.统一使用spring cloud stream框架

1.2.topic统一命名"环境-服务名称-模块"，group统一命名"环境-服务名称-模块-group"

### 2.关于分布式任务调度
2.1.统一使用xxl-job，可参考https://www.xuxueli.com/xxl-job/

### 3.关于excel操作
3.1.统一使用easyexcel，可参考https://github.com/alibaba/easyexcel

### 4.关于分布式事务
4.1.能不用分布式事务就不用，如果非得使用的，结合具体业务分析，比较适合哪一种，是在乎强一致，还是最终一致，分布式事务中间件统一使用seata（默认AT模式-强一致性），可参考http://seata.io/zh-cn

### 5.关于sentinel
5.1.可参考https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel

### 6.关于Java工具类库
6.1.core已集成Hutool，统一使用Hutool，可参考https://www.hutool.cn/docs/#/

### 7.关于elasticsearch
7.1.客户端api使用 high-level rest，api文档可参考https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-getting-started.html