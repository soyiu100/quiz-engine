package screens;

import java.util.ArrayList;
import java.util.Scanner;

import internals.FileProcessor;
import internals.InputParser;

public class ListCoursesByKeywordScreen extends AbstractOpenInputScreen {
	
	private FileProcessor fp;
	
	public ListCoursesByKeywordScreen(Scanner scan, FileProcessor fp) {
		super(scan);
		this.fp = fp;
		initStartingText();
		screenStartAndLoop();
	}

	@Override
	void initStartingText() {
		startingText = "What's the keyword that you would like to filter the courses by?";
	}

	@Override
	int textAction(String choice) {
		if (choice.equals(InputParser.END_KEY)) {
			new ClassManagementScreen(sPtr, fp);
			return -1;
		}
		fp.printAllClasses(new ArrayList<String>(), choice);
		System.out.println(startingText);
		return 0;
	}

	@Override
	void printReenterText() {}


}
