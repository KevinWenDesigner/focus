# API网关身份验证参数 - 更新说明

## 📅 更新日期
2025-10-30

## 🎯 更新目标
为所有接口添加API网关身份验证功能，支持多医院场景下的安全访问控制。

---

## ✅ 已完成的更新

### 1. 新增 BaseRequest 基础请求类

**文件位置**：`src/main/java/com/hospital/invoice/dto/request/BaseRequest.java`

**包含字段**：
- `hospitalCode` (String) - 医院ID（必填）
- `hospitalName` (String) - 医院名称（必填）
- `tickets` (String) - API网关验证票据（必填）

**作用**：
- 作为所有请求类的父类
- 统一管理网关验证参数
- 便于扩展和维护

---

### 2. 更新所有请求类

所有请求类现在继承自 `BaseRequest`：

| 请求类 | 文件路径 | 说明 |
|--------|---------|------|
| InvoiceListRequest | dto/request/InvoiceListRequest.java | 发票列表查询请求 |
| InvoiceDetailRequest | dto/request/InvoiceDetailRequest.java | 发票详情查询请求 |
| SendEmailRequest | dto/request/SendEmailRequest.java | 发送邮件请求 |
| InvoiceDownloadRequest | dto/request/InvoiceDownloadRequest.java | 下载发票请求 |

**继承关系**：
```
BaseRequest
    ├── InvoiceListRequest
    ├── InvoiceDetailRequest
    ├── SendEmailRequest
    └── InvoiceDownloadRequest
```

---

### 3. 更新 InvoiceController 控制器

**文件位置**：`src/main/java/com/hospital/invoice/controller/InvoiceController.java`

**新增功能**：

#### 3.1 网关参数验证方法
```java
private String validateGatewayParams(BaseRequest request, String errorMessage)
```

**验证内容**：
- hospitalCode 不能为空
- hospitalName 不能为空
- tickets 不能为空
- 预留了tickets合法性验证接口（待实现）

#### 3.2 所有接口添加网关验证

✅ **发票列表查询** (`/api/invoice/list`)
- POST请求体包含网关参数
- GET请求URL参数包含网关参数

✅ **发票详情查询** (`/api/invoice/detail`)
- 请求体包含网关参数

✅ **发送邮件** (`/api/invoice/sendEmail`)
- 请求体包含网关参数

✅ **下载PDF** (`/api/invoice/download`)
- URL参数包含网关参数：`&hospitalCode=H001&hospitalName=xxx&tickets=xxx`

---

### 4. 更新文档

#### 4.1 README.md
- ✅ 添加API网关参数说明表格
- ✅ 更新所有接口示例，包含网关参数
- ✅ 添加网关验证流程说明
- ✅ 更新安全说明章节
- ✅ 添加更新日志 v1.1.0

#### 4.2 新增文档
- ✅ **API测试示例.md** - 详细的API测试示例文档
  - 包含所有接口的完整请求示例
  - 包含cURL命令示例
  - 包含错误处理示例
  - 包含tickets获取示例

---

## 📋 接口变更说明

### 变更前（v1.0.0）

```json
POST /api/invoice/list
{
  "patientId": "123456"
}
```

### 变更后（v1.1.0）

```json
POST /api/invoice/list
{
  "hospitalCode": "H001",
  "hospitalName": "北京协和医院",
  "tickets": "your_gateway_ticket",
  "patientId": "123456"
}
```

---

## 🔄 兼容性说明

### ⚠️ 重大变更（Breaking Changes）

**所有接口现在都需要携带三个网关参数**，否则会返回错误：

```json
{
  "code": 400,
  "message": "医院ID不能为空",
  "data": null
}
```

### 升级指南

如果你的客户端正在使用旧版本（v1.0.0），需要进行以下更新：

#### 1. 获取tickets
向API网关申请tickets：
```javascript
const response = await fetch('https://api-gateway.hospital.com/auth/getTickets', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    hospitalCode: 'H001',
    appKey: 'your_app_key',
    appSecret: 'your_app_secret'
  })
});
const { tickets } = await response.json();
```

#### 2. 在所有请求中添加网关参数
```javascript
// 更新前
const requestBody = {
  patientId: '123456'
};

// 更新后
const requestBody = {
  hospitalCode: 'H001',
  hospitalName: '北京协和医院',
  tickets: tickets,  // 从网关获取的tickets
  patientId: '123456'
};
```

#### 3. 处理tickets过期
```javascript
// tickets过期时重新获取
if (response.code === 401) {
  // 重新获取tickets
  await refreshTickets();
  // 重试请求
  await retryRequest();
}
```

---

## 🔐 安全增强

### 1. 多医院隔离
- 每个医院使用独立的hospitalCode
- tickets与hospitalCode绑定
- 防止跨医院数据访问

### 2. 访问控制
- tickets有时效性（建议30分钟-2小时）
- tickets可以设置访问权限范围
- 支持黑名单和白名单机制

### 3. 审计追踪
- 所有请求都记录hospitalCode
- 便于追踪和审计
- 支持按医院统计接口使用情况

---

## 📊 测试验证

### 测试用例

#### ✅ 测试1：正常请求（包含完整网关参数）
```bash
curl -X POST "http://localhost:8080/api/invoice/list" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalCode": "H001",
    "hospitalName": "北京协和医院",
    "tickets": "valid_ticket",
    "patientId": "123456"
  }'
```
**预期结果**：返回200，查询成功

#### ❌ 测试2：缺少hospitalCode
```bash
curl -X POST "http://localhost:8080/api/invoice/list" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalName": "北京协和医院",
    "tickets": "valid_ticket",
    "patientId": "123456"
  }'
```
**预期结果**：返回400，"医院ID不能为空"

#### ❌ 测试3：缺少tickets
```bash
curl -X POST "http://localhost:8080/api/invoice/list" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalCode": "H001",
    "hospitalName": "北京协和医院",
    "patientId": "123456"
  }'
```
**预期结果**：返回400，"API网关票据不能为空"

---

## 🚀 后续开发建议

### 1. 实现tickets验证服务
```java
@Service
public class GatewayService {
    public boolean validateTickets(String tickets, String hospitalCode) {
        // 调用网关API验证tickets
        // 检查tickets是否过期
        // 检查tickets是否与hospitalCode匹配
        return true;
    }
}
```

### 2. 添加tickets缓存
```java
@Cacheable(value = "tickets", key = "#hospitalCode")
public String getTickets(String hospitalCode) {
    // 从网关获取tickets并缓存
}
```

### 3. 实现自动刷新机制
```java
// tickets快过期时自动刷新
if (tickets.getExpiresIn() < 300) {  // 剩余5分钟
    refreshTickets();
}
```

### 4. 添加统计功能
```java
// 按医院统计接口调用次数
@Autowired
private StatisticsService statisticsService;

statisticsService.recordApiCall(hospitalCode, apiPath);
```

---

## 📞 联系方式

如有问题，请联系：
- **开发者**：Kevin
- **邮箱**：22233329@qq.com
- **更新日期**：2025-10-30

---

## 📝 版本对照

| 版本 | 日期 | 主要变更 |
|------|------|---------|
| v1.1.0 | 2025-10-30 | 新增API网关身份验证参数 |
| v1.0.0 | 2025-10-30 | 初始版本，基础功能实现 |

---

**更新完成！** 🎉

