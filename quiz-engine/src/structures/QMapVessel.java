package structures;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QMapVessel {

	// TODO is this class neccessary for other classes besides quiztime bro
	// come back to this fundamental q
	
	private Map<String, ArrayList<String>> qToChoice;
	private Map<String, ArrayList<String>> qToShort;
	private Map<String, String> qToExact;

	private Map<Integer, String> indToQ;
	
	public QMapVessel() {
		qToChoice = new HashMap<>();
		qToShort = new HashMap<>();
		qToExact = new HashMap<>();

		indToQ = new HashMap<>();
	}
	
	// TODO THIS IS ABSOLUTELY DISGUSTING WTF
	public void populate(BufferedReader br) throws IOException {
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
	
	// TODO
	// reminder that new copies are for basic security, so it's up to you that you use the get wisely
	
	public Map<String, ArrayList<String>> getQC() {
		return new HashMap<>(qToChoice);
	}
	
	public Map<String, ArrayList<String>> getQS() {
		return new HashMap<>(qToShort);
	}
	
	public Map<String, String> getQE() {
		return new HashMap<>(qToExact);
	}
	
	public Map<Integer, String> getITQ() {
		return new HashMap<>(indToQ);
	}
}
