package screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class ClassManagementScreen extends AbstractSelectionScreen {

	public ClassManagementScreen(Scanner scan) {
		super(scan);
	}

	@Override
	void initStartingText() {
		startingText = "What would you like to do?";

	}

	@Override
	void initCyclingOptions() {
		cycleOptions = Arrays.asList("Create a course", "Delete a course", "List all course quizzes",
				"List all course quizzes by a certain keyword", InputParser.quitMessage(false));

	}

	@Override
	int choiceAction(int prevResult) {
		if (prevResult == 1) {
			new ClassEditorScreen(sPtr, true);
			return 1;
		} else if (prevResult == 2) {
			new ClassEditorScreen(sPtr);
			return 2;
		} else if (prevResult == 3) {
			FileProcessor.getAllClasses(new ArrayList<String>());
			new ClassManagementScreen(sPtr);
			return 3;
		} else if (prevResult == 4) {
			new ClassListKeywordScreen(sPtr);
			new ClassManagementScreen(sPtr);
			return 4;
		}
		if (prevResult == -2) {
			printReenterText();
		}
		return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum(), true);
	}

}