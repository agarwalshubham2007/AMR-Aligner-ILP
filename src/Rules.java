import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Rules extends Catvar {
	/*
	 * Rule: aligns to concept if concepts is exactly same as lemma token of sentence
	 */
	public int isApplicableRule1(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		if (n.isNodeCoref())
			return -1;
		if (n.word.equals("\"Richmond\"")) {
			System.out.println("break here");
		}
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		List<String> words = su.lemmatize(sentence.toLowerCase());
		words = preprocess.cleanList(words);
		//		String[] words = sentence.split(" ");
		//		preprocess.cleanArray(words);
		String concept = n.word;
		concept = preprocess.cleanString(concept);

		for (int i = 0; i < words.size(); i++) {
			if (!words.get(i).equals("") && concept.equals(words.get(i)) && !sentenceWordsAlignedIndex.contains(i)) {
				sentenceWordsAlignedIndex.add(i);
				return i;
			}
			if (concept.length() > 0) {
				if ((concept.equals("virginia") && words.get(i).equals("virginium"))
						|| (concept.equals("watkins") && words.get(i).equals("watkin"))
						|| (concept.equals("dulles") && words.get(i).equals("dulle"))
						|| (concept.equals("economy") && words.get(i).equals("economic"))
						|| (concept.charAt(concept.length() - 1) == 's'
								&& words.get(i).equals(concept.substring(0, concept.length() - 1)))) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}
			}
		}
		return -1;
	}

	/*
	 * Rule: aligns to modal concepts(possible, recommend, obligate, etc.)
	 */
	public int isApplicableRule2(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String concept = n.word;
		concept = preprocess.cleanString(su.lemmatize(concept).get(0));

		// base case : if the concept is not a modal concept, return -1
		if (!Constants.containsStringElement(Constants.ModalConceptList, concept)) {
			return -1;
		}

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);
		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !sentenceWordsAlignedIndex.contains(i)) {
				if ((words[i].equals("can") || words[i].equals("ca") || words[i].equals("can")
						|| words[i].equals("cannot") || words[i].equals("could") || words[i].equals("might"))
						&& (concept.equals("possible") || concept.equals("recommend"))) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if ((words[i].equals("must") || words[i].equals("shall")) && concept.equals("obligate")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if (words[i].equals("should") && (concept.equals("recommend") || concept.equals("likely"))) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if (words[i].equals("may") && (concept.equals("possible") || concept.equals("obligate"))) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if (words[i].equals("will")
						&& (concept.equals("possible") || concept.equals("obligate") || concept.equals("recommend"))) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if (words[i].equals("would") && (concept.equals("possible") || concept.equals("prefer"))) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}
			}
		}
		return -1;
	}

	/*
	 * Rule: aligns to polarity - concept
	 */
	public int isApplicableRule3(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String concept = n.word;

		// base case : if the concept is not a negation concept, return -1
		if (!concept.equals("-")) {
			return -1;
		}

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);
		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !sentenceWordsAlignedIndex.contains(i)) {
				if (words[i].equals("no") || words[i].equals("not") || words[i].equals("nt")
						|| words[i].equals("n't")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}
			}
		}
		return -1;
	}

	/*
	 * Rule: align concept that matches catvar of a word 
	 * TODO: currently i am only finding catwords of the concept and matching with words in sentence. 
	 * I should do the reverse too but finding the catwords for words like not, of will increase time complexity 
	 * a lot and i am not concerned with it right now
	 */
	public int isApplicableRule4(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex)
			throws IOException, InterruptedException {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String concept = n.word;

		if (n.isNodeCoref())
			return -1;

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);

		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !concept.equals("i") && !sentenceWordsAlignedIndex.contains(i)) {
				if (getCatWords(concept).contains(words[i]) && !words[i].startsWith(concept)) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}

				for (String s : getCatWords(concept)) {
					if (words[i].startsWith(s) && (words[i].length() - s.length()) <= 1) {
						sentenceWordsAlignedIndex.add(i);
						return i;
					}
				}

			}
		}

		return -1;
	}

	public int isApplicableRule4Neg(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex,
			int wordIndex) throws IOException, InterruptedException {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String concept = n.word;

		if (n.isNodeCoref())
			return -1;

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);

		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !concept.equals("i") && !sentenceWordsAlignedIndex.contains(wordIndex)) {
				if (getCatWords(concept).contains(words[i])) {
					sentenceWordsAlignedIndex.add(wordIndex);
					return i;
				}

			}
		}

		return -1;
	}

	/*
	 * Rule: align to concepts that start with negative prefix and suffix like a-, im-, in-, non-, -less etc.
	 */
	public int isApplicableRule5(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex)
			throws IOException, InterruptedException {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);

		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !sentenceWordsAlignedIndex.contains(i)) {
				if (words[i].startsWith("a")) {
					if (isApplicableRule4Neg(words[i].substring(1), n, sentenceWordsAlignedIndex, i) == 0) {
						sentenceWordsAlignedIndex.add(i);
						return i;
					}
				} else if (words[i].startsWith("im") || words[i].startsWith("ir") || words[i].startsWith("in")
						|| words[i].startsWith("un")) {
					if (isApplicableRule4Neg(words[i].substring(2), n, sentenceWordsAlignedIndex, i) == 0) {
						sentenceWordsAlignedIndex.add(i);
						return i;
					}
				} else if (words[i].startsWith("dis")) {
					if (isApplicableRule4Neg(words[i].substring(3), n, sentenceWordsAlignedIndex, i) == 0) {
						sentenceWordsAlignedIndex.add(i);
						return i;
					}
				} else if (words[i].startsWith("non-")) {
					if (isApplicableRule4Neg(words[i].substring(4), n, sentenceWordsAlignedIndex, i) == 0) {
						sentenceWordsAlignedIndex.add(i);
						return i;
					}
				} else if (words[i].startsWith("non")) {
					if (isApplicableRule4Neg(words[i].substring(3), n, sentenceWordsAlignedIndex, i) == 0) {
						sentenceWordsAlignedIndex.add(i);
						return i;
					}
				}
			}
		}

		return -1;
	}

	/*
	 * Rule: aligns amr-unknown concept to wh- question words. Needs to have a question mark in the sentence
	 */
	public int isApplicableRule6(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);
		List<String> pos = su.getPOS(sentence);
		String concept = n.word.trim();

		if (!concept.equals("amr")) {
			return -1;
		}

		if (sentence.contains("?")) {
			for (int i = 0; i < pos.size(); i++) {
				if (words[i].equals("which") && pos.get(i).equals("WDT")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if ((words[i].equals("what") || words[i].equals("who") || words[i].equals("whom"))
						&& pos.get(i).equals("WP")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if (words[i].equals("whose") && pos.get(i).equals("WP$")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				} else if ((words[i].equals("how") || words[i].equals("where") || words[i].equals("when")
						|| words[i].equals("why")) && pos.get(i).equals("WRB")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}
			}
		}

		return -1;
	}

	/*
	 * Rule: align concept interrogative if there is a question mark in the sentence.
	 */
	public int isApplicableRule7(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String concept = n.word;
		concept = preprocess.cleanString(concept);
		if (!concept.equals("interrogative"))
			return -1;

		String[] words = sentence.split(" ");
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals("?")) {
				sentenceWordsAlignedIndex.add(i);
				return i;
			}
		}

		return -1;
	}

	/*
	 * Rule: concept month in date entity
	 */
	public String isApplicableRule8(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String[] words = sentence.split(" ");
		//		preprocess.cleanArray(words);
		if (!n.word.trim().equals("date"))
			return "-1";
		for (int i = 0; i < n.childEdge.size(); i++) {
			if (n.childEdge.get(i).equals("month")) {
				String month = n.childNode.get(i).word;
				if (StringUtils.isNumeric(month)) {
					month = StringUtil.numToMonth(month);
				}
				for (int j = 0; j < words.length; j++) {
					String w = words[j];
					if (month.equalsIgnoreCase(w) || (w.length() >= 4 && w.charAt(3) == '.'
							&& month.startsWith(w.substring(0, 3).toLowerCase()))) {
						sentenceWordsAlignedIndex.add(j);
						return n.childNode.get(i).position + " " + j;
					}
				}
			}
		}
		return "-1";
	}

	/*
	 * Rule: align dollar concept to $
	 */
	public int isApplicableRule9(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String[] words = sentence.split(" ");
		if (!n.word.trim().equals("dollar"))
			return -1;

		for (int i = 0; i < words.length; i++) {
			if (words[i].equals("$")) {
				sentenceWordsAlignedIndex.add(i);
				return i;
			}
		}

		return -1;
	}

	/*
	 * Rule: align abstract concept person ,thing
	 */
	public int isApplicableRule10(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex,
			HashMap<String, Integer> alignments) {
		Preprocessing preprocess = new Preprocessing();
		String concept = n.word;
		concept = preprocess.cleanString(concept);
		if (!concept.equals("person") && !concept.equals("thing")) {
			return -1;
		}

		if (n.childNode.size() > 0 && alignments.containsKey(n.childNode.get(0).position)) {
			sentenceWordsAlignedIndex.add(alignments.get(n.childNode.get(0).position));
			return alignments.get(n.childNode.get(0).position);
		}
		return -1;
	}

	/*
	 * Rule: align coreferences using stanford
	 */
	public int isApplicableRule11(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		if (n.isNodeCoref()) {
			return 0;
		}
		return -1;
	}

	/*
	 * Rule: align number as words in sentence with concept :quant <number>
	 */
	public int isApplicableRule12(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		String num;
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String[] words = sentence.split(" ");
		if ((num = StringUtil.isNumLessThanTen(n.word)) != null) {
			for (int i = 0; i < words.length; i++) {
				if (num.equals(words[i])) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}
			}
		}
		return -1;
	}

	/*
	 * Rule: align causal
	 */
	public int isApplicableRule13(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);
		String concept = n.word;
		concept = preprocess.cleanString(concept);
		if (!concept.equals("cause"))
			return -1;
		for (int i = 0; i < words.length; i++) {
			if (!sentenceWordsAlignedIndex.contains(i)) {
				if (words[i].equals("because")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}
				if (words[i].equals("since")) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}
			}
		}
		return -1;
	}

	/*
	 * Rule: align 'million' concept
	 */
	public int isApplicableRule14(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		if (sentence.equals("I think we could have spent the $ 400 million better elsewhere .")) {
			System.out.println("break here");
		}
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false, false);
		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);
		String concept = n.word;
		if (StringUtil.isNumber(n.word)) {
			for (int i = 0; i < words.length - 1; i++) {
				if (words[i + 1].equals("million") && concept.length() > 6
						&& words[i].equals(concept.substring(0, words[i].length()))) {
					sentenceWordsAlignedIndex.add(i + 1);
					return i + 1;
				}
				if (words[i + 1].equals("billion") && concept.length() > 12
						&& words[i].equals(concept.substring(0, words[i].length()))) {
					sentenceWordsAlignedIndex.add(i + 1);
					return i + 1;
				}
			}
		}
		return -1;
	}

}
