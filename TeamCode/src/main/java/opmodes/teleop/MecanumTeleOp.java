package opmodes.teleop;
import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Config
public class MecanumTeleOp extends OpMode {
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor, intakeMotor, outtakeMotor;
    Servo spinServo;
    @Override
    public void init() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        outtakeMotor = hardwareMap.get(DcMotor.class, "outtakeMotor");

        spinServo = hardwareMap.get(Servo.class, "spinServo");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        if (gamepad1.left_bumper) {
            intakeMotor.setPower(1.0);
        } else if(gamepad1.right_bumper){
            intakeMotor.setPower(-1.0);
        } else{
            intakeMotor.setPower(0.0);
        }

        if (gamepad1.circleWasPressed() || gamepad1.bWasPressed()) {
            spinServo.setPosition(1);
            sleep(240);
            spinServo.setPosition(0.5);
        }
        if(gamepad1.squareWasReleased() || gamepad1.aWasReleased()){
            spinServo.setPosition(0);
            sleep(480);
            spinServo.setPosition(0.5);
        }


        if (gamepad1.left_trigger > 0.1) {
            outtakeMotor.setPower(-0.85);
        }
        else{
            outtakeMotor.setPower(0.0);
        }

        if(gamepad1.right_trigger > 0.1){
            outtakeMotor.setPower(-1.0);
        }
        else{
            outtakeMotor.setPower(0.0);
        }

        telemetry.addData("Y",-gamepad1.left_stick_y);
        telemetry.addData("X",-gamepad1.left_stick_x * 1.1);
        telemetry.addData("RX",gamepad1.right_stick_x);
        telemetry.addData("Left Bumper", gamepad1.left_bumper);
        telemetry.addData("Right Bumper", gamepad1.right_bumper);
    }
}