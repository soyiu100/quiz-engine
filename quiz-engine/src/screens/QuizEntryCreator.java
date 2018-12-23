package screens;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// TODO if squiggle is found in question or answer that isn't the indicating squiggle, 
// telll them to use a not squiggle
public class QuizEntryCreator {

	public static final String PHIL242_FILENAME = "242";
	public static final String CSE332_FILENAME = "332";
	public static final String MATH308_FILENAME = "308";

	private static final String TEST = "lmao";
	private Scanner s;

	public static void main(String[] args) {
		new QuizEntryCreator().run();
	}

	public void run() {
		File p = new File(PHIL242_FILENAME);
		File c = new File(CSE332_FILENAME);
		File m = new File(MATH308_FILENAME);
		File t = new File(TEST);
		fileCreate();
		s = new Scanner(System.in);
		printClass();
		Integer input = null;
		while (input == null) {
			String str = s.nextLine();
			if (str.equals("q")) {
				break;
			}
			try {
				input = Integer.parseInt(str);
			} catch (Exception e) {
				System.out.println("Wow, wtf!!!! Enter 1, 2, or 3. Loser.");
			}
		}
		FileWriter fw = null;
		try {
			if (input == 0) {
				fw = new FileWriter(t.getName(), true);
			} else if (input == 1) {
				fw = new FileWriter(p.getName(), true);
			} else if (input == 2) {
				fw = new FileWriter(c.getName(), true);
			} else if (input == 3) {
				fw = new FileWriter(m.getName(), true);
			} else {
				System.out.println("Try again Dingodile");
				printClass();
			}
			if (fw != null) {
				questionAdder(fw);
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void fileCreate() {
		File p = new File(PHIL242_FILENAME);
		File c = new File(CSE332_FILENAME);
		File m = new File(MATH308_FILENAME);
		File t = new File(TEST);
		try {
			if (!p.exists()) {
				p.createNewFile();

			} else if (!c.exists()) {
				c.createNewFile();

			} else if (!m.exists()) {
				m.createNewFile();

			} else if (!t.exists()) {
				t.createNewFile();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void printClass() {
		System.out.println("Choose a class to write to (Enter 1, 2, or 3):");
		System.out.println("1: PHIL 242");
		System.out.println("2: CSE 332");
		System.out.println("3: MATH 308");
	}

	// private void questionEditor() {
	// // TODO future implementation; for now, this isn't that important
	// }

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
}
