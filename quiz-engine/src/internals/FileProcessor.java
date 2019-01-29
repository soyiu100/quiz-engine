package internals;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Strictly processes any specific to files and filenames.
 * 
 * @author Isaac Pang
 *
 */
public class FileProcessor {

	public static final List<String> BLACKLISTED_NAMES = Arrays.asList("src", "README", "changelog", "bin", "jsoup",
			"pom", "target");

	private final static ForkJoinPool POOL = new ForkJoinPool();

	private Set<String> allFilenames;

	public FileProcessor() {
		allFilenames = new HashSet<String>();
		initAllFiles();
	}

	private void initAllFiles() {
		File[] files = new File(".").listFiles();
		POOL.invoke(new ClassListingTask(files, allFilenames, 0, files.length, ""));
		// TODO
		// inefficent second pass, but surefire way to clog null or "disappearing" classes for small class sizes FOR NOW
		POOL.invoke(new ClassListingTask(files, allFilenames, 0, files.length, ""));
	}

	public List<String> getAllClasses() {
		return new ArrayList<String>(allFilenames);
	}

	public static class ClassListingTask extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		File[] files;
		Set<String> newList;
		List<String> filenames;
		int lo, hi;
		String keyword;

		public ClassListingTask(File[] files, Set<String> newList, int lo, int hi, String keyword) {
			this.files = files;
			this.newList = newList;
			this.lo = lo;
			this.hi = hi;
			this.keyword = keyword;
		}

		public ClassListingTask(List<String> filenames, Set<String> newList, int lo, int hi, String keyword) {
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

						RecursiveAction[] branches = new RecursiveAction[BLACKLISTED_NAMES.size() - 1];
						Set<String> testList = new HashSet<>();
						List<String> singleFilename = Arrays.asList(files[lo].getName());
						boolean blacklisted = false;
						for (int i = 0; i < BLACKLISTED_NAMES.size() - 1; i++) {
							branches[i] = new ClassListingTask(singleFilename, testList, 0, 1,
									BLACKLISTED_NAMES.get(i));
							branches[i].fork();
						}
						new ClassListingTask(singleFilename, testList, 0, 1,
								BLACKLISTED_NAMES.get(BLACKLISTED_NAMES.size() - 1)).compute();

						for (int i = 0; i < BLACKLISTED_NAMES.size(); i++) {
							if (!testList.isEmpty()) { // the filename does NOT contain the blacklisted name
								blacklisted = true;
								break;
							}
							
							if (i != BLACKLISTED_NAMES.size() - 1) {
								branches[i].join();
							}
						}

						if (!blacklisted && files[lo] != null) {
							newList.add(files[lo].getName());
						}
					}

				} else {
					if (filenames.get(lo).contains(keyword) && filenames.get(lo) != null) {
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

	public boolean addClass(String filename) {
		return this.allFilenames.add(filename);
	}

	public boolean removeClass(String filename) {
		return this.allFilenames.remove(filename);
	}

	public int numClasses() {
		return this.allFilenames.size();
	}
}
