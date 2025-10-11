//package mechanisms;
//
//import com.qualcomm.robotcore.hardware.DcMotorEx;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
//
//public class SampleMecanumDrive {
//    private Pose2D pose;
//    private DcMotorEx frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
//    public SampleMecanumDrive(HardwareMap hardware_map){
//        frontLeftMotor = hardware_map.get(DcMotorEx.class, "frontLeftMotor");
//        backLeftMotor = hardware_map.get(DcMotorEx.class, "backLeftMotor");
//        frontRightMotor = hardware_map.get(DcMotorEx.class, "frontRightMotor");
//        backRightMotor = hardware_map.get(DcMotorEx.class, "backRightMotor");
//
//        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//    }
//    public void setPose(Pose2D pose){
//        this.pose = pose;
//    }
//    public trajectoryBuilder(Pose2D start_pose){
//
//    }
//}
