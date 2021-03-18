e(website).
e(air_conditioned).
e(fanbox_airConditioned).
e(servers).
e(attacker).
e(biometricAuth).
e(room).
e(path).
e(path2).
e(internet).
e(firewall).
e(antivirus).
e(antimalwer).
e(ids).
e(tcpConnection).


t(virus).
t(malwer).
t(intrusion).
t(unauthorizedAccess).
t(physicalAttack).
t(maliciousPurpose).
t(dos).


contain(servers, website).
contain(room, servers).
contain(room, air_conditioned).
depend(air_conditioned, fanbox_airConditioned).
depend(servers, air_conditioned).
depend(website, servers).


connect(tcpConnection, internet, servers).
connect(path, attacker, fanbox_airConditioned).
connect(path2, attacker, room).


protect(firewall, tcpConnection, dos).
protect(ids, tcpConnection, intrusion).
protect(antivirus, servers, virus).
protect(antimalwer, servers, malwer).
protect(biometricAuth, path2, unauthorizedAccess).


spread(room, physicalAttack).
spread(attacker, physicalAttack).
spread(internet, dos).
spread(internet, virus).
spread(internet, intrusion).
spread(internet, malwer).




assVul(air_conditioned, physicalAttack).
assVul(fanbox_airConditioned, physicalAttack).
assComp(attacker, maliciousPurpose).
assComp(internet, maliciousPurpose).
assVul(servers, virus).
assVul(servers, dos).
assVul(servers, malwer).
assVul(website, virus).
assVul(website, dos).
assVul(website, malwer).
