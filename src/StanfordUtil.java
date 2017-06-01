import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class StanfordUtil {
	protected StanfordCoreNLP pipeline;

	public StanfordUtil(boolean lemma, boolean dependencies) {
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

}
