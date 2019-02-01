package screens;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

// TODO use InputParser.BLACKLISTED_CHARS to filter out bad input with squiggles or backwards apostrophes
public class QuestionEditorScreen extends AbstractSelectionScreen {
	
	// TODO pretty sure you don't need a filewriter,,,,

	public QuestionEditorScreen(Scanner scan, FileProcessor fp, AbstractScreen scr) {
		this(scan, fp, scr, 0);
	}

	public QuestionEditorScreen(Scanner scan, FileProcessor fp, AbstractScreen scr, int optionStart) {
		super(scan, fp, scr);
		if (optionStart != 0) {
			// TODO refer to changelog
			new QuestionAdderScreen(scan, this, cycleOptions.get(optionStart), true);
		} else {
			screenStartAndLoop();
		}

	}

	@Override
	public void initGeneralStart() {
		startingText = "Choose a class to write to:";
	}

	@Override
	void initCyclingOptions() {
		cycleOptions.addAll(fp.getAllClasses());
		cycleOptions.add(InputParser.quitMessage());
	}

	@Override
	int choiceAction(int prevResult) {
		try {
			if (prevResult == -1) {
				prevScr.screenStartAndLoop();
			} else if (prevResult != -3) {
				new QuestionAdderScreen(sPtr, this, cycleOptions.get(prevResult - 1), true);
				return prevResult;
			}
			assert (prevResult == -3);
			return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum());

		} catch (ArrayIndexOutOfBoundsException e) {
			assert (prevResult == -2);
			printReenterText();
			return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum());
		}
	}

	// TODO
	// TODO
	// TODO
	// TODO
	// TODO
	// TODO
	// TODO
	// TODO
	// TODO
	// TODO
	// TODO

	private class QuestionAdderScreen extends AbstractMultiLineInputScreen {

		FileWriter fw;
		private StringBuilder qBuild;

		public QuestionAdderScreen(Scanner scan, AbstractScreen scr, String filename, boolean ignore) {
			super(scan, scr);
			// TODO init any fields here
			initGeneralStart();
			initEndInputKey();
			try {
				this.fw = new FileWriter(filename);
			} catch (IOException e) {
				e.printStackTrace(); // TODO change message maybe?
				scr.screenStartAndLoop();
			}
			this.ignoreBlankLines = ignore;
			this.qBuild = new StringBuilder();
			screenStartAndLoop();
		}

		@Override
		public void initGeneralStart() {
			startingText = "Enter a question:";
		}

		@Override
		int textAction(String choice) {
			if (choice != null) {
				if (choice.equals(InputParser.SCREND_KEY)) {
					prevScr.screenStartAndLoop();
					return -1;
				} else {
					if (choice.equals(endInputKey)) {
						new QuestionTypeAdderScreen(sPtr, prevScr, fwPtr, qBuild);
					}
				}
			}
			return 0;
		}

		@Override
		public void printReenterText() {
		}

		@Override
		void initEndInputKey() {
			endInputKey = "//";
		}

	}

	private class QuestionTypeAdderScreen extends AbstractOpenInputScreen {

		FileWriter fwPtr;
		// TODO: this could be deletable, and substituted for the super field scr, since
		// these screen never need the qes ptr
		private QuestionAdderScreen qasPtr;
		private StringBuilder qFragPtr;

		public QuestionTypeAdderScreen(Scanner scan, AbstractScreen scr) {
			super(scan, scr);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void initGeneralStart() {
			// TODO Auto-generated method stub

		}

		@Override
		public void printReenterText() {
			// TODO Auto-generated method stub

		}

		// TODO
		/**
		 * You're not allowed to q` out of this screen so it will deny leaving on this
		 * screen. Ever. >:)
		 */
		@Override
		int textAction(String choice) {
			String qType = "";
			if (choice.equals("c") || choice.contains("multiple") || choice.contains("choice") || choice.contains("opt")) {
				qType = "c"; // c for choice
			} else if (choice.equals("s") || choice.contains("open") || choice.contains("short response")
					|| choice.contains("rough")) {
				qType = "s"; // s for short answer
			} else if (choice.equals("e") || choice.contains("math") || choice.contains("exact")) {
				qType = "e"; // e for exact answer
			} else if (choice.equals("q~")) {
				System.out.println("You can't quit, stupid...");
			} else {
				System.out.println("I'm dumb. Say it in a different way");
			}
			if (!qType.isEmpty()) {
				new
				// TOOD include qType as a param
				return -3;
			}
			return 0;
		}

	}

	private class AnswerAdderScreen extends AbstractMultiLineInputScreen {

		FileWriter fwPtr;
		private QuestionAdderScreen qasPtr;

		public AnswerAdderScreen(Scanner scan, AbstractScreen scr) {
			super(scan, scr);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void initGeneralStart() {
			// TODO Auto-generated method stub

		}

		@Override
		public void printReenterText() {
			// TODO Auto-generated method stub

		}

		@Override
		void initEndInputKey() {
			// TODO Auto-generated method stub

		}

		/**
		 * You can't q` out of this screen either.
		 */
		@Override
		int textAction(String choice) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	private void questionAdder(FileWriter fw) throws IOException {
		FileWriter localFW = fw;
		s = new Scanner(System.in);
		String input = "";
		boolean loser = false;
		while (!input.equals("q~")) {
			String qBit = "";
			while (!qBit.equals("//")) {
				if (!qBit.equals("")) {
					if (qBit.equals("q~")) {
						loser = true;
						break;
					}
					input += qBit + "\n";
				}
				qBit = s.nextLine();
			}
			if (loser)
				break;
			String qType = null;
			while (qType == null) {
				System.out.println("What type of question is it?");
				String ss = s.nextLine().toLowerCase();

			}
			if (loser)
				break;
			localFW.write(qType + "~" + input);
			input = "";
			if (qType.equals("o")) {
				System.out.println("List possible answers for this question. \nMark wrong answers with a '-' "
						+ "preceding them, and a '+' preceding the single right answer.");
				// TODO need to implement -, + checkers; out of all practicality, this isn't a
				// thing yet
				qBit = "";
				while (!qBit.equals("//")) {
					if (!qBit.equals("")) {
						input += qBit + "\n";
					}
					qBit = s.nextLine();
				}
				localFW.write(input);
			} else if (qType.equals("s")) {
				System.out.println("Answer the approximate answer that you would like for this question.\n"
						+ "Although you only need to list one, you may list mutliple solutions that "
						+ "could be the answer to this solution. \n Make sure you list answer(s) with an "
						+ "asterisk preceding them.");
				qBit = "";
				while (!qBit.equals("//")) {
					if (!qBit.equals("")) {
						input += qBit + "\n";
					}
					qBit = s.nextLine();
				}
				localFW.write(input);
			} else if (qType.equals("e")) {
				System.out.println("Answer the exact answer that you would like for this question. \nWrite carefully, "
						+ "the quiz taker will be expected to write the exact phrase as the answer \nthat you give.");
				qBit = "";
				while (!qBit.equals("//")) {
					if (!qBit.equals("")) {
						input += qBit + "\n";
					}
					qBit = s.nextLine();
				}
				localFW.write(input);
			}
			input = "";
		}
	}

	// private void questionEditor() {
	// // TODO future implementation; for now, this isn't that important
	// }

}
