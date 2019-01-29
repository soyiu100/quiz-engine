package screens;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;
import structures.QMapVessel;

public class QuizTimeScreen extends AbstractSelectionScreen {

	public QuizTimeScreen(Scanner scan, FileProcessor fp, AbstractScreen scr) {
		super(scan, fp, scr);
		screenStartAndLoop();
	}

	@Override
	public void initGeneralStart() {
		startingText = "Choose a class to quiz from:";
	}

	@Override
	void initCyclingOptions() {
		cycleOptions = fp.getAllClasses();
		cycleOptions.add(InputParser.quitMessage());
	}

	@Override
	int choiceAction(int prevResult) {
		if (prevResult == -1) {
			prevScr.screenStartAndLoop();
		} else if (prevResult != -3) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(cycleOptions.get(prevResult - 1)));
				QMapVessel qmv = new QMapVessel();
				qmv.populate(br);
				System.out.println(qmv.getQC());
				System.out.println(qmv.getQS());
				System.out.println(qmv.getQE());
				System.out.println(qmv.getITQ());

			} catch (Exception e) {
				// TODO what is the right msg
				System.out.println("Try again Dingodile");
				screenStartAndLoop();
			}
		}

		return InputParser.choiceCheck(sPtr.nextLine(), getOptionNum());
	}

}
