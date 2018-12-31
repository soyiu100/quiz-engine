package screens;

import java.util.Scanner;

public abstract class AbstractOpenInputScreen {
	
	protected Scanner sPtr;
	
	protected String startingText;
	
	public AbstractOpenInputScreen(Scanner scan) {
		sPtr = scan;
	}
	
	abstract void initStartingText();
	
	void screenStartAndLoop() {
		String choice = "src";
		int choiceResult = 0;
		while (choiceResult >= 0) {
			choiceResult = textAction(choice);
			choice = sPtr.nextLine();
		}
		textAction(choice);
	}
		
	abstract int textAction(String choice);

	abstract void printReenterText();

}	

