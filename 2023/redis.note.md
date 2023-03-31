# redis

## note

- 配置相关
- springboot redis
    - 自动配置
    - 连接及管理
- 持久化



在Redis的官网上，Java语言的推荐客户端列表中，Redisson 便在其中。
Redisson是在Redis的基础上实现的Java驻内存数据网格（In-Memory Data Grid）.

它不仅提供了一系列的 redis 常用数据结构命令服务，还提供了许多分布式服务，例如分布式锁、分布式对象、分布式集合、分布式远程服务、分布式调度任务服务等等。

它充分利用了Redis键值数据库提供的一系列优势，基于Java实用工具包中常用接口，为使用者提供了一系列具有分布式特性的常用工具类，让使用Redis更加简单、便捷，从而让使用者能够将更多精力集中到业务逻辑处理上。

Redisson 在 java.util 中常用接口的基础上，为我们提供了一系列具有分布式特性的工具类。

RLock 是 Java 中可重入锁的分布式实现

Redisson不仅仅是一个Redis客户端，它还实现了很多具有分布式特性的常用工具类，例如分布式锁、布隆过滤器等

Redisson中如何轻松操作Redis中的字符串(strings)、哈希(hashes)、列表(lists)、集合(sets)和有序集合(sorted sets)，以及如何使用Redisson实现的布隆过滤器和分布式锁，最后分析一下Redisson中分布式锁的解决方案。

-----
总结下来，Jedis 把 Redis 命令封装的非常全面，Lettuce 则进一步丰富了 Api，支持 Redis 各种高级特性。

但是两者并没有进一步深化，只给了你操作 Redis 数据库的工具，而 Redisson 则是基于 Redis、Lua 和 Netty 建立起了一套的分布式解决方案，比如分布式锁的实现，分布式对象的操作等等。

在实际使用过程中，Lettuce + Redisson组合使用的比较多，两者相铺相成。

关于分布式锁实现的应用，生产环境推荐尽量采用单点环境来实现，基本上解决绝大部分的分布式锁问题，如果当前服务的环境确实很复杂，可以采用RedissonRedLock来实现。

----


追踪请求线程
统计重入次数的能力
不具备操作的原子性






## Reference

[SpringBoot教程(十四) | SpringBoot集成Redis(全网最全)](https://cloud.tencent.com/developer/article/1975743)
[redis的数据类型以及使用场景总结](https://juejin.cn/post/7193618755153788983)

