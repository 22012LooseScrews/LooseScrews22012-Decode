package abstraction.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class OuttakeMotor {
    private DcMotorEx outtakeMotor;
    private VoltageSensor voltageSensor;
    private double close_rpm = 1775;
    private double far_rpm = 2150;
    private double best_voltage;

    public OuttakeMotor(OpMode opMode) {
        outtakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        voltageSensor = opMode.hardwareMap.voltageSensor.iterator().next();
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setVelocityPIDFCoefficients(
                17,
                0.5,
                1,
                10
        );
    }
    public void outtake_close() {
        outtakeMotor.setVelocity(newCloseRpm(close_rpm));
    }
    public void outtake_far() {
        outtakeMotor.setVelocity(newFarRpm(far_rpm));
    }
    public void outtake_stop() {
        outtakeMotor.setVelocity(0);
    }
    public double getVel(){
        return outtakeMotor.getVelocity();
    }
    public double newCloseRpm(double close_rpm){
        double current_voltage = voltageSensor.getVoltage();
//        if(current_voltage>13.3){
//            best_voltage = 13.3;
//        }
//        else{
//            best_voltage = 13;
//        }
//        double factor = best_voltage/current_voltage;
//        return factor * close_rpm;

        if(current_voltage>13.1){
            best_voltage = 13.75;
        }
        else{
            best_voltage = 13.25;
        }
        double factor = best_voltage/current_voltage;
        return factor * close_rpm;
    }
    public double newFarRpm(double far_rpm){
        double current_voltage = voltageSensor.getVoltage();
        if(current_voltage>13.1){
            best_voltage = 13.75;
        }
        else{
            best_voltage = 13.25;
        }
        double factor = best_voltage/current_voltage;
        return factor * far_rpm;
    }
    public double getVol(){
        return voltageSensor.getVoltage();
    }
    public double getFactor(){
        double current_voltage = voltageSensor.getVoltage();
        double factor = best_voltage/current_voltage;
        return factor;
    }
}
