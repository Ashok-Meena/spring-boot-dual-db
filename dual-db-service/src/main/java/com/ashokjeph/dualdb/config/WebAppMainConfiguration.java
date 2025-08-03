//package com.ashokjeph.dualdb.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.ByteArrayHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@EnableWebMvc
//public class WebAppMainConfiguration implements WebMvcConfigurer {
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
//        byteArrayHttpMessageConverter.setSupportedMediaTypes(getSupportedMediaTypes());
//        converters.add(byteArrayHttpMessageConverter);
////        super.configureMessageConverters(converters);
//        converters.add(new MappingJackson2HttpMessageConverter());
//    }
//
//    private List<MediaType> getSupportedMediaTypes() {
//        List<MediaType> list = new ArrayList<>();
//        list.add(MediaType.APPLICATION_PDF);
//        list.add(MediaType.IMAGE_PNG);
//        list.add(MediaType.APPLICATION_OCTET_STREAM);
//        list.add(MediaType.ALL);
//        return list;
//    }
//
//}