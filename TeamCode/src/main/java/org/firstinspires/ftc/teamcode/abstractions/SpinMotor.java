package org.firstinspires.ftc.teamcode.abstractions;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class SpinMotor {
    private DcMotor spinMotor;
    private static final int ticks_for_120_degrees_one = 180;
    private static final int ticks_for_120_degrees_two = 200;
    private static final int ticks_for_360_degrees = 540;

    public SpinMotor(OpMode opMode){
        spinMotor = opMode.hardwareMap.get(DcMotor.class, "spinMotor");
        spinMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        spinMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spinMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void add120DegreesOne(double power) {
        int new120TargetOne = spinMotor.getCurrentPosition() + ticks_for_120_degrees_one;

        spinMotor.setTargetPosition(new120TargetOne);
        spinMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spinMotor.setPower(power);
    }
    public void add120DegreesTwo(double power){
        int new120TargetTwo = spinMotor.getCurrentPosition() + ticks_for_120_degrees_two;

        spinMotor.setTargetPosition(new120TargetTwo);
        spinMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spinMotor.setPower(power);
    }

    public void add360Degrees(double power){
        int new360Target = spinMotor.getCurrentPosition() + ticks_for_360_degrees;

        spinMotor.setTargetPosition(new360Target);
        spinMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spinMotor.setPower(power);
    }

    public void spin_forward(){
        spinMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spinMotor.setPower(0.55);
    }

    public void spin_backward(){
        spinMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        spinMotor.setPower(-0.55);
    }

    public void spin_stop(){
        spinMotor.setPower(0);
    }
}