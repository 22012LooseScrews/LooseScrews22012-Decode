package opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class BigEncoderAutoRight extends LinearOpMode {

    // --- Declare motors ---
    private DcMotor frontLeftMotor = null;
    private DcMotor backLeftMotor = null;
    private DcMotor frontRightMotor = null;
    private DcMotor backRightMotor = null;

    private ElapsedTime runtime = new ElapsedTime();

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


        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRight");

        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);


        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            // --- STEP 1: Drive diagonally forward-right (~15 inches total) ---
            encoderDrive(DRIVE_SPEED, 15, 3.0);

            // --- STEP 2: Strafe left (~12 inches) ---
            encoderStrafe(DRIVE_SPEED, -12, 3.0);

            stopAllMotors();

        }

    }

    // --- ENCODER DRIVE FUNCTION (FORWARD/BACKWARD) ---
    public void encoderDrive(double speed, double inches, double timeoutS) {
        int moveCounts = (int)(inches * COUNTS_PER_INCH);

        int newFrontLeftTarget = frontLeftMotor.getCurrentPosition() + moveCounts;
        int newBackLeftTarget = backLeftMotor.getCurrentPosition() + moveCounts;
        int newFrontRightTarget = frontRightMotor.getCurrentPosition() + moveCounts;
        int newBackRightTarget = backRightMotor.getCurrentPosition() + moveCounts;

        frontLeftMotor.setTargetPosition(newFrontLeftTarget);
        backLeftMotor.setTargetPosition(newBackLeftTarget);
        frontRightMotor.setTargetPosition(newFrontRightTarget);
        backRightMotor.setTargetPosition(newBackRightTarget);

        setRunToPosition();

        runtime.reset();
        setMotorPower(speed);

        while (opModeIsActive() && runtime.seconds() < timeoutS &&
                (frontLeftMotor.isBusy() && frontRightMotor.isBusy())) {
            telemetry.addData("Path", "Driving forward...");
            telemetry.update();
        }

        stopAllMotors();
    }

    // --- ENCODER STRAFE FUNCTION ---
    public void encoderStrafe(double speed, double inches, double timeoutS) {
        int moveCounts = (int)(inches * COUNTS_PER_INCH);

        // Strafing requires opposite wheel directions
        int frontLeftTarget = frontLeftMotor.getCurrentPosition() + moveCounts;
        int backLeftTarget = backLeftMotor.getCurrentPosition() - moveCounts;
        int frontRightTarget = frontRightMotor.getCurrentPosition() - moveCounts;
        int backRightTarget = backRightMotor.getCurrentPosition() + moveCounts;

        frontLeftMotor.setTargetPosition(frontLeftTarget);
        backLeftMotor.setTargetPosition(backLeftTarget);
        frontRightMotor.setTargetPosition(frontRightTarget);
        backRightMotor.setTargetPosition(backRightTarget);

        setRunToPosition();

        runtime.reset();
        setMotorPower(Math.abs(speed));

        while (opModeIsActive() && runtime.seconds() < timeoutS &&
                (frontLeftMotor.isBusy() && frontRightMotor.isBusy())) {
            telemetry.addData("Path", "Strafing...");
            telemetry.update();
        }

        stopAllMotors();
    }

    // --- HELPER FUNCTIONS ---
    private void setRunToPosition() {
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setMotorPower(double power) {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    private void stopAllMotors() {
        setMotorPower(0);
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
