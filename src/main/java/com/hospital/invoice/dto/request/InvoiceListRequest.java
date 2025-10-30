package com.hospital.invoice.dto.request;

/**
 * 发票列表查询请求参数类
 * 对应接口：/api/invoice/list
 */
public class InvoiceListRequest extends BaseRequest {
    
    /**
     * 患者ID（必填）
     */
    private String patientId;
    
    /**
     * 患者姓名（可选）
     */
    private String patientName;
    
    /**
     * 手机号码（可选）
     */
    private String phoneNumber;
    
    /**
     * 搜索开始日期（可选）
     * 格式：yyyy-MM-dd
     */
    private String startDate;
    
    /**
     * 搜索结束日期（可选）
     * 格式：yyyy-MM-dd
     */
    private String endDate;
    
    /**
     * 票据类型（可选）
     * 可选值：OUTPATIENT-门诊，INPATIENT-住院，EXAMINATION-体检
     */
    private String invoiceType;
    
    /**
     * 发票状态（可选）
     * 可选值：ISSUED-已开票，PENDING-待开票，CANCELLED-已作废
     */
    private String status;
    
    // 无参构造函数
    public InvoiceListRequest() {
    }
    
    // Getter 和 Setter 方法
    
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
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String getInvoiceType() {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
















