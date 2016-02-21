package com.kevin.es.demo;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

import com.kevin.es.utils.EsUtils;

public class Demo5Scroll_游标 {
	
	/*
	 * http://blog.csdn.net/xj626852095/article/details/50708213
	A scanning scroll 查询与 a standard scroll 查询有几点不同：
	1. A scanning scroll 查询结果没有排序，结果的顺序是doc入库时的顺序；
	2. A scanning scroll 查询不支持聚合
	3. A scanning scroll最初的查询结果的“hits”列表中不会包含结果
	*/
	
	//游标
	@Test
	public void scroll(){
		Client client = EsUtils.getEsClient();
		SearchResponse searchResponse =
			client.prepareSearch(EsUtils.getIndexName())
			.setTypes("bulk_user")
			.setSearchType(SearchType.SCAN)		//加上这个据说可以提高性能，但第一次却不返回结果
			.setSize(5)			//实际返回的数量为5*index的主分片格式，如果index是默认配置的话
			.setScroll(TimeValue.timeValueMinutes(8))	//这个游标维持多长时间
			.execute().actionGet();
		//第一次查询，只返回数量和一个scrollId
		System.out.println(searchResponse.getHits().getTotalHits());
		System.out.println(searchResponse.getHits().hits().length);  //受setSize(5)的影响，每个分片只返回5个
		//第一次运行没有结果
		for (SearchHit hit : searchResponse.getHits()) {
			System.out.println(hit.getSourceAsString());
		}
		System.out.println("------------------------------");
		
		searchResponse = client.prepareSearchScroll( searchResponse.getScrollId() )
				.setScroll(TimeValue.timeValueMinutes(8))				
				.execute().actionGet();
		System.out.println(searchResponse.getHits().getTotalHits());
		System.out.println(searchResponse.getHits().hits().length);
		for (SearchHit hit : searchResponse.getHits()) {
			System.out.println(hit.getSourceAsString());
		}
		
		EsUtils.closeClient();
	}
	
}
