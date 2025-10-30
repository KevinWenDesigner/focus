package com.hospital.invoice.entity;

import com.hospital.invoice.enums.InvoiceStatus;
import com.hospital.invoice.enums.InvoiceType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 发票实体类
 * 对应数据库中的发票表，存储发票的完整信息
 * 注意：这里预留了数据库映射注解的位置，实际使用时可添加 @Entity、@Table 等JPA注解
 */
public class Invoice {
    
    /**
     * 发票唯一标识ID（主键）
     * 例如：INV20240115001
     */
    private String invoiceId;
    
    /**
     * 发票号码
     * 例如：00123456789
     */
    private String invoiceNo;
    
    /**
     * 票据类型
     * 参考：InvoiceType枚举
     */
    private InvoiceType invoiceType;
    
    /**
     * 发票金额（单位：元）
     * 使用BigDecimal确保金额计算的精确性
     */
    private BigDecimal amount;
    
    /**
     * 开票时间
     */
    private LocalDateTime createTime;
    
    /**
     * 发票状态
     * 参考：InvoiceStatus枚举
     */
    private InvoiceStatus status;
    
    /**
     * 患者ID（外键）
     */
    private String patientId;
    
    /**
     * 患者姓名
     */
    private String patientName;
    
    /**
     * 患者手机号码
     */
    private String phoneNumber;
    
    /**
     * 患者身份证号
     */
    private String idCard;
    
    /**
     * 业务日期
     * 门诊-就诊日期，住院-出院日期，体检-体检日期
     */
    private LocalDate businessDate;
    
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
    private BigDecimal totalAmount;
    
    /**
     * 自付金额
     */
    private BigDecimal selfPayAmount;
    
    /**
     * 医保支付金额
     */
    private BigDecimal insuranceAmount;
    
    /**
     * 备注信息
     */
    private String remark;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    // 无参构造函数
    public Invoice() {
    }
    
    // Getter 和 Setter 方法
    
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
    
    public InvoiceType getInvoiceType() {
        return invoiceType;
    }
    
    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public InvoiceStatus getStatus() {
        return status;
    }
    
    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
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
    
    public LocalDate getBusinessDate() {
        return businessDate;
    }
    
    public void setBusinessDate(LocalDate businessDate) {
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
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public BigDecimal getSelfPayAmount() {
        return selfPayAmount;
    }
    
    public void setSelfPayAmount(BigDecimal selfPayAmount) {
        this.selfPayAmount = selfPayAmount;
    }
    
    public BigDecimal getInsuranceAmount() {
        return insuranceAmount;
    }
    
    public void setInsuranceAmount(BigDecimal insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}

















