package com.hospital.invoice.common;

/**
 * 统一API响应格式类
 * 所有接口都使用此格式返回数据，确保前后端数据交互的一致性
 * 
 * @param <T> 响应数据的类型
 */
public class ApiResponse<T> {
    
    /**
     * 响应状态码
     * 200表示成功，其他值表示各种错误情况
     */
    private Integer code;
    
    /**
     * 响应消息
     * 用于描述本次请求的处理结果
     */
    private String message;
    
    /**
     * 响应数据
     * 实际返回给前端的业务数据
     */
    private T data;
    
    /**
     * 无参构造函数
     */
    public ApiResponse() {
    }
    
    /**
     * 全参构造函数
     * @param code 状态码
     * @param message 响应消息
     * @param data 响应数据
     */
    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 创建成功响应（带数据）
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应对象
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "操作成功", data);
    }
    
    /**
     * 创建成功响应（自定义消息）
     * @param message 响应消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应对象
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }
    
    /**
     * 创建失败响应
     * @param code 错误码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败响应对象
     */
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }
    
    /**
     * 创建失败响应（默认500错误码）
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败响应对象
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, message, null);
    }
    
    // Getter 和 Setter 方法
    
    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}
















