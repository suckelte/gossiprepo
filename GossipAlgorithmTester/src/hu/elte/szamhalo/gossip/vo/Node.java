package hu.elte.szamhalo.gossip.vo;

import java.util.List;

public class Node implements Comparable<Node>{
	/**
	 * ID of the Node (also the ID of component in the GUI object)
	 */
	private String nodeID;
	/**
	 * list of neighbours
	 */
	private List<Node> neighbours;
	/**
	 * currently active algorithm 
	 */
	private IChoosingAlgorithm activeAlgorithm;
	/**
	 * list of Rumors that are known to the node
	 */
	private List<Rumor> knownRumors;
	
	public Node(String nodeID, List<Node> neighbours,
			IChoosingAlgorithm activeAlgorithm, List<Rumor> knownRumors) {
		super();
		this.nodeID = nodeID;
		this.neighbours = neighbours;
		this.activeAlgorithm = activeAlgorithm;
		this.knownRumors = knownRumors;
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
	public List<Node> getNeighbours() {
		return neighbours;
	}
	/**
	 * @param neighbours the neighbours to set
	 */
	public void setNeighbours(List<Node> neighbours) {
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
	 * @return the knownRumors
	 */
	public List<Rumor> getKnownRumors() {
		return knownRumors;
	}
	/**
	 * @param knownRumors the knownRumors to set
	 */
	public void setKnownRumors(List<Rumor> knownRumors) {
		this.knownRumors = knownRumors;
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
		if(o == null){
			return 1;
		}
		if(!o.getNodeID().equals(this.getNodeID())){
			return 1;
		}
		return 0;
	}
	
	
}
