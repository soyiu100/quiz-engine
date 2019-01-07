package screens;

import java.util.Arrays;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class StartingScreen extends AbstractSelectionScreen {

	public StartingScreen(Scanner scan) {
		super(scan, new FileProcessor(), null);
		screenStartAndLoop();
	}

	public StartingScreen(Scanner scan, FileProcessor fp) {
		super(scan, fp, null);
		screenStartAndLoop();
	}

	@Override
	public void initGeneralStart() {
		startingText = "Welcome to the Quiz Engine!\nPress 1, 2, or 3 to select an option::";
	}

	@Override
	public void initCyclingOptions() {
		cycleOptions = Arrays.asList("Course Creation/Deletion", "Quiz Entry Creator", "Start a Quiz!",
				InputParser.quitMessage());
	}

	@Override
	int choiceAction(int prevResult) {
		switch (prevResult) {
		case 1:
			new ClassManagementScreen(sPtr, fp, this);
		case 2:
			new QuestionEditorScreen(sPtr, fp, this);
		case 3:
			// TODO
//			 new QuizTime(sPtr, fp);
		case -1:
			System.exit(2);
		case -2:
			printReenterText();
		}

		return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum());
	}

}
