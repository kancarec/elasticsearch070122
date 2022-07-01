package com.elasticsearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elasticsearch.model.Product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.json.JsonData;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchService {
	@Autowired
	ElasticsearchClient esClient;

	public void searchKeywordQuery() {
		try {
			String searchText = "FunkyBuys";// "Kotobukiya";//"Teamsterz Transporter Dazzling";

			/*
			 * SearchResponse<Product> response = esClient.search( s ->
			 * s.index("productlist").query(q -> q.match(t ->
			 * t.field("name").query(searchText))), Product.class);
			 */
			SearchResponse<Product> response = esClient.search(
					s -> s.index("productlist").query(q -> q.match(t -> t.field("id").query("1"))), Product.class);
			TotalHits total = response.hits().total();
			boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

			if (isExactResult) {
				log.info("There are " + total.value() + " results");
			} else {
				log.info("There are more than " + total.value() + " results");
			}

			List<Hit<Product>> hits = response.hits().hits();
			for (Hit<Product> hit : hits) {
				Product product = hit.source();
				log.info("Found product " + product.getId() + ", score " + hit.score());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void searchMinimumShouldMatch() {
		try {
			String searchText = "Teamsterz Transporter Dazzling";

			SearchResponse<Product> response = esClient.search(
					s -> s.index("productlist")
							.query(q -> q.match(t -> t.field("name").query(searchText).minimumShouldMatch("2"))),
					Product.class);
			TotalHits total = response.hits().total();
			boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

			if (isExactResult) {
				log.info("There are " + total.value() + " results");
			} else {
				log.info("There are more than " + total.value() + " results");
			}

			List<Hit<Product>> hits = response.hits().hits();
			for (Hit<Product> hit : hits) {
				Product product = hit.source();
				log.info("Found product " + product.getId() + ", score " + hit.score());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void searchPhraseQuery() {
		try {
			String searchText = "4 Dragon Plastic Toys";// "Dickie Toys Fire Brigade";// 176,299,180

			SearchResponse<Product> response = esClient.search(
					s -> s.index("productlist").query(q -> q.matchPhrase(t -> t.field("name").query(searchText))),
					Product.class);
			TotalHits total = response.hits().total();
			boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

			if (isExactResult) {
				log.info("There are " + total.value() + " results");
			} else {
				log.info("There are more than " + total.value() + " results");
			}
			List<Hit<Product>> hits = response.hits().hits();
			for (Hit<Product> hit : hits) {
				Product product = hit.source();
				log.info("Found product " + product.getId() + ", score " + hit.score());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void searchFuzzyMatch() {
		try {
			String searchText = "Dakkling";// Wltoys

			SearchResponse<Product> response = esClient.search(
					// s -> s.index("productlist").query(q -> q.fuzzy(t ->
					// t.field("name").value(searchText))),
					s -> s.index("productlist")
							.query(q -> q.match(t -> t.field("name").query(searchText).fuzziness("1"))),

					Product.class);
			TotalHits total = response.hits().total();
			boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

			if (isExactResult) {
				log.info("There are " + total.value() + " results");
			} else {
				log.info("There are more than " + total.value() + " results");
			}

			List<Hit<Product>> hits = response.hits().hits();
			for (Hit<Product> hit : hits) {
				Product product = hit.source();
				log.info("Found product " + product.getId() + ", score " + hit.score());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void searchRangeQuery() {
		try {
			String searchText = "Teamsterz Transporter Dazzling";// 176,299,180
			Query byName = MatchQuery.of(m -> m.field("name").query(searchText))._toQuery();
			Query byMaxPrice = RangeQuery.of(r -> r.field("count").gte(JsonData.of(155)))._toQuery();
			SearchResponse<Product> response = esClient.search(
					s -> s.index("productlist").query(q -> q.bool(b -> b.must(byName).must(byMaxPrice))),
					Product.class);
			TotalHits total = response.hits().total();
			boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

			if (isExactResult) {
				log.info("There are " + total.value() + " results");
			} else {
				log.info("There are more than " + total.value() + " results");
			}
			List<Hit<Product>> hits = response.hits().hits();
			for (Hit<Product> hit : hits) {
				Product product = hit.source();
				log.info("Found product " + product.getId() + ", score " + hit.score());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void searchTermQuery() {
		SearchResponse<Product> search;
		try {
			search = esClient.search(
					s -> s.index("productlist").query(q -> q.term(t -> t.field("id").value(v -> v.stringValue("1")))),
					Product.class);
			for (Hit<Product> hit : search.hits().hits()) {
				System.out.println(hit.source());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
