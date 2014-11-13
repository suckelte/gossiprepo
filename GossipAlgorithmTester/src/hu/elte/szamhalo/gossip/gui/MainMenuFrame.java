package hu.elte.szamhalo.gossip.gui;

import hu.elte.szamhalo.gossip.vo.ChoosingAlgorithmEnum;
import hu.elte.szamhalo.gossip.vo.LayoutEnum;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class MainMenuFrame extends JFrame {
	private String fileName = "";
	private JLabel fileLabel;
	private ButtonGroup group;

	public MainMenuFrame(){
    	this.setTitle("Gossip Algorithm Tester");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout gridLayout = new GridLayout(4,2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        this.setLayout(gridLayout);
        
        JButton openfilePanelButton = new JButton("Bet�lt�s f�jlb�l");
        this.fileLabel = new JLabel("");
        
        JRadioButton singleButton = new JRadioButton("Egyszer� panel");
        singleButton.setActionCommand("1");
        JRadioButton multiButton = new JRadioButton("�sszehasonl�t� panel");
        multiButton.setActionCommand("2");
        singleButton.setSelected(true);

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(singleButton);
        group.add(multiButton);
        
        String[] comboStrings = { "CircleLayout", "ISOMLayout"};

		final JComboBox<String> layoutComboBox = new JComboBox<String>(comboStrings);
		layoutComboBox.setSelectedIndex(0);
		
		comboStrings = new String[]{ "Flood", "Random"};

		final JComboBox<String> algorithm1ComboBox = new JComboBox<String>(comboStrings);
		layoutComboBox.setSelectedIndex(0);
		
		final JComboBox<String> algorithm2ComboBox = new JComboBox<String>(comboStrings);
		layoutComboBox.setSelectedIndex(0);
				
		JButton startButton = new JButton("Ind�t�s");
        this.fileLabel = new JLabel("");
        
        this.getContentPane().add(singleButton);
        this.getContentPane().add(algorithm1ComboBox);
        this.getContentPane().add(multiButton);
        this.getContentPane().add(algorithm2ComboBox);
        this.getContentPane().add(openfilePanelButton);
        this.getContentPane().add(fileLabel);
        this.getContentPane().add(layoutComboBox);
        this.getContentPane().add(startButton);

        
        startButton.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				LayoutEnum le = null;
				if(layoutComboBox.getSelectedIndex() == 0){
					le = LayoutEnum.CIRCLE;
				}else{
					le = LayoutEnum.ISOM;
				}
				ChoosingAlgorithmEnum cae = null;
				if(algorithm1ComboBox.getSelectedIndex() == 0){
					cae = ChoosingAlgorithmEnum.FLOOD;
				}else{
					cae = ChoosingAlgorithmEnum.RANDOM;
				}
				if(Integer.parseInt(MainMenuFrame.this.group.getSelection().getActionCommand()) == 1){
					new SingleGraphPanel(le, cae, MainMenuFrame.this.fileName);
				}else{
//					new MultiGraphPanel(MainMenuFrame.this.graphView1, MainMenuFrame.this.graphView2);
				}
			}
		});
     
        openfilePanelButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {

				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(MainMenuFrame.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
				    File file = fc.getSelectedFile();
				    MainMenuFrame.this.fileLabel.setText(file.getName());
				    MainMenuFrame.this.fileName = file.getAbsolutePath();
				} else {
					System.out.println("Open command cancelled by user.");
				}
			}
		});
        this.setSize(400, 400);
        this.setVisible(true);
    }
}


