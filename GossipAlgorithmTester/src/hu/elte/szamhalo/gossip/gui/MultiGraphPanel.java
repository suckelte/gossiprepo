package hu.elte.szamhalo.gossip.gui;

import hu.elte.szamhalo.gossip.RumorVerifier;
import hu.elte.szamhalo.gossip.io.GraphGenerator;
import hu.elte.szamhalo.gossip.io.GraphLoader;
import hu.elte.szamhalo.gossip.util.GraphUtil;
import hu.elte.szamhalo.gossip.vo.ChoosingAlgorithmEnum;
import hu.elte.szamhalo.gossip.vo.IGraphPanel;
import hu.elte.szamhalo.gossip.vo.LayoutEnum;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiGraphPanel extends JFrame  implements MouseListener, Runnable, IGraphPanel{
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1454737796901375663L;
	
	private int nextStep = 0;

	private GraphView graphView1;
	
	private GraphView graphView2;

	private  SortedSet<Node>  graph1;
	
	private  SortedSet<Node>  graph2;

	private Rumor rumor1;
	
	private Rumor rumor2;

	private int klocal;

	private ControlPanel controlPanel;
	
	private JMenu stepMenu1;
	private JMenu stepMenu2;

	public MultiGraphPanel(){
		this.graph1 = null;
		this.graph2 = null;
		this.setTitle("Gossip algoritmus tesztelõ program");
		
        
        FlowLayout flowLayout = new FlowLayout();
        this.setLayout(flowLayout);
        this.setSize(1500,1000);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setVisible(true);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Gráf 1 szerkesztés");
        
        JMenuItem newNodeMenu = new JMenuItem("Új csomópont");
        menu.add(newNodeMenu);
        newNodeMenu.addMouseListener(this);
        JMenuItem deleteNodeMenu = new JMenuItem("Csomópont törlés");
        menu.add(deleteNodeMenu);
        deleteNodeMenu.addMouseListener(this);
        JMenuItem newEdgeMenu = new JMenuItem("Új él");
        menu.add(newEdgeMenu);
        newEdgeMenu.addMouseListener(this);
        JMenuItem deleteEdgeMenu = new JMenuItem("Él törlés");
        menu.add(deleteEdgeMenu);
        deleteEdgeMenu.addMouseListener(this);
        menuBar.add(menu);
        
        stepMenu1 = new JMenu("Lépés(gráf 1): 0");
        menuBar.add(stepMenu1);
        
        JMenu menu2 = new JMenu("Gráf 2 szerkesztés");
        
        JMenuItem newNodeMenu2 = new JMenuItem("Új csomópont(2)");
        menu2.add(newNodeMenu2);
        newNodeMenu2.addMouseListener(this);
        JMenuItem deleteNodeMenu2 = new JMenuItem("Csomópont törlés(2)");
        menu2.add(deleteNodeMenu2);
        deleteNodeMenu2.addMouseListener(this);
        JMenuItem newEdgeMenu2 = new JMenuItem("Új él(2)");
        menu2.add(newEdgeMenu2);
        newEdgeMenu2.addMouseListener(this);
        JMenuItem deleteEdgeMenu2 = new JMenuItem("Él törlés(2)");
        menu2.add(deleteEdgeMenu2);
        deleteEdgeMenu2.addMouseListener(this);
        menuBar.add(menu2);
        
        
        
        stepMenu2 = new JMenu("Lépés(gráf 2): 0");
        menuBar.add(stepMenu2);

        this.setJMenuBar(menuBar);
    }
	
	public MultiGraphPanel(LayoutEnum le, ChoosingAlgorithmEnum cae, ChoosingAlgorithmEnum cae2, int klocal, String inputvalue) {
		this();
		this.klocal = klocal;
		if(inputvalue.contains(":")){
			try {
				graph1 = GraphLoader.getJSONGraph(inputvalue);
				graph2 = GraphLoader.getJSONGraph(inputvalue);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				graph1 = GraphGenerator.getComplexGraph(new Integer[]{4,2,6,4}, new Integer[]{70,50,70,50});
				graph2 = GraphGenerator.getComplexGraph(new Integer[]{4,2,6,4}, new Integer[]{70,50,70,50});
			}
		}else{
			graph1 = GraphGenerator.getComplexGraph(inputvalue);
			graph2 = GraphGenerator.getComplexGraph(inputvalue);
		}
		GraphUtil.setChoosingAlgorithm(graph1, cae);
		GraphUtil.setChoosingAlgorithm(graph2, cae2);
		rumor1 = GraphUtil.setRandomRumor(graph1);
		rumor2 = GraphUtil.setRandomRumor(graph2);
		graphView1 = new GraphView(graph1,900,900,le);
		graphView2 = new GraphView(graph2,900,900,le);
		
		BorderLayout borderLayout = new BorderLayout();
		JPanel multipanel = new JPanel(borderLayout);
        

		multipanel.add(graphView1.getVisualizationViewer(), BorderLayout.WEST);
		multipanel.add(graphView2.getVisualizationViewer(), BorderLayout.EAST);
		
		this.getContentPane().add(multipanel);
		
		controlPanel = new ControlPanel(this,true);
		
		this.getContentPane().add(controlPanel);
		
		new Thread(this).start();
	}

	public void setNextstep(int nextStep){
		System.out.print("set:" + nextStep);
		this.nextStep = nextStep;
	}	
    
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(((JMenuItem)e.getSource()).getText().equals("Új csomópont")){
		    JTextField xField = new JTextField(5);
		    JTextField yField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Új csomópont:"));
		      myPanel.add(xField);
		      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		      myPanel.add(new JLabel("Szomszéd csomópont:"));
		      myPanel.add(yField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Új csomópont", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView1.addNode(xField.getText(), yField.getText());
		    	  graphView1.repaint();
		    	  graphView1.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("Csomópont törlés")){
			JTextField xField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Törlendõ csomópont:"));
		      myPanel.add(xField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Csomópont törlés", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView1.removeNode(xField.getText());
		    	  graphView1.repaint();
		    	  graphView1.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("Új él")){
			JTextField xField = new JTextField(5);
		    JTextField yField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Elsõ csomópont:"));
		      myPanel.add(xField);
		      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		      myPanel.add(new JLabel("Második csomópont:"));
		      myPanel.add(yField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Új él", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView1.addEdge(xField.getText(), yField.getText());
		    	  graphView1.repaint();
		    	  graphView1.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("Él törlés")){
			JTextField xField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Törlendõ él:"));
		      myPanel.add(xField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Él törlés", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView1.removeEdge(xField.getText());
		    	  graphView1.repaint();
		    	  graphView1.getVisualizationViewer().repaint();
		      }
		}else if(((JMenuItem)e.getSource()).getText().equals("Új csomópont(2)")){
		    JTextField xField = new JTextField(5);
		    JTextField yField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Új csomópont:"));
		      myPanel.add(xField);
		      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		      myPanel.add(new JLabel("Szomszéd csomópont:"));
		      myPanel.add(yField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Új csomópont", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView2.addNode(xField.getText(), yField.getText());
		    	  graphView2.repaint();
		    	  graphView2.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("Csomópont törlés(2)")){
			JTextField xField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Törlendõ csomópont:"));
		      myPanel.add(xField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Csomópont törlés", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView2.removeNode(xField.getText());
		    	  graphView2.repaint();
		    	  graphView2.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("Új él(2)")){
			JTextField xField = new JTextField(5);
		    JTextField yField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Elsõ csomópont:"));
		      myPanel.add(xField);
		      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		      myPanel.add(new JLabel("Második csomópont:"));
		      myPanel.add(yField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Új él", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView2.addEdge(xField.getText(), yField.getText());
		    	  graphView2.repaint();
		    	  graphView2.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("Él törlés(2)")){
			JTextField xField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Törlendõ él:"));
		      myPanel.add(xField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Él törlés", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView2.removeEdge(xField.getText());
		    	  graphView2.repaint();
		    	  graphView2.getVisualizationViewer().repaint();
		      }
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(2));
		} catch (InterruptedException e1) {}
		boolean done1 = false;
		boolean done2 = false;
		int step = 0;
		while(true){
			if(nextStep-- > 0){
				step++;
				if(!done1){
		        	for (Iterator<Node> it = graph1.iterator(); it.hasNext(); ) {
		        		Node node = it.next();
		        		node.getActiveAlgorithm().step();
		        	}
		        	for (Iterator<Node> it = graph1.iterator(); it.hasNext(); ) {
		        		Node node = it.next();
		        		if(node.getRumor() != null){
		        			node.getRumor().setFresh(false);
		        		}
		        	}
		        	graphView1.repaint();
		        	Node missingNode = RumorVerifier.verify(rumor1, klocal);
		        	if(missingNode != null){
		        		boolean failed = true;
		        		for (Iterator<Node> it = graph1.iterator(); it.hasNext(); ) {
			        		Node node = it.next();
			        		if(node.getActiveAlgorithm().isActive()  && node.getRumor() != null){
			        			failed = false;
			        			break;
			        		}
		        		}
		        		if(failed){
			        		done1 = true;
		        			controlPanel.setVerifier1Text("Failed!");
		        		}else{
		        			controlPanel.setVerifier1Text(missingNode.getNodeID());
		        		}
		        	}else{
		        		done1 = true;
		        		controlPanel.setVerifier1Text("Done!");
		        	}
		        	stepMenu1.setText("Lépés(gráf 1): " + step);
				}
	        	if(!done2){
		        	for (Iterator<Node> it = graph2.iterator(); it.hasNext(); ) {
		        		Node node = it.next();
		        		node.getActiveAlgorithm().step();
		        	}
		        	for (Iterator<Node> it = graph2.iterator(); it.hasNext(); ) {
		        		Node node = it.next();
		        		if(node.getRumor() != null){
		        			node.getRumor().setFresh(false);
		        		}
		        	}
		        	graphView2.repaint();
		        	Node missingNode = RumorVerifier.verify(rumor2, klocal);
		        	if(missingNode != null){
		        		boolean failed = true;
		        		for (Iterator<Node> it = graph2.iterator(); it.hasNext(); ) {
			        		Node node = it.next();
			        		if(node.getActiveAlgorithm().isActive() && node.getRumor() != null){
			        			failed = false;
			        			break;
			        		}
		        		}
		        		if(failed){
			        		done2 = true;
		        			controlPanel.setVerifier2Text("Failed!");
		        		}else{
		        			controlPanel.setVerifier2Text(missingNode.getNodeID());
		        		}
		        	}else{
		        		done2 = true;
		        		controlPanel.setVerifier2Text("Done!");
		        	}

		        	stepMenu2.setText("Lépés(gráf 2): " + step);
	        	}
			}
            try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(2));
			} catch (InterruptedException e) {}
        }
		
	}

}
