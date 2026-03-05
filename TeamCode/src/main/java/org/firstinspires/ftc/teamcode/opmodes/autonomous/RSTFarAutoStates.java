package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

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
public class RSTFarAutoStates extends LinearOpMode {
    public PathChain preloads, intake1, shoot1, wait1, square1, wait2, square2, shoot2, square3, shoot3, square4, shoot4, waitForTeleOp;
    boolean path_started = false;
    boolean timer_has_started = false;

    @Override
    public void runOpMode() throws InterruptedException{
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);
        ServoStopper servoStopper = new ServoStopper(this);

        AutoStates current_state = AutoStates.preloads;
        ElapsedTime timer = new ElapsedTime();
        Follower follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(88.58, 9.085, Math.toRadians(90)));

        preloads = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(88.58, 9.085), new Pose(84.7, 17.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(65))
                .addParametricCallback(0, ()-> outtakeMotor.outtake_far())
                .addParametricCallback(0.1, ()-> servoStopper.gate_open())
                .build();

        intake1 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(84.7, 17.300),
                                new Pose(95.394, 34.524),
                                new Pose(123.151, 35.659)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(0))
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.3, ()-> servoStopper.gate_close())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

        shoot1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(123.151, 35.659), new Pose(84.7, 17.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(62))
                .addParametricCallback(0, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.3, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0, ()-> outtakeMotor.outtake_far())
                .build();

//        wait1 = follower
//                .pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(59.300, 17.300), new Pose(11, 4))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(112), Math.toRadians(180))
//                .addParametricCallback(0, ()-> servoStopper.gate_close())
//                .build();

        square1 = follower
                .pathBuilder()
                .addPath(new BezierLine(new Pose(84.7, 17.300), new Pose(132, 4.000)))
                .setLinearHeadingInterpolation(Math.toRadians(62), Math.toRadians(0), 0.5)
                .addParametricCallback(0, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.3, ()-> servoStopper.gate_close())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

//        wait2 = follower
//                .pathBuilder()
//                .addPath(new BezierLine(new Pose(14.00, 4), new Pose(19.100, 4)))
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                .build();

//        square2 = follower
//                .pathBuilder()
//                .addPath(new BezierCurve(new Pose(12, 4.000),new Pose(35,20), new Pose(23.00, 4)))
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
//                .addParametricCallback(0, ()-> intakeMotor.intake_intake())
//                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
//                .build();

        shoot2 = follower
                .pathBuilder()
                .addPath(new BezierCurve(new Pose(132, 4.000),
                        new Pose(109,20),
                        new Pose(84.7, 17.3)))
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(60))
                .addParametricCallback(0, ()-> outtakeMotor.outtake_far())
                .addParametricCallback(0, ()-> intakeMotor.intake_intake())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

        square3 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(new Pose(84.7, 17.300), new Pose(118,34), new Pose(130, 2.00))
                )
                .setLinearHeadingInterpolation(Math.toRadians(60), Math.toRadians(310), 0.9)
                .addParametricCallback(0.2, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0, ()-> servoStopper.gate_close())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

        shoot3 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(130, 2.00), new Pose(84.7, 17.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(310), Math.toRadians(77.5))
                .addParametricCallback(1, ()-> servoStopper.gate_open())
                .addParametricCallback(0, ()-> outtakeMotor.outtake_far())
                .build();

        square4 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(new Pose(84.7, 17.300),new Pose(118,34), new Pose(129, 2.00))
                )
                .setLinearHeadingInterpolation(Math.toRadians(77.5), Math.toRadians(310), 0.9)
                .addParametricCallback(0., ()-> servoStopper.gate_close())
                .addParametricCallback(0.2, ()-> intakeMotor.intake_intake())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .build();

        shoot4 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(129, 2.00), new Pose(84.7, 17.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(310), Math.toRadians(68))
                .addParametricCallback(1, ()-> servoStopper.gate_open())
                .addParametricCallback(0, ()-> outtakeMotor.outtake_far())
                .build();

        waitForTeleOp = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(84.7, 17.300), new Pose(99, 17.300))
                )
                .setLinearHeadingInterpolation(Math.toRadians(68), Math.toRadians(0))
                .build();
        PanelsDrawing.init();
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

                        if(timer.seconds() >= 2.5){
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();

                            timer_has_started = false;
                            current_state = AutoStates.intake1;
                        }
                        else if(timer.seconds() >= 1.6){
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case intake1:
                    if(!path_started){
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

                        if(timer.seconds() <= 0.75){
                            servoStopper.gate_open();
                        }
                        else if(timer.seconds() >= 2.5){
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;
                            current_state = AutoStates.square1;
                        }
                        else if(timer.seconds() > 1.5){
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

//                case wait1:
//                    if(!path_started){
//                        follower.followPath(wait1);
//                        path_started = true;
//                    }
//                    if(!follower.isBusy()){
//                        path_started = false;
//                        current_state = AutoStates.square1;
//                    }
//                    break;

                case square1:
                    if(!path_started){
                        follower.followPath(square1);
                        path_started = true;
                    }
//                    if(path_started){
//                        if(!timer_has_started){
//                            timer.reset();
//                            timer_has_started = true;
//                        }
//                        if(timer.seconds() >= 5){
//                            path_started = false;
//                            current_state = AutoStates.square2;
//                        }
//                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.shoot2;
                    }
                    break;

//                case wait2:
//                    if(!path_started){
//                        follower.followPath(wait2);
//                        path_started = true;
//                    }
//                    if(!follower.isBusy()){
//                        path_started = false;
//                        current_state = AutoStates.square2;
//                    }
//                    break;

//                case square2:
//                    if(!path_started){
//                        follower.followPath(square2);
//                        path_started = true;
//                    }
//                    if(!follower.isBusy()){
//                        path_started = false;
//                        current_state = AutoStates.shoot2;
//                    }
//                    break;

                case shoot2:
                    if(!path_started){
                        follower.followPath(shoot2);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.wait_for_shot2;
                    }
                    break;

                case wait_for_shot2:
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 0.75){
                            servoStopper.gate_open();
                        }
                        else if(timer.seconds() >= 2.5){
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;
                            current_state = AutoStates.square3;
                        }
                        else if(timer.seconds() > 1.5){
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case square3:
                    if(!path_started){
                        follower.followPath(square3);
                        path_started = true;
                    }
//                    if(path_started){
//                        if(!timer_has_started){
//                            timer.reset();
//                            timer_has_started = true;
//                        }
//                        if(timer.seconds() >= 5){
//                            path_started = false;
//                            current_state = AutoStates.shoot3;
//                        }
//                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.shoot3;
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

                        if(timer.seconds() <= 0.75){
                            servoStopper.gate_open();
                        }
                        else if(timer.seconds() >= 2.5){
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;
                            current_state = AutoStates.square4;
                        }
                        else if(timer.seconds() > 1.5){
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

                case square4:
                    if(!path_started){
                        follower.followPath(square4);
                        path_started = true;
                    }
//                    if(path_started){
//                        if(!timer_has_started){
//                            timer.reset();
//                            timer_has_started = true;
//                        }
//                        if(timer.seconds() >= 5){
//                            path_started = false;
//                            current_state = AutoStates.shoot4;
//                        }
//                    }
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
                    if(!follower.isBusy()){
                        if(!timer_has_started){
                            timer.reset();
                            timer_has_started = true;
                        }

                        if(timer.seconds() <= 0.75){
                            servoStopper.gate_open();
                        }
                        else if(timer.seconds() >= 2.5){
                            outtakeMotor.outtake_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;
                            current_state = AutoStates.teleop_standby;
                        }
                        else if(timer.seconds() > 1.5){
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