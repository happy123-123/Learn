Stream流

1. 作用

简化集合/数组的遍历、过滤、映射等操作，链式调用更简洁

2. 常用方法

• 中间操作：filter()（过滤）、map()（映射）、sorted()（排序）、distinct()（去重）

• 终止操作：forEach()（遍历）、collect()（转集合，如Collectors.toList()）、count()（计数）、max()/min()（最值）

示例

    List<Integer> list = Arrays.asList(1,2,3,4,5);
    list.stream()
        .filter(n -> n%2==0) // 过滤偶数
        .map(n -> n*2)       // 每个数乘2
        .forEach(System.out::println); // 输出4、8