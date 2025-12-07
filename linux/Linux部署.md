### 后端项目部署学习笔记

1. **项目打包**
- 使用 Maven 的父工程执行 `package` 生命周期，对项目进行打包
- 打包前需确保已连接服务器数据库，并完成测试验证

2. **上传 JAR 包**
- 在 Linux 服务器的 `/usr/local` 目录下创建名为 `tlias-app` 的目录
- 将打包好的 JAR 文件上传至 `/usr/local/tlias-app` 目录中

3. **运行 JAR 包**
- 使用命令 `java -jar xxxxx.jar` 启动服务
- 注意：该命令会占用前台窗口，关闭窗口会导致服务停止

4. **后台运行服务**  
   使用 `nohup` 指令实现后台运行：
      ```bash
      nohup java -jar xxxxx.jar &> tlias.log &
      ```
   此命令将输出重定向到 `tlias.log` 文件，并在后台持续运行


5. **查看进程状态**  
   使用以下命令查看服务是否正常运行：
      ```bash
      ps -ef | grep xxxx
      ```


6. **Linux 特殊符号说明**  
   `|`：管道符，用于将前一个命令的输出作为后一个命令的输入，例如：
      ```bash
      ps -ef | grep java
      ```

   `>` 和 `>>`：重定向符号  
   `>`：覆盖重定向，将内容写入文件并覆盖原有内容  
   `>>`：追加重定向，将内容追加到文件末尾  
      示例：
          ```bash
          echo 'Hello Linux' > 1.log
          ```


---

### 前端项目部署学习笔记

1. **静态资源上传**
- 将前端打包后的静态资源（位于“资料\06.项目部署\前端\页面资源”文件夹）上传至 Nginx 的 `html` 目录中

2. **配置 Nginx**  
   编辑 Nginx 配置文件 `conf/nginx.conf`，设置反向代理和路径重写规则：
      ```nginx
      server {
          listen       80;
          server_name  localhost;
          client_max_body_size 10m;
 
          location / {
              root   html;
              index  index.html index.htm;
              try_files $uri $uri/ /index.html;
          }
 
          location ^~ /api/ {
              rewrite ^/api/(.*)$ /$1 break;
              proxy_pass http://localhost:8080;
          }
      }
      ```

   关键配置说明：  
- `location /`：处理静态资源请求，优先匹配文件，若不存在则返回 `index.html`


- `location ^~ /api/`：拦截所有以 `/api/` 开头的请求，通过 `proxy_pass` 转发至后端服务（端口 8080）


- `rewrite`：去除 `/api/` 前缀，确保请求正确转发

3. **启动 Nginx 服务**  
   在 Nginx 安装目录的 `sbin` 子目录下执行以下命令之一：
      ```bash
      sbin/nginx
      ```

      或
      ```bash
      sbin/nginx -s reload
      ```

   第一条命令用于启动 Nginx 服务，第二条命令用于重新加载配置而不中断服务

---

### 总结
- 后端部署重点在于 JAR 包的打包、上传与后台运行，同时掌握 Linux 常用命令和符号的使用
- 前端部署依赖 Nginx 实现静态资源托管和 API 请求代理，合理配置 `nginx.conf` 是关键
- 整体部署流程需确保前后端通信顺畅，且服务稳定运行