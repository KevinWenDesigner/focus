package com.hospital.invoice.service.impl;

import com.hospital.invoice.dto.request.InvoiceDetailRequest;
import com.hospital.invoice.dto.request.InvoiceDownloadRequest;
import com.hospital.invoice.dto.request.InvoiceListRequest;
import com.hospital.invoice.dto.request.SendEmailRequest;
import com.hospital.invoice.dto.response.InvoiceDetailVO;
import com.hospital.invoice.dto.response.InvoiceListVO;
import com.hospital.invoice.dto.response.SendEmailVO;
import com.hospital.invoice.entity.view.InvoiceDetailView;
import com.hospital.invoice.entity.view.InvoiceListView;
import com.hospital.invoice.entity.view.InvoiceListViewSimple;
import com.hospital.invoice.repository.InvoiceDetailViewRepository;
import com.hospital.invoice.repository.InvoiceListViewRepository;
import com.hospital.invoice.repository.InvoiceListViewSimpleRepository;
import com.hospital.invoice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 发票服务实现类
 * 
 * 数据来源说明：
 * 1. 发票列表数据来自视图：comm.invoice_list_view
 * 2. 发票详情数据来自视图：comm.invoice_detail_view
 * 3. 视图已经处理了数据脱敏和格式化，可以直接使用
 * 
 * 使用方式：
 * 1. 确保数据库中存在这两个视图
 * 2. 配置好数据库连接（application.properties）
 * 3. 启动项目后会自动连接数据库查询
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {
    
    /**
     * 注入发票列表视图数据访问层
     * 用于查询发票列表数据
     */
    @Autowired
    private InvoiceListViewRepository invoiceListViewRepository;
    
    /**
     * 简化版Repository - 用于测试
     */
    @Autowired
    private InvoiceListViewSimpleRepository invoiceListViewSimpleRepository;
    
    /**
     * 注入发票详情视图数据访问层
     * 用于查询发票详情数据
     */
    @Autowired
    private InvoiceDetailViewRepository invoiceDetailViewRepository;
    
    /**
     * 注入 EntityManager 用于原生SQL查询
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    // TODO: 注入邮件服务（用于发送发票到邮箱功能）
    // @Autowired
    // private JavaMailSender mailSender;
    
    
    /**
     * 查询发票列表
     * 从视图 comm.invoice_list_view 查询数据
     */
    @Override
    public List<InvoiceListVO> getInvoiceList(InvoiceListRequest request) throws Exception {
        // 步骤1：构建动态查询条件
        Specification<InvoiceListView> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 必填条件：患者ID
            predicates.add(cb.equal(root.get("patientId"), request.getPatientId()));
            
            // 可选条件：患者姓名（模糊查询）
            if (request.getPatientName() != null && !request.getPatientName().trim().isEmpty()) {
                predicates.add(cb.like(root.get("patientName"), "%" + request.getPatientName() + "%"));
            }
            
            // 可选条件：手机号码
            if (request.getPhoneNumber() != null && !request.getPhoneNumber().trim().isEmpty()) {
                predicates.add(cb.like(root.get("phoneNumber"), "%" + request.getPhoneNumber() + "%"));
            }
            
            // 可选条件：发票类型
            if (request.getInvoiceType() != null && !request.getInvoiceType().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("invoiceType"), request.getInvoiceType()));
            }
            
            // 可选条件：发票状态
            if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
                predicates.add(cb.equal(root.get("status"), request.getStatus()));
            }
            
            // 可选条件：开始日期
            if (request.getStartDate() != null && !request.getStartDate().trim().isEmpty()) {
                LocalDate startDate = LocalDate.parse(request.getStartDate());
                predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), startDate.atStartOfDay()));
            }
            
            // 可选条件：结束日期
            if (request.getEndDate() != null && !request.getEndDate().trim().isEmpty()) {
                LocalDate endDate = LocalDate.parse(request.getEndDate());
                predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), endDate.plusDays(1).atStartOfDay()));
            }
            
            // 组合所有条件（AND关系）
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        
        // 临时使用简化版测试
        System.out.println("=== 使用简化版测试 ===");
        List<InvoiceListViewSimple> simpleList = invoiceListViewSimpleRepository.findByPatientId(request.getPatientId());
        System.out.println("简化版查询到的记录数: " + (simpleList != null ? simpleList.size() : 0));
        
        if (simpleList != null && !simpleList.isEmpty()) {
            for (int i = 0; i < simpleList.size(); i++) {
                InvoiceListViewSimple simple = simpleList.get(i);
                if (simple != null) {
                    System.out.println("简化版数据[" + i + "] - " +
                                     "收据号: " + simple.getRcptNo() + 
                                     ", 发票ID: " + simple.getInvoiceId() + 
                                     ", 患者: " + simple.getPatientName() + 
                                     ", 发票号: " + simple.getInvoiceNo());
                } else {
                    System.out.println("警告：列表中索引[" + i + "]的对象为null");
                }
            }
        }
        
        // 使用原生SQL测试 - 查询所有字段
        System.out.println("=== 使用原生SQL测试（查询所有字段）===");
        try {
            String nativeSql = "SELECT * FROM COMM.INVOICE_LIST_VIEW WHERE PATIENT_ID = ? AND ROWNUM <= 2";
            Query query = entityManager.createNativeQuery(nativeSql);
            query.setParameter(1, request.getPatientId());
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            System.out.println("原生SQL查询到的记录数: " + results.size());
            if (!results.isEmpty()) {
                Object[] firstRow = results.get(0);
                System.out.println("第一条记录的字段数量: " + firstRow.length);
                for (int j = 0; j < firstRow.length; j++) {
                    Object value = firstRow[j];
                    String type = (value != null) ? value.getClass().getSimpleName() : "null";
                    System.out.println("  字段[" + j + "]: " + value + " (类型: " + type + ")");
                }
            }
        } catch (Exception e) {
            System.err.println("原生SQL查询失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        // 查询视图列信息
        System.out.println("=== 查询视图列定义 ===");
        try {
            String columnSql = "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_ID " +
                             "FROM ALL_TAB_COLUMNS " +
                             "WHERE OWNER = 'COMM' AND TABLE_NAME = 'INVOICE_LIST_VIEW' " +
                             "ORDER BY COLUMN_ID";
            Query columnQuery = entityManager.createNativeQuery(columnSql);
            @SuppressWarnings("unchecked")
            List<Object[]> columns = columnQuery.getResultList();
            
            System.out.println("视图列数: " + columns.size());
            for (Object[] col : columns) {
                System.out.println("  列: " + col[0] + " (类型: " + col[1] + ", 顺序: " + col[2] + ")");
            }
        } catch (Exception e) {
            System.err.println("查询列信息失败: " + e.getMessage());
        }
        
        // 步骤2：查询数据库视图
        List<InvoiceListView> viewList = invoiceListViewRepository.findAll(spec);
        
        // 添加日志：查看查询结果
        System.out.println("完整版查询到的记录数: " + (viewList != null ? viewList.size() : 0));
        
        // 步骤3：将视图实体转换为VO对象
        List<InvoiceListVO> resultList = new ArrayList<>();
        
        if (viewList == null || viewList.isEmpty()) {
            return resultList;  // 如果没有数据，直接返回空列表
        }
        
        for (InvoiceListView view : viewList) {
            try {
                // 添加日志：查看每条记录
                System.out.println("处理记录 - 收据号: " + (view != null ? view.getRcptNo() : "null") + 
                                 ", 发票ID: " + (view != null ? view.getInvoiceId() : "null"));
                
                if (view == null) {
                    System.out.println("警告：视图对象为null，跳过");
                    continue;
                }
                
                InvoiceListVO vo = new InvoiceListVO();
                
                // 使用安全的方式设置每个字段
                vo.setRcptNo(view.getRcptNo());  // 收据号（主键）
                vo.setInvoiceId(view.getInvoiceId());  // 发票ID（可能为null）
                vo.setInvoiceNo(view.getInvoiceNo());  // 发票号（可能为null）
                vo.setInvoiceType(view.getInvoiceType());
                
                // ⭐ getter已经自动转换了，直接使用
                vo.setInvoiceTypeLabel(view.getInvoiceTypeLabel());
                
                // 金额（添加空值检查）
                if (view.getAmount() != null) {
                    vo.setAmount(view.getAmount().toString());
                } else {
                    System.out.println("警告：发票金额为null");
                }
                
                // 格式化时间（添加空值检查）
                if (view.getCreateTime() != null) {
                    // 将Date转换为字符串
                    java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    vo.setCreateTime(sdf1.format(view.getCreateTime()));
                    vo.setDisplayTime(sdf2.format(view.getCreateTime()));
                } else {
                    System.out.println("警告：创建时间为null");
                }
                
                vo.setStatus(view.getStatus());
                
                // ⭐ getter已经自动转换了，直接使用
                vo.setStatusLabel(view.getStatusLabel());
                
                // ⭐ getter已经自动转换了，直接使用
                vo.setPatientName(view.getPatientName());
                
                // 手机号码（视图中已经脱敏，直接使用）
                vo.setPhoneNumber(view.getPhoneNumber());
                
                resultList.add(vo);
                
            } catch (Exception e) {
                System.err.println("转换发票数据时出错: " + e.getMessage());
                e.printStackTrace();
                // 跳过这条记录，继续处理下一条
                continue;
            }
        }
        
        return resultList;
    }
    
    /**
     * 查询发票详情
     * 从视图 comm.invoice_detail_view 查询数据
     */
    @Override
    public InvoiceDetailVO getInvoiceDetail(InvoiceDetailRequest request) throws Exception {
        // 步骤1：从视图查询发票详情（带权限校验）
        Optional<InvoiceDetailView> viewOpt = invoiceDetailViewRepository
                .findByInvoiceIdAndPatientId(request.getInvoiceId(), request.getPatientId());
        
        // 步骤2：检查发票是否存在
        if (!viewOpt.isPresent()) {
            throw new Exception("发票不存在或无权访问");
        }
        
        InvoiceDetailView view = viewOpt.get();
        
        // 步骤3：将视图实体转换为VO对象
        InvoiceDetailVO vo = new InvoiceDetailVO();
        vo.setRcptNo(view.getRcptNo());  // 收据号（主键）
        vo.setInvoiceId(view.getInvoiceId());  // 发票ID（可能为null）
        vo.setInvoiceNo(view.getInvoiceNo());  // 发票号（可能为null）
        vo.setInvoiceType(view.getInvoiceType());
        
        // ⭐ getter已经自动转换了，直接使用
        vo.setInvoiceTypeLabel(view.getInvoiceTypeLabel());
        
        // 金额（添加空值检查）
        if (view.getAmount() != null) {
            vo.setAmount(view.getAmount().toString());
        }
        
        // 格式化时间（添加空值检查）
        if (view.getCreateTime() != null) {
            java.text.SimpleDateFormat timeFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setCreateTime(timeFormatter.format(view.getCreateTime()));
        }
        
        vo.setStatus(view.getStatus());
        
        // ⭐ getter已经自动转换了，直接使用
        vo.setStatusLabel(view.getStatusLabel());
        
        // ⭐ getter已经自动转换了，直接使用
        vo.setPatientName(view.getPatientName());
        vo.setPhoneNumber(view.getPhoneNumber());
        
        // 身份证号（视图中已经脱敏，直接使用）
        vo.setIdCard(view.getIdCard());
        
        // 业务日期（添加空值检查）
        if (view.getBusinessDate() != null) {
            java.text.SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
            vo.setBusinessDate(dateFormatter.format(view.getBusinessDate()));
        }
        
        // ⭐ getter已经自动转换了，直接使用
        vo.setDeptOrPackage(view.getDeptOrPackage());
        vo.setInvoiceCode(view.getInvoiceCode());
        vo.setMachineCode(view.getMachineCode());
        vo.setCheckCode(view.getCheckCode());
        vo.setPdfUrl(view.getPdfUrl());
        vo.setQrCodeUrl(view.getQrCodeUrl());
        
        // 金额信息（添加空值检查）
        if (view.getTotalAmount() != null) {
            vo.setTotalAmount(view.getTotalAmount().toString());
        }
        if (view.getSelfPayAmount() != null) {
            vo.setSelfPayAmount(view.getSelfPayAmount().toString());
        }
        if (view.getInsuranceAmount() != null) {
            vo.setInsuranceAmount(view.getInsuranceAmount().toString());
        }
        
        // ⭐ getter已经自动转换了，直接使用
        vo.setRemark(view.getRemark());
        
        return vo;
    }
    
    /**
     * 发送发票到邮箱
     * 
     * TODO: 邮件发送功能需要配置SMTP服务器后才能使用
     * 当前返回模拟的成功响应
     */
    @Override
    public SendEmailVO sendInvoiceToEmail(SendEmailRequest request) throws Exception {
        // 步骤1：验证发票是否存在（带权限校验）
        Optional<InvoiceDetailView> viewOpt = invoiceDetailViewRepository
                .findByInvoiceIdAndPatientId(request.getInvoiceId(), request.getPatientId());
        
        if (!viewOpt.isPresent()) {
            throw new Exception("发票不存在或无权访问");
        }
        
        InvoiceDetailView view = viewOpt.get();
        
        // 步骤2：获取PDF文件URL
        String pdfUrl = view.getPdfUrl();
        if (pdfUrl == null || pdfUrl.isEmpty()) {
            throw new Exception("PDF文件不存在");
        }
        
        // TODO: 实际的邮件发送实现
        /*
        // 1. 下载PDF文件
        byte[] pdfBytes = downloadPdfFromUrl(pdfUrl);
        
        // 2. 构建邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(request.getEmail());
        helper.setSubject(request.getEmailTitle() != null ? request.getEmailTitle() : "电子发票");
        helper.setText("尊敬的" + view.getPatientName() + "，您的电子发票请查收。");
        
        // 3. 添加附件
        helper.addAttachment("发票_" + view.getInvoiceNo() + ".pdf", 
                           new ByteArrayResource(pdfBytes));
        
        // 4. 发送邮件
        mailSender.send(message);
        */
        
        // 当前返回模拟的成功响应（实际发送邮件功能需要配置SMTP）
        SendEmailVO vo = new SendEmailVO();
        vo.setSendStatus("SUCCESS");
        
        // 格式化当前时间
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setSendTime(sdf.format(new java.util.Date()));
        vo.setEmail(request.getEmail());
        
        System.out.println("提示：邮件发送功能需要配置SMTP服务器，当前返回模拟成功响应");
        
        return vo;
    }
    
    /**
     * 下载发票PDF
     * 从发票详情视图获取PDF URL，然后读取文件
     */
    @Override
    public byte[] downloadInvoicePdf(InvoiceDownloadRequest request) throws Exception {
        // 步骤1：查询发票详情（带权限校验）
        Optional<InvoiceDetailView> viewOpt = invoiceDetailViewRepository
                .findByInvoiceIdAndPatientId(request.getInvoiceId(), request.getPatientId());
        
        if (!viewOpt.isPresent()) {
            throw new Exception("发票不存在或无权访问");
        }
        
        InvoiceDetailView view = viewOpt.get();
        
        // 步骤2：获取PDF文件路径
        String pdfUrl = view.getPdfUrl();
        if (pdfUrl == null || pdfUrl.isEmpty()) {
            throw new Exception("PDF文件不存在");
        }
        
        // 步骤3：读取PDF文件
        // TODO: 根据实际PDF存储方式实现
        /*
        // 方式1：如果PDF存储在本地文件系统
        Path path = Paths.get(pdfUrl);
        if (!Files.exists(path)) {
            throw new Exception("PDF文件不存在");
        }
        byte[] pdfBytes = Files.readAllBytes(path);
        return pdfBytes;
        
        // 方式2：如果PDF存储在远程服务器（HTTP/HTTPS）
        URL url = new URL(pdfUrl);
        InputStream in = url.openStream();
        byte[] pdfBytes = IOUtils.toByteArray(in);
        in.close();
        return pdfBytes;
        
        // 方式3：如果PDF存储在对象存储（如阿里云OSS、MinIO等）
        // 使用对应的SDK下载文件
        */
        
        throw new Exception("PDF下载功能需要根据实际PDF存储方式实现，请在InvoiceServiceImpl中配置");
    }
    
    /**
     * 校验患者访问权限
     * 查询发票详情视图，检查患者ID是否匹配
     */
    @Override
    public boolean validatePatientAccess(String invoiceId, String patientId) throws Exception {
        // 尝试查询发票（带患者ID条件）
        Optional<InvoiceDetailView> viewOpt = invoiceDetailViewRepository
                .findByInvoiceIdAndPatientId(invoiceId, patientId);
        
        // 如果能查到，说明有权限；查不到说明发票不存在或患者ID不匹配
        return viewOpt.isPresent();
    }
    
    // ========================================
    // 工具方法
    // ========================================
    
    /**
     * 根据状态代码获取状态标签
     * @param statusCode 状态代码
     * @return 状态标签（中文）
     */
    private String getStatusLabel(String statusCode) {
        if (statusCode == null || statusCode.trim().isEmpty()) {
            return "";
        }
        com.hospital.invoice.enums.InvoiceStatus status = 
            com.hospital.invoice.enums.InvoiceStatus.fromCode(statusCode);
        return status != null ? status.getLabel() : statusCode;
    }
    
    /**
     * 根据发票类型代码获取类型标签
     * @param typeCode 发票类型代码
     * @return 类型标签（中文）
     */
    private String getInvoiceTypeLabel(String typeCode) {
        if (typeCode == null || typeCode.trim().isEmpty()) {
            return "";
        }
        com.hospital.invoice.enums.InvoiceType type = 
            com.hospital.invoice.enums.InvoiceType.fromCode(typeCode);
        return type != null ? type.getLabel() : typeCode;
    }
}

