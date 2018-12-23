package screens;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import internals.InputParser;

public class ClassRegisterScreen extends AbstractSelectionScreen {
	private List<String> filenames;

	public ClassRegisterScreen(Scanner scan) {
		super(scan);

		filenames = new ArrayList<>();
		initFilenames();
	}

	private void initFilenames() {
		File[] files = new File(".").listFiles();
		for (File f : files) {
			if (!InputParser.BLACKLISTED_NAMES.contains(f.getName())) {
				filenames.add(f.getName());
			}
		}
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
		// TODO Auto-generated method stub
		return 0;
	}

}