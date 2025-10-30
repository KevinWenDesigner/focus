package com.hospital.invoice.repository;

import com.hospital.invoice.entity.view.InvoiceListViewSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 简化版Repository - 用于测试
 */
@Repository
public interface InvoiceListViewSimpleRepository extends JpaRepository<InvoiceListViewSimple, String> {
    
    List<InvoiceListViewSimple> findByPatientId(String patientId);
}

















