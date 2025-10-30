package com.hospital.invoice.util;

import java.io.UnsupportedEncodingException;

/**
 * 字符编码转换工具类
 * 专门用于解决Oracle US7ASCII字符集的中文乱码问题
 * 
 * 背景说明：
 * - Oracle数据库字符集设置为US7ASCII（7位ASCII，不支持中文）
 * - 但数据库中实际存储了中文数据（以GBK编码的字节形式）
 * - JDBC读取时，这些字节被错误地解释为ISO-8859-1字符，导致乱码
 * 
 * 解决方案：
 * - 使用ISO-8859-1作为"桥梁"编码来还原原始字节
 * - 然后用正确的编码（GBK/UTF-8）重新构建字符串
 * 
 * @author Hospital Invoice System
 * @version 1.0
 */
public class EncodingConverter {
    
    /**
     * 方法1：ISO-8859-1 -> GBK（推荐，最常用）
     * 
     * 使用场景：
     * - Oracle数据库字符集为US7ASCII
     * - 数据以GBK编码存储
     * - JDBC读取时误解为ISO-8859-1
     * 
     * 原理：
     * - ISO-8859-1是单字节编码，每个字符对应一个字节（0-255）
     * - getBytes("ISO-8859-1")可以完整保留原始字节不变
     * - new String(bytes, "GBK")用正确的编码重新解释这些字节
     * 
     * @param str 从数据库读取的可能乱码的字符串
     * @return 转换后的正确中文字符串
     */
    public static String convertFromUS7ASCII(String str) {
        if (str == null || str.trim().isEmpty()) {
            return str;
        }
        
        try {
            // 第一步：用ISO-8859-1获取原始字节（保持字节不变）
            byte[] bytes = str.getBytes("ISO-8859-1");
            
            // 第二步：用GBK重新解码这些字节
            String result = new String(bytes, "GBK");
            
            // 验证转换结果
            if (isValidChinese(result)) {
                return result;
            }
            
            // 如果GBK失败，尝试UTF-8
            result = new String(bytes, "UTF-8");
            if (isValidChinese(result)) {
                return result;
            }
            
            // 转换失败，返回原字符串
            return str;
            
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }
    
    /**
     * 智能编码转换（自动尝试多种编码组合）
     * 
     * @param str 原始字符串
     * @return 转换后的字符串
     */
    public static String smartConvert(String str) {
        if (str == null || str.trim().isEmpty()) {
            return str;
        }
        
        // 如果已经包含正常中文，直接返回
        if (str.matches(".*[\u4e00-\u9fa5].*")) {
            return str;
        }
        
        // 如果是纯ASCII且不包含乱码特征，直接返回
        if (str.matches("^[\\x00-\\x7F]*$")) {
            return str;
        }
        
        try {
            String result;
            
            // 尝试1: ISO-8859-1 -> GBK（最常用）
            result = tryConvert(str, "ISO-8859-1", "GBK");
            if (result != null) return result;
            
            // 尝试2: ISO-8859-1 -> UTF-8
            result = tryConvert(str, "ISO-8859-1", "UTF-8");
            if (result != null) return result;
            
            // 尝试3: ISO-8859-1 -> GB2312
            result = tryConvert(str, "ISO-8859-1", "GB2312");
            if (result != null) return result;
            
            // 尝试4: US-ASCII -> GBK
            result = tryConvert(str, "US-ASCII", "GBK");
            if (result != null) return result;
            
            // 尝试5: UTF-8 -> GBK
            result = tryConvert(str, "UTF-8", "GBK");
            if (result != null) return result;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 所有尝试都失败，返回原字符串
        return str;
    }
    
    /**
     * 尝试使用指定的编码组合进行转换
     * 
     * @param str 原始字符串
     * @param fromCharset 源编码
     * @param toCharset 目标编码
     * @return 转换成功返回新字符串，失败返回null
     */
    private static String tryConvert(String str, String fromCharset, String toCharset) {
        try {
            byte[] bytes = str.getBytes(fromCharset);
            String result = new String(bytes, toCharset);
            
            if (isValidChinese(result)) {
                System.out.println("[✓ 编码转换成功] " + fromCharset + " → " + toCharset + ": " + result);
                return result;
            }
        } catch (Exception e) {
            // 忽略异常，继续尝试下一种编码
        }
        return null;
    }
    
    /**
     * 检查字符串是否包含有效的中文字符
     * 
     * @param str 待检查的字符串
     * @return true表示包含有效中文，false表示不包含或有乱码
     */
    private static boolean isValidChinese(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        // 必须包含中文字符
        boolean hasChinese = str.matches(".*[\u4e00-\u9fa5]+.*");
        
        // 不能包含替换字符（乱码标记）
        boolean hasReplacementChar = str.contains("�");
        
        // 不能包含常见乱码
        boolean hasGarbledText = str.contains("锟") || str.contains("斤");
        
        return hasChinese && !hasReplacementChar && !hasGarbledText;
    }
    
    /**
     * 批量转换字符串数组
     * 
     * @param strs 字符串数组
     * @return 转换后的字符串数组
     */
    public static String[] convertArray(String[] strs) {
        if (strs == null) {
            return null;
        }
        
        String[] results = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            results[i] = convertFromUS7ASCII(strs[i]);
        }
        return results;
    }
    
    /**
     * 测试方法：验证编码转换是否正常工作
     */
    public static void main(String[] args) {
        // 测试用例：模拟从US7ASCII数据库读取的乱码
        // 注意：实际乱码需要从真实数据库中获取
        
        System.out.println("=== 编码转换工具测试 ===");
        System.out.println();
        
        // 测试1：正常中文（不需要转换）
        String test1 = "张三";
        System.out.println("测试1 - 正常中文:");
        System.out.println("  原始: " + test1);
        System.out.println("  转换: " + convertFromUS7ASCII(test1));
        System.out.println();
        
        // 测试2：空字符串
        String test2 = "";
        System.out.println("测试2 - 空字符串:");
        System.out.println("  原始: '" + test2 + "'");
        System.out.println("  转换: '" + convertFromUS7ASCII(test2) + "'");
        System.out.println();
        
        // 测试3：null
        String test3 = null;
        System.out.println("测试3 - null:");
        System.out.println("  原始: " + test3);
        System.out.println("  转换: " + convertFromUS7ASCII(test3));
        System.out.println();
        
        // 测试4：纯英文
        String test4 = "OUTPATIENT";
        System.out.println("测试4 - 纯英文:");
        System.out.println("  原始: " + test4);
        System.out.println("  转换: " + convertFromUS7ASCII(test4));
        System.out.println();
        
        // 实际使用时，需要从数据库读取真实的乱码数据进行测试
        System.out.println("提示：请从真实数据库读取数据进行测试");
    }
}












