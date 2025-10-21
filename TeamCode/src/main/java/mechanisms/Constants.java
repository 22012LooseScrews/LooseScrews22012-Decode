package mechanisms;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class  Constants {
    public static MecanumConstants drive_constants = new MecanumConstants()
            .rightFrontMotorName("frontRightMotor")
            .rightRearMotorName("backRightMotor")
            .leftFrontMotorName("frontLeftMotor")
            .leftRearMotorName("backLeftMotor")
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .maxPower(1.0);
    public static FollowerConstants follower_constants = new FollowerConstants()
            .mass(7.0);
    public static PathConstraints path_constraints = new PathConstraints(
            1.0,
            10.0
    );
    public static ThreeWheelConstants odo_wheel_constants = new ThreeWheelConstants()
            .forwardTicksToInches(0.001979)
            .strafeTicksToInches(0.001979)
            .turnTicksToInches(0.001979)
            .leftEncoder_HardwareMapName("frontLeftMotor")
            .rightEncoder_HardwareMapName("backLeftMotor")
            .strafeEncoder_HardwareMapName("frontRightMotor")
            .leftPodY(8.5)
            .rightPodY(-8.5)
            .strafePodX(-9.0)
            .leftEncoderDirection(Encoder.FORWARD)
            .rightEncoderDirection(Encoder.FORWARD)
            .strafeEncoderDirection(Encoder.FORWARD);
    public static Follower createFollower(HardwareMap hardwareMap){
        return new FollowerBuilder(follower_constants, hardwareMap)
                .pathConstraints(path_constraints)
                .mecanumDrivetrain(drive_constants)
                .threeWheelLocalizer(odo_wheel_constants)
                .build();
    }
}
