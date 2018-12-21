
public class Test {
	public static void main(String[] args) {
		System.out.println("\033[0;31m\033[1;40m" + "i can't see shit" + "\033[0m");
		String line1 = "o=wee";
		String line2 = "[oce]=wee";
		System.out.println(line1.startsWith("[oce]="));
		System.out.println(line2.startsWith("[oce]="));
	}
}
