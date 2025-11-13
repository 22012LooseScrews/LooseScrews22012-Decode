package opmodes.autonomous;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class AprilTagLimelightAutoTestRed extends LinearOpMode {

    private Limelight3A limelight;
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    // PID constants for alignment
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
        limelight.pipelineSwitch(0); // AprilTag pipeline
        limelight.start();

        telemetry.addLine("Red Auto Initialized. Waiting for start...");
        telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        // Step 1: Detect AprilTag
        LLResult result = null;
        while (opModeIsActive() && result == null) {
            LLResult temp = limelight.getLatestResult();
            if (temp != null && temp.isValid()) result = temp;
            telemetry.addLine("Searching for AprilTag...");
            telemetry.update();
        }

        if (result != null) {
            telemetry.addData("Tag Detected", true);
            telemetry.addData("tx", result.getTx());
            telemetry.addData("ty", result.getTy());
            telemetry.addData("ta", result.getTa());
            telemetry.update();
        } else {
            telemetry.addLine("No tag detected - proceeding blindly");
            telemetry.update();
        }

        // Step 2: Drive from (123,123) → (96,96)
        driveToPoint(123.36, 123.36, 96, 96, 0.5);

        // Step 3: Align with detected AprilTag
        if (result != null && result.isValid()) {
            autoAlignToTag(result.getTx());
        }

        // Step 4: Perform scoring or drop sequence
        telemetry.addLine("Performing scoring sequence...");
        telemetry.update();
        sleep(1000);

        stopAll();
    }

    private void driveToPoint(double startX, double startY, double endX, double endY, double speed) {
        double dx = endX - startX;
        double dy = endY - startY;
        double distance = Math.hypot(dx, dy);

        long driveTime = (long) (distance * 15); // tune scaling per robot

        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);

        sleep(driveTime);
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

            // Apply turn correction
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
