package hu.elte.szamhalo.gossip.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.elte.szamhalo.gossip.vo.IChoosingAlgorithm;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

public class FloodAlgorithm implements IChoosingAlgorithm {

	private boolean active = true;
	private Node node;
	private List<String> alreadyTold = new ArrayList<String>();

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
					neighbour.getActiveAlgorithm().getAlreadyTold().add(node.getNodeID());
				}
				toldRumor++;
			}

			toldRumor -= alreadyTold.size();
			this.active = false;
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
		return alreadyTold;
	}
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
}
