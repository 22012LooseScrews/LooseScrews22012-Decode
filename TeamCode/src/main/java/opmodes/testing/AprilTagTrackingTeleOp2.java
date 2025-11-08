/*
package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.opencv.core.Size;

import java.util.List;

*/
/**
 * AprilTag Tracking TeleOp for testing.
 *
 * This OpMode tracks and faces AprilTag ID 21 from the 36h11 family when button A is held.
 * The robot will automatically turn to face the detected AprilTag.
 *
 * Make sure your Logitech C920 is configured as "Webcam 1" in the robot configuration.
 *//*

@TeleOp(name = "AprilTag Tracking Test", group = "Testing")
public class AprilTagTrackingTeleOp2 extends OpMode {

    // AprilTag detection components
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    // Mecanum drive motors
    private DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;

    // Constants for tracking
    private static final int TARGET_TAG_ID = 24;
    private static final double TURN_POWER = 0.8; // Increased from 0.3 to match normal driving speed
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

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Target Tag", "ID " + TARGET_TAG_ID + " (36h11 family)");
        telemetry.addData("Controls", "Hold A to track and face the tag");
        telemetry.update();
    }

    @Override
    public void loop() {
        // Check if button A is held for tracking
        isTracking = gamepad1.a;

        if (isTracking) {
            // Find and track the target AprilTag
            trackAprilTag();
        } else {
            // Stop all motors when not tracking
            stopMotors();
            targetDetection = null;
        }

        // Display telemetry (reduced frequency to improve performance)
        if (opModeIsActive()) {
            displayTelemetry();
            telemetry.update();
        }

        // Add small sleep to prevent overwhelming the control hub
        sleep(10);
    }

    */
/**
     * Initialize the AprilTag processor with 36h11 family (optimized for performance)
     *//*

    private void initAprilTag() {
        // Create the AprilTag processor with 36h11 family and optimized settings
        aprilTag = new AprilTagProcessor.Builder()
                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(false) // Disable axes drawing for better performance
                .setDrawCubeProjection(false) // Disable cube projection for better performance
                .build();

        // Create the vision portal with optimized settings
        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .setCameraResolution(new Size(640, 480)) // Lower resolution for better performance
                .setStreamFormat(VisionPortal.StreamFormat.YUY2) // More efficient format
                .enableLiveView(false) // Disable live view to save processing power
                .setAutoStopLiveView(false)
                .build();
    }

    */
/**
     * Track the target AprilTag and face it
     *//*

    private void trackAprilTag() {
        // Only get detections if we don't have a current target or it's been a while
        if (targetDetection == null || !aprilTag.getDetections().contains(targetDetection)) {
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();

            // Find the target tag (ID 24) - use stream for better performance
            targetDetection = currentDetections.stream()
                    .filter(detection -> detection.id == TARGET_TAG_ID)
                    .findFirst()
                    .orElse(null);
        }

        if (targetDetection != null) {
            // Calculate the bearing to the tag (yaw angle)
            double bearing = targetDetection.ftcPose.bearing;

            // Check if we're close enough to the target orientation
            if (Math.abs(bearing) > TOLERANCE_DEGREES) {
                // Turn to face the tag with proportional power for smoother movement
                double turnPower = Math.min(TURN_POWER, Math.abs(bearing) / 30.0 * TURN_POWER);
                if (bearing > 0) {
                    // Tag is to the right, turn right
                    turnRight(turnPower);
                } else {
                    // Tag is to the left, turn left
                    turnLeft(turnPower);
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

    */
/**
     * Turn the robot right
     *//*

    private void turnRight(double power) {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
    }

    */
/**
     * Turn the robot left
     *//*

    private void turnLeft(double power) {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    */
/**
     * Stop all drive motors
     *//*

    private void stopMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    */
/**
     * Display telemetry information (optimized for performance)
     *//*

    private void displayTelemetry() {
        telemetry.addData("Tracking", isTracking ? "ON" : "OFF");

        if (targetDetection != null) {
            telemetry.addData("Tag", "ID " + TARGET_TAG_ID + " DETECTED");
            telemetry.addData("Bearing", "%.1f°", targetDetection.ftcPose.bearing);

            // Show turn direction
            if (Math.abs(targetDetection.ftcPose.bearing) > TOLERANCE_DEGREES) {
                telemetry.addData("Action", targetDetection.ftcPose.bearing > 0 ? "RIGHT" : "LEFT");
            } else {
                telemetry.addData("Action", "FACING");
            }
        } else {
            telemetry.addData("Tag", "ID " + TARGET_TAG_ID + " NOT FOUND");
            telemetry.addData("Action", "SEARCHING");
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
*/
