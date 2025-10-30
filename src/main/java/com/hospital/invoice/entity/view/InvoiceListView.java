package com.hospital.invoice.entity.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票列表视图实体类
 * 对应数据库视图：comm.invoice_list_view
 * 
 * 重要说明：
 * 1. 这个类对应的是数据库视图，不是表
 * 2. Oracle字段名默认大写，必须用@Column指定
 * 3. @Immutable标记为只读（视图不支持增删改）
 * 4. schema必须指定为COMM
 */
@Entity
@Immutable
@Table(name = "INVOICE_LIST_VIEW", schema = "COMM")
public class InvoiceListView {
    
    /**
     * 收据号（主键）
     * 注意：收据号是业务流水号，永远不为空，适合作为主键
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
     * 发票号码
     * 注意：未开票时为NULL
     */
    @Column(name = "INVOICE_NO")
    private String invoiceNo;
    
    /**
     * 票据类型代码
     * OUTPATIENT-门诊，INPATIENT-住院，EXAMINATION-体检
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
     * 列表显示时间（格式化后的时间字符串）
     * 格式：yyyy-MM-dd HH:mm
     */
    @Column(name = "DISPLAY_TIME")
    private String displayTime;
    
    /**
     * 发票状态代码
     * ISSUED-已开票，PENDING-待开票，CANCELLED-已作废
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
     * 手机号码（已脱敏）
     * 例如：138****0000
     */
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    
    // 无参构造函数
    public InvoiceListView() {
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
}

