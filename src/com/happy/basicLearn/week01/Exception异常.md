Exception

1. 异常概述

• 定义：程序运行时出现的不正常情况（如空指针、数组下标越界），会中断程序执行。

• 体系：顶层类 Throwable，子类 Error（严重错误，如内存溢出，无法处理）和 Exception（可处理异常）。

• Exception 分类：

• 编译时异常（受检异常）：必须处理（try-catch 或 throws），如 IOException、ParseException。

• 运行时异常（非受检异常）：无需强制处理，如 NullPointerException、ArrayIndexOutOfBoundsException、ClassCastException。

2. 异常处理机制

（1）try-catch-finally

• 语法：

    try {
    // 可能出现异常的代码
    } catch (异常类型1 变量名) {
    // 处理异常1的逻辑
    } catch (异常类型2 变量名) {
    // 处理异常2的逻辑（异常类型需从小到大）
    } finally {
    // 无论是否异常，必执行的代码（如关闭流、释放资源）
    }
    • 注意：catch 捕获的异常类型需与抛出的一致或为其父类；finally 中避免写 return（会覆盖 try/catch 中的 return）。

（2）throws 声明异常

• 语法：在方法声明后加 throws 异常类型1, 异常类型2，表示该方法可能抛出这些异常，由调用者处理。

• 示例：
    
    public void readFile() throws IOException {}

（3）throw 主动抛出异常

• 语法：在方法内部用 throw new 异常类型(提示信息)，主动触发异常。

• 示例：
    
    if (num < 0) throw new IllegalArgumentException("数值不能为负");

3. 自定义异常

• 步骤：1. 继承 Exception（编译时异常）或 RuntimeException（运行时异常）；2. 提供构造方法（无参、带消息参数）。

• 示例：

    class MyException extends Exception {
    public MyException() {}
    public MyException(String message) {
    super(message);
    }
    }