package com.kevin.es;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;

import com.kevin.es.utils.EsUtils;

public class Demo1Crud {
	
	//增加
	@Test
	public void insert(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prodId", 2);
		map.put("prodName", "iphone6");
		map.put("prodDesc", "手机");
		map.put("catId", 2);
				
		Client client = EsUtils.getEsClient();
		IndexResponse indexResponse = client.prepareIndex()
				.setIndex(EsUtils.getIndexName())
				.setType(EsUtils.getTypeName()) 
				//.setSource("{\"prodId\":1,\"prodName\":\"ipad5\",\"prodDesc\":\"比你想的更强大\",\"catId\":1}")
				.setSource(map)
				.setId("1")
				.execute()
				.actionGet();
		System.out.println("插入成功, isCreated="+indexResponse.isCreated());
		EsUtils.closeClient();
	}
	
	
	//查询
	@Test
	public void query(){
		Client client = EsUtils.getEsClient();
		GetResponse getResponse = client.prepareGet()
				.setIndex(EsUtils.getIndexName())
				.setType(EsUtils.getTypeName())
				.setId("1")
				.execute()
				.actionGet();
		System.out.println("get="+getResponse.getSourceAsString());
		EsUtils.closeClient();
	}
	
	//搜索
	@Test
	public void search(){
		Client client = EsUtils.getEsClient();
		QueryBuilder query = QueryBuilders.queryString("iphone");
		SearchResponse searchResponse = client.prepareSearch( EsUtils.getIndexName() )
				.setQuery(query)
				.setFrom(0).setSize(10)
				.execute()
				.actionGet();
		//SearchHits是SearchHit的复数形式，表示这个是一个列表
		SearchHits shs = searchResponse.getHits();
		for(SearchHit hit : shs){
			System.out.println( hit.getSourceAsString() );
		}
		EsUtils.closeClient();
	}
	
	//删除
	@Test
	public void delete(){
		Client client = EsUtils.getEsClient();
		DeleteResponse delResponse = client.prepareDelete()
				.setIndex(EsUtils.getIndexName())
				.setType(EsUtils.getTypeName())
				.setId("1")
				.execute()
				.actionGet();
		System.out.println("del id found="+delResponse.isFound());
		EsUtils.closeClient();
	}
	
	//更新
	@Test
	public void update(){
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("prodId", 2);
		updateMap.put("prodName", "iphone6s");
		updateMap.put("prodDesc", "手机");
		//updateMap.put("catId", 2);
				
		Client client = EsUtils.getEsClient();
		UpdateResponse updateResponse = client.prepareUpdate()
				.setIndex(EsUtils.getIndexName())
				.setType(EsUtils.getTypeName())				
				.setDoc(updateMap)
				.setId("1")
				.execute()
				.actionGet();
		System.out.println("更新成功， isUpdated="+updateResponse.isCreated());  //???? 一直是false
		EsUtils.closeClient();
	}
	

}
