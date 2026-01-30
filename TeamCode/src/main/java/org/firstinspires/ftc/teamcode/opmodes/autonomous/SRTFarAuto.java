package org.firstinspires.ftc.teamcode.opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.abstractions.RevColorSensor;
import org.firstinspires.ftc.teamcode.abstractions.SpinMotor;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.PanelsDrawing;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.SpinServo;
import org.firstinspires.ftc.teamcode.common.AutoStates;

@Autonomous
public class SRTFarAuto extends LinearOpMode {
    public PathChain apriltag, preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3, waitForTeleOp;
    boolean path_started = false;
    boolean has_spun_path = false;
    boolean last_sample_detected = false;

    @Override
    public void runOpMode() throws InterruptedException {
        SpinServo spindexer = new SpinServo(this);
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);
        SpinMotor spinMotor = new SpinMotor(this);

        RevColorSensor colorSensor = new RevColorSensor();
        RevColorSensor.DetectedColor detectedColor;

        AutoStates current_state = AutoStates.preloads;
        Follower follower = Constants.createFollower(hardwareMap);

        ElapsedTime timer = new ElapsedTime();
        follower.setStartingPose(new Pose(84, 9, Math.toRadians(90)));
        boolean timer_has_started = false;
        colorSensor.init(hardwareMap);

//        apriltag = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(84, 9), new Pose(84.455, 23.231))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(96))
//                .build();

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(84, 9), new Pose(87.0,14))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(56.5))
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(87, 14),
                                new Pose(75, 33),
                                new Pose(134, 32.641)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(56.5), Math.toRadians(0))
                .addParametricCallback(0.35, () -> intakeMotor.intake_intake())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(134.664, 32.641), new Pose(87, 14))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(66.5))
                .addParametricCallback(0.01, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.8,() -> intakeMotor.intake_stop())
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(87, 14),
                                new Pose(65, 63),
                                new Pose(131, 55)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(66.5), Math.toRadians(0))
                .addParametricCallback(0.35, () -> intakeMotor.intake_intake())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(131, 55),
                                new Pose(93,57),
                                new Pose(84, 82)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(49.5))
                .addParametricCallback(0.01, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.8,() -> intakeMotor.intake_stop())
                .build();

        intake3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(84, 82),
                                new Pose(91,67),
                                new Pose(126.5, 88)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(49.5), Math.toRadians(0))
                .addParametricCallback(0.35, () -> intakeMotor.intake_intake())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(126.5, 88), new Pose(84, 82))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(48.5))
                .addParametricCallback(0.01, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.8,() -> intakeMotor.intake_stop())
                .build();

        waitForTeleOp = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(84, 82), new Pose(94, 67))
                )
                .setLinearHeadingInterpolation(Math.toRadians(48.5), Math.toRadians(90))
                .build();

        PanelsDrawing.init();
        waitForStart();

        if (isStopRequested()) {
            return;
        }

        while (opModeIsActive() && current_state != AutoStates.end) {
            detectedColor = colorSensor.getDetectedColor(telemetry);
            boolean current_sample_detected = (detectedColor == RevColorSensor.DetectedColor.GREEN || detectedColor == RevColorSensor.DetectedColor.PURPLE);

            switch (current_state) {
//                case apriltag:
//                    if(!path_started) {
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
                        has_spun_path = false;
                        follower.followPath(preloads);
                        path_started = true;
                    }
                    if (!follower.isBusy() && !spinMotor.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.wait_for_preload_shot;
                    }
                    break;

                case wait_for_preload_shot:
                    if (!follower.isBusy()) {
                        if (!timer_has_started) {
                            timer.reset();
                            timer_has_started = true;
                            has_spun_path = false;
                        }
                        if (timer.seconds() <= 1) {
                            outtakeMotor.auto_outtake_close();
                        }
                        else if(timer.seconds() > 2.75){
                            outtakeMotor.outtake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        }
                        else if (timer.seconds() > 2) {
                            outtakeMotor.auto_outtake_close();
                            triggerSpin2(spinMotor);
                        }
                    }
                    break;

                case intake1:
                    if (!path_started) {
                        has_spun_path = false;
                        last_sample_detected = false;
                        follower.followPath(intake1);
                        path_started = true;
                    }
                    if(current_sample_detected && !last_sample_detected){
                        triggerSpin(spinMotor);
                    }
                    last_sample_detected = current_sample_detected;
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
                            has_spun_path = false;
                        }
                        if (timer.seconds() <= 1) {
                            outtakeMotor.auto_outtake_close();
                        }
                        else if(timer.seconds() > 2.75){
                            outtakeMotor.outtake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake2;
                        }
                        else if (timer.seconds() > 2) {
                            outtakeMotor.auto_outtake_close();
                            triggerSpin2(spinMotor);
                        }
                    }
                    break;

                case intake2:
                    if (!path_started) {
                        has_spun_path = false;
                        last_sample_detected = false;
                        follower.followPath(intake2);
                        path_started = true;
                    }
                    if(current_sample_detected && !last_sample_detected){
                        triggerSpin(spinMotor);
                    }
                    last_sample_detected = current_sample_detected;
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.shoot2;
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
                            has_spun_path = false;
                        }
                        if (timer.seconds() <= 1) {
                            outtakeMotor.auto_outtake_close();
                        }
                        else if(timer.seconds() > 2.75){
                            outtakeMotor.outtake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake3;
                        }
                        else if (timer.seconds() > 2) {
                            outtakeMotor.auto_outtake_close();
                            triggerSpin2(spinMotor);
                        }
                    }
                    break;

                case intake3:
                    if (!path_started) {
                        has_spun_path = false;
                        last_sample_detected = false;
                        follower.followPath(intake3);
                        path_started = true;
                    }
                    if(current_sample_detected && !last_sample_detected){
                        triggerSpin(spinMotor);
                    }
                    last_sample_detected = current_sample_detected;
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.shoot3;
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
                            has_spun_path = false;
                        }
                        if (timer.seconds() <= 1) {
                            outtakeMotor.auto_outtake_close();
                        }
                        else if(timer.seconds() > 2.75){
                            outtakeMotor.outtake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.teleop_standby;
                        }
                        else if (timer.seconds() > 2) {
                            outtakeMotor.auto_outtake_close();
                            triggerSpin2(spinMotor);
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
    public void triggerSpin(SpinMotor spinMotor){
        spinMotor.add120Degrees(0.55);
    }
    public void triggerSpin2(SpinMotor spinMotor){
        if(!has_spun_path){
            spinMotor.add360Degrees(0.55);
            has_spun_path = true;
        }
    }
}