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
		String choice = "src";
		int choiceResult = 0;
		while (choiceResult >= 0) {
			choiceResult = textAction(choice);
			choice = sPtr.nextLine();
		}
		textAction(choice);
	}
		
	abstract int textAction(String choice);


}	

