package opmodes.testing;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class VariablePractice extends OpMode {
    @Override
    public void init(){
        int team_number = 22012;
        int motor_angle = 90;
        double motor_speed = 0.75;
        boolean claw_closed = true;
        String team_name = "Loose Screws";

        telemetry.addData("Team Number", team_number);
        telemetry.addData("Motor Speed", motor_speed);
        telemetry.addData("Claw State", claw_closed);
        telemetry.addData("Team Name", team_name);
        telemetry.addData("Motor Angle", motor_angle);
    }
    public void loop(){

    }
}
