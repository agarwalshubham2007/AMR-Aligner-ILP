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
}
