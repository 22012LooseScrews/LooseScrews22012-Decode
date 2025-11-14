package opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.robotcore.external.StateMachine;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import abstraction.subsystems.SpinServo;
import abstraction.subsystems.OuttakeMotor;
import common.AutoStates;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;


@Autonomous
public class SmallTriangleBlueAuto extends LinearOpMode {
    private Timer pathTimer, opmodeTimer;
    PathChain preloads;
    PathChain intake1;
    PathChain shoot1;
    PathChain intake2;
    PathChain shoot2;
    PathChain intake3;
    PathChain shoot3;

    private enum Autostates {
        preloads,
        intake1,
        shoot1,
        intake2,
        shoot2,
        intake3,
        shoot3,
    }

    @Override
    public void runOpMode() throws InterruptedException {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        SpinServo spinServo = new SpinServo(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor();
        Follower follower = Constants.createFollower(hardwareMap);
        PathBuilder builder = new PathBuilder(follower);
        final Pose startPose = new Pose(28.5, 128, Math.toRadians(180));
        follower.setStartingPose(startPose);
        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 8.000), new Pose(56.000, 20.000))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(110))
                .addParametricCallback(0.5, () -> spinServo.spin_forward())
                .addParametricCallback(0.99, () -> spinServo.spin_stop())
                .addParametricCallback(0.5, () -> outtakeMotor.outtake_far())
                .addParametricCallback(0.99, () -> outtakeMotor.outtake_stop())
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        // Path 1
                        new BezierCurve(
                                new Pose(56.000, 9.000),
                                new Pose(72.421, 46.526),
                                new Pose(22.737, 36.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();
        shoot1 = follower.pathBuilder()
                .addPath(
                        // Path 2
                        new BezierLine(new Pose(22.737, 36.000), new Pose(71.881, 22.774))
                )
                .setLinearHeadingInterpolation(Math.toRadians(184), Math.toRadians(118.5))
                //   .setReversed()
                .build();
        intake2 = follower.pathBuilder()
                .addPath(
                        // Path 3
                        new BezierCurve(
                                new Pose(71.881, 22.774),
                                new Pose(52.191, 63.104),
                                new Pose(20.402, 60.731)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        shoot2 = follower.pathBuilder()
                .addPath(
                        // Path 4
                        new BezierLine(new Pose(20.402, 60.731), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(185), Math.toRadians(134))
                .build();
        intake3 = follower.pathBuilder()
                .addPath(
                        // Path 5
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(57.885, 89.437),
                                new Pose(24.672, 84.455)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        shoot3 = follower.pathBuilder()
                .addPath(
                        // Path 6
                        new BezierLine(new Pose(24.672, 84.455), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(189), Math.toRadians(134))
                .build();


        waitForStart();

        if (isStopRequested()) return;
        AutoStates state = AutoStates.preloads;
        PathChain currentPath = null;

        while (opModeIsActive() && state != AutoStates.shoot3) {

            switch (state) {
                case preloads:
                    currentPath = preloads;
                    follower.followPath(currentPath);
                    state = AutoStates.intake1; // next state when this path finishes
                    break;

                case intake1:
                    // wait until follower finishes current path
                    if (!follower.isBusy()) {
                        currentPath = intake1;
                        follower.followPath(currentPath);
                        state = AutoStates.shoot1;
                    }
                    break;

                case shoot1:
                    if (!follower.isBusy()) {
                        currentPath = shoot1;
                        follower.followPath(currentPath);
                        state = AutoStates.intake2;
                    }
                    break;

                case intake2:
                    if (!follower.isBusy()) {
                        currentPath = intake2;
                        follower.followPath(currentPath);
                        state = AutoStates.shoot2;
                    }
                    break;

                case shoot2:
                    if (!follower.isBusy()) {
                        currentPath = shoot2;
                        follower.followPath(currentPath);
                        state = AutoStates.intake3;
                    }
                    break;

                case intake3:
                    if (!follower.isBusy()) {
                        currentPath = intake3;
                        follower.followPath(currentPath);
                        state = AutoStates.shoot3;
                    }
                    break;

                case shoot3:
                    if (!follower.isBusy()) {
                        currentPath = shoot3;
                        follower.followPath(currentPath);
                        break;
                    }



            }

            // Keep updating follower while it's busy. If not busy, loop will advance state next iteration.
            if (follower.isBusy()) {
                follower.update();
            }

            idle(); // let the system breathe / give up CPU briefly
        }

        // All done
    }
}