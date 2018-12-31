package internals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

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

	private static class SubWordTask extends RecursiveTask<Boolean> {

		int lo, hi;
		String targ;

		public SubWordTask(String targ, int lo, int hi) {
			this.targ = targ;
			this.lo = lo;
			this.hi = hi;
		}

		/**
		 * @return false if target contains blacklisted word, else returns true
		 */
		@Override
		protected Boolean compute() {
			if (hi - lo == 1) {
				return targ.contains(BLACKLISTED_NAMES.get(lo)) ? false : true;
			} else if (hi - lo != 0) {
				int mid = lo + (hi - lo) / 2;

				SubWordTask left = new SubWordTask(targ, lo, mid);
				SubWordTask right = new SubWordTask(targ, mid, hi);

				left.fork();
				boolean rightResult = right.join();
				boolean leftResult = left.compute();

				return leftResult && rightResult;
			}
			return true;

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
				if (files != null) { // constructor pass through FJTask
					if (POOL.invoke(new SubWordTask(files[lo].getName(), 0, BLACKLISTED_NAMES.size()))) {
						newList.add(files[lo].getName());
					}
				} else {
					assert (POOL.invoke(new SubWordTask(filenames.get(lo), 0, BLACKLISTED_NAMES.size())));
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
