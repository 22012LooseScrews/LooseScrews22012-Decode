package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.RevColorSensor;
import org.firstinspires.ftc.teamcode.abstractions.SpinMotor;
import org.firstinspires.ftc.teamcode.abstractions.SpinServo;

@Config
@TeleOp
public class MecanumTeleOp extends OpMode {
    RevColorSensor color_sensor = new RevColorSensor();
    RevColorSensor.DetectedColor detectedColor;
    FtcDashboard dashboard;
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;
    OuttakeMotor outtake_motor;
    SpinServo spindexer;
    IntakeMotor intake_motor;
    SpinMotor spin_motor;
    private Limelight3A limelight;
    private static final double kp_turn = 0.03;
    private static final double max_speed = 1.0;
    int spin_counter;
    long lastSpinTime;
    boolean lastCircleBtn = false;
    boolean readyToFinalSpin;
    boolean colorSpinTriggered = false;

    @Override
    public void init() {
        dashboard = FtcDashboard.getInstance();
        color_sensor.init(hardwareMap);

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
        outtake_motor = new OuttakeMotor(this);
        intake_motor = new IntakeMotor(this);
        spin_motor = new SpinMotor(this);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(2);

        spin_counter = 0;
        lastSpinTime = 0;
        readyToFinalSpin = false;
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;
        double final_rx = rx;

        detectedColor = color_sensor.getDetectedColor(telemetry);
        LLResult ll_result = limelight.getLatestResult();

        if(gamepad1.dpad_up && ll_result != null && ll_result.isValid()){
            double close_turn_error = ll_result.getTx() - 3.3;
            double close_turn_power = close_turn_error * -kp_turn;
            final_rx = Math.min(Math.abs(close_turn_power), max_speed) * Math.signum(close_turn_power);
        }
        else if(gamepad1.dpad_down && ll_result != null && ll_result.isValid()){
            double far_turn_error =  ll_result.getTx() + 3;
            double far_turn_power = far_turn_error * -kp_turn;
            final_rx = Math.min(Math.abs(far_turn_power), max_speed) * Math.signum(far_turn_power);
        }

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

        boolean currentColorTarget = (detectedColor == RevColorSensor.DetectedColor.GREEN || detectedColor == RevColorSensor.DetectedColor.PURPLE);
        boolean currentCircleBtn = (gamepad1.circle || gamepad1.b || gamepad2.circle || gamepad2.b);

        if (currentColorTarget && !colorSpinTriggered) {
            if (spin_counter == 0) {
                spin_motor.add120DegreesOne(1.0);
                spin_counter++;
            }
            else if(spin_counter == 1){
                spin_motor.add120DegreesOne(1.0);
                spin_counter++;
            }
            else if (spin_counter == 2) {
                readyToFinalSpin = true;
            }
            colorSpinTriggered = true;
        } else if (!currentColorTarget) {
            colorSpinTriggered = false;
        }

        if ((currentCircleBtn && !lastCircleBtn) && System.currentTimeMillis() - lastSpinTime > 250) {
            lastSpinTime = System.currentTimeMillis();
            if (spin_counter == 0) {
                spin_motor.add120DegreesOne(1.0);
                spin_counter++;
            }
            else if(spin_counter == 1){
                spin_motor.add120DegreesTwo(1.0);
                spin_counter++;
            }
            else if (spin_counter == 2) {
                readyToFinalSpin = true;
            }
        }

        if (readyToFinalSpin && outtake_motor.getVel() > 1825) {
            spin_motor.add360Degrees(1.0);

            readyToFinalSpin = false;
        }

        lastCircleBtn = currentCircleBtn;

        if (gamepad2.left_trigger > 0.1 || gamepad1.left_trigger > 0.1) {
            outtake_motor.outtake_close();
            spin_counter = 0;
        } else if (gamepad2.right_trigger > 0.1 || gamepad1.right_trigger > 0.1) {
            outtake_motor.outtake_far();
            spin_counter = 0;
        } else {
            outtake_motor.outtake_stop();
        }

        if(gamepad1.circleWasPressed() || gamepad2.circleWasPressed()){
            spin_motor.spin_forward();
        }
        else if(gamepad1.triangleWasPressed() || gamepad2.triangleWasPressed()){
            spin_motor.spin_backward();
        }
        else if(gamepad1.triangleWasReleased() || gamepad1.circleWasReleased() || gamepad2.triangleWasReleased() || gamepad2.circleWasReleased()){
            spin_motor.spin_stop();
        }

        telemetry.addData("Outtake Velocity", outtake_motor.getVel());
        telemetry.addData("Detected Color", detectedColor);
        telemetry.addData("Num of spins", spin_counter);
        telemetry.addData("what it actually is", ll_result.getTx());
        telemetry.update();
    }
}
