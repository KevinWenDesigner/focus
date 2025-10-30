package com.hospital.invoice.entity.view;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * 发票列表视图实体类 - 简化版
 * 用于测试字段映射，只包含最基本的字段
 */
@Entity
@Immutable
@Table(name = "INVOICE_LIST_VIEW", schema = "COMM")
public class InvoiceListViewSimple {
    
    @Id
    @Column(name = "RCPT_NO")
    private String rcptNo;  // 收据号（主键）
    
    @Column(name = "INVOICE_ID")
    private String invoiceId;
    
    @Column(name = "PATIENT_ID")
    private String patientId;
    
    @Column(name = "INVOICE_NO")
    private String invoiceNo;
    
    @Column(name = "PATIENT_NAME")
    private String patientName;
    
    // 无参构造函数
    public InvoiceListViewSimple() {
    }
    
    // Getter 和 Setter
    
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
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public String getInvoiceNo() {
        return invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}


