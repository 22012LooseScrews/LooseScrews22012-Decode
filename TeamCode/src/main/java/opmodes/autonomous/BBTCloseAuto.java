package opmodes.autonomous;

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

import abstraction.subsystems.IntakeMotor;
import abstraction.subsystems.OuttakeMotor;
import abstraction.subsystems.SpinServo;
import common.AutoStates;

@Autonomous
public class BBTCloseAuto extends LinearOpMode {
    public PathChain preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3, waitForTeleOp;

    @Override
    public void runOpMode() throws InterruptedException {
        SpinServo spindexer = new SpinServo(this);
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor OuttakeMotor = new OuttakeMotor(this);

        AutoStates current_state = AutoStates.preloads;
        Follower follower = Constants.createFollower(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        follower.setStartingPose(new Pose(22.5, 126, Math.toRadians(144)));
        boolean timer_has_started = false;
        boolean path_started = false;

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(22.5, 126), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(137))
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.413, 81.909),
                                new Pose(74.077, 89.101),
                                new Pose(19.336, 79.067)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(137), Math.toRadians(180))
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.55, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.99, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.85, () -> spindexer.spin_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(17.336, 79.067), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
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
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
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
                .setLinearHeadingInterpolation(Math.toRadians(127), Math.toRadians(180))
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.55, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.99, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.85, () -> spindexer.spin_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(14.336, 32.641), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))
                .addParametricCallback(0.2, ()-> spindexer.spin_forward_2())
                .addParametricCallback(0.25, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.95, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.4, () -> spindexer.spin_stop())
                .build();

//        waitForTeleOp = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(60.413, 81.909), new Pose(17.081, 10.438))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(130), Math.toRadians(85))
//                .build();

        PanelsDrawing.init();
        waitForStart();

        if(isStopRequested()){
            return;
        }

        while(opModeIsActive() && current_state != AutoStates.end){
            switch(current_state) {
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

                        if (timer.seconds() <= 1) {
                            OuttakeMotor.auto_outtake_close();
                        }
                        else if(timer.seconds() > 3){
                            OuttakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        }
                        else if (timer.seconds() > 1) {
                            OuttakeMotor.auto_outtake_close();
                            spindexer.spin_forward_2();
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

                        if (timer.seconds() <= 1) {
                            OuttakeMotor.auto_outtake_close();
                        }
                        else if (timer.seconds() > 3) {
                            OuttakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.end;
                        }
                        else if (timer.seconds() > 1) {
                            OuttakeMotor.auto_outtake_close();
                            spindexer.spin_forward_2();
                            intakeMotor.intake_intake();
                        }
                    }
                    break;

//                case teleop_standby:
//                    if(!path_started){
//                        follower.followPath(waitForTeleOp);
//                        path_started = true;
//                    }
//                    if(!follower.isBusy()){
//                        current_state = AutoStates.end;
//                        path_started = false;
//                    }
//                    break;

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
