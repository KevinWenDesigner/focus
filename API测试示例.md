# API测试示例 - 包含网关验证参数

本文档提供了包含API网关验证参数的完整测试示例。

## 📋 API网关参数说明

所有接口都必须包含以下三个参数：

| 参数名 | 类型 | 必填 | 说明 | 示例值 |
|--------|------|------|------|--------|
| hospitalId | String | 是 | 医院唯一标识 | "H001" |
| hospitalName | String | 是 | 医院名称 | "北京协和医院" |
| tickets | String | 是 | API网关验证票据 | "your_gateway_ticket" |

---

## 1. 发票列表查询

### 请求示例（Postman）

```
POST http://localhost:8080/api/invoice/list
Content-Type: application/json
```

### 请求体

```json
{
  "hospitalId": "H001",
  "hospitalName": "北京协和医院",
  "tickets": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "patientId": "123456",
  "patientName": "张三",
  "phoneNumber": "13800138000",
  "invoiceType": "OUTPATIENT",
  "status": "ISSUED",
  "startDate": "2024-01-01",
  "endDate": "2024-12-31"
}
```

### 成功响应

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

### 错误响应（缺少网关参数）

```json
{
  "code": 400,
  "message": "医院ID不能为空",
  "data": null
}
```

---

## 2. 发票详情查询

### 请求示例

```
POST http://localhost:8080/api/invoice/detail
Content-Type: application/json
```

### 请求体

```json
{
  "hospitalId": "H001",
  "hospitalName": "北京协和医院",
  "tickets": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "invoiceId": "INV20240115001",
  "patientId": "123456"
}
```

### 成功响应

```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "invoiceId": "INV20240115001",
    "invoiceNo": "00123456",
    "invoiceCode": "044001900111",
    "invoiceType": "OUTPATIENT",
    "invoiceTypeLabel": "门诊",
    "totalAmount": "128.50",
    "patientId": "123456",
    "patientName": "张三",
    "idCard": "110***********1234",
    "phoneNumber": "138****8000",
    "createTime": "2024-01-15 10:30:00",
    "status": "ISSUED",
    "statusLabel": "已开票",
    "items": [
      {
        "itemName": "挂号费",
        "itemAmount": "10.00"
      },
      {
        "itemName": "检查费",
        "itemAmount": "118.50"
      }
    ]
  }
}
```

---

## 3. 发送邮件

### 请求示例

```
POST http://localhost:8080/api/invoice/sendEmail
Content-Type: application/json
```

### 请求体

```json
{
  "hospitalId": "H001",
  "hospitalName": "北京协和医院",
  "tickets": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "invoiceId": "INV20240115001",
  "patientId": "123456",
  "email": "patient@example.com",
  "emailTitle": "电子发票"
}
```

### 成功响应

```json
{
  "code": 200,
  "message": "发送成功",
  "data": {
    "success": true,
    "sendTime": "2024-01-15 14:30:00",
    "message": "邮件已发送至 patient@example.com"
  }
}
```

---

## 4. 下载PDF

### 请求示例（GET请求，URL参数）

```
GET http://localhost:8080/api/invoice/download?invoiceId=INV20240115001&patientId=123456&hospitalId=H001&hospitalName=北京协和医院&tickets=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### URL参数说明

| 参数名 | 值 | 必填 |
|--------|-----|------|
| invoiceId | INV20240115001 | 是 |
| patientId | 123456 | 是 |
| hospitalId | H001 | 是 |
| hospitalName | 北京协和医院 | 是 |
| tickets | eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... | 是 |

### 成功响应

直接返回PDF文件流，浏览器会自动下载或预览。

### 错误响应

```json
{
  "code": 400,
  "message": "API网关票据不能为空",
  "data": null
}
```

---

## 🔧 cURL命令示例

### 1. 发票列表查询

```bash
curl -X POST "http://localhost:8080/api/invoice/list" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalId": "H001",
    "hospitalName": "北京协和医院",
    "tickets": "your_gateway_ticket",
    "patientId": "123456",
    "invoiceType": "OUTPATIENT"
  }'
```

### 2. 发票详情查询

```bash
curl -X POST "http://localhost:8080/api/invoice/detail" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalId": "H001",
    "hospitalName": "北京协和医院",
    "tickets": "your_gateway_ticket",
    "invoiceId": "INV20240115001",
    "patientId": "123456"
  }'
```

### 3. 发送邮件

```bash
curl -X POST "http://localhost:8080/api/invoice/sendEmail" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalId": "H001",
    "hospitalName": "北京协和医院",
    "tickets": "your_gateway_ticket",
    "invoiceId": "INV20240115001",
    "patientId": "123456",
    "email": "patient@example.com"
  }'
```

### 4. 下载PDF

```bash
curl -X GET "http://localhost:8080/api/invoice/download?invoiceId=INV20240115001&patientId=123456&hospitalId=H001&hospitalName=北京协和医院&tickets=your_gateway_ticket" \
  --output invoice.pdf
```

---

## ⚠️ 常见错误

### 错误1：缺少网关参数

**请求**：
```json
{
  "patientId": "123456"
}
```

**响应**：
```json
{
  "code": 400,
  "message": "医院ID不能为空",
  "data": null
}
```

### 错误2：tickets过期或无效

```json
{
  "code": 401,
  "message": "API网关票据验证失败",
  "data": null
}
```

### 错误3：hospitalId与tickets不匹配

```json
{
  "code": 401,
  "message": "医院ID与票据不匹配",
  "data": null
}
```

---

## 📝 注意事项

1. **tickets有时效性**：通常有效期为30分钟到2小时，过期需重新申请
2. **tickets与hospitalId绑定**：不能跨医院使用
3. **GET请求参数编码**：URL中的中文参数需要进行URL编码
4. **所有接口都必须包含这三个参数**：hospitalId、hospitalName、tickets

---

## 🔐 获取tickets示例

tickets通常由API网关系统颁发，示例流程：

```bash
# 1. 向网关申请tickets
curl -X POST "https://api-gateway.hospital.com/auth/getTickets" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalId": "H001",
    "appKey": "your_app_key",
    "appSecret": "your_app_secret"
  }'

# 2. 网关返回tickets
{
  "tickets": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 7200,
  "expiresAt": "2024-01-15 16:30:00"
}

# 3. 使用tickets调用业务接口
curl -X POST "http://localhost:8080/api/invoice/list" \
  -H "Content-Type: application/json" \
  -d '{
    "hospitalId": "H001",
    "hospitalName": "北京协和医院",
    "tickets": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "patientId": "123456"
  }'
```

---

**测试愉快！** 🎉

