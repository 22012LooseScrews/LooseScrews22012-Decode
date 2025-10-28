package mechanisms;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Utility class for working with Rev Color Sensor
 * Provides ball color detection for the FTC Decode competition
 */
public class ColorSensor {
    private final ColorSensor colorSensor;

    public ColorSensor(HardwareMap hardwareMap, String hardwareMapName) {
        this.colorSensor = hardwareMap.get(ColorSensor.class, hardwareMapName);
        // Enable LED for better detection
        colorSensor.enableLed(true);
    }

    /**
     * Get the detected ball color
     * @return "Green", "Purple", or "none"
     */
    public String getBallColor() {
        // Get hue value (0-360)
        float[] hsv = colorSensor.getNormalizedColors().toHsv();
        float hue = hsv[0];

        // Determine color based on hue
        // Green typically ranges from 60-150 degrees in HSV
        // Purple typically ranges from 270-330 degrees in HSV
        if (hue >= 60 && hue <= 150) {
            return "Green";
        } else if (hue >= 270 || hue <= 30) { // Purple wraps around (270-360 and 0-30)
            return "Purple";
        } else {
            return "none";
        }
    }

    /**
     * Get the raw hue value
     * @return hue value (0-360)
     */
    public float getHue() {
        return colorSensor.getNormalizedColors().toHsv()[0];
    }

    /**
     * Get RGB values
     * @return array of [Red, Green, Blue] values (0-255)
     */
    public int[] getRGB() {
        int[] rgb = new int[3];
        rgb[0] = colorSensor.red();
        rgb[1] = colorSensor.green();
        rgb[2] = colorSensor.blue();
        return rgb;
    }

    /**
     * Enable or disable the LED
     */
    public void setLedEnabled(boolean enabled) {
        colorSensor.enableLed(enabled);
    }

    /**
     * Get the underlying ColorSensor hardware device
     */
    public ColorSensor getHardwareDevice() {
        return colorSensor;
    }
}

