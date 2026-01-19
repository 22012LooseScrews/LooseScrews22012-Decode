//package opmodes.testing;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import mechanisms.RevColorSensorUseless;
//
//@TeleOp
//public class RevColorSensorTeleOpUseless extends OpMode {
//    RevColorSensorUseless color_sensor = new RevColorSensorUseless();
//    RevColorSensorUseless.DetectedColor detectedColor;
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
