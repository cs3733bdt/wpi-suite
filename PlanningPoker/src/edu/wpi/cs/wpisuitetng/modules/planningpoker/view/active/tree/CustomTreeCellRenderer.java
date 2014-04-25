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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.active.tree;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;


/**
 * 
 * @author Bobby Drop Tables
 */
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
	Icon gameIcon;

	public CustomTreeCellRenderer() {
		super();
		gameIcon = new GameIcon();
	}

	/**
	 * Method getTreeCellRendererComponent.
	 * 
	 * @param tree
	 *            JTree
	 * @param value
	 *            Object
	 * @param sel
	 *            boolean
	 * @param expanded
	 *            boolean
	 * @param leaf
	 *            boolean
	 * @param row
	 *            int
	 * @param hasFocus
	 *            boolean
	
	
	 * @return Component * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(JTree,
	 *      Object, boolean, boolean, boolean, int, boolean) */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		setIcon(gameIcon);
		return this; 
	}
}
