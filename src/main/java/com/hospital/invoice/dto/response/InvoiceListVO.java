package com.hospital.invoice.dto.response;

/**
 * 发票列表项视图对象（View Object）
 * 用于返回发票列表数据给前端
 */
public class InvoiceListVO {
    
    /**
     * 收据号（业务流水号）
     */
    private String rcptNo;
    
    /**
     * 发票唯一标识ID
     * 注意：未开票时为NULL
     */
    private String invoiceId;
    
    /**
     * 发票号码
     * 注意：未开票时为NULL
     */
    private String invoiceNo;
    
    /**
     * 票据类型代码
     * 例如：OUTPATIENT、INPATIENT、EXAMINATION
     */
    private String invoiceType;
    
    /**
     * 票据类型中文描述
     * 例如：门诊、住院、体检
     */
    private String invoiceTypeLabel;
    
    /**
     * 发票金额（单位：元）
     * 返回字符串格式，保留两位小数
     */
    private String amount;
    
    /**
     * 开票时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String createTime;
    
    /**
     * 列表显示时间
     * 格式：yyyy-MM-dd HH:mm
     */
    private String displayTime;
    
    /**
     * 发票状态代码
     * 例如：ISSUED、PENDING、CANCELLED
     */
    private String status;
    
    /**
     * 发票状态中文描述
     * 例如：已开票、待开票、已作废
     */
    private String statusLabel;
    
    /**
     * 患者姓名
     */
    private String patientName;
    
    /**
     * 手机号码（脱敏处理）
     * 例如：138****0000
     */
    private String phoneNumber;
    
    // 无参构造函数
    public InvoiceListVO() {
    }
    
    // Getter 和 Setter 方法
    
    public String getRcptNo() {
        return rcptNo;
    }
    
    public void setRcptNo(String rcptNo) {
        this.rcptNo = rcptNo;
    }
    
    public String getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    
    public String getInvoiceNo() {
        return invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    
    public String getInvoiceType() {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
    
    public String getInvoiceTypeLabel() {
        return invoiceTypeLabel;
    }
    
    public void setInvoiceTypeLabel(String invoiceTypeLabel) {
        this.invoiceTypeLabel = invoiceTypeLabel;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    public String getDisplayTime() {
        return displayTime;
    }
    
    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatusLabel() {
        return statusLabel;
    }
    
    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}


