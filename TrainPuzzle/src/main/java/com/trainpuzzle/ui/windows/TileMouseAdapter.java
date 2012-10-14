package com.trainpuzzle.ui.windows;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class TileMouseAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
        }
}
