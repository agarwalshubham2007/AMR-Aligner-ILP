import java.util.ArrayList;

public class RuleLearnDataset extends Preprocessing {

	/*
	 * generating a sub-dataset for learning modal concepts like "possible", "likely", "obligate", "permit", "recommend", "prefer" 
	 */
	public ArrayList<DataInstance> generateModalConceptDataset(String modalConcept,
			ArrayList<DataInstance> dataInstances) {
		ArrayList<DataInstance> modalConceptDataset = new ArrayList<>();
		for (DataInstance d : dataInstances) {
			ArrayList<String> concepts = new ArrayList<>();
			getConcepts(d.root, concepts);
			if (concepts.contains(modalConcept)) {
				modalConceptDataset.add(d);
			}
		}
		return modalConceptDataset;
	}
}
