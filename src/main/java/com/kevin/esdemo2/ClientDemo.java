package com.kevin.esdemo2;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class ClientDemo {

	public static void main(String[] args) {
		
		Node node = NodeBuilder.nodeBuilder().clusterName("elasticsearch").node();
		Client client = node.client();
		
		node.close();
		
	}

}
