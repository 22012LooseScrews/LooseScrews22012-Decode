package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.ServoStopper;

@TeleOp
public class MecanumTeleOp extends OpMode {
//    RevColorSensor color_sensor = new RevColorSensor();
//    RevColorSensor.DetectedColor detectedColor;
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;
    OuttakeMotor outtake_motor, outtake_motor2;
    IntakeMotor intake_motor;
    ServoStopper servo_Stopper;
//    private Limelight3A limelight;
    private static final double kp_turn = 0.03;
    private static final double max_speed = 1.0;

    @Override
    public void init() {
//        color_sensor.init(hardwareMap);

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

        outtake_motor = new OuttakeMotor(this);
        intake_motor = new IntakeMotor(this);
        servo_Stopper = new ServoStopper(this);

//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        limelight.pipelineSwitch(2);
    }

    @Override
    public void start() {
//        limelight.start();
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;
        double final_rx = rx;

//        detectedColor = color_sensor.getDetectedColor(telemetry);
//        LLResult ll_result = limelight.getLatestResult();
//
//        if(ll_result != null && ll_result.isValid()){
//            double tx = ll_result.getTx();
//            if(gamepad1.dpad_up){
//                telemetry.addLine("Apriltag Detected");
//                double close_turn_error = tx - 3.3;
//                double close_turn_power = close_turn_error * -kp_turn;
//                final_rx = Math.min(Math.abs(close_turn_power), max_speed) * Math.signum(close_turn_power);
//            }
//            else if(gamepad1.dpad_down){
//                telemetry.addLine("Apriltag Detected");
//                double far_turn_error = tx - 3;
//                double far_turn_power = far_turn_error * -kp_turn;
//                final_rx = Math.min(Math.abs(far_turn_power), max_speed) * Math.signum(far_turn_power);
//            }
//            else{
//                telemetry.addLine("No Apriltag Detected");
//            }
//        }

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(final_rx), 1);
        frontLeftMotor.setPower((y + x + final_rx) / denominator);
        backLeftMotor.setPower((y - x + final_rx) / denominator);
        frontRightMotor.setPower((y - x - final_rx) / denominator);
        backRightMotor.setPower((y + x - final_rx) / denominator);

        if (gamepad1.left_bumper) {
            intake_motor.intake_intake();
        } else if (gamepad1.right_bumper) {
            intake_motor.intake_outtake();
        } else {
            intake_motor.intake_stop();
        }

        if(gamepad1.dpad_left){
            servo_Stopper.gate_open();
        }
        else if(gamepad1.dpad_right){
            servo_Stopper.gate_close();
        }

        if (gamepad2.left_trigger > 0.1 || gamepad1.left_trigger > 0.1) {
            outtake_motor.outtake_close();
        } else if (gamepad2.right_trigger > 0.1 || gamepad1.right_trigger > 0.1) {
            outtake_motor.outtake_far();
        } else {
            outtake_motor.outtake_stop();
        }

        telemetry.addData("Outtake Velocity", outtake_motor.getVel());
        telemetry.update();
    }
}
