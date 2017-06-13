import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class StanfordUtil {
	protected StanfordCoreNLP pipeline;

	public StanfordUtil(boolean lemma, boolean dependencies, boolean coref) {
		// Create StanfordCoreNLP object properties, with POS tagging
		// (required for lemmatization), and lemmatization
		Properties props;
		props = new Properties();
		if (lemma) {
			props.put("annotators", "tokenize, ssplit, pos, lemma");

			// StanfordCoreNLP loads a lot of models, so you probably
			// only want to do this once per execution
			this.pipeline = new StanfordCoreNLP(props);
		}

		if (dependencies) {
			props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse");
			this.pipeline = new StanfordCoreNLP(props);
		}

		if (coref) {
			props.put("annotators", "tokenize,ssplit,pos,lemma,ner,parse,dcoref");
			this.pipeline = new StanfordCoreNLP(props);
		}

	}

	public List<String> lemmatize(String documentText) {
		List<String> lemmas = new LinkedList<String>();

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(documentText);

		// run all Annotators on this text
		this.pipeline.annotate(document);

		// Iterate over all of the sentences found
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// Iterate over all tokens in a sentence
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// Retrieve and add the lemma for each word into the list of
				// lemmas
				lemmas.add(token.get(LemmaAnnotation.class));
			}
		}

		return lemmas;
	}

	public SemanticGraph getDependencies(String sent) {
		Annotation document = new Annotation(sent);
		pipeline.annotate(document);
		CoreMap sentence = document.get(CoreAnnotations.SentencesAnnotation.class).get(0);
		SemanticGraph dependencyParse = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
		System.out.println(dependencyParse.toList());

		return dependencyParse;
	}

	public List<String> getPOS(String sent) {
		List<String> pos = new LinkedList<String>();

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(sent);

		// run all Annotators on this text
		this.pipeline.annotate(document);

		// Iterate over all of the sentences found
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// Iterate over all tokens in a sentence
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// Retrieve and add the lemma for each word into the list of
				// lemmas
				pos.add(token.get(PartOfSpeechAnnotation.class));
			}
		}

		return pos;
	}

	/**
	 * extracts co-references and put them in {@link #corefMap}
	 * 
	 * @param sentence
	 */
	public HashMap<String, String> extractCoreferenceChain(String sentence) {
		HashMap<String, String> corefMap = new HashMap<String, String>();

		Annotation document = new Annotation(sentence);

		pipeline.annotate(document);
		Map<Integer, CorefChain> coref = document.get(CorefChainAnnotation.class);

		for (Map.Entry<Integer, CorefChain> entry : coref.entrySet()) {
			CorefChain c = entry.getValue();

			CorefMention cm = c.getRepresentativeMention();
			String clust = "";
			List<CoreLabel> tks = document.get(SentencesAnnotation.class).get(cm.sentNum - 1)
					.get(TokensAnnotation.class);
			for (int i = cm.startIndex - 1; i < cm.endIndex - 1; i++)
				clust += tks.get(i).get(TextAnnotation.class) + " ";
			clust = clust.trim();
			//			System.out.println("representative mention: \"" + clust + "\" is mentioned by:");

			for (CorefMention m : c.getMentionsInTextualOrder()) {
				String clust2 = "";
				tks = document.get(SentencesAnnotation.class).get(m.sentNum - 1).get(TokensAnnotation.class);
				for (int i = m.startIndex - 1; i < m.endIndex - 1; i++)
					clust2 += tks.get(i).get(TextAnnotation.class) + " ";
				clust2 = clust2.trim();
				//don't need the self mention
				if (clust.equals(clust2))
					continue;

				//				System.out.println("\t" + clust2);
				corefMap.put(clust2.toLowerCase() + "_" + m.startIndex, getModifiedRepresentativeMention(clust, cm));
			}
		}

		return corefMap;
	}

	private String getModifiedRepresentativeMention(String clust, CorefMention representativeMention) {
		StringBuffer strB = new StringBuffer();
		strB.append(":");
		int modifiedStartIndex = representativeMention.startIndex;

		String[] tokens = clust.toLowerCase().split("\\s+");
		int i = 0;
		for (; i < tokens.length; i++) {
			if (tokens[i].equals("a") || tokens[i].equals("an") || tokens[i].equals("the"))
				modifiedStartIndex++;
			else
				break;
		}
		for (; i < tokens.length; i++) {
			strB.append(tokens[i]);
			if (i != tokens.length - 1)
				strB.append("_");
		}
		strB.append("_").append(modifiedStartIndex);
		return strB.toString();
	}

}
