package opmodes.testing;

public class RobotLocationPractice {
    double angle;
    double x;
    double y;
    public void changeY(double change_amount){
        y+=change_amount;
    }
    public void setY(double y){
        this.y = y;
    }
    public double getY(){
        return this.y;
    }
    public void changeX(double change_amount){
        x+=change_amount;
    }
    public void setX(double x){
        this.x=x;
    }
    public double getX(){
        return this.x;
    }
    // Constructor method
    public RobotLocationPractice(double angle){
        this.angle = angle;
    }
    public double getHeading(){
        // This method normalizes robot heading between -180 and 180
        //This is useful for calculating turn angles, especially when crossing the 0,360 degree boundary
        double angle = this.angle; //Copy the raw angle
        while(angle>180){
            angle-=360; //Subtract until in target range
        }
        while(angle<=-180){
            angle+=360; //Add until in target range
        }
        return angle;
    }
    public void turnRobot(double angle_change){
        angle+=angle_change;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public double getAngle(){
        return this.angle;
    }
}
