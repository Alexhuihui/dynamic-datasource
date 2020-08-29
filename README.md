2.1 方案一
基于 Spring AbstractRoutingDataSource 做拓展。

简单来说，通过继承 AbstractRoutingDataSource 抽象类，实现一个管理项目中多个 DataSource 的动态 DynamicRoutingDataSource 实现类。这样，Spring 在获取数据源时，可以通过 DynamicRoutingDataSource 返回实际的 DataSource 。

然后，我们可以自定义一个 @DS 注解，可以添加在 Service 方法、Dao 方法上，表示其实际对应的 DataSource 。

如此，整个过程就变成，执行数据操作时，通过“配置”的 @DS 注解，使用 DynamicRoutingDataSource 获得对应的实际的 DataSource 。之后，在通过该 DataSource 获得 Connection 连接，最后发起数据库操作。

可能这么说，没有实现过多数据源的胖友会比较懵逼，比较大概率。所以推荐胖胖看看艿艿的基友写的 《剖析 Spring 多数据源》 文章。

不过呢，这种方式在结合 Spring 事务的时候，会存在无法切换数据源的问题。具体我们在 「3. baomidou 多数据源」 中，结合示例一起来看。

艿艿目前找了一圈开源的项目，发现比较好的是 baomidou 提供的 dynamic-datasource-spring-boot-starter 。所以我们在 「3. baomidou 多数据源」 和 「4. baomidou 读写分离」 中，会使用到它。

2.2 方案二
不同操作类，固定数据源。

关于这个方案，解释起来略有点晦涩。以 MyBatis 举例子，假设有 orders 和 users 两个数据源。
那么我们可以创建两个 SqlSessionTemplate ordersSqlSessionTemplate 和 usersSqlSessionTemplate ，分别使用这两个数据源。

然后，配置不同的 Mapper 使用不同的 SqlSessionTemplate 。

如此，整个过程就变成，执行数据操作时，通过 Mapper 可以对应到其 SqlSessionTemplate ，使用 SqlSessionTemplate 获得对应的实际的 DataSource 。之后，在通过该 DataSource 获得 Connection 连接，最后发起数据库操作。

咳咳咳，是不是又处于懵逼状态了？！没事，咱在 「5. MyBatis 多数据源」、「6. Spring Data JPA 多数据源」、「7. JdbcTemplate 多数据源」 中，结合案例一起看。「Talk is cheap. Show me the code」

不过呢，这种方式在结合 Spring 事务的时候，也会存在无法切换数据源的问题。淡定淡定。多数据源的情况下，这个基本是逃不掉的问题。

2.3 方案三
分库分表中间件。

对于分库分表的中间件，会解析我们编写的 SQL ，路由操作到对应的数据源。那么，它们天然就支持多数据源。如此，我们仅需配置好每个表对应的数据源，中间件就可以透明的实现多数据源或者读写分离。

目前，Java 最好用的分库分表中间件，就是 Apache ShardingSphere ，没有之一。

那么，这种方式在结合 Spring 事务的时候，会不会存在无法切换数据源的问题呢？答案是不会。在上述的方案一和方案二中，在 Spring 事务中，会获得对应的 DataSource ，再获得 Connection 进行数据库操作。而获得的 Connection 以及其上的事务，会通过 ThreadLocal 的方式和当前线程进行绑定。这样，就导致我们无法切换数据源。

难道分库分表中间件不也是需要 Connection 进行这些事情么？答案是的，但是不同的是分库分表中间件返回的 Connection 返回的实际是动态的 DynamicRoutingConnection ，它管理了整个请求（逻辑）过程中，使用的所有的 Connection ，而最终执行 SQL 的时候，DynamicRoutingConnection 会解析 SQL ，获得表对应的真正的 Connection 执行 SQL 操作。

难道方案一和方案二不可以这么做吗？答案是，当然可以。前提是，他们要实现解析 SQL 的能力。

那么，分库分表中间件就是多数据源的完美方案落？从一定程度上来说，是的。但是，它需要解决多个 Connection 可能产生的多个事务的一致性问题，也就是我们常说的，分布式事务。关于这块，艿艿最近有段时间没跟进 Sharding-JDBC 的版本，所以无法给出肯定的答案。不过我相信，Sharding-JDBC 最终会解决分布式事务的难题，提供透明的多数据源的功能。