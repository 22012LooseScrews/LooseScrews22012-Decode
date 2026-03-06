package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeServo;
import org.firstinspires.ftc.teamcode.abstractions.ServoStopper;

//import com.qualcomm.hardware.limelightview.Limelight3A;
//import com.qualcomm.hardware.limelightview.LLResult;

@TeleOp
public class MecanumTeleOp extends OpMode {
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;
    OuttakeMotor outtake_motor;
    IntakeMotor intake_motor;
    ServoStopper servo_Stopper;
    OuttakeServo outtake_servo;
    private Limelight3A limelight;
    private static final double kp_turn = 0.03;
    private static final double max_speed = 1.0;
    boolean was_a_pressed, was_b_pressed;
    String servo_stopper_position;

    @Override
    public void init() {
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        outtake_motor = new OuttakeMotor(this);
        intake_motor = new IntakeMotor(this);
        servo_Stopper = new ServoStopper(this);
        outtake_servo = new OuttakeServo(this);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(2);

        servo_stopper_position = "Open";
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;
        double final_rx = rx;

        LLResult ll_result = limelight.getLatestResult();
        if(ll_result != null && ll_result.isValid()){
            double ty = ll_result.getTy();
            if(gamepad1.dpad_left){
                telemetry.addLine("Apriltag Detected - Aligning Close");
                double close_turn_error = ty;
                double close_turn_power = close_turn_error * kp_turn;
                final_rx = Math.min(Math.abs(close_turn_power), max_speed) * Math.signum(close_turn_power);
            }
            else if(gamepad1.dpad_right){
                telemetry.addLine("Apriltag Detected - Aligning Far");
                double far_turn_error = ty;
                double far_turn_power = far_turn_error * kp_turn;
                final_rx = Math.min(Math.abs(far_turn_power), max_speed) * Math.signum(far_turn_power);
            }
        }

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(final_rx), 1);
        frontLeftMotor.setPower((y + x + final_rx) / denominator);
        backLeftMotor.setPower((y - x + final_rx) / denominator);
        frontRightMotor.setPower((y - x - final_rx) / denominator);
        backRightMotor.setPower((y + x - final_rx) / denominator);

        if (gamepad1.right_trigger > 0.1) {
            outtake_motor.outtake_far();
            outtake_servo.outtake_shift_far();
        } else if (gamepad1.left_trigger > 0.1) {
            outtake_motor.outtake_close();
            outtake_servo.outtake_shift_close();
        } else {
            outtake_motor.outtake_stop();
            if (gamepad1.dpad_right) {
                outtake_servo.outtake_shift_far();
            } else {
                outtake_servo.outtake_shift_close();
            }
        }

        if (gamepad1.a && !was_a_pressed) {
            servo_Stopper.gate_open();
            servo_stopper_position = "Open";
        } else if (gamepad1.b && !was_b_pressed) {
            servo_Stopper.gate_close();
            servo_stopper_position = "Closed";
        }

        if ((gamepad1.right_trigger > 0.1 && outtake_motor.getVel() > 2175) || (gamepad1.left_trigger > 0.1 && outtake_motor.getVel() > 1625)) {
            servo_Stopper.gate_open();
            servo_stopper_position = "Open";
        }
        was_a_pressed = gamepad1.a;
        was_b_pressed = gamepad1.b;

        if (gamepad1.left_bumper) {
            intake_motor.intake_intake();
        } else if (gamepad1.right_bumper) {
            intake_motor.intake_outtake();
        } else if (gamepad1.right_trigger > 0.1 && outtake_motor.getVel() > 2175) {
            intake_motor.intake_intake();
        } else if (gamepad1.left_trigger > 0.1 && outtake_motor.getVel() > 1625) {
            intake_motor.intake_intake();
        } else if (servo_stopper_position.equals("Closed")) {
            intake_motor.intake_intake();
        } else {
            intake_motor.intake_stop();
        }

        telemetry.addData("Outtake Velocity", outtake_motor.getVel());
        telemetry.addData("Gate Position", servo_stopper_position);
        telemetry.update();
    }
}