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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
	
	private Integer stepCount = 0;

	private ControlPanel controlPanel;

	private JMenu stepMenu;
	
	private Boolean done = false;
	
	private Boolean stepEnded = true;

	private int threadReadyNumber = 0;
	
	private SingleGraphPanel(){
		this.graph = null;
		this.setTitle("Gossip algoritmus tesztel� program");
        this.setLayout(new BorderLayout());
        this.setSize(850,920);
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
		if(inputvalue.contains("grph")){
			try {
				graph = GraphLoader.getJSONGraph(inputvalue);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}else{
			graph = GraphGenerator.getComplexGraph(inputvalue);
		}
		rumor = GraphUtil.setN1Rumor(graph);
		graphView = new GraphView(graph,800,800,le);
		GraphUtil.setChoosingAlgorithm(graph, cae, graphView.getDiameter());
		
		controlPanel = new ControlPanel(this);
        
        JPanel cp = new JPanel(new GridLayout(1, 3));
        cp.add(new JLabel(""));
        cp.add(controlPanel);
        cp.add(new JLabel(""));
        
		this.getContentPane().add(cp,BorderLayout.NORTH);
		this.getContentPane().add(graphView.getVisualizationViewer(),BorderLayout.SOUTH);
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
		while(!getDone()){
			if(nextStep-- > 0 && getStepEnded()){
				setStepEnded(false);
				controlPanel.startSimulationButton.setEnabled(false);
				controlPanel.step1SimulationButton.setEnabled(false);
				Set<Thread> threadSet = new HashSet<Thread>(); 
	        	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
	        		final Node node = it.next();
	        		threadSet.add(new Thread(new Runnable() {
						@Override
						public void run() {
							setStepCount(node.getActiveAlgorithm().step(graphView) + getStepCount());
							setThreadReadyNumber();
						}
					}));
	        	}
	        	for (Iterator<Thread> it = threadSet.iterator(); it.hasNext(); ) {
	        		final Thread th = it.next();
	        		th.start();
	        	}
	        	new Thread(new Runnable() {
					
					@Override
					public void run() {
						graphView.repaint();
			        	do{
			        		try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
			        	}while(graph.size() != getThreadReadyNumber());
			        	Node missingNode = RumorVerifier.verify(rumor, klocal);
			        	if(missingNode != null){
			        		controlPanel.setVerifier1Text(missingNode.getNodeID());
			        	}else{
			        		setDone(true);
			        		controlPanel.setVerifier1Text("K�sz!(" + stepCount + ")");
			        	}
			        	
			        	controlPanel.startSimulationButton.setEnabled(true);
						controlPanel.step1SimulationButton.setEnabled(true);
						threadReadyNumber = 0;
						setStepEnded(true);
					}

				}).start();
	        	stepMenu.setText("L�p�s: " + ++step);
			}
			if(nextStep > 10){
	            try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(5));
				} catch (InterruptedException e) {}
			}
        }
		
	}

	private synchronized Integer getStepCount() {
		return stepCount;
	}

	private synchronized void setStepCount(Integer stepCount) {
		this.stepCount = stepCount;
	}

	public synchronized int getThreadReadyNumber() {
		return threadReadyNumber;
	}

	public  synchronized void setThreadReadyNumber() {
		this.threadReadyNumber += 1;
	}

	public synchronized Boolean getDone() {
		return done;
	}

	public synchronized void setDone(Boolean done) {
		this.done = done;
	}

	public synchronized Boolean getStepEnded() {
		return stepEnded;
	}

	public synchronized void setStepEnded(Boolean stepEnded) {
		this.stepEnded = stepEnded;
	}

}