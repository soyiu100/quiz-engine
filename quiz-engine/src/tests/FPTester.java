package tests;

import internals.FileProcessor;

public class FPTester {
	
	public static void main(String[] args) {
		int totalClasses = 4 + 11;
		
		FileProcessor fp = new FileProcessor();
//		int count = 0;
//		while (!fp.getAllClasses().contains("null") && !fp.getAllClasses().contains(null) && fp.getAllClasses().size() == totalClasses) {
//			fp = new FileProcessor();
//			count++;
//		}
		System.out.println(fp.getAllClasses());
		System.out.println(fp.numClasses());
//		System.out.println("the bug is still here lmao");
//		System.out.println(count);
	}

}
