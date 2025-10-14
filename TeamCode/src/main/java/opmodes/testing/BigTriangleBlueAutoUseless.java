package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous
public class BigTriangleBlueAutoUseless extends LinearOpMode {

    // --- Declare motors ---
    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;

    // --- Encoder & Movement Constants ---
    static final double COUNTS_PER_MOTOR_REV = 384.5 * 4;   // ≈1538 ticks per revolution (RS-555)
    static final double DRIVE_GEAR_REDUCTION = 1.0;         // No external gearing
    static final double WHEEL_DIAMETER_INCHES = 4.0;        // Wheel diameter
    static final double COUNTS_PER_INCH =
            (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                    (WHEEL_DIAMETER_INCHES * Math.PI);

    static final double DRIVE_SPEED = 0.5;                  // Motor power (50%)
    static final double TARGET_DISTANCE_INCHES = 60.0;      // Drive 60 inches forward

    @Override
    public void runOpMode() {

        // --- INIT PHASE ---
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // Map hardware to configuration names (must match robot config)
        frontLeftMotor  = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor   = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor  = hardwareMap.get(DcMotor.class, "backRightMotor");

        // Reverse right side so all wheels move forward together
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Reset encoders
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motors to run using encoders by default
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Ready to start");
        telemetry.update();

        // Wait for the start command
        waitForStart();

        if (opModeIsActive()) {
            // --- DRIVE FORWARD 60 INCHES (with 10s timeout) ---
            encoderDrive(DRIVE_SPEED, TARGET_DISTANCE_INCHES, 10.0);

            // Ensure everything is stopped
            stopAllMotors();

            telemetry.addData("Path", "Complete");
            telemetry.update();
        }
    }

    // =========================================================
    // ENCODER DRIVE METHOD (4-MOTOR VERSION) - defined in same class
    // =========================================================
    public void encoderDrive(double speed, double distanceInches, double timeoutS) {
        ElapsedTime runtime = new ElapsedTime();

        int moveCounts = (int)(distanceInches * COUNTS_PER_INCH);

        // Targets for left and right sides (front/back per side use same target)
        int newLeftTarget  = frontLeftMotor.getCurrentPosition() + moveCounts;
        int newRightTarget = frontRightMotor.getCurrentPosition() + moveCounts;

        // Apply target positions to all motors
        frontLeftMotor.setTargetPosition(newLeftTarget);
        backLeftMotor.setTargetPosition(newLeftTarget);
        frontRightMotor.setTargetPosition(newRightTarget);
        backRightMotor.setTargetPosition(newRightTarget);

        // Switch motors to RUN_TO_POSITION
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Start motion
        frontLeftMotor.setPower(Math.abs(speed));
        backLeftMotor.setPower(Math.abs(speed));
        frontRightMotor.setPower(Math.abs(speed));
        backRightMotor.setPower(Math.abs(speed));

        // Reset runtime and wait for motors to reach target or timeout
        runtime.reset();
        while (opModeIsActive() &&
                runtime.seconds() < timeoutS &&
                (frontLeftMotor.isBusy() && frontRightMotor.isBusy() &&
                        backLeftMotor.isBusy() && backRightMotor.isBusy())) {

            telemetry.addData("Target", "%7d : %7d", newLeftTarget, newRightTarget);
            telemetry.addData("Current", "%7d : %7d",
                    frontLeftMotor.getCurrentPosition(),
                    frontRightMotor.getCurrentPosition());
            telemetry.addData("Timeout", "%.1f/%.1f s", runtime.seconds(), timeoutS);
            telemetry.update();
        }

        // Stop motion and restore encoder mode
        stopAllMotors();

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // =========================================================
    // Helper: Stop all drive motors
    // =========================================================
    private void stopAllMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
