package com.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.service.DataService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

@RestController
@RequestMapping("/es")
public class EsController {
	@Autowired
	ElasticsearchClient esClient;
	
	@Autowired
	DataService dataService;

	@GetMapping("/insertData")
	public void insertData() {
		dataService.insertData();
	}	
	
	@GetMapping("/bulk")
	public void insertBulk() {
		dataService.insertDataBulk();

	}
	
	@GetMapping("/updateData")
	public void updateData() {
		dataService.updateData();
	}	

	@GetMapping("/data")
	public String getData() {
		return dataService.getIndexData();
	}
	
	@GetMapping("/deleteDoc")
	public void deleteDoc() {
		dataService.deleteData();
	}
	@GetMapping("/deleteDocById")
	public void deleteDocById() {
		dataService.deleteDataById();
	}
}
