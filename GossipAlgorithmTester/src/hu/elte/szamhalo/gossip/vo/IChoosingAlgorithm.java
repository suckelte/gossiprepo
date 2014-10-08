package hu.elte.szamhalo.gossip.vo;

public interface IChoosingAlgorithm {
	/**
	 * makes a step in spreading the rumor
	 * @return how many neighbours did it tell the rumor 
	 */
	public int step();
	/**
	 * is the node activelly spreading the rumor
	 */
	public boolean isActive();
	
}
