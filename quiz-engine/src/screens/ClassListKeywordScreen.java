package screens;

import java.util.ArrayList;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class ClassListKeywordScreen extends AbstractOpenInputScreen {
	
	public ClassListKeywordScreen(Scanner scan) {
		super(scan);
		initStartingText();
		screenStartAndLoop();
	}

	@Override
	void initStartingText() {
		startingText = "What's the keyword that you would like to filter the courses by?";
	}

	@Override
	void textAction(String choice) {
		if (choice.equals(InputParser.END_KEY)) {
			new ClassManagementScreen(sPtr);
		}
		FileProcessor.getAllClasses(new ArrayList<String>(), choice);
	}

	@Override
	void printReenterText() {}


}
