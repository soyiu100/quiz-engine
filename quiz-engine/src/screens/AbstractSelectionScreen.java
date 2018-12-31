package screens;

import java.util.List;
import java.util.Scanner;

import internals.FileProcessor;

public abstract class AbstractSelectionScreen {
	
	protected Scanner sPtr;
	protected FileProcessor fp;

	protected String startingText;
	protected List<String> cycleOptions;
	
	public AbstractSelectionScreen(Scanner scan, FileProcessor fp) {
		sPtr = scan;
		this.fp = fp;
		initGeneralStart();
		initCyclingOptions();
	}

	abstract void initGeneralStart();

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

	/**
	 * Method mainly to be used in a while loop, where a string is evaluated in
	 * this method, and that string is given a certain value. Based on that 
	 * value, the loop decides to continue to loop or not. 
	 * 
	 * @param prevResult
	 * 			passes in the previous result to evaluate
	 * @return
	 * 		usually returns a choice ranging from 1-k, where k is the number of choices
	 * 		returns -1 if the user would like to return to the previous screen
	 * 		returns -2 if input is invalid
	 * 		should never return -3 or 0
	 */
	abstract int choiceAction(int prevResult);

	void printReenterText() {
		System.out.println("Please enter one of the choices from 1-" + getOptionNum() + ".");
	}
	
	int getOptionNum() {
		return cycleOptions.size() - 1;
	}

}
