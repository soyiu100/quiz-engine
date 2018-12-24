package internals;

import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FileProcessor {

	private static final ForkJoinPool POOL = new ForkJoinPool();

	static File[] allFiles = new File(".").listFiles();

	public static void getAllClasses(List<String> newList) {
		getAllClasses(newList, "");
	}

	public static void getAllClasses(List<String> newList, String keyword) {
		if (!InputParser.BLACKLISTED_NAMES.contains(keyword)) {
			POOL.invoke(new ClassListingTask(allFiles, newList, 0, allFiles.length, keyword));
			System.out.print("== Here are all of the courses currently created");
			if (keyword.length() == 0) {
				System.out.println(" ==");
			} else {
				System.out.println(", filtered by the keyword \"" + keyword + "\" ==");
			}
			for (String filename : newList) {
				System.out.println(filename);
			}
			System.out.println();
		}
	}

	private static class ClassListingTask extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		File[] files;
		List<String> newList;
		int lo, hi;
		String keyword;

		public ClassListingTask(File[] files, List<String> newList, int lo, int hi, String keyword) {
			this.files = files;
			this.newList = newList;
			this.lo = lo;
			this.hi = hi;
			this.keyword = keyword;
		}

		@Override
		protected void compute() {
			if (hi - lo == 1) {
				if (!InputParser.BLACKLISTED_NAMES.contains(files[lo].getName())
						&& !files[lo].getName().startsWith(".")) {
					if (files[lo].getName().contains(keyword)) {
						newList.add(files[lo].getName());

					}
				}
			} else if (hi - lo != 0) {
				int mid = lo + (hi - lo) / 2;

				ClassListingTask left = new ClassListingTask(files, newList, lo, mid, keyword);
				ClassListingTask right = new ClassListingTask(files, newList, mid, hi, keyword);

				left.fork();
				right.compute();
				left.join();

			}
		}

	}
}
