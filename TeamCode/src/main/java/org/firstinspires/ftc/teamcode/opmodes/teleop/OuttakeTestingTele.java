package org.firstinspires.ftc.teamcode.opmodes.teleop;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.RevColorSensor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeServo;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class OuttakeTestingTele extends OpMode {
    OuttakeMotor outtake_motor, outtake_motor2;
    OuttakeServo outtakeServo;

    @Override
    public void init() {

        outtake_motor = new OuttakeMotor(this);
        outtakeServo = new OuttakeServo(this);

    }

    @Override
    public void loop() {
        if (gamepad2.left_trigger > 0.1 || gamepad1.left_trigger > 0.1) {
            outtake_motor.outtake_close();

        } else if (gamepad2.right_trigger > 0.1 || gamepad1.right_trigger > 0.1) {
            outtake_motor.outtake_far();

        } else {
            outtake_motor.outtake_stop();
        }
        if (gamepad1.a) {
            outtakeServo.outtake_shift_far();
        } else if (gamepad1.b) {
            outtakeServo.outtake_shift_close();
        }
    }
}
