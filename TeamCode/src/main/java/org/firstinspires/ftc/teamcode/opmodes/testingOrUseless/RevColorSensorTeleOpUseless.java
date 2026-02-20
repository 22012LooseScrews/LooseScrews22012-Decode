//package org.firstinspires.ftc.teamcode.opmodes.testingOrUseless;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.opmodes.testingOrUseless.RevColorSensor;
//@Disabled
//@TeleOp
//public class RevColorSensorTeleOpUseless extends OpMode {
//    RevColorSensor color_sensor = new RevColorSensor();
//    RevColorSensor.DetectedColor detectedColor;
//    @Override
//    public void init() {
//        color_sensor.init(hardwareMap);
//    }
//
//    @Override
//    public void loop() {
//        detectedColor = color_sensor.getDetectedColor(telemetry);
//        telemetry.addData("Detected Color", detectedColor);
//    }
//}
