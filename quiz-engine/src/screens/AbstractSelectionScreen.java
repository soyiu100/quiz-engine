package screens;

import java.util.List;
import java.util.Scanner;

public abstract class AbstractSelectionScreen {
	
	protected Scanner sPtr;

	protected String startingText;
	protected List<String> cycleOptions;
	
	public AbstractSelectionScreen(Scanner scan) {
		sPtr = scan;
		initStartingText();
		initCyclingOptions();
		screenStartAndLoop();
	}

	abstract void initStartingText();

	abstract void initCyclingOptions();

	void screenStartAndLoop() {
		System.out.println(startingText);
		for (int i = 1; i < cycleOptions.size(); i++) {
			System.out.println(i + ": " + cycleOptions.get(i - 1));	
		}
		System.out.println(cycleOptions.get(getOptionNum()));

		int choice = -3;

		while (choice == -2 || choice == -3) {
			choice = choiceAction(choice);
		}
		choiceAction(choice);
	}

	abstract int choiceAction(int prevResult);

	void printReenterText() {
		System.out.println("Please enter one of the choices from 1-" + getOptionNum() + ".");
	}
	
	int getOptionNum() {
		return cycleOptions.size() - 1;
	}

}
