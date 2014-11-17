package hu.elte.szamhalo.gossip.util;

import hu.elte.szamhalo.gossip.algorithm.FloodAlgorithm;
import hu.elte.szamhalo.gossip.algorithm.RandomAlgorithm;
import hu.elte.szamhalo.gossip.algorithm.SimpleRandomAlgorithm;
import hu.elte.szamhalo.gossip.vo.ChoosingAlgorithmEnum;
import hu.elte.szamhalo.gossip.vo.IChoosingAlgorithm;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;

public class GraphUtil {

	private static Random rand = new Random();
	
	public static Rumor setRandomRumor(SortedSet<Node> graph){
		int nodeIndex = rand.nextInt(graph.size());
		for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
    		Node node = it.next();
    		if(nodeIndex-- == 0){
    			Rumor rumor = new Rumor();
    			rumor.setSourceNode(node);
				node.setRumor(rumor);
    			return rumor;
    		}
    	}
		return null;
	}
	
	public static void setChoosingAlgorithm(SortedSet<Node> graph, ChoosingAlgorithmEnum choosingAlgorithmEnum){
		for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
    		Node node = it.next();
    		IChoosingAlgorithm activeAlgorithm = null;
    		switch (choosingAlgorithmEnum) {
				case FLOOD:
					activeAlgorithm = new FloodAlgorithm(node);
					break;
				case RANDOM:
					activeAlgorithm = new RandomAlgorithm(node);
					break;
			case SIMPLERANDOM:
				activeAlgorithm = new SimpleRandomAlgorithm(node);
				break;
			default:
				break;
    		}
			node.setActiveAlgorithm(activeAlgorithm);
    	}
	}
}
