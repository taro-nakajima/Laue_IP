import java.awt.*;

public class draw3D{
	public static int colorRED = 0;
	public static int colorGREEN = 1;
	public static int colorBLUE = 2;
	public static int colorORANGE = 3;

	public static void Sphere(Point3D R2,double r,int color,double dL,Graphics ct){
		double x=0,y=0,z=0;
		double x0=0,y0=0,z0=0;

		//
		double dr0[] = {0,1,2,3,6};
		double dR0[] = {0,5,12,20,30};
		//
		double dr[] = {0,0,0,0,0};
		double dR[] = {0,0,0,0,0};

		//
		int C0[] = {20,80,120,140,150};
		//
		int C[] = {0,0,0,0,0};
		//
		double L2 =2.0;

		//
		int R[] = new int[5];
		int G[] = new int[5];
		int B[] = new int[5];

		//
		double R0=40;
		//
		double R1=0;
		//
		double delta_x=0,delta_z=0;

		int i=0,k=0;

		x = R2.getX();
		y = R2.getY();
		z = R2.getZ();

		x0 = R2.getX0();
		y0 = R2.getY0();
		z0 = R2.getZ0();

		delta_x = x*(y+dL)/(y0+dL+y);
		delta_z = z*(y+dL)/(y0+dL+y);

		R1 = R0*(r/R0)*y0/(y0+dL+y);

		for(k=0;k<dR.length;k++){
			dR[k] = dR0[k]*(r/R0)*y0/(y0+dL+y);
		}
		for(k=0;k<dr.length;k++){
			dr[k] = dr0[k]*(r/R0)*y0/(y0+dL+y);
		}

		for(k=0;k<5;k++){
			C[k] = (int)((double)C0[k]*(y0+dL)/(y0+dL+y/L2));
			if(C[k]>255){
				C[k]=255;
			}
		}

		switch(color){
			case 0:		//RED
				for(i=0;i<R.length;i++){
					R[i] = 255;
					G[i] = C[i];
					B[i] = C[i];
				}
				break;

			case 1:		//GREEN
				for(i=0;i<R.length;i++){
					R[i] = C[i];
					G[i] = 255;
					B[i] = C[i];
				}
				break;
			
			case 2:		//BLUE
				for(i=0;i<R.length;i++){
					R[i] = C[i];
					G[i] = C[i];
					B[i] = 255;
				}
				break;
			
			case 3:		//BLUE
				for(i=0;i<R.length;i++){
					R[i] = 255;
					G[i] = 255;
					B[i] = C[i];
				}
				break;

			case 4:		//BLUE
				for(i=0;i<R.length;i++){
					R[i] = C[i];
					G[i] = 255;
					B[i] = 255;
				}
				break;
			
			default:
				for(i=0;i<R.length;i++){
					R[i] = 255;
					G[i] = C[i];
					B[i] = C[i];
				}
				break;
		}
				
		for(i=0;i<5;i++){
			ct.setColor(new Color(R[i],G[i],B[i]));
			ct.fillOval((int)(x0+x-delta_x-(R1/2)+dr[i]),(int)(z0-z+delta_z-(R1/2)+dr[i]),(int)(R1-dR[i]),(int)(R1-dR[i]));
		}
	}


	public static void Vector(Point3D R,double dL,Graphics g){
		double x=0,y=0,z=0;
		double x0=0,y0=0,z0=0;
		int xPoints[] = new int[7];
		int zPoints[] = new int[7];
		double c0 = 10;	//vector constant
		double c = 10;	//vector constant
		double C0 = 10;
		double C = 10;
		double r = 0;	//vector length
		
		
		//�v�Z�p
		double delta_x=0,delta_z=0;

		int i=0,k=0;

		x = R.getX();
		y = R.getY();
		z = R.getZ();

		x0 = R.getX0();
		y0 = R.getY0();
		z0 = R.getZ0();
	

		delta_x = x*(y+dL)/(y0+dL+y);
		delta_z = z*(y+dL)/(y0+dL+y);

		x = x - delta_x;
		z = z - delta_z;

		r = Math.sqrt(x*x + z*z);

		c0 = C0*y0/(y0+dL);
		c = C*y0/(y0+dL+y);


		xPoints[0] = (int)( x0 + c0*(-z)/r );
		zPoints[0] = (int)( z0 - c0*x/r );

		xPoints[1] = (int)( x0 + x + c*(-z)/r + c*(1.5)*(-x)/r );
		zPoints[1] = (int)( z0 - z - c*x/r - c*(1.5)*(-z)/r );

		xPoints[2] = (int)( x0 + x + c*(1.5)*(-z)/r + c*(1.5)*(-x)/r );
		zPoints[2] = (int)( z0 - z - c*(1.5)*x/r - c*(1.5)*(-z)/r );

		xPoints[3] = (int)( x0 + x );
		zPoints[3] = (int)( z0 - z );

		xPoints[4] = (int)( x0 + x + c*(1.5)*z/r + c*(1.5)*(-x)/r );
		zPoints[4] = (int)( z0 - z - c*(1.5)*(-x)/r - c*(1.5)*(-z)/r );

		xPoints[5] = (int)( x0 + x + c*z/r + c*(1.5)*(-x)/r );
		zPoints[5] = (int)( z0 - z - c*(-x)/r - c*(1.5)*(-z)/r );

		xPoints[6] = (int)( x0 + c0*z/r );
		zPoints[6] = (int)( z0 - c0*(-x)/r );


		g.setColor(Color.green);
		g.fillPolygon(xPoints,zPoints,xPoints.length);

	}

	public static void Lines(Point3D S,Point3D E,Graphics g){
		Point2D s = new Point2D();
		Point2D e = new Point2D();
		s = S.get2D();
		e = E.get2D();

		g.drawLine(s.getXpm(),s.getYpm(),e.getXpm(),e.getYpm());
//        System.out.println(" "+s.getXpm()+" "+s.getYpm()+" "+e.getXpm()+" "+e.getYpm());

	}

}
