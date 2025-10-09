package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@Disabled
@TeleOp
public class UseRobotLocationOpMode extends OpMode {
    RobotLocationPractice robot_location_practice = new RobotLocationPractice(0);

    @Override
    public void init() {
        robot_location_practice.setAngle(0);
        robot_location_practice.setX(0);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            robot_location_practice.turnRobot(0.1);
        } else if (gamepad1.b) {
            robot_location_practice.turnRobot(-0.1);
        }

        if (gamepad1.dpad_left) {
            robot_location_practice.changeX(0.1);
        } else if (gamepad1.dpad_right) {
            robot_location_practice.changeX(-0.1);
        }

        if (gamepad1.dpad_up) {
            robot_location_practice.changeY(0.1);
        }
        else if (gamepad1.dpad_down) {
            robot_location_practice.changeY(-0.1);
        }

        telemetry.addData("Heading", robot_location_practice.getHeading());
        telemetry.addData("Angle", robot_location_practice.getAngle());
        telemetry.addData("X Value", robot_location_practice.getX());
        telemetry.addData("Y Value", robot_location_practice.getY());
    }
}
