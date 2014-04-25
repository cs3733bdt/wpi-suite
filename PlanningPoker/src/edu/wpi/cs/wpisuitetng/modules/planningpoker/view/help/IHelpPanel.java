package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.help;

public interface IHelpPanel {

	boolean readyToRemove();
	
	/**
	 * Gets the identifier index for easy finding of the panel inside the list
	 *  int preferenceIndex = 0;
	 *	int endGameIndex = 1;
	 *	int activeGameIndex = 2;
     *  int createGameIndex = 3;
	 *	int createDeckIndex = 4;
	 * @return
	 */
	int getIdentifierIndex();
}
