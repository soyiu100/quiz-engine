package screens;

import java.util.Scanner;

public abstract class AbstractOpenInputScreen implements AbstractScreen {
	
	protected Scanner sPtr;
	protected AbstractScreen prevScr;

	protected String startingText;
		
	public AbstractOpenInputScreen(Scanner scan, AbstractScreen scr) {
		sPtr = scan;
		prevScr = scr;
	}
		
	public void screenStartAndLoop() {
		String choice = null;
		int choiceResult = 0;
		while (choiceResult >= 0) {
			choiceResult = textAction(choice);
			choice = sPtr.nextLine();
		}
	}
		
	/**
	 * 
	 * @param choice
	 * 			the choice previously made/written that is to be evaluated
	 * @return
	 *		returns -1 if the user would like to return to the previous screen
	 * 		returns -2 if input is invalid
	 * 		if else returns any integer larger than 0
	 */
	abstract int textAction(String choice);

}	

