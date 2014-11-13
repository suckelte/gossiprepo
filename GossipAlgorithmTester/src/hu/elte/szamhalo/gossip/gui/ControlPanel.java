package hu.elte.szamhalo.gossip.gui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5569988446826483845L;
	
	private JTextField generateTextField;
	private SingleGraphPanel singleGraphPanel;
	
	
	public ControlPanel(SingleGraphPanel singleGraphPanel, boolean enableGenerating) {
		this.singleGraphPanel = singleGraphPanel;
    	this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		this.setMaximumSize(new Dimension(100,50));
    	
    	JButton stopSimulationButton = new JButton("Stop");
    	stopSimulationButton.addMouseListener(this);
    	stopSimulationButton.setSize(20, 20);
		JButton startSimulationButton = new JButton(">");
		startSimulationButton.addMouseListener(this);
		startSimulationButton.setSize(30, 20);
		JButton step1SimulationButton = new JButton(">|");
		step1SimulationButton.addMouseListener(this);
		step1SimulationButton.setSize(30, 20);
		

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
        add(stopSimulationButton, gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
        add(step1SimulationButton, gbc);

                
        gbc.gridx = 2;
        gbc.gridy = 0;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints.CENTER;
        add(startSimulationButton, gbc);
		
		if(enableGenerating){
			JButton generateSimulationButton = new JButton("Generálás");
			generateSimulationButton.addMouseListener(this);
			generateSimulationButton.setSize(30, 20);
			generateTextField = new JTextField("", 40);
			
			gbc.gridx = 0;
	        gbc.gridy = 1;
//	        gbc.fill = GridBagConstraints.HORIZONTAL;
//	        gbc.anchor = GridBagConstraints.CENTER;
	        add(generateSimulationButton, gbc);
	        
	        gbc.gridx = 1;
	        gbc.gridy = 1;
//	        gbc.fill = GridBagConstraints.HORIZONTAL;
//	        gbc.anchor = GridBagConstraints.CENTER;
	        add(generateTextField, gbc);
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(((JButton)e.getSource()).getText().equals("Stop")){
			singleGraphPanel.setNextstep(0);
		}else if (((JButton)e.getSource()).getText().equals(">")){
			singleGraphPanel.setNextstep(Integer.MAX_VALUE);
		}else if (((JButton)e.getSource()).getText().equals(">|")){
			singleGraphPanel.setNextstep(1);
		}else if (((JButton)e.getSource()).getText().equals("Generálás")){
			singleGraphPanel.setNextstep(0);
			generateTextField.getText();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
