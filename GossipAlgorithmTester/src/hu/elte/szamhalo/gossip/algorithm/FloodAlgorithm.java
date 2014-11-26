package hu.elte.szamhalo.gossip.algorithm;

import hu.elte.szamhalo.gossip.gui.GraphView;
import hu.elte.szamhalo.gossip.vo.IChoosingAlgorithm;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FloodAlgorithm implements IChoosingAlgorithm {

	private Node node;
	private int n;
	private double diameter;
	private int maxDegree;

	@Override
	public int step(GraphView graphView) {
		for (Iterator<Node> it = node.getNeighbours().iterator(); it.hasNext(); ) {
			Node neighbour = it.next();
			if(node.getRumor() != null && neighbour.getRumor() == null){
				Rumor freshRumor = node.getRumor();
				neighbour.setRumor(freshRumor);
			}
			try {
				graphView.repaint();
				Thread.sleep(250);
			} catch (InterruptedException e) {}
		}
		return node.getNeighbours().size();
	}

	public FloodAlgorithm(Node node, int n, double diameter, int maxDegree){
		this.node = node;
		this.n = n;
		this.diameter = diameter;
		this.maxDegree = maxDegree;
	}

	@Override
	public List<String> getAlreadyTold() {
		return new ArrayList<String>();
		
	}

	@Override
	public void setActive(boolean isActive) {
		// TODO Auto-generated method stub
		
	}
}
