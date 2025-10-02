package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

/**
 * Simple AprilTag detection test for Logitech C920 webcam.
 * 
 * This OpMode continuously searches for AprilTags from the 36h11 family
 * and displays the name of any detected tags in telemetry.
 * 
 * Make sure your Logitech C920 is configured as "Webcam 1" in the robot configuration.
 */
@TeleOp(name = "AprilTag Test", group = "Testing")
public class AprilTagTest extends LinearOpMode {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {
        
        // Initialize AprilTag detection
        initAprilTag();

        // Wait for the DS start button to be touched.
        telemetry.addData("Camera Status", "Initialized");
        telemetry.addData("AprilTag Family", "36h11");
        telemetry.addData(">", "Touch START to begin detection");
        telemetry.update();
        
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                
                // Display detected AprilTags
                displayAprilTags();
                
                // Push telemetry to the Driver Station
                telemetry.update();

                // Share the CPU
                sleep(20);
            }
        }

        // Clean up resources
        visionPortal.close();
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
     * Display detected AprilTags in telemetry
     */
    private void displayAprilTags() {
        
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("AprilTags Detected", currentDetections.size());

        if (currentDetections.isEmpty()) {
            telemetry.addLine("No AprilTags detected");
        } else {
            // Display each detected AprilTag
            for (AprilTagDetection detection : currentDetections) {
                if (detection.metadata != null) {
                    // Display the name of the detected AprilTag
                    telemetry.addLine(String.format("Detected: %s (ID: %d)", 
                        detection.metadata.name, detection.id));
                } else {
                    // Unknown tag (not in library)
                    telemetry.addLine(String.format("Unknown Tag (ID: %d)", detection.id));
                }
            }
        }
        
        telemetry.addLine("\n---");
        telemetry.addLine("Looking for 36h11 AprilTags...");
    }
}
