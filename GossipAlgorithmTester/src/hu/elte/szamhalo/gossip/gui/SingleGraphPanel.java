package hu.elte.szamhalo.gossip.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class SingleGraphPanel extends JFrame {
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1454737796901375663L;

	public SingleGraphPanel(GraphView graphView){
    	this.setTitle("Gossip Algorithm Tester");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        this.getContentPane().add(graphView.getVisualizationViewer(), BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }
}
