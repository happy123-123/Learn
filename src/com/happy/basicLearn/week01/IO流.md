IO流

1. IO 流概述

• 定义：输入/输出流，用于程序与外部设备（文件、键盘、网络）之间的数据传输。

• 分类：
(1) 按方向：输入流（读数据，程序←外部）、输出流（写数据，程序→外部）。

(2) 按数据单位：字节流（读写字节，万能流，如 InputStream/OutputStream）、字符流（读写字符，仅用于文本文件，如 Reader/Writer）。

2. 字节流（抽象父类）

• InputStream（字节输入流）：核心方法 int read()（读1个字节，返回字节值；读到末尾返回-1）、int read(byte[] b)（读多个字节到数组，返回读取的字节数）、close()（关闭流，释放资源）。

• 常用实现类：FileInputStream（读取文件字节），构造方法 new FileInputStream("文件路径")（需处理 FileNotFoundException）。

• OutputStream（字节输出流）：核心方法 void write(int b)（写1个字节）、void write(byte[] b)（写数组中所有字节）、flush()（刷新缓冲区，字节流可省略，字符流必须）、close()。

• 常用实现类：FileOutputStream（写入文件字节），构造方法 new FileOutputStream("文件路径", true)（true 表示追加写入）。

3. 字符流（抽象父类）

• 基于字节流，通过编码表（如 UTF-8、GBK）将字节转为字符，避免文本文件乱码。

• Reader（字符输入流）：核心方法 int read()（读1个字符）、int read(char[] cbuf)（读多个字符到数组）、close()。

• 常用实现类：FileReader（读取文本文件字符），构造方法 new FileReader("文件路径")。

• Writer（字符输出流）：核心方法 void write(int c)（写1个字符）、void write(char[] cbuf)（写字符数组）、void write(String str)（写字符串）、flush()（必须调用，否则数据可能留在缓冲区）、close()。

• 常用实现类：FileWriter（写入文本文件字符），构造方法 new FileWriter("文件路径", true)（追加）。

4. 转换流（字节流→字符流）

• 问题：FileReader/FileWriter 依赖系统默认编码，易出现乱码，转换流可指定编码。

• 核心类（字节流与字符流的桥梁）：

• InputStreamReader：字节输入流 → 字符输入流，构造方法 new InputStreamReader(InputStream in, String charsetName)（如 new InputStreamReader(new FileInputStream("a.txt"), "UTF-8")）。

• OutputStreamWriter：字节输出流 → 字符输出流，构造方法 new OutputStreamWriter(OutputStream out, String charsetName)（如 new OutputStreamWriter(new FileOutputStream("b.txt"), "GBK")）。

• 示例（用转换流读UTF-8编码文件）：

    InputStream in = new FileInputStream("test.txt");
    FileReader reader = new InputStreamReader(in, "UTF-8");
    char[] buf = new char[1024];
    int len;
    while ((len = reader.read(buf)) != -1) {
    System.out.print(new String(buf, 0, len));
    }
    reader.close();
    in.close();

5. 序列流和反序列流

一、序列化流（对象操作输出流）

作用：将Java中的对象写入本地文件。

(1) 构造方法

                        构造方法                             说明
      public ObjectOutputStream(OutputStream out)    把基本流包装成高级流

(2) 成员方法
   
                        成员方法                                说明
      public final void writeObject(Object obj)      将对象序列化（写出）到文件中

二、反序列化流（对象操作输入流）

作用：把序列化到本地文件中的对象，读取到程序中来。

(1) 构造方法
   
                        构造方法                            说明
      public ObjectInputStream(InputStream out)      把基本流变成高级流

(2) 成员方法
   
                  成员方法                           说明
      public Object readObject()      将本地文件中的序列化对象读取到程序中

三、细节汇总

(1) 序列化前提：
   使用序列化流写对象时，JavaBean类必须实现Serializable接口，否则会抛出NotSerializableException异常。

(2) 序列化文件不可修改：
   序列化到文件中的数据不能手动修改，否则无法反序列化。

(3) 类修改后的反序列化问题：
   序列化对象后修改JavaBean类，再次反序列化会抛出InvalidClassException异常。

 解决方案：给JavaBean类添加serialVersionUID（版本号），格式为private static final long serialVersionUID = 1L;。

(4) 成员变量不参与序列化：
   若对象中某个成员变量不想被序列化，可使用transient关键字修饰，被标记的变量不参与序列化过程。

6. 打印流

一、打印流概述

打印流是Java IO中用于向文件/输出流写入数据的工具类，分为两类：

• 字节打印流：PrintStream

• 字符打印流：PrintWriter

二、打印流核心特点

(1) 只操作目的地，不操作数据源
   仅用于“写数据”，不能读数据。

(2) 数据原样写出
   打印的数据会以“原格式”写入文件（例如打印97，文件中直接显示97；打印true，文件中显示true）。

(3) 自动换行+自动刷新（特有方法）
   调用println()等方法时，会自动完成：写出数据 + 换行 + 刷新缓存。

三、字节打印流（PrintStream）

(1) 构造方法 

                        构造方法                                                              说明
      public PrintStream(OutputStream/File/String)                                 关联字节输出流/文件/文件路径
      public PrintStream(String fileName, Charset charset)                         指定字符编码
      public PrintStream(OutputStream out, boolean autoFlush)                      自动刷新（字节流底层无缓冲区，此参数实际无影响）
      public PrintStream(OutputStream out, boolean autoFlush, String encoding)     指定字符编码且自动刷新

(2) 成员方法
   
               方法                                                      说明
      public void write(int b)                              常规方法：写出指定字节
      public void println(Xxx xx)                           特有方法：打印任意数据 + 自动换行 + 刷新
      public void print(Xxx xx)                             特有方法：打印任意数据（不换行）
      public void printf(String format, Object... args)     特有方法：带占位符的打印（不换行）

四、字符打印流（PrintWriter）

(1) 构造方法
   
                     构造方法                                                                    说明
      public PrintWriter(Writer/File/String)                                        关联字符输出流/文件/文件路径
      public PrintWriter(String fileName, Charset charset)                          指定字符编码
      public PrintWriter(Writer w, boolean autoFlush)                               自动刷新（字符流底层有缓冲区，需手动开启）
      public PrintStream(OutputStream out, boolean autoFlush, Charset charset)      指定字符编码且自动刷新

(2) 成员方法
   
                  方法                                                  说明
      public void write(...)                                常规方法：写出字节/字符串
      public void println(Xxx xx)                           特有方法：打印任意数据 + 自动换行 + 刷新
      public void print(Xxx xx)                             特有方法：打印任意数据（不换行）
      public void printf(String format, Object... args)     特有方法：带占位符的打印（不换行）

五、总结

(1) 打印流分为字节打印流（PrintStream）和字符打印流（PrintWriter）。

(2) 打印流仅操作目的地，不操作数据源。

(3) 字节打印流：默认自动刷新，println()自动换行。

(4) 字符打印流：自动刷新需手动开启，println()自动换行。