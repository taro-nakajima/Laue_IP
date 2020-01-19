//Point2D ver0.0
public class Point2D{
	protected double x=0,y=0;		//
	protected double x0=0,y0=0;	//
	
	public void setXY(double a,double b){
		x=a;
		y=b;
		}

	public void setXY(double e[]){
		x = e[0];
		y = e[1];
		}

	public void setOPoint(double a,double b){
		x0=a;
		y0=b;
		}

	public void setX0(double a){
		x0 = a;
		}

	public void setY0(double a){
		y0=a;
		}

	public void setLength(double l){
		double x1=0,y1=0,z1=0;		//

		x1 = x*l/(Math.sqrt(x*x+y*y));
		y1 = y*l/(Math.sqrt(x*x+y*y));

		x=x1;
		y=y1;
		}

	public double getX(){
		return x;
		}

	public int getXpm(){
		return (int)(x0+x);
		}

	public double getY(){
		return y;
		}

	public int getYpm(){
		return (int)(y0+y);
		}

	public double getX0(){
		return x0;
		}

	public double getY0(){
		return y0;
		}


	public double getLength(){
		return Math.sqrt(x*x+y*y);
		}
}
