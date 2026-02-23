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
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE);
//            .maxPower(1.0)
//            .xVelocity(66.70028277980974)
//            .yVelocity(55.36111188046402);
    public static FollowerConstants follower_constants = new FollowerConstants()
            .mass(10.6);
//            .centripetalScaling(0.0005)
//            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.3, 0, 0.002, 0.6, 0.025))
//            .headingPIDFCoefficients(new PIDFCoefficients(0.6, 0, 0.01, 0.025))
//            .translationalPIDFCoefficients(new PIDFCoefficients(0.06, 0, 0.01, 0.02))
//            .forwardZeroPowerAcceleration(-32.372163315984494)
//            .lateralZeroPowerAcceleration(-78.47962665767436);

    public static PathConstraints path_constraints = new PathConstraints(
            0.995,
            100,
            1.2,
            1
    );
    public static ThreeWheelConstants three_wheel_constants = new ThreeWheelConstants()
            .forwardTicksToInches(-43.5287932607343)
            .strafeTicksToInches(-43.5287932607343)
            .turnTicksToInches(-43.5287932607343)
            .leftEncoder_HardwareMapName("backLeftMotor")
            .rightEncoder_HardwareMapName("frontRightMotor")
            .strafeEncoder_HardwareMapName("frontLeftMotor")
            .leftPodY(3.75)
            .rightPodY(-3.75)
            .strafePodX(-7.25)
            .leftEncoderDirection(Encoder.FORWARD)
            .rightEncoderDirection(Encoder.FORWARD)
            .strafeEncoderDirection(Encoder.FORWARD);


    public static Follower createFollower(HardwareMap hardwareMap){
        return new FollowerBuilder(follower_constants, hardwareMap)
                .mecanumDrivetrain(drive_constants)
                .threeWheelLocalizer(three_wheel_constants)
                .build();
    }
}


