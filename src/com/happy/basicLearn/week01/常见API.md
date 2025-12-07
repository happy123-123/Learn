详细去查API帮助文档

一、常见 API

1. 核心基础 API

• String 类：不可变字符序列，常用方法：equals()（内容比较）、length()（长度）、substring(int beginIndex, int endIndex)（截取子串）、split(String regex)（分割字符串）、trim()（去除首尾空格）。

• StringBuilder/StringBuffer：可变字符序列，append()（拼接）、reverse()（反转）、replace()（替换）；StringBuffer 线程安全（效率低），StringBuilder 非线程安全（效率高）。

• 包装类：8种基本类型对应包装类（如 int→Integer、boolean→Boolean），核心方法：valueOf(基本类型)（装箱）、xxxValue()（拆箱，如 intValue()）、parseXxx(String s)（字符串转基本类型，如 Integer.parseInt("123")）。

• Date/DateFormat：Date 表示时间戳，DateFormat（抽象类）及子类 SimpleDateFormat 用于时间格式化，format(Date d)（日期转字符串）、parse(String s)（字符串转日期，需处理 ParseException）。

2. 工具类

• Math 类：静态方法，abs()（绝对值）、max(a,b)（最大值）、min(a,b)（最小值）、random()（生成 0.0~1.0 随机数）、round(double d)（四舍五入）。

• Arrays 类：操作数组的工具，sort(数组)（排序）、toString(数组)（转为字符串）、copyOf(原数组, 新长度)（复制数组）、binarySearch(数组, 目标值)（二分查找，需先排序）。