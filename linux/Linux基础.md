# Linux 常用命令

## `mkdir` 命令

- **作用**：创建目录
- **语法**：`mkdir [-p] dirName`
- **选项说明**：  
    - `-p`：确保目录名称存在，若不存在则自动创建。支持多层目录同时创建
- **示例**：  
    - `mkdir itcast`：在当前目录下创建名为 `itcast` 的子目录  
    - `mkdir -p itcast/test`：在 `itcast` 目录中创建 `test` 子目录；若 `itcast` 不存在，则先创建

---

## `rm` 命令

- **作用**：删除文件或目录
- **语法**：`rm [-rf] name`
- **选项说明**：  
    - `-r`：递归删除目录及其所有内容（包括子目录和文件）  
    - `-f`：强制删除，无需确认提示
- **示例**：  
    - `rm -r itcast/`：删除 `itcast` 目录及其中所有文件，删除前需确认  
    - `rm -rf itcast/`：无需确认，直接删除 `itcast` 目录及其所有内容  
    - `rm -f hello.txt`：无需确认，直接删除 `hello.txt` 文件

> ⚠️ 使用 `-rf` 时需谨慎，因为该操作不可逆且无提示。

---

## `ls` 命令

- **作用**：显示指定目录下的内容
- **语法**：`ls [-al] [dir]`
- **选项说明**：  
    - `-a`：显示所有文件和目录，包括以`.`开头的隐藏文件  
    - `-l`：以详细列表形式显示，包含文件类型、权限、所有者、大小、修改时间等信息
- **组合使用**：  
    `ls -al`：显示当前目录下所有文件（含隐藏）的详细信息

---

## `cd` 命令

- **作用**：切换当前工作目录
- **语法**：`cd [dirName]`
- **特殊符号说明**：  
    - `.`：表示当前所在目录  
    - `..`：表示上级目录  
    - `~`：表示用户的 home 目录
- **示例**：  
    - `cd ..`：切换到当前目录的上级目录  
    - `cd ~`：切换到用户的 home 目录  
    - `cd /usr/local`：切换到 `/usr/local` 目录  
    - `cd -`：切换到上一次所在目录

---
# Linux 文件操作命令 
## `cat` 命令

- **作用**：用于显示文件的所有内容
- **语法**：`cat [-n] fileName`
- **选项说明**：
    - `-n`：从1开始对所有输出的行进行编号
- **示例**：
    - `cat /etc/profile`：查看 `/etc` 目录下的 `profile` 文件内容


---

## `more` 命令

- **作用**：以分页的形式显示文件内容
- **语法**：`more fileName`
- **操作说明**：
    - 回车键：向下滚动一行
    - 空格键：向下滚动一屏
    - `b`：返回上一屏
    - `q` 或 `Ctrl+C`：退出 `more`
- **示例**：
    - `more /etc/profile`：以分页方式显示 `/etc` 目录下的 `profile` 文件内容


---

## `head` 命令

- **作用**：查看文件开头的内容
- **语法**：`head [-n] fileName`
- **选项说明**：
    - `-n`：输出文件开头的 `n` 行内容
- **示例**：
    - `head 1.log`：默认显示 `1.log` 文件开头的10行内容
    - `head -20 1.log`：显示 `1.log` 文件开头的20行内容

---

## `tail` 命令

- **作用**：查看文件末尾的内容
- **语法**：`tail [-nf] fileName`
- **选项说明**：
    - `-n`：输出文件末尾的 `n` 行内容
    - `-f`：动态读取文件末尾内容并实时刷新，常用于监控日志文件
- **示例**：
    - `tail 1.log`：默认显示 `1.log` 文件末尾10行的内容
    - `tail -20 1.log`：显示 `1.log` 文件末尾20行的内容
    - `tail -f 1.log`：动态读取 `1.log` 文件末尾内容并实时刷新

---

# Linux 拷贝移动命令

## `cp` 命令

- **作用**：用于复制文件或目录
- **语法**：`cp [-r] source dest`
- **选项说明**：
    - `-r`：递归复制，适用于目录，会复制该目录下的所有子目录和文件
- **示例**：
    - `cp hello.txt itcast/`：将 `hello.txt` 复制到 `itcast` 目录中
    - `cp hello.txt ./hi.txt`：将 `hello.txt` 复制到当前目录，并重命名为 `hi.txt`
    - `cp -r itcast/ ./itheima/`：将 `itcast` 目录及其所有内容复制到 `itheima` 目录下
    - `cp -r itcast/* ./itheima/`：将 `itcast` 目录下所有文件复制到 `itheima` 目录下

---

## `mv` 命令

- **作用**：为文件或目录重命名，或将文件或目录移动到其他位置
    - 如果第二个参数是已存在的目录，则执行移动操作
- **语法**：`mv source dest`
- **示例**：
    - `mv hello.txt hi.txt`：将 `hello.txt` 重命名为 `hi.txt`
    - `mv hi.txt itheima/`：将 `hi.txt` 移动到 `itheima` 目录中
    - `mv hi.txt itheima/hello.txt`：将 `hi.txt` 移动到 `itheima` 目录中，并重命名为 `hello.txt`
    - `mv itcast/ itheima/`：如果 `itheima` 目录不存在，则将 `itcast` 目录重命名为 `itheima`
    - `mv itcast/ itheima/`：如果 `itheima` 目录存在，则将 `itcast` 目录移动到 `itheima` 目录中

---

# Linux `tar` 打包压缩命令

## 作用
- 对文件进行打包、解包、压缩、解压

## 语法

```bash
    tar [-zcxvf] fileName [files]
```


## 选项说明
- `-z`：使用 gzip 进行压缩或解压，对应 `.tar.gz` 文件格式
- `-c`：create，创建新的包文件
- `-x`：extract，从包文件中提取文件
- `-v`：verbose，显示命令执行过程中的详细信息
- `-f`：file，指定包文件的名称

## 文件后缀含义
- `.tar`：仅打包，未压缩
- `.tar.gz`：打包并压缩

## 打包操作
- `tar -cyf hello.tar hello`：将当前目录下所有文件打包为 `hello.tar`
- `tar -zcvf hello.tar.gz hello`：将当前目录下所有文件打包并压缩为 `hello.tar.gz`

## 解包操作
- `tar -xvf hello.tar`：解压 `hello.tar` 文件到当前目录
- `tar -zxvf hello.tar.gz`：解压 `hello.tar.gz` 文件到当前目录
- `tar -zxvf hello.tar.gz -C /usr/local`：解压 `hello.tar.gz` 文件到指定目录 `/usr/local`

---

# Linux `vi/vim` 编辑器

## vi/vim 概述

- **作用**：`vi` 是 Linux 系统提供的文本编辑工具，类似于 Windows 中的记事本
- **语法**：`vi fileName`
- **说明**：
    - `vim` 是从 `vi` 发展而来的功能更强大的文本编辑器，支持语法高亮（更常用）
    - 使用 `vim` 命令前需手动安装：`yum install vim`

---

## vim 编辑器模式

### 模式结构图

```
    vim filename
         ↓
      命令模式
    / /     \  \
 ESC i,a,o   :  ESC         
 /  /         \  \
插入模式      底行模式
```


### 各模式说明

#### 命令模式（Command Mode）
- 默认进入模式
- 可执行移动、删除、复制等操作
- 进入方式：按 `ESC` 键

##### 常用命令
| 命令          | 含义 |
|-------------|------|
| `gg`        | 定位到文件第一行 |
| `G`         | 定位到文件最后一行 |
| `dd`        | 删除光标所在行 |
| `n.dd`      | 删除当前行及之后的 n 行 |
| `u`         | 撤销上一步操作 |
| `i`,`a`,`o` | 进入插入模式 |

#### 插入模式（Insert Mode）
- 在该模式下可输入文本
- 进入方式：在命令模式下按 `i`,`a`, 或 `o`
- 退出方式：按 `ESC` 返回命令模式

#### 底行模式（Ex Mode）
- 用于执行保存、退出、搜索等命令
- 进入方式：在命令模式下按 `:`

##### 常用命令
| 命令 | 含义 |
|------|------|
| `:wq` | 保存并退出 |
| `:q!` | 不保存退出 |
| `:set nu` | 显示行号 |
| `:set nonu` | 取消行号显示 |
| `:n` | 定位到第 n 行（如 `:10` 定位到第 10 行） |

---

# Linux `find`和 `grep` 查找命令

## `find`命令

- **作用**：在指定目录下查找文件
- **语法**：`find dirName -option fileName`
- **示例**：
    - `find . -name "*.log"`：在当前目录及其子目录中查找以 `.log` 结尾的文件
    - `find /itcast -name "*.log"`：在 `/itcast` 目录及其子目录中查找以 `.log` 结尾的文件

---

## `grep` 命令

- **作用**：从指定文件中查找指定的文本内容
- **语法**：`grep [-inAB] word fileName`
- **选项说明**：
    - `-i`：忽略大小写进行搜索（ignore）
    - `-n`：显示匹配行的行号
    - `-A`：输出关键字所在行及之后的若干行（After），如 `-A5` 表示输出后5行
    - `-B`：输出关键字所在行及之前的若干行（Before），如 `-B5` 表示输出前5行
- **示例**：
    - `grep Hello HelloWorld.java`：查找 `HelloWorld.java` 文件中出现的 `Hello` 字符串位置
    - `grep hello *.java`：查找当前目录中所有 `.java`文件中包含 `hello` 字符串的位置

___

# Linux 防火墙操作

## 防火墙管理命令

### 查看防火墙状态
- `systemctl status firewalld`：查看防火墙服务状态
- `firewall-cmd --state`：查看防火墙运行状态

### 启动与停止防火墙
- `systemctl stop firewalld`：关闭防火墙服务
- `systemctl start firewalld`：开启防火墙服务
- `systemctl disable firewalld`：永久关闭防火墙服务

### 端口管理
- **开放指定端口**：
  ```bash
  firewall-cmd --zone=public --add-port=8080/tcp --permanent
  ```

- **关闭指定端口**：
  ```bash
  firewall-cmd --zone=public --remove-port=8080/tcp --permanent
  ```

- **立即生效**：
  ```bash
  firewall-cmd --reload
  ```

- **查看开放的端口**：
  ```bash
  firewall-cmd --zone=public --list-ports
  ```

## 注意事项
1. `systemctl` 是 Linux 中用于管理服务的命令，支持启动、停止、重启、查看状态等操作
2. `firewall-cmd` 是 Linux 中专门用于控制防火墙的命令
3. 为了保证系统安全，生产服务器的防火墙不建议关闭
