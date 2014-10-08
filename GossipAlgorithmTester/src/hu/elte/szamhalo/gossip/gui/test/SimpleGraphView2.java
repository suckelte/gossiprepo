package hu.elte.szamhalo.gossip.gui.test;
/*
 * SimpleGraphView.java
 *
 * Created on March 8, 2007, 7:49 PM
 *
 * Copyright March 8, 2007 Grotto Networking
 */


import hu.elte.szamhalo.gossip.RumorVerifier;
import hu.elte.szamhalo.gossip.io.GraphGenerator;
import hu.elte.szamhalo.gossip.util.RumorUtil;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class SimpleGraphView2 {
    Graph<String, String> g;
    static Rumor r = null;
	private static SortedSet<Node> graph;
    public SimpleGraphView2() throws IOException {
        
    }
    
    /**
     * @param args the command line arguments
     * @throws IOException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
        // Layout<V, E>, VisualizationComponent<V,E>
//        Layout<Integer, String> layout = new CircleLayout(sgv.g);
        Layout<String, String> layout = new ISOMLayout(sgv.g);
        layout.setSize(new Dimension(900,900));
        final VisualizationViewer<String,String> vv = new VisualizationViewer<String,String>(layout);
        vv.setPreferredSize(new Dimension(900,900));       
        // Setup up a new vertex to paint transformer...
        Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
            public Paint transform(String i) {
            	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
            		Node node = it.next();
            		if(node.getNodeID().equals(i)){
            			if(RumorUtil.knowsThatRumor(node, "rumor1")){
            				return Color.GREEN;
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
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);    
        
//        vv.addGraphMouseListener(new GraphMouseListener<String>() {
//
//            @Override
//            public void graphClicked(String v, MouseEvent me) {
//                if (me.getButton() == MouseEvent.BUTTON1 && me.getClickCount() == 2) {
//                	
//                	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
//                		Node node = it.next();
//                		if(node.getNodeID().equals(v)){
//                			node.getKnownRumors().add(new Rumor());
//                			vv.repaint();
//                			return;
//                		}
//                	}
//                    System.out.println("Double clicked "+ v.getClass().getName());
//                }
//                me.consume();
//            }
//            
//            
//			@Override
//			public void graphPressed(String arg0, MouseEvent arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void graphReleased(String arg0, MouseEvent arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//        });
        
        JFrame frame = new JFrame("Simple Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout();
        frame.setLayout(borderLayout);
        frame.getContentPane().add(vv, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        for (int i = 1; i < 10; i++) {
        	List<String> thisroundnodes = new ArrayList<String>();
        	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
        		Node node = it.next();
        		if(RumorUtil.knowsThatRumor(node, "rumor1") && !thisroundnodes.contains(node.getNodeID())){
        			for (Iterator<Node> it2 = node.getNeighbours().iterator(); it2.hasNext(); ) {
        				Node neighbour = it2.next();
        				if(!RumorUtil.knowsThatRumor(neighbour, "rumor1")){{
        					neighbour.getKnownRumors().add(node.getKnownRumors().first());
        					thisroundnodes.add(neighbour.getNodeID());
        				}
        		}}}
        	}
        	vv.repaint();
        	frame.repaint();
        	System.out.println(RumorVerifier.verify(r, 3));
        	System.out.println("sleep1");
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            System.out.println("sleep2");
        }
    }
    
}