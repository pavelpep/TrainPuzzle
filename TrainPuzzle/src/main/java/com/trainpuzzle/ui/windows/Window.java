package com.trainpuzzle.ui.windows;

import java.awt.*;
import javax.swing.*;

/* The purpose of this abstract class (i.e. superclass) is to put the common method name Create() 
 * into one class, which is used in common by other subclasses that create Windows.
 */

abstract class Window extends JFrame {
	Window(){
		
	}
	
	// Creates the JFrame with elements
	public abstract void Create();
}
