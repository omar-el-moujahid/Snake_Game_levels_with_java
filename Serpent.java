package levels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Serpent extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panel ; 
	private int largeur=20;
	private int vitesse=110;
	private int nbCasesX=50;
	private int nbCasesY=30;
	private int margeX=100;
	private int margeY=100;
	Timer horloge ;
	Color headrcolor = Color.orange;
	Color bodyrcolor = Color.green;
	Color ropasrcolor = Color.blue;
	Color obstclercolor = Color.red;
	String direction="r";
	ArrayList<Cellule> serpent ;
	Cellule ropas ;
	int score ;
	JLabel scoreLabel;
	public Serpent() {
		
		this.setTitle("Serpent Game ");
		this.setSize(margeX*2 +nbCasesX* largeur ,margeY*2 +nbCasesY* largeur);
		this.setLocationRelativeTo(null);
		score=0;
		scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setBounds(360, 100, 50, 50); 
		serpent = new ArrayList<Cellule>();
		for(int i =5 ; i>0 ;i--) {
			Cellule cellule =new Cellule(margeX + i *largeur, margeY , bodyrcolor) ;
			serpent.add(cellule);
		}
		serpent.get(0).color = headrcolor ;
		
		init_ropas();
		panel= new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				
				// desing panel without margen
				
				
				
				g.setColor(Color.black);
                g.fillRect(margeX, margeY, nbCasesX * largeur, nbCasesY * largeur);
                
                
             // lines
				g.setColor(Color.gray);
				for (int i = 0; i <= nbCasesX; i++) {
					g.drawLine(margeX + i*largeur, margeY, margeX + i*largeur , margeY + nbCasesY*largeur);
                }
                for (int j = 0; j <= nbCasesY; j++) {
                    g.drawLine(margeX, margeY + j * largeur, margeX + nbCasesX * largeur, margeY + j * largeur);
                }
                
                // serpent 
                for(Cellule hearder : serpent ) {
                	g.setColor(hearder.color);
                    g.fillRect(hearder.x, hearder.y, largeur, largeur);
				}
                // ropas
                
                g.setColor(ropas.color);
                g.fillRect(ropas.x, ropas.y, largeur, largeur);
			}
			
		};
		
// horloge 
		
		horloge = new Timer(vitesse, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=serpent.size()-1;i>0;i--) {
					
					serpent.get(i).x=serpent.get(i-1).x;
					serpent.get(i).y=serpent.get(i-1).y;
				}
				if(direction.equals("r"))  serpent.get(0).x+=largeur;
				if(direction.equals("l"))  serpent.get(0).x-=largeur;
				if(direction.equals("u")) serpent.get(0).y-=largeur;
				if(direction.equals("d"))  serpent.get(0).y+=largeur;
				if (serpent.get(0).x == nbCasesX * largeur + margeX) {
				    serpent.get(0).x = margeX;
				}

				if (serpent.get(0).x == margeX - largeur) {
				    serpent.get(0).x = nbCasesX * largeur + margeX -largeur;
				}

				if (serpent.get(0).y == nbCasesY * largeur + margeY) {
				    serpent.get(0).y = margeY;
				}

				if (serpent.get(0).y == margeY - largeur) {
				    serpent.get(0).y = nbCasesY * largeur + margeY -largeur;
				}
				
				if(serpent.get(0).x==ropas.x && serpent.get(0).y==ropas.y ) {
					serpent.add(new Cellule(serpent.get(serpent.size()-1).x, serpent.get(serpent.size()-1).y, bodyrcolor));
					score+=10;
					init_ropas();
				}
				
				// condtion d arrete 
				for(int i=1;i<serpent.size();i++) {
					if(serpent.get(0).x==serpent.get(i).x && serpent.get(0).y==serpent.get(i).y) {
						horloge.stop();	
						int option = JOptionPane.showConfirmDialog(null, "Your Score is " + score + "\n Do you want to repeat the game?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if (option == JOptionPane.YES_OPTION) {
							 resetGame();
							    horloge.start();
						} else {
						    Serpent.this.dispose(); // Close the current frame
						}
						}
					
				}
				scoreLabel.setText("Score: " + score);
				repaint();
			}
			
		} );
		horloge.start();
		
		this.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode()==KeyEvent.VK_RIGHT && !direction.equals("l"))
					direction="r";
				if(e.getKeyCode()==KeyEvent.VK_LEFT && !direction.equals("r"))
					direction="l";
				if(e.getKeyCode()==KeyEvent.VK_UP && !direction.equals("d") )
					direction="u";
				if(e.getKeyCode()==KeyEvent.VK_DOWN && !direction.equals("u") )
					direction="d";
			}
		});
		panel.add(scoreLabel);
		this.setContentPane(panel);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		
		
		
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        int option = JOptionPane.showConfirmDialog(null, "Your Score is " + score + "\n Do you want Exit ?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		        if (option == JOptionPane.YES_OPTION) {
		        	Serpent.this.dispose(); 
		        	System.exit(0);
		        } else {
		        }
		    }
		});
		
	}
	
	
	private void init_ropas() {
		Random random = new Random();
		int x ;
		int y;
		boolean in_the_serpent=false;
		do {
			 in_the_serpent=false;
			 x = random.nextInt(nbCasesX) * largeur + margeX;
		     y = random.nextInt(nbCasesY) * largeur + margeY;
			for(Cellule c : serpent) {
				if(c.x==x && c.y==y) {
					in_the_serpent=true;
					break;
				}
				
			}
			
		}while(in_the_serpent==true);
		ropas = new Cellule(x, y, ropasrcolor);
		
	}
	
	private void resetGame() {
	    
	    serpent.clear(); 
	    for (int i = 5; i > 0; i--) {
	        Cellule cellule = new Cellule(margeX + i * largeur, margeY, bodyrcolor);
	        serpent.add(cellule);
	    }
	    serpent.get(0).color = headrcolor;

	    init_ropas();

	    direction = "r";
	    score =0;
	}
	public static void main(String[] args) {
		Serpent serpent =new Serpent() ;
	}

}


