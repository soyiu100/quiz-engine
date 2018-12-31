package screens;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

// TODO use InputParser.BLACKLISTED_CHARS to filter out bad input with squiggles or backwards apostrophes
public class QuestionEditorScreen extends AbstractSelectionScreen {

	private FileWriter fw;

	/**
	 * TODO more source screens?? 0 if prev screen was StartingScreen, 1 if
	 * ClassEditorScreen
	 */
	private int screenSrc;

	public QuestionEditorScreen(Scanner scan, FileProcessor fp) {
		this(scan, fp, 0);
	}

	public QuestionEditorScreen(Scanner scan, FileProcessor fp, int src) {
		super(scan, fp);
		// TODO Auto-generated constructor stub; this is probably incomplete
		screenSrc = src;
		screenStartAndLoop();
	}

	@Override
	void initGeneralStart() {
		startingText = "Choose a class to write to:";
	}

	@Override
	void initCyclingOptions() {
		cycleOptions = fp.getAllClasses();
	}

	@Override
	int choiceAction(int prevResult) {
		try {
			if (prevResult == -1) {
				if (screenSrc == 0) {
					new StartingScreen(sPtr, fp);
				} else if (screenSrc == 1) {
					new ClassEditorScreen(sPtr, fp);
				}
			} else if (prevResult != 3) {
				fw = new FileWriter(cycleOptions.get(prevResult), true);
				if (fw != null) {
					questionAdder(fw);
					fw.close();
				}
				return prevResult;
			}
			assert (prevResult == -3);
			return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum(), true);

		} catch (IOException e) {
			System.out.println("File creation went wrong...Oops, this should really never happen but");
			return -1;
		} catch (ArrayIndexOutOfBoundsException e) {
			assert (prevResult == -2);
			printReenterText();
			return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum(), true);
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

	private class QuestionAdderScreen extends AbstractOpenInputScreen {

		public QuestionAdderScreen(Scanner scan) {
			super(scan);
			// TODO init any fields here
			initStartingText();
			screenStartAndLoop();
		}

		@Override
		void initStartingText() {
			// TODO Auto-generated method stub

		}

		@Override
		int textAction(String choice) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		void printReenterText() {
			// TODO Auto-generated method stub

		}

	}

	private void questionAdder(FileWriter fw) throws IOException {
		FileWriter localFW = fw;
		s = new Scanner(System.in);
		String input = "";
		boolean loser = false;
		while (!input.equals("q~")) {
			System.out.println("Enter a question:");
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
				if (ss.equals("c") || ss.contains("multiple") || ss.contains("choice") || ss.contains("opt")) {
					qType = "c"; // c for choice
				} else if (ss.equals("s") || ss.contains("open") || ss.contains("short response")
						|| ss.contains("rough")) {
					qType = "s"; // s for short answer
				} else if (ss.equals("e") || ss.contains("math") || ss.contains("exact")) {
					qType = "e"; // e for exact answer
				} else if (ss.equals("q~")) {
					loser = true;
					break;
				} else {
					System.out.println("I'm dumb. Say it in a different way");
				}
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
