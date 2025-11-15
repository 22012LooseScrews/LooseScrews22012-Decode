package opmodes.teleop;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import abstraction.subsystems.SpinServo;
/*import abstraction.subsystems.IntakeMotor;
import abstraction.subsystems.OuttakeMotor;*/
@TeleOp
@Config
public class MecanumTeleOp extends OpMode {
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor, intakeMotor;
    DcMotorEx outtakeMotor;
    CRServo vectorServo, spinServo;
//    Servo gateServo;
    public static ElapsedTime myStopwatch = new ElapsedTime();
    double spin_servo_position = 0;
    private boolean opModeIsActive() {
        return true;
    }
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


//        gateServo = hardwareMap.get(Servo.class, "gateServo");
        vectorServo = hardwareMap.get(CRServo.class, "vectorServo");

        SpinServo spinServo = new SpinServo(this);

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        myStopwatch.reset();
        // Optionally tune PIDF here if needed
        // double P = 10.0, I = 0.0, D = 0.5, F = 13.0;
        // outtakeMotor.setVelocityPIDFCoefficients(P, I, D, F);
    }

    @Override
    public void loop() {
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

        if (gamepad1.left_bumper) {
            intakeMotor.setPower(1.0);
            vectorServo.setPower(0.0);
        } else if (gamepad1.right_bumper) {
            intakeMotor.setPower(-1.0);
            vectorServo.setPower(1.0);
        } else {
            intakeMotor.setPower(0.0);
            vectorServo.setPower(0.0);
        }

        if (gamepad1.circle || gamepad1.b) {
            while (opModeIsActive() && myStopwatch.seconds() < 0.5) {
                spinServo.setPower(1.0);
            }
        } else if (gamepad1.triangle || gamepad1.y) {
            while (opModeIsActive() && myStopwatch.seconds() < 0.5) {
                spinServo.setPower(-1.0);
            }
        }else {
            spinServo.setPower(0.0);
        }

//
//        if(gamepad1.cross || gamepad1.a){
//            gateServo.setPosition(0.25);
//        }
//        else{
//            gateServo.setPosition(0);
//        }

        if (gamepad1.dpadUpWasPressed()) {
            vectorServo.setPower(-1.0);
        } else if (gamepad1.dpadUpWasReleased()) {
            vectorServo.setPower(0.0);
        }

        if (gamepad1.left_trigger > 0.1) {
            outtakeMotor.setPower(-0.79);
        } else if (gamepad1.right_trigger > 0.1) {
            outtakeMotor.setPower(-0.93);
        } else {
            outtakeMotor.setPower(0);
        }

   /*    telemetry.addData("Y", -gamepad1.left_stick_y);
        telemetry.addData("X", -gamepad1.left_stick_x * 1.1);
        telemetry.addData("RX", gamepad1.right_stick_x);
        telemetry.addData("Left Bumper", gamepad1.left_bumper);
        telemetry.addData("Right Bumper", gamepad1.right_bumper);*/
        //telemetry.addData("Target Velocity (RPM)", velocity);
        //telemetry.addData("Outtake Velocity (ticks/s)", outtakeMotor.getVelocity());
    }


}