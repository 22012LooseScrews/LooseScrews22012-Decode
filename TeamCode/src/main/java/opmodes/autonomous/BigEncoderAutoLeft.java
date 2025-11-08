package opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class BigEncoderAutoLeft extends LinearOpMode {

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

    static final double DRIVE_SPEED = 0.5;

    @Override
    public void runOpMode() {

        // --- Hardware Mapping ---
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        // --- Motor Directions ---
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // --- Reset Encoders ---
        resetEncoders();

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // --- STEP 1: Drive forward 15 inches ---
            encoderDrive(DRIVE_SPEED, 15);

            // --- STEP 2: Strafe left 12 inches ---
            encoderStrafeLeft(DRIVE_SPEED, 12);

            stopAllMotors();
        }
    }

    // --- DRIVE FORWARD/BACKWARD ---
    public void encoderDrive(double speed, double inches) {
        int moveCounts = (int) (inches * COUNTS_PER_INCH);

        int newFrontLeftTarget = frontLeftMotor.getCurrentPosition() + moveCounts;
        int newBackLeftTarget = backLeftMotor.getCurrentPosition() + moveCounts;
        int newFrontRightTarget = frontRightMotor.getCurrentPosition() + moveCounts;
        int newBackRightTarget = backRightMotor.getCurrentPosition() + moveCounts;

        frontLeftMotor.setTargetPosition(newFrontLeftTarget);
        backLeftMotor.setTargetPosition(newBackLeftTarget);
        frontRightMotor.setTargetPosition(newFrontRightTarget);
        backRightMotor.setTargetPosition(newBackRightTarget);

        setRunToPosition();
        setMotorPower(speed);

        while (opModeIsActive() &&
                (frontLeftMotor.isBusy() && frontRightMotor.isBusy() &&
                        backLeftMotor.isBusy() && backRightMotor.isBusy())) {
            telemetry.addData("Path", "Driving forward...");
            telemetry.update();
        }

        stopAllMotors();
        resetEncoders();
    }

    // --- STRAFE LEFT ---
    public void encoderStrafeLeft(double speed, double inches) {
        int moveCounts = (int) (inches * COUNTS_PER_INCH);

        // Strafing left = FL -, FR +, BL +, BR -
        int frontLeftTarget = frontLeftMotor.getCurrentPosition() - moveCounts;
        int frontRightTarget = frontRightMotor.getCurrentPosition() + moveCounts;
        int backLeftTarget = backLeftMotor.getCurrentPosition() + moveCounts;
        int backRightTarget = backRightMotor.getCurrentPosition() - moveCounts;

        frontLeftMotor.setTargetPosition(frontLeftTarget);
        frontRightMotor.setTargetPosition(frontRightTarget);
        backLeftMotor.setTargetPosition(backLeftTarget);
        backRightMotor.setTargetPosition(backRightTarget);

        setRunToPosition();
        setMotorPower(Math.abs(speed));

        while (opModeIsActive() &&
                (frontLeftMotor.isBusy() && frontRightMotor.isBusy() &&
                        backLeftMotor.isBusy() && backRightMotor.isBusy())) {
            telemetry.addData("Path", "Strafing left...");
            telemetry.update();
        }

        stopAllMotors();
        resetEncoders();
    }

    // --- HELPER FUNCTIONS ---
    private void setRunToPosition() {
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void resetEncoders() {
        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setMotorPower(double power) {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    private void stopAllMotors() {
        setMotorPower(0);
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
