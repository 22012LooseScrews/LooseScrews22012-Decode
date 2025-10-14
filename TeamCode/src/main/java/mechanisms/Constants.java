package mechanisms;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static MecanumConstants drive_constants = new MecanumConstants()
            .rightFrontMotorName("frontRightMotor")
            .rightRearMotorName("backRightMotor")
            .leftFrontMotorName("frontLeftMotor")
            .leftRearMotorName("backLeftMotor")
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .maxPower(1.0);
    public static FollowerConstants follower_constants = new FollowerConstants()
            .mass(5.0);
    public static PathConstraints path_constraints = new PathConstraints(
            1.0,
            10.0
    );
    public static Follower createFollower(HardwareMap hardwareMap){
        return new FollowerBuilder(follower_constants, hardwareMap)
                .pathConstraints(path_constraints)
                .mecanumDrivetrain(drive_constants)
                .build();
    }
}
