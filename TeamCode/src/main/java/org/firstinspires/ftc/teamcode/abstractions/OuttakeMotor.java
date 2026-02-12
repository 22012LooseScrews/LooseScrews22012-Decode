package org.firstinspires.ftc.teamcode.abstractions;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class OuttakeMotor {
    private DcMotorEx outtakeMotor;

    private DcMotorEx outtakeMotor2;
    private double auto_close_rpm = 1650;
    private double close_rpm = 1650;
    private double far_rpm = 1900;

    public OuttakeMotor(OpMode opMode) {
        outtakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        outtakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setVelocityPIDFCoefficients(
                250,
                0,
                0,
                12.4
        );
        outtakeMotor2 = opMode.hardwareMap.get(DcMotorEx.class, "outtakeMotor2");
        outtakeMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor2.setVelocityPIDFCoefficients(
                250,
                0,
                0,
                12.4
        );
    }

    public void auto_outtake_close() {
        outtakeMotor.setVelocityPIDFCoefficients(
                80,
                0,
                0,
                12.365
        );
        outtakeMotor.setVelocity(auto_close_rpm);
        outtakeMotor2.setVelocityPIDFCoefficients(
                80,
                0,
                0,
                12.365
        );
        outtakeMotor2.setVelocity(auto_close_rpm);
    }
    public void outtake_close() {
        outtakeMotor.setVelocityPIDFCoefficients(
                70,
                0,
                0,
                12.3
        );
        outtakeMotor.setVelocity(close_rpm);
        outtakeMotor2.setVelocityPIDFCoefficients(
                70,
                0,
                0,
                12.3
        );
        outtakeMotor2.setVelocity(close_rpm);
    }
    public void outtake_far() {
        outtakeMotor.setVelocityPIDFCoefficients(
                90,
                0,
                0,
                12.93
        );
        outtakeMotor.setVelocity(far_rpm);
        outtakeMotor2.setVelocityPIDFCoefficients(
                90,
                0,
                0,
                12.93
        );
        outtakeMotor2.setVelocity(far_rpm);
    }
    public void outtake_stop() {
        outtakeMotor.setVelocity(0);
        outtakeMotor2.setVelocity(0);
    }
    public double getVel(){
        return outtakeMotor.getVelocity();
    }
    public double getVel2(){
        return outtakeMotor2.getVelocity();
    }
}
