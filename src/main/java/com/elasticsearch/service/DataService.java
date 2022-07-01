package com.elasticsearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elasticsearch.model.Product;
import com.elasticsearch.util.DataUtil;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.DeleteByQueryResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateByQueryRequest;
import co.elastic.clients.elasticsearch.core.UpdateByQueryResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataService {
	@Autowired
	ElasticsearchClient esClient;
	
	public void insertData() {
		Product product = new Product("1", "City bike", 123.0);
		try {
			IndexResponse response = esClient.index(i -> i.index("productlist").id(product.getId()).document(product));
			if(response!=null) {
				log.info("response: "+response.id());
			}
		} catch (Exception e) {			
			e.printStackTrace();
		} 
	}
	
	public void insertDataBulk() {
		try {
		    List<Product> products = DataUtil.getExcelData();
			BulkRequest.Builder br = new BulkRequest.Builder();
			for (Product product : products) {
				br.operations(op -> op.index(idx -> idx.index("productlist").id(product.getId()).document(product)));
			}
			BulkResponse result;
			result = esClient.bulk(br.build());
			// Log errors, if any
			if (result.errors()) {
				// logger.error("Bulk had errors");
				for (BulkResponseItem item : result.items()) {
					if (item.error() != null) {
						System.out.println(item.error().reason());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateData() {
		try {
			Script inline=Script.of(s->s.inline(i->i.source("proudctlist").source("ctx._source.name='Test Update Data Case'")));
			UpdateByQueryRequest request=UpdateByQueryRequest.of( m->m.query(q -> q.term(t -> t.field("id").value("1"))).script(inline).index("productlist"));
			UpdateByQueryResponse response=esClient.updateByQuery(request);
			log.info("Update"+response.failures().size());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	
		
	}
	
	public void updateDataBulk() {
		
	}
	
	public void deleteData() {
		DeleteByQueryRequest request=DeleteByQueryRequest.of( m->m.query(q -> q.matchPhrase(t -> t.field("name").query("4 Dragon Plastic Toys"))).index("productlist"));
		try {
		DeleteByQueryResponse response=esClient.deleteByQuery(request);
		log.info("Delete"+response.failures().size());
		} catch (Exception e) {
			log.error("error");
		} 
	}
	
	public void deleteDataById() {
		DeleteByQueryRequest request=DeleteByQueryRequest.of( m->m.query(q -> q.term(t -> t.field("id").value("1.0"))).index("productlist"));
		try {
		DeleteByQueryResponse response=esClient.deleteByQuery(request);
		log.info("Delete"+response.failures().size());
		} catch (Exception e) {
			log.error("error");
		} 
	}
	
	public String getIndexData() {
		List<Product> productList = null;
		try {
			productList = DataUtil.getExcelData();
			for (Product pr : productList) {
				System.out.println(pr);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList.toString();
	}

}
