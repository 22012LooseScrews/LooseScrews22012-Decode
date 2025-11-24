//package opmodes.testing;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.LLStatus;
//import com.qualcomm.hardware.limelightvision.LLResultTypes;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
//import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//@Autonomous
//public class RealDetection extends LinearOpMode {
//
//    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor, intakeMotor;
//    private Limelight3A Limelight;
//
//    private IMU imu;
//
//    @Override
//    public void init() {
//        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
//        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
//        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
//        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
//    }
//    public void runOpMode() throws InterruptedException
//    {
//        Limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        telemetry.setMsTransmissionInterval(11);
//        Limelight.pipelineSwitch(0);
//        Limelight.start();
//
//        while (opModeIsActive()) {
//            LLResult result = Limelight.getLatestResult();
//            if (result != null) {
//                if (result.isValid()) {
//                    Pose3D botpose = result.getBotpose();
//                    telemetry.addData("tx", result.getTx());
//                    telemetry.addData("ty", result.getTy());
//                }
//            }
//            while (opModeIsActive()) {
//                YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
//                Limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));
//                LLResult LLResult = Limelight.getLatestResult();
//                if (result != null) {
//                    if (result.isValid()) {
//                        Pose3D botpose = result.getBotpose_MT2();
//                        // Use botpose data
//                    }
//                }
//            }
//        }
//    }
//}