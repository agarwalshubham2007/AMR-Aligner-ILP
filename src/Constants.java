import java.util.HashSet;

public class Constants {

	public static HashSet<String> IgnoreSent = new HashSet<>();
	public static String IgnoreSent1 = "I headed straight for the center of activities, but in actual fact traffic was being controlled as early as 4 o'clock, and they had already started limiting the crowds entering the sports center.";
	public static String IgnoreSent2 = "I guessed the carrying capacity of the base station towers was totally overloaded, and I couldn't get through at all. I was only able to find a signal, not a chance of connecting to the Internet.";
	public static String IgnoreSent3 = "But the first, perhaps facetious, response I had to \"what would happen\"-question was to recall the 80's movies: Mad Max, and Mad Mad: Road Warrior";
	public static String trainingDataPath = "/Users/Shubham/Documents/workspace/ILP/amr_anno_1.0/data/split/training";
	public static String serializedPath = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects";
	public static String ilpPath = "/Users/Shubham/Documents/workspace/ILP/trainingILP";
	public static String learntRulesPath = "/Users/Shubham/Documents/workspace/ILP/learntRules";
	public static String ontoNotesFramesFolderPath = "/Users/Shubham/Documents/workspace/ILP/OntoNotes_Frames";
	public static String catVarPath = "perl /Users/Shubham/Downloads/catvar21/CVsearch.pl";
	public static String catVarClustersPath = "/Users/Shubham/Documents/workspace/ILP/catvarClusters";

	public static HashSet<String> Modals = new HashSet<>();
	// public static HashSet<String> ModalConcepts;
	public static String[] ModalsList = { "can", "cannot", "can't", "could", "couldn't", "may", "might", "must",
			"shall", "should", "shouldn't", "will", "would", "wouldn't", "won't" };
	public static String[] ModalConceptList = { "possible", "likely", "obligate", "permit", "recommend", "prefer" };
	public static String[] NegationLemmaList = { "not", "no" };

	public Constants() {
		IgnoreSent.add(IgnoreSent1);
		IgnoreSent.add(IgnoreSent2);
		IgnoreSent.add(IgnoreSent3);

		for (String s : Constants.ModalsList)
			Modals.add(s);

		// ModalConcepts = new HashSet<>();
		// for (String s : Constants.ModalConceptList)
		// ModalConcepts.add(s);

	}
}
