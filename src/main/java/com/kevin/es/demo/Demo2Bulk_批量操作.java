package com.kevin.es.demo;

import java.security.SecureRandom;
import java.util.Random;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.junit.Test;

import com.kevin.es.model.User;
import com.kevin.es.utils.EsUtils;
import com.kevin.es.utils.JsonUtils;
import com.kevin.es.utils.RandomName;

public class Demo2Bulk_批量操作 {
	
	
	/**
	 * 批量操作请求 BulkRequestBuilder作为一个容器
	 * 可以添加  IndexRequestBuilder,DeleteRequestBuilder,UpdateRequestBuilder
	 * 从而做到批量增删改 
	 * **/
	
	//批量插入
	@Test
	public void batchInsert(){
		Client client = EsUtils.getEsClient();
		
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		
		User user = new User();
		for(int i=0; i<100; i++){
			user.setName( RandomName.getEnglishName() );			
			Random random = new Random();
			long num = Math.abs( random.nextInt(200));
			user.setWeight(num);
			user.setMarried(num%2==0?true:false);
			user.setAge(Math.abs( random.nextInt(100)));
			
			IndexRequestBuilder ir = client.prepareIndex()
					.setIndex(EsUtils.INDEX_NAME)
					.setType("bulk_user")
					.setId(""+i)
					.setSource( JsonUtils.toJson(user) );
			bulkRequest.add(ir);
		}
		
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if(!bulkResponse.hasFailures()){
			System.out.println("全部插入成功");
		}
		
		EsUtils.closeClient();
	}
	
	//批量删除
	@Test
	public void batchDel(){
		Client client = EsUtils.getEsClient();		
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(int i=10; i<100; i++){
			DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete()
					.setIndex(EsUtils.INDEX_NAME)
					.setType("bulk_user")
					.setId(""+i);
			bulkRequest.add(deleteRequestBuilder);
		}
		
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if(!bulkResponse.hasFailures()){
			System.out.println("全部删除成功");
		}
		EsUtils.closeClient();
	}
	
}
