package hu.elte.szamhalo.gossip.util;

import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;

public class RumorUtil {

	private static Random rand = new Random();
	
	public static Rumor setRandomRumor(SortedSet<Node> graph, String rumorId){
		int nodeIndex = rand.nextInt(graph.size());
		for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
    		Node node = it.next();
    		if(nodeIndex-- == 0){
    			Rumor rumor = new Rumor();
    			rumor.setId(rumorId);
    			rumor.setSourceNode(node);
				node.getKnownRumors().add(rumor);
    			return rumor;
    		}
    	}
		return null;
	}
	
	public static boolean knowsThatRumor(Node node, String rumorId){
		for (Iterator<Rumor> it = node.getKnownRumors().iterator(); it.hasNext(); ) {
			Rumor rumor = it.next();
			if(rumor.getId().equals(rumorId)){
				return true;
			}
		}
		return false;
	}
}
