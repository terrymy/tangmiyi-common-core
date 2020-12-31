package com.tangmiyi.future.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * EnableWebMvc 解决无法通过网关访问actuator问题(http://localhost:9191/api/config/actuator)
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverters());
        super.configureMessageConverters(converters);
    }

    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverters(){

        //1.定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2:添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
            SerializerFeature.MapSortField,
            // null值是否输出
            SerializerFeature.WriteMapNullValue,
            //SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.WriteDateUseDateFormat,
            //SerializerFeature.WriteNonStringValueAsString,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullStringAsEmpty,
            //SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.PrettyFormat
        );

        //3:处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        //4:在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return fastJsonHttpMessageConverter;
    }
}