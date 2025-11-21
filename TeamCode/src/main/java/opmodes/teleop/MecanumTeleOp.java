package opmodes.teleop;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import abstraction.subsystems.SpinServo;
import abstraction.subsystems.IntakeMotor;
import abstraction.subsystems.OuttakeMotor;
@TeleOp
@Config
public class MecanumTeleOp extends OpMode {
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor, intakeMotor;
    DcMotorEx outtakeMotor;
    CRServo vectorServo, spinServo;
    SpinServo spindexer;

//    public static ElapsedTime myStopwatch = new ElapsedTime();
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

        vectorServo = hardwareMap.get(CRServo.class, "vectorServo");

        //spinServo = hardwareMap.get(CRServo.class, "spinServo");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        spindexer = new SpinServo(this);

//        myStopwatch.reset();
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
            //vectorServo.setPower(0.0);
        } else if (gamepad1.right_bumper) {
            intakeMotor.setPower(-1.0);
         //   vectorServo.setPower(1.0);
        } else {
            intakeMotor.setPower(0.0);
           // vectorServo.setPower(0.0);
        }

        if(gamepad1.circleWasPressed() || gamepad1.bWasPressed()){
           // spinServo.setPower(1.0);
            spindexer.spin_forward();
        }
        else if(gamepad1.triangleWasPressed() || gamepad1.bWasReleased()){
//      //      spinServo.setPower(-1.0);
            spindexer.spin_backward();
        }
        else if(gamepad1.circleWasReleased() || gamepad1.bWasReleased() || gamepad1.triangleWasReleased() || gamepad1.bWasReleased()){
         //   spinServo.setPower(0.0);
            spindexer.spin_stop();
        }

        if (gamepad1.dpadUpWasPressed()) {
            vectorServo.setPower(-1.0);
        } else if (gamepad1.dpadUpWasReleased()) {
            vectorServo.setPower(0.0);
        }

        if (gamepad1.left_trigger > 0.1) {
            outtakeMotor.setPower(-0.93);
        } else if (gamepad1.right_trigger > 0.1) {
            outtakeMotor.setPower(-1.00);
        } else {
            outtakeMotor.setPower(0);
        }

   /*   telemetry.addData("Y", -gamepad1.left_stick_y);
        telemetry.addData("X", -gamepad1.left_stick_x * 1.1);
        telemetry.addData("RX", gamepad1.right_stick_x);
        telemetry.addData("Left Bumper", gamepad1.left_bumper);
        telemetry.addData("Right Bumper", gamepad1.right_bumper);*/
        //telemetry.addData("Target Velocity (RPM)", velocity);
        //telemetry.addData("Outtake Velocity (ticks/s)", outtakeMotor.getVelocity());
    }
}