package com.hospital.invoice.dto.response;

/**
 * 发票详情视图对象（View Object）
 * 用于返回发票完整详情数据给前端
 */
public class InvoiceDetailVO {
    
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
     * 发票号码（完整号码）
     * 注意：未开票时为NULL
     */
    private String invoiceNo;
    
    /**
     * 票据类型代码
     */
    private String invoiceType;
    
    /**
     * 票据类型中文描述
     */
    private String invoiceTypeLabel;
    
    /**
     * 发票金额（单位：元）
     */
    private String amount;
    
    /**
     * 开票时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String createTime;
    
    /**
     * 发票状态代码
     */
    private String status;
    
    /**
     * 发票状态中文描述
     */
    private String statusLabel;
    
    /**
     * 患者姓名
     */
    private String patientName;
    
    /**
     * 患者手机号码
     */
    private String phoneNumber;
    
    /**
     * 患者身份证号（脱敏处理）
     */
    private String idCard;
    
    /**
     * 业务日期
     * 门诊-就诊日期，住院-出院日期，体检-体检日期
     * 格式：yyyy-MM-dd
     */
    private String businessDate;
    
    /**
     * 科室或套餐信息
     * 门诊-就诊科室，住院-出院科室，体检-体检套餐
     */
    private String deptOrPackage;
    
    /**
     * 发票代码
     */
    private String invoiceCode;
    
    /**
     * 机器码
     */
    private String machineCode;
    
    /**
     * 校验码
     */
    private String checkCode;
    
    /**
     * 电子发票PDF文件下载地址
     */
    private String pdfUrl;
    
    /**
     * 发票二维码图片地址
     */
    private String qrCodeUrl;
    
    /**
     * 总金额
     */
    private String totalAmount;
    
    /**
     * 自付金额
     */
    private String selfPayAmount;
    
    /**
     * 医保支付金额
     */
    private String insuranceAmount;
    
    /**
     * 备注信息
     */
    private String remark;
    
    // 无参构造函数
    public InvoiceDetailVO() {
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
    
    public String getIdCard() {
        return idCard;
    }
    
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    
    public String getBusinessDate() {
        return businessDate;
    }
    
    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }
    
    public String getDeptOrPackage() {
        return deptOrPackage;
    }
    
    public void setDeptOrPackage(String deptOrPackage) {
        this.deptOrPackage = deptOrPackage;
    }
    
    public String getInvoiceCode() {
        return invoiceCode;
    }
    
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }
    
    public String getMachineCode() {
        return machineCode;
    }
    
    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
    
    public String getCheckCode() {
        return checkCode;
    }
    
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
    
    public String getPdfUrl() {
        return pdfUrl;
    }
    
    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
    
    public String getQrCodeUrl() {
        return qrCodeUrl;
    }
    
    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
    
    public String getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getSelfPayAmount() {
        return selfPayAmount;
    }
    
    public void setSelfPayAmount(String selfPayAmount) {
        this.selfPayAmount = selfPayAmount;
    }
    
    public String getInsuranceAmount() {
        return insuranceAmount;
    }
    
    public void setInsuranceAmount(String insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}


