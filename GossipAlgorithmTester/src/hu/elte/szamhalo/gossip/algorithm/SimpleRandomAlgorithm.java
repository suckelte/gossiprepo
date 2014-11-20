package hu.elte.szamhalo.gossip.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import hu.elte.szamhalo.gossip.vo.IChoosingAlgorithm;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

public class SimpleRandomAlgorithm implements IChoosingAlgorithm {

	private boolean active = true;  
	private Node node;
	private List<String> alreadyTold = new ArrayList<String>();
	private int n;
	private double diameter;
	private int maxDegree;

	private static Random rand = new Random();
	
	@Override
	public int step() {
		int toldRumor = 0;
		if(node.getRumor() != null){
			int nodeIndex = rand.nextInt(node.getNeighbours().size());
			for (Iterator<Node> it = node.getNeighbours().iterator(); it.hasNext(); ) {
					if(nodeIndex-- == 0){
						Node neighbour = it.next();
						if(alreadyTold.contains(neighbour.getNodeID())){
							if(it.hasNext()){
								nodeIndex++;
							}else{
								it = node.getNeighbours().iterator();
							}
							continue;
						}
						if(neighbour.getRumor() == null){
							Rumor freshRumor = new Rumor();
							freshRumor.setSourceNode(node.getRumor().getSourceNode());
							neighbour.setRumor(freshRumor);
//							neighbour.getActiveAlgorithm().getAlreadyTold().addAll(alreadyTold);
							neighbour.getActiveAlgorithm().getAlreadyTold().add(node.getNodeID());
						}
						alreadyTold.add(neighbour.getNodeID());
						if(node.getNeighbours().size() == alreadyTold.size()){
							this.active = false;
							return toldRumor;
						}
						break;
				}
			}
			toldRumor = 1;
		}
		return toldRumor;
	}

	public SimpleRandomAlgorithm(Node node, int n, double diameter, int maxDegree){
			this.node = node;
			this.n = n;
			this.diameter = diameter;
			this.maxDegree = maxDegree;
		}

	/**
	 * @return the alreadyTold
	 */
	public List<String> getAlreadyTold() {
		return alreadyTold;
	}
	
}
