# Spring Boot Redis 示例项目

这是一个基于Spring Boot 3.1.0和Redis的示例项目，演示了如何在Spring Boot应用中使用Redis进行数据存储和检索。

## 项目功能

1. 用户信息的Redis存储和检索
2. 使用Redis Hash结构存储用户名
3. 提供RESTful API接口进行用户数据操作

## 技术栈

- Spring Boot 3.1.0
- Java 21
- Redis
- Maven
- Lombok

## 项目结构

```
src/main/java/com/example/redis/
├── SpringbootRedisExampleApplication.java  # 应用启动类
├── config/
│   └── RedisConfig.java                    # Redis配置类
├── controller/
│   └── UserController.java                 # 用户控制器，提供API接口
├── entity/
│   └── User.java                           # 用户实体类
├── service/
│   └── UserService.java                    # 用户服务类
└── util/
    └── RedisUtil.java                      # Redis工具类，封装常用操作
```

## API接口

### 保存用户到Redis
- **URL**: `POST /api/user/save`
- **参数**: User对象（JSON格式）
- **示例**:
```json
{
  "id": 1,
  "username": "张三",
  "age": 25,
  "email": "zhangsan@example.com"
}
```

### 从Redis获取用户
- **URL**: `GET /api/user/get/{userId}`
- **参数**: userId（路径变量）

### 从Redis删除用户
- **URL**: `DELETE /api/user/delete/{userId}`
- **参数**: userId（路径变量）

### 保存用户名到Redis Hash
- **URL**: `POST /api/user/hash/save`
- **参数**: userId（请求参数）, userName（请求参数）

### 从Redis Hash获取用户名
- **URL**: `GET /api/user/hash/get/{userId}`
- **参数**: userId（路径变量）

## 配置

项目使用`application.yml`进行配置，主要配置项包括：

- Redis服务器地址和端口
- Redis数据库索引
- 连接池配置
- 应用端口（默认8080）

## 运行项目

1. 确保Redis服务器已启动
2. 使用Maven构建项目：
   ```
   mvn clean package
   ```
3. 运行项目：
   ```
   java -jar target/springboot-redis-example-0.0.1-SNAPSHOT.jar
   ```
4. 访问`http://localhost:8080`查看应用

## 依赖

- spring-boot-starter-web：Spring Boot Web依赖
- spring-boot-starter-data-redis：Spring Boot Redis依赖
- commons-pool2：连接池依赖
- jakarta.annotation-api：Jakarta注解API
- lombok：简化代码的工具库