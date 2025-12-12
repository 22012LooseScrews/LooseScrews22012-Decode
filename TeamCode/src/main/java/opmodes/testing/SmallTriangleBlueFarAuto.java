package opmodes.testing;

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
import abstraction.subsystems.VectorServo;
import common.AutoStates;

@Autonomous
public class SmallTriangleBlueFarAuto extends LinearOpMode {
    public PathChain preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3;

    @Override
    public void runOpMode() throws InterruptedException {
        SpinServo spindexer = new SpinServo(this);
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);
        VectorServo vectorServo = new VectorServo(this);

        AutoStates current_state = AutoStates.preloads;
        Follower follower = Constants.createFollower(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        follower.setStartingPose(new Pose(56, 8, Math.toRadians(90)));
        boolean timer_has_started = false;
        boolean path_started = false;

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(56.000, 8.000), new Pose(62.392, 21.825))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(122.5))
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(62.392, 21.825),
                                new Pose(83.506, 40.567),
                                new Pose(21.336, 35.641)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(122.5), Math.toRadians(180))
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.3, ()->vectorServo.vector_intake())
                .addParametricCallback(0.3, ()-> spindexer.spin_forward_2())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(1, () -> vectorServo.vector_stop())
                .addParametricCallback(0.9, () -> spindexer.spin_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(21.336, 35.641), new Pose(62.392, 21.825))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(122.5))
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(62.392, 21.825),
                                new Pose(92.521, 63.341),
                                new Pose(21.816, 59.854)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(122.5), Math.toRadians(180))
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.3, ()-> vectorServo.vector_intake())
                .addParametricCallback(0.3, ()-> spindexer.spin_forward_2())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(1, ()-> vectorServo.vector_stop())
                .addParametricCallback(0.9, () -> spindexer.spin_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(21.816, 59.854), new Pose(62.392, 21.825))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(122.5))
                .build();

        intake3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(62.392, 21.825),
                                new Pose(89.911, 89.199),
                                new Pose(21.336, 84.067)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(122.5), Math.toRadians(180))
                .addParametricCallback(0.3, ()-> intakeMotor.intake_intake())
                .addParametricCallback(0.3, ()-> vectorServo.vector_intake())
                .addParametricCallback(0.3, ()-> spindexer.spin_forward_2())
                .addParametricCallback(1, ()-> intakeMotor.intake_stop())
                .addParametricCallback(1, ()->vectorServo.vector_stop())
                .addParametricCallback(0.9, () -> spindexer.spin_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(21.336, 84.067), new Pose(62.392, 21.825))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(122.5))
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

                        if(timer.seconds() <= 1.25){
                            outtakeMotor.outtake_far();
                        }
                        else if(timer.seconds() > 5.8){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        }
                        else if(timer.seconds() >= 5.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 5.05){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 3.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 3.5){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 2.25){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 1.5){
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

                        if(timer.seconds() <= 1.25){
                            outtakeMotor.outtake_far();
                        }
                        else if(timer.seconds() > 5.8){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake2;
                        }
                        else if(timer.seconds() >= 5.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 5.05){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 3.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 3.5){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 2.25){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 1.5){
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
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

                        if(timer.seconds() <= 1.25){
                            outtakeMotor.outtake_far();
                        }
                        else if(timer.seconds() > 5.8){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake3;
                        }
                        else if(timer.seconds() >= 5.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 5.05){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 3.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 3.5){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 2.25){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 1.5){
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
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

                        if(timer.seconds() <= 1.25){
                            outtakeMotor.outtake_far();
                        }
                        else if(timer.seconds() > 5.8){
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.end;
                        }
                        else if(timer.seconds() >= 5.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 5.05){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 3.8){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 3.5){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 2.25){
                            spindexer.spin_stop();
                        }
                        else if(timer.seconds() > 1.5){
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
                        }
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
