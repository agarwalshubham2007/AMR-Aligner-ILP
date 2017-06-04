import java.io.IOException;
import java.util.HashSet;

public class Rules extends Catvar {
	/*
	 * Rule: aligns to concept if concepts is exactly same as lemma token of sentence
	 * TODO: I am not converting words to lemma
	 */
	public int isApplicableRule1(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false);
		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);
		String concept = n.word;
		concept = preprocess.cleanString(concept);
		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && concept.equals(words[i]) && !sentenceWordsAlignedIndex.contains(i)) {
				sentenceWordsAlignedIndex.add(i);
				return i;
			}
		}
		return -1;
	}

	/*
	 * Rule: aligns to modal concepts(possible, recommend, obligate, etc.)
	 */
	public int isApplicableRule2(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex) {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false);
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
						&& concept.equals("possible")) {
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
		StanfordUtil su = new StanfordUtil(true, false);
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
		StanfordUtil su = new StanfordUtil(true, false);
		String concept = n.word;

		if (n.isNodeCoref())
			return -1;

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);

		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !sentenceWordsAlignedIndex.contains(i)) {
				if (getCatWords(concept).contains(words[i])) {
					sentenceWordsAlignedIndex.add(i);
					return i;
				}

			}
		}

		return -1;
	}

	public int isApplicableRule4Neg(String sentence, TreeNode n, HashSet<Integer> sentenceWordsAlignedIndex,
			int wordIndex) throws IOException, InterruptedException {
		Preprocessing preprocess = new Preprocessing();
		StanfordUtil su = new StanfordUtil(true, false);
		String concept = n.word;

		if (n.isNodeCoref())
			return -1;

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);

		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !sentenceWordsAlignedIndex.contains(wordIndex)) {
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
		StanfordUtil su = new StanfordUtil(true, false);

		String[] words = sentence.split(" ");
		preprocess.cleanArray(words);

		for (int i = 0; i < words.length; i++) {
			if (!words[i].equals("") && !sentenceWordsAlignedIndex.contains(i)) {
				if (words[i].startsWith("a")) {
					if (isApplicableRule4Neg(words[i].substring(1), n, sentenceWordsAlignedIndex, i) == 0) {
						sentenceWordsAlignedIndex.add(i);
						return i;
					}
				} else if (words[i].startsWith("im") || words[i].startsWith("in") || words[i].startsWith("un")) {
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

		return -1;
	}
}
