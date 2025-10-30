package com.hospital.invoice.controller;

import com.hospital.invoice.common.ApiResponse;
import com.hospital.invoice.dto.request.InvoiceDetailRequest;
import com.hospital.invoice.dto.request.InvoiceDownloadRequest;
import com.hospital.invoice.dto.request.InvoiceListRequest;
import com.hospital.invoice.dto.request.SendEmailRequest;
import com.hospital.invoice.dto.response.InvoiceDetailVO;
import com.hospital.invoice.dto.response.InvoiceListVO;
import com.hospital.invoice.dto.response.SendEmailVO;
import com.hospital.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 发票控制器
 * 处理所有与电子发票相关的HTTP请求
 * 
 * 注意：
 * 1. 所有接口需要在请求体中包含API网关验证参数：hospitalCode、hospitalName、tickets
 * 2. 所有接口需要在请求头中携带用户认证Token（Authorization: Bearer {token}）
 * 3. 实际使用时需要添加认证拦截器或过滤器来验证Token
 */
@RestController
@RequestMapping("/api/invoice")
@CrossOrigin // 支持跨域请求
public class InvoiceController {
    
    /**
     * 注入发票服务
     * 注意：实际使用时需要有InvoiceService的实现类（如InvoiceServiceImpl）
     */
    @Autowired
    private InvoiceService invoiceService;
    
    /**
     * 验证API网关必需参数
     * 
     * @param request 基础请求对象
     * @param errorMessage 验证失败时的错误信息（用于区分不同接口）
     * @return 验证失败返回错误信息，验证通过返回null
     */
    private String validateGatewayParams(com.hospital.invoice.dto.request.BaseRequest request, String errorMessage) {
        if (request.getHospitalCode() == null || request.getHospitalCode().trim().isEmpty()) {
            return "医院编码不能为空";
        }
        if (request.getHospitalName() == null || request.getHospitalName().trim().isEmpty()) {
            return "医院名称不能为空";
        }
        if (request.getTickets() == null || request.getTickets().trim().isEmpty()) {
            return "API网关票据不能为空";
        }
        // TODO: 实际应用中，这里需要调用网关服务验证tickets的合法性
        // boolean isValid = gatewayService.validateTickets(request.getTickets(), request.getHospitalCode());
        // if (!isValid) {
        //     return "API网关票据验证失败";
        // }
        return null;
    }
    
    /**
     * 1. 发票列表查询接口
     * 接口路径：/api/invoice/list
     * 请求方式：GET 或 POST
     * 
     * @param request 查询条件请求对象
     * @return 统一响应格式，包含发票列表数据
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST}, 
                    produces = "application/json;charset=UTF-8")
    public ApiResponse<List<InvoiceListVO>> getInvoiceList(
            @RequestBody(required = false) InvoiceListRequest requestBody,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String invoiceType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            // 支持两种方式：POST的JSON body 或 GET的URL参数
            InvoiceListRequest request = requestBody != null ? requestBody : new InvoiceListRequest();
            
            // 如果是GET请求，从URL参数设置
            if (requestBody == null && patientId != null) {
                request.setPatientId(patientId);
                request.setPatientName(patientName);
                request.setPhoneNumber(phoneNumber);
                request.setInvoiceType(invoiceType);
                request.setStatus(status);
                request.setStartDate(startDate);
                request.setEndDate(endDate);
            }
            
            // API网关参数验证
            String gatewayError = validateGatewayParams(request, "发票列表查询");
            if (gatewayError != null) {
                return ApiResponse.error(400, gatewayError);
            }
            
            // 参数校验：患者ID为必填项
            if (request.getPatientId() == null || request.getPatientId().trim().isEmpty()) {
                return ApiResponse.error(400, "患者ID不能为空");
            }
            
            // 调用服务层方法查询发票列表
            List<InvoiceListVO> invoiceList = invoiceService.getInvoiceList(request);
            
            // 返回成功响应
            return ApiResponse.success("查询成功", invoiceList);
            
        } catch (Exception e) {
            // 异常处理：记录日志并返回错误响应
            // TODO: 添加日志记录
            e.printStackTrace();
            return ApiResponse.error(500, "查询失败：" + e.getMessage());
        }
    }
    
    /**
     * 2. 发票详情查询接口
     * 接口路径：/api/invoice/detail
     * 请求方式：GET 或 POST
     * 
     * @param request 查询请求对象，包含发票ID和患者ID
     * @return 统一响应格式，包含发票详情数据
     */
    @RequestMapping(value = "/detail", method = {RequestMethod.GET, RequestMethod.POST},
                    produces = "application/json;charset=UTF-8")
    public ApiResponse<InvoiceDetailVO> getInvoiceDetail(@RequestBody(required = false) InvoiceDetailRequest request) {
        try {
            // 请求对象为空检查
            if (request == null) {
                return ApiResponse.error(400, "请求参数不能为空");
            }
            
            // API网关参数验证
            String gatewayError = validateGatewayParams(request, "发票详情查询");
            if (gatewayError != null) {
                return ApiResponse.error(400, gatewayError);
            }
            
            // 参数校验：发票ID和患者ID为必填项
            if (request.getInvoiceId() == null || request.getInvoiceId().trim().isEmpty()) {
                return ApiResponse.error(400, "发票ID不能为空");
            }
            if (request.getPatientId() == null || request.getPatientId().trim().isEmpty()) {
                return ApiResponse.error(400, "患者ID不能为空");
            }
            
            // 权限校验：验证患者是否有权访问该发票
            boolean hasAccess = invoiceService.validatePatientAccess(request.getInvoiceId(), request.getPatientId());
            if (!hasAccess) {
                return ApiResponse.error(403, "无权访问该发票");
            }
            
            // 调用服务层方法查询发票详情
            InvoiceDetailVO invoiceDetail = invoiceService.getInvoiceDetail(request);
            
            // 发票不存在的情况
            if (invoiceDetail == null) {
                return ApiResponse.error(404, "发票不存在");
            }
            
            // 返回成功响应
            return ApiResponse.success("查询成功", invoiceDetail);
            
        } catch (Exception e) {
            // 异常处理
            e.printStackTrace();
            return ApiResponse.error(500, "查询失败：" + e.getMessage());
        }
    }
    
    /**
     * 3. 发票发送邮箱接口
     * 接口路径：/api/invoice/sendEmail
     * 请求方式：POST
     * 
     * @param request 发送邮件请求对象
     * @return 统一响应格式，包含发送结果数据
     */
    @PostMapping(value = "/sendEmail", produces = "application/json;charset=UTF-8")
    public ApiResponse<SendEmailVO> sendInvoiceToEmail(@RequestBody SendEmailRequest request) {
        try {
            // 请求对象为空检查
            if (request == null) {
                return ApiResponse.error(400, "请求参数不能为空");
            }
            
            // API网关参数验证
            String gatewayError = validateGatewayParams(request, "发送邮件");
            if (gatewayError != null) {
                return ApiResponse.error(400, gatewayError);
            }
            
            // 参数校验
            if (request.getInvoiceId() == null || request.getInvoiceId().trim().isEmpty()) {
                return ApiResponse.error(400, "发票ID不能为空");
            }
            if (request.getPatientId() == null || request.getPatientId().trim().isEmpty()) {
                return ApiResponse.error(400, "患者ID不能为空");
            }
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ApiResponse.error(400, "邮箱地址不能为空");
            }
            
            // 邮箱格式校验（简单验证，实际可使用正则表达式进行更严格的校验）
            if (!request.getEmail().contains("@")) {
                return ApiResponse.error(30001, "邮箱格式错误");
            }
            
            // 权限校验
            boolean hasAccess = invoiceService.validatePatientAccess(request.getInvoiceId(), request.getPatientId());
            if (!hasAccess) {
                return ApiResponse.error(403, "无权访问该发票");
            }
            
            // 调用服务层方法发送邮件
            SendEmailVO result = invoiceService.sendInvoiceToEmail(request);
            
            // 返回成功响应
            return ApiResponse.success("发送成功", result);
            
        } catch (Exception e) {
            // 异常处理
            e.printStackTrace();
            return ApiResponse.error(30002, "邮件发送失败：" + e.getMessage());
        }
    }
    
    /**
     * 4. 发票查看/下载接口
     * 接口路径：/api/invoice/download
     * 请求方式：GET
     * 
     * 说明：此接口直接返回PDF文件流，不使用统一的ApiResponse格式
     * 
     * @param invoiceId 发票ID（URL参数）
     * @param patientId 患者ID（URL参数，用于权限校验）
     * @param hospitalCode 医院编码（URL参数，必填）
     * @param hospitalName 医院名称（URL参数，必填）
     * @param tickets API网关票据（URL参数，必填）
     * @return PDF文件流或错误信息
     */
    @GetMapping("/download")
    public ResponseEntity<?> downloadInvoice(
            @RequestParam("invoiceId") String invoiceId,
            @RequestParam("patientId") String patientId,
            @RequestParam("hospitalCode") String hospitalCode,
            @RequestParam("hospitalName") String hospitalName,
            @RequestParam("tickets") String tickets) {
        try {
            // 创建请求对象用于参数验证
            InvoiceDownloadRequest request = new InvoiceDownloadRequest(invoiceId, patientId);
            request.setHospitalCode(hospitalCode);
            request.setHospitalName(hospitalName);
            request.setTickets(tickets);
            
            // API网关参数验证
            String gatewayError = validateGatewayParams(request, "下载发票");
            if (gatewayError != null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, gatewayError));
            }
            
            // 参数校验
            if (invoiceId == null || invoiceId.trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "发票ID不能为空"));
            }
            if (patientId == null || patientId.trim().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error(400, "患者ID不能为空"));
            }
            
            // 权限校验
            boolean hasAccess = invoiceService.validatePatientAccess(invoiceId, patientId);
            if (!hasAccess) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.error(403, "无权访问该发票"));
            }
            
            // 调用服务层方法获取PDF文件字节流
            byte[] pdfBytes = invoiceService.downloadInvoicePdf(request);
            
            // PDF文件不存在的情况
            if (pdfBytes == null || pdfBytes.length == 0) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "发票PDF文件不存在"));
            }
            
            // 设置响应头，告诉浏览器这是一个PDF文件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // 设置文件名（可以根据实际情况调整）
            headers.setContentDispositionFormData("attachment", "invoice_" + invoiceId + ".pdf");
            headers.setContentLength(pdfBytes.length);
            
            // 返回PDF文件流
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            // 异常处理：返回JSON格式的错误信息
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(500, "下载失败：" + e.getMessage()));
        }
    }
}



