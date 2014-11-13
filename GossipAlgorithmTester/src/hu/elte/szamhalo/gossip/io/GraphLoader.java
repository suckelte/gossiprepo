package hu.elte.szamhalo.gossip.io;

import hu.elte.szamhalo.gossip.vo.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.json.JSONArray;

public class GraphLoader {
	
	/**
	 * Generates a set of Node based on an input graph file<br>
	 * First the nodes are generated based on names, after that the connections are builded
	 * @param fileName if the file contains connection with non existing nodes, that will be awarded with  a nullPointerE.
	 * @return
	 * @throws IOException
	 */
	public static SortedSet<Node> getGraph(String fileName) throws IOException{
		SortedSet<Node> nodeList = new TreeSet<Node>();
	    try(BufferedReader br = new BufferedReader(new FileReader(fileName));) {
	        String line = br.readLine();

	        while (line != null) {
	            if(!line.contains("-")){
	            	nodeList.add(new Node(line, new TreeSet<Node>(), null, null));
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
	

	public static SortedSet<Node> getJSONGraph(String fileName) throws IOException{
		SortedSet<Node> nodeList = new TreeSet<Node>();
	    try(BufferedReader br = new BufferedReader(new FileReader(fileName));) {
	        String line = br.readLine();

	        final JSONArray input = new JSONArray(line);
	    
	        for (int i = 0; i < input.length(); i++) {
	            JSONArray nodePair = input.getJSONArray(i);
	
	            for (int j = 0; j <= 1; j++) {
	            	Node firstAdded = null;
	            	Node secondAdded = null;
	            	for (Iterator<Node> it = nodeList.iterator(); it.hasNext(); ) {
	            		Node node = it.next();
	            		if(node.getNodeID().equals(nodePair.get(0))){
	            			firstAdded = node;
	            		}
	            		if(node.getNodeID().equals(nodePair.get(1))){
	            			secondAdded = node;
	            		}
	            	}
	            	if(firstAdded == null){
		            	final Node node1 = new Node("n" + nodePair.get(0), new TreeSet<Node>(), null, null);
		            	firstAdded = node1;
						nodeList.add(node1);
	            	}
	            	if(secondAdded == null){
	            		final Node node2 = new Node("n" + nodePair.get(1), new TreeSet<Node>(), null, null);
	            		secondAdded = node2;
						nodeList.add(node2);
	            	}
	            	connect(firstAdded, secondAdded);
	            }
	        }
	    }
		return nodeList;
	}
	
	private static void connect(Node node1, Node node2){
		node1.getNeighbours().add(node2);
		node2.getNeighbours().add(node1);
	}
}
