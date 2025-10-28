package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import mechanisms.ColorSensor;

/**
 * Test teleop for Rev Color Sensor
 * Displays detected ball color: "Green", "Purple", or "none"
 */
@TeleOp(name = "Color Sensor Test", group = "Testing")
public class ColorSensorTestTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Initialize color sensor (replace "colorSensor" with your hardware map name)
        ColorSensor sensor = new mechanisms.ColorSensor(hardwareMap, "colorSensor");

        telemetry.addData("Status", "Ready to start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Get detected color
            String detectedColor = sensor.getBallColor();

            // Get raw values for debugging
            int[] rgb = sensor.getRGB();
            float hue = sensor.getHue();

            // Display results
            telemetry.addLine("=== COLOR SENSOR TEST ===");
            telemetry.addData("Detected Color", detectedColor);
            telemetry.addLine();
            telemetry.addData("Hue", String.format("%.2f", hue));
            telemetry.addData("Red", rgb[0]);
            telemetry.addData("Green", rgb[1]);
            telemetry.addData("Blue", rgb[2]);
            telemetry.update();
        }
    }
}

