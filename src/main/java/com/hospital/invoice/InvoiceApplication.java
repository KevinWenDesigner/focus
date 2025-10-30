package com.hospital.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 电子发票系统主启动类
 * 
 * @SpringBootApplication 注解说明：
 * 这是一个组合注解，包含了以下三个注解的功能：
 * 1. @Configuration：表示这是一个配置类
 * 2. @EnableAutoConfiguration：启用Spring Boot的自动配置机制
 * 3. @ComponentScan：启用组件扫描，自动发现和注册Bean
 * 
 * 使用说明：
 * 1. 直接运行此类的main方法即可启动整个应用
 * 2. 默认启动后访问地址为：http://localhost:8080
 * 3. 接口访问路径示例：http://localhost:8080/api/invoice/list
 */
@SpringBootApplication
public class InvoiceApplication {
    
    /**
     * 应用程序入口方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(InvoiceApplication.class, args);
        
        // 打印启动成功信息
        System.out.println("\n========================================");
        System.out.println("  HIS电子发票系统启动成功！");
        System.out.println("  访问地址：http://localhost:8080");
        System.out.println("  接口文档见：README.md");
        System.out.println("========================================\n");
    }
}
















