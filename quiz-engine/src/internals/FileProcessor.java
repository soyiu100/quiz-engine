package internals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FileProcessor {

	public static final List<String> BLACKLISTED_NAMES = Arrays.asList("src", "README", "changelog", "bin", "jsoup",
			"pom", "target");

	private final static ForkJoinPool POOL = new ForkJoinPool();

	private List<String> allFilenames;

	public FileProcessor() {
		allFilenames = new ArrayList<String>();
		File[] files = new File(".").listFiles();
		POOL.invoke(new ClassListingTask(files, allFilenames, 0, files.length, ""));

	}

	public List<String> getAllClasses() {
		return new ArrayList<String>(allFilenames);
	}

	public void printAllClasses() {
		printAllClasses(null, "");
	}

	public void printAllClasses(List<String> newList, String keyword) {
		if (!BLACKLISTED_NAMES.contains(keyword)) {
			String possInitStr = "== Here are all of the courses currently created";
			if (keyword.length() == 0 || newList == null) {
				System.out.print(possInitStr);
				System.out.println(" ==");
				for (String filename : allFilenames) {
					System.out.println(filename);
				}
			} else {
				String respondent = ", filtered by the keyword \"" + keyword + "\" ==";
				POOL.invoke(new ClassListingTask(allFilenames, newList, 0, allFilenames.size(), keyword));
				if (newList.isEmpty()) {
					System.out.print("== There are no courses" + respondent);
				} else {
					System.out.print(possInitStr);
					System.out.println(respondent);
					for (String filename : newList) {
						System.out.println(filename);
					}
				}

			}

			System.out.println();
		}
	}

	private static class ClassListingTask extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		File[] files;
		List<String> newList, filenames;
		int lo, hi;
		String keyword;

		public ClassListingTask(File[] files, List<String> newList, int lo, int hi, String keyword) {
			this.files = files;
			this.newList = newList;
			this.lo = lo;
			this.hi = hi;
			this.keyword = keyword;
		}

		public ClassListingTask(List<String> filenames, List<String> newList, int lo, int hi, String keyword) {
			this.filenames = filenames;
			this.newList = newList;
			this.lo = lo;
			this.hi = hi;
			this.keyword = keyword;
		}

		@Override
		protected void compute() {
			if (hi - lo == 1) {
				if (files != null) { // indicates this is a call from the constructor of FP
					if (!files[lo].getName().startsWith(".")) {
						ClassListingTask[] branches = new ClassListingTask[BLACKLISTED_NAMES.size()];
						List<String> testList = new ArrayList<>();
						List<String> singleFilename = Arrays.asList(files[lo].getName());
						boolean blacklisted = false;
						for (int i = 0; i < BLACKLISTED_NAMES.size() - 1; i++) {
							branches[i] = new ClassListingTask(singleFilename, testList, 0, 1, BLACKLISTED_NAMES.get(i));
							branches[i].fork();
						}
						new ClassListingTask(singleFilename, testList, 0, 1,
								BLACKLISTED_NAMES.get(BLACKLISTED_NAMES.size() - 1)).compute();
							
						if (testList.isEmpty()) { // the filename does NOT contain the last blacklisted name
							for (int i = 0; i < branches.length - 1; i++) {
								branches[i].join();
								if (!testList.isEmpty()) {
									blacklisted = true;
									break;
								}
							}
						} else {
							blacklisted = true;
						}
						if (!blacklisted) {
							newList.add(files[lo].getName());
						}
					}

				} else {
					if (filenames.get(lo).contains(keyword)) {
						newList.add(filenames.get(lo));
					}
				}
			} else if (hi - lo != 0) {
				int mid = lo + (hi - lo) / 2;

				ClassListingTask left = null;
				ClassListingTask right = null;

				if (files == null) {
					left = new ClassListingTask(filenames, newList, lo, mid, keyword);
					right = new ClassListingTask(filenames, newList, mid, hi, keyword);

				} else {
					left = new ClassListingTask(files, newList, lo, mid, keyword);
					right = new ClassListingTask(files, newList, mid, hi, keyword);
				}

				left.fork();
				right.compute();
				left.join();

			}
		}

	}

	public boolean add(String filename) {
		return this.allFilenames.add(filename);
	}

	public boolean remove(String filename) {
		return this.allFilenames.remove(filename);
	}
}
