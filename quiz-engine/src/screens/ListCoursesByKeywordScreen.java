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
		startingText = "What's the keyword that you would like to filter the courses by?";
	}

	@Override
	int textAction(String choice) {
		if (choice.equals(InputParser.END_KEY)) {
			prevScr.screenStartAndLoop();
			return -1;
		}
		fp.printAllClasses(new ArrayList<String>(), choice);
		System.out.println(startingText);
		return 0;
	}

	@Override
	public void printReenterText() {}


}
