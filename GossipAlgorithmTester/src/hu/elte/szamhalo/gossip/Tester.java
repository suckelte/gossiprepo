package hu.elte.szamhalo.gossip;

import hu.elte.szamhalo.gossip.io.GraphLoader;
import hu.elte.szamhalo.gossip.vo.Node;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tester {
	private static List<Node> graph = new ArrayList<Node>();
	
	public static void main(String[] args) throws IOException {
		
		
		

    	for (Iterator<Node> it = GraphLoader.getGraph("input/input1.grph").iterator(); it.hasNext(); ) {
    		Node node = it.next();
    		System.out.println(node.getNodeID() +" / " + node.getNeighbours().size());
    		
    	}
		
//		List<Node> graph2;
//		graph2 = GraphGenerator.getGraph(5, 0);
//		for (Node node : graph2) {
//			System.out.print(node.getNeighbours().size());
//		}
//		System.out.println(",");
//		graph2 = GraphGenerator.getGraph(5, 100);
//		for (Node node : graph2) {
//			System.out.print(node.getNeighbours().size());
//		}
//		System.out.println(",");
//		graph2 = GraphGenerator.getGraph(5, 50);
//		for (Node node : graph2) {
//			System.out.print(node.getNeighbours().size());
//		}
//		System.out.println(",");
//		graph2 = GraphGenerator.getGraph(5, 5);
//		for (Node node : graph2) {
//			System.out.print(node.getNeighbours().size());
//		}
//		System.out.println(",");
//		graph2 = GraphGenerator.getGraph(5, 2);
//		for (Node node : graph2) {
//			System.out.print(node.getNeighbours().size());
//		}
//		System.out.println(",");
//		graph2 = GraphGenerator.getGraph(5, 1);
//		for (Node node : graph2) {
//			System.out.print(node.getNeighbours().size());
//		}
//		System.out.println(",");
		
		/*
		
		List<Rumor> rumors = new ArrayList<Rumor>();
		List<Rumor> erumors = new ArrayList<Rumor>();
		Rumor r = new Rumor();
		r.setId("r1");
		rumors.add(r);
		Node sourceNode = new Node("sn",new ArrayList<Node>(), null, rumors);
		r.setSourceNode(sourceNode);
		
		
		Node node1 = new Node("n1",new ArrayList<Node>(), null, rumors);
		Node node2 = new Node("n2",new ArrayList<Node>(), null, rumors);
		Node node3 = new Node("n3",new ArrayList<Node>(), null, rumors);
		Node node4 = new Node("n4",new ArrayList<Node>(), null, rumors);
		Node node5 = new Node("n5",new ArrayList<Node>(), null, rumors);
		Node node6 = new Node("n6",new ArrayList<Node>(), null, erumors);
		
		graph.add(sourceNode);
		graph.add(node1);
		graph.add(node2);
		graph.add(node3);
		graph.add(node4);
		graph.add(node5);
		graph.add(node6);
		
		sourceNode.getNeighbours().add(node1);
		sourceNode.getNeighbours().add(node2);
		
		node1.getNeighbours().add(sourceNode);
		node2.getNeighbours().add(sourceNode);
		
		node1.getNeighbours().add(node3);
		node1.getNeighbours().add(node4);
		
		node3.getNeighbours().add(node1);
		node4.getNeighbours().add(node1);
		
		node2.getNeighbours().add(node5);
		node5.getNeighbours().add(node2);
		
		node5.getNeighbours().add(node6);
		node6.getNeighbours().add(node5);
		
		System.out.println(RumorVerifier.verify(graph, r, 0));
		System.out.println(RumorVerifier.verify(graph, r, 1));
		System.out.println(RumorVerifier.verify(graph, r, 2));
		System.out.println(RumorVerifier.verify(graph, r, 3));
		*/
	}
}
