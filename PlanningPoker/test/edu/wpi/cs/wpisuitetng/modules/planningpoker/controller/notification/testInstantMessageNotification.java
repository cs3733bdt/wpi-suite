/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import org.junit.Before;
import org.junit.Test;
import java.net.*;
import java.io.*;
import java.util.*;

/**
 * @author doruk
 *
 */
public class testInstantMessageNotification{
	
	Chatable owner;
	
	InstantMessageNotification instantMessageNot = new InstantMessageNotification(owner);
	
	@Test	
	public void testSendIM() throws IOException
	{
		instantMessageNot.login("wpi.suite.bdt.noreply@gmail.com", "cs3733team2");
		instantMessageNot.send("zillion582@gmail.com", "Test the IM notification");
		//instantMessageNot.logout();
	}

}
