package com.tangmiyi.future.exampleorder.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// xxxAutoConfiguaion xxxProperties
@Configuration
@Slf4j
public class ElasticSearchClientConfig {

    @Value("${elasticsearch.host:localhost}")
    private String elasticsearchHost;

    @Value("${elasticsearch.port:9200}")
    private Integer elasticsearchPort;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
           new HttpHost(elasticsearchHost,elasticsearchPort,"http")));
        return client;
    }
}