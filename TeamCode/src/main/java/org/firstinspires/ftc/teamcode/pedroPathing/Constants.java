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
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .maxPower(1)
            .xVelocity(90.36711618911659)
            .yVelocity(62.76004559496336);
    public static FollowerConstants follower_constants = new FollowerConstants()
            .mass(10.6)
            .centripetalScaling(0.0005)
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.01, 0, 0, 0.6, 0.025))
            .headingPIDFCoefficients(new PIDFCoefficients(0.59, 0, 0.02, 0.025))
            .translationalPIDFCoefficients(new PIDFCoefficients(0.046, 0, 0, 0.025))
            .forwardZeroPowerAcceleration(-39.12631581708491)
            .lateralZeroPowerAcceleration(-80.6827746044343);

    public static PathConstraints path_constraints = new PathConstraints(
            0.995,
            100,
            1.67,
            1
    );
    public static ThreeWheelConstants three_wheel_constants = new ThreeWheelConstants()
            .forwardTicksToInches(0.0019948597345897812)
            .strafeTicksToInches(0.0019659617285042587)
            .turnTicksToInches(0.0019686551902422284)
            .leftEncoder_HardwareMapName("backLeftMotor")
            .rightEncoder_HardwareMapName("frontLeftMotor")
            .strafeEncoder_HardwareMapName("backRightMotor")
            .leftPodY(3.875)
            .rightPodY(-3.875)
            .strafePodX(-5.5)
            .leftEncoderDirection(Encoder.REVERSE)
            .rightEncoderDirection(Encoder.REVERSE)
            .strafeEncoderDirection(Encoder.FORWARD);

    public static Follower createFollower(HardwareMap hardwareMap){
        return new FollowerBuilder(follower_constants, hardwareMap)
                .mecanumDrivetrain(drive_constants)
                .threeWheelLocalizer(three_wheel_constants)
                .build();
    }
}


