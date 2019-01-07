package screens;

/**
 * Used to represent a screen of options and/or questions for the quiz engine.
 * 
 * @author Isaac Pang
 *
 */
public interface AbstractScreen {

	/**
	 * Used for initializing startingText and some other variables that are need to
	 * initialize before the screen loop
	 */
	void initGeneralStart();

	/**
	 * Prints re-enter text if the input is faulty.
	 */
	void printReenterText();

	/**
	 * Starts the screen and loops options if needed.
	 */
	void screenStartAndLoop();
}
