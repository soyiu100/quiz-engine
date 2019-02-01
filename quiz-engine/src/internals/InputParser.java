package internals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import internals.FileProcessor.WhiteOutTask;

public class InputParser {

	public static final List<Character> BLACKLISTED_CHARS = Arrays.asList('`', '~', '/');

	// public static final String ENDALL_KEY = "q~";
	public static final String SCREND_KEY = "q`";

	private static final String INSTRC = "...or quit (type \"";
	private static final String INSTRC_END = "\")";

	private final static ForkJoinPool POOL = new ForkJoinPool();

	public static void printAllClasses(FileProcessor fp) {
		printAllClasses("", fp);
	}

	// TODO separate printing from processing; like have it return something so that
	// the screen can print or something
	public static void printAllClasses(String keyword, FileProcessor fp) {
		if (!FileProcessor.BLACKLISTED_NAMES.contains(keyword)) {
			String possInitStr = "== Here are all of the courses currently created";
			if (keyword.length() == 0) {
				System.out.print(possInitStr);
				System.out.println(" ==");
				for (String filename : fp.getAllClasses()) {
					System.out.println(filename);
				}
			} else {
				String respondent = ", filtered by the keyword \"" + keyword + "\" ==";
				Set<String> allClasses = fp.getAllClasses();
				String[] names = allClasses.toArray(new String[allClasses.size()]);
				POOL.invoke(new WhiteOutTask(names, 0, fp.getAllClasses().size(), keyword, false, false));
				allClasses = new HashSet<String>(Arrays.asList(names));
				allClasses.remove("");
				if (allClasses.isEmpty()) {
					System.out.print("== There are no courses" + respondent);
				} else {
					System.out.print(possInitStr);
					System.out.println(respondent);
					for (String filename : allClasses) {
						System.out.println(filename);
					}
				}

			}

			System.out.println();
		}
	}

	public static String quitMessage() {
		StringBuilder q = new StringBuilder(INSTRC);
		// if (endAll)
		// return (q.append(ENDALL_KEY)).append(INSTRC_END).toString();
		return (q.append(SCREND_KEY)).append(INSTRC_END).toString();

	}

	/**
	 * 
	 * @param input
	 *            the string entered by the user
	 * @return -1 if user wants to quit from the current screen; -2 if other
	 *         gibberish input; otherwise, returns the choice that the user selected
	 */
	public static int choiceCheck(String input, int numOpts) {
		return choiceCheck(input, numOpts, SCREND_KEY);
	}
	
	public static int choiceCheck(String input, int numOpts, String customEndKey) {
		if ((input.equals(customEndKey))
		// || (input.equals(ENDALL_KEY) && endAll == -1)) {
		) {
			return -1;
		}
		try {
			int temp = Integer.parseInt(input);
			if (temp <= 0 || temp > numOpts) {
				return -2;
			}
			return temp;
		} catch (Exception e) {
			return -2;
		}
	}
}
