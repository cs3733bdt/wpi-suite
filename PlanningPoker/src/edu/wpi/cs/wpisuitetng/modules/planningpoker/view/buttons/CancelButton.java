/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Bobby Drop Tables
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

public class CancelButton extends JPanel{
    private JComponent parent;
    private JButton cancelButton = new JButton();
    
    public CancelButton(String buttonText, JComponent parentComponent){
    	parent = parentComponent;
    	cancelButton.setText(buttonText);
    	
        SpringLayout layout = new SpringLayout(); 
        setLayout(layout);
        
        setupActionListeners();
        
        add(cancelButton);
        
        layout.putConstraint(SpringLayout.NORTH, cancelButton, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, cancelButton, 0, SpringLayout.SOUTH, this);    
        layout.putConstraint(SpringLayout.WEST, cancelButton, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, cancelButton, 0, SpringLayout.EAST, this);
        
        
        setPreferredSize(cancelButton.getPreferredSize());
    }
    
    public JButton getCancelButton() {
        return cancelButton;
    }
    
    public void setupActionListeners(){
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
        		ViewEventController.getInstance().removeTab(parent);
            }
        });
    }
    
}
