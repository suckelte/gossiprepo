package hu.elte.szamhalo.gossip.vo;

import hu.elte.szamhalo.gossip.gui.GraphView;

import java.util.List;

public interface IChoosingAlgorithm {
	/**
	 * makes a step in spreading the rumor
	 * @param graphView 
	 * @return how many neighbours did it tell the rumor 
	 */
	public int step(GraphView graphView);
	
	/**
	 * the list of nodes that it already told
	 * @return
	 */
	public List<String> getAlreadyTold();
	
	public void setActive(boolean isActive);
}
