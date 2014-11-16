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
import java.awt.GridLayout;
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

public class SingleGraphPanel extends JFrame implements MouseListener, Runnable, IGraphPanel{
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1454737796901375663L;

	private int nextStep = 0;

	private GraphView graphView;

	private  SortedSet<Node>  graph;

	private Rumor rumor;

	private int klocal;

	private ControlPanel controlPanel;

	private JMenu stepMenu;
	
	private SingleGraphPanel(){
		this.graph = null;
		this.setTitle("Gossip algoritmus tesztel� program");
        this.setLayout(new BorderLayout());
        this.setSize(930,1000);
        this.setVisible(true);
    
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Gr�f szerkeszt�s");
        
        JMenuItem newNodeMenu = new JMenuItem("�j csom�pont");
        menu.add(newNodeMenu);
        newNodeMenu.addMouseListener(this);
        JMenuItem deleteNodeMenu = new JMenuItem("Csom�pont t�rl�s");
        menu.add(deleteNodeMenu);
        deleteNodeMenu.addMouseListener(this);
        JMenuItem newEdgeMenu = new JMenuItem("�j �l");
        menu.add(newEdgeMenu);
        newEdgeMenu.addMouseListener(this);
        JMenuItem deleteEdgeMenu = new JMenuItem("�l t�rl�s");
        menu.add(deleteEdgeMenu);
        deleteEdgeMenu.addMouseListener(this);
        menuBar.add(menu);

        stepMenu = new JMenu("L�p�s: 0");
        menuBar.add(stepMenu);
        this.setJMenuBar(menuBar);
	}
	
	public SingleGraphPanel(LayoutEnum le, ChoosingAlgorithmEnum cae, int klocal, String inputvalue) {
		this();
		this.klocal = klocal;
		if(inputvalue.contains(":")){
			try {
				graph = GraphLoader.getJSONGraph(inputvalue);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				graph = GraphGenerator.getComplexGraph(new Integer[]{4,2,6,4}, new Integer[]{70,50,70,50});
			}
		}else{
			graph = GraphGenerator.getComplexGraph(inputvalue);
		}
		GraphUtil.setChoosingAlgorithm(graph, cae);
		rumor = GraphUtil.setRandomRumor(graph);
		graphView = new GraphView(graph,880,880,le);
		this.getContentPane().add(graphView.getVisualizationViewer(),BorderLayout.NORTH);
		controlPanel = new ControlPanel(this);
        
        JPanel cp = new JPanel(new GridLayout(1, 3));
        cp.add(new JLabel(""));
        cp.add(controlPanel);
        cp.add(new JLabel(""));
		this.getContentPane().add(cp,BorderLayout.SOUTH);
		
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
		if(((JMenuItem)e.getSource()).getText().equals("�j csom�pont")){
		    JTextField xField = new JTextField(5);
		    JTextField yField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("�j csom�pont:"));
		      myPanel.add(xField);
		      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		      myPanel.add(new JLabel("Szomsz�d csom�pont:"));
		      myPanel.add(yField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "�j csom�pont", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView.addNode(xField.getText(), yField.getText());
		    	  graphView.repaint();
		    	  graphView.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("Csom�pont t�rl�s")){
			JTextField xField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("T�rlend� csom�pont:"));
		      myPanel.add(xField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "Csom�pont t�rl�s", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView.removeNode(xField.getText());
		    	  graphView.repaint();
		    	  graphView.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("�j �l")){
			JTextField xField = new JTextField(5);
		    JTextField yField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("Els� csom�pont:"));
		      myPanel.add(xField);
		      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
		      myPanel.add(new JLabel("M�sodik csom�pont:"));
		      myPanel.add(yField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "�j �l", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView.addEdge(xField.getText(), yField.getText());
		    	  graphView.repaint();
		    	  graphView.getVisualizationViewer().repaint();
		      }
		}else if (((JMenuItem)e.getSource()).getText().equals("�l t�rl�s")){
			JTextField xField = new JTextField(5);
		      
			JPanel myPanel = new JPanel();
		      myPanel.add(new JLabel("T�rlend� �l:"));
		      myPanel.add(xField);

		      int result = JOptionPane.showConfirmDialog(null, myPanel, 
		               "�l t�rl�s", JOptionPane.OK_CANCEL_OPTION);
		      
		      if (result == JOptionPane.OK_OPTION) {
		    	  graphView.removeEdge(xField.getText());
		    	  graphView.repaint();
		    	  graphView.getVisualizationViewer().repaint();
		      }
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(TimeUnit.SECONDS.toMillis(1));
		} catch (InterruptedException e1) {}
		int step = 0;
		int stepCount = 0;
		boolean done = false;
		while(!done){
			if(nextStep-- > 0){
	        	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
	        		Node node = it.next();
	        		stepCount += node.getActiveAlgorithm().step();
	        	}
	        	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
	        		Node node = it.next();
	        		if(node.getRumor() != null){
	        			node.getRumor().setFresh(false);
	        		}
	        	}
	        	graphView.repaint();
	        	Node missingNode = RumorVerifier.verify(rumor, klocal);
	        	if(missingNode != null){
	        		boolean failed = true;
	        		for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
		        		Node node = it.next();
		        		if(node.getActiveAlgorithm().isActive()  && node.getRumor() != null){
		        			failed = false;
		        			break;
		        		}
	        		}
	        		if(failed){
		        		done = true;
	        			controlPanel.setVerifier1Text("Sikertelen!");
	        		}else{
	        			controlPanel.setVerifier1Text(missingNode.getNodeID());
	        		}
	        	}else{
	        		done = true;
	        		controlPanel.setVerifier1Text("K�sz!(" + stepCount + ")");
	        	}
	        	stepMenu.setText("L�p�s: " + ++step);
			}
            try {
				Thread.sleep(TimeUnit.SECONDS.toMillis(1));
			} catch (InterruptedException e) {}
        }
		
	}

}