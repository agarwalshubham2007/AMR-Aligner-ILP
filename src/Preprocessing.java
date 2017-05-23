import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Preprocessing {

	public static int leftPos = 0;
	public static int rightPos = 1;
	public ArrayList<TreeNode> amr_roots = new ArrayList<>();
	public ArrayList<DataInstance> data_instances = new ArrayList<>();
	public ArrayList<String> sents = new ArrayList<>();
	public String path, fileName;
	StanfordUtil sCore;
	public Constants constants;

	public Preprocessing() {

	}

	public Preprocessing(boolean lemma, boolean dependencies) {
		sCore = new StanfordUtil(lemma, dependencies);
		constants = new Constants();
	}

	public void makeModalILPFile(String modalConcept, ArrayList<DataInstance> dataInstances) {
		String content = "";
		content += "#display concept/2.\n";
		content += "#display token/3.\n";
		content += "#display modal/1.\n";

		content += "%% Background" + "\n";
		content += "position(I) :- token(S,I,L).\n";
		content += "sentence(S) :- token(S,I,L).\n";
		content += "lemmaList(L) :- token(S,I,L).\n";
		content += "\n";

		content += "modalConcepts(possible;likely;obligate;permit;recommend;prefer;placeholder).\n\n";

		for (int i = 40; i < dataInstances.size() && i < 50; i++) {
			content += "%" + dataInstances.get(i).sentence + "\n\n";
			System.out.println(dataInstances.get(i).sentence);

			//			String[] words = dataInstances.get(i).sentence.split(" ");
			String[] words = dataInstances.get(i).sentence.replaceAll("[^a-zA-Z0-9]", "").replaceAll("\\s+", " ")
					.toLowerCase().split(" ");

			content += "\n";

			List<String> lemmas = sCore.lemmatize(
					dataInstances.get(i).sentence.replaceAll("[^a-zA-Z]", " ").replaceAll("\\s+", " ").toLowerCase());
			//			lemmas = cleanList(lemmas);

			boolean hasModal = false;

			for (int j = 0; j < lemmas.size(); j++) {
				if (!lemmas.get(j).equals("not"))
					content += "token(" + Integer.toString(i) + "," + Integer.toString(j + 1) + "," + lemmas.get(j)
							+ ").\n";
				else
					content += "token(" + Integer.toString(i) + "," + Integer.toString(j + 1) + "," + "nt" + ").\n";

				if (Constants.Modals.contains(lemmas.get(j))) {
					content += "modal(" + lemmas.get(j) + ").\n";
					hasModal = true;
				}
			}

			content += "\n";
			content += "%% Examples" + "\n";
			if (hasModal)
				content += "#example concept(" + modalConcept + "," + i + ").\n\n";
			else
				content += "#example not concept(" + modalConcept + "," + i + ").\n\n";
		}

		content += "token(200,1,how).\n";
		content += "token(200,2,are).\n";
		content += "#example not concept(possible,200).\n\n";

		content += "%% M. Modes\n\n";
		content += "#modeh concept($modalConcepts,+sentence).\n";
		content += "#modeb token(+sentence,-position,$modal).\n";

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.ilpPath + "/" + modalConcept + ".lp"))) {

			bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}

		Abstract.learnRules(Constants.ilpPath, modalConcept + ".lp");
	}

	public void makeILPFile() {

		String content = "";
		int n = amr_roots.size();

		content += "%% Background" + "\n";
		content += "position(I) :- token(S,I,L,W).\n";
		content += "sentence(S) :- token(S,I,L,W).\n";
		content += "lemmaList(L) :- token(S,I,L,W).\n";
		content += "wordList(W) :- token(S,I,L,W).\n";
		content += "next(S,I,J) :- token(S,I,L,W), token(S1,J,L1,W1), I=J+1, S=S1.\n";
		// content += "modalToken(token(S,I,L,W)) :- modal(L).\n";
		content += "\n";

		content += "modalConcepts(";
		for (String m : Constants.ModalConceptList)
			content += m + ";";
		content = content.substring(0, content.length() - 1);
		content += ").\n\n";

		for (int i = 0; i < n; i++) {
			content += "%" + sents.get(i) + "\n\n";
			System.out.println(sents.get(i));

			ArrayList<String> concepts = new ArrayList<String>();
			getConcepts(amr_roots.get(i), concepts);
			concepts = (ArrayList<String>) cleanList(concepts);

			content += "conceptList(";
			for (String c : concepts) {
				content += c + ";";
			}
			content += "placeholder).\n\n";

			String[] words = sents.get(i).replaceAll("[^a-zA-Z0-9? ]", "").replaceAll("\\s+", " ").toLowerCase()
					.split(" ");

			content += "\n";

			List<String> lemmas = sCore.lemmatize(sents.get(i));
			lemmas = cleanList(lemmas);

			for (int j = 0; j < lemmas.size(); j++) {
				content += "token(" + Integer.toString(i) + "," + Integer.toString(j + 1) + "," + lemmas.get(j) + ","
						+ words[j] + ").\n";

				if (Constants.Modals.contains(lemmas.get(j)))
					content += "modal(" + lemmas.get(j) + ").\n";
			}

			content += "\n";

			content += "%% Examples" + "\n";

			HashSet<String> conceptsHS = new HashSet<>();

			for (String c : concepts) {
				content += "#example concept(" + c + ").\n";
				conceptsHS.add(c);
			}

			HashSet<String> negative_lemma_examples = new HashSet<>();
			for (String l : lemmas) {
				if (!conceptsHS.contains((String) l)) {
					negative_lemma_examples.add(l);
					content += "#example not concept(" + l + ").\n";
				}
			}

			content += "\n";

		}

		content += "%% M. Modes\n\n";
		content += "#modeh concept(+conceptList).\n";
		// content += "#modeh concept(+modalConcepts).\n";
		content += "#modeb token(-sentence,-position, +lemmaList, -wordList).\n";
		content += "#modeb not modal(+lemmaList).\n";
		content += "#modeb modal(+lemmaList).\n";

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.ilpPath + "/" + fileName + ".lp"))) {

			bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	// cleans the passed list : remove punctuation(",. etc), lowercase, remove
	// extra spaces, trim
	private List cleanList(List L) {
		List newList = new ArrayList<>();
		for (int i = 0; i < L.size(); i++) {
			newList.add(
					((String) L.get(i)).replaceAll("[^a-zA-Z0-9ß ]", "").replaceAll("\\s+", " ").toLowerCase().trim());
		}
		return newList;
	}

	// cleans the passed set : remove punctuation(",. etc), lowercase, remove
	// extra spaces, trim
	private Set cleanSet(Set S) {
		Iterator it = S.iterator();
		Set newSet = new HashSet<>();
		while (it.hasNext()) {
			newSet.add(
					((String) it.next()).replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+", " ").toLowerCase().trim());
		}
		return newSet;
	}

	public void getConcepts(TreeNode root, ArrayList<String> concepts) {
		if (root.word == null)
			return;
		// System.out.println("Node: " + root.word);
		concepts.add(root.word);
		// System.out.println("# Children edges:" + root.childEdge.size());
		// for (int i = 0; i < root.childEdge.size(); i++)
		// System.out.println(root.childEdge.get(i));
		// System.out.println("# Children nodes:" + root.childNode.size());
		// for (int i = 0; i < root.childNode.size(); i++)
		// System.out.println(root.childNode.get(i).word);
		//
		// System.out.println();

		for (int i = 0; i < root.childNode.size(); i++)
			getConcepts(root.childNode.get(i), concepts);
	}

	public void readAMR(String path, String fileName) {
		this.path = path;
		this.fileName = fileName;

		String amr_file = path + "/" + fileName;

		BufferedReader br = null;
		FileReader fr = null;

		try {

			fr = new FileReader(amr_file);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(amr_file));

			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.startsWith("# ::snt")) {
					String sent = sCurrentLine.split("snt")[sCurrentLine.split("snt").length - 1].substring(1);

					sents.add(sent);

					String amr = "";
					while (!(sCurrentLine = br.readLine()).equals("")) {
						System.out.println("Line reading : " + sCurrentLine);
						if (!sCurrentLine.startsWith("# ::")) {
							amr += sCurrentLine;
						}
					}

					amr = amr.replaceAll("(?m)(^ *| +(?= |$))", "").replaceAll("(?m)^$([\r\n]+?)(^$[\r\n]+?^)+", "$1");

					leftPos = 0;
					rightPos = 1;

					System.out.println("AMR : " + amr);
					TreeNode root = new TreeNode();

					// make parse tree of AMR : ParseTree.java
					System.out.println(sent);
					if (!Constants.IgnoreSent.contains(sent)) {
						ParseTree.makeParseTree(root, amr);
						amr_roots.add(root);
						data_instances.add(new DataInstance(sent, root));
						printParseTree(root);
					}
				}
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
	}

	public static void printParseTree(TreeNode root) {
		if (root.word == null)
			return;
		System.out.println("Node: " + root.word);
		System.out.println("# Children edges:" + root.childEdge.size());
		for (int i = 0; i < root.childEdge.size(); i++)
			System.out.println(root.childEdge.get(i));
		System.out.println("# Children nodes:" + root.childNode.size());
		for (int i = 0; i < root.childNode.size(); i++)
			System.out.println(root.childNode.get(i).word);

		System.out.println();

		for (int i = 0; i < root.childNode.size(); i++)
			printParseTree(root.childNode.get(i));
	}
}
