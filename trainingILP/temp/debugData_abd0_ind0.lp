#hide.
#show concept/1.
#show modal/1.
#show token/3.
#show use_clause_literal/2.

%%% B. Background
position(I):-token(S,I,L,W).
sentence(S):-token(S,I,L,W).
lemmaList(L):-token(S,I,L,W).
wordList(W):-token(S,I,L,W).
next(S,I,J):-token(S,I,L,W),token(S1,J,L1,W1),I=J+1,S=S1.
modalConcepts(possible;likely;obligate;permit;recommend;prefer).
conceptList(possible;say;we;certain;placeholder).
token(0,1,we,we).
token(0,2,can,can).
modal(can).
token(0,3,say,say).
token(0,4,for,for).
token(0,5,certain,certain).

%%% E. Examples
% #example concept(say).
#maximize[ concept(say) =1 @1 ].
:-not concept(say).
% #example concept(we).
#maximize[ concept(we) =1 @1 ].
:-not concept(we).
% #example concept(certain).
#maximize[ concept(certain) =1 @1 ].
:-not concept(certain).
% #example not concept(can).
#maximize[ not concept(can) =1 @1 ].
:-concept(can).
% #example not concept(for).
#maximize[ not concept(for) =1 @1 ].
:-concept(for).

%%% C. Compression
{ use_clause_literal(V1,0) }:-clause(V1).
{ use_clause_literal(V1,V2) }:-clause(V1),literal(V1,V2).
% concept(V1):-token(V2,V3,V1,V1),not modal(V1).
clause(0).
literal(0,1).
literal(0,2).
:-not clause_level(0,0),clause_level(0,1).
clause_level(0,0):-use_clause_literal(0,0).
clause_level(0,1):-use_clause_literal(0,1).
clause_level(0,1):-use_clause_literal(0,2).
#minimize[ use_clause_literal(0,0) =1 @1 ].
#minimize[ use_clause_literal(0,1) =1 @1 ].
#minimize[ use_clause_literal(0,2) =1 @1 ].
concept(V1):-use_clause_literal(0,0),try_clause_literal(0,1,V2,V3,V1),try_clause_literal(0,2,V1),lemmaList(V1),sentence(V2),position(V3).
try_clause_literal(0,1,V2,V3,V1):-use_clause_literal(0,1),token(V2,V3,V1,V1),sentence(V2),position(V3),lemmaList(V1).
try_clause_literal(0,1,V2,V3,V1):-not use_clause_literal(0,1),sentence(V2),position(V3),lemmaList(V1).
try_clause_literal(0,2,V1):-use_clause_literal(0,2),not modal(V1),lemmaList(V1).
try_clause_literal(0,2,V1):-not use_clause_literal(0,2),lemmaList(V1).

