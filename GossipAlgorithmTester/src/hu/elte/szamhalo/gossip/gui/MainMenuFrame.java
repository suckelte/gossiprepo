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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainMenuFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1281533343606807590L;
	private String fileName = "";
	private JLabel fileLabel;
	private ButtonGroup group;
	private JTextField klocalTextField;
	private JTextField generateTextField;
	private ButtonGroup group2;

	public MainMenuFrame(){
    	this.setTitle("Gossip algoritmus tesztelõ program");
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout gridLayout = new GridLayout(12,2);
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
        
        group = new ButtonGroup();
        group.add(singleButton);
        group.add(multiButton);
        
        JRadioButton fileButton = new JRadioButton("Fájlból olvasás");
        fileButton.setActionCommand("1");
        JRadioButton generateButton = new JRadioButton("Gráf generálás");
        generateButton.setActionCommand("2");
        fileButton.setSelected(true);

        group2 = new ButtonGroup();
        group2.add(fileButton);
        group2.add(generateButton);
        
        String[] comboStrings = { "CircleLayout", "ISOMLayout"};

		final JComboBox<String> layoutComboBox = new JComboBox<String>(comboStrings);
		layoutComboBox.setSelectedIndex(0);
		
		comboStrings = new String[]{ "1. Round-robin flooding", "2. Egyszerû véletlen", "3. Determinisztikus", "4. Determinisztikus fa"};

		final JComboBox<String> algorithm1ComboBox = new JComboBox<String>(comboStrings);
		layoutComboBox.setSelectedIndex(0);
		
		final JComboBox<String> algorithm2ComboBox = new JComboBox<String>(comboStrings);
		layoutComboBox.setSelectedIndex(0);
				
		JButton startButton = new JButton("Indítás");
        this.fileLabel = new JLabel("[fájl név]");
        

		klocalTextField = new JTextField("3", 30);
		generateTextField = new JTextField("6,0|3,40|4,100", 30);
		
        this.getContentPane().add(singleButton);
        this.getContentPane().add(algorithm1ComboBox);
        this.getContentPane().add(multiButton);
        this.getContentPane().add(algorithm2ComboBox);
        this.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL));
        this.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL));
        this.getContentPane().add(fileButton);
        this.getContentPane().add(new JLabel(""));
        this.getContentPane().add(openfilePanelButton);
        this.getContentPane().add(fileLabel);        
        this.getContentPane().add(generateButton);
        this.getContentPane().add(generateTextField);
        this.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL));
        this.getContentPane().add(new JSeparator(SwingConstants.HORIZONTAL));
        this.getContentPane().add(new JLabel("Layout: "));
        this.getContentPane().add(layoutComboBox);
        this.getContentPane().add(new JLabel("K-local érték: "));
        this.getContentPane().add(klocalTextField);
        
        
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
				switch (algorithm1ComboBox.getSelectedIndex()){
					case 0:
						cae = ChoosingAlgorithmEnum.FLOOD;
						break;
					case 1:
						cae = ChoosingAlgorithmEnum.RANDOM;
						break;
					default:
						cae = ChoosingAlgorithmEnum.SIMPLERANDOM;
						break;
				}
				ChoosingAlgorithmEnum cae2;
				switch (algorithm2ComboBox.getSelectedIndex()){
					case 0:
						cae2 = ChoosingAlgorithmEnum.FLOOD;
						break;
					case 1:
						cae2 = ChoosingAlgorithmEnum.RANDOM;
						break;
					default:
						cae2 = ChoosingAlgorithmEnum.SIMPLERANDOM;
						break;
				}
				if(Integer.parseInt(MainMenuFrame.this.group.getSelection().getActionCommand()) == 1){
					new SingleGraphPanel(le, cae,Integer.parseInt(klocalTextField.getText()),
							(Integer.parseInt(MainMenuFrame.this.group2.getSelection().getActionCommand()) == 1 ? fileName : generateTextField.getText()));
				}else{
					new MultiGraphPanel(le, cae, cae2 ,Integer.parseInt(klocalTextField.getText()),
							(Integer.parseInt(MainMenuFrame.this.group2.getSelection().getActionCommand()) == 1 ? fileName : generateTextField.getText()));
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


