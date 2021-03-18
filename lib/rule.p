%%%%%%%Rule%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
canbeComp(A,T):- 
				assComp(A,T),
				write('\n-> ('),
				write(A), 
				write(') is Assumed Compromised by ('),
				write(T), 
				write(')'). 


canbeMalfun(A):- 
				assMalfun(A),
				write('\n-> ('),
				write(A), 
				write(') is Assumed Mulfuntion') .
				
				
canbeVul(A,T):-  
				assVul(A,T),
				write('\n-> ('),
				write(A), 
				write(') is Assumed Vulnerable to ('),
				write(T), 
				write(')').


%%%%%%%%%%%%%%%%%%%Higt-level%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%Usable
usable(A):- 
			e(A),(\+canbeComp(A,_T), \+canbeMalfun(A)).

%%Protected
protected(A,T):- 
				protect(B,A,T),	usable(B).

%%Safe
notSafe(A,T):- 
			  e(A), t(T),  (\+protected(A,T),   canbeVul(A,T)).

%%Monitored
monitored(A,T):-  
				monitor(B,A,T), usable(B).

%%Replicate
replicated(A):- 
				replica(B,A), usable(B).

%%Checked
checked(A):- 
			check(B,A), usable(B).



%%%%%%%%%%%%%% Derivation Rules%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%Malfunctioned%%%%%%%%%%%%%%%%%%%%%
canbeMalfun4Comp(A):- 
					canbeComp(A,_T), 
					write('\n-> ('), 
					write(A), 
					write(') can be Mulfuntion because it can be Compromised').

canbeMalfun4Dep(A):- 
					depend(A,B),
					(canbeComp(B,_T); assMalfun(B); canbeMalfun4Dep(B)),
					write('\n-> ('),
					write(A), 
					write(') can be Mulfuntion because depend on ('),
					write(B), 
					write(')').

canbeMalfun(B):- canbeMalfun4Comp(B).
canbeMalfun(B):- canbeMalfun4Dep(B).

%%%%%Vulnerable%%%%%%%%%%%%%%%%%%%%%
canbeVul(A,T):- potentiallyVul(A,T), canbeMalfun4Dep(A).

%%%%%Compromised%%%%%%%%%%%%%%%%%%%%%
canbeComp(A,T2):- 
				control(B,A), 
				canbeComp(B,_T1), 
				spread(B,T2), 
				notSafe(A,T2),
				write('\n-> ('),
				write(A), 
				write(') can be compromises through ('),
				write(B), 
				write(') by ('),
				write(T2),  
				write(')').


canbeComp(A,T2):- 
				connect(C,B,A), 
				canbeComp(B,_T1), 
				spread(B,T2),
				notSafe(A,T2), 
				(canbeComp(C,_T3); \+protected(C,T2)), 
				write('\n-> ('), 
				write(A),
				write(') can be compromises through ('),
				write(B), 
				write(') by ('), 
				write(T2), 
				write(')').

canbeComp(A,T2):- 
				(contain(A,B); isContained(A,B)),
				canbeComp(B,_T1), 
				spread(B,T2), 
				notSafe(A,T2), 
				write('\n-> ('), 
				write(B), 
				write(') compromises ('), 
				write(A), 
				write(') by ('), 
				write(T2),
				write(')').


%%%%%Detectable
canbeDet(A,T):- canbeComp(A,T), monitored(A,T).

%%%%%Restorable
canbeRest(A):- canbeDet(A,_T) , replicated(A).

%%%%Fixed
canbeFix(A):- canbeMalfun(A), checked(A).

%%%%Penso siano necessari per permettere le varie operazioni sopra
%%%%Ad es. control/2 non sarebbe definito se non ci fosse la riga qui sotto

control(a1,b1).
connect(a2,b2,c2).
contain(a3,b3).
isContained(a4,b4).
depend(a5,b5).
protect(a6,b6,c6).
potentiallyVul(a7,b7).
assMalfun(a8).
monitor(a9,b9,c9).
check(a10,b10).
assComp(a11,b11).
assVul(a13,b13).
spread(a14,b14).
replica(a15,b15).
e(a16).
t(a17).

%%%provo a aggiungere voci canbeCompromised(a11,b11). ...
%%%canbeComp(a18,b18).
%%%canbeMalfun(a19).
%%%canbeVul(a20,b20).
%%%canbeDet(a21,b21).
%%%canbeRest(a22).
%%%canbeFix(a23).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%