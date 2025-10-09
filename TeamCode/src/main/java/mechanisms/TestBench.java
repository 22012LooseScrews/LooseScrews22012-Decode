package mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

// THIS CLASS INCLUDES ALL OF THE CODE FOR MECHANISMS
public class TestBench {
    private DcMotor dc_motor;
    private double ticks_per_revolution;
    private DigitalChannel touch_sensor;
    private DistanceSensor distance_sensor;
    private Servo positional_servo;
    private CRServo rotational_servo;
    NormalizedColorSensor color_sensor;

    public void init(HardwareMap hd_map){
        // touch sensor code
        touch_sensor = hd_map.get(DigitalChannel.class, "touch_sensor");
        touch_sensor.setMode(DigitalChannel.Mode.INPUT);

        //dc motor code
        dc_motor = hd_map.get(DcMotor.class, "dc_motor");
        dc_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ticks_per_revolution = dc_motor.getMotorType().getTicksPerRev();
        dc_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        dc_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        //distance sensor code
        distance_sensor = hd_map.get(DistanceSensor.class, "distance_sensor");

        //positional and rotational servo code
        positional_servo = hd_map.get(Servo.class, "positional_servo");
        rotational_servo = hd_map.get(CRServo.class, "rotational_servo");
        positional_servo.scaleRange(0.5,1.0); // allows to set a range
        positional_servo.setDirection(Servo.Direction.REVERSE); //reverses the direction of the positional servo
        rotational_servo.setDirection(DcMotorSimple.Direction.REVERSE); //reverses the direction of the rotational servo

        //color detector code
        color_sensor = hd_map.get(NormalizedColorSensor.class, "color_sensor");
    }
    //this enum thing is for the color detector
/*
//    public enum ColorDetector{
        GREEN,
        PURPLE,
        UNKNOWN,
    }
*/
    //touch sensor methods
    public boolean isTouchSensorPressed(){
        return !touch_sensor.getState();
    }
/*    public boolean isTouchSensorReleased(){
        return touch_sensor.getState();
  */
    //dc motor methods
    public void setMotorSpeed(double speed){
        // Accepts values from -1.0 to 0.1
        dc_motor.setPower(speed);
    }
    public double getMotorRevs(){
        // Normalizing ticks to revolutions
        return dc_motor.getCurrentPosition()/ticks_per_revolution;
    }
    public void setBrakeBehavior(DcMotor.ZeroPowerBehavior zero_behavior){
        dc_motor.setZeroPowerBehavior(zero_behavior);
    }
    //distance sensor methods
    public double getDistanceSensorState(){
        return distance_sensor.getDistance(DistanceUnit.CM);
    }
    //positional and rotational servo methods
    public void setPositionalServoAngle(double angle){
        positional_servo.setPosition(angle);
    }
    public void setRotationalServoPower(double power){
        rotational_servo.setPower(power);
    }
    //color sensor methods
    //public Detect getDetectedColor(){
        // DO THIS LATER
    }

