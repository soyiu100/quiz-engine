package screens;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class QuizTime {

	private static final String ANSI = "\033[";

	/**
	 * 0m - reset 
	 * 30m - black 
	 * 31m - red 
	 * 32m - green 
	 * 33m - yellow 
	 * 34m - blue 
	 * 35m - purple 
	 * 36m - cyan
	 * 37m - grey
	 */
	private static final String RESET = "0m";

	private Map<String, ArrayList<String>> qToChoice;
	private Map<String, ArrayList<String>> qToShort;
	private Map<String, String> qToExact;

	private Map<Integer, String> indToQ;

	public QuizTime() {
		qToChoice = new HashMap<>();
		qToShort = new HashMap<>();
		qToExact = new HashMap<>();

		indToQ = new HashMap<>();
	}

	public static void main(String[] args) {
		new QuizTime().run();
	}

	public void run() {
		boolean quitter = false;

		File p = new File(QuestionEditorScreen.PHIL242_FILENAME);
		File c = new File(QuestionEditorScreen.CSE332_FILENAME);
		File m = new File(QuestionEditorScreen.MATH308_FILENAME);

		File t = new File("lmao");
		if (!p.exists() || !c.exists() || !m.exists() || !t.exists()) {
			QuestionEditorScreen.fileCreate();
		}
		s = new Scanner(System.in);
		printClass();
		Integer input = null;
		while (input == null) {
			String str = s.nextLine();
			if (str.toLowerCase().equals("q")) {
				quitter = true;
				break;
			}
			try {
				input = Integer.parseInt(str);
			} catch (Exception e) {
				System.out.println("Wow, wtf!!!! Enter 1, 2, or 3. Loser.");
			}
		}
		if (!quitter) {
			BufferedReader br = null;
			try {
				if (input.intValue() == 1) {
					br = new BufferedReader(new FileReader(p.getName()));
				} else if (input.intValue() == 2) {
					br = new BufferedReader(new FileReader(c.getName()));
				} else if (input.intValue() == 3) {
					br = new BufferedReader(new FileReader(m.getName()));
				} else {
					if (input.intValue() == 0) {
						br = new BufferedReader(new FileReader("lmao"));
					} else {
						System.out.println("Try again Dingodile");
						run();
					}
				}
				if (input != null) {
					String line = br.readLine();
					String currQuestion = "";
					String currQType = ""; // needed once the question and answer are compiled and finished to put in
											// map
					String currAnswer = "";
					int index = -1;
					String startingChar = "";
					while (line != null) {
						if (line.length() > 0) {
							if (line.startsWith("c~") || line.startsWith("s~") || line.startsWith("e~")) {
								if (!startingChar.equals("")) { // this is not the first question, and we've reached the
																// end
																// of a question
									index++;
									indToQ.put(index, currQuestion);
									if (currQType.equals("c~")) {
										ArrayList<String> exAns = qToChoice.get(currQuestion);
										if (exAns == null) {
											exAns = new ArrayList<>();
										}
										exAns.add(currAnswer);
										qToChoice.put(currQuestion, exAns);
									} else if (currQType.equals("s~")) {
										ArrayList<String> exAns = qToShort.get(currQuestion);
										if (exAns == null) {
											exAns = new ArrayList<>();
										}
										exAns.add(currAnswer);
										qToShort.put(currQuestion, exAns);
									} else if (currQType.equals("e~")) {
										qToExact.put(currQuestion, currAnswer);
									}
								}
								currQuestion = "";
								currAnswer = "";
								currQType = line.substring(0, 2);
								startingChar = line.substring(0, 2);
								currQuestion += line;
							} else if (line.startsWith("^") || line.startsWith("*") || line.startsWith("+")
									|| line.startsWith("-")) {
								if (!currAnswer.equals("")) { // is not the first answer
									if (currQType.equals("c~")) {
										ArrayList<String> exAns = qToChoice.get(currQuestion);
										if (exAns == null) {
											exAns = new ArrayList<>();
										}
										exAns.add(currAnswer);
										qToChoice.put(currQuestion, exAns);
									} else if (currQType.equals("s~")) {
										ArrayList<String> exAns = qToShort.get(currQuestion);
										if (exAns == null) {
											exAns = new ArrayList<>();
										}
										exAns.add(currAnswer);
										qToShort.put(currQuestion, exAns);
									}
									// e~ should only have one answer...
									currAnswer = "";
								}
								startingChar = line.substring(0, 1);
								currAnswer += line;
							} else {
								if (startingChar.equals("^") || startingChar.equals("*") || startingChar.equals("+")
										|| startingChar.equals("-")) {
									currAnswer += "\n" + line;
								} else {
									currQuestion += "\n" + line;
								}
							}
						}
						line = br.readLine();

					}
					index++;
					indToQ.put(index, currQuestion);
					if (currQType.equals("c~")) {
						ArrayList<String> exAns = qToChoice.get(currQuestion);
						if (exAns == null) {
							exAns = new ArrayList<>();
						}
						exAns.add(currAnswer);
						qToChoice.put(currQuestion, exAns);
					} else if (currQType.equals("s~")) {
						ArrayList<String> exAns = qToShort.get(currQuestion);
						if (exAns == null) {
							exAns = new ArrayList<>();
						}
						exAns.add(currAnswer);
						qToShort.put(currQuestion, exAns);
					} else if (currQType.equals("e~")) {
						qToExact.put(currQuestion, currAnswer);
					}

				}

			} catch (Exception e) {
				quitter = true;
				e.printStackTrace();
				System.out.println("Boy, I hope somebody got fired for that blunder...");
			}
			System.out.println("Everything's processed!");
			String str = "";
			String qAsked = "";
			int origQSize = indToQ.size();
			while (!str.equals("q") || indToQ.isEmpty()) {
				int randomInd = (int) (Math.random() * origQSize);
				if (indToQ.size() == 0) {
					break;
				}
				while (indToQ.get(randomInd) == null) {
					randomInd = (int) (Math.random() * origQSize);
				}
				qAsked = indToQ.get(randomInd);
				indToQ.remove(randomInd);
				String[] typeToQuestion = qAsked.split("~");
				System.out.println(ANSI + "0;37m" + ANSI + "1;40m" + "Here's a question::" + ANSI + RESET);
				if (typeToQuestion.length != 2) {
					System.out.println("Unepic goof...");
					break;
				} else {
					System.out.println(typeToQuestion[1]);
					if (typeToQuestion[0].equals("c")) {
						System.out.println(ANSI + "0;36m" + "Type in one of the choices word for word." + ANSI + RESET);
						ArrayList<String> choices = qToChoice.get(qAsked);
						ArrayList<String> correctChoices = new ArrayList<>();
						for (String choice : choices) {
							if (choice.charAt(0) == '+') {
								correctChoices.add(choice.substring(1));
							}
							System.out.println(">" + choice.substring(1));
						}
						String answer = "";
						String aBit = "";
						boolean giveUp = false;
						while (!aBit.equals("//")) {
							if (!aBit.equals("")) {
								if (aBit.equals("//c")) {
									answer = "";
								} else if (aBit.equals("//gg")) {
									giveUp = true;
									break;
								} else {
									answer += aBit + "\n";
								}
							}
							aBit = s.nextLine();
						}
						if (answer.length() > 0)
							answer = answer.substring(0, answer.length() - 1);
						if (!correctChoices.contains(answer) || giveUp) {
							System.out
									.println(ANSI + "0;31m" + ANSI + "1;40m" + "Wow, incorrect, vro..." + ANSI + RESET);
							System.out.println("Here are some choice(s) that you could have chosen...");
							for (String choice : correctChoices)
								System.out.println(choice);
						} else {
							System.out.println("Wow. Nice.");
						}
					} else if (typeToQuestion[0].equals("s")) {
						System.out.println(ANSI + "0;36m"
								+ "Answer this open-ended question to the best of your ability." + ANSI + RESET);
						ArrayList<String> shorts = qToShort.get(qAsked);
						String answer = "";
						String aBit = "";
						boolean giveUp = false;
						while (!aBit.equals("//")) {
							if (!aBit.equals("")) {
								if (aBit.equals("//c")) {
									answer = "";
								} else if (aBit.equals("//gg")) {
									giveUp = true;
									break;
								} else {
									answer += aBit + "\n";
								}
							}
							aBit = s.nextLine();
						}
						if (answer.length() > 0)
							answer = answer.substring(0, answer.length() - 1);
						if (giveUp) {
							System.out.println("Wow. Ok. You didn't even try...");
						} else if (shorts.contains("*" + answer)) {
							System.out.println("Wow. How did you it get so exactly???!");
						} else {
							int index = 0;
							int bestIndex = 0;
							int leastDiff = Integer.MAX_VALUE;
							int choicesLength = 0;
							while (index <= shorts.size() - 1) {
								choicesLength += shorts.get(index).length() - 1;
								index++;
							}
							index = 0;
							choicesLength /= shorts.size();
							if (choicesLength * 1.25 < answer.length()) {
								System.out.println("Your answer is too long...");
								System.out.println(ANSI + "0;31m" + ANSI + "1;40m" + "Vro..." + ANSI + RESET);
								System.out.println("Here are some answer(s) that you could have chosen...");
								for (String sh : shorts)
									System.out.println(sh);
							} else {
								String[] words = answer.split(" ");
								while (index <= shorts.size() - 1) {
									Set<String> wSet = new HashSet<>();
									String[] shs = shorts.get(index).split(" ");
									for (String word : words) {
										wSet.add(word);
									}
									for (String word : shs) {
										wSet.remove(word);
									}
									if (leastDiff > wSet.size()) {
										bestIndex = index;
										leastDiff = wSet.size();
									}
									index++;
								}
								if (leastDiff >= words.length / 2 && words.length >= 10) { // there were more than half
																							// the words that were
									// different from the listed answers
									// TODO make 10 a global variable or something; just one of
									// the many things to fix here lol
									System.out.println("Looks like you were pretty off...");
								} else {
									System.out.println("Don't know how close you were, but!");
								}
								System.out.println("Here's a similar answer::");
								System.out.println(shorts.get(bestIndex).substring(1));
							}
						}
					} else if (typeToQuestion[0].equals("e")) {
						System.out.println(ANSI + "0;36m" + "There should be only one answer for this. Type correctly."
								+ ANSI + RESET);
						String answer = "";
						String aBit = "";
						boolean giveUp = false;
						while (!aBit.equals("//")) {
							if (!aBit.equals("")) {
								if (aBit.equals("//c")) {
									answer = "";
								} else if (aBit.equals("//gg")) {
									giveUp = true;
									break;
								} else {
									answer += aBit + "\n";
								}
							}
							aBit = s.nextLine();
						}
						if (answer.length() > 0)
							answer = answer.substring(0, answer.length() - 1);
						if (!answer.equals(qToExact.get(qAsked).substring(1)) || giveUp) {
							System.out
									.println(ANSI + "0;31m" + ANSI + "1;40m" + "Wow, incorrect, vro..." + ANSI + RESET);
							System.out.println("Here's the correct answer:");
							System.out.println(qToExact.get(qAsked).substring(1));
						} else {
							System.out.println("Wow. Nice, you got it");

						}
					} else {
						System.out.println("Unepic goof...");
						break;
					}
					System.out.println();
				}

			}
			System.out.println("That's all, folk. Lol");
		} else {
			System.out.println("How unepic...");
		}

	}

	private void printClass() {
		System.out.println("Choose a class to quiz from (Enter 1, 2, or 3):");
		System.out.println("1: PHIL 242");
		System.out.println("2: CSE 332");
		System.out.println("3: MATH 308");
	}
}
