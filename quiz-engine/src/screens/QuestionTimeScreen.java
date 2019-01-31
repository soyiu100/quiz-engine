package screens;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import structures.QMapVessel;

public class QuestionTimeScreen {
	
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
	
	protected Scanner sPtr;
	protected QMapVessel qmvPtr;
	
	// TODO
	public QuestionTimeScreen(Scanner scan, QMapVessel qmv) {
		// this line below shouldn't be necessary if extending soon
		sPtr = scan;
		
		qmvPtr = qmv;
		initLocalQMap();
		dummy();
	}
	
	public void initLocalQMap() {
		
	}
	
	public void dummy() {
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

}}
