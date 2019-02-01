package internals;

import java.io.File;
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
	private String[] files;
	private MutInt numFiles;
	private MutInt[] indexRecorder;
	
	public FileProcessor() {
		allFilenames = new HashSet<String>();
		files = new File(".").list();
		numFiles = new MutInt();
		initAllFiles();
	}

	private void initAllFiles() {
		indexRecorder = new MutInt[files.length];
		POOL.invoke(new WhiteOutTask(files, indexRecorder, 0, files.length, "", false, numFiles, true));
		System.out.println(numFiles.val);
	}

	// TODO
	public List<String> getAllClasses() {
		return Arrays.asList(files);
	}
	
	public List<MutInt> getIndexes() {
		return Arrays.asList(indexRecorder);
	}

	public static class WhiteOutTask extends RecursiveAction {

		private static final long serialVersionUID = 1L;

		String[] files;
		MutInt[] indexRecorder;
		int lo, hi;
		MutInt vwCount;
		boolean initRun, purgeKey;
		String keyword;

		/**
		 * 
		 * @param files
		 * @param lo
		 * @param hi
		 * @param keyword
		 * @param purgeKey
		 *            if true, any word with the specified keyword will be whited out;
		 *            if false, any word that doesn't have the specified keyword will be
		 *            whited
		 * @param vwCount
		 *            valid word count; necessary for 2nd pass to pack
		 * @param initRun
		 *            if true, uses BLACKLISTED_NAMES to purge any names that have the
		 *            keyword; if false, doesn't do anything special
		 */
		public WhiteOutTask(String[] files, MutInt[] indRec, int lo, int hi, String keyword, boolean purgeKey, MutInt vwCount,
				boolean initRun) {
			this.files = files;
			this.indexRecorder = indRec;
			this.lo = lo;
			this.hi = hi;
			this.keyword = keyword;
			this.purgeKey = purgeKey;
			this.vwCount = vwCount;
			this.initRun = initRun;
		}

		@Override
		protected void compute() {
			if (hi - lo == 1) {
				if (!files[lo].startsWith(".")) {
					if (initRun) {
						WhiteOutTask[] branches = new WhiteOutTask[BLACKLISTED_NAMES.size()];
						for (int i = 0; i < BLACKLISTED_NAMES.size() - 1; i++) {
							// initRun should be false, or else infinite loop
							branches[i] = new WhiteOutTask(files, indexRecorder, lo, lo + 1, BLACKLISTED_NAMES.get(i), true, vwCount,
									false);
							branches[i].fork();
						}
						new WhiteOutTask(files, indexRecorder, lo, lo + 1, BLACKLISTED_NAMES.get(BLACKLISTED_NAMES.size() - 1), true,
								vwCount, false).compute();

						for (int i = 0; i < BLACKLISTED_NAMES.size(); i++) {
							if (i != BLACKLISTED_NAMES.size() - 1) {
								branches[i].join();
							}
						}
						
						if (!files[lo].equals("")) {
							indexRecorder[lo] = new MutInt(vwCount.val);
							vwCount.inc();
						}
					} else {
						if (purgeKey) {
							if (files[lo].contains(keyword)) {
								files[lo] = "";
								indexRecorder[lo] = new MutInt(-1);

							}
						}
					}

				} else {
					files[lo] = "";
					indexRecorder[lo] = new MutInt(-1);
				}

			} else if (hi - lo != 0) {
				int mid = lo + (hi - lo) / 2;

				WhiteOutTask left = null;
				WhiteOutTask right = null;

				left = new WhiteOutTask(files, indexRecorder, lo, mid, keyword, false, vwCount, initRun);
				right = new WhiteOutTask(files, indexRecorder, mid, hi, keyword, false, vwCount, initRun);

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
		return numFiles.val;
	}

	public static class MutInt {
		public int val;
		
		public MutInt() {
			this.val = 0;
		}
		
		public MutInt(int val) {
			this.val = val;
		}
		
		MutInt inc() {
			val++;
			return new MutInt(val);
		}
		
		 MutInt dec() {
			val--;
			return new MutInt(val);
		}
		
		MutInt add(MutInt other) {
			val += other.val;
			return new MutInt(val);
		}
	}
}
