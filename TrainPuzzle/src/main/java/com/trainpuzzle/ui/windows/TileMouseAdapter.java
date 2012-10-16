package com.trainpuzzle.ui.windows;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class TileMouseAdapter extends MouseAdapter {
        
		
		public int[]  returnCoordinates(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                //TransferHandler handler = c.getTransferHandler();
                //handler.exportAsDrag(c, e, TransferHandler.COPY);
                int[] position = new int[2];
                position[0] = c.getY()/40;
                position[1] = c.getX()/40;
                
                System.out.println();
                return position;
        }
        
}
