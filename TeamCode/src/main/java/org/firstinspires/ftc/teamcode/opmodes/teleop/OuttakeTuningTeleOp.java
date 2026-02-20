package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;

@TeleOp
public class OuttakeTuningTeleOp extends OpMode {
    public DcMotorEx outtakeMotor, outtakeMotor2;
    IntakeMotor intakeMotor;
    private double close_rpm = 1650;
    private double far_rpm = 1900;
    double F = 0;
    double P = 0;
    double current_target_velocity = close_rpm;
    double[] step_sizes = {10.0, 1.0, 0.1, 0.001, 0.0001};
    int step_index = 0;
    @Override
    public void init() {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtakeMotor");
        outtakeMotor2 = hardwareMap.get(DcMotorEx.class, "outtakeMotor2");
        outtakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        outtakeMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        outtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        outtakeMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        intakeMotor = new IntakeMotor(this);
    }
    @Override
    public void loop() {
        if(gamepad1.left_bumper){
            intakeMotor.intake_intake();
        }
        else if(gamepad1.right_bumper){
            intakeMotor.intake_outtake();
        }
        else{
            intakeMotor.intake_stop();
        }

        if(gamepad1.aWasPressed()){
            if(current_target_velocity == close_rpm){
                current_target_velocity = far_rpm;
            }
            else {
                current_target_velocity = close_rpm;
            }
        }
        if(gamepad1.xWasPressed()){
            step_index = (step_index+1)%step_sizes.length;
        }

        if(gamepad1.dpadLeftWasPressed()){
            F += step_sizes[step_index];
        }

        if(gamepad1.dpadRightWasPressed()){
            F -= step_sizes[step_index];
        }

        if(gamepad1.dpadUpWasPressed()){
            P += step_sizes[step_index];
        }

        if(gamepad1.dpadDownWasPressed()){
            P -= step_sizes[step_index];
        }

        PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);
        outtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        outtakeMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);

        outtakeMotor.setVelocity(current_target_velocity);
        outtakeMotor2.setVelocity(current_target_velocity);

        double current_velocity = outtakeMotor.getVelocity();
        double error = current_target_velocity - current_velocity;

        telemetry.addData("Target Velocity", current_target_velocity);
        telemetry.addData("Current Velocity", "%.2f", current_velocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addData("Tuning P", "%.4f (D-Pad U/D", P);
        telemetry.addData("Tuning F", "%.4f(D-Pad L/R", F);
        telemetry.addData("Step Size", "%.4f(X Button)", step_sizes[step_index]);
    }
}
