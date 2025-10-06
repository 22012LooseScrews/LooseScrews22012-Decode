package opmodes.testing;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class GamePadPractice extends OpMode {
    @Override
    public void init(){

    }
    public void loop(){
        double speed_forward = -gamepad1.left_stick_y;
        double diff_x_joysticks = gamepad1.left_stick_x - gamepad1.right_stick_x;
        double sum_triggers = gamepad1.left_trigger + gamepad1.right_trigger;
        telemetry.addData("Left X Value", gamepad1.left_stick_x);
        telemetry.addData("Left Y Value", speed_forward);
        telemetry.addData("Right X Value", gamepad1.right_stick_x);
        telemetry.addData("Right Y Value", gamepad1.right_stick_y);
        telemetry.addData("Difference In X", diff_x_joysticks);
        telemetry.addData("Sum Of Triggers", sum_triggers);
        telemetry.addData("A Button", gamepad1.a);
        telemetry.addData("B Button", gamepad1.b);
    }
}
