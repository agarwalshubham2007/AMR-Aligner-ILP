import edu.stanford.nlp.util.StringUtils;

public class StringUtil {
	public static boolean isNumber(String s) {
		if (StringUtils.isNumeric(s))
			return true;
		else
			return false;
	}

	public static String cleanString(String word) {
		return word.replaceAll("[^a-zA-Z0-9- ]", "").replaceAll("\\s+", " ").toLowerCase().trim();
	}

	public static String numToMonth(String month) {
		switch (month) {
		case "1":
			return "january";
		case "2":
			return "february";
		case "3":
			return "march";
		case "4":
			return "april";
		case "5":
			return "may";
		case "6":
			return "june";
		case "7":
			return "july";
		case "8":
			return "august";
		case "9":
			return "september";
		case "10":
			return "october";
		case "11":
			return "november";
		case "12":
			return "december";
		default:
			return "";
		}
	}

	public static String isNumLessThanTen(String num) {
		if (num.equals("1"))
			return "one";
		else if (num.equals("2"))
			return "two";
		else if (num.equals("3"))
			return "three";
		else if (num.equals("4"))
			return "four";
		else if (num.equals("5"))
			return "five";
		else if (num.equals("6"))
			return "six";
		else if (num.equals("7"))
			return "seven";
		else if (num.equals("8"))
			return "eight";
		else if (num.equals("9"))
			return "nine";
		else if (num.equals("10"))
			return "ten";
		return null;
	}
}
