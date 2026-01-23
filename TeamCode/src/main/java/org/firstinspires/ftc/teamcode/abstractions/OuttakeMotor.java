package org.firstinspires.ftc.teamcode.abstractions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class OuttakeMotor {
    private DcMotorEx outtakeMotor;
    private double auto_close_rpm = 1875;
    private double close_rpm = 1745;
    private double far_rpm = 2150;

    public OuttakeMotor(OpMode opMode) {
        outtakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setVelocityPIDFCoefficients(
                200,
                0,
                0,
                11.9
        );
    }

    public void auto_outtake_close() {
        outtakeMotor.setVelocity(auto_close_rpm);
    }
    public void outtake_close() {
        outtakeMotor.setVelocity(close_rpm);
    }
    public void outtake_far() {
        outtakeMotor.setVelocityPIDFCoefficients(
                300,
                0,
                0,
                26.9
        );
        outtakeMotor.setVelocity(far_rpm);
    }
    public void outtake_stop() {
        outtakeMotor.setVelocity(0);
    }
    public double getVel(){
        return outtakeMotor.getVelocity();
    }
}
