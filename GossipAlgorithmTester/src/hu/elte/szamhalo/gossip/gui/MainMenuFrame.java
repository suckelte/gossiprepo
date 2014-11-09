package hu.elte.szamhalo.gossip.gui;

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
	private final GraphView graphView1;
	private final GraphView graphView2;
	private String fileName = "";
	private JLabel fileLabel;
	private ButtonGroup group;

	public MainMenuFrame(GraphView graphView1, GraphView graphView2){
		this.graphView1 = graphView1;
		this.graphView2 = graphView2;
    	this.setTitle("Gossip Algorithm Tester");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout gridLayout = new GridLayout(2,3);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        this.setLayout(gridLayout);
        
        JButton openfilePanelButton = new JButton("Betöltés fájlból");
        this.fileLabel = new JLabel("");
        
        JRadioButton singleButton = new JRadioButton("Egyszerü panel");
        singleButton.setActionCommand("1");
        JRadioButton multiButton = new JRadioButton("Összehasonlító panel");
        multiButton.setActionCommand("2");
        singleButton.setSelected(true);

        //Group the radio buttons.
        group = new ButtonGroup();
        group.add(singleButton);
        group.add(multiButton);
        
        String[] comboStrings = { "CircleLayout", "ISOMLayout"};

		JComboBox<String> layoutComboBox = new JComboBox<String>(comboStrings);
		layoutComboBox.setSelectedIndex(0);
		
		JButton startButton = new JButton("Indítás");
        this.fileLabel = new JLabel("");
        
        this.getContentPane().add(singleButton);
        this.getContentPane().add(openfilePanelButton);
        this.getContentPane().add(fileLabel);
        this.getContentPane().add(multiButton);
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
				if(Integer.parseInt(MainMenuFrame.this.group.getSelection().getActionCommand()) == 1){
					new SingleGraphPanel(MainMenuFrame.this.graphView1);
				}else{
					new MultiGraphPanel(MainMenuFrame.this.graphView1, MainMenuFrame.this.graphView2);
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
        this.setSize(550, 150);
        this.setVisible(true);
    }
}


