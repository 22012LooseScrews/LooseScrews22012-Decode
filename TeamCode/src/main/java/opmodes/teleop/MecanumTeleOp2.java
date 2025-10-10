package opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MecanumTeleOp2 extends OpMode {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    @Override
    public void init() {
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void loop(){
        double right_x = gamepad1.right_stick_x;
        double right_y = gamepad1.right_stick_y*1.1;
        double leftstick_y = -gamepad1.left_stick_y;
        double leftstick_x = -gamepad1.left_stick_x;

        double denominator = Math.max(Math.abs(right_x) + Math.abs(right_y) + Math.abs(leftstick_y), 1);
        double frontLeftPower = (right_x + right_y + leftstick_y) / denominator;
        double backLeftPower = (right_x - right_y + leftstick_y) / denominator;
        double frontRightPower = (right_x - right_y - leftstick_y) / denominator;
        double backRightPower = (right_x + right_y - leftstick_y) / denominator;

        if (gamepad1.left_stick_x < -0.7) {
            frontRightMotor.setPower(-frontRightPower);
            backRightMotor.setPower(backRightPower);
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(-backLeftPower);
            telemetry.addLine("going right");
        }
        else if(gamepad1.left_stick_x > 0.7){
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(-backRightPower);
            frontLeftMotor.setPower(-frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            telemetry.addLine("going left");
        }
        else {
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            telemetry.addLine("poopy");

        }

 /*     frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);*/
        telemetry.addData("Y value",right_x);
        telemetry.addData("X value", right_y);
        telemetry.addData("RX value", leftstick_x);
    }
}
