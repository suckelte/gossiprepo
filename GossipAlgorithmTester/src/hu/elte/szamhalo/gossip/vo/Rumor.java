package hu.elte.szamhalo.gossip.vo;

public class Rumor  implements Comparable<Rumor>{
	/**
	 * id of rumor
	 */
	private String id;
	/**
	 * the source of the Rumor
	 */
	private Node sourceNode;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	
	@Override
	public int compareTo(Rumor o) {
		if(o == null || o.getId() == null){
			return 1;
		}
		if(!o.getId().equals(this.getId())){
			return 1;
		}
		return 0;
	}
}
