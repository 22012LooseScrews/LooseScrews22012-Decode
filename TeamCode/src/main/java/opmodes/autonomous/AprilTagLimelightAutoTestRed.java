package opmodes.autonomous;

import com.qualcomm.hardware.limelightvision.LLResult;

import com.qualcomm.hardware.limelightvision.Limelight3A;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "AprilTag Limelight Auto Test Red")

public class AprilTagLimelightAutoTestRed extends LinearOpMode {

    private Limelight3A limelight;

    private DcMotor frontLeft, frontRight, backLeft, backRight;

    private static final double KP_TURN = 0.035;

    private static final double MIN_TURN_POWER = 0.08;

    @Override

    public void runOpMode() throws InterruptedException {

        // === Initialize drivetrain ===

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");

        frontRight = hardwareMap.get(DcMotor.class, "frontRightMotor");

        backLeft = hardwareMap.get(DcMotor.class, "backLeftMotor");

        backRight = hardwareMap.get(DcMotor.class, "backRightMotor");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // === Initialize Limelight ===

        limelight = hardwareMap.get(Limelight3A.class, "Limelight");

        limelight.pipelineSwitch(0);

        limelight.start();

        sleep(1000); // give time for Limelight to start

        telemetry.addLine("Red Auto Initialized. Waiting for start...");

        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        // Step 1: Try detecting AprilTag with timeout

        LLResult result = null;

        long startTime = System.currentTimeMillis();

        while (opModeIsActive() && result == null && System.currentTimeMillis() - startTime < 3000) {

            LLResult temp = limelight.getLatestResult();

            if (temp != null && temp.isValid()) result = temp;

            telemetry.addLine("Searching for AprilTag...");

            telemetry.addData("Result valid?", (temp != null && temp.isValid()));

            telemetry.update();

            sleep(50);

        }

        if (result != null && result.isValid()) {

            telemetry.addData("Tag Detected", true);

            telemetry.addData("tx", result.getTx());

            telemetry.addData("ty", result.getTy());

            telemetry.addData("ta", result.getTa());

            telemetry.update();

        } else {

            telemetry.addLine("No tag detected - proceeding anyway");

            telemetry.update();

        }

        // Step 2: Drive forward a fixed distance

        driveForward(0.5, 1500);

        // Step 3: Align if tag was seen

        if (result != null && result.isValid()) {

            autoAlignToTag(result.getTx());

        }

        telemetry.addLine("Scoring...");

        telemetry.update();

        sleep(1000);

        stopAll();

    }

    private void driveForward(double power, long timeMs) {

        frontLeft.setPower(power);

        backLeft.setPower(power);

        frontRight.setPower(power);

        backRight.setPower(power);

        sleep(timeMs);

        stopAll();

    }

    private void autoAlignToTag(double tx) {

        telemetry.addLine("Aligning to AprilTag...");

        telemetry.update();

        while (opModeIsActive()) {

            LLResult res = limelight.getLatestResult();

            if (res == null || !res.isValid()) {

                stopAll();

                telemetry.addLine("Tag lost, stopping");

                telemetry.update();

                break;

            }

            double error = res.getTx();

            if (Math.abs(error) < 1.0) {

                stopAll();

                telemetry.addLine("Aligned!");

                telemetry.update();

                break;

            }

            double turnPower = KP_TURN * error;

            if (turnPower > 0) turnPower += MIN_TURN_POWER;

            else turnPower -= MIN_TURN_POWER;

            frontLeft.setPower(turnPower);

            backLeft.setPower(turnPower);

            frontRight.setPower(-turnPower);

            backRight.setPower(-turnPower);

        }

    }

    private void stopAll() {

        frontLeft.setPower(0);

        frontRight.setPower(0);

        backLeft.setPower(0);

        backRight.setPower(0);

    }

}

