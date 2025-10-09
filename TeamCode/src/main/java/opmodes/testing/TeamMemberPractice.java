package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class TeamMemberPractice extends OpMode {
    boolean init_done;
    @Override
    public void init() {
        telemetry.addData("Init", init_done);
        init_done = true;
    }
    public double squareInputWithSign(double input){
        double output = input*input;
        if(input<0){
            output*=-1;
        }
        return output;
    }
    @Override
    public void loop() {
        telemetry.addData("init", init_done);
        double y_axis = gamepad1.left_stick_y;
        telemetry.addData("Left Stick Normal", y_axis);
        y_axis = squareInputWithSign(y_axis);
        telemetry.addData("Left Stick Modified", y_axis);
    }
}
