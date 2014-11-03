package hu.elte.szamhalo.gossip.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MultiGraphPanel extends JFrame {
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1454737796901375663L;

	public MultiGraphPanel(GraphView graphView1, GraphView graphView2){
    	this.setTitle("Gossip Algorithm Tester");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        this.getContentPane().add(graphView1.getVisualizationViewer(), BorderLayout.EAST);
        this.getContentPane().add(graphView2.getVisualizationViewer(), BorderLayout.WEST);
        this.pack();
        this.setVisible(true);
    }
}
