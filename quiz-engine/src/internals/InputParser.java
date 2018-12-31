package internals;

import java.util.Arrays;
import java.util.List;

public class InputParser {

	public static final List<Character> BLACKLISTED_CHARS = Arrays.asList('`', '~');
	
	public static final String ENDALL_KEY = "q~";
	public static final String END_KEY = "q`";

	private static final String INSTRC = "...or quit (type \"";
	private static final String INSTRC_END = "\")";

	public static String quitMessage(boolean endAll) {
		StringBuilder q = new StringBuilder(INSTRC);
		if (endAll)
			return (q.append(ENDALL_KEY)).append(INSTRC_END).toString();
		return (q.append(END_KEY)).append(INSTRC_END).toString();

	}

	/**
	 * 
	 * @param input
	 *            the string entered by the user
	 * @param endAll
	 *            determines if this is the main screen that will end the whole
	 *            program if true; if false, is just a sub-screen of the program
	 * @return -1 if user wants to quit from the current screen; -2 if other
	 *         gibberish input; otherwise, returns the choice that the user selected
	 */
	public static int choiceCheck(String input, int numOpts, boolean endAll) {
		if ((input.equals(ENDALL_KEY) && endAll) || (input.equals(END_KEY) && !endAll)) {
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
