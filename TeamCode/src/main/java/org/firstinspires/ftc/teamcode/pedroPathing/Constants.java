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
            .maxPower(1.0)
            .xVelocity(66.70028277980974)
            .yVelocity(55.36111188046402);
    public static FollowerConstants follower_constants = new FollowerConstants()
            .mass(11.34)
            .centripetalScaling(0.0005)
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.6, 0, 0.0001, 0.6, 0.025))
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
    public static ThreeWheelConstants odo_wheel_constants = new ThreeWheelConstants()
            .forwardTicksToInches(0.0020031719903638194)
            .strafeTicksToInches(0.002012573702528496)
            .turnTicksToInches(0.0019443986361740578)
            .leftEncoder_HardwareMapName("frontLeftMotor")
            .rightEncoder_HardwareMapName("backRightMotor")
            .strafeEncoder_HardwareMapName("backLeftMotor")
            .leftPodY(7)
            .rightPodY(-7)
            .strafePodX(-5.5)
            .leftEncoderDirection(Encoder.REVERSE)
            .rightEncoderDirection(Encoder.REVERSE)
            .strafeEncoderDirection(Encoder.REVERSE);
    public static Follower createFollower(HardwareMap hardwareMap){
        return new FollowerBuilder(follower_constants, hardwareMap)
                .mecanumDrivetrain(drive_constants)
                .threeWheelLocalizer(odo_wheel_constants)
                .build();
    }
}


