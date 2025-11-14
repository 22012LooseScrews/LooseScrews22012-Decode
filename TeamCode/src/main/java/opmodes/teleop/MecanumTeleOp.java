package opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Config
public class MecanumTeleOp extends OpMode {
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor, intakeMotor;
    DcMotorEx outtakeMotor;
    CRServo vectorServo, spinServo;
    int num_of_times_circle_pressed;

    // Dashboard-tunable variables
  //  public static double close_rpm = 4000;  // close shot
//    public static double far_rpm = 5400;    // far shot
//    public static double velocity = 0;      // current target velocity (RPM)
//    public static double ticksPerRev = 28;  // update if your motor encoder differs

    @Override
    public void init() {
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtakeMotor");

        spinServo = hardwareMap.get(CRServo.class, "spinServo");
        vectorServo = hardwareMap.get(CRServo.class, "vectorServo");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Optionally tune PIDF here if needed
        // double P = 10.0, I = 0.0, D = 0.5, F = 13.0;
        // outtakeMotor.setVelocityPIDFCoefficients(P, I, D, F);
    }

    @Override
    public void loop() {
        // === Mecanum Drive Control ===
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        // === Intake Control ===
        if (gamepad1.left_bumper) {
            intakeMotor.setPower(1.0);
        } else if (gamepad1.right_bumper) {
            intakeMotor.setPower(-1.0);
        } else {
            intakeMotor.setPower(0.0);
        }

        // === Spin Servo Control ===
        if (gamepad1.circleWasPressed() || gamepad1.bWasPressed()) {
            spinServo.setPower(1.0);
        } else if (gamepad1.circleWasReleased() || gamepad1.bWasReleased()) {
            spinServo.setPower(0.0);
        } else if (gamepad1.triangleWasPressed() || gamepad1.yWasPressed()) {
            spinServo.setPower(-1.0);
        } else if (gamepad1.triangleWasReleased() || gamepad1.yWasReleased()) {
            spinServo.setPower(0.0);
        }

        // === Vector Servo Control ===
        if (gamepad1.dpadUpWasPressed()) {
            vectorServo.setPower(-1.0);
        } else if (gamepad1.dpadUpWasReleased()) {
            vectorServo.setPower(0.0);
        }

        // === Outtake Velocity Control ===
        if (gamepad1.left_trigger > 0.1) {
            outtakeMotor.setPower(-0.79);
        } else if (gamepad1.right_trigger > 0.1) {
            outtakeMotor.setPower(-0.93);
        } else {
            outtakeMotor.setPower(0);
        }

        // === Telemetry ===
   /*     telemetry.addData("Y", -gamepad1.left_stick_y);
         telemetry.addData("X", -gamepad1.left_stick_x * 1.1);
        telemetry.addData("RX", gamepad1.right_stick_x);
        telemetry.addData("Left Bumper", gamepad1.left_bumper);
        telemetry.addData("Right Bumper", gamepad1.right_bumper);*/
        //telemetry.addData("Target Velocity (RPM)", velocity);
        //telemetry.addData("Outtake Velocity (ticks/s)", outtakeMotor.getVelocity());
    }
}