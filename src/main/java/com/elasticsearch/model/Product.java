package com.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
private String id;
private String name;
private Double count;
@Override
public String toString() {
	return "Product [id=" + id + ", name=" + name + ", count=" + count + "]";
}

}
