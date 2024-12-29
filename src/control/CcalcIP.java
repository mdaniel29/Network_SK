package control;

import model.Ip_adr;
import view.MainApp;
import view.VcalcIP;

public class CcalcIP {

	MainApp app;
	VcalcIP vip;
	Boolean unfocusJt1;
	private Ip_adr ipadr1, ipadr2;

	// CONSTRUCTOR1
	public CcalcIP(MainApp ma,VcalcIP vip) {
		this.app = ma;
		this.vip = vip;
		System.out.println("Constructeur de CcalcIP, 2 paramètres.");
		String getIp = vip.getJt1().getText();
		String getMask = vip.getJt2().getText();
		if (checkIP(getIp)) {
			ipadr1 = new Ip_adr(getIp, getMask);

		//char resClassIp = ipadr1.getClassIp(getIp);
			System.out.println("ipClass : "+ipadr1.getIpClass());
		}
		System.out.println("getIp : "+getIp);
		try {
			if (vip.getJt1().getText().equals("")) {
				vip.getJta1().setText("Vous n'avez pas fourni d'adresse ip.");
			}
			if (checkIP(getIp)) {
				System.out.println("Format d'adresse ip correct.");
				vip.getJta1().setText("");
				vip.getJta1().append("@IP : "+getIp);
				vip.getJta1().append("\nRéseau de classe : ");
				vip.getJta1().append(String.valueOf(ipadr1.getIpClass()));

				vip.getJta1().append("\nId Net : "+(ipadr1.getId_net_b()));
				vip.getJta1().append("\nId Host : "+(ipadr1.getId_host_b()));
				vip.getJta1().append("\nMasque (CIDR) : /"+ipadr1.getCidr());

				vip.getJt3().setText(ipadr1.getId_net());
				vip.getJt4().setText(ipadr1.getId_host());
			}
			else {
				System.out.println("Format incorrect pour l'adresse ip.");
				vip.getJta1().setText("Format incorrect pour l'adresse ip.");
			}
		}
		catch (Exception e) {
			System.out.println("Traitement regex impossible.");
		}


		ma.repaint();
		ma.revalidate();

	}

	// CONSTRUCTOR2
	public CcalcIP(MainApp ma,VcalcIP vip, Boolean unfocusJt1) {
		this.app = ma;
		this.vip = vip;
		this.unfocusJt1 = unfocusJt1;

		String getIp = this.vip.getJt1().getText();
		String getMask = vip.getJt2().getText();

		if (!getMask.equals("") && checkIP(getIp)) { // sélection du constructeur en fonction de la présence d'un masque
			ipadr2 = new Ip_adr(getIp, getMask);
		}
		else if (checkIP(getIp)) {
			ipadr2 = new Ip_adr(getIp);
		}

		String laClasse="";
		System.out.println("Constructeur de CcalcIP, 3 paramètres.");
		System.out.println("getIp : "+getIp);
		try {
			if (vip.getJt1().getText().equals("")) {
				vip.getJta1().setText("Vous n'avez pas fourni d'adresse ip.");
			}
			if (checkIP(getIp)) {
				//System.out.println("Format d'adresse ip correct.");
				//System.out.println("ipadr1 : "+ipadr2.getIpAdr());
				laClasse = String.valueOf(ipadr2.getIpClass());
				//System.out.println("laClasse : "+laClasse);
			}
			else {
				System.out.println("Format incorrect pour l'adresse ip.");
				vip.getJta1().setText("Format incorrect pour l'adresse ip.");
			}
		}
		catch (Exception e) {
			System.out.println("Traitement regex impossible : "+e.getMessage());
		}
		if (this.unfocusJt1) {
			//System.out.println("UNFOCUSJT1"); // DEBUG
			switch (laClasse) {
			case "A":
				vip.getJt2().setText("255.0.0.0");
				break;
			case "B":
				vip.getJt2().setText("255.255.0.0");
				break;
			case "C":
				vip.getJt2().setText("255.255.255.0");
				break;
			default:
				vip.getJt2().setText("");

			}

		}

	}

	public boolean checkIP(String ip) { // vérifie le format de l'@ip
		String[] blocs = ip.split("\\.");
		boolean boolBloc = false;

		for (String elem: blocs) {
			//System.out.println("elem : "+elem);
			if (!(Integer.valueOf(elem)>-1 && Integer.valueOf(elem)<256)) {
				//System.out.println("bad cond");
				boolBloc = false;
				break;
			} else {
				boolBloc = true;
			}
		}
		//System.out.println("boolBloc : "+boolBloc);
		if (ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}") && boolBloc) {
			return true;
		} else {
			return false;
		}
	}

}
