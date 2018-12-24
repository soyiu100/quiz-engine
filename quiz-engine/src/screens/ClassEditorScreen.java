package screens;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import internals.InputParser;

public class ClassEditorScreen extends AbstractOpenInputScreen {

	private boolean creating;
	private boolean srcBlock;

	public ClassEditorScreen(Scanner scan) {
		this(scan, false);
	}

	public ClassEditorScreen(Scanner scan, boolean creating) {
		super(scan);
		this.creating = creating;
		this.srcBlock = true;
		initStartingText();
		screenStartAndLoop();

	}

	@Override
	void initStartingText() {
		if (creating) {
			startingText = "What course would you like to create?";
		} else {
			startingText = "What course would you like to delete?";
		}
	}

	@Override
	void textAction(String choice) {
		if (choice.equals(InputParser.END_KEY)) {
			new ClassManagementScreen(sPtr);
		} else {
			File potentFile = new File(choice);
			if (!InputParser.BLACKLISTED_NAMES.contains(choice)) {
				// TODO possible refactoring?
				try {
					potentFile.createNewFile();
					System.out.println("Course created!");
					System.out.println("Would you like to add questions to this course?");
					// TODO
					aaa
				} catch (IOException e) {
					printReenterText();
				}
			} else {
				if (srcBlock && choice.equals("src")) {
					srcBlock = false;
				} else {
					System.out.println("Print a valid name.");
					initStartingText();
				}

			}

		}
	}

	@Override
	void printReenterText() {
		if (creating > 0) {
			System.out.println("Please create a course that does not exist.");
		} else {
			System.out.println("Please delete a course that exists.");
		}

	}

}
