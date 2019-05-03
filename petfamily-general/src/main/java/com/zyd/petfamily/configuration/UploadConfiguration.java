package com.zyd.petfamily.configuration;

import com.zyd.petfamily.utils.CodeUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @program: petfamily
 * @author: zyd
 * @description: 静态文件虚拟路径映射到绝对路径
 * @create: 2019-04-28 16:13
 */

@Configuration
public class UploadConfiguration extends WebMvcConfigurationSupport{

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + CodeUtil.PIC_URL);
    }
}
