<style>
table th:first-of-type {
    width: 50pt;
}
table th:nth-of-type(3) {
    width: 30%;
}
table th:nth-of-type(4) {
    width: 30%;
}
</style>

# xxl-job

## 学习背景

在 Java 相关的后端服务开发中，会经常接触到多任务的动态创建、定时任务的实时执行。在多实例的业务场景中，java.util.Timer/SpringBoot @Scheduled注解均无法实现多任务的业务功能设计。这种情况下，则需要引入分布式的任务调度方案，解决定时任务的触发、调度及执行。

## 分布式任务调度方案对比

| 项目  | Quartz    | Elastic-Job   | XXL-JOB   |
| :--- | :--- | :--- | :--- |
| 依赖  | MySQL | jdk1.7+，zookeeper3.4.6+，maven3.0.4+，mesos | mysql，jdk1.7+，maven3.0+ |
| 定时调度  | Cron  | Cron  | Cron  |
| 管理界面  | 无    | 支持  | 支持（可查看历史记录、运行日志、监控大盘）|
| 多语言    | Java  | Java、脚本任务    | Java、脚本任务    |
| 报警监控  | 无    | 邮件  | 邮件  |

从以上表格中可以看出，相比 Quartz，任务调度平台 xxl-job 支持集群、统计，拥有管理平台、失败报警、可监控等，在微服务项目的复杂业务场景中，可以更方便地支持业务开发。

Quartz 作为开源的 Java 任务调度框架，是多数场景下任务调度的首选。在集群环境中，Quartz 采用 API 对任务进行管理，也存在一些问题：
    - 调用API的的方式操作任务，不人性化；
    - 需要持久化业务QuartzJobBean到底层数据表中，系统侵入性相当严重。
    - 调度逻辑和QuartzJobBean耦合在同一个项目中，这将导致一个问题，在调度任务数量逐渐增多，同时调度任务逻辑逐渐加重的情况下，此时调度系统的性能将大大受限于业务；
    - quartz底层以“抢占式”获取DB锁并由抢占成功节点负责运行任务，会导致节点负载悬殊非常大；而XXL-JOB通过执行器实现“协同分配式”运行任务，充分发挥集群优势，负载各节点均衡。
XXL-JOB 弥补了 quartz 的上述不足之处，同时支持统计、日志记录，拥有管理平台、失败报警、可监控等，在微服务项目的复杂业务场景中，可以更方便地支持业务开发。

xxl-job中心式的调度平台轻量级，开箱即用，操作简易，上手快，与SpringBoot有非常好的集成，而且监控界面就集成在调度中心，界面简洁，对于企业维护起来成本不高，还有失败的邮件告警等等。这就使很多企业选择xxl-job做调度平台。

xxl-job则相反，是通过一个中心式的调度平台，调度多个执行器执行任务，调度中心通过DB锁保证集群分布式调度的一致性，这样扩展执行器会增大DB的压力，但是如果实际上这里数据库只是负责任务的调度执行。但是如果没有大量的执行器的话和任务的情况，是不会造成数据库压力的。实际上大部分公司任务数，执行器并不多。


### cron 表达式
```
    最初是Unix操作系统中的 cron 守护进程所使用的一种语法规则，用于设置定时任务。cron守护进程是Unix系统中的一个后台进程，用于周期性地执行指定的命令或脚本。它可以根据用户的需求，按照指定的时间间隔或时间点来执行任务，通常用于定时备份、清理日志、发送邮件等操作。
    为了方便用户设置定时任务，cron守护进程引入了一种简单的语法规则，即cron表达式。通过这种语法规则，用户可以非常灵活地设置定时任务，满足不同的需求。
    随着Unix操作系统的普及和互联网的发展，cron表达式逐渐成为了一种通用的定时任务设置语法，被广泛应用于各种计算机系统和应用程序中。目前，cron表达式已经成为了计算机科学中的一个基本概念，被包括Java、Python、Ruby等编程语言在内的许多软件框架和库所支持和应用。
```

- cron 表达式在任务调度框架中，也被用来配置定时任务的执行规则。
- 表达式组成：
    - CRON表达式基本格式为：* * * * *，每个星号代表一个字段的取值范围。CRON表达式中的每个字段都可以使用以下符号和数字设置特定的任务执行时间：
        - 数字：表示对应的时间点值（如1表示1点）
        - 星号（*）：表示该字段的所有可能值，如分钟字段中的星号表示每分钟的任何时间点
        - 逗号（,）：用于分割字段中的多个取值，如“2,5,10”表示取值为2、5、10的时间点
        - 连接符（-）：用于指定时间段，如“1-5”表示取值范围为1到5的所有时间点
        - 斜杠（/）：用于指定时间步长，如“*/10”表示每隔10个时间点执行一次任务

| 特殊字符   | 含义|
| :--- | :---  |
| * | 所有可能值    |
| / | 步长  |
| , | 列举多个值，表示具体时间点   |
| - | 表示范围，如1-5表示取值范围为1到5的所有时间点 |
| ? | 日和星期任选其一  |
| L | last，表示某个月最后一天或某个星期的最后一天  |
| W | 工作日（周一至周五）|
| # | 表示某个月的第几个星期几，如4#2表示某月的第二个星期四 |

### Quartz 调度器

Spring Boot 可以很方便地集成 Quartz，引入相关依赖 ```spring-boot-starter-quartz```，实现对定时任务的相关操作（创建、启停、删除等）。
- 通过 Spring 中的 SchedulerFactoryBean 自动配置的 Scheduler，代表一个调度容器，相关的类型主要包括：
    - JobDetail： 定义一个接口 Job 的实现类，是一个具体的可执行的调度程序，同时包含了这个任务调度的方案和策略；
    - Trigger： 代表一个调度参数的配置，什么时候去调；
    - 一个调度容器 Scheduler 中可以注册多个 JobDetail 和 Trigger。

- Quartz 存储方式有两种：
    - MEMORY
        - 默认是内存形式维护任务信息，意味着服务重启了任务就从头再来;
    - JDBC:
        - JDBC 能够把任务信息持久化到数据库;

## xxl-job 

[xxl-job架构图](xxl-job%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

设计思想
将调度行为抽象形成“调度中心”公共平台，而平台自身并不承担业务逻辑，“调度中心”负责发起调度请求。

将任务抽象成分散的JobHandler，交由“执行器”统一管理，“执行器”负责接收调度请求并执行对应的JobHandler中业务逻辑。

因此，“调度”和“任务”两部分可以相互解耦，提高系统整体稳定性和扩展性；

5.3.2 系统组成
调度模块（调度中心）：
负责管理调度信息，按照调度配置发出调度请求，自身不承担业务代码。调度系统与任务解耦，提高了系统可用性和稳定性，同时调度系统性能不再受限于任务模块；
支持可视化、简单且动态的管理调度信息，包括任务新建，更新，删除，GLUE开发和任务报警等，所有上述操作都会实时生效，同时支持监控调度结果以及执行日志，支持执行器Failover。
执行模块（执行器）：
负责接收调度请求并执行任务逻辑。任务模块专注于任务的执行等操作，开发和维护更加简单和高效；
接收“调度中心”的执行请求、终止请求和日志请求等。


5.4 调度模块剖析
5.4.1 quartz的不足
Quartz作为开源作业调度中的佼佼者，是作业调度的首选。但是集群环境中Quartz采用API的方式对任务进行管理，从而可以避免上述问题，但是同样存在以下问题：

问题一：调用API的的方式操作任务，不人性化；
问题二：需要持久化业务QuartzJobBean到底层数据表中，系统侵入性相当严重。
问题三：调度逻辑和QuartzJobBean耦合在同一个项目中，这将导致一个问题，在调度任务数量逐渐增多，同时调度任务逻辑逐渐加重的情况下，此时调度系统的性能将大大受限于业务；
问题四：quartz底层以“抢占式”获取DB锁并由抢占成功节点负责运行任务，会导致节点负载悬殊非常大；而XXL-JOB通过执行器实现“协同分配式”运行任务，充分发挥集群优势，负载各节点均衡。
XXL-JOB弥补了quartz的上述不足之处。

5.4.2 自研调度模块
XXL-JOB最终选择自研调度组件（早期调度组件基于Quartz）；一方面是为了精简系统降低冗余依赖，另一方面是为了提供系统的可控度与稳定性；

XXL-JOB中“调度模块”和“任务模块”完全解耦，调度模块进行任务调度时，将会解析不同的任务参数发起远程调用，调用各自的远程执行器服务。这种调用模型类似RPC调用，调度中心提供调用代理的功能，而执行器提供远程服务的功能。

5.4.3 调度中心HA（集群）
基于数据库的集群方案，数据库选用Mysql；集群分布式并发环境中进行定时任务调度时，会在各个节点会上报任务，存到数据库中，执行时会从数据库中取出触发器来执行，如果触发器的名称和执行时间相同，则只有一个节点去执行此任务。

5.4.4 调度线程池
调度采用线程池方式实现，避免单线程因阻塞而引起任务调度延迟。

5.4.5 并行调度
XXL-JOB调度模块默认采用并行机制，在多线程调度的情况下，调度模块被阻塞的几率很低，大大提高了调度系统的承载量。

XXL-JOB的不同任务之间并行调度、并行执行。
XXL-JOB的单个任务，针对多个执行器是并行运行的，针对单个执行器是串行执行的。同时支持任务终止。

六、调度中心/执行器 RESTful API
XXL-JOB 目标是一种跨平台、跨语言的任务调度规范和协议。

针对Java应用，可以直接通过官方提供的调度中心与执行器，方便快速的接入和使用调度中心，可以参考上文 “快速入门” 章节。

针对非Java应用，可借助 XXL-JOB 的标准 RESTful API 方便的实现多语言支持。

调度中心 RESTful API：
说明：调度中心提供给执行器使用的API；不局限于官方执行器使用，第三方可使用该API来实现执行器；
API列表：执行器注册、任务结果回调等；
执行器 RESTful API ：
说明：执行器提供给调度中心使用的API；官方执行器默认已实现，第三方执行器需要实现并对接提供给调度中心；
API列表：任务触发、任务终止、任务日志查询……等；
此处 RESTful API 主要用于非Java语言定制个性化执行器使用，实现跨语言。除此之外，如果有需要通过API操作调度中心，可以个性化扩展 “调度中心 RESTful API” 并使用。


## xxl-job 在 Spring 项目中的具体应用

### 多任务的定时触发执行

### 动态创建任务实现方式

    - xxl-job 最常见的使用场景为：网页端手动创建调度任务，业务相关的 Spring 项目开发任务执行方法（引入@XxlJob）。
    - 但在动态创建、执行定时任务的业务场景中，Spring 项目针对 xxl-job 则需要进行更多的开发步骤：
        - 配置

## 参考

- [**3千字带你搞懂XXL-JOB任务调度平台**](https://zhuanlan.zhihu.com/p/263051022)

- [**PowerJob 简介(任务调度平台的对比）**](https://www.yuque.com/powerjob/guidence/intro)

