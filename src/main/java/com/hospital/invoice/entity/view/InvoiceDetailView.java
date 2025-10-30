package com.hospital.invoice.entity.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票详情视图实体类
 * 对应数据库视图：comm.invoice_detail_view
 * 
 * 重要说明：
 * 1. 这个类对应的是数据库视图，不是表
 * 2. Oracle字段名默认大写，必须用@Column指定
 * 3. @Immutable标记为只读（视图不支持增删改）
 * 4. schema必须指定为COMM
 */
@Entity
@Immutable
@Table(name = "INVOICE_DETAIL_VIEW", schema = "COMM")
public class InvoiceDetailView {
    
    /**
     * 收据号（主键）
     */
    @Id
    @Column(name = "RCPT_NO")
    private String rcptNo;
    
    /**
     * 发票唯一标识ID
     * 注意：未开票时为NULL
     */
    @Column(name = "INVOICE_ID")
    private String invoiceId;
    
    /**
     * 发票号码（完整号码）
     * 注意：未开票时为NULL
     */
    @Column(name = "INVOICE_NO")
    private String invoiceNo;
    
    /**
     * 票据类型代码
     */
    @Column(name = "INVOICE_TYPE")
    private String invoiceType;
    
    /**
     * 票据类型中文标签（原始字节）
     * 🔑 关键：定义为byte[]，让Hibernate使用getBytes()读取
     */
    @Column(name = "INVOICE_TYPE_LABEL")
    private byte[] invoiceTypeLabelBytes;
    
    /**
     * 发票金额（单位：元）
     */
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    
    /**
     * 开票时间
     * 注意：Oracle的DATE类型在JPA中映射为Timestamp
     */
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
    /**
     * 发票状态代码
     */
    @Column(name = "STATUS")
    private String status;
    
    /**
     * 发票状态中文标签（原始字节）
     * 🔑 关键：定义为byte[]
     */
    @Column(name = "STATUS_LABEL")
    private byte[] statusLabelBytes;
    
    /**
     * 患者ID
     */
    @Column(name = "PATIENT_ID")
    private String patientId;
    
    /**
     * 患者姓名（原始字节）
     * 🔑 关键：定义为byte[]
     */
    @Column(name = "PATIENT_NAME")
    private byte[] patientNameBytes;
    
    /**
     * 患者手机号码
     */
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    
    /**
     * 患者身份证号（已脱敏）
     */
    @Column(name = "ID_CARD")
    private String idCard;
    
    /**
     * 业务日期
     * 门诊-就诊日期，住院-出院日期，体检-体检日期
     */
    @Column(name = "BUSINESS_DATE")
    @Temporal(TemporalType.DATE)
    private Date businessDate;
    
    /**
     * 科室或套餐信息（原始字节）
     * 门诊-就诊科室，住院-出院科室，体检-体检套餐
     * 🔑 关键：定义为byte[]
     */
    @Column(name = "DEPT_OR_PACKAGE")
    private byte[] deptOrPackageBytes;
    
    /**
     * 发票代码
     */
    @Column(name = "INVOICE_CODE")
    private String invoiceCode;
    
    /**
     * 机器码
     */
    @Column(name = "MACHINE_CODE")
    private String machineCode;
    
    /**
     * 校验码
     */
    @Column(name = "CHECK_CODE")
    private String checkCode;
    
    /**
     * 电子发票PDF文件下载地址
     */
    @Column(name = "PDF_URL")
    private String pdfUrl;
    
    /**
     * 发票二维码图片地址
     */
    @Column(name = "QR_CODE_URL")
    private String qrCodeUrl;
    
    /**
     * 总金额
     */
    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;
    
    /**
     * 自付金额
     */
    @Column(name = "SELF_PAY_AMOUNT")
    private BigDecimal selfPayAmount;
    
    /**
     * 医保支付金额
     */
    @Column(name = "INSURANCE_AMOUNT")
    private BigDecimal insuranceAmount;
    
    /**
     * 备注信息（原始字节）
     * 🔑 关键：定义为byte[]
     */
    @Column(name = "REMARK")
    private byte[] remarkBytes;
    
    // 无参构造函数
    public InvoiceDetailView() {
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
    
    /**
     * 获取发票类型标签（自动转换GBK字节为String）
     * 🔑 关键方法：将byte[]用GBK解码为String
     */
    public String getInvoiceTypeLabel() {
        if (invoiceTypeLabelBytes == null) {
            return null;
        }
        try {
            return new String(invoiceTypeLabelBytes, "GBK");
        } catch (Exception e) {
            return new String(invoiceTypeLabelBytes);
        }
    }
    
    public void setInvoiceTypeLabelBytes(byte[] invoiceTypeLabelBytes) {
        this.invoiceTypeLabelBytes = invoiceTypeLabelBytes;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * 获取状态标签（自动转换GBK字节为String）
     * 🔑 关键方法：将byte[]用GBK解码为String
     */
    public String getStatusLabel() {
        if (statusLabelBytes == null) {
            return null;
        }
        try {
            return new String(statusLabelBytes, "GBK");
        } catch (Exception e) {
            return new String(statusLabelBytes);
        }
    }
    
    public void setStatusLabelBytes(byte[] statusLabelBytes) {
        this.statusLabelBytes = statusLabelBytes;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    /**
     * 获取患者姓名（自动转换GBK字节为String）
     * 🔑 关键方法：将byte[]用GBK解码为String
     */
    public String getPatientName() {
        if (patientNameBytes == null) {
            return null;
        }
        try {
            return new String(patientNameBytes, "GBK");
        } catch (Exception e) {
            return new String(patientNameBytes);
        }
    }
    
    public void setPatientNameBytes(byte[] patientNameBytes) {
        this.patientNameBytes = patientNameBytes;
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
    
    public Date getBusinessDate() {
        return businessDate;
    }
    
    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }
    
    /**
     * 获取科室或套餐（自动转换GBK字节为String）
     * 🔑 关键方法：将byte[]用GBK解码为String
     */
    public String getDeptOrPackage() {
        if (deptOrPackageBytes == null) {
            return null;
        }
        try {
            return new String(deptOrPackageBytes, "GBK");
        } catch (Exception e) {
            return new String(deptOrPackageBytes);
        }
    }
    
    public void setDeptOrPackageBytes(byte[] deptOrPackageBytes) {
        this.deptOrPackageBytes = deptOrPackageBytes;
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
    
    /**
     * 获取备注（自动转换GBK字节为String）
     * 🔑 关键方法：将byte[]用GBK解码为String
     */
    public String getRemark() {
        if (remarkBytes == null) {
            return null;
        }
        try {
            return new String(remarkBytes, "GBK");
        } catch (Exception e) {
            return new String(remarkBytes);
        }
    }
    
    public void setRemarkBytes(byte[] remarkBytes) {
        this.remarkBytes = remarkBytes;
    }
}

