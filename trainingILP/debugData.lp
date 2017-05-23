#display concept/1.
#display token/2.
#display modal/1.

%% Background
position(I) :- token(S,I,L).
sentence(S) :- token(S,I,L).
lemmaList(L) :- token(S,I,L).
%next(S,I,J) :- token(S,I,L), token(S1,J,L1), I=J+1, S=S1.

%modalConcepts(possible;likely;obligate;permit;recommend;prefer;placeholder).
%modalConcepts(possible;placeholder).

%we can say for certain

conceptList(possible;say;we;certain;placeholder).


token(0,1,we).
token(0,2,can).
modal(can).
token(0,3,say).
token(0,4,for).
token(0,5,certain).

%% Examples
%#example concept(possible).
#example not concept(placeholder).
#example concept(say).
#example concept(we).
#example concept(certain).
#example not concept(can).
#example not concept(for).



%% M. Modes

%#modeh concept(+modalConcepts).
%#modeb token(-sentence,-position, $modal).
%#modeb modal($lemmaList).

#modeh concept(+conceptList).
#modeb token(-sentence,-position, +lemmaList).
%#modeb modal(+lemmaList).
%#modeb not modal(+lemmaList).

%#modeb not token(-sentence,-position, -modal).