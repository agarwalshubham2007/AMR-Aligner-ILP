#display concept/2.
#display token/3.
#display modal/1.
%% Background
position(I) :- token(S,I,L).
sentence(S) :- token(S,I,L).
lemmaList(L) :- token(S,I,L).

modalConcepts(possible;likely;obligate;permit;recommend;prefer;placeholder).

%Natural disasters cannot be predicted, but the school buildings should not have collapsed.


token(40,1,natural).
token(40,2,disaster).
token(40,3,can).
modal(can).
token(40,4,nt).
token(40,5,be).
token(40,6,predict).
token(40,7,but).
token(40,8,the).
token(40,9,school).
token(40,10,building).
token(40,11,should).
modal(should).
token(40,12,nt).
token(40,13,have).
token(40,14,collapse).

%% Examples
#example concept(possible,40).

%In Mao Zedong's residence, the glass window in the foyer door rattled uncontrollably, and there was a huge bang from below like a tractor starting up, with Mao Zedong's sickbed shaking non-stop.......


token(41,1,in).
token(41,2,mao).
token(41,3,zedong).
token(41,4,be).
token(41,5,residence).
token(41,6,the).
token(41,7,glass).
token(41,8,window).
token(41,9,in).
token(41,10,the).
token(41,11,foyer).
token(41,12,door).
token(41,13,rattle).
token(41,14,uncontrollably).
token(41,15,and).
token(41,16,there).
token(41,17,be).
token(41,18,a).
token(41,19,huge).
token(41,20,bang).
token(41,21,from).
token(41,22,below).
token(41,23,like).
token(41,24,a).
token(41,25,tractor).
token(41,26,start).
token(41,27,up).
token(41,28,with).
token(41,29,mao).
token(41,30,zedong).
token(41,31,s).
token(41,32,sickb).
token(41,33,shake).
token(41,34,non).
token(41,35,stop).

%% Examples
#example not concept(possible,41).

%In normal times, apart from the talking and noise of medical instruments when the doctors revived him, not a whisper could be heard.


token(42,1,in).
token(42,2,normal).
token(42,3,time).
token(42,4,apart).
token(42,5,from).
token(42,6,the).
token(42,7,talking).
token(42,8,and).
token(42,9,noise).
token(42,10,of).
token(42,11,medical).
token(42,12,instrument).
token(42,13,when).
token(42,14,the).
token(42,15,doctor).
token(42,16,revive).
token(42,17,he).
token(42,18,nt).
token(42,19,a).
token(42,20,whisper).
token(42,21,could).
modal(could).
token(42,22,be).
token(42,23,hear).

%% Examples
#example concept(possible,42).

%During the early nineties, the formal employees of this enterprise still had complete working teams, such as bricklaying teams, woodwork teams, steel reinforcement teams, and machinery maintenance teams, but since they could not cope with a large volume of project tasks, they were already cooperating with some migrant worker units, in other words the familiar "contracting gangs".


token(43,1,during).
token(43,2,the).
token(43,3,early).
token(43,4,ninety).
token(43,5,the).
token(43,6,formal).
token(43,7,employee).
token(43,8,of).
token(43,9,this).
token(43,10,enterprise).
token(43,11,still).
token(43,12,have).
token(43,13,complete).
token(43,14,work).
token(43,15,team).
token(43,16,such).
token(43,17,as).
token(43,18,bricklay).
token(43,19,team).
token(43,20,woodwork).
token(43,21,team).
token(43,22,steel).
token(43,23,reinforcement).
token(43,24,team).
token(43,25,and).
token(43,26,machinery).
token(43,27,maintenance).
token(43,28,team).
token(43,29,but).
token(43,30,since).
token(43,31,they).
token(43,32,could).
modal(could).
token(43,33,nt).
token(43,34,cope).
token(43,35,with).
token(43,36,a).
token(43,37,large).
token(43,38,volume).
token(43,39,of).
token(43,40,project).
token(43,41,task).
token(43,42,they).
token(43,43,be).
token(43,44,already).
token(43,45,cooperate).
token(43,46,with).
token(43,47,some).
token(43,48,migrant).
token(43,49,worker).
token(43,50,unit).
token(43,51,in).
token(43,52,other).
token(43,53,word).
token(43,54,the).
token(43,55,familiar).
token(43,56,contracting).
token(43,57,gang).

%% Examples
#example concept(possible,43).

%In fact, in a socialist country one cannot act in that way.


token(44,1,in).
token(44,2,fact).
token(44,3,in).
token(44,4,a).
token(44,5,socialist).
token(44,6,country).
token(44,7,one).
token(44,8,can).
modal(can).
token(44,9,nt).
token(44,10,act).
token(44,11,in).
token(44,12,that).
token(44,13,way).

%% Examples
#example concept(possible,44).

%Not knowing how much profit is in there, not caring who can afford it and who can't, these do not seem to be the principles of socialism.


token(45,1,nt).
token(45,2,know).
token(45,3,how).
token(45,4,much).
token(45,5,profit).
token(45,6,be).
token(45,7,in).
token(45,8,there).
token(45,9,nt).
token(45,10,care).
token(45,11,who).
token(45,12,can).
modal(can).
token(45,13,afford).
token(45,14,it).
token(45,15,and).
token(45,16,who).
token(45,17,can).
modal(can).
token(45,18,t).
token(45,19,these).
token(45,20,do).
token(45,21,nt).
token(45,22,seem).
token(45,23,to).
token(45,24,be).
token(45,25,the).
token(45,26,principle).
token(45,27,of).
token(45,28,socialism).

%% Examples
#example concept(possible,45).

%So when the Jerry Yangs hope that by bargaining they can bump up Microsoft's purchase price, they are being very unrealistic.


token(46,1,so).
token(46,2,when).
token(46,3,the).
token(46,4,jerry).
token(46,5,yang).
token(46,6,hope).
token(46,7,that).
token(46,8,by).
token(46,9,bargaining).
token(46,10,they).
token(46,11,can).
modal(can).
token(46,12,bump).
token(46,13,up).
token(46,14,microsoft).
token(46,15,s).
token(46,16,purchase).
token(46,17,price).
token(46,18,they).
token(46,19,be).
token(46,20,be).
token(46,21,very).
token(46,22,unrealistic).

%% Examples
#example concept(possible,46).

%Right from the start, this was a super acquisition that lacked bite; despite the numbers being huge, even if the acquisition was successful, truly revolutionizing the framework of the Internet was simply not possible.


token(47,1,right).
token(47,2,from).
token(47,3,the).
token(47,4,start).
token(47,5,this).
token(47,6,be).
token(47,7,a).
token(47,8,super).
token(47,9,acquisition).
token(47,10,that).
token(47,11,lack).
token(47,12,bite).
token(47,13,despite).
token(47,14,the).
token(47,15,number).
token(47,16,be).
token(47,17,huge).
token(47,18,even).
token(47,19,if).
token(47,20,the).
token(47,21,acquisition).
token(47,22,be).
token(47,23,successful).
token(47,24,truly).
token(47,25,revolutionize).
token(47,26,the).
token(47,27,framework).
token(47,28,of).
token(47,29,the).
token(47,30,internet).
token(47,31,be).
token(47,32,simply).
token(47,33,nt).
token(47,34,possible).

%% Examples
#example not concept(possible,47).

%"I went repeatedly to find the relevant health department, but they passed my case back and forth. I was helpless and could only take my daughter to the place where I work -- Xiamen, to carry out treatment."


token(48,1,i).
token(48,2,go).
token(48,3,repeatedly).
token(48,4,to).
token(48,5,find).
token(48,6,the).
token(48,7,relevant).
token(48,8,health).
token(48,9,department).
token(48,10,but).
token(48,11,they).
token(48,12,pass).
token(48,13,my).
token(48,14,case).
token(48,15,back).
token(48,16,and).
token(48,17,forth).
token(48,18,i).
token(48,19,be).
token(48,20,helpless).
token(48,21,and).
token(48,22,could).
modal(could).
token(48,23,only).
token(48,24,take).
token(48,25,my).
token(48,26,daughter).
token(48,27,to).
token(48,28,the).
token(48,29,place).
token(48,30,where).
token(48,31,i).
token(48,32,work).
token(48,33,xiamen).
token(48,34,to).
token(48,35,carry).
token(48,36,out).
token(48,37,treatment).

%% Examples
#example concept(possible,48).

%The doctor in charge told us that the kid would be out of danger if he could just urinate.


token(49,1,the).
token(49,2,doctor).
token(49,3,in).
token(49,4,charge).
token(49,5,tell).
token(49,6,we).
token(49,7,that).
token(49,8,the).
token(49,9,kid).
token(49,10,would).
modal(would).
token(49,11,be).
token(49,12,out).
token(49,13,of).
token(49,14,danger).
token(49,15,if).
token(49,16,he).
token(49,17,could).
modal(could).
token(49,18,just).
token(49,19,urinate).

%% Examples
#example concept(possible,49).

token(200,1,how).
token(200,2,are).
#example not concept(possible,200).

%% M. Modes

#modeh concept($modalConcepts,+sentence).
#modeb token(+sentence,-position,$modal).
