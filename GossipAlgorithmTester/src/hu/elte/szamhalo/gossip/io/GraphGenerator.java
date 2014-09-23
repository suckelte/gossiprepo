package hu.elte.szamhalo.gossip.io;

import hu.elte.szamhalo.gossip.vo.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

public class GraphGenerator {
	
	private static Random rand = new Random();
	
	/**
	 * generates a simple coherent graph(set of nodes)<br>
	 * (The edges will be added randomly) 
	 * @param numberOfNodes
	 * @param density percent value(0-100), the number of edges will be added between n-1 and n(n-1)<br>e.g.: 4 egdes -> 0% = 3 edge, 100% = 12 edge, 30% = 6 edge  
	 * @param startIndex nodenames are generated automatically ("n" + startindex in 000 format)
	 * @return
	 */
	private static List<Node> getGraph(int numberOfNodes, int density, int startIndex){
		List<Node> graph = new ArrayList<Node>();
		List<Node> connectedGraph = new ArrayList<Node>();
		for(int i = 0; i < numberOfNodes; i++){
			Node node = new Node("n" + String.format("%03d", startIndex++), new TreeSet<Node>(), null, null);
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
	/**
	 * creates an edge between the two nodes
	 * @param node1
	 * @param node2
	 */
	private static void connect(Node node1, Node node2){
		node1.getNeighbours().add(node2);
		node2.getNeighbours().add(node1);
	}
	
	/**
	 * generates a more complex coherent graph(set of nodes), where every part of the graph will be connected with one edge (bottleneck)<br>
	 * (The edges will be added randomly)<br> 
	 * NOTE: size of numberOfNodesList and density have to be equals
	 * @param numberOfNodesList number of nodes in every part of the graph
	 * @param density the density of edges in every part of the graph<br> percent value(0-100), the number of edges will be added between n-1 and n(n-1)<br>e.g.: 4 egdes -> 0% = 3 edge, 100% = 12 edge, 30% = 6 edge
	 * @return
	 */
	public static SortedSet<Node> getComplexGraph(Integer[] numberOfNodesList, Integer[] density){
		List<Node> complexGraph = new ArrayList<Node>(); 
		int startIndex = 1;
		for(int i = 0; i < numberOfNodesList.length; i++){
			List<Node> graphPart = getGraph(numberOfNodesList[i],density[i],startIndex);
			if(complexGraph.size() > 0){
				Node connectingNode = complexGraph.get(rand.nextInt(complexGraph.size()));
				Node nextNode = graphPart.get(rand.nextInt(graphPart.size()));
				connect(connectingNode, nextNode);
			}
			complexGraph.addAll(graphPart);
			startIndex += graphPart.size();
			System.out.println(complexGraph.size());
		}
		return new TreeSet<Node>(complexGraph);
	}
	
}
