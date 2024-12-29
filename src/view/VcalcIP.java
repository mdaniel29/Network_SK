package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import control.CcalcIP;

public class VcalcIP extends JPanel implements ActionListener, KeyListener, FocusListener{

	private MainApp app;
	private Color vcIPcol;
	private static JLabel jl1 = new JLabel("Adresse IP (IPv4)");
	private static JLabel jl2 = new JLabel("Masque de sous-réseau");
	private static JLabel jl3 = new JLabel("Id Net");
	private static JLabel jl4 = new JLabel("Id Host");
	private static JLabel jl5 = new JLabel("Broadcast");
	private JTextField jt1 = new JTextField(); // adresse IP
	private JTextField jt2 = new JTextField(); // mask
	private JTextField jt3 = new JTextField(); // id_Net
	private JTextField jt4 = new JTextField(); // id_Host
	private JTextField jt5 = new JTextField(); // broadcast @
	private JTextArea jta1;
	private Dimension dimLabel = new Dimension(100,30);
	private Dimension dimTextField = new Dimension(100,20);
	private GridLayout leGridLayout;
	private Font font1, font2;
	private Color fColor1;
	private JButton jb1,jb2;

	public VcalcIP(MainApp app) {
		super();
		this.app = app;

		vcIPcol = Color.getColor(getUIClassID());
		setBackground(vcIPcol);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		System.out.println("constructeur VcalcIP");
		JPanel paneContain = new JPanel();
		JPanel paneContain0 = new JPanel();
		JLabel jl_haut = new JLabel("HAUT");
		paneContain0.add(jl_haut);
		this.add(paneContain0);

		leGridLayout = new GridLayout(5,2,20,12);
		leGridLayout.setHgap(10);
		paneContain.setLayout(leGridLayout);

		font1 = new Font("Courier", Font.PLAIN, 18);
		font2 = new Font("Arial", Font.TRUETYPE_FONT, 16);
		fColor1 = Color.BLACK;

		jl1.setPreferredSize(dimLabel);
		jl1.setFont(font1);

		jl1.setForeground(fColor1);
		jl1.setMaximumSize(dimLabel);

		Dimension myDim = new Dimension(dimTextField.width*2,dimTextField.height*5);
		//jt1.setPreferredSize(myDim);
		paneContain.setSize(myDim);
		System.out.println("Dimensions myDim : "+myDim);
		System.out.println("Dimensions paneContain : "+paneContain.getSize());

		//jt1.setMaximumSize(dimTextField);
		//Dimension dimGet = jt1.getMaximumSize();
		//System.out.println("dimGet : "+dimGet.width + " x "+dimGet.height);
		//jt1.setMaximumSize(dimGet);
		jt1.setColumns(12);
		jt1.addKeyListener(this);
		//Tooltip ipTooltip = new Tooltip("Entrez l'adresse IP ici.");
		jt1.setToolTipText("Entrez l'adresse IP ici.");
		jt1.addFocusListener(this);
		SwingUtilities.invokeLater( new Runnable() {

			@Override
			public void run() { // pour placer le curseur par défaut sur jt1
			        jt1.requestFocusInWindow();
			    }
			} );


		jl2.setFont(font1);
		jl2.setForeground(fColor1);
		jl3.setFont(font1);
		jl3.setForeground(fColor1);
		jl4.setFont(font1);
		jl4.setForeground(fColor1);
		jl5.setFont(font1);
		jl5.setForeground(fColor1);

		jta1 = new JTextArea();
		jta1.append("Utilisez calc1");
		jta1.setFont(font2);
		jta1.setBorder(new EmptyBorder(5,25,10,25));
		jta1.setColumns(26);

		paneContain.add(jl1); paneContain.add(jt1);
		paneContain.add(jl2); paneContain.add(jt2);
		paneContain.add(jl3); paneContain.add(jt3);
		paneContain.add(jl4); paneContain.add(jt4);
		paneContain.add(jl5); paneContain.add(jt5);
		paneContain.setMaximumSize(new Dimension(Math.round(app.getWidth()/2),60));
		paneContain.setBackground(new Color(250,210,210));
		paneContain.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		//paneContain.setAlignmentX(LEFT_ALIGNMENT);

		this.add(paneContain);


		JPanel pan_butt = new JPanel();
		BoxLayout blbut = new BoxLayout(pan_butt, BoxLayout.X_AXIS);
		pan_butt.setLayout(blbut);
		pan_butt.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
		jb1 = new JButton("calc1");
		jb2 = new JButton("calc2");
		jb1.addActionListener(this);
		jb1.addKeyListener(this);
		jb2.addActionListener(this);
		pan_butt.add(jb1);
		pan_butt.add(jb2);
		pan_butt.setAlignmentX(LEFT_ALIGNMENT);
		this.add(pan_butt);


		JPanel paneContain2 = new JPanel();
		JLabel jl_bas = new JLabel("BAS");
		//Insets ins1 = new Insets(10,10,10,10);
		paneContain2.setBorder(new EmptyBorder(10,25,5,5));
		paneContain2.add(jta1);
		paneContain2.add(jl_bas);

		this.add(paneContain2);
		app.getContentPane().add(this);
		app.revalidate();
	}


	@Override
	public void actionPerformed (ActionEvent ev) {
		if (ev.getSource()==jb1) {
			System.out.println("Action jb1");
			new CcalcIP(app, this, true); // pour mettre à jour le masque de s/réseau
			new CcalcIP(app,this); // pour traiter  le reste
		}
	}


	@Override
	    public void keyTyped(KeyEvent e) {

	        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	            System.out.println("Right key typed");
	        }
	        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	            System.out.println("Left key typed");
	        }

	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
	    	if (e.getKeyCode()==KeyEvent.VK_ENTER) {
	    		System.out.println("ENTER key pressed");
	    		new CcalcIP(app, this, true); // MAJ masque s/reseau
	    		new CcalcIP(app, this);
	    	}
	        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	            System.out.println("Right key pressed");
	        }
	        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	            System.out.println("Left key pressed");
	        }

	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	            System.out.println("Right key Released");
	        }
	        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	            System.out.println("Left key Released");
	        }
	    }

	@Override
	public void focusGained(FocusEvent efg) {
		//if (!(jt1.getText().equals("") && efg.getSource()==jt1)){
		//jt2.setText("ee");
		//}
	}
	@Override
	public void focusLost(FocusEvent efl) {
		String[] tab_mask_base = {"255.255.255.0", "255.255.0.0", "255.0.0.0"};
		if (jt1.getText()!="") {
			Boolean b_test = Arrays.toString(tab_mask_base).contains(jt1.getText()); // recherche si jt1 contient 1 élément de tab_mask_base
		}
		//if (!(jt1.getText().equals("") && efl.getSource()==jt1 && !(jt2.getText().equals("")))){
			if (!(jt1.getText().equals("")) && efl.getSource()==jt1 && jt2.getText().equals("")) {
			new CcalcIP(this.app, this, true);
			System.out.println("new CcalcIP, 3 params");
			}
		else {
			System.out.println("Focus Lost sur jt1, mais jt2 déjà renseigné.");
		}
		}


	public JTextField getJt1() {
		return jt1;
	}

	public void setJt1(JTextField jt1) {
		this.jt1 = jt1;
	}

	public JTextField getJt2() {
		return jt2;
	}

	public void setJt2(JTextField jt2) {
		this.jt2 = jt2;
	}

	 public JTextField getJt3() {
			return jt3;
		}


		public JTextField getJt4() {
			return jt4;
		}


		public JTextField getJt5() {
			return jt5;
		}

	public JTextArea getJta1() {
		return jta1;
	}

	public void setJta1(JTextArea jta1) {
		this.jta1 = jta1;
	}
}
