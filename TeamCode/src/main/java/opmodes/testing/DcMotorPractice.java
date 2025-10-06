package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import mechanisms.TestBench;
@Disabled
@TeleOp
public class DcMotorPractice extends OpMode {
    TestBench bench = new TestBench();
    @Override
    public void init() {
        bench.init(hardwareMap);
    }
    @Override
    public void loop() {
        if(gamepad1.a){
            bench.setBrakeBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else if(gamepad1.b){
            bench.setBrakeBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
        double motor_speed = gamepad1.left_stick_y;
        bench.setMotorSpeed(motor_speed);
        telemetry.addData("Motor Revolutions", bench.getMotorRevs());
    }
}
