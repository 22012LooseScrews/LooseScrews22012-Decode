package opmodes.testing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import abstraction.subsystems.IntakeMotor;
import abstraction.subsystems.OuttakeMotor;
import abstraction.subsystems.SpinServo;
import common.AutoStates;

@Autonomous
public class BigTriangleBlueAuto extends LinearOpMode {
    public PathChain preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3;

    public void runOpMode() throws InterruptedException {
        SpinServo spindexer = new SpinServo(this);
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);

        AutoStates current_state = AutoStates.preloads;
        Follower follower = Constants.createFollower(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        PathBuilder pathBuilder = new PathBuilder(follower);
        follower.setStartingPose(new Pose(22.5, 126, Math.toRadians(144)));
        boolean timer_has_started = false;
        boolean path_started = false;

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(22.5, 126), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(134))
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(69.984, 87.776),
                                new Pose(24.435, 84.455)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.2, ()-> spindexer.spin_forward_2())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(1, () -> spindexer.spin_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24.435, 84.455), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(178), Math.toRadians(134))
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(103.908, 61.206),
                                new Pose(24.672, 60.494)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.2, ()-> spindexer.spin_forward_2())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(1, () -> spindexer.spin_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24.672, 60.494), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))
                .build();

        intake3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(82.557, 29.654),
                                new Pose(24.672, 35.822)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.2, ()-> spindexer.spin_forward_2())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(1, () -> spindexer.spin_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(24.672, 35.822), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))
                .build();

        waitForStart();

        if(isStopRequested()){
            return;
        }

        while(opModeIsActive() && current_state != AutoStates.end){
            switch(current_state){
                case preloads:
                    if(!path_started){
                        follower.followPath(preloads);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.wait_for_preload_shot;
                    }
                    break;

                case wait_for_preload_shot:
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 1.25){
                            outtakeMotor.outtake_far();
                        }
                        else if(timer.seconds() > 7){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        }
                        else if(timer.seconds() > 6.7){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 6.2){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 5.5){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 5){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 4.3){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 2.25){
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
                        }
                    }
                    break;

                case intake1:
                    if(!path_started) {
                        follower.followPath(intake1);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.shoot1;
                    }
                    break;

                case shoot1:
                    if(!path_started){
                        follower.followPath(shoot1);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.wait_for_shot1;
                    }
                    break;

                case wait_for_shot1:
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 3.5){
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 3.5){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake2;
                        }
                    }
                    break;

                case intake2:
                    if(!path_started) {
                        follower.followPath(intake2);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        current_state = AutoStates.shoot2;
                        path_started = false;
                    }
                    break;

                case shoot2:
                    if(!path_started) {
                        follower.followPath(shoot2);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        current_state = AutoStates.wait_for_shot2;
                        path_started = false;
                    }
                    break;

                case wait_for_shot2:
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 3.5){
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 3.5){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake3;
                        }
                    }
                    break;

                case intake3:
                    if(!path_started) {
                        follower.followPath(intake3);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        current_state = AutoStates.shoot3;
                        path_started = false;
                    }
                    break;

                case shoot3:
                    if(!path_started) {
                        follower.followPath(shoot3);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        current_state = AutoStates.wait_for_shot_3;
                        path_started = false;
                    }
                    break;

                case wait_for_shot_3:
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 6.5){
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 6.5){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.end;
                        }
                    }
                    break;

                default:
                    break;
            }

            if(follower.isBusy()){
                follower.update();
            }
        }
    }
}
