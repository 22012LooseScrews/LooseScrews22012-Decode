package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import mechanisms.TestBench;

@Disabled
@TeleOp
public class TouchSensorPractice extends OpMode {
    TestBench bench = new TestBench();
    @Override
    public void init() {
        bench.init(hardwareMap);
    }

    @Override
    public void loop() {
        String touch_sensor_state = "Not Pressed!";
        if(bench.isTouchSensorPressed()){
            touch_sensor_state = "Pressed!";
        }
        telemetry.addData("Touch Sensor State", touch_sensor_state);
    }
}
