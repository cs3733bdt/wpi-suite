package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.buttons;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.exportCsv.ExportToCsv;

/*
 * 
 * @author Doruk Uzunoglu  
 */

public class ExportButtonPanel extends ToolbarGroupView {

	private final JPanel contentPanel = new JPanel();
	JButton createExportButton = new JButton("<html>Export<br />Requirements</html>");
	final JButton createCancelExportButton = new JButton("<html>Cancel<br />Export</html>");
	private ImageIcon exportImg = null;
	private ImageIcon exportConfirmImg = null;
	
	/**
	 *  disables the export requirements button 
	 */
	public void disablecreateExportButton() {
		createExportButton.setEnabled(false);
	}
	
	/**
	 *  enables the export requirements button 
	 */
	public void enableCreateExportButton() {
		createExportButton.setEnabled(true);
	}
	
	public ExportButtonPanel(){
		super("");
		
		this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(390);
		
		createExportButton.setPreferredSize(new Dimension(150,50));
		createCancelExportButton.setVisible(false);
		
		
		try {
			Image img = ImageIO.read(getClass().getResource("cancelexport.png"));
		    createCancelExportButton.setIcon(new ImageIcon(img));
			
		    exportImg = new ImageIcon(ImageIO.read(getClass().getResource("export.png")));
		    createExportButton.setIcon(exportImg);
		    exportConfirmImg = new ImageIcon(ImageIO.read(getClass().getResource("export.png")));
		    
		} catch (IOException ex) {}
			
	// the action listener for the export requirements button
		
	createExportButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
					
			if(createCancelExportButton.isVisible() == false)
				setButtonToConfirm();
			else {
				int requirementId = -1;
				int [] reqsArray = ViewEventController.getInstance().getTableSelection();
				
				if(reqsArray == null) 
					System.out.println("No requirement selected");
				else {
				requirementId = reqsArray[0];
				System.out.println(requirementId);
				//exportToCsvFile("test.csv", requirementId);
				setButtonToExport();
				}
			}
				
			}
	});
	
	createCancelExportButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		
				
			setButtonToExport();
			createExportButton.setEnabled(true);
		 
		}
	});
	
	createExportButton.setVisible(true);
	contentPanel.add(createExportButton);
	contentPanel.add(createCancelExportButton);
	contentPanel.setOpaque(false);
	
	this.add(contentPanel);
	
	}
	
	/**
	 * Method getExpButton.
	
	 * @return JButton */
	public JButton getExpButton() {
		return this.createExportButton;
	}
	
	public void setButtonToExport(){
		if (exportImg != null){
			createExportButton.setIcon(exportImg);}
		createExportButton.setText("<html>Export<br />Requirements</html>");
		createExportButton.setEnabled(true);
		createCancelExportButton.setVisible(false);
	}
	public void setButtonToConfirm(){
		if (exportConfirmImg != null){
			createExportButton.setIcon(exportConfirmImg);}
		createExportButton.setText("<html>Export<br />Selected <br />Requirements</html>");
		createExportButton.setEnabled(true);
		createCancelExportButton.setVisible(true);
	}
	public void setConfirmEnabled(boolean enabled){
		createExportButton.setEnabled(enabled);
	}
	
}