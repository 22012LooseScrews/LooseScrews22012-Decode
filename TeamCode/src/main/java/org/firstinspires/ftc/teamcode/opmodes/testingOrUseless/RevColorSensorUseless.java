//package org.firstinspires.ftc.teamcode.opmodes.testingOrUseless;
//
//import com.bylazar.telemetry.PanelsTelemetry;
//import com.bylazar.telemetry.TelemetryManager;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
//import com.qualcomm.robotcore.hardware.NormalizedRGBA;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//
//public class RevColorSensorUseless {
//    NormalizedColorSensor revColorSensor;
//    public TelemetryManager telemetryM;
//    public enum DetectedColor{
//        GREEN,
//        PURPLE,
//        UNKNOWN
//    }
//    public void init(HardwareMap hardwareMap){
//        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
//        revColorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
//        revColorSensor.setGain(4);
//    }
//    public DetectedColor getDetectedColor(Telemetry telemetry){
//        NormalizedRGBA colors = revColorSensor.getNormalizedColors();
//
//        float normRed, normBlue, normGreen;
//        normRed = colors.red / colors.alpha;
//        normBlue = colors.blue / colors.alpha;
//        normGreen = colors.green / colors.alpha;
//
//        telemetry.addData("Red:", normRed);
//        telemetry.addData("Blue: ", normBlue);
//        telemetry.addData("Green", normGreen);
//
//        if(normRed >= 0.06 && normRed <= 0.073 && normBlue >= 0.0779 && normBlue <= 0.1224 && normGreen >= 0.12 && normGreen <= 0.1419){
//            return DetectedColor.GREEN;
//        }
//        else if(normRed >= 0.07 && normRed <= 0.0833 && normBlue >= 0.089 && normBlue <= 0.13 && normGreen >= 0.09 && normGreen <= 0.13){
//            return DetectedColor.PURPLE;
//        }
//        else{
//            return DetectedColor.UNKNOWN;
//        }
//    }
//    public DetectedColor getDetectedColor2(TelemetryManager telemetryM){
//        NormalizedRGBA colors = revColorSensor.getNormalizedColors();
//
//        float normRed, normBlue, normGreen;
//        normRed = colors.red / colors.alpha;
//        normBlue = colors.blue / colors.alpha;
//        normGreen = colors.green / colors.alpha;
//
//        telemetryM.debug("Red:", normRed);
//        telemetryM.debug("Blue: ", normBlue);
//        telemetryM.debug("Green", normGreen);
//
//        if(normRed >= 0.06 && normRed <= 0.073 && normBlue >= 0.0779 && normBlue <= 0.1224 && normGreen >= 0.12 && normGreen <= 0.1419){
//            return DetectedColor.GREEN;
//        }
//        else if(normRed >= 0.07 && normRed <= 0.0833 && normBlue >= 0.089 && normBlue <= 0.13 && normGreen >= 0.09 && normGreen <= 0.13){
//            return DetectedColor.PURPLE;
//        }
//        else{
//            return DetectedColor.UNKNOWN;
//        }
//    }
//}