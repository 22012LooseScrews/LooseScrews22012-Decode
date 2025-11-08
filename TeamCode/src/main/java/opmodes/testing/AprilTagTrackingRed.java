package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

/**
 * AprilTag Tracking TeleOp for testing.
 *
 * This OpMode tracks and faces AprilTag ID 21 from the 36h11 family when button A is held.
 * The robot will automatically turn to face the detected AprilTag.
 *
 * Make sure your Logitech C920 is configured as "Webcam 1" in the robot configuration.
 */
@TeleOp(name = "AprilTag Tracking Red", group = "Testing")
public class AprilTagTrackingRed extends OpMode {

    // AprilTag detection components
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    // Mecanum drive motors
    private DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;

    // Constants for tracking
    private static final int TARGET_TAG_ID = 24;
    private static final double TURN_POWER = 0.9;
    private static final double TOLERANCE_DEGREES = 5.0; // How close to target before stopping

    // Tracking state
    private boolean isTracking = false;
    private AprilTagDetection targetDetection = null;

    @Override
    public void init() {
        // Initialize Mecanum drive motors
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");

        // Set motor directions
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set brake behavior
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize AprilTag detection
        initAprilTag();

//        telemetry.addData("Status", "Initialized");
//        telemetry.addData("Target Tag", "ID " + TARGET_TAG_ID + " (36h11 family)");
//        telemetry.addData("Controls", "Hold A to track and face the tag");
//        telemetry.update();
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

        // Check if button A is held for tracking
        isTracking = gamepad1.circle;

        if (isTracking) {
            // Find and track the target AprilTag
            trackAprilTag();
        } else {
            // Stop all motors when not tracking
            stopMotors();
            targetDetection = null;
        }

        // Display telemetry
        displayTelemetry();
        telemetry.update();
    }

    /**
     * Initialize the AprilTag processor with 36h11 family
     */
    private void initAprilTag() {
        // Create the AprilTag processor with 36h11 family
        aprilTag = new AprilTagProcessor.Builder()
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .build();

        // Create the vision portal with Logitech C920 webcam
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();
    }

    /**
     * Track the target AprilTag and face it
     */
    private void trackAprilTag() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        // Find the target tag (ID 21)
        targetDetection = null;
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == TARGET_TAG_ID) {
                targetDetection = detection;
                break;
            }
        }

        if (targetDetection != null) {
            // Calculate the bearing to the tag (yaw angle)
            double bearing = targetDetection.ftcPose.bearing+0.9;

            // Check if we're close enough to the target orientation
            if (Math.abs(bearing) > TOLERANCE_DEGREES) {
                // Turn to face the tag
                if (bearing > 0) {
                    // Tag is to the right, turn right
                    turnRight(TURN_POWER);
                } else {
                    // Tag is to the left, turn left
                    turnLeft(TURN_POWER);
                }
            } else {
                // We're facing the tag, stop turning
                stopMotors();
            }
        } else {
            // Target tag not detected, stop motors
            stopMotors();
        }
    }

    /**
     * Turn the robot right
     */
    private void turnRight(double power) {
        frontLeftMotor.setPower(1);
        backLeftMotor.setPower(1);
        frontRightMotor.setPower(-1);
        backRightMotor.setPower(-1);
    }

    /**
     * Turn the robot left
     */
    private void turnLeft(double power) {
        frontLeftMotor.setPower(-1);
        backLeftMotor.setPower(-1);
        frontRightMotor.setPower(1);
        backRightMotor.setPower(1);
    }

    /**
     * Stop all drive motors
     */
    private void stopMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    /**
     * Display telemetry information
     */
    private void displayTelemetry() {
        telemetry.addData("Tracking Active", isTracking);

        if (targetDetection != null) {
            telemetry.addData("Target Tag", "ID " + TARGET_TAG_ID + " DETECTED");
            telemetry.addData("Bearing", "%.1f degrees", targetDetection.ftcPose.bearing);
            telemetry.addData("Range", "%.1f inches", targetDetection.ftcPose.range);
            telemetry.addData("Elevation", "%.1f degrees", targetDetection.ftcPose.elevation);

            // Show turn direction
            if (Math.abs(targetDetection.ftcPose.bearing) > TOLERANCE_DEGREES) {
                if (targetDetection.ftcPose.bearing > 0) {
                    telemetry.addData("Action", "Turning RIGHT");
                } else {
                    telemetry.addData("Action", "Turning LEFT");
                }
            } else {
                telemetry.addData("Action", "FACING TARGET");
            }
        } else {
            telemetry.addData("Target Tag", "ID " + TARGET_TAG_ID + " NOT DETECTED");
            telemetry.addData("Action", "Searching...");
        }

        telemetry.addLine("---");
        telemetry.addData("Total Tags Detected", aprilTag.getDetections().size());

        // Show all detected tags
        List<AprilTagDetection> allDetections = aprilTag.getDetections();
        if (!allDetections.isEmpty()) {
            telemetry.addLine("All Detected Tags:");
            for (AprilTagDetection detection : allDetections) {
                telemetry.addLine(String.format("  ID %d (%.1f° bearing)", detection.id, detection.ftcPose.bearing));
            }
        }
    }

    @Override
    public void stop() {
        // Clean up resources
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}