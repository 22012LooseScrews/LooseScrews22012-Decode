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
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.PanelsDrawing;

import org.firstinspires.ftc.teamcode.abstractions.IntakeMotor;
import org.firstinspires.ftc.teamcode.abstractions.OuttakeMotor;
import org.firstinspires.ftc.teamcode.common.AutoStates;
import org.firstinspires.ftc.teamcode.abstractions.SpinMotor;

@Autonomous
public class BBLeagueAuto extends LinearOpMode {
    public PathChain preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3, waitForTeleOp;
    boolean path_started = false;
    boolean has_spun_path = false;
    boolean last_sample_detected = false;

    @Override
    public void runOpMode() throws InterruptedException {
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor OuttakeMotor = new OuttakeMotor(this);
        SpinMotor spinMotor = new SpinMotor(this);

        RevColorSensor colorSensor = new RevColorSensor();
        RevColorSensor.DetectedColor detectedColor;

        AutoStates current_state = AutoStates.preloads;
        ElapsedTime timer = new ElapsedTime();

        boolean timer_has_started = false;
        Follower follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(22.5, 126, Math.toRadians(144)));
        colorSensor.init(hardwareMap);

//        apriltag = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(22.5, 126), new Pose(55.275, 81.435))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(72))
//                .build();

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(22.5, 126), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(132))
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.413, 81.909),
                                new Pose(74.077, 89.101),
                                new Pose(15.5, 80.5)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(132), Math.toRadians(180))
                .addParametricCallback(0.35,()->intakeMotor.intake_intake())
                .addParametricCallback(1,()->intakeMotor.intake_stop())
                .addParametricCallback(0.6, () -> spinMotor.spin_forward_auto())
                .addParametricCallback(0.9, () -> spinMotor.spin_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(15.5, 80.5), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134.5))
                .addParametricCallback(0.01, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.65, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0, () -> spinMotor.spin_forward())
                .addParametricCallback(0.3, () -> spinMotor.spin_stop())
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.413, 81.909),
                                new Pose(100.908, 58.206),
                                new Pose(12, 55.75)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(134.5), Math.toRadians(180))
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.6, () -> spinMotor.spin_forward_auto())
                .addParametricCallback(0.9, () -> spinMotor.spin_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(12, 55.75), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(129))
                .addParametricCallback(0.01, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.65, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0, () -> spinMotor.spin_forward())
                .addParametricCallback(0.5, () -> spinMotor.spin_stop())
                .build();

        intake3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60.413, 81.909),
                                new Pose(78.557, 35.654),
                                new Pose(11.336, 34.891)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(129), Math.toRadians(180))
                .addParametricCallback(0.35, ()-> intakeMotor.intake_intake())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0.6, () -> spinMotor.spin_forward_auto())
                .addParametricCallback(0.9, () -> spinMotor.spin_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(11.336, 34.891), new Pose(60.413, 81.909))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(120))
                .addParametricCallback(0.01, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.65, ()-> intakeMotor.intake_stop())
                .addParametricCallback(0, () -> spinMotor.spin_forward())
                .addParametricCallback(0.3, () -> spinMotor.spin_stop())
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
            detectedColor = colorSensor.getDetectedColor(telemetry);
            boolean current_sample_detected = (detectedColor == RevColorSensor.DetectedColor.GREEN || detectedColor == RevColorSensor.DetectedColor.PURPLE);

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
                            OuttakeMotor.auto_outtake_close();
                        }
                        else if(timer.seconds() > 2.75){
                            OuttakeMotor.outtake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        }
                        else if (timer.seconds() > 2) {
                            OuttakeMotor.auto_outtake_close();
                            triggerSpin2(spinMotor);
                        }
                    }
                    break;

                case intake1:
                    if (!path_started) {
//                        has_spun_path = false;
//                        last_sample_detected = false;
                        follower.followPath(intake1);
                        path_started = true;
                    }
//                    if(current_sample_detected && !last_sample_detected){
//                        triggerSpin(spinMotor);
//                    }
//                    last_sample_detected = current_sample_detected;
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.shoot1;
                    }
                    break;

                case shoot1:
                    if (!path_started) {
                        intakeMotor.intake_intake();
                        follower.followPath(shoot1);
                        path_started = true;
                        has_spun_path = false;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        intakeMotor.intake_stop();
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
                            OuttakeMotor.auto_outtake_close();
                            intakeMotor.intake_intake();
                        }
                        else if(timer.seconds() > 2.75){
                            OuttakeMotor.outtake_stop();
                            timer_has_started = false;
                            intakeMotor.intake_stop();

                            current_state = AutoStates.intake2;
                        }
                        else if (timer.seconds() > 2) {
                            OuttakeMotor.auto_outtake_close();
                            intakeMotor.intake_intake();
                            triggerSpin2(spinMotor);
                        }
                    }
                    break;

                case intake2:
                    if (!path_started) {
//                        has_spun_path = false;
//                        last_sample_detected = false;
                        follower.followPath(intake2);
                        path_started = true;
                    }
//                    if(current_sample_detected && !last_sample_detected){
//                        triggerSpin(spinMotor);
//                    }
//                    last_sample_detected = current_sample_detected;
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.shoot2;
                    }
                    break;

                case shoot2:
                    if (!path_started) {
                        intakeMotor.intake_intake();
                        follower.followPath(shoot2);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        path_started = false;
                        intakeMotor.intake_stop();
                        current_state = AutoStates.wait_for_shot2;
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
                            OuttakeMotor.auto_outtake_close();
                            intakeMotor.intake_intake();
                        }
                        else if(timer.seconds() > 2.75){
                            OuttakeMotor.outtake_stop();
                            timer_has_started = false;
                            intakeMotor.intake_stop();

                            current_state = AutoStates.intake3;
                        }
                        else if (timer.seconds() > 2) {
                            OuttakeMotor.auto_outtake_close();
                            intakeMotor.intake_intake();
                            triggerSpin2(spinMotor);
                        }
                    }
                    break;

                case intake3:
                    if (!path_started) {
//                        has_spun_path = false;
//                        last_sample_detected = false;
                        follower.followPath(intake3);
                        path_started = true;
                    }
//                    if(current_sample_detected && !last_sample_detected){
//                        triggerSpin(spinMotor);
//                    }
//                    last_sample_detected = current_sample_detected;
                    if (!follower.isBusy()) {
                        path_started = false;
                        current_state = AutoStates.shoot3;
                    }
                    break;

                case shoot3:
                    if (!path_started) {
                        intakeMotor.intake_intake();
                        follower.followPath(shoot3);
                        path_started = true;
                    }
                    if (!follower.isBusy()) {
                        intakeMotor.intake_stop();
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
                            OuttakeMotor.auto_outtake_close();
                            intakeMotor.intake_intake();
                        }
                        else if(timer.seconds() > 2.75){
                            OuttakeMotor.outtake_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.teleop_standby;
                        }
                        else if (timer.seconds() > 2) {
                            OuttakeMotor.auto_outtake_close();
                            intakeMotor.intake_intake();
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
            if(follower.isBusy()){
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
