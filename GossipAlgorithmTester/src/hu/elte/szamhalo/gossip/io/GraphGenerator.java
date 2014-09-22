package hu.elte.szamhalo.gossip.io;

import hu.elte.szamhalo.gossip.vo.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphGenerator {
	
	private static Random rand = new Random();
	
	public static List<Node> getGraph(int numberOfNodes, int density){
		List<Node> graph = new ArrayList<Node>();
		List<Node> connectedGraph = new ArrayList<Node>();
		for(int i = 0; i < numberOfNodes; i++){
			Node node = new Node("n" + i, new ArrayList<Node>(), null, null);
			graph.add(node);
		}
		int numberOfMinEdge = numberOfNodes - 1;
		int numberOfMaxEdge = numberOfNodes * (numberOfNodes - 1);
		int selectedEdgeNumber = numberOfMinEdge + ((numberOfMaxEdge - numberOfMinEdge) * density / 100); 
		
		System.out.println("GraphGenerator:" + numberOfMinEdge + "/" + numberOfMaxEdge + ":" + selectedEdgeNumber);
		
		for(int i = 0; i < selectedEdgeNumber + 1; i++){
			Node nextNode;
			int nextNodeId = -1;
			if(!graph.isEmpty()){
				nextNode = graph.get(rand.nextInt(graph.size()));
				graph.remove(nextNode);
			}else{
				nextNodeId = rand.nextInt(connectedGraph.size());
				nextNode = connectedGraph.get(nextNodeId);
			}
			if(!connectedGraph.isEmpty()){
				int connectingNodeId;
				do{
					connectingNodeId = rand.nextInt(connectedGraph.size());
				}while(connectingNodeId == nextNodeId);
				connect(nextNode, connectedGraph.get(connectingNodeId));
			}
			if(nextNodeId == -1){
				connectedGraph.add(nextNode);
			}
		}
		return connectedGraph;
	}
	
	private static void connect(Node node1, Node node2){
		node1.getNeighbours().add(node2);
		node2.getNeighbours().add(node1);
	}
	
	public static List<Node> getComplexGraph(List<Integer> numberOfNodesList, List<Integer> density){
		List<Node> complexGraph = new ArrayList<Node>(); 
		for(int i = 0; i < numberOfNodesList.size(); i++){
			List<Node> graphPart = getGraph(numberOfNodesList.get(i),density.get(i));
			if(complexGraph.size() > 0){
				Node connectingNode = complexGraph.get(rand.nextInt(complexGraph.size()));
				Node nextNode = graphPart.get(rand.nextInt(graphPart.size()));
				connect(connectingNode, nextNode);
			}
			complexGraph.addAll(graphPart);
		}
		return complexGraph;
	}
	
}
