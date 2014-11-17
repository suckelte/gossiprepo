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

	private static Random rand = new Random();
	
	@Override
	public int step() {
		int toldRumor = 0;
		if(isActive() && node.getRumor() != null && !node.getRumor().isFresh()){
			int nodeIndex = rand.nextInt(node.getNeighbours().size());
			System.out.println(nodeIndex);
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
							freshRumor.setFresh(true);
							freshRumor.setSourceNode(node.getRumor().getSourceNode());
							neighbour.setRumor(freshRumor);
							neighbour.getActiveAlgorithm().getAlreadyTold().addAll(alreadyTold);
							neighbour.getActiveAlgorithm().getAlreadyTold().add(node.getNodeID());
						}
						this.active = false;
						break;
				}
			}
			toldRumor = 1;
		}
		return toldRumor;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	public SimpleRandomAlgorithm(Node node){
		this.node = node;
	}

	/**
	 * @return the alreadyTold
	 */
	public List<String> getAlreadyTold() {
		return alreadyTold;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
