package hu.elte.szamhalo.gossip.vo;

import java.util.List;

public interface IChoosingAlgorithm {
	/**
	 * makes a step in spreading the rumor
	 * @return how many neighbours did it tell the rumor 
	 */
	public int step();
	/**
	 * is the node actively spreading the rumor
	 */
	public boolean isActive();
	
	/**
	 * the list of nodes that it already told
	 * @return
	 */
	public List<String> getAlreadyTold();
}
