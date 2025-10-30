package com.hospital.invoice.dto.request;

/**
 * 发票发送邮箱请求参数类
 * 对应接口：/api/invoice/sendEmail
 */
public class SendEmailRequest extends BaseRequest {
    
    /**
     * 发票唯一标识ID（必填）
     */
    private String invoiceId;
    
    /**
     * 患者ID（必填，用于权限校验）
     */
    private String patientId;
    
    /**
     * 接收邮箱地址（必填）
     */
    private String email;
    
    /**
     * 邮件标题（可选）
     * 默认值："电子发票"
     */
    private String emailTitle;
    
    // 无参构造函数
    public SendEmailRequest() {
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getEmailTitle() {
        return emailTitle;
    }
    
    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }
}
















