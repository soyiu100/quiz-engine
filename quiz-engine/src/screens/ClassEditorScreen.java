package screens;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class ClassEditorScreen extends AbstractOpenInputScreen {

	private boolean creating;

	private FileProcessor fp;

	public ClassEditorScreen(Scanner scan, FileProcessor fp, AbstractScreen scr) { // fp arg in case the user is to
																					// return to the previous screen
		this(scan, fp, scr, false);
	}

	public ClassEditorScreen(Scanner scan, FileProcessor fp, AbstractScreen scr, boolean creating) {
		super(scan, scr);
		this.creating = creating;
		this.fp = fp;
		initGeneralStart();
		screenStartAndLoop();

	}

	@Override
	public void initGeneralStart() {
		StringBuilder sb = new StringBuilder();
		if (creating) {
			sb.append("What course would you like to create?");
		} else {
			sb.append("What course would you like to delete?");
		}
		sb.append("\n(");
		sb.append(InputParser.quitMessage());
		sb.append(")");
		startingText = sb.toString();
	}

	@Override
	int textAction(String choice) {
		if (choice != null) {
			if (choice.equals(InputParser.SCREND_KEY)) {
				prevScr.screenStartAndLoop();
				return -1;
			} else {
				File potentFile = new File(choice);
				// TODO redo the filter right below
				if (!FileProcessor.BLACKLISTED_NAMES.contains(choice)) {
					// TODO possible refactoring?
					try {
						if (creating) {
							if (potentFile.exists()) {
								printReenterText();
							} else {
								potentFile.createNewFile();
								fp.addClass(potentFile.getName());
								System.out.println("Course created!");
								System.out.println("Would you like to add questions to this course?");
								String input = sPtr.nextLine();
								int inputResult = 3;
								while (inputResult == 3) {
									if (input.startsWith("yes") || input.equals("y")) {
										new QuestionEditorScreen(sPtr, fp, this, fp.numClasses() - 1);
										inputResult = -1;
									} else if (input.equals("no") || input.equals("n")) {
										screenStartAndLoop();
										inputResult = 0;
									} else {
										System.out.println("Sorry, I didn't understand that.");
										input = sPtr.nextLine();
									}
								}
								return inputResult;
							}

						} else {
							if (potentFile.exists()) {
								potentFile.delete();
								System.out.println("Course deleted!");
								fp.removeClass(potentFile.getName());
							} else {
								printReenterText();
							}
							System.out.println(startingText);
						}
						return 0;
					} catch (IOException e) {
						printReenterText();
					}
				} else {
					System.out.println("Print a valid name.");
					System.out.println(startingText);
				}
			}
			return 0;
		} else {
			System.out.println(startingText);
			return 0;			
		}

	}

	@Override
	public void printReenterText() {
		if (creating) {
			System.out.println("Please create a course that does not exist.");
		} else {
			System.out.println("Please delete a course that exists.");
		}

	}

}
