package com.kevin.es.utils;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class EsUtils {
	
	public static final String INDEX_NAME = "esindex";
	public static final String TYPE_NAME = "estype";
	
	private static Client client;
	
	public static Client getEsClient(){
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", "es") //指定集群名称
				.put("client.transport.sniff", true)	//探测集群中机器状态
				.build();
		client = new TransportClient(settings)
			.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
		return client;
	}
	
	public static void closeClient(){
		if(client!=null)
			client.close();
	}
	
	public static String getIndexName() {
		return INDEX_NAME;
	}
	public static String getTypeName() {
		return TYPE_NAME;
	}
	
	
	
	
	
}
