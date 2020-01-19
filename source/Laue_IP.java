import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Laue_IP extends JFrame implements WindowListener,ActionListener,ItemListener,KeyListener,FocusListener{

    // original class
	IPmap IP = new IPmap();
    Lattice LATTICE = new Lattice();
    
    
	JPanel pnl[] = new JPanel[4];
	JButton but_save,but_display,but_file,but_setting,but_FRot,but_Gonio,but_lattice;
	JLabel lbfn;
    JComboBox<String> ColorCB,ReflectConditionCB,SymmetryCB;
    
	//Dialog for "Simulation setting"
	JDialog dlg_setting;
    JPanel dlg_setting_pnl;
    JButton dlg_setting_bt[] = new JButton[2];
    JLabel dlg_setting_lbl[] = new JLabel[11];
    JTextField dlg_setting_tf[] = new JTextField[12];

	//Dialog for "Display setting"
	JDialog dlg_display;
    JPanel dlg_display_pnl;
    JButton dlg_display_bt[] = new JButton[5];
    JLabel dlg_display_lbl[] = new JLabel[5];
    JTextField dlg_display_tf[] = new JTextField[4];
    
	//Dialog for "Free Rotation"
	JDialog dlg_FRot;
    JPanel dlg_FRot_pnl;
    JButton dlg_FRot_bt[] = new JButton[7];
    JLabel dlg_FRot_lbl[] = new JLabel[7];
    JTextField dlg_FRot_tf[] = new JTextField[6];

	//Dialog for "Gonio Rotation"
	JDialog dlg_Gonio;
    JPanel dlg_Gonio_pnl;
    JButton dlg_Gonio_bt[] = new JButton[6];
    JLabel dlg_Gonio_lbl[] = new JLabel[3];
    JTextField dlg_Gonio_tf[] = new JTextField[3];

    //Dialog for "Set lattice"
	JDialog dlg_Lattice;
    JPanel dlg_lattice_pnl;
    JButton dlg_lattice_bt[] = new JButton[2];
    JLabel dlg_lattice_lbl[] = new JLabel[6];
    JTextField dlg_lattice_tf[] = new JTextField[4];

    
	String curr_path = "";

	

    
	JFileChooser filechooser,filechooser_save;

	int drawmode = 0;

	public Laue_IP(){	//
		super("Laue-IP ver3.1.1");
		setSize(890,550);
		addWindowListener(this);
		addKeyListener(this);
		addFocusListener(this);
		this.setFocusable(true);

		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());
		cont.setFocusable(true);

		

		//JPanel 
		for(int i=0;i<pnl.length;i++){
			pnl[i] = new JPanel();
		}

		//IntMap
		IP.setSize(690,650);
		IP.setFocusable(false);
        IP.setLattice(LATTICE);
		//pnl[0].setBounds(BorderLayout.CENTER);
		pnl[0].add(IP,BorderLayout.CENTER);
		//

		//JButton
		but_file = new JButton(" Load Image File ");
		but_file.addActionListener(this);
		
		but_save = new JButton(" Save Image File ");
		but_save.addActionListener(this);
        
		but_setting = new JButton(" Simulation Setting ");
		but_setting.addActionListener(this);

		but_display = new JButton(" Display Setting ");
		but_display.addActionListener(this);

		but_FRot = new JButton(" Free Rotation ");
		but_FRot.addActionListener(this);

		but_Gonio = new JButton(" Gonio Rotation ");
		but_Gonio.addActionListener(this);
        
        but_lattice = new JButton(" Set lattice ");
        but_lattice.addActionListener(this);
        
		pnl[1].setLayout(new GridLayout(7,1));

		pnl[1].add(but_file);
		pnl[1].add(but_save);
		pnl[1].add(but_setting);
		pnl[1].add(but_display);
		pnl[1].add(but_FRot);
		pnl[1].add(but_Gonio);
		pnl[1].add(but_lattice);
        
        
		//JDialog for Simulation setting ============================
		dlg_setting = new JDialog(this,"Laue Spot Calculation ");
        dlg_setting.setSize(320,450);
        Container dlg_setting_cnt = dlg_setting.getContentPane();
		dlg_setting_cnt.setLayout(new BorderLayout());
        dlg_setting_pnl = new JPanel();
//		dlg_setting_pnl.setLayout(new GridLayout(2,4));
		dlg_setting_pnl.setLayout(null);
        int dlg_padding_x = 40;
        int dlg_padding_y = 40;
        int lbl_width = 70;
        int tf_width = 40;
        int com_height = 30;

        dlg_setting_lbl[0] = new JLabel("H range : ");
        dlg_setting_lbl[0].setBounds(dlg_padding_x,dlg_padding_y,lbl_width,com_height);
        dlg_setting_lbl[1] = new JLabel("   -");
        dlg_setting_lbl[1].setBounds(dlg_padding_x+tf_width+lbl_width,dlg_padding_y,lbl_width/2,com_height);
        dlg_setting_tf[0] = new JTextField("-20");
        dlg_setting_tf[0].setBounds(dlg_padding_x+lbl_width,dlg_padding_y,tf_width,com_height);
        dlg_setting_tf[1] = new JTextField("20");
        dlg_setting_tf[1].setBounds(dlg_padding_x+lbl_width+tf_width*2,dlg_padding_y,tf_width,com_height);


        dlg_setting_lbl[2] = new JLabel("K range : ");
        dlg_setting_lbl[2].setBounds(dlg_padding_x,dlg_padding_y*2,lbl_width,com_height);
        dlg_setting_lbl[3] = new JLabel("   -");
        dlg_setting_lbl[3].setBounds(dlg_padding_x+tf_width+lbl_width,dlg_padding_y*2,lbl_width/2,com_height);
        dlg_setting_tf[2] = new JTextField("-20");
        dlg_setting_tf[2].setBounds(dlg_padding_x+lbl_width,dlg_padding_y*2,tf_width,com_height);
        dlg_setting_tf[3] = new JTextField("20");
        dlg_setting_tf[3].setBounds(dlg_padding_x+lbl_width+tf_width*2,dlg_padding_y*2,tf_width,com_height);

        dlg_setting_lbl[4] = new JLabel("L range : ");
        dlg_setting_lbl[4].setBounds(dlg_padding_x,dlg_padding_y*3,lbl_width,com_height);
        dlg_setting_lbl[5] = new JLabel("   -");
        dlg_setting_lbl[5].setBounds(dlg_padding_x+tf_width+lbl_width,dlg_padding_y*3,lbl_width/2,com_height);
        dlg_setting_tf[4] = new JTextField("-20");
        dlg_setting_tf[4].setBounds(dlg_padding_x+lbl_width,dlg_padding_y*3,tf_width,com_height);
        dlg_setting_tf[5] = new JTextField("20");
        dlg_setting_tf[5].setBounds(dlg_padding_x+lbl_width+tf_width*2,dlg_padding_y*3,tf_width,com_height);


        dlg_setting_lbl[6] = new JLabel("Lambda min (A): ");
        dlg_setting_lbl[6].setBounds(dlg_padding_x,dlg_padding_y*4,lbl_width*2,com_height);
        dlg_setting_tf[6] = new JTextField("0.37");
        dlg_setting_tf[6].setBounds(dlg_padding_x+lbl_width+tf_width*2,dlg_padding_y*4,tf_width,com_height);

        dlg_setting_lbl[7] = new JLabel("S-F Distance (mm) : ");
        dlg_setting_lbl[7].setBounds(dlg_padding_x,dlg_padding_y*5,lbl_width*2,com_height);
        dlg_setting_tf[7] = new JTextField("40");
        dlg_setting_tf[7].setBounds(dlg_padding_x+lbl_width+tf_width*2,dlg_padding_y*5,tf_width,com_height);

        dlg_setting_lbl[8] = new JLabel("Film size (mm) ");
        dlg_setting_lbl[8].setBounds(dlg_padding_x,dlg_padding_y*6,lbl_width*2,com_height);
        dlg_setting_tf[8] = new JTextField("115");
        dlg_setting_tf[8].setBounds(dlg_padding_x+lbl_width+tf_width*2,dlg_padding_y*6,tf_width,com_height);

		int lbl_width2 = 120;
        dlg_setting_lbl[9] = new JLabel("Target (H,K,L)");
        dlg_setting_lbl[9].setBounds(dlg_padding_x,dlg_padding_y*7,lbl_width2,com_height);
        dlg_setting_tf[9] = new JTextField("1");
        dlg_setting_tf[9].setBounds(dlg_padding_x+lbl_width2,dlg_padding_y*7,lbl_width/2,com_height);
        dlg_setting_tf[10] = new JTextField("1");
        dlg_setting_tf[10].setBounds(dlg_padding_x+lbl_width2+lbl_width/2,dlg_padding_y*7,lbl_width/2,com_height);
        dlg_setting_tf[11] = new JTextField("0");
        dlg_setting_tf[11].setBounds(dlg_padding_x+lbl_width2+lbl_width,dlg_padding_y*7,lbl_width/2,com_height);

        dlg_setting_lbl[10] = new JLabel("Color :");
        dlg_setting_lbl[10].setBounds(dlg_padding_x,dlg_padding_y*8,lbl_width2,com_height);
        ColorCB = new JComboBox<String>();
        ColorCB.addItem("green");
        ColorCB.addItem("blue");
        ColorCB.setBounds(dlg_padding_x+lbl_width2,dlg_padding_y*8,lbl_width2,com_height);
        ColorCB.addActionListener(this);

        int X1=dlg_padding_x;
		int Y1=dlg_padding_y*8;
        int but_width = 120;
        dlg_setting_bt[0] = new JButton("cancel");
        dlg_setting_bt[0].setBounds(X1,Y1+dlg_padding_y,but_width,com_height);
		dlg_setting_bt[0].addActionListener(this);
        dlg_setting_bt[1] = new JButton("set");
        dlg_setting_bt[1].setBounds(X1+but_width,Y1+dlg_padding_y,but_width,com_height);
		dlg_setting_bt[1].addActionListener(this);
		        

        for(int i=0;i<dlg_setting_lbl.length;i++){
            dlg_setting_pnl.add(dlg_setting_lbl[i]);
        }

		for(int i=0;i<dlg_setting_tf.length;i++){
			dlg_setting_pnl.add(dlg_setting_tf[i]);
		}
        
        for(int i=0;i<dlg_setting_bt.length;i++){
            dlg_setting_pnl.add(dlg_setting_bt[i]);
        }
        dlg_setting_pnl.add(ColorCB);
        
        dlg_setting_cnt.add(dlg_setting_pnl,BorderLayout.CENTER);
		//JDialog for Dialog for Simulation setting end =======================

		//JDialog for Dsiplay setting=================================================================
		dlg_display = new JDialog(this," Display Setting ");
        dlg_display.setSize(320,300);
        Container dlg_display_cnt = dlg_display.getContentPane();
		dlg_display_cnt.setLayout(new BorderLayout());
        dlg_display_pnl = new JPanel();
//		dlg_setting_pnl.setLayout(new GridLayout(2,4));
		dlg_display_pnl.setLayout(null);

		int Disp_bt_width = 70;
		int Disp_lbl_width = 50;
		int Disp_tf_width = 40;

		dlg_display_lbl[0] = new JLabel(" Beam center position :");
        dlg_display_lbl[0].setBounds(dlg_padding_x,dlg_padding_y,Disp_lbl_width*3,com_height);

		dlg_display_lbl[1] = new JLabel(" X :");
        dlg_display_lbl[1].setBounds(dlg_padding_x,dlg_padding_y*2,Disp_lbl_width,com_height);
        dlg_display_tf[0] = new JTextField("1");
        dlg_display_tf[0].setBounds(dlg_padding_x+Disp_lbl_width,dlg_padding_y*2,Disp_tf_width,com_height);
        dlg_display_bt[0] = new JButton("Jog+");
        dlg_display_bt[0].setBounds(dlg_padding_x+Disp_lbl_width+Disp_tf_width,dlg_padding_y*2,Disp_bt_width,com_height);
        dlg_display_bt[1] = new JButton("Jog-");
        dlg_display_bt[1].setBounds(dlg_padding_x+Disp_lbl_width+Disp_tf_width+Disp_bt_width,dlg_padding_y*2,Disp_bt_width,com_height);

		dlg_display_lbl[2] = new JLabel(" Z :");
        dlg_display_lbl[2].setBounds(dlg_padding_x,dlg_padding_y*3,Disp_lbl_width,com_height);
        dlg_display_tf[1] = new JTextField("1");
        dlg_display_tf[1].setBounds(dlg_padding_x+Disp_lbl_width,dlg_padding_y*3,Disp_tf_width,com_height);
        dlg_display_bt[2] = new JButton("Jog+");
        dlg_display_bt[2].setBounds(dlg_padding_x+Disp_lbl_width+Disp_tf_width,dlg_padding_y*3,Disp_bt_width,com_height);
        dlg_display_bt[3] = new JButton("Jog-");
        dlg_display_bt[3].setBounds(dlg_padding_x+Disp_lbl_width+Disp_tf_width+Disp_bt_width,dlg_padding_y*3,Disp_bt_width,com_height);

        dlg_display_lbl[3] = new JLabel("Offset : X ");
        dlg_display_lbl[3].setBounds(dlg_padding_x,dlg_padding_y*4+dlg_padding_y/2,lbl_width,com_height);
        dlg_display_lbl[4] = new JLabel("    Z ");
        dlg_display_lbl[4].setBounds(dlg_padding_x+tf_width+lbl_width,dlg_padding_y*4+dlg_padding_y/2,lbl_width/2,com_height);
        dlg_display_tf[2] = new JTextField("0");
        dlg_display_tf[2].setBounds(dlg_padding_x+lbl_width,dlg_padding_y*4+dlg_padding_y/2,tf_width,com_height);
        dlg_display_tf[3] = new JTextField("0");
        dlg_display_tf[3].setBounds(dlg_padding_x+lbl_width+tf_width*2,dlg_padding_y*4+dlg_padding_y/2,tf_width,com_height);

        dlg_display_bt[4] = new JButton(" Set offset ");
        dlg_display_bt[4].setBounds(dlg_padding_x,dlg_padding_y*5+dlg_padding_y/2,Disp_bt_width*3,com_height);

        for(int i=0;i<dlg_display_lbl.length;i++){
            dlg_display_pnl.add(dlg_display_lbl[i]);
        }

		for(int i=0;i<dlg_display_tf.length;i++){
			dlg_display_pnl.add(dlg_display_tf[i]);
		}
        
        for(int i=0;i<dlg_display_bt.length;i++){
			dlg_display_bt[i].addActionListener(this);
            dlg_display_pnl.add(dlg_display_bt[i]);
        }

        
        dlg_display_cnt.add(dlg_display_pnl,BorderLayout.CENTER);
		//JDialog for Display setting end ========================================

		//JDialog for Free Rotation==========================================
		dlg_FRot = new JDialog(this,"Free Rotation");
        dlg_FRot.setSize(350,400);
        Container dlg_FRot_cnt = dlg_FRot.getContentPane();
		dlg_FRot_cnt.setLayout(new BorderLayout());
        dlg_FRot_pnl = new JPanel();
		dlg_FRot_pnl.setLayout(null);
		
		int FRot_bt_width = 70;
		int FRot_lbl_width = 100;
		int FRot_tf_width = 40;

		dlg_FRot_lbl[0] = new JLabel(" X-Rotation :");
        dlg_FRot_lbl[0].setBounds(dlg_padding_x,dlg_padding_y,FRot_lbl_width,com_height);
        dlg_FRot_tf[0] = new JTextField("1");
        dlg_FRot_tf[0].setBounds(dlg_padding_x+FRot_lbl_width,dlg_padding_y,FRot_tf_width,com_height);
        dlg_FRot_bt[0] = new JButton("Jog+");
        dlg_FRot_bt[0].setBounds(dlg_padding_x+FRot_lbl_width+FRot_tf_width,dlg_padding_y,FRot_bt_width,com_height);
        dlg_FRot_bt[1] = new JButton("Jog-");
        dlg_FRot_bt[1].setBounds(dlg_padding_x+FRot_lbl_width+FRot_tf_width+FRot_bt_width,dlg_padding_y,FRot_bt_width,com_height);

		dlg_FRot_lbl[1] = new JLabel(" Y-Rotation :");
        dlg_FRot_lbl[1].setBounds(dlg_padding_x,dlg_padding_y*2,FRot_lbl_width,com_height);
        dlg_FRot_tf[1] = new JTextField("1");
        dlg_FRot_tf[1].setBounds(dlg_padding_x+FRot_lbl_width,dlg_padding_y*2,FRot_tf_width,com_height);
        dlg_FRot_bt[2] = new JButton("Jog+");
        dlg_FRot_bt[2].setBounds(dlg_padding_x+FRot_lbl_width+FRot_tf_width,dlg_padding_y*2,FRot_bt_width,com_height);
        dlg_FRot_bt[3] = new JButton("Jog-");
        dlg_FRot_bt[3].setBounds(dlg_padding_x+FRot_lbl_width+FRot_tf_width+FRot_bt_width,dlg_padding_y*2,FRot_bt_width,com_height);

		dlg_FRot_lbl[2] = new JLabel(" Z-Rotation :");
        dlg_FRot_lbl[2].setBounds(dlg_padding_x,dlg_padding_y*3,FRot_lbl_width,com_height);
        dlg_FRot_tf[2] = new JTextField("1");
        dlg_FRot_tf[2].setBounds(dlg_padding_x+FRot_lbl_width,dlg_padding_y*3,FRot_tf_width,com_height);
        dlg_FRot_bt[4] = new JButton("Jog+");
        dlg_FRot_bt[4].setBounds(dlg_padding_x+FRot_lbl_width+FRot_tf_width,dlg_padding_y*3,FRot_bt_width,com_height);
        dlg_FRot_bt[5] = new JButton("Jog-");
        dlg_FRot_bt[5].setBounds(dlg_padding_x+FRot_lbl_width+FRot_tf_width+FRot_bt_width,dlg_padding_y*3,FRot_bt_width,com_height);

		dlg_FRot_lbl[3] = new JLabel(" - Set as -");
        dlg_FRot_lbl[3].setBounds(dlg_padding_x,dlg_padding_y*4,FRot_lbl_width,com_height);

		int FRot_tf_width2 = 120;
		dlg_FRot_lbl[4] = new JLabel(" Omega : ");
        dlg_FRot_lbl[4].setBounds(dlg_padding_x,dlg_padding_y*5,FRot_lbl_width,com_height);
        dlg_FRot_tf[3] = new JTextField("0");
        dlg_FRot_tf[3].setBounds(dlg_padding_x+FRot_lbl_width,dlg_padding_y*5,FRot_tf_width2,com_height);
		dlg_FRot_lbl[5] = new JLabel(" Chi : ");
        dlg_FRot_lbl[5].setBounds(dlg_padding_x,dlg_padding_y*6,FRot_lbl_width,com_height);
        dlg_FRot_tf[4] = new JTextField("0");
        dlg_FRot_tf[4].setBounds(dlg_padding_x+FRot_lbl_width,dlg_padding_y*6,FRot_tf_width2,com_height);
		dlg_FRot_lbl[6] = new JLabel(" Phi : ");
        dlg_FRot_lbl[6].setBounds(dlg_padding_x,dlg_padding_y*7,FRot_lbl_width,com_height);
        dlg_FRot_tf[5] = new JTextField("0");
        dlg_FRot_tf[5].setBounds(dlg_padding_x+FRot_lbl_width,dlg_padding_y*7,FRot_tf_width2,com_height);

		int FRot_bt_width2 = 200;
		dlg_FRot_bt[6] = new JButton(" Set to Gonio ");
        dlg_FRot_bt[6].setBounds(dlg_padding_x,dlg_padding_y*8,FRot_bt_width2,com_height);

        for(int i=0;i<dlg_FRot_lbl.length;i++){
            dlg_FRot_pnl.add(dlg_FRot_lbl[i]);
        }

        for(int i=0;i<dlg_FRot_tf.length;i++){
            dlg_FRot_pnl.add(dlg_FRot_tf[i]);
        }

        for(int i=0;i<dlg_FRot_bt.length;i++){
			dlg_FRot_bt[i].addActionListener(this);
            dlg_FRot_pnl.add(dlg_FRot_bt[i]);
        }
        dlg_FRot_cnt.add(dlg_FRot_pnl,BorderLayout.CENTER);

		// JDialog for Free Rotation ========================================= end

		//JDialog for Gonio Rotation==========================================
		dlg_Gonio = new JDialog(this,"Goniometer Rotation");
        dlg_Gonio.setSize(350,220);
        Container dlg_Gonio_cnt = dlg_Gonio.getContentPane();
		dlg_Gonio_cnt.setLayout(new BorderLayout());
        dlg_Gonio_pnl = new JPanel();
		dlg_Gonio_pnl.setLayout(null);
		
		int Gonio_bt_width = 70;
		int Gonio_lbl_width = 100;
		int Gonio_tf_width = 40;

		dlg_Gonio_lbl[0] = new JLabel(" Omega :");
        dlg_Gonio_lbl[0].setBounds(dlg_padding_x,dlg_padding_y,Gonio_lbl_width,com_height);
        dlg_Gonio_tf[0] = new JTextField("1");
        dlg_Gonio_tf[0].setBounds(dlg_padding_x+Gonio_lbl_width,dlg_padding_y,Gonio_tf_width,com_height);
        dlg_Gonio_bt[0] = new JButton("Jog+");
        dlg_Gonio_bt[0].setBounds(dlg_padding_x+Gonio_lbl_width+Gonio_tf_width,dlg_padding_y,Gonio_bt_width,com_height);
        dlg_Gonio_bt[1] = new JButton("Jog-");
        dlg_Gonio_bt[1].setBounds(dlg_padding_x+Gonio_lbl_width+Gonio_tf_width+Gonio_bt_width,dlg_padding_y,Gonio_bt_width,com_height);

		dlg_Gonio_lbl[1] = new JLabel(" Chi :");
        dlg_Gonio_lbl[1].setBounds(dlg_padding_x,dlg_padding_y*2,Gonio_lbl_width,com_height);
        dlg_Gonio_tf[1] = new JTextField("1");
        dlg_Gonio_tf[1].setBounds(dlg_padding_x+Gonio_lbl_width,dlg_padding_y*2,Gonio_tf_width,com_height);
        dlg_Gonio_bt[2] = new JButton("Jog+");
        dlg_Gonio_bt[2].setBounds(dlg_padding_x+Gonio_lbl_width+Gonio_tf_width,dlg_padding_y*2,Gonio_bt_width,com_height);
        dlg_Gonio_bt[3] = new JButton("Jog-");
        dlg_Gonio_bt[3].setBounds(dlg_padding_x+Gonio_lbl_width+Gonio_tf_width+Gonio_bt_width,dlg_padding_y*2,Gonio_bt_width,com_height);

		dlg_Gonio_lbl[2] = new JLabel(" Phi :");
        dlg_Gonio_lbl[2].setBounds(dlg_padding_x,dlg_padding_y*3,Gonio_lbl_width,com_height);
        dlg_Gonio_tf[2] = new JTextField("1");
        dlg_Gonio_tf[2].setBounds(dlg_padding_x+Gonio_lbl_width,dlg_padding_y*3,Gonio_tf_width,com_height);
        dlg_Gonio_bt[4] = new JButton("Jog+");
        dlg_Gonio_bt[4].setBounds(dlg_padding_x+Gonio_lbl_width+Gonio_tf_width,dlg_padding_y*3,Gonio_bt_width,com_height);
        dlg_Gonio_bt[5] = new JButton("Jog-");
        dlg_Gonio_bt[5].setBounds(dlg_padding_x+Gonio_lbl_width+Gonio_tf_width+Gonio_bt_width,dlg_padding_y*3,Gonio_bt_width,com_height);

        for(int i=0;i<dlg_Gonio_lbl.length;i++){
            dlg_Gonio_pnl.add(dlg_Gonio_lbl[i]);
        }

        for(int i=0;i<dlg_Gonio_tf.length;i++){
            dlg_Gonio_pnl.add(dlg_Gonio_tf[i]);
        }

        for(int i=0;i<dlg_Gonio_bt.length;i++){
			dlg_Gonio_bt[i].addActionListener(this);
            dlg_Gonio_pnl.add(dlg_Gonio_bt[i]);
        }
        dlg_Gonio_cnt.add(dlg_Gonio_pnl,BorderLayout.CENTER);
		//JDialog for Gonio Rotation=========================================
        
        
        //JDialog for Set lattice ==========================================
		dlg_Lattice = new JDialog(this,"Set Lattice constants");
        dlg_Lattice.setSize(450,280);
        Container dlg_lattice_cnt = dlg_Lattice.getContentPane();
		dlg_lattice_cnt.setLayout(new BorderLayout());
        dlg_lattice_pnl = new JPanel();
		dlg_lattice_pnl.setLayout(null);
		
		int lattice_bt_width = 120;
		int lattice_lbl_width = 60;
		int lattice_tf_width = 50;
        int lattice_lbl_width2 = 70;  // label for combobox
        int lattice_cb_width = 260;

		dlg_lattice_lbl[0] = new JLabel(" a :");
        dlg_lattice_lbl[0].setBounds(dlg_padding_x,dlg_padding_y,lattice_lbl_width,com_height);
        dlg_lattice_tf[0] = new JTextField("3.8");
        dlg_lattice_tf[0].setBounds(dlg_padding_x+lattice_lbl_width,dlg_padding_y,lattice_tf_width,com_height);

		dlg_lattice_lbl[1] = new JLabel(" b :");
        dlg_lattice_lbl[1].setBounds(dlg_padding_x+lattice_lbl_width+lattice_tf_width,dlg_padding_y,lattice_lbl_width,com_height);
        dlg_lattice_tf[1] = new JTextField("3.8");
        dlg_lattice_tf[1].setBounds(dlg_padding_x+lattice_lbl_width*2+lattice_tf_width,dlg_padding_y,lattice_tf_width,com_height);

		dlg_lattice_lbl[2] = new JLabel(" c :");
        dlg_lattice_lbl[2].setBounds(dlg_padding_x+lattice_lbl_width*2+lattice_tf_width*2,dlg_padding_y,lattice_lbl_width,com_height);
        dlg_lattice_tf[2] = new JTextField("3.8");
        dlg_lattice_tf[2].setBounds(dlg_padding_x+lattice_lbl_width*3+lattice_tf_width*2,dlg_padding_y,lattice_tf_width,com_height);

		dlg_lattice_lbl[3] = new JLabel("beta or gamma:");
        dlg_lattice_lbl[3].setBounds(dlg_padding_x,dlg_padding_y*2,lattice_lbl_width*2,com_height);
        dlg_lattice_tf[3] = new JTextField("90");
        dlg_lattice_tf[3].setBounds(dlg_padding_x+lattice_lbl_width*2,dlg_padding_y*2,lattice_tf_width,com_height);
		
/*		dlg_lattice_lbl[4] = new JLabel(" beta :");
        dlg_lattice_lbl[4].setBounds(dlg_padding_x+lattice_lbl_width+lattice_tf_width,dlg_padding_y*2,lattice_lbl_width,com_height);
        dlg_lattice_tf[4] = new JTextField("90");
        dlg_lattice_tf[4].setBounds(dlg_padding_x+lattice_lbl_width*2+lattice_tf_width,dlg_padding_y*2,lattice_tf_width,com_height);
		
		dlg_lattice_lbl[5] = new JLabel(" gamma:");
        dlg_lattice_lbl[5].setBounds(dlg_padding_x+lattice_lbl_width*2+lattice_tf_width*2,dlg_padding_y*2,lattice_lbl_width,com_height);
        dlg_lattice_tf[5] = new JTextField("90");
        dlg_lattice_tf[5].setBounds(dlg_padding_x+lattice_lbl_width*3+lattice_tf_width*2,dlg_padding_y*2,lattice_tf_width,com_height);
*/
        dlg_lattice_lbl[4] = new JLabel("symmetry :");
        dlg_lattice_lbl[4].setBounds(dlg_padding_x,dlg_padding_y*3,lattice_lbl_width2,com_height);
        
        SymmetryCB = new JComboBox<String>();
        SymmetryCB.addItem("Cubic,Ortho,Tetra: alpha=beta=gamma=90");
        SymmetryCB.addItem("Monoclinic: alpha=gamma=90");
        SymmetryCB.addItem("Hexagonal: alpha=beta=90");
        
        SymmetryCB.setBounds(dlg_padding_x+lattice_lbl_width*2,dlg_padding_y*3,lattice_cb_width,com_height);
        SymmetryCB.addActionListener(this);
        
        dlg_lattice_lbl[5] = new JLabel("condition :");
        dlg_lattice_lbl[5].setBounds(dlg_padding_x,dlg_padding_y*4,lattice_lbl_width2,com_height);

        ReflectConditionCB = new JComboBox<String>();
        ReflectConditionCB.addItem("none");
        ReflectConditionCB.addItem("H+K=2n");
        ReflectConditionCB.addItem("H+L=2n");
        ReflectConditionCB.addItem("K+L=2n");
        ReflectConditionCB.addItem("H+K+L=2n");
        ReflectConditionCB.addItem("H,K,L all even or odd");
        ReflectConditionCB.addItem("-H+K+L=3n");
        
        ReflectConditionCB.setBounds(dlg_padding_x+lattice_lbl_width*2,dlg_padding_y*4,lattice_cb_width,com_height);
        ReflectConditionCB.addActionListener(this);

        dlg_lattice_bt[0] = new JButton("close");
        dlg_lattice_bt[0].setBounds(dlg_padding_x,dlg_padding_y*5,lattice_bt_width,com_height);
		dlg_lattice_bt[0].addActionListener(this);
        dlg_lattice_bt[1] = new JButton("set");
        dlg_lattice_bt[1].setBounds(dlg_padding_x+lattice_bt_width,dlg_padding_y*5,lattice_bt_width,com_height);
		dlg_lattice_bt[1].addActionListener(this);
		
		for(int i=0;i<dlg_lattice_lbl.length;i++){
            dlg_lattice_pnl.add(dlg_lattice_lbl[i]);
        }
		for(int i=0;i<dlg_lattice_tf.length;i++){
            dlg_lattice_pnl.add(dlg_lattice_tf[i]);
        }
        for(int i=0;i<dlg_lattice_bt.length;i++){
            dlg_lattice_pnl.add(dlg_lattice_bt[i]);
        }

        dlg_lattice_pnl.add(SymmetryCB);
        dlg_lattice_pnl.add(ReflectConditionCB);
		
		dlg_lattice_cnt.add(dlg_lattice_pnl,BorderLayout.CENTER);
		//JDialog for Set lattice=========================================
        

		cont.add(pnl[0],BorderLayout.CENTER);
		cont.add(pnl[1], BorderLayout.EAST);
		
		

	}

	public static void main(String args[]){
		Laue_IP window = new Laue_IP();
		window.setVisible(true);
	//	window.setFocusable(true);

	}

	//ActionListener methods-------------------------------------
	public void actionPerformed(ActionEvent e){
        if(e.getSource() == but_file){
			filechooser = new JFileChooser(IP.getCurrPath());
			filechooser.addActionListener(this);
			filechooser.showOpenDialog(this);

		}
		else if(e.getSource() == filechooser){
			if(filechooser.getSelectedFile() != null){

            IP.loadFile(filechooser.getSelectedFile());
				
			}
		}
		else if(e.getSource() == but_save){
			filechooser_save = new JFileChooser(IP.getCurrPath());
			filechooser_save.addActionListener(this);
			filechooser_save.showSaveDialog(this);

		}
		else if(e.getSource() == filechooser_save){
			if(filechooser_save.getSelectedFile() != null){
                if(filechooser_save.getSelectedFile().exists() == true){
                    int reply = JOptionPane.showConfirmDialog(this, "The file already exists. replace?","Warnning", JOptionPane.YES_NO_OPTION);
                    if(reply == JOptionPane.YES_OPTION){
                        IP.outputImageFile(filechooser_save.getSelectedFile());                
                    }
                }
                else{
                    IP.outputImageFile(filechooser_save.getSelectedFile());                
                }
			}
		}
		else if(e.getSource() == but_setting){
			dlg_setting.setVisible(true);
		}
		else if(e.getSource() == but_display){
			dlg_display.setVisible(true);
		}
		else if(e.getSource() == but_FRot){
			dlg_FRot.setVisible(true);
		}
		else if(e.getSource() == but_Gonio){
			dlg_Gonio.setVisible(true);
		}
		else if(e.getSource() == but_lattice){
			dlg_Lattice.setVisible(true);
		}
		else if(e.getSource() == dlg_setting_bt[0]){
			dlg_setting.setVisible(false);
		}
		else if(e.getSource() == dlg_setting_bt[1]){
            int h_min = (int)Double.parseDouble(dlg_setting_tf[0].getText());
            int h_max = (int)Double.parseDouble(dlg_setting_tf[1].getText());
            int k_min = (int)Double.parseDouble(dlg_setting_tf[2].getText());
            int k_max = (int)Double.parseDouble(dlg_setting_tf[3].getText());
            int l_min = (int)Double.parseDouble(dlg_setting_tf[4].getText());
            int l_max = (int)Double.parseDouble(dlg_setting_tf[5].getText());
            LATTICE.setHKLrange(h_max,h_min,k_max,k_min,l_max,l_min);
            
            double lam_min = Double.parseDouble(dlg_setting_tf[6].getText());
            LATTICE.setLambdaMin(lam_min);
            
            double dist_mm = Double.parseDouble(dlg_setting_tf[7].getText());
            LATTICE.setDistance(dist_mm);
            
            double film_mm = Double.parseDouble(dlg_setting_tf[8].getText());
            LATTICE.setRealFilmSize(film_mm);

			int H_HL = (int)Double.parseDouble(dlg_setting_tf[9].getText());
			int K_HL = (int)Double.parseDouble(dlg_setting_tf[10].getText());
			int L_HL = (int)Double.parseDouble(dlg_setting_tf[11].getText());
			LATTICE.setHighlightHKL(H_HL,K_HL,L_HL);
            
            switch(ColorCB.getSelectedIndex()){
                case 0:
                    LATTICE.setSpotColor(Color.green);
                    break;
                case 1:
                    LATTICE.setSpotColor(Color.blue);
                    break;
                default:
                    LATTICE.setSpotColor(Color.green);
                    break;
            }

            IP.setLattice(LATTICE);

		}
		else if(e.getSource() == dlg_display_bt[0]){   // beam center X jog+
			int dX = (int)Double.parseDouble(dlg_display_tf[0].getText());
			LATTICE.moveOrigin(dX,0);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_display_bt[1]){   // beam center X jog-
			int dX = -(int)Double.parseDouble(dlg_display_tf[0].getText());
			LATTICE.moveOrigin(dX,0);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_display_bt[2]){   // beam center Z jog+
			int dZ = -(int)Double.parseDouble(dlg_display_tf[1].getText());
			LATTICE.moveOrigin(0,dZ);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_display_bt[3]){   // beam center Z jog-
			int dZ = (int)Double.parseDouble(dlg_display_tf[1].getText());
			LATTICE.moveOrigin(0,dZ);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_display_bt[4]){   // beam center Offset
			int Xo = (int)Double.parseDouble(dlg_display_tf[2].getText());
			int Zo = (int)Double.parseDouble(dlg_display_tf[3].getText());
			LATTICE.setOriginOffset(Xo,Zo);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_FRot_bt[0]){   // X rotation jog+
			double delta = Double.parseDouble(dlg_FRot_tf[0].getText());
			LATTICE.rotateX(delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_FRot_bt[1]){   // X rotation jog-
			double delta = Double.parseDouble(dlg_FRot_tf[0].getText());
			LATTICE.rotateX(-delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_FRot_bt[2]){  // Y rotation jog+
			double delta = Double.parseDouble(dlg_FRot_tf[1].getText());
			LATTICE.rotateY(delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_FRot_bt[3]){  // Y rotation jog-
			double delta = Double.parseDouble(dlg_FRot_tf[1].getText());
			LATTICE.rotateY(-delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_FRot_bt[4]){  // Z rotation jog+
			double delta = Double.parseDouble(dlg_FRot_tf[2].getText());
			LATTICE.rotateZ(delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_FRot_bt[5]){  // Z rotation jog-
			double delta = Double.parseDouble(dlg_FRot_tf[2].getText());
			LATTICE.rotateZ(-delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_FRot_bt[6]){ // set to Gonio
			double omega = Double.parseDouble(dlg_FRot_tf[3].getText());
			double chi = Double.parseDouble(dlg_FRot_tf[4].getText());
			double phi = Double.parseDouble(dlg_FRot_tf[5].getText());
			
			LATTICE.setToGonio(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_Gonio_bt[0]){  // Gonio omega rotation jog+
			double omega = LATTICE.getOmega() + Double.parseDouble(dlg_Gonio_tf[0].getText());
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_Gonio_bt[1]){ // Gonio omega rotation jog-
			double omega = LATTICE.getOmega() - Double.parseDouble(dlg_Gonio_tf[0].getText());
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_Gonio_bt[2]){ // Gonio chi rotation jog+
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi() + Double.parseDouble(dlg_Gonio_tf[1].getText());
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_Gonio_bt[3]){  // Gonio chi rotation jog-
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi() - Double.parseDouble(dlg_Gonio_tf[1].getText());
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_Gonio_bt[4]){  // Gonio phi rotation jog+
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi() + Double.parseDouble(dlg_Gonio_tf[2].getText());
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_Gonio_bt[5]){ // Gonio phi rotation jog-
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi() - Double.parseDouble(dlg_Gonio_tf[2].getText());
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getSource() == dlg_lattice_bt[1]){    // set lattice [set]
			double a1 = Double.parseDouble(dlg_lattice_tf[0].getText());
			double b1 = Double.parseDouble(dlg_lattice_tf[1].getText());
			double c1 = Double.parseDouble(dlg_lattice_tf[2].getText());
			double betaORgamma = Double.parseDouble(dlg_lattice_tf[3].getText());
            
			LATTICE.setSymmetry(SymmetryCB.getSelectedIndex()); // this command should be called before setLatticeConst()
			LATTICE.setLatticeConst(a1,b1,c1,betaORgamma);
            LATTICE.setReflectionCondition(ReflectConditionCB.getSelectedIndex());
			IP.setLattice(LATTICE);

		}
		else if(e.getSource() == dlg_lattice_bt[0]){
			dlg_Lattice.setVisible(false);
		}
			
	}
	// the end of ActionListener Methods-------------------------

	// Item listener Methods------------------------------------
	public void itemStateChanged(ItemEvent e){

	}
	//the end of ItemListener Methods-----------------------------
	
	
    /*/ AdjustmentListener Methods
    public void adjustmentValueChanged(AdjustmentEvent e){
        if((e.getSource() == Sbar_omega)||(e.getSource() == Sbar_chi)||(e.getSource() == Sbar_phi)){
            LATTICE.setAngle(Sbar_omega.getValue(),Sbar_chi.getValue(),Sbar_phi.getValue());
            IP.setLattice(LATTICE);
        }
    
    }
*/


	//WindowListener methods
	public void windowActivated(WindowEvent e){
		this.requestFocus();
	}

	public void windowClosed(WindowEvent e){
	}

	public void windowClosing(WindowEvent e){
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent e){
	}

	public void windowDeiconified(WindowEvent e){
	}

	public void windowIconified(WindowEvent e){
	}

	public void windowOpened(WindowEvent e){
	}
	//WindowListener methods end

	// KeyListener Methods----------------------------------------
	public void keyPressed(KeyEvent e){

	}
	public void keyTyped(KeyEvent e){

		char C_X = 'X';		// key character for X + rotation
		char C_x = 'x';		// key character for X - rotation
		char C_Y = 'Y';		// key character for Y + rotation
		char C_y = 'y';		// key character for Y - rotation
		char C_Z = 'Z';		// key character for Z + rotation
		char C_z = 'z';		// key character for Z - rotation
		char C_O = 'O';		// key character for omega + rotation
		char C_o = 'o';		// key character for omega - rotation
		char C_C = 'C';		// key character for chi + rotation
		char C_c = 'c';		// key character for chi - rotation
		char C_P = 'P';		// key character for phi + rotation
		char C_p = 'p';		// key character for phi - rotation

		if(e.getKeyChar() == C_X){   // X rotation jog+
			double delta = Double.parseDouble(dlg_FRot_tf[0].getText());
			LATTICE.rotateX(delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_x){   // X rotation jog-
			double delta = Double.parseDouble(dlg_FRot_tf[0].getText());
			LATTICE.rotateX(-delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_Y){  // Y rotation jog+
			double delta = Double.parseDouble(dlg_FRot_tf[1].getText());
			LATTICE.rotateY(delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_y){  // Y rotation jog-
			double delta = Double.parseDouble(dlg_FRot_tf[1].getText());
			LATTICE.rotateY(-delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_Z){  // Z rotation jog+
			double delta = Double.parseDouble(dlg_FRot_tf[2].getText());
			LATTICE.rotateZ(delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_z){  // Z rotation jog-
			double delta = Double.parseDouble(dlg_FRot_tf[2].getText());
			LATTICE.rotateZ(-delta);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_O){  // Gonio omega rotation jog+
			double omega = LATTICE.getOmega() + Double.parseDouble(dlg_Gonio_tf[0].getText());
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_o){ // Gonio omega rotation jog-
			double omega = LATTICE.getOmega() - Double.parseDouble(dlg_Gonio_tf[0].getText());
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_C){ // Gonio chi rotation jog+
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi() + Double.parseDouble(dlg_Gonio_tf[1].getText());
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_c){  // Gonio chi rotation jog-
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi() - Double.parseDouble(dlg_Gonio_tf[1].getText());
			double phi = LATTICE.getPhi();
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_P){  // Gonio phi rotation jog+
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi() + Double.parseDouble(dlg_Gonio_tf[2].getText());
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else if(e.getKeyChar() == C_p){ // Gonio phi rotation jog-
			double omega = LATTICE.getOmega();
			double chi = LATTICE.getChi();
			double phi = LATTICE.getPhi() - Double.parseDouble(dlg_Gonio_tf[2].getText());
			
			LATTICE.setAngle(omega,chi,phi);
			IP.setLattice(LATTICE);
		}
		else{
			
		}
	
	}
	public void keyReleased(KeyEvent e){
//		System.out.println("released");		
	}
	
	
	//---------Methods of FocusListener (For debug)
	public void focusGained(FocusEvent e){
	//	System.out.println("Focus Gained ");
	}
	
	public void focusLost(FocusEvent e){
	//	System.out.println("Focus Lost ");
	}
	//---------end of Methods of FocusListener (For debug)

}