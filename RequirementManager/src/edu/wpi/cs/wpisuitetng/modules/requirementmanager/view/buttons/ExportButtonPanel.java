/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Bobby Drop Tables
 ******************************************************************************/

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
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.exportCsv.ExportToCsv;

/**
 * creates the export requirements button
 * @author Doruk Uzunoglu  
 */

public class ExportButtonPanel extends ToolbarGroupView {

	private final JPanel contentPanel = new JPanel();
	JButton createExportButton = new JButton("<html>Export<br />Requirements</html>");
	private ImageIcon exportImg = null;
	
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
		this.setPreferredWidth(195);
				
		try {
		
		    exportImg = new ImageIcon(ImageIO.read(getClass().getResource("export.png")));
		    createExportButton.setIcon(exportImg);
		    
		} catch (IOException ex) {}	
	
	createExportButton.setVisible(true);
	contentPanel.add(createExportButton);
	contentPanel.setOpaque(false);
	this.add(contentPanel);
	
	createExportButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This is not functional yet.", "Dummy Button", 1);
			}
		
	});
	
	}
	
	/**
	 * getter for the export button.
	
	 * @return the export button
	 */
	public JButton getExpButton() {
		return this.createExportButton;
	}
	
	
}