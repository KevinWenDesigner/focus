package com.hospital.invoice.dto.request;

/**
 * 发票详情查询请求参数类
 * 对应接口：/api/invoice/detail
 */
public class InvoiceDetailRequest extends BaseRequest {
    
    /**
     * 发票唯一标识ID（必填）
     */
    private String invoiceId;
    
    /**
     * 患者ID（必填，用于权限校验）
     */
    private String patientId;
    
    // 无参构造函数
    public InvoiceDetailRequest() {
    }
    
    // 全参构造函数
    public InvoiceDetailRequest(String invoiceId, String patientId) {
        this.invoiceId = invoiceId;
        this.patientId = patientId;
    }
    
    // Getter 和 Setter 方法
    
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
}
















