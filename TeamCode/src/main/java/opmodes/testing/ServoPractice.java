package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import mechanisms.TestBench;

@TeleOp
public class ServoPractice extends OpMode {
    TestBench bench = new TestBench();
    double left_trigger, right_trigger;
    @Override
    public void init() {
        bench.init(hardwareMap);
        left_trigger = 0;
        right_trigger = 0;
    }

    @Override
    public void loop() {
        left_trigger = gamepad1.left_trigger;
        right_trigger = gamepad1.right_trigger;

        if(gamepad1.a){
            bench.setPositionalServoAngle(0.0);
        }
        else{
            bench.setPositionalServoAngle(1.0);
        }

        if(gamepad1.b){
            bench.setRotationalServoPower(1.0);
        }
        else{
            bench.setRotationalServoPower(0.0);
        }

        bench.setRotationalServoPower(right_trigger);
        bench.setPositionalServoAngle(left_trigger);
    }
}
