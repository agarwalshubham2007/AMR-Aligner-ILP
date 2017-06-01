#display concept/2.
#display token/3.
#display modal/1.
%% Background
position(I) :- token(S,I,L).
sentence(S) :- token(S,I,L).
lemmaList(L) :- token(S,I,L).

modalConcepts(possible;likely;obligate;permit;recommend;prefer;placeholder).

%Looking the territorial seas with smoke of beacon-fires up everywhere, what can being a great sports and arts nation accomplish?


token(200,1,look).
token(200,2,the).
token(200,3,territorial).
token(200,4,sea).
token(200,5,with).
token(200,6,smoke).
token(200,7,of).
token(200,8,beacon).
token(200,9,fire).
token(200,10,up).
token(200,11,everywhere).
token(200,12,what).
token(200,13,can).
modal(can).
token(200,14,be).
token(200,15,a).
token(200,16,great).
token(200,17,sport).
token(200,18,and).
token(200,19,art).
token(200,20,nation).
token(200,21,accomplish).

%% Examples
#example concept(possible,200).

%China can not be so " Doctrine of the Mean"~


token(201,1,china).
token(201,2,can).
modal(can).
token(201,3,nt).
token(201,4,be).
token(201,5,so).
token(201,6,doctrine).
token(201,7,of).
token(201,8,the).
token(201,9,mean).

%% Examples
#example concept(possible,201).

%Good things also need to be promoted. Flying radars, factory direct, purchase once and have free lifetime high-speed internet access, the latest technology product. Easy to carry and also high-speed and stable. You will be able to have free high-speed internet access wherever you go with a flight radar as big as a bowl! People who are smart certainly know how to enjoy life better, I wish you a happy life, thanks for finishing reading! Contact Q q 514194237


token(202,1,good).
token(202,2,thing).
token(202,3,also).
token(202,4,need).
token(202,5,to).
token(202,6,be).
token(202,7,promote).
token(202,8,fly).
token(202,9,radar).
token(202,10,factory).
token(202,11,direct).
token(202,12,purchase).
token(202,13,once).
token(202,14,and).
token(202,15,have).
token(202,16,free).
token(202,17,lifetime).
token(202,18,high).
token(202,19,speed).
token(202,20,internet).
token(202,21,access).
token(202,22,the).
token(202,23,latest).
token(202,24,technology).
token(202,25,product).
token(202,26,easy).
token(202,27,to).
token(202,28,carry).
token(202,29,and).
token(202,30,also).
token(202,31,high).
token(202,32,speed).
token(202,33,and).
token(202,34,stable).
token(202,35,you).
token(202,36,will).
modal(will).
token(202,37,be).
token(202,38,able).
token(202,39,to).
token(202,40,have).
token(202,41,free).
token(202,42,high).
token(202,43,speed).
token(202,44,internet).
token(202,45,access).
token(202,46,wherever).
token(202,47,you).
token(202,48,go).
token(202,49,with).
token(202,50,a).
token(202,51,flight).
token(202,52,radar).
token(202,53,as).
token(202,54,big).
token(202,55,as).
token(202,56,a).
token(202,57,bowl).
token(202,58,people).
token(202,59,who).
token(202,60,be).
token(202,61,smart).
token(202,62,certainly).
token(202,63,know).
token(202,64,how).
token(202,65,to).
token(202,66,enjoy).
token(202,67,life).
token(202,68,better).
token(202,69,i).
token(202,70,wish).
token(202,71,you).
token(202,72,a).
token(202,73,happy).
token(202,74,life).
token(202,75,thanks).
token(202,76,for).
token(202,77,finish).
token(202,78,reading).
token(202,79,contact).
token(202,80,q).
token(202,81,q).

%% Examples
#example concept(possible,202).

%That is not good. How can we speak in such a untrustworthy manner, we are a responsible big country - let the matter be, do not mention it again, to develop the economy is of overriding importance.


token(203,1,that).
token(203,2,be).
token(203,3,nt).
token(203,4,good).
token(203,5,how).
token(203,6,can).
modal(can).
token(203,7,we).
token(203,8,speak).
token(203,9,in).
token(203,10,such).
token(203,11,a).
token(203,12,untrustworthy).
token(203,13,manner).
token(203,14,we).
token(203,15,be).
token(203,16,a).
token(203,17,responsible).
token(203,18,big).
token(203,19,country).
token(203,20,let).
token(203,21,the).
token(203,22,matter).
token(203,23,be).
token(203,24,do).
token(203,25,nt).
token(203,26,mention).
token(203,27,it).
token(203,28,again).
token(203,29,to).
token(203,30,develop).
token(203,31,the).
token(203,32,economy).
token(203,33,be).
token(203,34,of).
token(203,35,override).
token(203,36,importance).

%% Examples
#example concept(possible,203).

%4. Accelerate the research and deployment of high-speed intercontinental ballistic missiles which can break missile defense systems and whose range is more than 10,000 nautical miles.


token(204,1,accelerate).
token(204,2,the).
token(204,3,research).
token(204,4,and).
token(204,5,deployment).
token(204,6,of).
token(204,7,high).
token(204,8,speed).
token(204,9,intercontinental).
token(204,10,ballistic).
token(204,11,missile).
token(204,12,which).
token(204,13,can).
modal(can).
token(204,14,break).
token(204,15,missile).
token(204,16,defense).
token(204,17,system).
token(204,18,and).
token(204,19,whose).
token(204,20,range).
token(204,21,be).
token(204,22,more).
token(204,23,than).
token(204,24,nautical).
token(204,25,mile).

%% Examples
#example concept(possible,204).

%Since there is responsibility, we are not afraid. The territory is small, but it is related to the matter of dignity; no matter how the winds and clouds changes, the interests of the country and national unity are the guarantee of honor; even though scarred with wounds, we can not let our native lands be separated!


token(205,1,since).
token(205,2,there).
token(205,3,be).
token(205,4,responsibility).
token(205,5,we).
token(205,6,be).
token(205,7,nt).
token(205,8,afraid).
token(205,9,the).
token(205,10,territory).
token(205,11,be).
token(205,12,small).
token(205,13,but).
token(205,14,it).
token(205,15,be).
token(205,16,related).
token(205,17,to).
token(205,18,the).
token(205,19,matter).
token(205,20,of).
token(205,21,dignity).
token(205,22,no).
token(205,23,matter).
token(205,24,how).
token(205,25,the).
token(205,26,wind).
token(205,27,and).
token(205,28,cloud).
token(205,29,change).
token(205,30,the).
token(205,31,interest).
token(205,32,of).
token(205,33,the).
token(205,34,country).
token(205,35,and).
token(205,36,national).
token(205,37,unity).
token(205,38,be).
token(205,39,the).
token(205,40,guarantee).
token(205,41,of).
token(205,42,honor).
token(205,43,even).
token(205,44,though).
token(205,45,scar).
token(205,46,with).
token(205,47,wound).
token(205,48,we).
token(205,49,can).
modal(can).
token(205,50,nt).
token(205,51,let).
token(205,52,we).
token(205,53,native).
token(205,54,land).
token(205,55,be).
token(205,56,separate).

%% Examples
#example concept(possible,205).

%The best defense is to attack - we should strengthen forces which can engage in ultra-long-range attack, improve combat capability throughout all of aerospace, of great depth, and over great distances. On the premise of avoiding influence on national economic development, increase investment and construction of the defense industry, accelerate modernization and internationalization of military equipment, and establish a high-quality, high-tech elite division.


token(206,1,the).
token(206,2,best).
token(206,3,defense).
token(206,4,be).
token(206,5,to).
token(206,6,attack).
token(206,7,we).
token(206,8,should).
modal(should).
token(206,9,strengthen).
token(206,10,force).
token(206,11,which).
token(206,12,can).
modal(can).
token(206,13,engage).
token(206,14,in).
token(206,15,ultra).
token(206,16,long).
token(206,17,range).
token(206,18,attack).
token(206,19,improve).
token(206,20,combat).
token(206,21,capability).
token(206,22,throughout).
token(206,23,all).
token(206,24,of).
token(206,25,aerospace).
token(206,26,of).
token(206,27,great).
token(206,28,depth).
token(206,29,and).
token(206,30,over).
token(206,31,great).
token(206,32,distance).
token(206,33,on).
token(206,34,the).
token(206,35,premise).
token(206,36,of).
token(206,37,avoid).
token(206,38,influence).
token(206,39,on).
token(206,40,national).
token(206,41,economic).
token(206,42,development).
token(206,43,increase).
token(206,44,investment).
token(206,45,and).
token(206,46,construction).
token(206,47,of).
token(206,48,the).
token(206,49,defense).
token(206,50,industry).
token(206,51,accelerate).
token(206,52,modernization).
token(206,53,and).
token(206,54,internationalization).
token(206,55,of).
token(206,56,military).
token(206,57,equipment).
token(206,58,and).
token(206,59,establish).
token(206,60,a).
token(206,61,high).
token(206,62,quality).
token(206,63,high).
token(206,64,tech).
token(206,65,elite).
token(206,66,division).

%% Examples
#example concept(possible,206).

%4. Accelerate the research and deployment of high-speed intercontinental ballistic missiles which can break missile defense systems and whose range is more than 10,000 nautical miles.


token(207,1,accelerate).
token(207,2,the).
token(207,3,research).
token(207,4,and).
token(207,5,deployment).
token(207,6,of).
token(207,7,high).
token(207,8,speed).
token(207,9,intercontinental).
token(207,10,ballistic).
token(207,11,missile).
token(207,12,which).
token(207,13,can).
modal(can).
token(207,14,break).
token(207,15,missile).
token(207,16,defense).
token(207,17,system).
token(207,18,and).
token(207,19,whose).
token(207,20,range).
token(207,21,be).
token(207,22,more).
token(207,23,than).
token(207,24,nautical).
token(207,25,mile).

%% Examples
#example concept(possible,207).

%My fellow citizens, only if we have self-confidence and self-improvement, can we strive to be strong. Qin Shi Huang unified China. Zheng Chenggong recovered Taiwan. In our responsibilities we must carry on the farsightedness, insight, and strong will of our predecessors.


token(208,1,my).
token(208,2,fellow).
token(208,3,citizen).
token(208,4,only).
token(208,5,if).
token(208,6,we).
token(208,7,have).
token(208,8,self).
token(208,9,confidence).
token(208,10,and).
token(208,11,self).
token(208,12,improvement).
token(208,13,can).
modal(can).
token(208,14,we).
token(208,15,strive).
token(208,16,to).
token(208,17,be).
token(208,18,strong).
token(208,19,qin).
token(208,20,shi).
token(208,21,huang).
token(208,22,unify).
token(208,23,china).
token(208,24,zheng).
token(208,25,chenggong).
token(208,26,recover).
token(208,27,taiwan).
token(208,28,in).
token(208,29,we).
token(208,30,responsibility).
token(208,31,we).
token(208,32,must).
modal(must).
token(208,33,carry).
token(208,34,on).
token(208,35,the).
token(208,36,farsightedness).
token(208,37,insight).
token(208,38,and).
token(208,39,strong).
token(208,40,will).
modal(will).
token(208,41,of).
token(208,42,we).
token(208,43,predecessor).

%% Examples
#example concept(possible,208).

%How can we allow the Diaoyu Islands to fall to other countries


token(209,1,how).
token(209,2,can).
modal(can).
token(209,3,we).
token(209,4,allow).
token(209,5,the).
token(209,6,diaoyu).
token(209,7,island).
token(209,8,to).
token(209,9,fall).
token(209,10,to).
token(209,11,other).
token(209,12,country).

%% Examples
#example concept(possible,209).

token(20000,1,how).
token(20000,2,are).
#example not concept(possible,20000).

%% M. Modes

#modeh concept($modalConcepts,+sentence).
#modeb token(+sentence,-position,$modal).
