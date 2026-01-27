package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.abstractions.RevColorSensor;

@TeleOp
public class RevColorSensorTeleOp extends OpMode {
    RevColorSensor color_sensor = new RevColorSensor();
    RevColorSensor.DetectedColor detectedColor;
    @Override
    public void init() {
        color_sensor.init(hardwareMap);
    }

    @Override
    public void loop() {
        detectedColor = color_sensor.getDetectedColor(telemetry);
        telemetry.addData("Detected Color", detectedColor);
    }
}
