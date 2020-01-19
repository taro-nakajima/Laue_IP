import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.RenderingHints;

public class IPmap extends Canvas implements MouseInputListener{

    public Lattice LATTICE;

	public String fname,pathname;
    public Image photo;
    public ImageIcon photoicon;
	
	private Dimension photodim = new Dimension(400,400);
	private int padding1 = 10;
	private int lineheight = 15;

	public int Mouse_X1,Mouse_X2,Mouse_Y1,Mouse_Y2;
    
	


    public IPmap(){  // constractor
        LATTICE = new Lattice();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
    }
    
	

	public void loadFile(File file1){    // file reading method ==========================

		try{
			FileReader reader = new FileReader(file1);
			BufferedReader BR = new BufferedReader(reader);

            fname = file1.getName();
            pathname = file1.getParent();

		}
		catch(IOException e){
			System.out.println("error");
		}


        repaint();

	}
    
    public void setLattice(Lattice Lat){
        LATTICE = Lat;
        repaint();
    }
	
	public String getCurrPath(){
		return pathname;
	}
    
	public void update(Graphics g){
		paint(g);
	}

	public void paint(Graphics g){

		Dimension dim = getSize();
        Image bfimg;
        Graphics ofg;
        bfimg = createImage(dim.width,dim.height);
        ofg = bfimg.getGraphics();

		ofg.setColor(Color.darkGray);
		ofg.fillRect(0,0,dim.width,dim.height);

		ofg.setColor(Color.black);
		ofg.fillRect(0,0,photodim.width,photodim.height);

        if((pathname != null)&&(fname != null)){
            String fullpath = pathname+"/"+fname;
            ImageIcon photoicon = new ImageIcon(fullpath);
            photo = photoicon.getImage();
            ofg.drawImage(photo,0,0,null);
        }

        LATTICE.drawLaueSpots(ofg);

		ofg.setColor(new Color(80,80,80));
		ofg.fillRect(padding1/2,photodim.height+padding1/2,photodim.width-padding1/2,lineheight+padding1/2);
		ofg.setColor(Color.white);
		if(fname == null){
			ofg.drawString("Filename : No file selected!",padding1+0,photodim.height+lineheight+padding1/2);
		}
		else{
			ofg.drawString("Filename : "+fname,padding1+0,photodim.height+lineheight+padding1/2);
		}

		int Y1 = photodim.height+padding1/2 + lineheight+padding1/2;
		ofg.setColor(new Color(80,80,80));
		ofg.fillRect(padding1/2,Y1+padding1/2,photodim.width/3-padding1/2,lineheight*4+padding1);
		ofg.setColor(Color.white);
		Font font1 = new Font("Arial",Font.PLAIN,15);
		ofg.setFont(font1);
		ofg.drawString("-Gonio Rotation",padding1+0,Y1+lineheight+padding1/2);
		ofg.drawString(" omega : "+LATTICE.getOmega(),padding1+0,Y1+lineheight*2+padding1/2);
		ofg.drawString(" chi   : "+LATTICE.getChi(),padding1+0,Y1+lineheight*3+padding1/2);
		ofg.drawString(" phi   : "+LATTICE.getPhi(),padding1+0,Y1+lineheight*4+padding1/2);
        
		int X1 = photodim.width/3;
		ofg.setColor(new Color(80,80,80));
		ofg.fillRect(X1+padding1/2,Y1+padding1/2,photodim.width*2/3-padding1/2,lineheight*4+padding1);
		ofg.setColor(Color.white);
		ofg.drawString("-Lattice Parameters",X1+padding1+0,Y1+lineheight+padding1/2);
		ofg.drawString(" a : "+LATTICE.getLatticeA(),X1+padding1+0,Y1+lineheight*2+padding1/2);
		ofg.drawString(" b : "+LATTICE.getLatticeB(),X1+padding1+0,Y1+lineheight*3+padding1/2);
		ofg.drawString(" c : "+LATTICE.getLatticeC(),X1+padding1+0,Y1+lineheight*4+padding1/2);

		int X2 = photodim.width*2/3-padding1;
		ofg.drawString(" alpha : "+LATTICE.getLatticeAlpha(),X2+padding1+0,Y1+lineheight*2+padding1/2);
		ofg.drawString(" beta  : "+LATTICE.getLatticeBeta(),X2+padding1+0,Y1+lineheight*3+padding1/2);
		ofg.drawString(" gamma : "+LATTICE.getLatticeGamma(),X2+padding1+0,Y1+lineheight*4+padding1/2);
        
        int OX1 = photodim.width + (dim.width-photodim.width)/2;
        int OY1 = (dim.height)/4;
        ofg.setColor(new Color(80,80,80));
		ofg.fillRect(photodim.width+padding1/2,padding1/2,dim.width-photodim.width-padding1,dim.height/2-padding1/2);        

        ofg.setColor(Color.white);
        ofg.drawString("Reciprocal Lattice",photodim.width+padding1,padding1/2+lineheight);

        LATTICE.drawReciprocalLattice(ofg,OX1,OY1);
        
        int legend_width = 40;
        int legend_spacer = 200;
        ofg.setColor(Color.red);
        ofg.drawLine(photodim.width+padding1+legend_spacer,padding1/2+lineheight*2/3,
        photodim.width+padding1+legend_spacer+legend_width,padding1/2+lineheight*2/3);
        ofg.setColor(Color.white);
        ofg.drawString("a*",photodim.width+padding1*2+legend_spacer+legend_width,padding1/2+lineheight);
        ofg.setColor(Color.blue);
        ofg.drawLine(photodim.width+padding1+legend_spacer,padding1/2+lineheight*2/3+lineheight,
        photodim.width+padding1+legend_spacer+legend_width,padding1/2+lineheight*2/3+lineheight);
        ofg.setColor(Color.white);
        ofg.drawString("b*",photodim.width+padding1*2+legend_spacer+legend_width,padding1/2+lineheight*2);
        ofg.setColor(Color.green);
        ofg.drawLine(photodim.width+padding1+legend_spacer,padding1/2+lineheight*2/3+lineheight*2,
        photodim.width+padding1+legend_spacer+legend_width,padding1/2+lineheight*2/3+lineheight*2);
        ofg.setColor(Color.white);
        ofg.drawString("c*",photodim.width+padding1*2+legend_spacer+legend_width,padding1/2+lineheight*3);

        ofg.setColor(Color.white);
        ofg.drawLine(photodim.width+padding1+legend_width,padding1/2+lineheight*2/3+lineheight*2,
        photodim.width+padding1+legend_width*2,padding1/2+lineheight*2/3+lineheight*2);
        ofg.drawString("X-ray",photodim.width+padding1,padding1/2+lineheight*3);
        int arrow_width = 8;
        int triangle_x[] = {photodim.width+padding1+legend_width*2,photodim.width+padding1+legend_width*2-arrow_width,
        photodim.width+padding1+legend_width*2-arrow_width};
        int triangle_y[] = {padding1/2+lineheight*2/3+lineheight*2,padding1/2+lineheight*2/3+lineheight*2-arrow_width/2,
        padding1/2+lineheight*2/3+lineheight*2+arrow_width/2};
        ofg.fillPolygon(triangle_x,triangle_y,triangle_x.length);
		
        int OX2 = photodim.width + (dim.width-photodim.width)/2;
        int OY2 = (int)((double)(dim.height)/1.5);

        ofg.setColor(Color.white);
        ofg.drawString("Lattice",photodim.width+padding1,OY1*2+padding1/2+lineheight);

        LATTICE.drawDirectLattice(ofg,OX2,OY2);

        g.drawImage(bfimg,0,0,null);


	}
    
    public void outputImageFile(File outfile){
    
        BufferedImage BIMG = new BufferedImage(photodim.width,photodim.height,BufferedImage.TYPE_INT_BGR);
        Graphics screen = BIMG.createGraphics();
        
        paint(screen);

        
        try {
            ImageIO.write(BIMG, "png", outfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	
	//MouseInputListener methods
	public void mouseClicked(MouseEvent e){
	}
	public void mousePressed(MouseEvent e){
		Mouse_X1 = e.getX();
		Mouse_Y1 = e.getY();

	}
	public void mouseReleased(MouseEvent e){
	}
	public void mouseEntered(MouseEvent e){
	}
	public void mouseExited(MouseEvent e){
	}
	public void mouseDragged(MouseEvent e){

		if((Mouse_X1 <photodim.getWidth())&&(Mouse_Y1<photodim.getHeight())){
			Mouse_X2 = e.getX();
			Mouse_Y2 = e.getY();
		
			int dx = Mouse_X2-Mouse_X1;
			int dy = Mouse_Y2-Mouse_Y1;
		
			LATTICE.moveOrigin(dx,dy);
			repaint();
		
			Mouse_X1 = e.getX();
			Mouse_Y1 = e.getY();
		}

	}
	public void mouseMoved(MouseEvent e){
	}
	


	
}