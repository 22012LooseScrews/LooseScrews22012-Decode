package abstraction.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class OuttakeMotor {
    private DcMotorEx outtakeMotor;
    private VoltageSensor voltageSensor;
    private double close_rpm = 2000;
    private double far_rpm = 2650;
    private double best_voltage = 12.9;

    public OuttakeMotor(OpMode opMode) {
        outtakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        voltageSensor = opMode.hardwareMap.voltageSensor.iterator().next();
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setVelocityPIDFCoefficients(
                17.5,
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
        double factor = best_voltage/current_voltage;
        return factor * close_rpm;
    }
    public double newFarRpm(double far_rpm){
        double current_voltage = voltageSensor.getVoltage();
        double factor = best_voltage/current_voltage;
        return factor * far_rpm;
    }
    public double getVol(){
        return voltageSensor.getVoltage();
    }
}
