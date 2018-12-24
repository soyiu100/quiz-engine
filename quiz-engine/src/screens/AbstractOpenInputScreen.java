package screens;

import java.util.Scanner;

import internals.InputParser;

public abstract class AbstractOpenInputScreen {
	
	protected Scanner sPtr;
	
	protected String startingText;
	
	public AbstractOpenInputScreen(Scanner scan) {
		sPtr = scan;
	}
	
	abstract void initStartingText();
	
	void screenStartAndLoop() {
		System.out.println(startingText);
		String choice = "src";
		while (choice != InputParser.END_KEY) {
			textAction(choice);
			choice = sPtr.nextLine();
		}
		textAction(choice);
	}
		
	abstract void textAction(String choice);

	abstract void printReenterText();

}	

