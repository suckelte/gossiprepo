package hu.elte.szamhalo.gossip.vo;

public class Rumor {
	/**
	 * the source of the Rumor
	 */
	private Node sourceNode;
	
	/**
	 * @return the sourceNode
	 */
	public Node getSourceNode() {
		return sourceNode;
	}
	/**
	 * @param sourceNode the sourceNode to set
	 */
	public void setSourceNode(Node sourceNode) {
		this.sourceNode = sourceNode;
	}
}
