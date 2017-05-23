#hide.
#show abduced_concept/1.
#show concept/1.
#show conceptList/1.
#show lemmaList/1.
#show modal/1.
#show position/1.
#show sentence/1.
#show token/3.
#show token/4.
#show wordList/1.

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

%%% I. Inflation
% #modeh concept(+lemmaList).
0 { abduced_concept(V1) :lemmaList(V1) } 2147483647.
#minimize[ abduced_concept(V1) =1 @1 :lemmaList(V1) ].
concept(V1):-abduced_concept(V1),lemmaList(V1).

