package com.kevin.es.demo;

import java.util.Map;

import org.apache.lucene.queryparser.xml.builders.BooleanFilterBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.junit.Test;

import com.kevin.es.utils.EsUtils;

public class Demo3Facet_group分组 {
	
	//分组统计, 按married分组，统计出结婚和未婚的各多少
	@Test
	public void facet(){
		Client client = EsUtils.getEsClient();
		//请求
		SearchRequestBuilder searchRequestBuilder = client.prepareSearch(EsUtils.getIndexName()).setTypes("bulk_user");
		//过滤器, 先过滤user年龄，在正常的年龄范围内做分组
		BoolFilterBuilder filter = FilterBuilders.boolFilter();
		filter.must(FilterBuilders.matchAllFilter());
		filter.must(FilterBuilders.rangeFilter("age").from(1).to(150));
		//分组条件
		String facetName = "marriedFacet";
		FacetBuilder marriedFacet = FacetBuilders.termsFacet(facetName).field("married").allTerms(true);
		marriedFacet.facetFilter(filter);
		searchRequestBuilder.addFacet(marriedFacet);
		
		SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
		Facets facets = searchResponse.getFacets();
		Map<String, Facet> mapFacets = facets.getFacets();
		Facet facet = mapFacets.get(facetName);
		TermsFacet mFacet = (TermsFacet) facet;
		for( TermsFacet.Entry entry : mFacet.getEntries() ){
			System.out.println("key:"+entry.getTerm().toString()+" count:"+entry.getCount());
		}
	
		
		EsUtils.closeClient();
	}
	
	
}
