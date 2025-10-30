package com.hospital.invoice.enums;

/**
 * 票据类型枚举
 * 用于标识不同类型的医疗票据
 */
public enum InvoiceType {
    
    /**
     * 门诊票据
     */
    OUTPATIENT("OUTPATIENT", "门诊"),
    
    /**
     * 住院票据
     */
    INPATIENT("INPATIENT", "住院"),
    
    /**
     * 体检票据
     */
    EXAMINATION("EXAMINATION", "体检");
    
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
    InvoiceType(String code, String label) {
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
    public static InvoiceType fromCode(String code) {
        for (InvoiceType type : InvoiceType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}


