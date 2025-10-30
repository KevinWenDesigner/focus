package com.hospital.invoice.enums;

/**
 * 发票状态枚举
 * 用于标识发票的不同状态
 */
public enum InvoiceStatus {
    
    /**
     * 已开票
     */
    ISSUED("ISSUED", "已开票"),
    
    /**
     * 待开票
     */
    PENDING("PENDING", "待开票"),
    
    /**
     * 已作废
     */
    CANCELLED("CANCELLED", "已作废"),
    
    /**
     * 已冲红
     */
    REVERSED("REVERSED", "已冲红");
    
    /**
     * 枚举值代码（用于数据库存储和接口传输）
     */
    private final String code;
    
    /**
     * 枚举值中文标签（用于前端显示）
     */
    private final String label;
    
    /**
     * 构造函数
     * @param code 枚举代码
     * @param label 中文标签
     */
    InvoiceStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }
    
    /**
     * 获取枚举代码
     * @return 枚举代码
     */
    public String getCode() {
        return code;
    }
    
    /**
     * 获取中文标签
     * @return 中文标签
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * 根据代码获取对应的枚举对象
     * @param code 枚举代码
     * @return 对应的枚举对象，如果不存在返回null
     */
    public static InvoiceStatus fromCode(String code) {
        for (InvoiceStatus status : InvoiceStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}




