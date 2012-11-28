package com.trainpuzzle.ui.windows;
	
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicIconFactory;
import javax.swing.plaf.metal.MetalIconFactory;

import com.trainpuzzle.infrastructure.Images;

	@SuppressWarnings("serial")
	public class IconListRenderer 
		extends DefaultListCellRenderer {

		private Map<Object, Icon> icons = null;

		public IconListRenderer() {
			icons = new HashMap<Object, Icon>();
			icons.put("locked", Images.LS_LOCKED_IMAGE);
			icons.put("completed", Images.LS_COMPLETED_IMAGE);
			icons.put("saved", Images.LS_SAVED_IMAGE);
		}
		
		
		@Override
		public Component getListCellRendererComponent(
			JList list, Object value, int index, 
			boolean isSelected, boolean cellHasFocus) {
			
			// Get the renderer component from parent class
			
			JLabel label = (JLabel) super.getListCellRendererComponent(list, 
					value, index, isSelected, cellHasFocus);
			
			// Get icon to use for the list item value
			
			
			
			// Set icon to display for value
			
			Icon icon = (Icon)(icons.get(value));
			label.setIcon(icon);
			label.add((Component) icon);
			
			return label;
		}

		
			

		

	}