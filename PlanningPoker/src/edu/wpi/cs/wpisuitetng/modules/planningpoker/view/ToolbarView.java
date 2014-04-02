package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;

/**
 * Sets up the upper toolbar of the PlanningPoker Window
 * 
 * 
 * @author jonathanleitschuh
 */
public class ToolbarView extends DefaultToolbarView {
	
	/**
	 * Creates and positions the buttons/other information in the tool bar
	 */
	public ToolbarView(){
	    this.setBorder(BorderFactory.createLineBorder(Color.blue, 2)); // add a border so you can see the panel
	    Calendar today = Calendar.getInstance();
	    if(today.get(Calendar.MONTH) == (Calendar.APRIL) && today.get(Calendar.DAY_OF_MONTH) == 1){
			BlinkButton blink = new BlinkButton("Congratulations!   You won!  Thanks for playing!");
			blink.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "\tApril Fools!", "Insanity Error", JOptionPane.INFORMATION_MESSAGE);
				}
				
			});
			GridLayout experement = new GridLayout(0, 1, -50, 0);
			JPanel filler = new JPanel();
			Dimension current = this.getSize();
		    current.height -= 20;
			filler.setMinimumSize(current);
			this.setLayout(experement);
			this.add(blink);
			this.add(filler);
	    }
	    this.repaint();
	    
	}
}

class BlinkButton extends JButton{
	private static final int BLINKING_RATE = 500;
	boolean blinkOn = true;
	
	public BlinkButton(String text){
		super(text);
		Timer timer = new Timer( BLINKING_RATE , new TimerListener(this) );
	    timer.setInitialDelay(0);
	    timer.start();
	}
}

class TimerListener implements ActionListener{
	private BlinkButton button;
	private Color fg;
	private Color bg;
	private boolean isForeground = true;
	
	public TimerListener(BlinkButton button){
		super();
		this.button = button;
		button.setForeground(Color.RED);
		fg = button.getForeground();
		bg = button.getBackground();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (button.blinkOn) {
	        if (isForeground) {
	          button.setForeground(fg);
	        }
	        else {
	          button.setForeground(bg);
	        }
	        isForeground = !isForeground;
	      }
	      else {
	        // here we want to make sure that the label is visible
	        // if the blinking is off.
			if (isForeground) {
				button.setForeground(fg);
				isForeground = false;
			}
	      }
	}
}
