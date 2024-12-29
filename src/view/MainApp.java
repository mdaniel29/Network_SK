package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MainApp extends JFrame implements MouseListener{
	public static Vn1 vn1a,vn1b;
	public static VcalcIP vcip1;
	private JMenuBar menu;
	private JMenu fichier;
	private JMenu edit;
	private JMenu outils;
	private JMenuItem jmi_f1,jmi_f2;
	private JMenuItem jmi_o0,jmi_o1,jmi_o2;
	private JMenuItem jmi_fquit;

	public MainApp() {
		setTitle("Network_SK : le couteau suisse du réseau.");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(dimFrame());

		menu = new JMenuBar();
		fichier = new JMenu("Fichier");
		edit = new JMenu("Edition");
		jmi_f1 = new JMenuItem("Nouveau");
		jmi_f2 = new JMenuItem("Ouvrir");
		jmi_fquit = new JMenuItem("Quitter");
		outils = new JMenu("Outils");
		jmi_o0 = new JMenuItem("Home Panel");
		jmi_o1 = new JMenuItem("Calcul adresses ip");
		jmi_o2 = new JMenuItem("Scanner de ports");

		barreMenu(); // on appelle la fonction qui gère le Menu principal

	}

	public static void main(String[] args){
		// TODO Auto-generated method stub
		MainApp app = new MainApp();
		app.setResizable(true);
		app.setVisible(true);

		/* vue de base de l'application : vn1 */
		vn1a = new Vn1(app);
		centre(app);
		app.getContentPane().add(vn1a);


		//vcip1 = new VcalcIP(app);
		//moveToScreen(app);
		//centre(app);
		//app.getContentPane().add(vcip1);



		app.revalidate();
		//app.pack();

		// test 24/11/24
		//String ipStr = "192.168.1.12";
		//String ipMask = "255.255.255.0";
		//Ip_adr ipAdr1 = new Ip_adr(ipStr,ipMask);
	}


	public Dimension dimFrame() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension scrSize = tk.getScreenSize();
		int scrSize_w = Math.round(scrSize.width*60/100);
		int scrSize_h = Math.round(scrSize.height*60/100);
		return (new Dimension(scrSize_w,scrSize_h));
	}

	private void barreMenu() {
		setJMenuBar(menu);
		menu.add(fichier);
		fichier.add(jmi_f1);
		fichier.add(jmi_f2);
		fichier.addSeparator();
		fichier.add(jmi_fquit);
		menu.add(edit);
		outils.add(jmi_o0);
		menu.add(outils);
		outils.add(jmi_o1);
		outils.add(jmi_o2);
		jmi_f1.addMouseListener(this);
		jmi_fquit.addMouseListener(this);
		jmi_o0.addMouseListener(this);
		jmi_o1.addMouseListener(this);

	}

	public static void centre(JFrame frame) { // centrage fenêtre
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = ((screen.width - frame.getPreferredSize().width)) / 2;
	    int y = ((screen.height - frame.getPreferredSize().height)) / 2;
	    frame.setLocation(x, y);
	    System.out.println("Screen width : "+screen.width);
	    System.out.println("frame.getPreferredSize().width : "+frame.getPreferredSize().width);
	    System.out.println("Frame location : ("+x+", "+y+")");
	}
	public static void moveToScreen(JFrame frame) {
	    frame.setVisible(false);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] screens = ge.getScreenDevices();
	    int n = screens.length;
	    for (int i = 0; i < n; i++) {
	        	System.out.println("screen[i].getDefaultConfiguration : "+ screens[i].getDefaultConfiguration());
	            //JFrame dummy = new JFrame(screens[i].getDefaultConfiguration());
	        	JFrame dummy = new JFrame(screens[1].getDefaultConfiguration());
	            frame.setLocationRelativeTo(dummy);
	            dummy.dispose();

	    }
	    frame.setVisible(true);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("MOUSE.EVENT - mouseClicked");

	}
	@Override
	public void mouseExited(MouseEvent e) {
	       //System.out.println("Mouse exited : "+ e);
	    }
	@Override
	public void mouseReleased(MouseEvent e) {
	    //System.out.println("Mouse released; # of clicks: "+ e.getClickCount());
	    }
	@Override
	public void mouseEntered(MouseEvent e) {
	      //System.out.println("Mouse entered "+ e);

	    }
	@Override
	public void mousePressed(MouseEvent e) {
	       System.out.println("Mouse pressed; # of clicks: "+ e.getClickCount());

	       if (e.getSource()==jmi_f1) {
				System.out.println("jmi_f1 ACTIVE !!!");
				MainApp man = new MainApp();
				man.setVisible(true);
			}

	       if (e.getSource()==jmi_fquit) {
				vn1a.setMyBgColor(Color.RED);
				this.revalidate();
				System.out.println("jmi_f1 QUIT !!!");
				dispose();
			}
	       if (e.getSource()==jmi_o0) {
				this.remove(vcip1);
	    	   //Vn1 vn1_0 = new Vn1(this);
				this.getContentPane().add(vn1a);
				repaint();
				revalidate();
				System.out.println("jmi_o0 ACTIVE !!!");
			}
	       if (e.getSource()==jmi_o1) {
	    	   remove(vn1a);
	    	   vcip1 = new VcalcIP(this);
	    	   repaint();
	    	   revalidate();
			System.out.println("mousePressed : jmi_o1 ACTIVE !!!");
			}
	    }
}
