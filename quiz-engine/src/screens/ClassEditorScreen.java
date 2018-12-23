package screens;

import java.util.Scanner;

public class ClassEditorScreen extends AbstractOpenInputScreen {
	
	private boolean creating;
	
	public ClassEditorScreen(Scanner scan) { this(scan, false); }
	
	public ClassEditorScreen(Scanner scan, boolean creating) {
		super(scan);
		this.creating = creating;
	}

	@Override
	void initStartingText() {
		// TODO Auto-generated method stub
	}

	@Override
	void screenStartAndLoop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void printReenterText() {
		if (creating) {
			System.out.println("Please create a course that does not exist.");
		} else {
			System.out.println("Please delete a course that exists.");
		}
		
	}

	
}
