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

	public HashMap<String, Integer> align(DataInstance d) {
		HashMap<String, Integer> alignments = new HashMap<>();
		HashSet<Integer> sentenceWordsAlignedIndex = new HashSet<>();
		ArrayList<TreeNode> nodes = d.getNodeCollection();
		for (TreeNode n : nodes) {
			int wordIndex;
			if (!alignments.containsKey(n.position)) {
				if ((wordIndex = isApplicableRule1(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule2(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule3(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				} else if ((wordIndex = isApplicableRule4(d.sentence, n, sentenceWordsAlignedIndex)) >= 0) {
					alignments.put(n.position, wordIndex);
				}
			}
		}
		return alignments;
	}
}
