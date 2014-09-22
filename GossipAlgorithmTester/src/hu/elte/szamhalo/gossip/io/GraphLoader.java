package hu.elte.szamhalo.gossip.io;

import hu.elte.szamhalo.gossip.vo.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class GraphLoader {
	
	public static SortedSet<Node> getGraph(String fileName) throws IOException{
		SortedSet<Node> nodeList = new TreeSet<Node>();
	    try(BufferedReader br = new BufferedReader(new FileReader(fileName));) {
	        String line = br.readLine();

	        while (line != null) {
	            if(!line.contains("-")){
	            	nodeList.add(new Node(line, new ArrayList<Node>(), null, null));
	            }else{
	            	String node1ID = line.split("-")[0];
	            	String node2ID = line.split("-")[1];
	            	Node node1 = null;
	            	Node node2 = null;
	            	for (Iterator<Node> it = nodeList.iterator(); it.hasNext(); ) {
	            		Node node = it.next();
	                    if (node.getNodeID().equals(node1ID)){
	                    	node1 = node;
	                    }
	                    if (node.getNodeID().equals(node2ID)){
	                    	node2 = node;
	                    }
	                }
	            	connect(node1, node2);
	            }
	            line = br.readLine();
	        }
	    }
		return nodeList;
	}
	
	private static void connect(Node node1, Node node2){
		node1.getNeighbours().add(node2);
		node2.getNeighbours().add(node1);
	}
}
