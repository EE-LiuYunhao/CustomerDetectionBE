package com.ivm.CustomerDetect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebAppConfiguration extends WebMvcConfigurationSupport
{
    @Value("${static.img}")
    private String imgFileFolder;

    @Value("${static.encodedFace}")
    private String encodedFaceFolder;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+imgFileFolder);
        registry.addResourceHandler("/face/**").addResourceLocations("file:"+encodedFaceFolder);
	}
}