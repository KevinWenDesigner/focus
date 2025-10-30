package com.hospital.invoice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Web配置类
 * 用于解决HTTP响应中文乱码问题
 * 
 * 乱码原因：
 * - Spring Boot返回JSON时，如果没有明确指定字符编码
 * - 浏览器可能用错误的编码（如ISO-8859-1）解析UTF-8的字节
 * - 导致中文显示为乱码
 * 
 * 解决方法：
 * - 配置HTTP消息转换器，强制使用UTF-8编码
 * - 确保所有HTTP响应都明确声明charset=UTF-8
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * 配置HTTP消息转换器
     * 设置字符串转换器使用UTF-8编码
     * 
     * @param converters Spring MVC的消息转换器列表
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建字符串转换器，并设置为UTF-8编码
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        
        // 设置为writeAcceptCharset为false，避免响应头中出现多余的Accept-Charset
        stringConverter.setWriteAcceptCharset(false);
        
        // 将配置好的转换器添加到转换器列表的最前面（优先使用）
        converters.add(0, stringConverter);
    }
}





