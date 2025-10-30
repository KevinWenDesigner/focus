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
     * 医院编码（必填）
     * 用于标识请求来源的医院
     */
    private String hospitalCode;
    
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
    public BaseRequest(String hospitalCode, String hospitalName, String tickets) {
        this.hospitalCode = hospitalCode;
        this.hospitalName = hospitalName;
        this.tickets = tickets;
    }
    
    // Getter 和 Setter 方法
    
    public String getHospitalCode() {
        return hospitalCode;
    }
    
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
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
                "hospitalCode='" + hospitalCode + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", tickets='" + (tickets != null ? "***" : null) + '\'' +
                '}';
    }
}

