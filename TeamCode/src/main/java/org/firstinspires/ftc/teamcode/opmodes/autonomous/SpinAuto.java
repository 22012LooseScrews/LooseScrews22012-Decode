package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.abstractions.SpinMotor;
@Autonomous
public class SpinAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SpinMotor spin_motor = new SpinMotor(this);
        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        // Start the first spin
        spin_motor.add120DegreesOne(1.0);
        timer.reset();

        // LOOP: Wait 2 seconds for the motor to finish moving
        while (opModeIsActive() && timer.seconds() < 2.0) {
            telemetry.addData("Status", "Spinning 1...");
            telemetry.update();
        }

        // Start the second spin
        spin_motor.add120DegreesOne(1.0);
        timer.reset();

        // LOOP: Wait another 2 seconds
        while (opModeIsActive() && timer.seconds() < 2.0) {
            telemetry.addData("Status", "Spinning 2...");
            telemetry.update();
        }

        spin_motor.spin_stop();
    }
}