package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Vector;

import abstraction.subsystems.IntakeMotor;
import abstraction.subsystems.VectorServo;

@Autonomous
public class IntakeAuto extends OpMode {
    IntakeMotor intake_motor;
    VectorServo vector_servo;
    @Override
    public void init() {
        intake_motor = new IntakeMotor(this);
        vector_servo = new VectorServo(this);
    }
    public void loop(){
        intake_motor.intake_intake();
        vector_servo.vector_intake();
    }
}
