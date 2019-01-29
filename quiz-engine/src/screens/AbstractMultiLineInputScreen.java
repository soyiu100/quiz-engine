package screens;

import java.util.Scanner;

public abstract class AbstractMultiLineInputScreen extends AbstractOpenInputScreen {
	
	protected boolean ignoreBlankLines;
	protected String endInputKey;
	
	protected String answerBit;

	public AbstractMultiLineInputScreen(Scanner scan, AbstractScreen scr) {
		super(scan, scr);
		// TODO Auto-generated constructor stub
	}
	
	// TODO: the possibility if overriding the screen loop
	
	abstract void initEndInputKey();

	


}
