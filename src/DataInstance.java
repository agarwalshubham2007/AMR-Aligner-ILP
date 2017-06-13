import java.io.Serializable;
import java.util.ArrayList;

public class DataInstance implements Serializable {
	public String id;
	public String sentence;
	public TreeNode root;

	public DataInstance(String id, String sentence, TreeNode root) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.root = root;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public ArrayList<TreeNode> getNodeCollection() {
		ArrayList<TreeNode> nodes = new ArrayList<>();
		nodeCollection(this.root, nodes);
		return nodes;
	}

	private void nodeCollection(TreeNode root2, ArrayList<TreeNode> nodes) {
		if (root2.word == null)
			return;
		nodes.add(root2);

		for (int i = 0; i < root2.childNode.size(); i++)
			nodeCollection(root2.childNode.get(i), nodes);
	}

}
