//Point3D ver2.0
public class Point3D{
	protected double x=0,y=0,z=0;		//
	protected double x0=0,y0=0,z0=0;	//
	
	public void setXYZ(double a,double b,double c){
		x=a;
		y=b;
		z=c;
		}

	public void setXYZ(double e[]){
		x = e[0];
		y = e[1];
		z = e[2];
		}

	public void setOPoint(double a,double b,double c){
		x0=a;
		y0=b;
		z0=c;
		}

	public void setX0(double a){
		x0 = a;
		}

	public void setY0(double a){
		y0=a;
		}

	public void setZ0(double a){
		z0=a;
		}

	public void setX(double a){
		x = a;
		}

	public void setY(double a){
		y=a;
		}

	public void setZ(double a){
		z=a;
		}

	public void setLength(double l){
		double x1=0,y1=0,z1=0;		//

		x1 = x*l/(Math.sqrt(x*x+y*y+z*z));
		y1 = y*l/(Math.sqrt(x*x+y*y+z*z));
		z1 = z*l/(Math.sqrt(x*x+y*y+z*z));

		x=x1;
		y=y1;
		z=z1;
		}

	public double getX(){
		return x;
		}

	public double getY(){
		return y;
		}

	public double getZ(){
		return z;
		}

	public double getX0(){
		return x0;
		}

	public double getY0(){
		return y0;
		}

	public double getZ0(){
		return z0;
		}

	public double getLength(){
		return Math.sqrt(x*x+y*y+z*z);
		}

	public Point2D get2D(){
		Point2D p2d = new Point2D();
		p2d.setOPoint(x0,y0);
		
		p2d.setXY(x*z0/(z0+z),y*z0/(z0+z));
		
		return p2d;
	}

}
