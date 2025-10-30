package com.hospital.invoice.dto.request;

/**
 * 基础请求类
 * 包含所有接口共用的API网关身份验证参数
 * 
 * @author Kevin
 * @since 2025-10-30
 */
public class BaseRequest {
    
    /**
     * 医院ID（必填）
     * 用于标识请求来源的医院
     */
    private String hospitalId;
    
    /**
     * 医院名称（必填）
     * 医院的完整名称
     */
    private String hospitalName;
    
    /**
     * API网关身份验证票据（必填）
     * 用于API网关验证请求的合法性
     */
    private String tickets;
    
    // 无参构造函数
    public BaseRequest() {
    }
    
    // 全参构造函数
    public BaseRequest(String hospitalId, String hospitalName, String tickets) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.tickets = tickets;
    }
    
    // Getter 和 Setter 方法
    
    public String getHospitalId() {
        return hospitalId;
    }
    
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
    
    public String getHospitalName() {
        return hospitalName;
    }
    
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    
    public String getTickets() {
        return tickets;
    }
    
    public void setTickets(String tickets) {
        this.tickets = tickets;
    }
    
    @Override
    public String toString() {
        return "BaseRequest{" +
                "hospitalId='" + hospitalId + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", tickets='" + (tickets != null ? "***" : null) + '\'' +
                '}';
    }
}

