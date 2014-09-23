package hu.elte.szamhalo.gossip;

import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;

import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

public class RumorVerifier {
	
	/**
	 * Verifies that every nodes knows the specific Rumor in k distance
	 * @param rumor 
	 * @param kLocal distance from the source node
	 * @return
	 */
	public static Node verify(Rumor rumor, Integer kLocal){
		Map<Node, Integer> kLocalMap = new ConcurrentHashMap<Node,Integer>();
		kLocalMap.put(rumor.getSourceNode(), 0);
		int distanceFromSource = 0;
		while(distanceFromSource < kLocal){
			distanceFromSource++;
			for(Node node : kLocalMap.keySet()){
				if(distanceFromSource - 1 != kLocalMap.get(node)){
					continue;
				}
				for(Node neighbour : node.getNeighbours()){
					if(!kLocalMap.containsKey(neighbour)){
						if(neighbour.getKnownRumors().contains(rumor)){
							kLocalMap.put(neighbour, distanceFromSource);
						}else{
							return neighbour;
						}
					}
				}
			}
		}
		return null;
	}
}
