package com.hospital.invoice.service;

import com.hospital.invoice.dto.request.InvoiceDetailRequest;
import com.hospital.invoice.dto.request.InvoiceDownloadRequest;
import com.hospital.invoice.dto.request.InvoiceListRequest;
import com.hospital.invoice.dto.request.SendEmailRequest;
import com.hospital.invoice.dto.response.InvoiceDetailVO;
import com.hospital.invoice.dto.response.InvoiceListVO;
import com.hospital.invoice.dto.response.SendEmailVO;

import java.util.List;

/**
 * 发票服务接口
 * 定义发票相关的业务逻辑方法，具体实现由实现类完成
 * 
 * 说明：这是一个预留的接口框架，实际开发时需要创建实现类（如InvoiceServiceImpl）
 */
public interface InvoiceService {
    
    /**
     * 查询发票列表
     * 根据条件查询用户的电子发票列表
     * 
     * @param request 查询条件请求对象，包含患者ID、日期范围、发票类型等筛选条件
     * @return 发票列表数据，每个元素是一个发票的摘要信息
     * @throws Exception 当查询过程出现异常时抛出
     */
    List<InvoiceListVO> getInvoiceList(InvoiceListRequest request) throws Exception;
    
    /**
     * 查询发票详情
     * 根据发票ID查询电子发票的完整详细信息
     * 
     * @param request 查询请求对象，包含发票ID和患者ID（用于权限校验）
     * @return 发票详情数据，包含所有字段信息
     * @throws Exception 当发票不存在或无权访问时抛出异常
     */
    InvoiceDetailVO getInvoiceDetail(InvoiceDetailRequest request) throws Exception;
    
    /**
     * 发送发票到邮箱
     * 将电子发票PDF文件发送到指定邮箱
     * 
     * @param request 发送请求对象，包含发票ID、患者ID、邮箱地址等
     * @return 发送结果数据，包含发送状态、发送时间等信息
     * @throws Exception 当邮箱格式错误或发送失败时抛出异常
     */
    SendEmailVO sendInvoiceToEmail(SendEmailRequest request) throws Exception;
    
    /**
     * 下载发票PDF文件
     * 获取电子发票PDF文件的字节流数据
     * 
     * @param request 下载请求对象，包含发票ID和患者ID（用于权限校验）
     * @return PDF文件的字节数组，可直接返回给前端或写入响应流
     * @throws Exception 当发票不存在、PDF生成失败或无权访问时抛出异常
     */
    byte[] downloadInvoicePdf(InvoiceDownloadRequest request) throws Exception;
    
    /**
     * 校验患者是否有权访问该发票
     * 用于权限验证，确保患者只能访问自己的发票
     * 
     * @param invoiceId 发票ID
     * @param patientId 患者ID
     * @return true-有权访问，false-无权访问
     * @throws Exception 当查询过程出现异常时抛出
     */
    boolean validatePatientAccess(String invoiceId, String patientId) throws Exception;
}

















