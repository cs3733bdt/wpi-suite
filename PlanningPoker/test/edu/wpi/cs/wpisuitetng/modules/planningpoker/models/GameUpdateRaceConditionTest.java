package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;

import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.requirement.Requirement;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.vote.Vote;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.game.GameModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockData;

public class GameUpdateRaceConditionTest {
	
	Requirement req1;
	
	Vote vote1, vote2, vote3, vote4, vote5, vote6, vote7, vote8, vote9, vote10, vote11, vote12,
	vote13, vote14, vote15, vote16, vote17, vote18, vote19, vote20, vote21, vote22, vote23, vote24,
	vote25, vote26, vote27, vote28, vote29, vote30;
	
	ArrayList<Requirement> reqList;
	
	final GameModel model = GameModel.getInstance();
	
	Game game1, game2, game3, game4, game5, game6, game7, game8, game9, game10,
	game11, game12, game13, game14, game15, game16, game17, game18, game19, game20;
	
	Project project200;
	
	Session session1;

	
	public class AddVotesThread extends Thread {
		public void run()
		{
			req1.addVote(vote1);
			req1.addVote(vote2);
			req1.addVote(vote3);		
			req1.addVote(vote4);	
			req1.addVote(vote5);
			req1.addVote(vote6);
			req1.addVote(vote7);
			req1.addVote(vote8);
			req1.addVote(vote9);
			req1.addVote(vote10);	
			req1.addVote(vote11);
			req1.addVote(vote12);
			req1.addVote(vote13);
			req1.addVote(vote14);
			req1.addVote(vote15);
			req1.addVote(vote16);
			req1.addVote(vote17);
			req1.addVote(vote18);
			req1.addVote(vote19);
			req1.addVote(vote20);	
			req1.addVote(vote21);
			req1.addVote(vote22);
			req1.addVote(vote23);
			req1.addVote(vote24);
			req1.addVote(vote25);
			req1.addVote(vote26);
			req1.addVote(vote27);
			req1.addVote(vote28);
			req1.addVote(vote29);
			req1.addVote(vote30);
			
			System.out.println(game1.getRequirements().get(0).getVoteCount());
		}
	}
	
	public class UpdateGameThread extends Thread {
		public void run()
		{

			model.addGame(game1);
			model.addGame(game2);
			model.addGame(game3);
			model.addGame(game4);
			model.addGame(game5);
			model.addGame(game6);
			model.addGame(game7);
			model.addGame(game8);
			model.addGame(game9);
			model.addGame(game10);
			model.addGame(game11);
			model.addGame(game12);
			model.addGame(game13);
			model.addGame(game14);
			model.addGame(game15);
			model.addGame(game16);
			model.addGame(game17);
			model.addGame(game18);
			model.addGame(game19);
			model.addGame(game20);
		}
	}
	
	
	@Test
	public void testRaceConditionVoteAndUpdate()
	{
		req1 = new Requirement("Req1", "Desc1");
		project200 = new Project("project 1", "200");	
		req1.setProject(project200);
		reqList = new ArrayList<Requirement>();		
		reqList.add(req1);
		User Jeremy =  new User("Jeremy", "Jim", "", "generic.email", "fbtest", 144);
			
		session1 = new Session(Jeremy, project200, "");
		
		game1 = new Game("Game 1", "Description",  reqList, false, false);
		game1.setProject(project200);
		game2 = new Game("Game2", "With a name2", new ArrayList<Requirement>(), true, true);
		game3 = new Game("Game3", "With a name3", new ArrayList<Requirement>(), false, false);
		game4 = new Game("Game4", "With a name4", new ArrayList<Requirement>(), true, true);
		game5= new Game("Game5", "With a name5", new ArrayList<Requirement>(), false, false);
		game6 = new Game("Game6", "With a name6", new ArrayList<Requirement>(), true, true);
		game7 = new Game("Game7", "With a name7", new ArrayList<Requirement>(), false, false);
		game8 = new Game("Game8", "With a name8", new ArrayList<Requirement>(), true, true);
		game9 = new Game("Game9", "With a name9", new ArrayList<Requirement>(), false, false);
		game10 = new Game("Game10", "With a name10", new ArrayList<Requirement>(), true, true);
		game11 = new Game("Game11", "With a name11", new ArrayList<Requirement>(), false, false);
		game12 = new Game("Game12", "With a name12", new ArrayList<Requirement>(), true, true);
		game13 = new Game("Game13", "With a name13", new ArrayList<Requirement>(), false, false);
		game14 = new Game("Game14", "With a name14", new ArrayList<Requirement>(), true, true);
		game15 = new Game("Game15", "With a name15", new ArrayList<Requirement>(), false, false);
		game16 = new Game("Game16", "With a name16", new ArrayList<Requirement>(), true, true);
		game17 = new Game("Game17", "With a name17", new ArrayList<Requirement>(), false, false);
		game18 = new Game("Game18", "With a name18", new ArrayList<Requirement>(), true, true);
		game19 = new Game("Game19", "With a name19", new ArrayList<Requirement>(), false, false);
		game20 = new Game("Game20", "With a name20", new ArrayList<Requirement>(), true, true);
	
		vote1 = new Vote("user1", 5);
		vote2 = new Vote("user2", 5);
		vote3 = new Vote("user3", 5);
		vote4 = new Vote("user4", 5);
		vote5 = new Vote("user5", 5);
		vote6 = new Vote("user6", 5);
		vote7 = new Vote("user7", 5);
		vote8 = new Vote("user8", 5);
		vote9 = new Vote("user9", 5);
		vote10 = new Vote("user10", 5);
		vote11 = new Vote("user11", 5);
		vote12 = new Vote("user12", 5);
		vote13 = new Vote("user13", 5);
		vote14 = new Vote("user14", 5);
		vote15 = new Vote("user15", 5);
		vote16 = new Vote("user16", 5);
		vote17 = new Vote("user17", 5);
		vote18 = new Vote("user18", 5);
		vote19 = new Vote("user19", 5);
		vote20 = new Vote("user20", 5);
		vote21 = new Vote("user21", 5);
		vote22 = new Vote("user22", 5);
		vote23 = new Vote("user23", 5);
		vote24 = new Vote("user24", 5);
		vote25 = new Vote("user25", 5);
		vote26 = new Vote("user26", 5);
		vote27 = new Vote("user27", 5);
		vote28 = new Vote("user28", 5);
		vote29 = new Vote("user29", 5);
		vote30 = new Vote("user30", 5);

		(new AddVotesThread()).start();
		(new UpdateGameThread()).start();
		System.out.println(model.getElementAt(0).getName());
	}
}
