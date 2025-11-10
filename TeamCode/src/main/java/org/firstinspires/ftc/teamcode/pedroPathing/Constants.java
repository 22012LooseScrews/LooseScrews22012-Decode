package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
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
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .maxPower(1.0);
    // Change this later        .xVelocity(78.884)
    // Change this later        .yVelocity(28.054);

    public static FollowerConstants follower_constants = new FollowerConstants()
            .mass(11.34);
    // Change this later        .centripetalScaling(0.0005)
    // Change this later        .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.6, 0, 0.0001, 0.6, 0.025))
    // Change this later        .headingPIDFCoefficients(new PIDFCoefficients(0.71, 0, 0.002, 0.025))
    // Change this later        .translationalPIDFCoefficients(new PIDFCoefficients(0.06, 0, 0.001, 0.025))
    // Change this later        .forwardZeroPowerAcceleration(-26.146);
    // Change this later        .lateralZeroPowerAcceleration(-92.162)

    public static PathConstraints path_constraints = new PathConstraints(
            3.4,
            10
    );
    public static ThreeWheelConstants odo_wheel_constants = new ThreeWheelConstants()
            .forwardTicksToInches(0.001979)
            .strafeTicksToInches(0.001979)
            .turnTicksToInches(0.001979)
            .leftEncoder_HardwareMapName("frontLeftMotor")
            .rightEncoder_HardwareMapName("backLeftMotor")
            .strafeEncoder_HardwareMapName("frontRightMotor")
            .leftPodY(7.25)
            .rightPodY(-7.25)
            .strafePodX(-5.5)
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


