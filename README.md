# AMR-Aligner-ILP
This readme gives implementation details of the ILP based aligner, starting from raw data processing to aligning. From implementation point of view, the system consists of four major modules - *Data Preprocessing, Data Encoding, Rule Learning and Aligning*. Each of these modules is discussed in the following sections.

The tools used in the development process are the following:  
1. [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/), for background knowledge
2. [Catvar 2.0](https://clipdemos.umiacs.umd.edu/catvar/), for categorial variation clusters
3. [XHAIL](https://github.com/stefano-bragaglia/XHAIL), for Inductive Logic Programming

## Data Preprocessing
The raw data consists of English sentences and AMR representation. Preprocessing steps on this raw data are as follows:
  - The English sentence is first converted into lowercase.
  - Using Stanford CoreNLP, background knowledge mentioned in Chapter 2 is extracted from the sentence.
  - The AMR representation is stored in memory in a graph data structure using a recursive algorithm. The algorithm is Shown [here](https://github.com/agarwalshubham2007/AMR-Aligner-ILP/tree/master/images/AlgoAMRGraph.png)
