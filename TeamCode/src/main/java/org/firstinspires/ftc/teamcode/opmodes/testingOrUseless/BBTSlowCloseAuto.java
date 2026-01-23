//package opmodes.autonomous;
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.PathChain;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
//import org.firstinspires.ftc.teamcode.pedroPathing.PanelsDrawing;
//
//import abstraction.subsystems.IntakeMotor;
//import abstraction.subsystems.OuttakeMotor;
//import abstraction.subsystems.SpinServo;
//import abstraction.subsystems.VectorServo;
//import common.AutoStates;
//
//@Autonomous
//public class BBTSlowCloseAuto extends LinearOpMode {
//    public PathChain preloads, intakestartseq, intakeball1, intakeball2, intakeball3, firstrow;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        SpinServo spindexer = new SpinServo(this);
//        IntakeMotor intakeMotor = new IntakeMotor(this);
//        OuttakeMotor outtakeMotor = new OuttakeMotor(this);
//        VectorServo vectorServo = new VectorServo(this);
//
//        AutoStates current_state = AutoStates.preloads;
//        Follower follower = Constants.createFollower(hardwareMap);
//        ElapsedTime timer = new ElapsedTime();
//        follower.setStartingPose(new Pose(22.5, 126, Math.toRadians(144)));
//        boolean timer_has_started = false;
//        boolean path_started = false;
//
//        preloads = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(22.600, 126.000), new Pose(60.400, 81.900))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(144), Math.toRadians(135))
//
//                .build();
//
//        intakestartseq = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(60.400, 81.900), new Pose(40.700, 87.000))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
//                .build();
//
//
//        intakeball1 = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(40.700, 87.000), new Pose(15.700, 87.000))
//                )
//                .setTangentHeadingInterpolation()
//                .addParametricCallback(0.2,()-> intakeMotor.intake_intake())
//                .addParametricCallback(0.3,()-> spindexer.spin_forward_2())
//                .addParametricCallback(0.6,()-> spindexer.spin_stop())
//                .addParametricCallback(0.99,()-> intakeMotor.intake_stop()).build();
//
//
//
//
////        intakeball2 = follower.pathBuilder()
////                .addPath(
////                        new BezierLine(new Pose(20.700, 87.000), new Pose(30.700, 87.000)))
////                .setTangentHeadingInterpolation()
////                .addParametricCallback(0.2,()-> intakeMotor.intake_intake())
////                .addParametricCallback(0.3,()-> spindexer.spin_forward_2())
////                .addParametricCallback(0.99,()-> spindexer.spin_stop())
////                .addParametricCallback(0.99,()-> intakeMotor.intake_stop()).build();
////
////
////        intakeball3 = follower.pathBuilder()
////                .addPath(
////                        new BezierLine(new Pose(30.700, 87.000), new Pose(25.700, 87.000))
////                )
////                .setTangentHeadingInterpolation()
////                .addParametricCallback(0.2,()-> intakeMotor.intake_intake())
////                .addParametricCallback(0.3,()-> spindexer.spin_forward_2())
////                .addParametricCallback(0.99,()-> spindexer.spin_stop())
////                .addParametricCallback(0.99,()-> intakeMotor.intake_stop()).build();
//
//        firstrow = follower.pathBuilder()
//                .addPath(
//                        new BezierLine(new Pose(15.700, 87.000), new Pose(60.400, 81.900))
//                )
//                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(135))
//                .build();
//        PanelsDrawing.init();
//        waitForStart();
//
//        if(isStopRequested()){
//            return;
//        }
//        while(opModeIsActive() && current_state != AutoStates.end){
//            if(follower.isBusy()){
//
//            }
//            switch(current_state) {
//                case preloads:
//                    if (!path_started) {
//                        follower.followPath(preloads);
//                        path_started = true;
//                    }
//                    if (!follower.isBusy()) {
//                        path_started = false;
//                        current_state = AutoStates.wait_for_preload_shot;
//                    }
//                    break;
//
//                case wait_for_preload_shot:
//                    if (!follower.isBusy()) {
//                        if (!timer_has_started) {
//                            timer.reset();
//                            timer_has_started = true;
//                        }
//
//                        if (timer.seconds() <= 1.25) {
//                            outtakeMotor.outtake_close();
//                        } else if (timer.seconds() > 5.5) {
//                            outtakeMotor.outtake_stop();
//                            spindexer.spin_stop();
//                            timer_has_started = false;
//
//                            current_state = AutoStates.intake1;
//                        } else if (timer.seconds() >= 5.4) {
//                            spindexer.spin_stop();
//                        } else if (timer.seconds() > 5) {
//                            spindexer.spin_forward_2();
//                        } else if (timer.seconds() > 3.9) {
//                            spindexer.spin_stop();
//                        } else if (timer.seconds() > 3.5) {
//                            spindexer.spin_forward_2();
//                        } else if (timer.seconds() > 2.25) {
//                            spindexer.spin_stop();
//                        } else if (timer.seconds() > 1.5) {
//                            outtakeMotor.outtake_close();
//                            spindexer.spin_forward_2();
//                        }
//                    }
//                    break;
//                case intakestartseq:
//                    if (!path_started) {
//                        follower.followPath(intakestartseq);
//                        path_started = true;
//                    }
//                    if (!follower.isBusy()) {
//                        path_started = false;
//                        current_state = AutoStates.intake1;
//                    }
//                    break;
//                case intake1:
//                    if (!path_started) {
//                        follower.followPath(intakeball1);
//                        path_started = true;
//                    }
//                    if (!follower.isBusy()) {
//                        path_started = false;
//                        current_state = AutoStates.shoot1;
//                    }
//                    break;
////                case intake2:
////                    if (!path_started) {
////                        follower.followPath(intakeball2);
////                        path_started = true;
////                    }
////                    if (!follower.isBusy()) {
////                        path_started = false;
////                        current_state = AutoStates.intake3;
////                    }
////                    break;
////                case intake3:
////                    if (!path_started) {
////                        follower.followPath(intakeball3);
////                        path_started = true;
////                    }
////                    if (!follower.isBusy()) {
////                        path_started = false;
////                        current_state = AutoStates.shoot1;
////                    }
////                    break;
//                case shoot1:
//                    if (!path_started) {
//                        follower.followPath(firstrow);
//                        path_started = true;
//                    }
//                    if (!follower.isBusy()) {
//                        path_started = false;
//                        current_state = AutoStates.wait_for_shot1;
//                    }
//                    break;
//                case wait_for_shot1:
//                    if (!follower.isBusy()) {
//                        if (!timer_has_started) {
//                            timer.reset();
//                            timer_has_started = true;
//                        }
//
//                        if (timer.seconds() <= 1.25) {
//                            outtakeMotor.outtake_close();
//                        } else if (timer.seconds() > 5.5) {
//                            outtakeMotor.outtake_stop();
//                            spindexer.spin_stop();
//                            timer_has_started = false;
//
//                            current_state = AutoStates.end;
//                        } else if (timer.seconds() >= 5.4) {
//                            spindexer.spin_stop();
//                        } else if (timer.seconds() > 5) {
//                            spindexer.spin_forward_2();
//                        } else if (timer.seconds() > 3.9) {
//                            spindexer.spin_stop();
//                        } else if (timer.seconds() > 3.5) {
//                            spindexer.spin_forward_2();
//                        } else if (timer.seconds() > 2.25) {
//                            spindexer.spin_stop();
//                        } else if (timer.seconds() > 1.5) {
//                            outtakeMotor.outtake_close();
//                            spindexer.spin_forward_2();
//                        }
//                    }
//                    break;
//
//                default:
//                    break;
//            }
//            follower.update();
//            if(follower.isBusy()){
//                PanelsDrawing.drawDebug(follower);
//            }
//        }
//    }
//}