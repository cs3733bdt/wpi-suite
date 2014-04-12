package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;

import org.junit.Before;
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
	Project project1;
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
		MockData mockDB = new MockData(null);
		req1 = new Requirement("Req1", "Desc1");
		project1 = new Project("project 1", "7");	
		
		reqList = new ArrayList<Requirement>();		
		req1.setProject(project1);
		reqList.add(req1);
		User Jeremy =  new User("Jeremy", "Jim", "", "generic.email", "fbtest", 144);
		User user1 =  new User("user1", "user1", "", "generic.email", "fbtest", 1);
		User user2 =  new User("user2", "user2", "", "generic.email", "fbtest", 2);
		User user3 =  new User("user3", "user3", "", "generic.email", "fbtest", 3);
		User user4 =  new User("user4", "user4", "", "generic.email", "fbtest", 4);
		User user5 =  new User("user5", "user5", "", "generic.email", "fbtest", 5);
		User user6 =  new User("user6", "user6", "", "generic.email", "fbtest", 6);
		User user7 =  new User("user7", "user7", "", "generic.email", "fbtest", 7);
		User user8 =  new User("user8", "user8", "", "generic.email", "fbtest", 8);
		User user9 =  new User("user9", "user9", "", "generic.email", "fbtest", 9);
		User user10 =  new User("user10", "user10", "", "generic.email", "fbtest", 10);
		User user11 =  new User("user11", "user11", "", "generic.email", "fbtest", 11);
		User user12 =  new User("user12", "user12", "", "generic.email", "fbtest", 12);
		User user13 =  new User("user13", "user13", "", "generic.email", "fbtest", 13);
		User user14 =  new User("user14", "user14", "", "generic.email", "fbtest", 14);
		User user15 =  new User("user15", "user15", "", "generic.email", "fbtest", 15);
		User user16 =  new User("user16", "user16", "", "generic.email", "fbtest", 16);
		User user17 =  new User("user17", "user17", "", "generic.email", "fbtest", 17);
		User user18 =  new User("user18", "user18", "", "generic.email", "fbtest", 18);
		User user19 =  new User("user19", "user19", "", "generic.email", "fbtest", 19);
		User user20 =  new User("user20", "user20", "", "generic.email", "fbtest", 20);
		User user21 =  new User("user21", "user21", "", "generic.email", "fbtest", 21);
		User user22 =  new User("user22", "user22", "", "generic.email", "fbtest", 22);
		User user23 =  new User("user23", "user23", "", "generic.email", "fbtest", 23);
		User user24 =  new User("user24", "user24", "", "generic.email", "fbtest", 24);
		User user25 =  new User("user25", "user25", "", "generic.email", "fbtest", 25);
		User user26 =  new User("user26", "user26", "", "generic.email", "fbtest", 26);
		User user27 =  new User("user27", "user27", "", "generic.email", "fbtest", 27);
		User user28 =  new User("user28", "user28", "", "generic.email", "fbtest", 28);
		User user29 =  new User("user29", "user29", "", "generic.email", "fbtest", 29);
		User user30 =  new User("user30", "user30", "", "generic.email", "fbtest", 30);
		project1.addTeamMember(user1);
		project1.addTeamMember(user2);
		project1.addTeamMember(user3);
		project1.addTeamMember(user4);
		project1.addTeamMember(user5);
		project1.addTeamMember(user6);
		project1.addTeamMember(user7);
		project1.addTeamMember(user8);
		project1.addTeamMember(user9);
		project1.addTeamMember(user10);
		project1.addTeamMember(user11);
		project1.addTeamMember(user12);
		project1.addTeamMember(user13);
		project1.addTeamMember(user14);
		project1.addTeamMember(user15);
		project1.addTeamMember(user16);
		project1.addTeamMember(user17);
		project1.addTeamMember(user18);
		project1.addTeamMember(user19);
		project1.addTeamMember(user20);
		project1.addTeamMember(user21);
		project1.addTeamMember(user22);
		project1.addTeamMember(user23);
		project1.addTeamMember(user24);
		project1.addTeamMember(user25);
		project1.addTeamMember(user26);
		project1.addTeamMember(user27);
		project1.addTeamMember(user28);
		project1.addTeamMember(user29);
		project1.addTeamMember(user30);
		
		session1 = new Session(Jeremy, project1, "");
		game1 = new Game("Game 1", "Description",  reqList, false, false);
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
		

		(new AddVotesThread()).start();;
		//updateGameThread.start();
	}
}
