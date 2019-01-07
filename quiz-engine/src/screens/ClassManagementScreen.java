package screens;

import java.util.Arrays;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class ClassManagementScreen extends AbstractSelectionScreen {

	public ClassManagementScreen(Scanner scan, FileProcessor fp, AbstractSelectionScreen scr) {
		super(scan, fp, scr);
		screenStartAndLoop();
	}

	@Override
	public void initGeneralStart() {
		startingText = "What would you like to do?";

	}

	@Override
	void initCyclingOptions() {
		cycleOptions = Arrays.asList("Create a course", "Delete a course", "List all course quizzes",
				"List all course quizzes by a certain keyword", InputParser.quitMessage());

	}

	@Override
	int choiceAction(int prevResult) {
		switch (prevResult) {
		case 1:
			new ClassEditorScreen(sPtr, fp, this, true);
		case 2:
			new ClassEditorScreen(sPtr, fp, this);
		case 3:
			InputParser.printAllClasses(fp);
			screenStartAndLoop();
		case 4:
			new ListCoursesByKeywordScreen(sPtr, fp, this);
			screenStartAndLoop();
		case -1:
			prevScr.screenStartAndLoop();
		case -2:
			printReenterText();
		}
		return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum());
	}

}