import java.io.Serializable;

public class DataInstance implements Serializable {
	public String sentence;
	public TreeNode root;

	public DataInstance(String sentence, TreeNode root) {
		super();
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

}
