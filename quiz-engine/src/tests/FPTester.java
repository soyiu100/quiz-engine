package tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import internals.FileProcessor;
import internals.FileProcessor.MutInt;

public class FPTester {

	public static void main(String[] args) {
		int totalClasses = 4 + 1001;

		FileProcessor fp = new FileProcessor();
		int count = 0;
		while (!fp.getAllClasses().contains("null") && !fp.getAllClasses().contains(null)
				&& fp.numClasses() == totalClasses) {
			fp = new FileProcessor();
			count++;
		}
		Set<String> ls = new HashSet<String>(fp.getAllClasses());
		System.out.println(ls);
		for (MutInt id : fp.getIndexes()) {
			System.out.print(id.val + ",");
		}
		System.out.println();
		ls.remove("");
		System.out.println("blanks removed");
		System.out.println(ls);
		System.out.println(ls.size());
		System.out.println(fp.numClasses());
		System.out.println("the bug is still here lmao");
		Set<String> allFiles = new HashSet<String>();
		for (int i = 0; i < 1001; i++) {
			if (i / 1000 > 0) {
				allFiles.add("0" + i + ".text");
			} else if (i / 100 > 0) {
				allFiles.add("00" + i + ".text");
			} else if (i / 10 > 0) {
				allFiles.add("000" + i + ".text");
			} else {
				allFiles.add("0000" + i + ".text");
			}
		}
		allFiles.add("332");
		allFiles.add("308");
		allFiles.add("lmao");
		allFiles.add("242");
		System.out.println(allFiles);
		System.out.println(allFiles.size());
		for (String s : fp.getAllClasses()) {
			allFiles.remove(s);
		}
		System.out.println(allFiles);
		System.out.println(count);
	}

}
