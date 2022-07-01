package com.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class EsConfig {

	@Bean
	ElasticsearchClient getClient() {
		RestClient restClient = RestClient
				.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")).build();
		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
		ElasticsearchClient esClient = new ElasticsearchClient(transport);
		return esClient;
	}

}
