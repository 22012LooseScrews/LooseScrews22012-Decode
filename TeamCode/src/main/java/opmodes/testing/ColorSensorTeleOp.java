// WHEN DONE TESTING REMOVE ALL THE EXTRA MAT OBJECTS
package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class ColorSensorTeleOp extends OpMode {
    OpenCvCamera webcam;
    MyPipeline pipeline;
    @Override
    public void init() {
        int webcam_ID = hardwareMap.appContext.getResources().getIdentifier("webcamMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 2"), webcam_ID);
        pipeline = new MyPipeline();
        webcam.setPipeline(pipeline);
        webcam.openCameraDevice();
        webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void loop(){
        telemetry.addData("Detected Color", pipeline.color_detected);
        telemetry.update();
    }
}

class MyPipeline extends OpenCvPipeline{
    public String color_detected = "None";
    @Override
    public Mat processFrame(Mat input) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_BGR2HSV);

        Scalar green_lower_bound = new Scalar(40,50,50);
        Scalar green_upper_bound = new Scalar(80,255,255);
        Scalar purple_lower_bound = new Scalar(130,50,50);
        Scalar purple_upper_bound = new Scalar(160,255,255);

        Mat green_binary_mask = new Mat();
        Mat purple_binary_mask = new Mat();
        Core.inRange(hsv, green_lower_bound, green_upper_bound, green_binary_mask);
        Core.inRange(hsv, purple_lower_bound, purple_upper_bound, purple_binary_mask);

        Mat eroded_green = new Mat();
        Mat dilated_green = new Mat();
        Mat eroded_purple = new Mat();
        Mat dilated_purple = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
        Imgproc.erode(green_binary_mask, eroded_green, kernel);
        Imgproc.dilate(eroded_green, dilated_green, kernel);
        Imgproc.erode(purple_binary_mask, eroded_purple, kernel);
        Imgproc.dilate(eroded_purple, dilated_purple, kernel);

        Mat green_hierarchy = new Mat();
        Mat purple_hierarchy = new Mat();
        List<MatOfPoint> green_contours = new ArrayList<>();
        List<MatOfPoint> purple_contours = new ArrayList<>();
        Imgproc.findContours(dilated_green, green_contours, green_hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.findContours(dilated_purple, purple_contours, purple_hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        double largest_green_area = 0;
        MatOfPoint largest_green_contour = null;
        double largest_purple_area = 0;
        MatOfPoint largest_purple_contour = null;
        for(MatOfPoint contour : green_contours){
            double green_contour_area = Imgproc.contourArea(contour);
            if(green_contour_area>largest_green_area){
                largest_green_area = green_contour_area;
                largest_green_contour = contour;
            }
        }

        for(MatOfPoint contour : purple_contours) {
            double purple_contour_area = Imgproc.contourArea(contour);
            if (purple_contour_area > largest_purple_area) {
                largest_purple_area = purple_contour_area;
                largest_purple_contour = contour;
            }
        }

        if(largest_green_area>largest_purple_area && largest_green_contour!=null){
            Rect rect = Imgproc.boundingRect(largest_green_contour);
            Point top_left = new Point(rect.x, rect.y);
            Point bottom_right = new Point(rect.x+rect.width, rect.y+rect.height);
            Imgproc.rectangle(input, top_left, bottom_right, new Scalar(0,255,0), 2);
            color_detected = "Green";
        }
        else if(largest_purple_area>largest_green_area && largest_purple_contour!=null){
            Rect rect = Imgproc.boundingRect(largest_purple_contour);
            Point top_left = new Point(rect.x, rect.y);
            Point bottom_right = new Point(rect.x+rect.width, rect.y+rect.height);
            Imgproc.rectangle(input, top_left, bottom_right, new Scalar(255,0,255), 2);
            color_detected = "Purple";


        }
        else{
            color_detected = "No Color Found";
        }
        hsv.release();
        green_binary_mask.release();
        purple_binary_mask.release();
        eroded_green.release();
        dilated_green.release();
        eroded_purple.release();
        dilated_purple.release();

        return input;
    }
}
