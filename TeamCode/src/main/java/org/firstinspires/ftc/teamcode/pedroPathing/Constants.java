package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.ThreeWheelConstants;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
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
            .maxPower(1.0)
            .xVelocity(66.70028277980974)
            .yVelocity(55.36111188046402);
    public static FollowerConstants follower_constants = new FollowerConstants()
            .mass(10.6)
            .centripetalScaling(0.0005)
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.3, 0, 0.002, 0.6, 0.025))
            .headingPIDFCoefficients(new PIDFCoefficients(0.6, 0, 0.01, 0.025))
            .translationalPIDFCoefficients(new PIDFCoefficients(0.06, 0, 0.01, 0.02))
            .forwardZeroPowerAcceleration(-32.372163315984494)
            .lateralZeroPowerAcceleration(-78.47962665767436);
    public static PathConstraints path_constraints = new PathConstraints(
            0.995,
            100,
            1.2,
            1
    );
    public static ThreeWheelConstants three_wheel_constants = new ThreeWheelConstants()
            .forwardTicksToInches(48.02418594678925)
            .strafeTicksToInches(0.002012573702528496)
            .turnTicksToInches(0.0019443986361740578)
            .leftEncoder_HardwareMapName("backLeftMotor")
            .rightEncoder_HardwareMapName("frontLeftMotor")
            .strafeEncoder_HardwareMapName("frontRightMotor")
            .leftPodY(3.75)
            .rightPodY(-3.75)
            .strafePodX(-7.25)
            .leftEncoderDirection(Encoder.FORWARD)
            .rightEncoderDirection(Encoder.FORWARD)
            .strafeEncoderDirection(Encoder.REVERSE);

//    public static TwoWheelConstants two_wheel_constants = new TwoWheelConstants()
//            .forwardEncoder_HardwareMapName("frontLeftMotor")
//            .strafeEncoder_HardwareMapName("frontRightMotor")
//            .forwardTicksToInches(-0.007524019991039439)
//            .strafeTicksToInches()
//            .forwardEncoderDirection(Encoder.FORWARD)
//            .strafeEncoderDirection(Encoder.FORWARD)
//            .forwardPodY(3.75)
//            .strafePodX(7.25)
//            .IMU_HardwareMapName("imu")
//            .IMU_Orientation(
//                    new RevHubOrientationOnRobot(
//                            RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
//                            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
//                    )
//            );

    public static Follower createFollower(HardwareMap hardwareMap){
        return new FollowerBuilder(follower_constants, hardwareMap)
                .mecanumDrivetrain(drive_constants)
                .threeWheelLocalizer(three_wheel_constants)
                .build();
    }
}


