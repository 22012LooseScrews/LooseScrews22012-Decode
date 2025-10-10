package opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class BigTriangleBlueAuto extends LinearOpMode {

    // --- Declare motors ---
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    // --- Encoder & Movement Constants ---
    static final double COUNTS_PER_MOTOR_REV = 384.5 * 4;
    // ≈1538 ticks per revolution (RS-555)
    static final double DRIVE_GEAR_REDUCTION = 1.0;
    static final double WHEEL_DIAMETER_INCHES = 4.0;
    // Wheel diam
    static final double COUNTS_PER_INCH =
            (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * Math.PI);

    static final double DRIVE_SPEED = 0.5;
    static final double TARGET_DISTANCE_INCHES = 27.0;
    static final double TIMEOUT_SECONDS = 10.0;
    @Override
    public void runOpMode() {

        // --- INIT PHASE ---
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Map hardware names (must match your robot configuration)
        frontLeftMotor  = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor   = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor  = hardwareMap.get(DcMotor.class, "backRightMotor");

        // Reverse right side motors so all wheels move forward together
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Reset encoders
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to use encoders
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Ready to start");
        telemetry.update();

        // Wait for the start command
        waitForStart();

        if (opModeIsActive()) {

            // --- DRIVE FORWARD 27 INCHES ---
            encoderDrive(DRIVE_SPEED, TARGET_DISTANCE_INCHES, TIMEOUT_SECONDS);

            // Stop all motors for safety
            stopAllMotors();

            telemetry.addData("Path", "Complete");
            telemetry.update();
        }
    }

    // =========================================================
    // ENCODER DRIVE METHOD (Handles precise movement)
    // =========================================================
    public void encoderDrive(double speed, double distanceInches, double timeoutS) {
        ElapsedTime runtime = new ElapsedTime();

        int moveCounts = (int)(distanceInches * COUNTS_PER_INCH);

        // Targets for left and right sides
        int newLeftTarget  = frontLeftMotor.getCurrentPosition() + moveCounts;
        int newRightTarget = frontRightMotor.getCurrentPosition() + moveCounts;

        // Apply target positions to all motors
        frontLeftMotor.setTargetPosition(newLeftTarget);
        backLeftMotor.setTargetPosition(newLeftTarget);
        frontRightMotor.setTargetPosition(newRightTarget);
        backRightMotor.setTargetPosition(newRightTarget);

        // Switch motors to RUN_TO_POSITION mode
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start motion
        frontLeftMotor.setPower(Math.abs(speed));
        backLeftMotor.setPower(Math.abs(speed));
        frontRightMotor.setPower(Math.abs(speed));
        backRightMotor.setPower(Math.abs(speed));

        // Reset runtime
        runtime.reset();

        // Run until target reached or timeout
        while (opModeIsActive()
                && (runtime.seconds() < timeoutS)
                && (frontLeftMotor.isBusy() && frontRightMotor.isBusy()
                && backLeftMotor.isBusy() && backRightMotor.isBusy())) {

            telemetry.addData("Target (ticks)", "%7d | %7d", newLeftTarget, newRightTarget);
            telemetry.addData("Current (ticks)", "%7d | %7d",
                    frontLeftMotor.getCurrentPosition(),
                    frontRightMotor.getCurrentPosition());
            telemetry.addData("Progress", "%.1f / %.1f seconds", runtime.seconds(), timeoutS);
            telemetry.update();
        }

        // Stop all motion once done
        stopAllMotors();

        // Restore encoders to RUN_USING_ENCODER
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // =========================================================
    // STOP ALL MOTORS
    // =========================================================
    private void stopAllMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}

