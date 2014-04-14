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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ClosableTabComponent;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.ViewEventController;

/**
 * This class sets the main view when the user goes to the PlanningPoker tab 
 * @author jonathanleitschuh
 *
 */
public class TabbedView extends JTabbedPane {
	private boolean dragging = false;
	private Image tabImage = null;
	private int draggedTabIndex = 0;
	private GamesTreePanel gamesList = new GamesTreePanel();
	private InitialView initView = new InitialView();
	
	private final JPopupMenu popup = new JPopupMenu();
	private JMenuItem closeAll = new JMenuItem("Close All Tabs");
	private JMenuItem closeOthers = new JMenuItem("Close Others");
	
	/**
	 * Adds Main View of the planning poker panel when the user goes to the planning poker tab
	 */
	public TabbedView(){
		
		this.addTab("Overview", initView);
	    //this.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		this.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
	    
	    closeAll.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().closeAllTabs();

			}	
		});
	    
	    closeOthers.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().closeOthers();

			}	
		});
	    
	    popup.add(closeAll);
	    popup.add(closeOthers);
	    
	    this.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if(e.isPopupTrigger()) popup.show(e.getComponent(), e.getX(), e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(dragging) {
					int tabNumber = getUI().tabForCoordinate(TabbedView.this, e.getX(), e.getY());
					if(tabNumber >= 0) {
						Component comp = getComponentAt(draggedTabIndex);
						String title = getTitleAt(draggedTabIndex);
						if (!title.equals("Overview")) {
							removeTabAt(draggedTabIndex);
							insertTab(title, null, comp, null, tabNumber);
							setSelectedIndex(tabNumber);
						}
					}
				}

				dragging = false;
				tabImage = null;
			}
		});
	}

	/**
	 * needed to get controller functioning
	 * TODO add purpose for this function
	 * @return boolean true always
	 */
	public boolean getNewGame(){
		return true;
	}
	
	/**
	 * Overridden insertTab function to add the closable tab element.
	 * 
	 * @param title	Title of the tab
	 * @param icon	Icon for the tab
	 * @param component	The tab
	 * @param tip	Showing mouse tip when hovering over tab
	 * @param index	Location of the tab
	 */
	@Override
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		if (!(component instanceof InitialView)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
}
