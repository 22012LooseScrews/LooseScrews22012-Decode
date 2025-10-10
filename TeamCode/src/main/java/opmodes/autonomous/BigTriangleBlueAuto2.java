package opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class BigTriangleBlueAuto2 extends LinearOpMode {

    // --- Declare motors ---
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    // --- Encoder & Movement Constants ---
    static final double COUNTS_PER_MOTOR_REV = 384.5 * 4;  // 1538 ticks per revolution (NeveRest 20)
    static final double DRIVE_GEAR_REDUCTION = 1.0;         // No gear reduction
    static final double WHEEL_DIAMETER_INCHES = 4.0;        // Wheel diameter in inches
    static final double COUNTS_PER_INCH =
            (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * Math.PI);

    static final double DRIVE_SPEED = 0.5;
    static final double TARGET_DISTANCE_INCHES = 27.0;
    static final double TIMEOUT_SECONDS = 10.0;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // --- Map hardware ---
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        // Reverse right side motors so all wheels move forward together
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Reset encoders
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to run using encoders
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Ready to start");

    }
}