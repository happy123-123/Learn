### 事务 (事务是一组操作的集合,是一个不可分割的工作单位。这组操作要么全部成功,要么全部失败)

1. 如何控制事务

   (1)开启事务:start transaction/begin;

   (2)提交事务:commit;(全部成功)

   (3)回滚事务:rollback;(只要有一项失败)


2. Spring事务管理  

    (1)添加@Transactional注解: 其会在方法运行之前,开启事务,运行完毕后,根据运行的结果,来提交或回滚事务 (位置:方法上、类上、接口上)  

    (2)常用rollbackFor属性用于控制出现何种异常类型,回滚事务,如：

>@Transactional(rollbackFor = {Exception.class})

(3)事务传播行为:指的就是当一个事务方法被另一个事务方法调用时,这个事务方法应该如何进行事务控制(此时可在@Transactional注解中指定以下属性)

          属性值                           含义
      
      (常见)REQUIRED【默认值】     需要事务,有则加入,无则创建新事务 需要新事务
      
      (常见)REQUIRES_NEW         无论有无,总是创建新事务
      
      SUPPORTS                  支持事务,有则加入
      
      NOT_SUPPORTED             无则在无事务状态中运行
      
      MANDATORY                 不支持事务,在无事务状态下运行
      
      NEVER                     如果当前存在已有事务,则挂起当前事 必须有事务,否则抛异 必须没事务,否则抛异常

3. 事务四大特性

    (1) 原子性:事务中的所有操作必须全部成功,或全部失败,不能只部分成功

    (2) 一致性:事务中的所有操作必须全部成功,或全部失败,不能只部分成功

    (3) 隔离性:多个事务不能同时对同一数据进行操作,数据会被其他事务修改,导致数据不一致

    (4) 持久化:数据在事务提交后,才会被持久化到数据库中

***

### 异常处理：  
可定义全局异常处理类，如：

      @RestControllerAdvice
      public class GlobalExceptionHandler {
      @ExceptionHandler
      public Result handleException(Exception e){
         log.error("全局异常处理器,拦截到异常”,e);
         return Result.error("对不起,服务器异常,请稍后重试”);
      }
注意：  

(1)其中可以添加多个异常处理方法：  
若添加多个异常处理方法，则优先级为：先匹配的异常处理方法优先级高  

(2)可以自定义异常处理方法

***

### MyBatis使用技巧
1. 注解与XML混合使用
   使用 @Select、@Insert、@Update、@Delete 等注解处理简单SQL语句，直接在 mapper 接口中定义
   复杂查询和需要动态SQL的场景使用XML映射文件，如 EmpMapper.xml 中的复杂查询


2. 动态SQL构建
   使用 <if> 标签实现条件判断，如多个查询条件
   使用 <where> 标签自动处理WHERE子句和AND/OR关键字
   使用 <foreach> 标签处理集合操作，如批量删除和插入


3. 结果映射处理

   (1)开启驼峰命名配置(map-underscore-to-camel-case) 实现数据库下划线命名到Java驼峰命名的自动转换  
   (2)使用 `<resultType>` 指定简单结果映射  
   (3)复杂对象关系映射可通过 `<resultMap>` 标签实现
- 如果查询返回的字段名与实体的属性名可以直接对应上,用resultType
- 如果查询返回的字段名与实体的属性名对应不上,或实体属性比较复杂,可以通过resultMap手
  动封装


4. 预编译 (Precompilation)
   MyBatis使用 #{} 占位符实现SQL预编译，防止SQL注入攻击 如使用 #{operateEmpId}, #{operateTime} 等占位符  
   预编译语句在数据库端缓存执行计划，提高执行效率  
   相比于 ${} 字符串拼接方式，#{} 更安全且性能更好

***

### 会话跟踪：
### 1. cookie:  

- 工作原理：  

   (1)浏览器发送请求时携带 Cookie: name=value  
   (2)服务器响应时通过 Set-Cookie: name=value 设置cookie  
   (3)后续请求自动携带该cookie  


- 优点：  

  (1)HTTP协议原生支持  
  (2)实现简单，浏览器自动处理  


- 缺点：  

  (1)移动端APP无法使用Cookie  
  (2)安全性差，用户可禁用或修改  
  (3)不支持跨域访问

### 2. session:  

- 工作原理：  

   (1)服务器生成sessionID(如JSESSIONID=1)  

   (2)通过Cookie将sessionID返回给浏览器  

   (3)浏览器后续请求携带该sessionID  

   (4)服务器根据sessionID查找对应session数据


- 优点：  

  (1)数据存储在服务端，安全性高  

  (2)可以存储复杂对象  


- 缺点：  

  (1)集群环境下无法直接共享session  

  (2)增加服务器内存压力  

  (3)仍存在Cookie的局限性  

### 3. 令牌


- 工作原理： 

   (1)用户登录成功后获取令牌  
   (2)请求时在请求头中携带令牌(如Authorization)  
   (3)服务器验证令牌有效性  
   (4)无需在客户端存储敏感信息  


- 优点：

  (1)支持PC端和移动端  
  (2)解决集群环境下的认证问题  
  (3)减轻服务器端存储压力  
  (4)更好的安全性  


- 缺点：  

  (1)需要自行实现令牌管理逻辑  
  (2)需要处理令牌过期、刷新等机制

***

### 令牌的组成：
1. 头部（header）记录令牌的类型，签名算法


2. 载荷（payload）记录令牌的信息 如用户名、密码等


3. 签名（signature）防止被篡改，保证安全性

### 登录界面的实现：JWT令牌机制  
即：
1. 用户登录成功，返回一个令牌


2. 令牌在服务器端保存，并设置过期时间


3. 令牌在客户端保存


4. 访问受保护的资源时，自动携带令牌，服务器端验证令牌，并返回结果

***

### 令牌验证：(在校验令牌时，需要验证令牌是否过期，是否被修改，是否正确)  
设置过滤器或拦截器，在请求资源访问时，验证令牌

### 过滤器：(拦截所有请求资源)
1. 创建过滤器类 并实现Filter接口


2. 配置过滤器 通过给实现类添加@WebFilter注解来配置过滤器路径，并给启动类添加@ServletComponentScan注解


3. 过滤器在实现类中重写doFilter方法放行，才能访问对应资源

### 拦截器：(只拦截spring中的请求资源)
1. 创建拦截器类 并实现HandlerInterceptor接口


2. 配置拦截器 实现WebMvcConfigurer接口 重写addInterceptors方法添加拦截器(配置路径),其中addPathPatterns添加拦截路径 excludePathPatterns排除拦截路径


3. 拦截器在实现类中重写preHandle方法放行(return true)，才能访问对应资源 (还有postHandle(报错后不会执行)和afterCompletion(报错后会执行)方法 一个是请求后 渲染前处理，一个是后处理)

注意：若拦截器与过滤器都配置了拦截路径，则优先执行过滤器

通过过滤器或拦截器后:
1. 获取令牌


2. 验证令牌 验证时可以使用密钥对令牌进行验证)


3. 验证成功则返回结果，验证失败则返回错误信息

***

### AOP面向切面编程：(通过动态代理：运行时生成代理对象，在调用原始方法时，将代理对象作为参数传递给原始方法)
1. 自定义注解结合AOP
   创建自定义注解 @Annotation 用于标记需要进行切面处理的方法
   通过 @Target(ElementType.METHOD) 指定注解只能用于方法级别
   使用 @Annotation 标记需要记录日志的操作方法，如删除、新增和更新部门
   AOP切面通过 @Around(" ") 拦截所有标记了自定义注解的方法


2. 方法执行监控(例如耗时监控)
   使用 System.currentTimeMillis() 记录方法执行的开始和结束时间
   计算方法执行耗时 costTime 并记录到操作日志中
   通过 ProceedingJoinPoint 获取方法签名、参数等信息
   实现对业务方法执行性能的监控和统计


3. 日志统一记录
   在 @Aspect注解下的实现类中定义统一处理操作日志记录逻辑
   记录完整的操作信息包括：类名 className、方法名 methodName、方法参数 methodParams、返回值 returnValue、操作时间 operateTime 等
   通过 insert(" ")类似的sql语句 将操作日志持久化到数据库中
   实现业务操作与日志记录的解耦，避免在业务代码中混入日志记录逻辑

***

### AOP核心概念  

- 连接点:JoinPoint,可以被AOP控制的方法(暗含方法执行时的相关信息)


- 通知:Advice,指那些重复的逻辑,也就是共性功能(最终体现为一个方法)


- 切入点:PointCut,匹配连接点的条件,通知仅会在切入点方法执行时被应用


- 切面:Aspect,描述通知与切入点的对应关系(通知+切入点)


- 目标对象:Target,通知所应用的对象

***

### AOP 通知类型  

@Around(环绕通知)
-   在目标方法执行前和执行后都被调用  
-   需要手动调用 ProceedingJoinPoint.proceed() 来执行原始方法  
-   方法返回值必须为 Object 类型，用于接收原始方法的返回值  

@Before(前置通知)
-   在目标方法执行前被调用
-   不影响目标方法的执行流程

@After(后置通知)
-   在目标方法执行后被调用，无论是否发生异常都会执行
-   适用于清理资源等操作

@AfterReturning(返回后通知)
-   在目标方法成功执行后被调用
-   如果目标方法抛出异常，则不会执行此通知

@AfterThrowing(异常后通知)
-   当目标方法抛出异常时被调用
-   适用于异常处理、错误日志记录等场景

关键注意事项  
@Around 特性：  
-   必须显式调用 ProceedingJoinPoint.proceed() 才能执行原始方法  
-   返回值类型必须为 Object，用于接收原始方法的返回结果

***

### AOP 切入点
1. @PointCut  
   作用：将公共的切入点表达式抽取出来，便于复用
   语法：

        @Pointcut("execution(* com.itheima.service.impl.DeptServiceImpl.*(..))")
        public void pt() {}

    访问控制：  
    private：仅能在当前切面类中引用  
    public：可在其他外部切面类中引用


2. 切入点表达式 - execution  
   基本语法：

        @Pointcut("execution(* com.itheima.service.impl.DeptServiceImpl.*(..))")
        public void pt() {}
    通配符使用：  
    *：匹配任意返回值、包名、类名、方法名或单个参数  
    ..：匹配任意层级的包或任意数量的参数  
    示例：

        execution(* com.*.service.*.update*(,))
        execution(* com.itheima..DeptService.*(..))


3. 切入点表达式 - @annotation  
    用途：匹配带有特定注解的方法  
    语法：

        @Around("@annotation(com.itheima.anno.LogOperation)")
        public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行逻辑
        }
    应用场景：通过自定义注解标记需要记录日志的方法


4. 连接点 (JoinPoint)  
   作用：获取方法执行时的相关信息  
   类型：  
   >ProceedingJoinPoint：用于 @Around 通知，可调用 proceed() 执行原始方法  
   JoinPoint：用于其他四种通知类型

   常用方法：

        joinPoint.getTarget().getClass().getName() // 获取目标类名
        joinPoint.getSignature().getName() // 获取方法名
        joinPoint.getArgs() // 获取方法参数


5. 通知顺序控制  
-   默认排序规则：  
   不同切面类按类名字母顺序排序:  
    (1)前置通知：字母靠前的先执行  
    (2)后置通知：字母靠前的后执行  


-   显式控制：  
   使用 @Order(数字) 注解指定执行顺序：  
    (1)数字小的优先执行（前置通知）  
    (2)数字小的后执行（后置通知）

## 注意：通过反射getClass的getDeclaredMethod获取到方法名
## 再通过反射的invoke赋值到当前方法

***

### ThreadLocal(本地线程)  

- new一个ThreadLocal对象 或使用工具类管理当前线程的用户信息


- 在过滤器或拦截器中解析token后将用户ID存储到 ThreadLocal 中


- 通过 set 设置线程储存的信息，如当前用户ID


- 在业务代码中通过 get 获取信息（当前用户ID）（可结合aop实现）


- 请求处理完成后通过 remove() 清理ThreadLocal资源，防止内存泄漏


