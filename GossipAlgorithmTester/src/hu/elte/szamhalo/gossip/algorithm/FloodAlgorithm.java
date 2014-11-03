package hu.elte.szamhalo.gossip.algorithm;

import java.util.Iterator;
import java.util.List;

import hu.elte.szamhalo.gossip.vo.IChoosingAlgorithm;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

public class FloodAlgorithm implements IChoosingAlgorithm {

	private boolean active = true;
	private Node node;

	@Override
	public int step() {
		int toldRumor = 0;
		if(isActive() && node.getRumor() != null && !node.getRumor().isFresh()){
			for (Iterator<Node> it = node.getNeighbours().iterator(); it.hasNext(); ) {
				Node neighbour = it.next();
				if(neighbour.getRumor() == null){
					Rumor freshRumor = node.getRumor();
					freshRumor.setFresh(true);
					neighbour.setRumor(freshRumor);
					toldRumor++;
				}else{
					this.active = false;
				}
			}
		}
		return toldRumor;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	public FloodAlgorithm(Node node){
		this.node = node;
	}

	@Override
	public List<String> getAlreadyTold() {
		// TODO Auto-generated method stub
		return null;
	}
}