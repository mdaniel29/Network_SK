package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Vn1 extends JPanel{
	private static final long serialVersionUID = 1L;
	private MainApp appli;
	private JLabel jl1;
	private JTextArea jt1;
	private Color myBgColor;

	public Vn1(MainApp app) {
		this.appli = app;
		//appli.getContentPane().removeAll();
		//this.removeAll();

		myBgColor= Color.PINK;
		setBackground(myBgColor);
		setLayout(new FlowLayout());
		jl1 = new JLabel("Le label");
		jt1 = new JTextArea();
		jt1.setText("Ceci est un texte de premier ordre.");

		this.add(jl1);
		this.add(jt1);

		this.setOpaque(true);
		//appli.repaint();
		//appli.revalidate();
		appli.getContentPane().add(this);
		appli.getContentPane().revalidate();

	}

	public Color getMyBgColor() {
		return myBgColor;
	}

	public void setMyBgColor(Color myBgColor) {
		this.myBgColor = myBgColor;
	}

	public void paint2(Graphics g) {
		// Dynamically calculate size information
		        Dimension size = getSize();         // diameter
		            int d = Math.min(size.width, size.height);
		            int x = (size.width - d)/3;
		            int y = (size.height - d)/2;
		            // draw circle (color already set to foreground)
		            g.setColor(Color.orange);
		        g.fillOval(x, y, d/10, d);
		            g.setColor(Color.black);
		            g.drawOval(x, y, d/10, d);
		            }


}
