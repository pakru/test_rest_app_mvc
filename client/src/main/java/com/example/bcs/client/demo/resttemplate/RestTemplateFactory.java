package com.example.bcs.client.demo.resttemplate;

import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {

    private RestTemplate restTemplate;

    @Value("${client.host:localhost}")
    private String host;

    @Value("${client.protocol:http}")
    private String protocol;

    @Value("${client.port:80}")
    private int port;

    @Value("${client.login:admin}")
    private String login;

    @Value("${client.password:password}")
    private String password;

    @Override
    public RestTemplate getObject() throws Exception {
        return restTemplate;
    }

    @Override
    public Class<?> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        HttpHost httpHost = new HttpHost(host, port, protocol);

        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(httpHost));
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(login, password));
    }
}
