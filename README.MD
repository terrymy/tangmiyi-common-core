公共模块-项目规范
===
一、编程规约
---
(1).命名风格
#
1.类名使用 UpperCamelCase 风格，但以下情形例外：DO/BO/DTO/VO/AO/等
##
2.所有编程相关的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式
##
3.方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase风格
##
4.常量命名全部大写，单词间用下划线隔开，力求语义表达完整清楚，不要嫌名字长
##
5.所有编程相关的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式
##
6.抽象类命名使用Abstract，异常类命名使用Exception结尾，工具累Utils结尾
##
7.不允许任何魔法值（即未经预先定义的常量）直接出现在代码中
##
(2).OOP规约
#
1.所有的覆写方法，必须加@Override注解
##
2.不能使用过时的类或方法
##
3.所有整型包装类对象之间值的比较，全部使用equals方法比较
##
4.任何货币金额，均以最小货币单位且整型类型来进行存储
##
5.定义数据对象 DO 类时，属性类型要与数据库字段类型相匹配
##
6.定义 DO/DTO/VO 等 POJO 类时，不要设定任何属性默认值
##
7.灵活应用Java8 StreamUtils工具类
##
8.避免用 Apache Beanutils 进行属性的 copy，使用base工具包里的Beanutils
##
9.对于精度技术不用 float 或 double，要用BigDecimal 或者 FloatDecimal
##
10.所有子服务都依赖common-core，公共的组件和工具都写在里头
##
11.所有controller加上@ServiceLogAop做公共日志输出，高并发或者不想输出日志方法单独注解@ServiceLogAop(logInfoOutput = false)，返回统一用ResultBean来接，否则无法打印日志，接口返回报错
##
12.common-core的pom不允许随意引入无用的依赖
##
13.不允许直接new一个线程，统一使用线程池工具类ThreadPoolUtils
##
(3).异常处理
#
1.异常处理原则请注意如果业务异常，业务方有能力处理自己处理掉，不要直接try cache(Exception)，否则无法补获公共异常
##
2.系统里只允许throw BusinessException和throw DisableNoticeBusinessException两种异常
##
3.所有对外提示的异常信息码统一维护到常量，提示信息统一写到resources/i18n，不允许直接在代码里写，否则报500错误信息
##
