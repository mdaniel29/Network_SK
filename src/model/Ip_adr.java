package model;

public class Ip_adr {
	private String ipAdr;
	private String ipMask;
	private char ipClass;
	private int[] tabIp;
	private int octet1;
	private int octet2;
	private int octet3;
	private int octet4;
	private String id_net;
	private String id_net_b;
	private String id_host;
	private String id_host_b;
	private String broadcast;
	private int cidr;

/**
 * Classe modèle qui définit une adresse IP, avec son @ réseau, masque, Broadcast,...
 */
public Ip_adr() {
	ipAdr = new String();
	ipMask = new String();

}

public Ip_adr(String ip) {
	if (ip.equals("")) {
		this.ipAdr = "0.0.0.0";
	} else {
		this.ipAdr = ip;
	}
	this.ipClass = getClassIp(ipAdr);
}

public Ip_adr(String ip, String mask) {
	if (ip.equals("")) {
		this.ipAdr = "0.0.0.0";
		System.out.println("Pas d'adresse ip (champ vide)");
	}
	else {
		this.ipAdr = ip;
		this.ipMask = mask;
		this.ipClass = getClassIp(ipAdr);
		tabIp = getTabIp(ipAdr);

		this.octet1 = Integer.valueOf(Integer.toBinaryString(tabIp[0]));
		this.octet2 = Integer.valueOf(Integer.toBinaryString(tabIp[1]));
		this.octet3 = Integer.valueOf(Integer.toBinaryString(tabIp[2]));
		this.octet4 = Integer.valueOf(Integer.toBinaryString(tabIp[3]));

		this.id_net_b = valIdNet(ipAdr);
		this.id_host_b = valIdHost(ipAdr);
		this.id_net = valIdNet_dec(ipAdr);
		this.id_host = valIdHost_dec(ipAdr);

		System.out.println("Class IP : "+ ipClass);

		this.cidr = obtientCidr(ipMask);
		System.out.println("Cidr : "+ cidr);
	}
}


public char getClassIp(String ip) { // retourne la classe de l'@IP
	// split de l'adresse pour décomposer en 4 blocs
	int[] tabIp = getTabIp(ip);
	if (tabIp[0]>0 && tabIp[0]<127) {
		return 'A';
	} else if (tabIp[0]>127 && tabIp[0]<192) {
		return 'B';
	} else if (tabIp[0]>191 && tabIp[0]<224) {
		return 'C';
	} else if (tabIp[0]>223 && tabIp[0]<240) {
		return 'D'; // Multidiffusion
	} else if (tabIp[0]>239 && tabIp[0]<256) {
		return 'C'; // Experimentation
	} else { // Experimentation
		return 0;
	}
}


public int[] getTabIp(String ip) { // retourne un tableau d'entiers correspondants à chaque octet de l'@IP
	String[] split_ip = ip.split("\\.");
	int[] retourTab = new int[split_ip.length]; // normalement int[4] ;)
	for (int i=0; i<split_ip.length; i++) {
		//System.out.println("tab["+i+"] : "+split_ip[i]);
		retourTab[i] = Integer.valueOf(split_ip[i]);
	}

	return retourTab;
}

public String valIdNet(String ip) {
	char gci = getClassIp(ip);
	String result;

	String str1 = String.format("%8s", Integer.toString(octet1)).replaceAll(" ", "0"); // permet un affichage sur 8 caractères
	String str2 = String.format("%8s", Integer.toString(octet2)).replaceAll(" ", "0");
	String str3 = String.format("%8s", Integer.toString(octet3)).replaceAll(" ", "0");
	//String str4 = String.format("%32s", Integer.toString(octet4)).replaceAll(" ", "0");
	switch (gci) {
	case 'A':
		//result =  Integer.toString(octet1);
		result =  str1;
		break;
	case 'B':
		result =  str1+"  "+str2;
		break;
	case 'C':
		result = str1+"  "+str2+"  "+str3;
		break;
	default:
		result =  "-";
		break;
	}
	return result;
}

public String valIdNet_dec(String ip) { // valeur en décimal de id Net
	char gci = getClassIp(ip);
	String result;
	int[] tab = getTabIp(ip);
	switch (gci) {
	case 'A':
		result = Integer.toString(tab[0]);
		break;
	case 'B':
		result = Integer.toString(tab[0])+"."+Integer.toString(tab[1]);
		break;
	case 'C':
		result = Integer.toString(tab[0])+"."+Integer.toString(tab[1])+"."+Integer.toString(tab[2]);
		break;
	default:
		result =  "-";
		break;
	}

	return result;
}

public String valIdHost(String ip) { // id Host en binaire
	char gci = getClassIp(ip);
	String result;
	//String str1 = String.format("%32s", Integer.toString(octet1)).replaceAll(" ", "0");
	String str2 = String.format("%8s", Integer.toString(octet2)).replaceAll(" ", "0");
	String str3 = String.format("%8s", Integer.toString(octet3)).replaceAll(" ", "0");
	String str4 = String.format("%8s", Integer.toString(octet4)).replaceAll(" ", "0");
	switch (gci) {
	case 'A':
		//result =  Integer.toString(octet2)+" "+Integer.toString(octet3)+" "+Integer.toString(octet4);
		result = str2+"  "+str3+"  "+str4;
		break;
	case 'B':
		result =  str3+"  "+str4;
		break;
	case 'C':
		result = str4;
		break;
	default:
		result =  "-";
		break;
	}
	return result;
}

public String valIdHost_dec(String ip) { // id Host en décimal
	char gci = getClassIp(ip);
	String result;
	int[] tab = getTabIp(ip);
	switch (gci) {
	case 'C':
		result = Integer.toString(tab[3]);
		break;
	case 'B':
		result = Integer.toString(tab[2])+"."+Integer.toString(tab[3]);
		break;
	case 'A':
		result = Integer.toString(tab[1])+"."+Integer.toString(tab[2])+"."+Integer.toString(tab[3]);
		break;
	default:
		result =  "-";
		break;
	}

	return result;
}

public int obtientCidr(String ipMask) {
	int i = 0; // compteur
	int somme = 0;
	// pas de vérification pour savoir si l'@ip est valide (doit être testé en amont)
	int[] tabMask = getTabIp(ipMask);
	//int octet1 = tabMask[0]; int octet2 = tabMask[1]; int octet3 = tabMask[2]; int octet4 = tabMask[4];
	String str1 = String.format("%8s", Integer.toBinaryString(tabMask[0])).replaceAll(" ", "0"); // permet un affichage sur 8 caractères
	String str2 = String.format("%8s", Integer.toBinaryString(tabMask[1])).replaceAll(" ", "0");
	String str3 = String.format("%8s", Integer.toBinaryString(tabMask[2])).replaceAll(" ", "0");
	String str4 = String.format("%8s", Integer.toBinaryString(tabMask[3])).replaceAll(" ", "0");
	
	while (i < str1.length()) {
		//System.out.println("** "+str1.charAt(i));
		if (str1.charAt(i)=='1'){
			somme+=1;
			//System.out.println("l205 : OUI");
		}
		i++;
		//System.out.println("l207 somme : "+somme);
			}
	i=0; // init
	while (i < str2.length()) {
		if (str2.charAt(i)=='1'){
			somme+=1;
		}
		i++;
			}
	i=0; // init
	while (i < str3.length()) {
		if (str3.charAt(i)=='1'){
			somme+=1;
		}
		i++;
			}
	i=0; // init
	while (i < str4.length()) {
		if (str4.charAt(i)=='1'){
			somme+=1;
		}
		i++;
			}
	//System.out.println("str1 : "+str1);
	//System.out.println("str2 : "+str2);
	//System.out.println("str3 : "+str3);
	//System.out.println("str4 : "+str4);
	return somme;
}

public String getIpAdr() {
	return ipAdr;
}

public void setIpAdr(String ipAdr) {
	this.ipAdr = ipAdr;
}

public int getCidr() {
	return cidr;
}

public String getIpMask() {
	return ipMask;
}

public void setIpMask(String ipMask) {
	this.ipMask = ipMask;
}

public char getIpClass() {
	return ipClass;
}

public void setIpClass(char ipClass) {
	this.ipClass = ipClass;
}

public int getOctet1() {
	return octet1;
}

public int getOctet2() {
	return octet2;
}

public int getOctet3() {
	return octet3;
}

public int getOctet4() {
	return octet4;
}

public String getId_net_b() {
	return id_net_b;
}

public String getId_host_b() {
	return id_host_b;
}

public String getId_net() {
	return id_net;
}

public String getId_host() {
	return id_host;
}
}
