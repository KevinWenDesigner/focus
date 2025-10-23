# HIS电子发票系统 HTTP接口文档

## 1. 发票列表查询接口

### 1.1 接口信息
- **接口名称**: 查询电子发票列表
- **接口路径**: `/api/invoice/list`
- **请求方式**: `GET` 或 `POST`
- **接口描述**: 根据条件查询用户的电子发票列表

### 1.2 请求参数

| 参数名 | 类型 | 必填 | 说明 | 示例值 |
|--------|------|------|------|--------|
| patientId | String | 是 | 患者ID | "123456" |
| patientName | String | 否 | 患者姓名 | "唐谢关" |
| phoneNumber | String | 否 | 手机号码 | "13800138000" |
| startDate | String | 否 | 搜索开始日期，格式：yyyy-MM-dd | "2023-05-01" |
| endDate | String | 否 | 搜索结束日期，格式：yyyy-MM-dd | "2023-05-31" |
| invoiceType | String | 否 | 票据类型：OUTPATIENT-门诊，INPATIENT-住院，EXAMINATION-体检 | "OUTPATIENT" |
| status | String | 否 | 发票状态：ISSUED-已开票，PENDING-待开票，CANCELLED-已作废 | "ISSUED" |

### 1.3 请求示例

```json
{
  "patientId": "123456",
  "patientName": "唐谢关",
  "phoneNumber": "13800138000",
  "startDate": "2023-05-01",
  "endDate": "2023-05-31",
  "invoiceType": "OUTPATIENT",
  "status": "ISSUED"
}
```

### 1.4 响应参数

| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 响应状态码，200-成功，其他-失败 |
| message | String | 响应消息 |
| data | Array | 发票列表数据 |
| └─ invoiceId | String | 发票唯一标识ID |
| └─ invoiceNo | String | 发票号码 |
| └─ invoiceType | String | 票据类型：OUTPATIENT-门诊，INPATIENT-住院，EXAMINATION-体检 |
| └─ invoiceTypeLabel | String | 票据类型中文描述 |
| └─ amount | String | 发票金额，单位：元 |
| └─ createTime | String | 开票时间，格式：yyyy-MM-dd HH:mm:ss |
| └─ displayTime | String | 列表显示时间，格式：yyyy-MM-dd HH:mm |
| └─ status | String | 发票状态：ISSUED-已开票，PENDING-待开票，CANCELLED-已作废 |
| └─ statusLabel | String | 发票状态中文描述 |
| └─ patientName | String | 患者姓名 |
| └─ phoneNumber | String | 手机号码（脱敏） |

### 1.5 响应示例

```json
{
  "code": 200,
  "message": "查询成功",
  "data": [
    {
      "invoiceId": "INV20230515001",
      "invoiceNo": "00123456",
      "invoiceType": "OUTPATIENT",
      "invoiceTypeLabel": "门诊",
      "amount": "298.50",
      "createTime": "2023-05-15 14:30:00",
      "displayTime": "2023-05-15 14:30",
      "status": "ISSUED",
      "statusLabel": "已开票",
      "patientName": "唐谢关",
      "phoneNumber": "6103**********0515"
    }
  ]
}
```

---

## 2. 发票详情查询接口

### 2.1 接口信息
- **接口名称**: 查询电子发票详情
- **接口路径**: `/api/invoice/detail`
- **请求方式**: `GET` 或 `POST`
- **接口描述**: 根据发票ID查询电子发票详细信息

### 2.2 请求参数

| 参数名 | 类型 | 必填 | 说明 | 示例值 |
|--------|------|------|------|--------|
| invoiceId | String | 是 | 发票唯一标识ID | "INV20240115001" |
| patientId | String | 是 | 患者ID（用于权限校验） | "123456" |

### 2.3 请求示例

```json
{
  "invoiceId": "INV20240115001",
  "userId": "123456"
}
```

### 2.4 响应参数

| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 响应状态码，200-成功，其他-失败 |
| message | String | 响应消息 |
| data | Object | 发票详情数据 |
| └─ invoiceId | String | 发票唯一标识ID |
| └─ invoiceNo | String | 发票号码（完整号码） |
| └─ invoiceType | String | 票据类型：OUTPATIENT-门诊，INPATIENT-住院，EXAMINATION-体检 |
| └─ invoiceTypeLabel | String | 票据类型中文描述 |
| └─ amount | String | 发票金额，单位：元 |
| └─ createTime | String | 开票时间，格式：yyyy-MM-dd HH:mm:ss |
| └─ status | String | 发票状态：ISSUED-已开票，PENDING-待开票，CANCELLED-已作废 |
| └─ statusLabel | String | 发票状态中文描述 |
| └─ patientName | String | 患者姓名 |
| └─ phoneNumber | String | 患者手机号码 |
| └─ idCard | String | 患者身份证号（脱敏） |
| └─ businessDate | String | 业务日期：门诊-就诊日期，住院-出院日期，体检-体检日期，格式：yyyy-MM-dd |
| └─ deptOrPackage | String | 科室或套餐信息：门诊-就诊科室，住院-出院科室，体检-体检套餐 |
| └─ invoiceCode | String | 发票代码 |
| └─ machineCode | String | 机器码 |
| └─ checkCode | String | 校验码 |
| └─ pdfUrl | String | 电子发票PDF文件下载地址 |
| └─ qrCodeUrl | String | 发票二维码图片地址 |
| └─ totalAmount | String | 总金额 |
| └─ selfPayAmount | String | 自付金额 |
| └─ insuranceAmount | String | 医保支付金额 |
| └─ remark | String | 备注信息 |

### 2.5 响应示例

#### 2.5.1 门诊发票示例

```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "invoiceId": "INV20240115001",
    "invoiceNo": "00123456789",
    "invoiceType": "OUTPATIENT",
    "invoiceTypeLabel": "门诊",
    "amount": "128.50",
    "createTime": "2024-01-15 10:30:00",
    "status": "ISSUED",
    "statusLabel": "已开票",
    "patientName": "唐谢关",
    "phoneNumber": "13800138000",
    "idCard": "610***********0515",
    "businessDate": "2024-01-15",
    "deptOrPackage": "内科",
    "invoiceCode": "044001900111",
    "machineCode": "499098765432",
    "checkCode": "12345",
    "pdfUrl": "https://example.com/invoice/INV20240115001.pdf",
    "qrCodeUrl": "https://example.com/invoice/qrcode/INV20240115001.png",
    "totalAmount": "128.50",
    "selfPayAmount": "128.50",
    "insuranceAmount": "0.00",
    "remark": ""
  }
}
```

#### 2.5.2 住院发票示例

```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "invoiceId": "INV20240120002",
    "invoiceNo": "00123456790",
    "invoiceType": "INPATIENT",
    "invoiceTypeLabel": "住院",
    "amount": "5680.00",
    "createTime": "2024-01-20 15:20:00",
    "status": "ISSUED",
    "statusLabel": "已开票",
    "patientName": "张三",
    "phoneNumber": "13900139000",
    "idCard": "110***********1234",
    "businessDate": "2024-01-20",
    "deptOrPackage": "骨科",
    "invoiceCode": "044001900112",
    "machineCode": "499098765433",
    "checkCode": "23456",
    "pdfUrl": "https://example.com/invoice/INV20240120002.pdf",
    "qrCodeUrl": "https://example.com/invoice/qrcode/INV20240120002.png",
    "totalAmount": "5680.00",
    "selfPayAmount": "2340.00",
    "insuranceAmount": "3340.00",
    "remark": ""
  }
}
```

#### 2.5.3 体检发票示例

```json
{
  "code": 200,
  "message": "查询成功",
  "data": {
    "invoiceId": "INV20240125003",
    "invoiceNo": "00123456791",
    "invoiceType": "EXAMINATION",
    "invoiceTypeLabel": "体检",
    "amount": "860.00",
    "createTime": "2024-01-25 09:15:00",
    "status": "ISSUED",
    "statusLabel": "已开票",
    "patientName": "李四",
    "phoneNumber": "13700137000",
    "idCard": "320***********5678",
    "businessDate": "2024-01-25",
    "deptOrPackage": "高级体检套餐A",
    "invoiceCode": "044001900113",
    "machineCode": "499098765434",
    "checkCode": "34567",
    "pdfUrl": "https://example.com/invoice/INV20240125003.pdf",
    "qrCodeUrl": "https://example.com/invoice/qrcode/INV20240125003.png",
    "totalAmount": "860.00",
    "selfPayAmount": "860.00",
    "insuranceAmount": "0.00",
    "remark": ""
  }
}
```

---

## 3. 发票发送邮箱接口

### 3.1 接口信息
- **接口名称**: 发送电子发票到邮箱
- **接口路径**: `/api/invoice/sendEmail`
- **请求方式**: `POST`
- **接口描述**: 将电子发票PDF文件发送到指定邮箱

### 3.2 请求参数

| 参数名 | 类型 | 必填 | 说明 | 示例值 |
|--------|------|------|------|--------|
| invoiceId | String | 是 | 发票唯一标识ID | "INV20240115001" |
| patientId | String | 是 | 患者ID（用于权限校验） | "123456" |
| email | String | 是 | 接收邮箱地址 | "user@example.com" |
| emailTitle | String | 否 | 邮件标题，默认："电子发票" | "您的电子发票" |

### 3.3 请求示例

```json
{
  "invoiceId": "INV20240115001",
  "patientId": "123456",
  "email": "user@example.com",
  "emailTitle": "您的电子发票"
}
```

### 3.4 响应参数

| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 响应状态码，200-成功，其他-失败 |
| message | String | 响应消息 |
| data | Object | 响应数据 |
| └─ sendStatus | String | 发送状态：SUCCESS-成功，FAILED-失败 |
| └─ sendTime | String | 发送时间，格式：yyyy-MM-dd HH:mm:ss |
| └─ email | String | 接收邮箱地址 |

### 3.5 响应示例

```json
{
  "code": 200,
  "message": "发送成功",
  "data": {
    "sendStatus": "SUCCESS",
    "sendTime": "2024-01-15 10:35:00",
    "email": "user@example.com"
  }
}
```

---

## 4. 发票查看/下载接口

### 4.1 接口信息
- **接口名称**: 下载电子发票PDF
- **接口路径**: `/api/invoice/download`
- **请求方式**: `GET`
- **接口描述**: 获取电子发票PDF文件

### 4.2 请求参数

| 参数名 | 类型 | 必填 | 说明 | 示例值 |
|--------|------|------|------|--------|
| invoiceId | String | 是 | 发票唯一标识ID | "INV20240115001" |
| patientId | String | 是 | 患者ID（用于权限校验） | "123456" |

### 4.3 请求示例

```
GET /api/invoice/download?invoiceId=INV20240115001&patientId=123456
```

### 4.4 响应说明

- **成功**: 返回PDF文件流，Content-Type: application/pdf
- **失败**: 返回JSON格式错误信息

```json
{
  "code": 404,
  "message": "发票不存在或无权访问",
  "data": null
}
```

---

## 5. 公共响应码说明

| 状态码 | 说明 |
|--------|------|
| 200 | 请求成功 |
| 400 | 请求参数错误 |
| 401 | 未授权，需要登录 |
| 403 | 无权访问该资源 |
| 404 | 请求的资源不存在 |
| 500 | 服务器内部错误 |
| 503 | 服务暂时不可用 |

---

## 6. 字段枚举值说明

### 6.1 票据类型（invoiceType）

| 枚举值 | 说明 |
|--------|------|
| OUTPATIENT | 门诊 |
| INPATIENT | 住院 |
| EXAMINATION | 体检 |

### 6.2 发票状态（status）

| 枚举值 | 说明 |
|--------|------|
| ISSUED | 已开票 |
| PENDING | 待开票 |
| CANCELLED | 已作废 |

---

## 7. 接口调用注意事项

1. **认证授权**: 所有接口需要在请求头中携带用户认证Token
   ```
   Authorization: Bearer {token}
   ```

2. **时间格式**: 所有时间字段统一使用北京时间（GMT+8）

3. **金额格式**: 所有金额字段保留两位小数，单位为元

4. **手机号脱敏**: 列表显示时手机号中间位进行脱敏处理

5. **身份证脱敏**: 身份证号中间位进行脱敏处理

6. **请求频率**: 建议限制同一用户每秒最多请求10次

7. **文件大小**: PDF文件大小限制在5MB以内

8. **邮箱格式**: 邮箱地址需符合标准邮箱格式验证

9. **跨域处理**: 接口支持CORS跨域请求

---

## 8. 错误码说明

| 错误码 | 说明 | 解决方案 |
|--------|------|----------|
| 10001 | 用户未登录 | 请先登录 |
| 10002 | Token已过期 | 请重新登录 |
| 10003 | 无权访问 | 检查权限配置 |
| 20001 | 发票不存在 | 检查invoiceId是否正确 |
| 20002 | 发票已作废 | 该发票已作废，无法操作 |
| 20003 | 发票PDF生成失败 | 请联系管理员 |
| 30001 | 邮箱格式错误 | 请输入正确的邮箱地址 |
| 30002 | 邮件发送失败 | 请稍后重试或联系管理员 |
| 40001 | 参数缺失 | 检查必填参数 |
| 40002 | 参数格式错误 | 检查参数格式是否正确 |
| 50001 | 系统异常 | 请联系管理员 |
| 50002 | 数据库异常 | 请稍后重试 |

---

## 9. 接口测试示例（cURL）

### 9.1 查询发票列表

```bash
curl -X POST "https://api.example.com/api/invoice/list" \
  -H "Authorization: Bearer your_token_here" \
  -H "Content-Type: application/json" \
  -d '{
    "patientId": "123456",
    "startDate": "2023-05-01",
    "endDate": "2023-05-31",
    "invoiceType": "OUTPATIENT"
  }'
```

### 9.2 查询发票详情

```bash
curl -X POST "https://api.example.com/api/invoice/detail" \
  -H "Authorization: Bearer your_token_here" \
  -H "Content-Type: application/json" \
  -d '{
    "invoiceId": "INV20240115001",
    "patientId": "123456"
  }'
```

### 9.3 发送到邮箱

```bash
curl -X POST "https://api.example.com/api/invoice/sendEmail" \
  -H "Authorization: Bearer your_token_here" \
  -H "Content-Type: application/json" \
  -d '{
    "invoiceId": "INV20240115001",
    "patientId": "123456",
    "email": "user@example.com"
  }'
```

### 9.4 下载发票

```bash
curl -X GET "https://api.example.com/api/invoice/download?invoiceId=INV20240115001&patientId=123456" \
  -H "Authorization: Bearer your_token_here" \
  --output invoice.pdf
```

---

## 10. 版本历史

| 版本号 | 更新日期 | 更新内容 | 更新人 |
|--------|----------|----------|--------|
| v1.0.0 | 2025-10-23 | 初始版本，包含基础发票功能 | - |

---

**文档维护**: Kevin
**最后更新**: 2025-10-23
**联系方式**: 22233329@qq.com

