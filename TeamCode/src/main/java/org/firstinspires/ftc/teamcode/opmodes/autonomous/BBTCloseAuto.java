package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.PanelsDrawing;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.SpinServo;
import org.firstinspires.ftc.teamcode.common.AutoStates;
import org.firstinspires.ftc.teamcode.abstractions.SpinMotor;

@Autonomous
public class BBTCloseAuto extends LinearOpMode {
    public PathChain  preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3, waitForTeleOp;

    @Override
    public void runOpMode() throws InterruptedException {
        SpinServo spindexer = new SpinServo(this);
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor OuttakeMotor = new OuttakeMotor(this);
        SpinMotor spinMotor = new SpinMotor(this);


        AutoStates current_state = AutoStates.preloads;
        Follower follower = Constants.createFollower(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        follower.setStartingPose(new Pose(22.5, 126, Math.toRadians(144)));
        boolean timer_has_started = false;
        boolean path_started = false;

//        apriltag = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(22.5, 126), new Pose(55.275, 81.435))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(72))
//                .build();

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(55.275, 81.435), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(137.5))
                //.addParametricCallback(0.01, ()-> OuttakeMotor.auto_outtake_close())
                //.addParametricCallback(0.85, ()-> spinMotor.add360Degrees(0.55))
              // .addParametricCallback(1, ()-> OuttakeMotor.outtake_stop())
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.413, 81.909),
                                new Pose(74.077, 89.101),
                                new Pose(17.336, 79.067)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(137.5), Math.toRadians(180))
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.55, ()-> spinMotor.add360Degrees(0.55))
                .addParametricCallback(0.99, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.575, () -> spindexer.spin_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(17.336, 79.067), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(136.5))
                .addParametricCallback(0.2, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.1, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.8, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.6, () -> spindexer.spin_stop())
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.413, 81.909),
                                new Pose(100.908, 58.206),
                                new Pose(12, 56.5)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(136.5), Math.toRadians(180))
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.55, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.99, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.85, () -> spindexer.spin_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(12, 56.5), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(125.5))
                .addParametricCallback(0.2, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.1, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.95, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.6, () -> spindexer.spin_stop())
                .build();

        intake3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.413, 81.909),
                                new Pose(78.557, 29.654),
                                new Pose(11.336, 32.641)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(125.5), Math.toRadians(180))
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.55, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.99, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.85, () -> spindexer.spin_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(11.336, 32.641), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))
                .addParametricCallback(0.2, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.25, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.95, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.4, () -> spindexer.spin_stop())
                .addParametricCallback(0.45, ()->spindexer.spin_backward())
                .addParametricCallback(0.55, ()->spindexer.spin_stop())
                .build();

        waitForTeleOp = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(60.413, 81.909), new Pose(50, 67))
                )
                .setLinearHeadingInterpolation(Math.toRadians(120), Math.toRadians(90))
                .build();

        PanelsDrawing.init();
        waitForStart();

        if(isStopRequested()){
            return;
        }

        while(opModeIsActive() && current_state != AutoStates.end){
            switch(current_state) {
//                case apriltag:
//                    if(!path_started){
//                        follower.followPath(apriltag);
//                        path_started = true;
//                    }
//                    if(!follower.isBusy()){
//                        path_started = false;
//                        current_state = AutoStates.preloads;
//                    }
//                    break;

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

                        if (timer.seconds() <= 0.5) {
                            OuttakeMotor.auto_outtake_close();

                        }
                        else if(timer.seconds() > 3.5){
                            OuttakeMotor.outtake_stop();

                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        }
                        else if(timer.seconds() >=2){
                            spinMotor.add360Degrees(0.55);
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
                        if (timer.seconds() <= .1) {
                            intakeMotor.intake_intake();
                        }
                        if (timer.seconds() <= 1) {
                            OuttakeMotor.auto_outtake_close();
                        }
                        else if (timer.seconds() > 3) {
                            OuttakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake2;
                        }
                        else if (timer.seconds() > 1) {
                            OuttakeMotor.auto_outtake_close();
                            spindexer.spin_forward_2();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case intake2:
                    if (!path_started) {
                        follower.followPath(intake2);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        current_state = AutoStates.shoot2;
                        path_started = false;
                    }
                    break;

                case shoot2:
                    if (!path_started) {
                        follower.followPath(shoot2);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        current_state = AutoStates.wait_for_shot2;
                        path_started = false;
                    }
                    break;

                case wait_for_shot2:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                        }
                        if (timer.seconds() <= .1) {
                            intakeMotor.intake_intake();
                        }
                        if (timer.seconds() <= 1) {
                            OuttakeMotor.auto_outtake_close();
                        }
                        else if (timer.seconds() > 3) {
                            OuttakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake3;
                        }
                        else if (timer.seconds() > 1) {
                            OuttakeMotor.auto_outtake_close();
                            spindexer.spin_forward_2();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case intake3:
                    if (!path_started) {
                        follower.followPath(intake3);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        current_state = AutoStates.shoot3;
                        path_started = false;
                    }
                    break;

                case shoot3:
                    if (!path_started) {
                        follower.followPath(shoot3);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        current_state = AutoStates.wait_for_shot_3;
                        path_started = false;
                    }
                    break;

                case wait_for_shot_3:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                        }
                        if (timer.seconds() <= .1) {
                            intakeMotor.intake_intake();
                        }
                        if (timer.seconds() <= 1) {
                            OuttakeMotor.auto_outtake_close();
                        }
                        else if (timer.seconds() > 3) {
                            OuttakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.teleop_standby;
                        }
                        else if (timer.seconds() > 1) {
                            OuttakeMotor.auto_outtake_close();
                            spindexer.spin_forward_2();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case teleop_standby:
                    if(!path_started){
                        follower.followPath(waitForTeleOp);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        current_state = AutoStates.end;
                        path_started = false;
                    }
                    break;

                default:
                    break;
            }

            follower.update();
            if(follower.isBusy()){
                PanelsDrawing.drawDebug(follower);
            }
        }
    }
}
