package screens;

import java.util.Scanner;

public abstract class AbstractOpenInputScreen {
	
	protected Scanner sPtr;
	
	protected String startingText;
	
	public AbstractOpenInputScreen(Scanner scan) {
		sPtr = scan;
	}
	
	abstract void initStartingText();
	
	abstract void screenStartAndLoop();
		
	abstract void printReenterText();
}	

