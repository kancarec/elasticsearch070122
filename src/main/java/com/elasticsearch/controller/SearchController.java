package com.elasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elasticsearch.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	SearchService service;
	
	@GetMapping("/keyword")
	public void searchKeywordQuery() {
		service.searchKeywordQuery();
	}
	
	@GetMapping("/phrase")
	public void searchPhraseQuery() {
		service.searchPhraseQuery();
	}
	
	@GetMapping("/range")
	public void searchRangeQuery() {
		service.searchRangeQuery();
	}
	
	@GetMapping("/minMatch")
	public void searchMinimumShouldMatch() {
		service.searchMinimumShouldMatch();
	}
	
	@GetMapping("/fuzzyMatch")
	public void searchFuzzyMatch() {
		service.searchFuzzyMatch();
	}
	
	@GetMapping("/termQuery")
	public void searchTermQuery() {
		service.searchTermQuery();
	}
	
	
	
	
}
