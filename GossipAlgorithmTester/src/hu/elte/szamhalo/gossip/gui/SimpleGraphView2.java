package hu.elte.szamhalo.gossip.gui;
/*
 * SimpleGraphView.java
 *
 * Created on March 8, 2007, 7:49 PM
 *
 * Copyright March 8, 2007 Grotto Networking
 */


import hu.elte.szamhalo.gossip.io.GraphGenerator;
import hu.elte.szamhalo.gossip.vo.Node;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class SimpleGraphView2 {
    Graph<String, String> g;
    public SimpleGraphView2() throws IOException {
        g = new SparseGraph<String, String>();

//        final SortedSet<Node> graph = GraphLoader.getGraph("input/input2.grph");
        final SortedSet<Node> graph = GraphGenerator.getComplexGraph(new Integer[]{4,4,6,3}, new Integer[]{30,70,20,70});
        
		for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
    		Node node = it.next();
    		g.addVertex(node.getNodeID());
    	}
        for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
    		Node node = it.next();	
    		for(Node n : node.getNeighbours()){
    			g.addEdge(node.getNodeID() + "-" + n.getNodeID(), node.getNodeID(), n.getNodeID());
    		}
    	}
        
        
    }
    
    /**
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
        // Layout<V, E>, VisualizationComponent<V,E>
//        Layout<Integer, String> layout = new CircleLayout(sgv.g);
        Layout<String, String> layout = new ISOMLayout(sgv.g);
        layout.setSize(new Dimension(900,900));
        BasicVisualizationServer<String,String> vv = new BasicVisualizationServer<String,String>(layout);
        vv.setPreferredSize(new Dimension(900,900));       
        // Setup up a new vertex to paint transformer...
        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
            public Paint transform(String i) {
                return Color.GREEN;
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
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);        
        
        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);     
    }
    
}