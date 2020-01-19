import java.awt.*;
import java.io.*;
import java.lang.*;
import javax.swing.*;


public class Lattice {


        public double[][] base_D,base_D2,base_R,base_R2;
        public double omega,chi,phi;
        public double a,b,c,alpha,beta,gamma;
        public int Hmax,Hmin,Kmax,Kmin,Lmax,Lmin;
		public int H_HL,K_HL,L_HL; // Highlight (H,K,L)
        public int Diameter;
		public int CenterCircle;
        public double distance;
        public int filmsize = 400;    // Laue image file should be [filmsize] pixel in both height and width.
        public double filmsize_R =115.0; // Real size of the imaing plate (mm).
        public int beamcenter_x,beamcenter_z,offset_x,offset_z;
        public double lambda_min;
        public double mm2pixel;
        public double Dscale,Rscale;
        public Color SpotColor;
        public int ReflectionCondition;
        public boolean IsReflectionObservable;
        public int Symmetry;
        
        public Lattice(){   // constractor

			//--- lattice const
            a = 3.8;
            b = 3.8;
            c = 3.8;
            alpha = 90.0;
            beta = 90.0;
            gamma = 90.0;
			//----end of lattice constant input
            Diameter = 8;
            distance = 40;
            Hmax = 20;
            Hmin = -20;
            Kmax = 20;
            Kmin = -20;
            Lmax = 20;
            Lmin = -20;
            beamcenter_x = filmsize/2;
            beamcenter_z = filmsize/2;
            lambda_min = 0.37;
            mm2pixel = (double)filmsize/filmsize_R;
			CenterCircle = 50;
			offset_x = 0;
			offset_z = 0;
			H_HL = 1;
			K_HL = 1;
			L_HL = 0;
            Rscale = 50;
			Dscale = 7;
            SpotColor = new Color(0,255,0);
            ReflectionCondition = 0;
            IsReflectionObservable = true;
            
        

            base_D = new double[3][3];
            base_R = new double[3][3];


            //set base
            base_D[0][0] = 0.0;
            base_D[0][1] = a;
            base_D[0][2] = 0.0;
      
            base_D[1][0] = -b;
            base_D[1][1] = 0.0;
            base_D[1][2] = 0.0;

            base_D[2][0] = 0.0;
            base_D[2][1] = 0.0;
            base_D[2][2] = c;


            //--- set reciprocal lattice bases
            base_R[0][0] = 0.0;
            base_R[0][1] = 2.0*Math.PI/a;
            base_R[0][2] = 0.0;
      
            base_R[1][0] = -2.0*Math.PI/b;
            base_R[1][1] = 0.0;
            base_R[1][2] = 0.0;
      
            base_R[2][0] = 0.0;
            base_R[2][1] = 0.0;
            base_R[2][2] = 2.0*Math.PI/c;

			base_D2 = new double[3][3];
            base_R2 = new double[3][3];
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
					base_D2[i][j] = base_D[i][j];
                    base_R2[i][j] = base_R[i][j];
                }
            }

            
            omega = 0.0;
            chi = 0.0;
            phi = 0.0;
            
        }

        public void setAngle(double a1,double a2,double a3){		//�����̉�]�p���Z�b�g����
        
            omega = a1;
            chi = a2;
            phi = a3;
			
            if(Math.abs(omega) < 1.0E-5){
                omega = 0.0;
            }
            if(Math.abs(chi) < 1.0E-5){
                chi = 0.0;
            }
            if(Math.abs(phi) < 1.0E-5){
                phi = 0.0;
            }
        
            double cos_omg = Math.cos(omega/180.0*Math.PI);
            double sin_omg = Math.sin(omega/180.0*Math.PI);

            double cos_chi = Math.cos(chi/180.0*Math.PI);
            double sin_chi = Math.sin(chi/180.0*Math.PI);

            double cos_phi = Math.cos(phi/180.0*Math.PI);
            double sin_phi = Math.sin(phi/180.0*Math.PI);
        
            for(int i=0;i<3;i++){
            //phi rotation 
                base_D2[i][0] = base_D[i][0]*cos_phi - base_D[i][2]*sin_phi;
                base_D2[i][1] = base_D[i][1];
                base_D2[i][2] = base_D[i][0]*sin_phi + base_D[i][2]*cos_phi;
                base_R2[i][0] = base_R[i][0]*cos_phi - base_R[i][2]*sin_phi;
                base_R2[i][1] = base_R[i][1];
                base_R2[i][2] = base_R[i][0]*sin_phi + base_R[i][2]*cos_phi;

				double temp_dx,temp_dy,temp_dz;		// temporary variable for direct space
                double temp_rx,temp_ry,temp_rz;		// temporary variable for reciprocal space
                
				temp_dx = base_D2[i][0];
				temp_dy = base_D2[i][1];
				temp_dz = base_D2[i][2];
                temp_rx = base_R2[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R2[i][1];
                temp_rz = base_R2[i][2];

            //chi rotation ()
                base_D2[i][2] = temp_dz*cos_chi - temp_dy*sin_chi;
                base_D2[i][1] = temp_dz*sin_chi + temp_dy*cos_chi;
                base_R2[i][2] = temp_rz*cos_chi - temp_ry*sin_chi;
                base_R2[i][1] = temp_rz*sin_chi + temp_ry*cos_chi;

                temp_dx = base_D2[i][0];     // temporary�ϐ��Ɋi�[
                temp_dy = base_D2[i][1];
                temp_dz = base_D2[i][2];
                temp_rx = base_R2[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R2[i][1];
                temp_rz = base_R2[i][2];

            //omega rotation ()
                base_D2[i][0] = temp_dx*cos_omg - temp_dy*sin_omg;
                base_D2[i][1] = temp_dx*sin_omg + temp_dy*cos_omg;
                base_R2[i][0] = temp_rx*cos_omg - temp_ry*sin_omg;
                base_R2[i][1] = temp_rx*sin_omg + temp_ry*cos_omg;
                

            }


        }
	
        public void setHKLrange(int maxH,int minH,int maxK,int minK,int maxL,int minL){
            Hmax = maxH;
            Hmin = minH;
            Kmax = maxK;
            Kmin = minK;
            Lmax = maxL;
            Lmin = minL;
        }
    
        public void setReflectionCondition(int condition){
            ReflectionCondition = condition;
        }
        
        public void setSymmetry(int Sym){
            Symmetry = Sym;
        }
        
        public void setLambdaMin(double Lmin){
            lambda_min = Lmin;
        }
        
        public void setDistance(double dist_mm){
            distance = dist_mm;
        }
        
        public void setOriginOffset(int Ox,int Oz){
            offset_x = Ox;
            offset_z = Oz;
        }
		
        public void setSpotColor(Color c){
            SpotColor = c;
        }
        
        public void setRealFilmSize(double rfmsz){
            filmsize_R = rfmsz;
            mm2pixel = (double)filmsize/filmsize_R;
        }

        public void moveOrigin(int dx,int dz){
			if(((beamcenter_x + dx)>=0)&&((beamcenter_x + dx)<=filmsize)){
				beamcenter_x = beamcenter_x + dx;
			}
			if(((beamcenter_z + dz)>=0)&&((beamcenter_z + dz)<=filmsize)){
				beamcenter_z = beamcenter_z + dz;
			}

		}
	
		public void setLatticeConst(double a1,double b1,double c1,double betaORgamma){
		
			a = a1;
			b = b1;
			c = c1;

			switch(Symmetry){
                //Symmetry 0.cubic,Ortho,Tetra 1.Monoclinic 2.Hexagonal,Trigonal
                //set base
                case 0:
                    alpha = 90.0;
                    beta = 90.0;
                    gamma = 90.0;

                    base_D[0][0] = 0.0;
                    base_D[0][1] = a;
                    base_D[0][2] = 0.0;
			
                    base_D[1][0] = -b;
                    base_D[1][1] = 0.0;
                    base_D[1][2] = 0.0;
			
                    base_D[2][0] = 0.0;
                    base_D[2][1] = 0.0;
                    base_D[2][2] = c;
			
			
                //--- set reciprocal lattice bases
                    base_R[0][0] = 0.0;
                    base_R[0][1] = 2.0*Math.PI/a;
                    base_R[0][2] = 0.0;
			
                    base_R[1][0] = -2.0*Math.PI/b;
                    base_R[1][1] = 0.0;
                    base_R[1][2] = 0.0;
			
                    base_R[2][0] = 0.0;
                    base_R[2][1] = 0.0;
                    base_R[2][2] = 2.0*Math.PI/c;
                
                break;
                
                case 1:
                    alpha = 90.0;
                    gamma = 90.0;
                    beta = betaORgamma;
                    
                    base_D[0][0] = 0.0;
                    base_D[0][1] = a;
                    base_D[0][2] = 0.0;
                
                    base_D[1][0] = -b;
                    base_D[1][1] = 0.0;
                    base_D[1][2] = 0.0;
                
                    base_D[2][0] = 0.0;
                    base_D[2][1] = -Math.sin((beta-90.0)/180.0*Math.PI)*c;
                    base_D[2][2] = Math.cos((beta-90.0)/180.0*Math.PI)*c;
                
                
                //--- set reciprocal lattice bases
                    base_R[0][0] = 0.0;
                    base_R[0][1] = 2.0*Math.PI/a;
                    base_R[0][2] = 2.0*Math.PI/b*Math.tan((beta-90.0)/180.0*Math.PI);
                
                    base_R[1][0] = -2.0*Math.PI/b;
                    base_R[1][1] = 0.0;
                    base_R[1][2] = 0.0;
                
                    base_R[2][0] = 0.0;
                    base_R[2][1] = 0.0;
                    base_R[2][2] = 2.0*Math.PI/c/Math.cos((beta-90.0)/180.0*Math.PI);
                    
                
                break;

					
                case 2:
                    alpha = 90.0;
                    beta = 90.0;
                    gamma = betaORgamma;
                    
                    base_D[0][0] = 0.0;
                    base_D[0][1] = a;
                    base_D[0][2] = 0.0;
					
                    base_D[1][0] = -b*Math.sin(gamma/180.0*Math.PI);
                    base_D[1][1] = b*Math.cos(gamma/180.0*Math.PI);
                    base_D[1][2] = 0.0;
					
                    base_D[2][0] = 0.0;
                    base_D[2][1] = 0.0;
                    base_D[2][2] = c;
					
					
					//--- set reciprocal lattice bases
                    base_R[0][0] = -2.0*Math.PI/a*Math.tan((gamma-90.0)/180.0*Math.PI);
                    base_R[0][1] = 2.0*Math.PI/a;
                    base_R[0][2] = 0.0;
					
                    base_R[1][0] = -2.0*Math.PI/b/Math.cos((gamma-90)/180.0*Math.PI);
                    base_R[1][1] = 0.0;
                    base_R[1][2] = 0.0;
					
                    base_R[2][0] = 0.0;
                    base_R[2][1] = 0.0;
                    base_R[2][2] = 2.0*Math.PI/c;
                    
					
					break;
                default:
                
                break;

            }
                
            base_D2 = new double[3][3];
            base_R2 = new double[3][3];
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
					base_D2[i][j] = base_D[i][j];
                    base_R2[i][j] = base_R[i][j];
                }
            }
			

            omega = 0.0;
            chi = 0.0;
            phi = 0.0;
            
			
		}
		
		public void rotateX(double dx){
			double temp_dx,temp_dy,temp_dz;
			double temp_rx,temp_ry,temp_rz;
                
				
			for(int i=0;i<3;i++){
                temp_dx = base_D2[i][0];     // temporary�ϐ��Ɋi�[
                temp_dy = base_D2[i][1];
                temp_dz = base_D2[i][2];
                temp_rx = base_R2[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R2[i][1];
                temp_rz = base_R2[i][2];

				double cos_x = Math.cos(dx/180.0*Math.PI);
				double sin_x = Math.sin(dx/180.0*Math.PI);

            //X rotation ()
                base_D2[i][2] = temp_dz*cos_x - temp_dy*sin_x;
                base_D2[i][1] = temp_dz*sin_x + temp_dy*cos_x;
                base_R2[i][2] = temp_rz*cos_x - temp_ry*sin_x;
                base_R2[i][1] = temp_rz*sin_x + temp_ry*cos_x;

			}

		}

		public void rotateY(double dy){
			double temp_dx,temp_dy,temp_dz;
			double temp_rx,temp_ry,temp_rz;                
				
			for(int i=0;i<3;i++){
                temp_dx = base_D2[i][0];     // temporary�ϐ��Ɋi�[
                temp_dy = base_D2[i][1];
                temp_dz = base_D2[i][2];
                temp_rx = base_R2[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R2[i][1];
                temp_rz = base_R2[i][2];

				double cos_y = Math.cos(dy/180.0*Math.PI);
				double sin_y = Math.sin(dy/180.0*Math.PI);

            //Y rotation ()
                base_D2[i][0] = temp_dx*cos_y - temp_dz*sin_y;
                base_D2[i][2] = temp_dx*sin_y + temp_dz*cos_y;
                base_R2[i][0] = temp_rx*cos_y - temp_rz*sin_y;
                base_R2[i][2] = temp_rx*sin_y + temp_rz*cos_y;

			}

		}

		public void rotateZ(double dz){
			double temp_dx,temp_dy,temp_dz;
			double temp_rx,temp_ry,temp_rz;                
				
			for(int i=0;i<3;i++){
                temp_dx = base_D2[i][0];     // temporary�ϐ��Ɋi�[
                temp_dy = base_D2[i][1];
                temp_dz = base_D2[i][2];
                temp_rx = base_R2[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R2[i][1];
                temp_rz = base_R2[i][2];

				double cos_z = Math.cos(dz/180.0*Math.PI);
				double sin_z = Math.sin(dz/180.0*Math.PI);

            //Z rotation ()
                base_D2[i][0] = temp_dx*cos_z - temp_dy*sin_z;
                base_D2[i][1] = temp_dx*sin_z + temp_dy*cos_z;
                base_R2[i][0] = temp_rx*cos_z - temp_ry*sin_z;
                base_R2[i][1] = temp_rx*sin_z + temp_ry*cos_z;

			}

		}
		
		public void setToGonio(double a1, double a2, double a3){
            omega = a1;
            chi = a2;
            phi = a3;
        
            double cos_omg = Math.cos(-omega/180.0*Math.PI);
            double sin_omg = Math.sin(-omega/180.0*Math.PI);

            double cos_chi = Math.cos(-chi/180.0*Math.PI);
            double sin_chi = Math.sin(-chi/180.0*Math.PI);

            double cos_phi = Math.cos(-phi/180.0*Math.PI);
            double sin_phi = Math.sin(-phi/180.0*Math.PI);
        
			double temp_dx,temp_dy,temp_dz;
			double temp_rx,temp_ry,temp_rz;
            for(int i=0;i<3;i++){
                
                temp_dx = base_D2[i][0];     // temporary�ϐ��Ɋi�[
                temp_dy = base_D2[i][1];
                temp_dz = base_D2[i][2];
                temp_rx = base_R2[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R2[i][1];
                temp_rz = base_R2[i][2];

            //omega rotation ()
                base_R[i][0] = temp_rx*cos_omg - temp_ry*sin_omg;
                base_R[i][1] = temp_rx*sin_omg + temp_ry*cos_omg;
				base_R[i][2] = temp_rz;
                base_D[i][0] = temp_dx*cos_omg - temp_dy*sin_omg;
                base_D[i][1] = temp_dx*sin_omg + temp_dy*cos_omg;
				base_D[i][2] = temp_dz;
				
                temp_rx = base_R[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R[i][1];
                temp_rz = base_R[i][2];
                temp_dx = base_D[i][0];     // temporary�ϐ��Ɋi�[
                temp_dy = base_D[i][1];
                temp_dz = base_D[i][2];

            //chi rotation ()
                base_R[i][2] = temp_rz*cos_chi - temp_ry*sin_chi;
                base_R[i][1] = temp_rz*sin_chi + temp_ry*cos_chi;
                base_D[i][2] = temp_dz*cos_chi - temp_dy*sin_chi;
                base_D[i][1] = temp_dz*sin_chi + temp_dy*cos_chi;

                temp_rx = base_R[i][0];     // temporary�ϐ��Ɋi�[
                temp_ry = base_R[i][1];
                temp_rz = base_R[i][2];
                temp_dx = base_D[i][0];     // temporary�ϐ��Ɋi�[
                temp_dy = base_D[i][1];
                temp_dz = base_D[i][2];
			
            //phi rotation 
                base_R[i][0] = temp_rx*cos_phi - temp_rz*sin_phi;
                base_R[i][2] = temp_rx*sin_phi + temp_rz*cos_phi;
                base_D[i][0] = temp_dx*cos_phi - temp_dz*sin_phi;
                base_D[i][2] = temp_dx*sin_phi + temp_dz*cos_phi;

			}
		
		}
		
		public void setHighlightHKL(int h,int k,int l){
			H_HL = h;
			K_HL = k;
			L_HL = l;
		}

        public double getOmega(){
            return omega;
        }
        
        public double getChi(){
            return chi;
        }
        
        public double getPhi(){
            return phi;
        }
        
		public double getLatticeA(){
			return a;
		}
		public double getLatticeB(){
			return b;
		}
		public double getLatticeC(){
			return c;
		}
		public double getLatticeAlpha(){
			return alpha;
		}
		public double getLatticeBeta(){
			return beta;
		}
		public double getLatticeGamma(){
			return gamma;
		}
		
        public void drawLaueSpots(Graphics g){
            g.setColor(SpotColor);

            for(int h=Hmin;h<=Hmax;h++){
                for(int k=Kmin;k<=Kmax;k++){
                    for(int l=Lmin;l<=Lmax;l++){
                        
                        //cast to double
                        double hd = (double)h;
                        double kd = (double)k;
                        double ld = (double)l;

                        //reflection condition
                        switch(ReflectionCondition){
                          // 0 "none"
                          // 1 "H+K=2n"
                          // 2 "H+L=2n"
                          // 3 "K+L=2n"
                          // 4 "H+K+L=2n"
                          // 5 "H,K,L all even or odd"
                          // 6 "-H+K+L=3n"

                            case 0:
                                IsReflectionObservable = true;
                                break;
                            case 1:
                               // System.out.println("case 1");
                                if((double)((h+k)/2)==(hd+kd)/2.0){
                                    IsReflectionObservable = true;
                                }
                                else{
                                    IsReflectionObservable = false;
                                }
                                break;
							case 2:
                                if((double)((h+l)/2)==(hd+ld)/2.0){
                                    IsReflectionObservable = true;
                                }
                                else{
                                    IsReflectionObservable = false;
                                }
								break;
							case 3:
                                if((double)((k+l)/2)==(kd+ld)/2.0){
                                    IsReflectionObservable = true;
                                }
                                else{
                                    IsReflectionObservable = false;
                                }
								break;
							case 4:
                                if((double)((h+k+l)/2)==(hd+kd+ld)/2.0){
                                    IsReflectionObservable = true;
                                }
                                else{
                                    IsReflectionObservable = false;
                                }
								break;
							case 5:
								if ((double)((h+k)/2)!=(hd+kd)/2.0) {
									IsReflectionObservable = false;
								}
								else if ((double)((h+l)/2)!=(hd+ld)/2.0) {
									IsReflectionObservable = false;
								}
								else if ((double)((k+l)/2)!=(kd+ld)/2.0) {
									IsReflectionObservable = false;
								}
								else {
									IsReflectionObservable = true;
								}

								break;

							case 6:
                                if((double)((-h+k+l)/3)==(-hd+kd+ld)/3.0){
                                    IsReflectionObservable = true;
                                }
                                else{
                                    IsReflectionObservable = false;
                                }
								break;								

                            default:
                                IsReflectionObservable = true;
                                break;
                                
                        }
                        if(IsReflectionObservable == true){

                            double q[] = new double[3];
                            for(int i=0;i<q.length;i++){
                                q[i]=hd*base_R2[0][i] + kd*base_R2[1][i] + ld*base_R2[2][i];
                            }
                        
                            double q_len = Math.sqrt(q[0]*q[0] + q[1]*q[1] + q[2]*q[2]);

                            for(int i=0;i<q.length;i++){ // !--- normalize q-vector
                                q[i] = q[i]/q_len;
                            }
                        

                            double theta = Math.acos(q[1]);
                            if((2.0*theta < Math.PI/2.0) && (q[1] > 0.0)){ //  !--- calc Laue spot
                                int X = (int)(distance*Math.tan(2.0*theta)*q[0]/Math.sqrt(q[0]*q[0] + q[2]*q[2])*mm2pixel);
                                int Z = (int)(distance*Math.tan(2.0*theta)*q[2]/Math.sqrt(q[0]*q[0] + q[2]*q[2])*mm2pixel);
                                if((beamcenter_x+X+offset_x <= filmsize-Diameter/2)&&(beamcenter_x+X+offset_x > Diameter/2)&&
								   (beamcenter_z-Z-offset_z <= filmsize-Diameter/2)&&(beamcenter_z-Z-offset_z>Diameter/2) && (q_len < 2*Math.PI/lambda_min)){
                                    g.drawOval(X+beamcenter_x+offset_x-Diameter/2,-Z+beamcenter_z-offset_z-Diameter/2,Diameter,Diameter);
                                }

                            }
                            
//                            System.out.println(""+h+" "+k+" "+l+":"+q[0]+" "+q[1]+" "+q[2]);
                            
                        }
                        else{
//                        System.out.println(" "+h+" "+k+" "+l);
                        }
                        

                        
                    }
                }
            }
			
			// Mark highlight spot============================
			//cast to double
			double hd = (double)H_HL;
			double kd = (double)K_HL;
			double ld = (double)L_HL;
			double q[] = new double[3];
			for(int i=0;i<q.length;i++){
				q[i]=hd*base_R2[0][i] + kd*base_R2[1][i] + ld*base_R2[2][i];
			}
                        
			double q_len = Math.sqrt(q[0]*q[0] + q[1]*q[1] + q[2]*q[2]);

			for(int i=0;i<q.length;i++){ // !--- normalize q-vector
				q[i] = q[i]/q_len;
			}
                        

			double theta = Math.acos(q[1]);
			if((2.0*theta < Math.PI/2.0) && (q[1] > 0.0)){ //  !--- calc Laue spot
				int X = (int)(distance*Math.tan(2.0*theta)*q[0]/Math.sqrt(q[0]*q[0] + q[2]*q[2])*mm2pixel);
				int Z = (int)(distance*Math.tan(2.0*theta)*q[2]/Math.sqrt(q[0]*q[0] + q[2]*q[2])*mm2pixel);
				if((beamcenter_x+X+offset_x <= filmsize-Diameter/2)&&(beamcenter_x+X+offset_x > Diameter/2)&&
					(beamcenter_z-Z-offset_z <= filmsize-Diameter/2)&&(beamcenter_z-Z-offset_z>Diameter/2) && (q_len < 2*Math.PI/lambda_min)){
					g.setColor(Color.orange);
					g.drawOval(X+beamcenter_x+offset_x-Diameter/2,-Z+beamcenter_z-offset_z-Diameter/2,Diameter,Diameter);
				}
			}
			// Mark highlight spot end============================
		
            g.setColor(Color.red);
            g.drawLine(0,beamcenter_z-offset_z,filmsize,beamcenter_z-offset_z);
            g.drawLine(beamcenter_x+offset_x,0,beamcenter_x+offset_x,filmsize);
			g.drawOval(beamcenter_x-CenterCircle/2,beamcenter_z-CenterCircle/2,CenterCircle,CenterCircle);

        }
        
        public void drawDirectLattice(Graphics g,int OriginX,int OriginY){
            Point3D p3d[] = new Point3D[14];
            
            double dummy_Z = 5000;
            
            for(int i=0;i<p3d.length;i++){
                p3d[i] = new Point3D();
                p3d[i].setOPoint(OriginX,OriginY,dummy_Z);
            }

            p3d[0].setXYZ(0,0,0);											//(0,0,0)
            p3d[1].setXYZ(-base_D2[0][1],-base_D2[0][2],-base_D2[0][0]);	//(1,0,0)
            p3d[2].setXYZ(-base_D2[1][1],-base_D2[1][2],-base_D2[1][0]);	//(0,1,0)
            p3d[3].setXYZ(-base_D2[2][1],-base_D2[2][2],-base_D2[2][0]);	//(0,0,1)
            p3d[4].setXYZ(-base_D2[0][1]-base_D2[1][1],-base_D2[0][2]-base_D2[1][2],-base_D2[0][0]-base_D2[1][0]);	//(1,1,0)
            p3d[5].setXYZ(-base_D2[0][1]-base_D2[1][1]-base_D2[2][1],-base_D2[0][2]-base_D2[1][2]-base_D2[2][2],-base_D2[0][0]-base_D2[1][0]-base_D2[2][0]);	//(1,1,1)
            p3d[6].setXYZ(-base_D2[0][1]-base_D2[2][1],-base_D2[0][2]-base_D2[2][2],-base_D2[0][0]-base_D2[2][0]);	//(1,0,1)
            p3d[7].setXYZ(-base_D2[1][1]-base_D2[2][1],-base_D2[1][2]-base_D2[2][2],-base_D2[1][0]-base_D2[2][0]);	//(0,1,1)
			
			for(int i=0;i<p3d.length;i++){
				double xd = p3d[i].getX();
				double yd = p3d[i].getY();
				double zd = p3d[i].getZ();
			
				p3d[i].setXYZ(xd+base_D2[2][1]/2.0,yd+base_D2[2][2]/2.0,zd+base_D2[2][0]/2.0);
			}
			
            for(int i=0;i<p3d.length;i++){
                double temp_len = p3d[i].getLength();
                p3d[i].setLength(temp_len*Dscale);
            }
			
		
			g.setColor(Color.lightGray);
			
            for(int i=0;i<4;i++){
				draw3D.Lines(p3d[0],p3d[i],g);
            }
			//            for(int i=8;i<14;i++){
			//				draw3D.Lines(p3d[3],p3d[i],g);
			//            }
			draw3D.Lines(p3d[3],p3d[6],g);	//(0,0,1) to (1,0,1)
			draw3D.Lines(p3d[3],p3d[7],g);	//(0,0,1) to (0,1,1)
			draw3D.Lines(p3d[4],p3d[1],g);	//(1,1,0) to (1,0,0)
			draw3D.Lines(p3d[4],p3d[2],g);	//(1,1,0) to (0,1,0)
			draw3D.Lines(p3d[4],p3d[5],g);	//(1,1,0) to (1,1,1)
			draw3D.Lines(p3d[5],p3d[6],g);	//(1,1,1) to (1,0,1)
			draw3D.Lines(p3d[5],p3d[7],g);	//(1,1,1) to (0,1,1)
			draw3D.Lines(p3d[1],p3d[6],g);	//(1,1,1) to (0,1,1)
			draw3D.Lines(p3d[2],p3d[7],g);	//(1,1,1) to (0,1,1)

        }

        public void drawReciprocalLattice(Graphics g,int OriginX,int OriginY){
        
            Point3D p3d[] = new Point3D[4];
            
            double dummy_Z = 50000;
            
            for(int i=0;i<p3d.length;i++){
                p3d[i] = new Point3D();
                p3d[i].setOPoint(OriginX,OriginY,dummy_Z);
            }

            p3d[0].setXYZ(0,0,0);
            p3d[1].setXYZ(-base_R2[0][1],-base_R2[0][2],-base_R2[0][0]);
            p3d[2].setXYZ(-base_R2[1][1],-base_R2[1][2],-base_R2[1][0]);
            p3d[3].setXYZ(-base_R2[2][1],-base_R2[2][2],-base_R2[2][0]);


            for(int i=1;i<p3d.length;i++){
                double temp_len = p3d[i].getLength();
                p3d[i].setLength(temp_len*Rscale);
            }

            int ColorNum[] = new int[4];
            ColorNum[0] = 0;    // 0 is dummy
            ColorNum[1] = 1;    // 0 is red
            ColorNum[2] = 2;    // 1 is blue
            ColorNum[3] = 3;    // 2 is green
            
            for(int i=1;i<p3d.length-1;i++){
                for(int j=i+1;j<p3d.length;j++){
                    if(p3d[j].getZ() < p3d[i].getZ()){
                        Point3D dummy3D = new Point3D();
                        int dummyColor = 0;
                        
                        dummy3D = p3d[j];
                        dummyColor = ColorNum[j];
                        
                        p3d[j] = p3d[i];
                        ColorNum[j] = ColorNum[i];
                        
                        p3d[i] = dummy3D;
                        ColorNum[i] = dummyColor;
                    }
                }
            }

            for(int i=0;i<p3d.length;i++){
                
                switch(ColorNum[i]){
                    case 1:
                        g.setColor(Color.red);
                        break;
                    case 2:
                        g.setColor(Color.blue);
                        break;
                    case 3:
                        g.setColor(Color.green);
                        break;
                    default:
                        g.setColor(Color.red);
                        break;
                }
                draw3D.Lines(p3d[0],p3d[i],g);
            }
        }
}