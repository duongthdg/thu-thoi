package trash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Zduongtd.MyTable;

public class FormatDialog extends JDialog{
	
	private FormatTable formatTable;
	private MyTable table;
	
	public FormatDialog(JFrame frameOwner, MyTable t){
		super(frameOwner, "Setting", true);
		table = t;
		formatTable = new FormatTable(this);
		this.add(formatTable);
		this.setMinimumSize(new Dimension(600, 550));
	}
	
	public class FormatTable extends JPanel {
		private JColorChooser colorChooser;
		private JPanel upPanel, downPanel, downRightPanel;
		private SettingFontTab fontTab;
		private SettingBackgroundTab backgroundTab;
		private JTabbedPane tabs;
		private JButton btnOk, btnCancel;
		private FormatDialog dialog;
		
		
		public FormatTable(FormatDialog d){
			super();
			dialog = d;
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			upPanel = new JPanel(new BorderLayout());
			downPanel = new JPanel(new BorderLayout());
			downRightPanel = new JPanel();
			downRightPanel.setLayout(new BoxLayout(downRightPanel, BoxLayout.Y_AXIS));
			
			btnOk = new JButton("OK");
			btnCancel = new JButton("Cancel");
			setEvent((JFrame)this.getParent());
			
			downRightPanel.add(btnOk);
			downRightPanel.add(btnCancel);
			//downPanel.add(downRightPanel, BorderLayout.EAST);
			
			tabs = new JTabbedPane();
			fontTab = new SettingFontTab();
			backgroundTab = new SettingBackgroundTab();
			tabs.addTab("Font", fontTab);
			tabs.addTab("Background", backgroundTab);
			
			downPanel.add(tabs, BorderLayout.CENTER);
			downPanel.add(downRightPanel, BorderLayout.EAST);
			
			colorChooser = new JColorChooser();
			setEventColor();
			upPanel.add(colorChooser, BorderLayout.CENTER);
			
			add(upPanel);
			add(downPanel);
		}
		
		public void setEvent(JFrame frameOwner){
			btnOk.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String fontName = (String)fontTab.fontChooser.getSelectedItem();
					int fontSize = (int)fontTab.fontSizeSpinner.getValue();
					String fontStyle = (String)fontTab.fontStyle.getSelectedItem();
					int fontStyleInt;
					if(fontStyle.equalsIgnoreCase("Plain"))
						fontStyleInt = Font.PLAIN;
					else if(fontStyle.equalsIgnoreCase("Bold"))
						fontStyleInt = Font.BOLD;
					else if(fontStyle.equalsIgnoreCase("Italic"))
						fontStyleInt = Font.ITALIC;
					else
						fontStyleInt = Font.BOLD + Font.ITALIC;
					table.setFont(new Font(fontName, fontStyleInt, fontSize));
					
					Color tempBgColor = backgroundTab.recColorBg.getBackground();
					if(tempBgColor != null && backgroundTab.enableSetBgColor)
						table.setBg(tempBgColor);
					
					//set Color
					Color tempFontColor = fontTab.recColor.getBackground();
					if(tempFontColor != null && fontTab.enableSetFontColor)
						table.setFg(tempFontColor);
					dialog.setVisible(false);
				}
				
			});
			
			btnCancel.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent arg0){
					dialog.setVisible(false);
				}
			});
		}
		
		
		
		public void setTabBackground(){
			
		}
		
		public void setEventColor(){
			colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e) {
					Color newColor = colorChooser.getColor();
					String hexColor = Integer.toHexString(newColor.getRGB() & 0xffffff);
					  	if (hexColor.length() < 6) {
					  		hexColor = "000000".substring(0, 6 - hexColor.length()) + hexColor;
					  	}
					hexColor = "#" + hexColor;
					int index = tabs.getSelectedIndex();
					System.out.println(index);
					
					if(index == 0){
						fontTab.textColor.setText(hexColor);
						fontTab.recColor.setBackground(newColor);
						fontTab.enableSetFontColor = true;
					}
					else if(index == 1){
						backgroundTab.textColor.setText(hexColor);
						backgroundTab.recColorBg.setBackground(newColor);
						backgroundTab.enableSetBgColor = true;
					}
				}
			});;
			
			//chuyển đổi hex <-> color, nhưng đang gặp bug dừng lại
			/*
			fontTab.textColor.getDocument().addDocumentListener(new DocumentListener(){
				public void changedUpdate(DocumentEvent e) {
				    decodeColor();
				}
				public void removeUpdate(DocumentEvent e) {
					decodeColor();
				}
				public void insertUpdate(DocumentEvent e) {
					decodeColor();
				}
			});
			*/
		}
		
		/*
		public void decodeColor(){
			String hexColor = fontTab.textColor.getText();
			Color tempColor = Color.decode(hexColor);
			fontTab.recColor.setBackground(tempColor);
		}
		*/
		
		
		/**
		 * tab Font cho setting bên dưới
		 * @author Duong Td
		 *
		 */
		public class SettingFontTab extends JPanel{
			private javax.swing.JTextPane recColor;
		    private javax.swing.JTextField textColor;
		    private javax.swing.JButton btnCancelFontColor;
		    private javax.swing.JButton btnSelectFontColor;
		    public javax.swing.JComboBox<String> fontChooser;
		    public javax.swing.JSpinner fontSizeSpinner;
		    public javax.swing.JComboBox<String> fontStyle;
		    private javax.swing.JScrollPane jScrollPane1;
		    private javax.swing.JLabel lblFont;
		    private javax.swing.JLabel lblFontColor;
		    private javax.swing.JLabel lblFontStyle;
		    private javax.swing.JLabel lblSize;
		    private boolean enableSetFontColor = true;
		    
		    public SettingFontTab(){
		    	lblFont = new javax.swing.JLabel();
		        fontChooser = new javax.swing.JComboBox<>();
		        lblSize = new javax.swing.JLabel();
		        fontSizeSpinner = new javax.swing.JSpinner();
		        lblFontStyle = new javax.swing.JLabel();
		        fontStyle = new javax.swing.JComboBox<>();
		        lblFontColor = new javax.swing.JLabel();
		        jScrollPane1 = new javax.swing.JScrollPane();
		        recColor = new javax.swing.JTextPane();
		        textColor = new javax.swing.JTextField();
		        btnSelectFontColor = new javax.swing.JButton();
		        btnCancelFontColor = new javax.swing.JButton();

		        lblFont.setText("Font");

		        fontChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Arial", "Calisto MT", "Century", "Comic Sans MS", "Georgia", "SansSerif", "Tahoma", "Times New Roman", "Webdings" }));

		        lblSize.setText("Size");

		        fontSizeSpinner.setRequestFocusEnabled(false);

		        lblFontStyle.setText("Font style");

		        fontStyle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plain", "Bold", "Italic", "Bold and Italic" }));

		        lblFontColor.setText("Font color");

		        jScrollPane1.setViewportView(recColor);

		        btnSelectFontColor.setText("Select");

		        btnCancelFontColor.setText("Cancel");

		        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		        this.setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addGap(39, 39, 39)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lblFont)
		                    .addComponent(fontChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(layout.createSequentialGroup()
		                        .addComponent(lblFontColor)
		                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
		                    .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGap(24, 24, 24)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnCancelFontColor)
		                    .addComponent(btnSelectFontColor)
		                    .addGroup(layout.createSequentialGroup()
		                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                            .addComponent(lblSize)
		                            .addComponent(fontSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
		                        .addGap(27, 27, 27)
		                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                            .addComponent(fontStyle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                            .addComponent(lblFontStyle))))
		                .addContainerGap(177, Short.MAX_VALUE))
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addGap(33, 33, 33)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(lblFont)
		                    .addComponent(lblSize)
		                    .addComponent(lblFontStyle))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		                    .addComponent(fontChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(fontSizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(fontStyle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addGap(18, 18, 18)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lblFontColor)
		                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnSelectFontColor))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnCancelFontColor))
		                .addContainerGap(162, Short.MAX_VALUE))
		        );
		        
		        //start
		        fontSizeSpinner.setValue(12);
		        btnCancelFontColor.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						recColor.setBackground(null);
						enableSetFontColor = false;
					}
					
				});
		    }
			
		}
		
		
		/**
		 * tab Background setting bên dưới
		 * @author Duong Td
		 *
		 */
		public class SettingBackgroundTab extends JPanel{
			private javax.swing.JTextField textColor;
		    private javax.swing.JButton btnCancelBgColor;
		    private javax.swing.JButton btnSelectBgColor;
		    private javax.swing.JTextPane recColorBg;
		    private javax.swing.JLabel lblBgColor;
		    private boolean enableSetBgColor = true;
		    
		    public SettingBackgroundTab(){
		    	lblBgColor = new javax.swing.JLabel();
		        textColor = new javax.swing.JTextField();
		        btnSelectBgColor = new javax.swing.JButton();
		        btnCancelBgColor = new javax.swing.JButton();
		        recColorBg = new javax.swing.JTextPane();

		        lblBgColor.setText("Font color");

		        btnSelectBgColor.setText("Select");

		        btnCancelBgColor.setText("Cancel");

		        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		        this.setLayout(layout);
		        layout.setHorizontalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addGap(36, 36, 36)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addGroup(layout.createSequentialGroup()
		                        .addComponent(lblBgColor)
		                        .addGap(26, 26, 26)
		                        .addComponent(recColorBg, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
		                .addGap(33, 33, 33)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(btnCancelBgColor)
		                    .addComponent(btnSelectBgColor))
		                .addContainerGap(60, Short.MAX_VALUE))
		        );
		        layout.setVerticalGroup(
		            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		            .addGroup(layout.createSequentialGroup()
		                .addGap(36, 36, 36)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(lblBgColor)
		                    .addComponent(btnSelectBgColor)
		                    .addComponent(recColorBg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
		                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		                    .addComponent(textColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		                    .addComponent(btnCancelBgColor))
		                .addContainerGap(66, Short.MAX_VALUE))
		        );
		        
		        btnCancelBgColor.addActionListener(new ActionListener(){
		        	@Override
		        	public void actionPerformed(ActionEvent arg0){
		        		recColorBg.setBackground(null);
		        		enableSetBgColor = false;
		        	}
		        });
		    }
		}
	}

}
