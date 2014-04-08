/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.notification;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.requirementmodels.Requirement;

/**
 * @author doruk
 *
 */
public class testSmsNotification {
	
	User u1;
	
	@Before
	public void setUp()
	{
		u1 = new User("testuser1", "user1", "pass", "dcuzunoglu@wpi.edu","7743125359", 129);
	}
	
	@Test
	public void testAppendCarrier()
	{
		
	}

}

