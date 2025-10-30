# HIS电子发票系统

## 📋 项目简介

这是一个医院信息系统（HIS）的电子发票管理后端服务，基于 **Spring Boot 2.7.14** 开发，提供发票查询、详情查看、邮件发送和PDF下载等功能。

---

## 🎯 核心功能

### 1. 发票列表查询
- **接口路径**：`/api/invoice/list`
- **请求方式**：GET 或 POST
- **功能**：根据条件查询用户的电子发票列表

### 2. 发票详情查询
- **接口路径**：`/api/invoice/detail`
- **请求方式**：GET 或 POST
- **功能**：查询发票的完整详细信息

### 3. 发票发送邮箱
- **接口路径**：`/api/invoice/sendEmail`
- **请求方式**：POST
- **功能**：将电子发票PDF发送到指定邮箱

### 4. 发票下载
- **接口路径**：`/api/invoice/download`
- **请求方式**：GET
- **功能**：下载电子发票PDF文件

---

## 🏗️ 项目结构

```
src/main/java/com/hospital/invoice/
├── common/                      # 公共类
│   └── ApiResponse.java         # 统一API响应格式类
├── controller/                  # 控制器层（处理HTTP请求）
│   └── InvoiceController.java   # 发票控制器
├── dto/                         # 数据传输对象
│   ├── request/                 # 请求参数类
│   │   ├── InvoiceListRequest.java      # 发票列表查询请求
│   │   ├── InvoiceDetailRequest.java    # 发票详情查询请求
│   │   ├── SendEmailRequest.java        # 发送邮件请求
│   │   └── InvoiceDownloadRequest.java  # 下载发票请求
│   └── response/                # 响应数据类
│       ├── InvoiceListVO.java           # 发票列表响应
│       ├── InvoiceDetailVO.java         # 发票详情响应
│       └── SendEmailVO.java             # 邮件发送响应
├── entity/                      # 实体类（对应数据库表/视图）
│   ├── Invoice.java             # 发票实体
│   └── view/                    # 数据库视图实体
│       ├── InvoiceDetailView.java       # 发票详情视图
│       ├── InvoiceListView.java         # 发票列表视图
│       └── InvoiceListViewSimple.java   # 发票列表简化视图
├── enums/                       # 枚举类
│   ├── InvoiceType.java         # 票据类型枚举
│   └── InvoiceStatus.java       # 发票状态枚举
├── repository/                  # 数据访问层
│   ├── InvoiceDetailViewRepository.java
│   ├── InvoiceListViewRepository.java
│   └── InvoiceListViewSimpleRepository.java
├── service/                     # 服务层（业务逻辑）
│   ├── InvoiceService.java      # 发票服务接口
│   └── impl/
│       └── InvoiceServiceImpl.java  # 发票服务实现类
├── config/                      # 配置类
│   └── WebConfig.java           # Web配置（CORS等）
└── InvoiceApplication.java      # 应用启动类
```

---

## 🔧 技术栈

- **开发语言**：Java 8
- **框架**：Spring Boot 2.7.14
- **构建工具**：Maven
- **数据库**：Oracle 11g（连接：172.16.1.9:1521/pqrac）
- **数据来源**：数据库视图（COMM.INVOICE_LIST_VIEW、COMM.INVOICE_DETAIL_VIEW）
- **ORM框架**：Spring Data JPA / Hibernate
- **PDF处理**：Apache PDFBox（预留）
- **邮件发送**：Spring Boot Mail（预留）

---

## 🚀 快速开始

### 前置条件

- ✅ **Java JDK 1.8** 或更高版本
- ✅ **Maven 3.6** 或更高版本
- ✅ **Oracle 11g 数据库**访问权限

### 安装Maven（必需）

**检查Maven是否已安装**：
```bash
mvn -version
```

如果未安装，请按以下步骤安装：

**方式1：下载安装（推荐）**
1. 访问：https://maven.apache.org/download.cgi
2. 下载 `apache-maven-3.9.x-bin.zip`
3. 解压到 `C:\Program Files\apache-maven`
4. 添加环境变量：
   - 新建系统变量：`MAVEN_HOME = C:\Program Files\apache-maven`
   - 编辑 `Path`，添加：`%MAVEN_HOME%\bin`
5. 重启命令提示符，验证：`mvn -version`

**方式2：使用Chocolatey（Windows包管理器）**
```bash
choco install maven
```

**配置Maven国内镜像（可选，加速下载）**：
编辑 `C:\Users\你的用户名\.m2\settings.xml`（如不存在则创建）：
```xml
<settings>
  <mirrors>
    <mirror>
      <id>aliyun</id>
      <mirrorOf>central</mirrorOf>
      <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>
</settings>
```

### 1. 配置数据库连接

编辑 `src/main/resources/application.properties`：

```properties
# Oracle数据库连接配置
spring.datasource.url=jdbc:oracle:thin:@172.16.1.9:1521:pqrac
spring.datasource.username=comm
spring.datasource.password=您的数据库密码
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA配置
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect

# 字符编码配置
spring.jpa.properties.hibernate.connection.characterEncoding=UTF-8
spring.jpa.properties.hibernate.connection.useUnicode=true
```

**重要**：
- 请将 `您的数据库密码` 替换为实际密码
- `ddl-auto=none` 表示不自动创建表（使用数据库视图）

### 2. 启动项目

**方式1：使用IDE（推荐）**
1. 用 IntelliJ IDEA 打开项目
2. 找到 `InvoiceApplication.java`
3. 右键 → Run

**方式2：使用Maven命令**
```bash
# 编译项目
mvn clean package -DskipTests

# 运行项目
mvn spring-boot:run

# 或直接运行jar包
java -jar target/invoice-system-1.0.0.jar
```

**方式3：使用Windows批处理**
```bash
# 双击运行
simple-start.cmd
```

### 3. 验证启动成功

看到以下信息说明启动成功：
```
========================================
  HIS电子发票系统启动成功！
  访问地址：http://localhost:8080
  接口文档见：README.md
========================================
```

---

## 📡 接口测试

### 重要说明：API网关参数

**所有接口都需要携带以下三个必需参数**（用于API网关身份验证）：

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| hospitalId | String | 是 | 医院ID，标识请求来源的医院 |
| hospitalName | String | 是 | 医院名称 |
| tickets | String | 是 | API网关身份验证票据 |

### 使用 Postman 测试

#### 1. 查询发票列表

**请求**：
```
POST http://localhost:8080/api/invoice/list
Content-Type: application/json

{
  "hospitalId": "H001",
  "hospitalName": "北京协和医院",
  "tickets": "your_api_gateway_ticket_here",
  "patientId": "123456",
  "invoiceType": "OUTPATIENT",
  "startTime": "2024-01-01",
  "endTime": "2024-12-31",
  "pageNum": 1,
  "pageSize": 10
}
```

**响应**：
```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "invoiceId": "INV20240115001",
      "invoiceNo": "00123456",
      "invoiceType": "OUTPATIENT",
      "invoiceTypeLabel": "门诊",
      "amount": "128.50",
      "createTime": "2024-01-15 10:30:00",
      "displayTime": "2024-01-15 10:30",
      "status": "ISSUED",
      "statusLabel": "已开票",
      "patientName": "张三",
      "phoneNumber": "138****8000"
    }
  ]
}
```

#### 2. 查询发票详情

**请求**：
```
POST http://localhost:8080/api/invoice/detail
Content-Type: application/json

{
  "hospitalId": "H001",
  "hospitalName": "北京协和医院",
  "tickets": "your_api_gateway_ticket_here",
  "invoiceId": "INV20240115001",
  "patientId": "123456"
}
```

#### 3. 发送邮件

**请求**：
```
POST http://localhost:8080/api/invoice/sendEmail
Content-Type: application/json

{
  "hospitalId": "H001",
  "hospitalName": "北京协和医院",
  "tickets": "your_api_gateway_ticket_here",
  "invoiceId": "INV20240115001",
  "patientId": "123456",
  "email": "patient@example.com"
}
```

#### 4. 下载PDF

**请求**：
```
GET http://localhost:8080/api/invoice/download?invoiceId=INV20240115001&patientId=123456&hospitalId=H001&hospitalName=北京协和医院&tickets=your_api_gateway_ticket_here
```

**注意**：下载接口使用GET请求，所有参数（包括网关参数）都通过URL查询参数传递。

---

## 🗄️ 数据库配置说明

### Oracle视图结构

本系统使用两个Oracle视图作为数据源：

#### 1. COMM.INVOICE_LIST_VIEW - 发票列表视图
用于展示发票列表，已进行数据脱敏处理。

**主要字段**：
- INVOICE_ID：发票ID（主键）
- INVOICE_NO：发票号码
- INVOICE_TYPE：票据类型（OUTPATIENT/INPATIENT/EXAMINATION）
- AMOUNT：发票金额
- CREATE_TIME：开票时间
- STATUS：发票状态（ISSUED/PENDING/CANCELLED）
- PATIENT_ID：患者ID
- PATIENT_NAME：患者姓名
- PHONE_NUMBER：手机号（已脱敏）

#### 2. COMM.INVOICE_DETAIL_VIEW - 发票详情视图
用于展示发票完整详情，包含所有字段信息。

### 连接Oracle数据库

**连接URL格式**：
```properties
# 方式1：SID连接（当前使用）
jdbc:oracle:thin:@172.16.1.9:1521:pqrac

# 方式2：Service Name连接
jdbc:oracle:thin:@172.16.1.9:1521/pqrac

# 方式3：TNS完整描述符
jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=172.16.1.9)(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=pqrac)))
```

### Oracle字符编码问题解决

如果遇到中文乱码，在连接URL添加参数：
```properties
spring.datasource.url=jdbc:oracle:thin:@172.16.1.9:1521:pqrac?oracle.jdbc.defaultNChar=true
```

或在SQL查询中使用：
```java
// 使用getBytes方法转换编码
String name = new String(rs.getBytes("PATIENT_NAME"), "GBK");
```

---

## 📊 枚举值说明

### 票据类型（InvoiceType）
- `OUTPATIENT` - 门诊
- `INPATIENT` - 住院
- `EXAMINATION` - 体检

### 发票状态（InvoiceStatus）
- `ISSUED` - 已开票
- `PENDING` - 待开票
- `CANCELLED` - 已作废

---

## 🔐 安全说明

### 1. API网关身份验证（新增）
所有接口都必须携带以下三个参数进行网关验证：
- **hospitalId**：医院唯一标识，用于标识请求来源
- **hospitalName**：医院名称，用于审计和日志记录
- **tickets**：API网关颁发的身份验证票据，用于验证请求合法性

**验证流程**：
```
1. 客户端向API网关申请tickets（需要提供医院凭证）
2. 网关验证医院身份后颁发tickets（有时效性）
3. 客户端携带tickets调用业务接口
4. 业务接口验证tickets的合法性和有效期
5. 验证通过后处理业务逻辑
```

**注意事项**：
- tickets有时效性，过期后需重新申请
- tickets与hospitalId绑定，不能跨医院使用
- 所有请求都会记录hospitalId用于审计追踪

### 2. 用户身份认证
所有接口都需要在请求头中携带Token：
```
Authorization: Bearer {your_token}
```

### 3. 权限校验
- 访问发票详情、下载等接口时，会校验患者ID是否匹配
- 防止用户查看他人的发票信息
- 验证医院是否有权访问该患者的发票数据

### 4. 数据脱敏
列表显示时进行数据脱敏：
- 手机号：`138****8000`（中间4位隐藏）
- 身份证号：`610***********0515`（中间部分隐藏）

---

## 🛠️ 项目特点

### 1. 标准分层架构
- **Controller层**：处理HTTP请求，参数校验
- **Service层**：业务逻辑处理
- **Repository层**：数据访问（JPA）
- **Entity层**：实体类定义
- **DTO层**：数据传输对象

### 2. 使用数据库视图
- 不直接查询原始表
- 视图中已处理好数据脱敏和字段映射
- 简化后端业务逻辑

### 3. 统一响应格式
所有接口返回统一格式：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

### 4. 完善的异常处理
- 参数校验异常
- 权限校验异常
- 业务异常
- 系统异常

---

## 📝 后续开发建议

### 1. PDF生成功能
使用 Apache PDFBox 或 iText 实现发票PDF生成：
```java
@Service
public class PdfService {
    public byte[] generateInvoicePdf(InvoiceDetailVO invoice) {
        // TODO: 实现PDF生成逻辑
    }
}
```

### 2. 邮件发送功能
配置Spring Mail实现邮件发送：
```properties
spring.mail.host=smtp.example.com
spring.mail.port=587
spring.mail.username=your_email@example.com
spring.mail.password=your_password
```

### 3. 添加接口文档
使用 Swagger 或 Knife4j 生成API文档：
```xml
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.3</version>
</dependency>
```

### 4. 添加日志记录
使用 SLF4J + Logback 记录系统日志：
```java
@Slf4j
@Service
public class InvoiceServiceImpl {
    public List<InvoiceListVO> getInvoiceList(InvoiceListRequest request) {
        log.info("查询发票列表，患者ID: {}", request.getPatientId());
        // ...
    }
}
```

### 5. 添加缓存
使用 Redis 缓存热点数据：
```java
@Cacheable(value = "invoice", key = "#invoiceId")
public InvoiceDetailVO getInvoiceDetail(String invoiceId) {
    // ...
}
```

### 6. 添加单元测试
使用 JUnit 和 Mockito 编写测试：
```java
@SpringBootTest
public class InvoiceServiceTest {
    @Test
    public void testGetInvoiceList() {
        // ...
    }
}
```

---

## ❓ 常见问题

### Q1: 启动时报错 "端口8080已被占用"
**解决方案**：
- 修改 `application.properties` 中的端口号：`server.port=8081`
- 或者关闭占用8080端口的程序

### Q2: 连接数据库失败
**检查清单**：
- [ ] 数据库服务是否启动
- [ ] IP地址和端口是否正确
- [ ] 用户名和密码是否正确
- [ ] 网络是否畅通（ping 172.16.1.9）

### Q3: 查询返回空数据
**可能原因**：
- 视图中没有数据
- 查询条件不匹配
- 患者ID不存在

### Q4: 中文乱码
**解决方案**：
- 检查数据库字符集
- 在连接URL添加编码参数
- 使用getBytes方法转换编码

### Q5: Maven依赖下载慢
**解决方案**：
配置阿里云镜像（在 `~/.m2/settings.xml` 中）：
```xml
<mirror>
  <id>aliyun</id>
  <mirrorOf>central</mirrorOf>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```

---

## 📂 项目文件说明

- `pom.xml` - Maven项目配置文件
- `simple-start.cmd` - Windows快速启动脚本
- `src/main/java/` - Java源代码目录
- `src/main/resources/` - 资源文件目录
  - `application.properties` - 应用配置文件

---

## 📞 联系方式

- **维护者**：Kevin
- **邮箱**：22233329@qq.com
- **最后更新**：2025-10-30

---

## 📄 许可证

本项目仅供学习和参考使用。

---

## 🎉 更新日志

### v1.1.0 (2025-10-30)
- ✅ **新增API网关身份验证功能**
  - 新增BaseRequest基础请求类
  - 添加hospitalId、hospitalName、tickets三个必需参数
  - 所有请求类继承BaseRequest
  - Controller中添加网关参数验证方法
  - 更新所有接口文档和示例
- ✅ 完善安全验证机制
- ✅ 支持多医院场景

### v1.0.0 (2025-10-30)
- ✅ 完成基础架构搭建
- ✅ 实现4个核心接口
- ✅ 连接Oracle数据库视图
- ✅ 实现数据脱敏功能
- ✅ 添加异常处理
- ✅ 添加CORS跨域支持
- ✅ 完善项目文档

---

**祝您使用愉快！** 😊
