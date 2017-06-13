import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Align extends Rules {
	/*
	 * assigns positions to every node of graph through breadth first traversal(non-recursive, Queue)
	 */
	public void assignPositionsToGraph(TreeNode root) {
		root.position = "1";

		Queue<TreeNode> qu = new LinkedList<>();
		qu.offer(root);

		while (!qu.isEmpty()) {
			TreeNode current = qu.poll();
			String currentPrefixPosition = current.position;
			for (int i = 0; i < current.childEdge.size(); i++) {
				current.childNode.get(i).position = currentPrefixPosition + "." + Integer.toString(i + 1);
				qu.offer(current.childNode.get(i));
			}
		}
	}

	public HashMap<String, Integer> align(DataInstance d) throws IOException, InterruptedException {
		HashMap<String, Integer> alignments = new HashMap<>();
		HashSet<Integer> sentenceWordsAlignedIndex = new HashSet<>();
		ArrayList<TreeNode> nodes = d.getNodeCollection();
		for (TreeNode n : nodes) {
			int wordIndex;
			String res;
			if (!alignments.containsKey(n.position)) {
				if ((wordIndex = isApplicableRule1(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule5(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
					System.out.println("aligned :" + n.word);
					alignments.put(n.position + ".1", wordIndex);
				} else if ((wordIndex = isApplicableRule2(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule3(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule4(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule6(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule7(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if (!(res = isApplicableRule8(d.sentence, n, sentenceWordsAlignedIndex)).equals("-1")) {
					alignments.put(res.split(" ")[0], Integer.parseInt(res.split(" ")[1]));
				} else if ((wordIndex = isApplicableRule9(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule10(d.sentence, n, sentenceWordsAlignedIndex,
						alignments)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule12(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule13(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule14(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				}
			}
		}

		for (TreeNode n : nodes) {
			int wordIndex;
			if (!alignments.containsKey(n.position)) {
				if ((wordIndex = isApplicableRule10(d.sentence, n, sentenceWordsAlignedIndex, alignments)) >= 0) {
					alignments.put(n.position, wordIndex);
				}
			}
		}
		return alignments;
	}

}
