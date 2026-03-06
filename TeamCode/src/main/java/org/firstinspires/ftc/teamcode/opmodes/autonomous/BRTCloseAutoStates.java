package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.ServoStopper;
import org.firstinspires.ftc.teamcode.common.AutoStates;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.PanelsDrawing;

@Autonomous
public class BRTCloseAutoStates extends LinearOpMode {
    public PathChain preloads, intake1, shoot1, gate1, shoot2, gate2, shoot3, intake2, shoot4, waitForTeleOp;
    boolean path_started = false;
    boolean timer_has_started = false;

    @Override
    public void runOpMode() throws InterruptedException {
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);
        ServoStopper servoStopper = new ServoStopper(this);

        AutoStates current_state = AutoStates.preloads;
        ElapsedTime timer = new ElapsedTime();
        Follower follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(126.669, 112.145, Math.toRadians(0)));

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(126.669, 112.145),
                                new Pose(83.587, 81.909)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(35.5))
                .addParametricCallback(0, ()-> outtakeMotor.outtake_close())
                .addParametricCallback(0, ()-> servoStopper.gate_close())
                .addParametricCallback(0.75, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.97, ()-> intakeMotor.intake_stop())
                .addParametricCallback(1, ()-> servoStopper.gate_open())
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(83.587, 81.909),
                                new Pose(77.359, 53.760),
                                new Pose(128.53, 47.451)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(35.5), Math.toRadians(0))
                .addParametricCallback(0.1, ()-> servoStopper.gate_close())
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(128.53, 47.451),
                                new Pose(83.587, 81.909)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(42.5))
                .addParametricCallback(0, ()-> outtakeMotor.outtake_close())
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.97, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.98, ()-> servoStopper.gate_open())
                .build();

        gate1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(83.587, 81.909),
                                new Pose(129.847, 53.568)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(42.5), Math.toRadians(7))
                .addParametricCallback(0.1, ()-> servoStopper.gate_close())
                .addParametricCallback(0.25, ()-> intakeMotor.intake_intake())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(129.847, 53.568),
                                new Pose(83.587, 81.909)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(7), Math.toRadians(41.5))
                .addParametricCallback(0, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0, ()-> outtakeMotor.outtake_close())
                .addParametricCallback(0.1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.5, ()-> servoStopper.gate_open())
                .build();

        gate2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(83.587, 81.909),
                                new Pose(127, 53.500)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(41.5), Math.toRadians(7))
                .addParametricCallback(0.1, ()-> servoStopper.gate_close())
                .addParametricCallback(0.5, ()-> intakeMotor.intake_intake())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(127, 53.500),
                                new Pose( 83.587, 81.909)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(7), Math.toRadians(41.5))
                .addParametricCallback(0, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0, ()-> outtakeMotor.outtake_close())
                .addParametricCallback(0.97, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.98, ()-> servoStopper.gate_open())
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(83.587, 81.909),
                                new Pose(130.376, 78.217)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(41.5), Math.toRadians(0))
                .addParametricCallback(0.1, ()-> servoStopper.gate_close())
                .addParametricCallback(0.25, ()-> intakeMotor.intake_intake())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

        shoot4 = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(130.376, 78.217),
                                new Pose(83.587, 81.909)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(39.5))
                .addParametricCallback(0, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0, ()-> outtakeMotor.outtake_close())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.98, ()-> servoStopper.gate_open())
                .build();

        waitForTeleOp = follower.pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(83.587, 81.909),
                                new Pose(94, 67.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(39.5), Math.toRadians(90))
                .build();

        PanelsDrawing.init();
        waitForStart();

        if(isStopRequested()){
            return;
        }

        while(opModeIsActive() && current_state != AutoStates.end) {
            switch (current_state) {
                case preloads:
                    if (!path_started) {
                        follower.followPath(preloads);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.wait_for_preload_shot;
                    }
                    break;

                case wait_for_preload_shot:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                        }

                        if (timer.seconds() <= 0.2) {
                            outtakeMotor.outtake_close();
                        } else if (timer.seconds() >= 0.7) {
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.intake1;
                        } else if (timer.seconds() > 0.2) {
                            outtakeMotor.outtake_close();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case intake1:
                    if (!path_started) {
                        follower.followPath(intake1);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.shoot1;
                    }
                    break;

                case shoot1:
                    if (!path_started) {
                        follower.followPath(shoot1);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.wait_for_shot1;
                    }
                    break;

                case wait_for_shot1:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                        }

                        if (timer.seconds() <= 0.2) {
                            outtakeMotor.outtake_close();
                        } else if (timer.seconds() >= 0.7) {
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.gate1;
                        } else if (timer.seconds() > 0.2) {
                            outtakeMotor.outtake_close();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case gate1:
                    if (!path_started) {
                        follower.followPath(gate1);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.wait_at_gate1;
                    }
                    break;

                case wait_at_gate1:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                        }

                        if (timer.seconds() <= 1.5) {
                            intakeMotor.intake_intake();
                        } else if (timer.seconds() > 1.5) {
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.shoot2;
                        }
                    }
                    break;

                case shoot2:
                    if (!path_started) {
                        follower.followPath(shoot2);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.wait_for_shot2;
                    }
                    break;

                case wait_for_shot2:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                        }

                        if (timer.seconds() <= 0.2) {
                            outtakeMotor.outtake_close();
                        } else if (timer.seconds() >= 0.7) {
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.gate2;
                        } else if (timer.seconds() > 0.2) {
                            outtakeMotor.outtake_close();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case gate2:
                    if (!path_started) {
                        follower.followPath(gate2);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.wait_at_gate2;
                    }
                    break;
                case wait_at_gate2:
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 1.5){
                            intakeMotor.intake_intake();
                        }
                        else if(timer.seconds() > 1.5){
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.shoot3;
                        }
                    }
                    break;

                case shoot3:
                    if(!path_started){
                        follower.followPath(shoot3);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.wait_for_shot3;
                    }
                    break;

                case wait_for_shot3:
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 0.2){
                            outtakeMotor.outtake_close();
                        }
                        else if(timer.seconds() >= 0.7){
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.intake2;
                        }
                        else if(timer.seconds() > 0.2){
                            outtakeMotor.outtake_close();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case intake2:
                    if(!path_started){
                        follower.followPath(intake2);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.shoot4;
                    }
                    break;

                case shoot4:
                    if(!path_started){
                        follower.followPath(shoot4);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.wait_for_shot4;
                    }
                    break;

                case wait_for_shot4:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                        }

                        if (timer.seconds() <= 0.2) {
                            outtakeMotor.outtake_close();
                        } else if (timer.seconds() >= 0.7) {
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.teleop_standby;
                        } else if (timer.seconds() > 0.2) {
                            outtakeMotor.outtake_close();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case teleop_standby:
                    if (!path_started) {
                        follower.followPath(waitForTeleOp);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        current_state = AutoStates.end;
                        path_started = false;
                    }
                    break;

                default:
                    break;
            }

            follower.update();
            if (follower.isBusy()) {
                PanelsDrawing.drawDebug(follower);
            }
        }
    }
}
