# 在线问答平台 - demo7

## 📋 项目概述
基于 Spring Boot 3 + Thymeleaf + H2 Database 构建的现代化在线问答平台，实现用户注册登录、发帖讨论、实时回复等功能，采用IOC控制反转和MVC分层架构设计。

## 🚀 技术栈
| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 3.2.0 | 核心框架 |
| Java | 21 | 开发语言 |
| Thymeleaf | 3.1.2 | 模板引擎 |
| H2 Database | 2.2.224 | 内存数据库 |
| Spring Data JPA | 3.2.0 | 数据持久层 |
| Lombok | 1.18.30 | 简化代码 |
| Maven | 3.9+ | 项目构建 |

## ✨ 功能特性
### 🔐 用户系统
- ✅ 用户注册（用户名唯一性校验）
- ✅ 图形验证码登录防刷
- ✅ Session会话管理
- ✅ 安全退出

### 💬 讨论系统
- ✅ 发起新讨论（标题+内容）
- ✅ 讨论列表（按时间倒序排序）
- ✅ 查看讨论详情
- ✅ 实时回复功能
- ✅ 回复列表展示

### 🛡️ 安全特性
- ✅ 前端表单验证
- ✅ 后端参数校验
- ✅ 验证码防机器人
- ✅ 会话超时管理

## 📁 项目结构
```
demo7/
├── src/main/java/com/example/demo7/
│   ├── Demo7Application.java          # Spring Boot启动类
│   ├── config/
│   │   └── DataInitializer.java       # 数据初始化
│   ├── controller/                    # MVC控制器层
│   │   ├── CaptchaController.java     # 验证码生成
│   │   ├── DiscussionController.java  # 讨论管理
│   │   ├── HomeController.java        # 首页重定向
│   │   ├── LoginController.java       # 登录处理
│   │   ├── LogoutController.java      # 退出登录
│   │   ├── RegisterController.java    # 用户注册
│   │   └── ReplyController.java       # 回复管理
│   ├── model/                         # 实体类（IOC Beans）
│   │   ├── Discussion.java
│   │   ├── Reply.java
│   │   └── User.java
│   ├── repository/                    # Spring Data JPA仓库
│   │   ├── DiscussionRepository.java
│   │   ├── ReplyRepository.java
│   │   └── UserRepository.java
│   ├── service/                       # 业务逻辑层
│   │   ├── CaptchaService.java
│   │   ├── DiscussionService.java
│   │   └── UserService.java
│   └── util/                          # 工具类
│       ├── GlobalExceptionHandler.java
│       └── UserValidator.java
├── src/main/resources/
│   ├── application.properties          # Spring配置
│   ├── static/                         # 静态资源
│   │   ├── css/
│   │   │   ├── forum.css              # 论坛样式
│   │   │   └── login.css              # 登录注册样式
│   │   └── js/
│   │       ├── login.js               # 登录页面脚本
│   │       └── register.js            # 注册页面脚本
│   └── templates/                      # Thymeleaf模板
│       ├── fragments/
│       │   └── discussionListFragment.html
│       ├── discussionDetail.html
│       ├── discussionList.html
│       ├── login.html
│       ├── newDiscussion.html
│       ├── register.html
│       └── success.html
└── pom.xml                             # Maven依赖配置
```

## ⚙️ 快速开始

### 前提条件
- JDK 21 或更高版本
- Maven 3.9+
- IntelliJ IDEA 或 Eclipse IDE

### 运行步骤
1. **克隆或下载项目**
   ```bash
   git clone <repository-url>
   cd demo7
   ```

2. **配置Maven（解决中文路径问题）**
   - 运行 `setup-project.bat` 创建无中文路径的Maven仓库
   - 或手动创建：`D:\maven-repo`

3. **在IDEA中打开项目**
   - File → Open → 选择项目目录
   - 等待Maven依赖下载完成

4. **配置IDEA设置**
   ```
   Settings → Build, Execution, Deployment → Maven
   User settings file: D:\Javaweb\demo7\settings.xml
   Local repository: D:\maven-repo
   ```

5. **运行项目**
   - 方式1：运行 `Demo7Application.java`
   - 方式2：命令行运行 `mvn spring-boot:run -Dmaven.repo.local=D:\maven-repo`
   - 方式3：运行 `run.bat`

### 🌐 访问地址
**主访问地址**：http://10.100.164.18:8081/

**详细页面**：
- **应用首页**：http://10.100.164.18:8081/
- **登录页面**：http://10.100.164.18:8081/login
- **注册页面**：http://10.100.164.18:8081/register
- **讨论列表**：http://10.100.164.18:8081/discussions
- **H2控制台**：http://10.100.164.18:8081/h2-console

### 默认账户
| 用户名 | 密码 | 邮箱 | 角色 |
|--------|------|------|------|
| admin  | 123456 | admin@example.com | 管理员 |
| user1  | 123456 | user1@example.com | 普通用户 |
| user2  | 123456 | user2@example.com | 普通用户 |

## 🗄️ 数据库配置
```properties
# H2内存数据库（应用重启数据清空）
spring.datasource.url=jdbc:h2:mem:discussiondb
spring.datasource.username=sa
spring.datasource.password=
```

### H2控制台访问
1. 访问：http://10.100.164.18:8081/h2-console
2. 连接设置：
   - JDBC URL: `jdbc:h2:mem:discussiondb`
   - User Name: `sa`
   - Password: (空)

## 🔧 配置文件
### `application.properties`
```properties
# 服务器配置（端口改为8081）
server.port=8081

# 数据库配置
spring.datasource.url=jdbc:h2:mem:discussiondb
spring.datasource.driver-class-name=org.h2.Driver
spring.h2.console.enabled=true

# JPA配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Thymeleaf配置
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
```

## 🎯 核心实现

### IOC控制反转
```java
@Service
public class UserService {
    @Autowired  // Spring自动注入
    private UserRepository userRepository;
    // ...
}
```

### MVC分层架构
```
浏览器请求 → Controller → Service → Repository → H2数据库
        ↑                                       ↓
        ←────────────── Thymeleaf渲染 ←──────────────
```

### 实时局部刷新
```javascript
// 讨论列表每10秒自动刷新
setInterval(() => {
  fetch('/discussions/list-fragment')
    .then(r => r.text())
    .then(html => {
      document.getElementById('listContainer').innerHTML = html;
    });
}, 10000);
```

## 📊 数据模型
### User（用户）
```java
@Entity
@Table(name = "users")
public class User {
    private Long id;
    private String username;    // 用户名
    private String password;    // 密码
    private String email;       // 邮箱
    private LocalDateTime registerTime; // 注册时间
}
```

### Discussion（讨论）
```java
@Entity
@Table(name = "discussions")
public class Discussion {
    private Long id;
    private String title;       // 标题
    private String content;     // 内容
    private User author;        // 作者
    private LocalDateTime createTime; // 创建时间
    private List<Reply> replies; // 回复列表
}
```

### Reply（回复）
```java
@Entity
@Table(name = "replies")
public class Reply {
    private Long id;
    private String content;     # 回复内容
    private User author;        # 回复者
    private Discussion discussion; # 所属讨论
    private LocalDateTime createTime; # 回复时间
}
```

## 🚨 常见问题

### Q1：Maven本地仓库路径问题（中文用户名）
**解决方案**：
1. 运行项目根目录的 `setup-project.bat`
2. 或手动设置：`-Dmaven.repo.local=D:\maven-repo`
3. 或修改IDEA的Maven设置

### Q2：无法访问 http://10.100.164.18:8081/
**检查清单**：
1. 应用是否成功启动（查看控制台日志）
2. 服务器防火墙是否允许8081端口访问
3. 是否在同一网络环境下
4. 确认IP地址是否正确

### Q3：无法访问页面（404错误）
**检查清单**：
1. 应用是否成功启动（查看控制台日志）
2. 模板文件是否存在于 `templates/` 目录
3. Controller是否有正确的 `@RequestMapping` 注解

### Q4：数据库连接失败
**解决方案**：
1. 检查H2依赖是否已添加
2. 确认JDBC URL是否正确
3. 查看 `spring.jpa.show-sql` 日志

### Q5：端口冲突或无法访问
**解决方案**：
1. 检查8081端口是否被占用
2. 修改端口号：在 `application.properties` 中修改 `server.port`
3. 确保服务器防火墙允许相应端口访问

## 📝 开发指南

### 添加新功能
1. 在 `model/` 中定义实体类
2. 在 `repository/` 中创建Repository接口
3. 在 `service/` 中实现业务逻辑
4. 在 `controller/` 中处理HTTP请求
5. 在 `templates/` 中创建视图模板

### 代码规范
- 使用Lombok注解简化代码
- 遵循Spring Boot命名约定
- 使用 `@Transactional` 管理事务
- 异常处理使用 `GlobalExceptionHandler`

### 测试建议
1. 使用默认账户登录测试基本功能
2. 验证表单校验是否正确
3. 测试验证码功能
4. 验证讨论和回复的CRUD操作

## 🔄 部署选项

### 本地运行
```bash
mvn clean package
java -jar target/demo7-1.0.0.jar --server.port=8081
```

### 服务器部署
```bash
# 1. 上传JAR文件到服务器
scp target/demo7-1.0.0.jar user@10.100.164.18:/opt/demo7/

# 2. 在服务器上运行
cd /opt/demo7
java -jar demo7-1.0.0.jar --server.port=8081

# 3. 后台运行（生产环境）
nohup java -jar demo7-1.0.0.jar --server.port=8081 > app.log 2>&1 &
```

### Docker部署
```bash
# 构建镜像
docker build -t demo7-app .

# 运行容器（映射到8081端口）
docker run -p 8081:8081 -d demo7-app
```

### 生产环境建议
1. 更换H2为MySQL或PostgreSQL
2. 配置Spring Security增强安全性
3. 添加日志记录和监控
4. 配置HTTPS加密传输

## 🌍 网络访问说明
- **内网访问**：http://10.100.164.18:8081/
- **如果需要外网访问**，请配置NAT转发或云服务器公网IP
- **端口开放**：确保服务器防火墙已开放8081端口



**最后更新**：2025年12月30日  
**版本**：1.0.0  
**访问地址**：http://10.100.164.18:8081/  
**状态**：✅ 运行正常
