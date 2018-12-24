package screens;

import java.util.Arrays;
import java.util.Scanner;

import internals.InputParser;

public class StartingScreen extends AbstractSelectionScreen {
		
	public StartingScreen(Scanner scan) {
		super(scan);
	}

	@Override
	public void initStartingText() {
		startingText = "Welcome to the Quiz Engine!\nPress 1, 2, or 3 to select an option::";
	}

	@Override
	public void initCyclingOptions() {
		cycleOptions = Arrays.asList("Course Creation/Deletion",
				"Quiz Entry Creator", "Start a Quiz!", InputParser.quitMessage(true));
	}

	@Override
	int choiceAction(int prevResult) {
		if (prevResult == 1) {
			new ClassManagementScreen(sPtr);
			return 1;
		} else if (prevResult == 2) {
			new QuizEntryCreator(sPtr);
			return 2;
		} else if (prevResult == 3) {
			new QuizTime(sPtr);
			return 3;
		}
		if (prevResult == -2) {
			printReenterText();
		}
		return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum(), true);
	}

}
