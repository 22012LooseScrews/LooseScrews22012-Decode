package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import org.firstinspires.ftc.teamcode.abstractions.SpinServo;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
@Autonomous
public class SmallTriangleBlueAutoSafe extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        SpinServo spinServo = new SpinServo(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);
        PathBuilder builder = new PathBuilder(follower);
        follower.setStartingPose(new Pose(56, 9, Math.toRadians(90)));

        PathChain small_triangle_blue_path = builder
                .addPath(

                        new BezierLine(new Pose(56, 9), new Pose(50.221, 19.990))

                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(130))
/*                .addParametricCallback(0.5, () -> spinServo.spin_forward())
                .addParametricCallback(0.99, () -> spinServo.spin_stop())
                .addParametricCallback(0.5, () -> outtakeMotor.outtake_far())
                .addParametricCallback(0.99, () -> outtakeMotor.outtake_stop())*/
                .build();

        waitForStart();
        follower.followPath(small_triangle_blue_path, true);
        if(isStopRequested()){
            return;
        }
        while(opModeIsActive() && follower.isBusy()){
            follower.update();
        }
    }
}
