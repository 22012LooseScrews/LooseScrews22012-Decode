package opmodes.testing;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

@TeleOp
@Config
public class AprilTagLimelightTestTeleop extends OpMode {

    // === MOTORS & SERVOS ===
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor, intakeMotor;
    DcMotorEx outtakeMotor;
    CRServo vectorServo, spinServo;

    // === LIMELIGHT ===
    Limelight3A limelight;

    // Target standoff distance from AprilTag (meters)
    public static double targetDistance = 0.40;

    // PID gains
    public static double kpForward = 0.04;
    public static double kpStrafe = 0.04;
    public static double kpTurn = 0.02;

    @Override
    public void init() {

        // === DRIVE MOTORS ===
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
        // FIXED: use DcMotor.ZeroPowerBehavior enum for DcMotorEx setZeroPowerBehavior
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // === LIMELIGHT INIT ===
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // set AprilTag pipeline (adjust index if needed)
    }

    @Override
    public void loop() {

        // If DPAD DOWN is held → run AprilTag autonomous tracking
        if (gamepad1.dpad_down) {
            runAprilTagTracking();
            return;
        }

        // Else → normal driver control
        runDriverControl();

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
        // FIXED: use camelCase methods (no underscore)
        if (gamepad1.dpadUpWasPressed()) {
            vectorServo.setPower(-1.0);
        } else if (gamepad1.dpadUpWasReleased()) {
            vectorServo.setPower(0.0);
        }

        // === Outtake Velocity ===
        if (gamepad1.left_trigger > 0.1) {
            outtakeMotor.setPower(-0.79);
        } else if (gamepad1.right_trigger > 0.1) {
            outtakeMotor.setPower(-0.93);
        } else {
            outtakeMotor.setPower(0);
        }
    }

    // ============================================================
    // DRIVER CONTROL (normal teleop)
    // ============================================================
    private void runDriverControl() {
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double fl = (y + x + rx) / denominator;
        double bl = (y - x + rx) / denominator;
        double fr = (y - x - rx) / denominator;
        double br = (y + x - rx) / denominator;

        setDrive(fl, fr, bl, br);
    }

    // ============================================================
    // APRILTAG AUTONOMOUS TRACKING (DPAD DOWN)
    // ============================================================
    private void runAprilTagTracking() {

        LLResult result = limelight.getLatestResult();

        if (result == null || !result.isValid()) {
            setDrive(0,0,0,0);
            telemetry.addLine("No AprilTag detected.");
            return;
        }

        // FIXED: use tx variable for horizontal offset (was ty incorrectly)
        double tx = result.getTx();            // Horizontal offset (deg)
        double distance = result.getTyNC(); // Distance in meters

        // PID calculations
        double forward = (distance - targetDistance) * kpForward;
        double strafe = (-tx) * kpStrafe;
        double turn   = (-tx) * kpTurn;

        forward = clip(forward, -0.45, 0.45);
        strafe  = clip(strafe,  -0.45, 0.45);
        turn    = clip(turn,    -0.35, 0.35);

        double fl = forward + strafe + turn;
        double bl = forward - strafe + turn;
        double fr = forward - strafe - turn;
        double br = forward + strafe - turn;

        setDrive(fl, fr, bl, br);

        telemetry.addLine("APRILTAG TRACKING");
        telemetry.addData("tx", tx);
        telemetry.addData("distance", distance);
    }

    // ============================================================
    // UTILITY
    // ============================================================
    private void setDrive(double fl, double fr, double bl, double br) {
        frontLeftMotor.setPower(fl);
        frontRightMotor.setPower(fr);
        backLeftMotor.setPower(bl);
        backRightMotor.setPower(br);
    }

    private double clip(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }
}
