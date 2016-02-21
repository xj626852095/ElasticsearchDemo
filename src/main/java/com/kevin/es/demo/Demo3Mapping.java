package com.kevin.es.demo;

import java.io.IOException;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;

import com.kevin.es.utils.EsUtils;

public class Demo3Mapping {
	
	private String indexName = "test_mapping_index";
	private String typeName = "test_mapping_type";
	
	//创建mapping 
	@Test
	public void createMapping() throws Exception{
		Client client =  EsUtils.getEsClient();
		//首先创建index
		CreateIndexResponse createIndexResponse = client.admin().indices()
			.prepareCreate(indexName).execute().actionGet();
		System.out.println("createIndexResponse="+createIndexResponse.isAcknowledged());
		
		PutMappingRequestBuilder mappingRequest = client.admin().indices().preparePutMapping(indexName)
			.setType(typeName)
			.setSource( createTestModelMapping() );
		PutMappingResponse putMappingResponse = mappingRequest.execute().actionGet();
		System.out.println("putMappingResponse="+putMappingResponse.isAcknowledged());
		
		EsUtils.closeClient();
	}
	
	
	private XContentBuilder createTestModelMapping() throws Exception{
		XContentBuilder mapping = XContentFactory.jsonBuilder()
			.startObject()
				.startObject(typeName)
					.startObject("properties")
						.startObject("id")
							.field("type", "long")
							.field("store", "yes")
						.endObject()
						.startObject("type")
							.field("type", "string")
							.field("index", "not_analyzed")
						.endObject()
						.startObject("catIds")
							.field("type", "integer")
						.endObject()
						.startObject("catName")
							.field("type", "string")
						.endObject()
					.endObject()
				.endObject()
			.endObject();		
		return mapping;
	}
	
	//获得mapping
	@Test
	public void getMapping() throws IOException{
		Client client = EsUtils.getEsClient();
		ClusterState cs = client.admin().cluster().prepareState().setIndices(indexName).execute().actionGet().getState();
		IndexMetaData imd = cs.getMetaData().index(indexName);		
		MappingMetaData mdd = imd.mapping(typeName);
		System.out.println(mdd.sourceAsMap());
		
		EsUtils.closeClient();
	}
	
}
