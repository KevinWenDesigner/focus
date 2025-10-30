package com.hospital.invoice.repository;

import com.hospital.invoice.entity.view.InvoiceDetailView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 发票详情视图数据访问接口
 * 对应数据库视图：comm.invoice_detail_view
 * 
 * 说明：
 * 1. 继承 JpaRepository 提供基础的CRUD方法
 * 2. 视图是只读的，只能查询不能修改
 * 3. 主要用于根据发票ID查询详情
 */
@Repository
public interface InvoiceDetailViewRepository extends JpaRepository<InvoiceDetailView, String> {
    
    /**
     * 根据发票ID查询详情
     * Spring Data JPA会自动实现此方法
     * 
     * @param invoiceId 发票ID
     * @return 发票详情（Optional包装，可能为空）
     */
    Optional<InvoiceDetailView> findByInvoiceId(String invoiceId);
    
    /**
     * 根据发票ID和患者ID查询详情（带权限校验）
     * 确保患者只能查看自己的发票
     * 
     * @param invoiceId 发票ID
     * @param patientId 患者ID
     * @return 发票详情
     */
    Optional<InvoiceDetailView> findByInvoiceIdAndPatientId(String invoiceId, String patientId);
    
    /**
     * 自定义查询示例：使用原生SQL查询
     * 
     * @param invoiceId 发票ID
     * @return 发票详情
     */
    @Query(value = "SELECT * FROM comm.invoice_detail_view WHERE invoice_id = :invoiceId", 
           nativeQuery = true)
    Optional<InvoiceDetailView> findInvoiceDetailById(@Param("invoiceId") String invoiceId);
    
    /**
     * 带权限校验的查询：使用原生SQL
     * 
     * @param invoiceId 发票ID
     * @param patientId 患者ID
     * @return 发票详情
     */
    @Query(value = "SELECT * FROM comm.invoice_detail_view " +
                   "WHERE invoice_id = :invoiceId AND patient_id = :patientId", 
           nativeQuery = true)
    Optional<InvoiceDetailView> findInvoiceDetailByIdAndPatientId(
            @Param("invoiceId") String invoiceId,
            @Param("patientId") String patientId);
}
















