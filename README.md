# AMR-Aligner-ILP
This readme gives implementation details of the ILP based aligner, starting from raw data processing to aligning. From implementation point of view, the system consists of four major modules - *Data Preprocessing, Data Encoding, Rule Learning and Aligning*. Each of these modules is discussed in the following sections.

The tools used in the development process are the following:  
1. [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/), for background knowledge
2. [Catvar 2.0](https://clipdemos.umiacs.umd.edu/catvar/), for categorial variation clusters
3. [XHAIL](https://github.com/stefano-bragaglia/XHAIL), for Inductive Logic Programming

## Data Preprocessing
The raw data consists of English sentences and AMR representation. Preprocessing steps on this raw data are as follows:
  - The English sentence is first converted into lowercase.
  - Using Stanford CoreNLP, background knowledge mentioned in Chapter 2 of thesis is extracted from the sentence.
  - The AMR representation is stored in memory in a graph data structure using a recursive algorithm. The algorithm is shown [here](https://github.com/agarwalshubham2007/AMR-Aligner-ILP/tree/master/images/AlgoAMRGraph.png)
  - The graph is traversed to extract out all the concepts.
  - The senses are removed from concepts. Example, in concept ‘want-01’,  ‘-01’ is removed.
  - There are certain concepts that are enclosed in inverted commas, those commas are removed too.
  
## Data Encoding
The preprocessed data is now encoded in ASP format using the extracted background knowledge and concepts in AMR representation. Following are its details:
  - The common background knowledge for each data instance is
    - *position(I) :- token(S,I,L).*
    - *sentence(S) :- token(S,I,L).*
    - *lemmaList(L) :- token(S,I,L).*
  - Then background knowledge for the category rule is being learnt is introduced. For example: Modal Concept Category  
  *modalConcepts(possible;likely;obligate;recommend;permit;prefer).*
  - From the concepts extracted in data processing, concepts related to this category are encoded as examples.  
  For example :  
  *#example concept(possible,0).*
  - Concepts missing in this category for this example are encoded using not prefix. For example:  
  *#example not concept(obligate,0).*  
  .....  
  .....
  - Mode declarations are encoded for the category, rules are being learnt for.
  
## Rule Learning
The encoded data is given to XHAIL system for learning rules. Implementation details for rule learning are as follows:
  - XHAIL system is not scalable for learning rules on large datasets. So rules are learnt on batches of 60 instances.
  - The count of number of times every unique learnt rule is stored as this will decide the preference rule execution inside any category.
  - The learnt rules are mentioned in Chapter 3 of thesis
  
## Aligning
The learnt rules are then executed on every test data instance(see [algorithm](https://github.com/agarwalshubham2007/AMR-Aligner-ILP/tree/master/images/AlgoAlignment.png)) in the following order in the implemented system on the development and test dataset:
  - Concepts as Word Tokens
  - Imperative
  - Concepts Negated with Prefix
    - Negated polarity concept rule
    - Root word concept rule
  - Modal Concepts
    - Concept *possible* invoked by *can*
    - Concept *obligate* invoked by *must*
    - Concept *obligate* invoked by *shall*
    - Concept *recommend* invoked by *should*
    - Concept *possible* invoked by *may*
    - Concept *obligate* invoked by *will*
    - Concept *possible* invoked by *would*
  - Negation Concept
  - Categorial Variation Concept
  - Question Concept
    - *which* with *WDT* POS tag invokes *amr-unknown* concept
    - *what*, *who* or *whom* with *WP* POS tag invokes *amr-unknown* concept
    - *whose* with *WP$* POS tag invokes *amr-unknown* concept
    - *how*, *where*, *when* or *why* with *WRB* POS tag invokes *amr-unknown* concept
  - Causal concept
    - *because* invokes *cause* concept
    - *since* invokes *cause* concept
  - Imperative Concept
