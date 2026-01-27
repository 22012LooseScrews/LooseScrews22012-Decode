package org.firstinspires.ftc.teamcode.abstractions;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RevColorSensor {
    NormalizedColorSensor revColorSensor;
    public enum DetectedColor{
        GREEN,
        PURPLE,
        UNKNOWN
    }
    public void init(HardwareMap hardwareMap){
        revColorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");
        revColorSensor.setGain(4);
    }
    public DetectedColor getDetectedColor(Telemetry telemetry){
        NormalizedRGBA colors = revColorSensor.getNormalizedColors();

        float normRed, normBlue, normGreen;
        normRed = colors.red / colors.alpha;
        normBlue = colors.blue / colors.alpha;
        normGreen = colors.green / colors.alpha;

        telemetry.addData("Red:", normRed);
        telemetry.addData("Blue: ", normBlue);
        telemetry.addData("Green", normGreen);

        if(normRed >= 0.014 && normRed <= 0.037 && normBlue >= 0.035 && normBlue <= 0.063 && normGreen >= 0.0455 && normGreen <= 0.07){
            return DetectedColor.GREEN;
        }
        else if(normRed >= 0.023 && normRed <= 0.032 && normBlue >= 0.0425 && normBlue <= 0.054 && normGreen >= 0.027 && normGreen <= 0.0414){
            return DetectedColor.PURPLE;
        }
        else{
            return DetectedColor.UNKNOWN;
        }
    }
}