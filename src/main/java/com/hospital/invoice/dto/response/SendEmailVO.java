package com.hospital.invoice.dto.response;

/**
 * 发送邮件响应视图对象（View Object）
 * 用于返回邮件发送结果数据给前端
 */
public class SendEmailVO {
    
    /**
     * 发送状态
     * SUCCESS-成功，FAILED-失败
     */
    private String sendStatus;
    
    /**
     * 发送时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String sendTime;
    
    /**
     * 接收邮箱地址
     */
    private String email;
    
    // 无参构造函数
    public SendEmailVO() {
    }
    
    // 全参构造函数
    public SendEmailVO(String sendStatus, String sendTime, String email) {
        this.sendStatus = sendStatus;
        this.sendTime = sendTime;
        this.email = email;
    }
    
    // Getter 和 Setter 方法
    
    public String getSendStatus() {
        return sendStatus;
    }
    
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }
    
    public String getSendTime() {
        return sendTime;
    }
    
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
















