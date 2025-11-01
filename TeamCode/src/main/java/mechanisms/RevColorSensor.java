package mechanisms;

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
        revColorSensor = hardwareMap.get(NormalizedColorSensor.class, "revColorSensor");
        /* revColorSensor.setGain(8);
           - .setGain() is for adjusting lighting, more lighting means more sensitivity to light
         */
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

        if((normGreen > normRed) && (normGreen > normBlue)){
            return DetectedColor.GREEN;
        }
        else if((normRed > normGreen) && (normBlue > normGreen)){
            return DetectedColor.PURPLE;
        }
        else {
            return DetectedColor.UNKNOWN;
        }
    }

}
