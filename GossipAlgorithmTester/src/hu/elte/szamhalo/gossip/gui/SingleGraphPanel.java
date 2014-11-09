package hu.elte.szamhalo.gossip.gui;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SingleGraphPanel extends JFrame implements MouseListener{
	
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1454737796901375663L;

	private int nextStep = 0;

	private GraphView graphView;
	
	
	
	public SingleGraphPanel(GraphView graphView){
    	this.setTitle("Gossip algoritmus tesztel� program");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.graphView = graphView;
    	FlowLayout flowLayout = new FlowLayout();
        this.setLayout(flowLayout);
        this.getContentPane().add(graphView.getVisualizationViewer());
        this.getContentPane().add(new ControlPanel(this,true));
        this.setSize(1000,1000);
        this.setVisible(true);
    
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Gr�f 1");
        
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

        this.setJMenuBar(menuBar);
    }
	
	public void setNextstep(int nextStep){
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


}