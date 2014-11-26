package hu.elte.szamhalo.gossip.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.elte.szamhalo.gossip.gui.GraphView;
import hu.elte.szamhalo.gossip.vo.IChoosingAlgorithm;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

public class DeterministicAlgorithm implements IChoosingAlgorithm {

	private Node node;
	private List<String> alreadyTold = new ArrayList<String>();
	private int n;
	private double diameter;
	private int maxDegree;

	
	@Override
	public int step(GraphView graphView) {
		int toldRumor = 0;
			if(alreadyTold.size() != node.getNeighbours().size()){
				int nodeIndex = alreadyTold.size();
				for (Iterator<Node> it = node.getNeighbours().iterator(); it.hasNext(); ) {
					Node neighbour = it.next();
					if(nodeIndex-- == 0){
						alreadyTold.add(neighbour.getNodeID());
						break;
					}
				}
			}
			for (Iterator<Node> it = node.getNeighbours().iterator(); it.hasNext(); ) {
				Node neighbour = it.next();
				if(alreadyTold.contains(neighbour.getNodeID())){
					if(node.getRumor() != null && neighbour.getRumor() == null){
						Rumor freshRumor = new Rumor();
						freshRumor.setSourceNode(node.getRumor().getSourceNode());
						neighbour.setRumor(freshRumor);
					}
					try {
						graphView.repaint();
						Thread.sleep(250);
					} catch (InterruptedException e) {}
				toldRumor++;
				}
			}		
		return toldRumor;
	}

	public DeterministicAlgorithm(Node node, int n, double diameter, int maxDegree){
		this.node = node;
		this.n = (int) Math.round(Math.pow(Math.log10(n),2));
		if(this.n>node.getNeighbours().size()){
			this.n = node.getNeighbours().size();
		}
		this.diameter = diameter;
		this.maxDegree = maxDegree;
	}
	
	/**
	 * @return the alreadyTold
	 */
	public List<String> getAlreadyTold() {
		return new ArrayList<String>();
	}
	
}
