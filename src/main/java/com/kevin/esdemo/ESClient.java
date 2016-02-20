package com.kevin.esdemo;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

/**
 * Hello world!
 *
 */
public class ESClient 
{
	private Client client;
	
	public void init(){
		client = new TransportClient()
			.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
	}
	
	public void close(){
		client.close();
	}
	
	public void createIndex() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(new Long(i));
            user.setName("huang fox " + i);
            user.setAge(i % 100);
            String source = generateJson(user);
            client.prepareIndex("users", "user").setSource(source)
            	.execute().actionGet();
        }
    }
	
	/**
     * 转换成json对象
     *
     * @param user
     * @return
     */
    private String generateJson(User user) {
        String json = "";
        try {
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
            contentBuilder.field("id", user.getId() + "");
            contentBuilder.field("name", user.getName());
            contentBuilder.field("age", user.getAge() + "");
            json = contentBuilder.endObject().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
	
    public static void main( String[] args )
    {
        
    	ESClient client = new ESClient();
        client.init();
        client.createIndex();
        client.close();
    }
}
