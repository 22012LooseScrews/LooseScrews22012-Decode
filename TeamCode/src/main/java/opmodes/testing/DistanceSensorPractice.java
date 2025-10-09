package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import mechanisms.TestBench;
@Disabled
@TeleOp
public class DistanceSensorPractice extends OpMode {
    TestBench bench = new TestBench();
    @Override
    public void init() {
        bench.init(hardwareMap);
    }
    @Override
    public void loop() {
        if(bench.getDistanceSensorState()<10){
            telemetry.addData("Distance Status", "Too Close!");
        }
        telemetry.addData("Distance", bench.getDistanceSensorState());
    }
}
