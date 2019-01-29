package screens;

import java.util.ArrayList;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class ListCoursesByKeywordScreen extends AbstractOpenInputScreen {

	private FileProcessor fp;

	public ListCoursesByKeywordScreen(Scanner scan, FileProcessor fp, AbstractScreen scr) {
		super(scan, scr);
		this.fp = fp;
		initGeneralStart();
		screenStartAndLoop();
	}

	@Override
	public void initGeneralStart() {
		startingText = "What's the keyword that you would like to filter the courses by?\n(" + InputParser.quitMessage()
				+ ")";
	}

	@Override
	int textAction(String choice) {
		if (choice != null) {
			if (choice.equals(InputParser.SCREND_KEY)) {
				prevScr.screenStartAndLoop();
				return -1;
			}
			InputParser.printAllClasses(new ArrayList<String>(), choice, fp);
		} 
		System.out.println(startingText);
		return 0;
	}

	@Override
	public void printReenterText() {
	}

}
