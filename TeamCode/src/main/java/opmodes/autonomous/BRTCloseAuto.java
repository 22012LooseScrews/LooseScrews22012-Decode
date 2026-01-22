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
public class BRTCloseAuto extends LinearOpMode {
    public PathChain apriltag, preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3, waitForTeleOp;

    @Override
    public void runOpMode() throws InterruptedException {
        SpinServo spindexer = new SpinServo(this);
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);

        AutoStates current_state = AutoStates.preloads;
        Follower follower = Constants.createFollower(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        follower.setStartingPose(new Pose(121.5, 126, Math.toRadians(36)));
        boolean timer_has_started = false;
        boolean path_started = false;

        apriltag = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(121.5, 126), new Pose(88.725, 81.435))
                )
                .setLinearHeadingInterpolation(Math.toRadians(36), Math.toRadians(108))
                .build();

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(88.725, 81.435), new Pose(83.587, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(108), Math.toRadians(48))
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(83.587, 81.909),
                                new Pose(69.923, 89.320),
                                new Pose(126.664, 77.5)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(48), Math.toRadians(0))
                .addParametricCallback(0.35, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.55, () -> spindexer.spin_forward_2())
                .addParametricCallback(0.99, () -> intakeMotor.intake_stop())
                .addParametricCallback(0.95, () -> spindexer.spin_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(126.664, 77.5), new Pose(83.587, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(46))
                .addParametricCallback(0.1, () -> spindexer.spin_forward_2())
                .addParametricCallback(0.425, () -> spindexer.spin_stop())
                .addParametricCallback(0.45, ()->spindexer.spin_backward())
                .addParametricCallback(0.55, ()->spindexer.spin_stop())
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(83.587, 81.909),
                                new Pose(43.092, 61.206),
                                new Pose(129.184, 56.854)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(46), Math.toRadians(0))
                .addParametricCallback(0.3, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.3, () -> spindexer.spin_forward_2())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .addParametricCallback(0.9, () -> spindexer.spin_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(129.184, 56.854), new Pose(83.587, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(45.5))
                .addParametricCallback(0.2, () -> spindexer.spin_forward_2())
                .addParametricCallback(0.45, () -> spindexer.spin_stop())
                .build();

        intake3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(83.587, 81.909),
                                new Pose(61.443, 29.654),
                                new Pose(132.664, 32.641)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(45.5), Math.toRadians(0))
                .addParametricCallback(0.3, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.2, () -> spindexer.spin_forward_2())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .addParametricCallback(0.99, () -> spindexer.spin_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(129.664, 35.64), new Pose(83.587, 81.909))
                )

                .setLinearHeadingInterpolation((Math.toRadians(0)), Math.toRadians(45.5))
                .addParametricCallback(0.2, () -> spindexer.spin_forward_2())
                .addParametricCallback(0.7, () -> spindexer.spin_stop())
                .build();

        waitForTeleOp = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(83.587, 81.909), new Pose(94, 67))
                )
                .setLinearHeadingInterpolation(Math.toRadians(45.5), Math.toRadians(90))
                .build();

        PanelsDrawing.init();
        waitForStart();

        if (isStopRequested()) {
            return;
        }

        while (opModeIsActive() && current_state != AutoStates.end) {
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

                        if (timer.seconds() <= 1) {
                            outtakeMotor.auto_outtake_close();
                        } else if (timer.seconds() > 3) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        } else if (timer.seconds() > 1) {
                            outtakeMotor.auto_outtake_close();
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
                            outtakeMotor.auto_outtake_close();
                        } else if (timer.seconds() > 3) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake2;
                        } else if (timer.seconds() > 1) {
                            outtakeMotor.auto_outtake_close();
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
                            outtakeMotor.auto_outtake_close();
                        } else if (timer.seconds() > 3) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake3;
                        } else if (timer.seconds() > 1) {
                            outtakeMotor.auto_outtake_close();
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

                        if (timer.seconds() <= 1.5) {
                            outtakeMotor.auto_outtake_close();
                        } else if (timer.seconds() > 4) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.teleop_standby;
                        } else if (timer.seconds() > 1.5) {
                            outtakeMotor.auto_outtake_close();
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
            if (follower.isBusy()) {
                PanelsDrawing.drawDebug(follower);
            }
        }
    }
}