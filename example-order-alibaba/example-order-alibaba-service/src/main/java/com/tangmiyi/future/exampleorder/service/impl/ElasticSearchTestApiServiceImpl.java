package com.tangmiyi.future.exampleorder.service.impl;


import com.tangmiyi.future.exampleorder.service.ElasticSearchApiTestService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ElasticSearchTestApiServiceImpl implements ElasticSearchApiTestService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void createIndex() throws IOException {
        // 创建索引请求
        CreateIndexRequest createdIndexRequest = new CreateIndexRequest("tang_index");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createdIndexRequest, RequestOptions.DEFAULT);
        createIndexResponse.index();
    }
}
