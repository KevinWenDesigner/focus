package com.hospital.invoice.repository;

import com.hospital.invoice.entity.view.InvoiceListView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 发票列表视图数据访问接口
 * 对应数据库视图：comm.invoice_list_view
 * 
 * 说明：
 * 1. 继承 JpaRepository 提供基础的CRUD方法
 * 2. 继承 JpaSpecificationExecutor 支持动态查询
 * 3. 视图是只读的，只能查询不能修改
 * 4. 可以使用 @Query 注解编写自定义SQL查询
 */
@Repository
public interface InvoiceListViewRepository extends JpaRepository<InvoiceListView, String>,
                                                    JpaSpecificationExecutor<InvoiceListView> {
    
    /**
     * 根据患者ID查询发票列表
     * Spring Data JPA会自动实现此方法
     * 
     * @param patientId 患者ID
     * @return 发票列表
     */
    List<InvoiceListView> findByPatientId(String patientId);
    
    /**
     * 根据患者ID和发票类型查询
     * 
     * @param patientId 患者ID
     * @param invoiceType 发票类型
     * @return 发票列表
     */
    List<InvoiceListView> findByPatientIdAndInvoiceType(String patientId, String invoiceType);
    
    /**
     * 根据患者ID和状态查询
     * 
     * @param patientId 患者ID
     * @param status 发票状态
     * @return 发票列表
     */
    List<InvoiceListView> findByPatientIdAndStatus(String patientId, String status);
    
    /**
     * 根据患者ID和时间范围查询
     * 
     * @param patientId 患者ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 发票列表
     */
    List<InvoiceListView> findByPatientIdAndCreateTimeBetween(
            String patientId, 
            LocalDateTime startTime, 
            LocalDateTime endTime);
    
    /**
     * 自定义查询示例：使用原生SQL查询
     * 如果视图结构特殊，可以直接写SQL
     * 
     * @param patientId 患者ID
     * @return 发票列表
     */
    @Query(value = "SELECT * FROM comm.invoice_list_view WHERE patient_id = :patientId ORDER BY create_time DESC", 
           nativeQuery = true)
    List<InvoiceListView> findInvoiceListByPatientId(@Param("patientId") String patientId);
    
    /**
     * 条件查询示例：使用原生SQL + 动态条件
     * 
     * @param patientId 患者ID
     * @param invoiceType 发票类型（可选）
     * @param status 发票状态（可选）
     * @return 发票列表
     */
    @Query(value = "SELECT * FROM comm.invoice_list_view " +
                   "WHERE patient_id = :patientId " +
                   "AND (:invoiceType IS NULL OR invoice_type = :invoiceType) " +
                   "AND (:status IS NULL OR status = :status) " +
                   "ORDER BY create_time DESC",
           nativeQuery = true)
    List<InvoiceListView> findInvoiceListByConditions(
            @Param("patientId") String patientId,
            @Param("invoiceType") String invoiceType,
            @Param("status") String status);
}
















