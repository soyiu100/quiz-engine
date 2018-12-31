package screens;

import java.util.Arrays;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class ClassManagementScreen extends AbstractSelectionScreen {

	public ClassManagementScreen(Scanner scan, FileProcessor fp) {
		super(scan, fp);
		screenStartAndLoop();
	}

	@Override
	void initGeneralStart() {
		startingText = "What would you like to do?";

	}

	@Override
	void initCyclingOptions() {
		cycleOptions = Arrays.asList("Create a course", "Delete a course", "List all course quizzes",
				"List all course quizzes by a certain keyword", InputParser.quitMessage(false));

	}

	@Override
	int choiceAction(int prevResult) {
		switch (prevResult) {
		case 1:
			new ClassEditorScreen(sPtr, fp, true);
			return 1;
		case 2:
			new ClassEditorScreen(sPtr, fp);
			return 2;
		case 3:
			fp.printAllClasses();
			new ClassManagementScreen(sPtr, fp);
			return 3;
		case 4:
			new ListCoursesByKeywordScreen(sPtr, fp);
			new ClassManagementScreen(sPtr, fp);
			return 4;
		case -2:
			printReenterText();

		}
		return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum(), false);
	}

}