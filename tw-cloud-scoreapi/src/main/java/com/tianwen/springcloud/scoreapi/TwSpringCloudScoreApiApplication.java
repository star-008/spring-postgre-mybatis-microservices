package com.tianwen.springcloud.scoreapi;

import com.tianwen.springcloud.commonapi.utils.BeanHolder;
import com.tianwen.springcloud.scoreapi.util.CORSFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.tianwen.springcloud.**.**.swagger2", "com.tianwen.springcloud.**.**.controller", "com.tianwen.springcloud.**.**.handler", "com.tianwen.springcloud.**.**.session",
    "com.tianwen.springcloud.**.**.service", "com.tianwen.springcloud.**.**.dao", "com.tianwen.springcloud.**.**.log",
    "com.tianwen.springcloud.**.**.configuration", "com.tianwen.springcloud.commonapi",
    "com.tianwen.springcloud.**.**.task"})
@EnableFeignClients(basePackages = {"com.tianwen.springcloud.**.**.**"})
@Import(value = {BeanHolder.class})
public class TwSpringCloudScoreApiApplication
{
    
    public static void main(String[] args)
    {
        SpringApplication.run(TwSpringCloudScoreApiApplication.class, args);
        System.out.println("TwSpringCloudECRapiApplication after");
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new CORSFilter());
        return bean;
    }

    @Bean
    feign.Request.Options feignRequestOptions() {
        int readTimeoutMills = 60 * 60 * 1000;
        int connectTimeoutMills = 60 * 60 * 1000;
        return new feign.Request.Options(connectTimeoutMills, readTimeoutMills);
    }
}
