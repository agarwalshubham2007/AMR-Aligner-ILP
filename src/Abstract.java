
/**
 * 
 */

import java.nio.file.Paths;
import java.util.Collection;

import xhail.core.Config;
import xhail.core.Dialler;
import xhail.core.Utils;
import xhail.core.entities.Grounding;
import xhail.core.entities.Hypothesis;
import xhail.core.entities.Problem;
import xhail.core.terms.Clause;

/**
 * @author stefano
 *
 */
public class Abstract {

	/**
	 * @param args
	 */
	public static void learnRules(String fpath, String fname) {
		Config config = new Config.Builder() //
				.setAll(true).setBlind(true).setDebug(true).setFull(true).setKill("5").setMute(false) //
				.setGringo("/Users/Shubham/Downloads/gringo-3.0.5-macos-10.8.3/gringo").setClasp("/usr/local/bin/clasp")
				.build();

		//		Problem problem = new Problem.Builder(config).parse(Paths.get("/Users/shubham/Downloads/test.lp")).build();
		Problem problem = new Problem.Builder(config).parse(Paths.get(fpath + "/" + fname)).build();
		// Problem problem = new Problem.Builder(config)
		// .parse(Paths.get("/Users/Shubham/Documents/workspace/ILP/trainingILP/debugData.lp")).build();

		Utils.dump(problem, System.err);

		Dialler dialer = new Dialler.Builder(config, problem).build();

		// TODO : I passed 1 to execute method. Originally it was empty
		for (Collection<String> answer : dialer.execute(500).getValue()) {
			Grounding grounding = new Grounding.Builder(problem).parse(answer).build();

			System.out.println(grounding.getKernel().length);

			for (Clause clause : grounding.getKernel())
				System.out.println(clause);

			System.out.println(grounding.getGeneralisation().length);

			for (Clause clause : grounding.getGeneralisation())
				System.out.println(clause);

			dialer = new Dialler.Builder(config, grounding).build();

			for (Collection<String> result : dialer.execute(5000).getValue()) {
				// Hypothesis hypothesis = new
				// Hypothesis.Builder(grounding).addAtoms(Parser.parseAnswer(result)).build();
				Hypothesis hypothesis = new Hypothesis.Builder(grounding).parse(result).build();
				System.out.println(hypothesis.getHypotheses().length);

				for (Clause clause : hypothesis.getHypotheses())
					System.out.println(clause);

			}

		}

		// Answers answers = problem.solve();
		// System.out.println(answers);
	}

}
