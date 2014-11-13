package hu.elte.szamhalo.gossip.gui;

import hu.elte.szamhalo.gossip.vo.LayoutEnum;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphView {

	 private Graph<String, String> graphView;
	 Rumor r = null;
	 private SortedSet<Node> graphModel;
	 private VisualizationViewer<String, String> visualizationViewer;
	 private int width;
	 private int height;
	 private LayoutEnum layoutEnum;
	 
	public GraphView(SortedSet<Node> graphModel, int width, int height, LayoutEnum layoutEnum) {
		super();
		this.width = width;
		this.height = height;
		this.layoutEnum = layoutEnum;
		this.graphView = initGraphViewObject(graphModel);
		visualizationViewer = initView();
	}
	 
	private Graph<String, String> initGraphViewObject(SortedSet<Node> graphModel){
		this.graphModel = graphModel;
		Graph<String, String> graphView = new SparseGraph<String, String>();
		for (Iterator<Node> it = this.graphModel.iterator(); it.hasNext(); ) {
    		Node node = it.next();
    		graphView.addVertex(node.getNodeID());
    	}
        for (Iterator<Node> it = this.graphModel.iterator(); it.hasNext(); ) {
    		Node node = it.next();	
    		for(Node n : node.getNeighbours()){
    			graphView.addEdge(node.getNodeID() + "-" + n.getNodeID(), node.getNodeID(), n.getNodeID());
    		}
    	}
        return graphView;
	}
	
	private VisualizationViewer<String, String> initView(){
      Layout<String, String> layout = null;
      if(LayoutEnum.CIRCLE.equals(layoutEnum)){
    	  layout = new CircleLayout<String, String>(graphView);  
      }else{
    	  layout = new ISOMLayout<String, String>(graphView);
      } 
      layout.setSize(new Dimension(width,height));
      final VisualizationViewer<String,String> vv = new VisualizationViewer<String,String>(layout);
      vv.setPreferredSize(new Dimension(width,height));    
      
      DefaultModalGraphMouse<String, String> graphMouse = new DefaultModalGraphMouse<String, String>();
      graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
      vv.setGraphMouse(graphMouse);
      
      Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
          public Paint transform(String nodeId) {
          	for (Iterator<Node> it = graphModel.iterator(); it.hasNext(); ) {
          		Node node = it.next();
          		if(node.getNodeID().equals(nodeId)){
          			if(node.getRumor() != null){
          				if(!node.getActiveAlgorithm().isActive()){
          					return Color.YELLOW;
          				}else{
          					return Color.GREEN;
          				}
          			}else{
          				return Color.RED;
          			}
          		}
          	}
              return Color.GRAY;
          }
      };
      final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.JOIN_MITER,
              BasicStroke.JOIN_BEVEL, 10.0f, null, 0);
         Transformer<String, Stroke> edgeStrokeTransformer = new Transformer<String, Stroke>() {
             public Stroke transform(String s) {
                 return edgeStroke;
             }
         };
         vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
         vv.getRenderContext().setVertexShapeTransformer(new Transformer<String, Shape>() {
             @Override
             public Shape transform(String st) {
             	Ellipse2D.Double ellipse 
                 	= new Ellipse2D.Double(-15, -15, 30, 30);
                 return ellipse;
             }});
         vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
         vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
         vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
         vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
         
         return vv;
	}
	
	public void repaint(){
		visualizationViewer.repaint();
	}
	
	public VisualizationViewer<String,String> getVisualizationViewer(){
		return visualizationViewer;
	}
	
	public void addNode(String nodeId,String neighbourNodeId){
		for (Iterator<Node> it = graphModel.iterator(); it.hasNext(); ) {
    		Node node = it.next();
    		System.out.println(node.getNodeID());
    		if(node.getNodeID().equals(neighbourNodeId)){
    			graphView.addVertex(nodeId);
    			graphView.addEdge(nodeId + "-" + neighbourNodeId, nodeId, neighbourNodeId);
    			TreeSet<Node> neighbours = new TreeSet<Node>();
    			neighbours.add(node);
				Node newNode = new Node(nodeId,neighbours,null, null);
				node.getNeighbours().add(newNode);
    			newNode.setActiveAlgorithm(node.getActiveAlgorithm());
    			return;
    		}
		}
	}
	
	public void removeNode(String nodeId){
		graphView.removeVertex(nodeId);
	}
	
	public void addEdge(String nodeId,String neighbourNodeId){
		graphView.addEdge(nodeId + "-" + neighbourNodeId, nodeId, neighbourNodeId);
	}
	
	public void removeEdge(String edgeId){
		graphView.removeEdge(edgeId);
	}
}
