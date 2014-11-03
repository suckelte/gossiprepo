package hu.elte.szamhalo.gossip.vo;

import java.util.SortedSet;

public class Node implements Comparable<Node>{
	/**
	 * ID of the Node (also the ID of component in the GUI object)
	 */
	private String nodeID;
	/**
	 * list of neighbours
	 */
	private SortedSet<Node> neighbours;
	/**
	 * currently active algorithm 
	 */
	private IChoosingAlgorithm activeAlgorithm;
	/**
	 * list of Rumors that are known to the node
	 */
	private Rumor rumor;
	
	
	public Node(String nodeID, SortedSet<Node> neighbours,
			IChoosingAlgorithm activeAlgorithm, Rumor rumor) {
		super();
		this.nodeID = nodeID;
		this.neighbours = neighbours;
		this.activeAlgorithm = activeAlgorithm;
		this.rumor = rumor;
	}
	/*
	 * {getter/setter list}
	 */
	
	/**
	 * @return the nodeID
	 */
	public String getNodeID() {
		return nodeID;
	}
	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	/**
	 * @return the neighbours
	 */
	public SortedSet<Node> getNeighbours() {
		return neighbours;
	}
	/**
	 * @param neighbours the neighbours to set
	 */
	public void setNeighbours(SortedSet<Node> neighbours) {
		this.neighbours = neighbours;
	}
	/**
	 * @return the activeAlgorithm
	 */
	public IChoosingAlgorithm getActiveAlgorithm() {
		return activeAlgorithm;
	}
	/**
	 * @param activeAlgorithm the activeAlgorithm to set
	 */
	public void setActiveAlgorithm(IChoosingAlgorithm activeAlgorithm) {
		this.activeAlgorithm = activeAlgorithm;
	}
	

	/**
	 * @return the rumor
	 */
	public Rumor getRumor() {
		return rumor;
	}

	/**
	 * @param rumor the rumor to set
	 */
	public void setRumor(Rumor rumor) {
		this.rumor = rumor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "/" + this.getNodeID();
	}

	@Override
	public int compareTo(Node o) {
		if(o == null || o.getNodeID() == null){
			return 1;
		}
		if(!o.getNodeID().equals(this.getNodeID())){
			return 1;
		}
		return 0;
	}

}
