package opmodes.teleop;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import abstraction.subsystems.IntakeMotor;
import abstraction.subsystems.OuttakeMotor;
import abstraction.subsystems.SpinServo;
import abstraction.subsystems.VectorServo;
@TeleOp
public class MecanumTeleOp extends OpMode {
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;
    OuttakeMotor outtake_motor;
    VectorServo vector_servo;
    SpinServo spindexer;
    IntakeMotor intake_motor;
    private Limelight3A limelight;
    private static final double kp_turn = 0.03;
    private static final double max_speed = 1.0;

    @Override
    public void init() {
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        spindexer = new SpinServo(this);
        vector_servo = new VectorServo(this);
        outtake_motor = new OuttakeMotor(this);
        intake_motor = new IntakeMotor(this);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
    }

    public void start() {
        limelight.start();
    }
    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;
        double final_rx = rx;

        LLResult ll_result = limelight.getLatestResult();
//        if(gamepad1.dpadUpWasPressed() && ll_result != null && ll_result.isValid()){
//            double turn_error = ll_result.getTx();
//            double turn_power = turn_error * -kp_turn;
//            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
//            final_rx = turn_power;
//
//            telemetry.addData("Current TX Error (Deg)", turn_error);
//            telemetry.update();
//        }
//        else {
//            telemetry.addLine("No AprilTag Detected");
//        }

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(final_rx), 1);
        double frontLeftPower = (y + x + final_rx) / denominator;
        double backLeftPower = (y - x + final_rx) / denominator;
        double frontRightPower = (y - x - final_rx) / denominator;
        double backRightPower = (y + x - final_rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        if (gamepad1.left_bumper) {
            intake_motor.intake_intake();
            vector_servo.vector_intake();
        } else if (gamepad1.right_bumper) {
            intake_motor.intake_outtake();
            vector_servo.vector_outtake();
        } else {
            intake_motor.intake_stop();
            vector_servo.vector_stop();
        }

        if(gamepad1.circleWasPressed() || gamepad1.bWasPressed()){
            spindexer.spin_forward_2();
        }
        else if(gamepad1.triangleWasPressed() || gamepad1.yWasPressed()){
            spindexer.spin_backward();
        }
        else if(gamepad1.circleWasReleased() || gamepad1.bWasReleased() || gamepad1.triangleWasReleased() || gamepad1.yWasReleased()){
            spindexer.spin_stop();
        }

        if (gamepad1.left_trigger > 0.1) {
            outtake_motor.outtake_close();
            double turn_error = ll_result.getTx();
            double turn_power = turn_error * -kp_turn;
            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
            final_rx = turn_power;

            telemetry.addData("Current TX Error (Deg)", turn_error);
            telemetry.update();

        } else if (gamepad1.right_trigger > 0.1) {
            outtake_motor.outtake_far();
            double turn_error = ll_result.getTx();
            double turn_power = turn_error * -kp_turn;
            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
            final_rx = turn_power;

            telemetry.addData("Current TX Error (Deg)", turn_error);
            telemetry.update();
        } else {
            outtake_motor.outtake_stop();
        }

        telemetry.addData("Outtake Velocity (ticks/s)", outtake_motor.getVel());
        telemetry.addData("Battery Voltage", outtake_motor.getVol());
        telemetry.addData("what it actually is ",ll_result.getTx());
        telemetry.update();
    }
}