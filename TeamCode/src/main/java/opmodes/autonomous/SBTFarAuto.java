package opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
//import com.qualcomm.hardware.limelightvision.LLResult;
//import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.PanelsDrawing;

import abstraction.subsystems.IntakeMotor;
import abstraction.subsystems.OuttakeMotor;
import abstraction.subsystems.SpinServo;
import common.AutoStates;

@Autonomous
public class SBTFarAuto extends LinearOpMode {
    public PathChain apriltag, preloads, intake1, shoot1, intake2, shoot2, intake3, shoot3, waitForTeleOp;
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;
//    private Limelight3A limelight;
    private static final double kp_turn = 0.03;
    private static final double max_speed = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
//        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
//        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
//        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
//        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
//
//        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        SpinServo spindexer = new SpinServo(this);
        IntakeMotor intakeMotor = new IntakeMotor(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);

//        limelight = hardwareMap.get(Limelight3A.class, "limelight");
//        limelight.pipelineSwitch(2);

        AutoStates current_state = AutoStates.apriltag;
        Follower follower = Constants.createFollower(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        follower.setStartingPose(new Pose(60, 9, Math.toRadians(90)));
        boolean timer_has_started = false;
        boolean path_started = false;

        apriltag = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(60, 9), new Pose(59.545, 28.231))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(84))
                .build();

        preloads = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(59.545, 28.231), new Pose(57.0,14))
                )
                .setLinearHeadingInterpolation(Math.toRadians(84), Math.toRadians(113))
                .build();

        intake1 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(57, 14),
                                new Pose(75, 33),
                                new Pose(12.336, 34.641)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(113), Math.toRadians(180))
                .addParametricCallback(0.2, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.3, () -> spindexer.spin_forward_2())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .addParametricCallback(0.99, () -> spindexer.spin_stop())
                .build();

        shoot1 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(12.336, 34.641), new Pose(61, 16.5))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(105.5))
                .addParametricCallback(0.1, () -> spindexer.spin_forward_2())
                .addParametricCallback(0.3, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.2,() -> spindexer.spin_stop())
                .addParametricCallback(0.5,() -> intakeMotor.intake_stop())
                .build();

        intake2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(61, 16.5),
                                new Pose(65, 63),
                                new Pose(13, 55)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(105.5), Math.toRadians(180))
                .addParametricCallback(0.3, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.3, () -> spindexer.spin_forward_2())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .addParametricCallback(0.95, () -> spindexer.spin_stop())
                .build();

        shoot2 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(13, 55),
                                new Pose(51,57),
                                new Pose(60, 82)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(122))
                .addParametricCallback(0.1, () -> spindexer.spin_forward_2())
                .addParametricCallback(0.3, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.5,() -> spindexer.spin_stop())
                .addParametricCallback(0.5,() -> intakeMotor.intake_stop())
                .build();

        intake3 = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(60, 82),
                                new Pose(53,67),
                                new Pose(17.5, 88)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(122), Math.toRadians(180))
                .addParametricCallback(0.3, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.3, () -> spindexer.spin_forward_2())
                .addParametricCallback(1, () -> intakeMotor.intake_stop())
                .addParametricCallback(0.9, () -> spindexer.spin_stop())
                .build();

        shoot3 = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(17.5, 88), new Pose(60, 82))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(128))
                .addParametricCallback(0.1, () -> spindexer.spin_forward_2())
                .addParametricCallback(0.3, () -> intakeMotor.intake_intake())
                .addParametricCallback(0.5,() -> spindexer.spin_stop())
                .addParametricCallback(0.5,() -> intakeMotor.intake_stop())
                .build();

        waitForTeleOp = follower.pathBuilder()
                .addPath(
                        new BezierLine(new Pose(60, 82), new Pose(50, 67))
                )
                .setLinearHeadingInterpolation(Math.toRadians(128), Math.toRadians(90))
                .build();

        PanelsDrawing.init();
        waitForStart();

        if (isStopRequested()) {
            return;
        }

        while (opModeIsActive() && current_state != AutoStates.end) {
            switch (current_state) {
                case apriltag:
                    if(!path_started) {
                        follower.followPath(apriltag);
                        path_started = true;
                    }
                    if(!follower.isBusy()){
                        path_started = false;
                        current_state = AutoStates.preloads;
                    }
                    break;

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

                        if (timer.seconds() <= 0.75) {
                            outtakeMotor.outtake_far();
                        } else if (timer.seconds() > 4.75) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake1;
                        } else if (timer.seconds() >= 4.75) {
                            spindexer.spin_stop();
                        } else if (timer.seconds() > 4) {
                            spindexer.spin_forward_2();
                        } else if (timer.seconds() > 3.25) {
                            spindexer.spin_stop();
                        } else if (timer.seconds() > 2.75) {
                            spindexer.spin_forward_2();
                        } else if (timer.seconds() > 1.5) {
                            spindexer.spin_stop();
                        } else if (timer.seconds() > 0.75) {
                            outtakeMotor.outtake_far();
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
//                        double y = gamepad1.left_stick_y;
//                        double x = -gamepad1.left_stick_x * 1.1;
//                        double rx = -gamepad1.right_stick_x;
//                        double final_rx = rx;
//
//                        LLResult ll_result = limelight.getLatestResult();
//                        telemetry.addLine(String.valueOf((ll_result != null)));
//                        if ( ll_result.isValid() && ll_result.getTx()>Math.abs(1)){
//                            double turn_error = ll_result.getTx();
//                            double turn_power = turn_error * kp_turn;
//                            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
//                            final_rx = turn_power;
//
//                            telemetry.addData("Current TX Error (Deg)", turn_error);
//                            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(final_rx), 1);
//                            double frontLeftPower = (y + x + final_rx) / denominator;
//                            double backLeftPower = (y - x + final_rx) / denominator;
//                            double frontRightPower = (y - x - final_rx) / denominator;
//                            double backRightPower = (y + x - final_rx) / denominator;
//
//                            frontLeftMotor.setPower(frontLeftPower);
//                            backLeftMotor.setPower(backLeftPower);
//                            frontRightMotor.setPower(frontRightPower);
//                            backRightMotor.setPower(backRightPower);
//                        }
//                        else {
//                            telemetry.addLine("No AprilTag Detected");
//                            frontLeftMotor.setPower(0);
//                            backLeftMotor.setPower(0);
//                            frontRightMotor.setPower(0);
//                            backRightMotor.setPower(0);
//                        }
//                        frontLeftMotor.setPower(0);
//                        backLeftMotor.setPower(0);
//                        frontRightMotor.setPower(0);
//                        backRightMotor.setPower(0);

                        if (timer.seconds() <= 1) {
                            outtakeMotor.outtake_far();
                            intakeMotor.intake_intake();
                        } else if (timer.seconds() > 4.75) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake2;
                        } else if (timer.seconds() >= 4.75) {
                            spindexer.spin_forward_2();
                        } else if (timer.seconds() >= 4.65) {
                            spindexer.spin_stop();
                        } else if (timer.seconds() > 4.) {
                            spindexer.spin_forward_2();
                        } else if (timer.seconds() > 3.1) {
                            spindexer.spin_stop();
                        } else if (timer.seconds() > 2.65) {
                            spindexer.spin_forward_2();
                        } else if (timer.seconds() > 1.4) {
                            spindexer.spin_stop();
                        } else if (timer.seconds() > 1) {
                            outtakeMotor.outtake_far();
                            spindexer.spin_forward_2();
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
//                        double y = gamepad1.left_stick_y;
//                        double x = -gamepad1.left_stick_x * 1.1;
//                        double rx = -gamepad1.right_stick_x;
//                        double final_rx = rx;
//
//                        LLResult ll_result = limelight.getLatestResult();
//                        telemetry.addLine(String.valueOf((ll_result != null)));
//                        if ( ll_result.isValid() && ll_result.getTx()>Math.abs(1)){
//                            double turn_error = ll_result.getTx();
//                            double turn_power = turn_error * kp_turn;
//                            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
//                            final_rx = turn_power;
//
//                            telemetry.addData("Current TX Error (Deg)", turn_error);
//                            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(final_rx), 1);
//                            double frontLeftPower = (y + x + final_rx) / denominator;
//                            double backLeftPower = (y - x + final_rx) / denominator;
//                            double frontRightPower = (y - x - final_rx) / denominator;
//                            double backRightPower = (y + x - final_rx) / denominator;
//
//                            frontLeftMotor.setPower(frontLeftPower);
//                            backLeftMotor.setPower(backLeftPower);
//                            frontRightMotor.setPower(frontRightPower);
//                            backRightMotor.setPower(backRightPower);
//                        }
//                        else {
//                            telemetry.addLine("No AprilTag Detected");
//                            frontLeftMotor.setPower(0);
//                            backLeftMotor.setPower(0);
//                            frontRightMotor.setPower(0);
//                            backRightMotor.setPower(0);
//                        }
//                        frontLeftMotor.setPower(0);
//                        backLeftMotor.setPower(0);
//                        frontRightMotor.setPower(0);
//                        backRightMotor.setPower(0);

                        if (timer.seconds() <= 1.5) {
                            outtakeMotor.outtake_close();
                            intakeMotor.intake_intake();
                        }
                        else if (timer.seconds() > 4) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.intake3;
                        }
                        else if(timer.seconds() > 2.5){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 2){
                            spindexer.spin_stop();
                        }
                        else if (timer.seconds() > 1.25) {
                            outtakeMotor.outtake_close();
                            spindexer.spin_forward_2();
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
//                        double y = gamepad1.left_stick_y;
//                        double x = -gamepad1.left_stick_x * 1.1;
//                        double rx = -gamepad1.right_stick_x;
//                        double final_rx = rx;
//
//                        LLResult ll_result = limelight.getLatestResult();
//                        telemetry.addLine(String.valueOf((ll_result != null)));
//                        if ( ll_result.isValid() && ll_result.getTx()>Math.abs(1)){
//                            double turn_error = ll_result.getTx();
//                            double turn_power = turn_error * kp_turn;
//                            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
//                            final_rx = turn_power;
//
//                            telemetry.addData("Current TX Error (Deg)", turn_error);
//                            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(final_rx), 1);
//                            double frontLeftPower = (y + x + final_rx) / denominator;
//                            double backLeftPower = (y - x + final_rx) / denominator;
//                            double frontRightPower = (y - x - final_rx) / denominator;
//                            double backRightPower = (y + x - final_rx) / denominator;
//
//                            frontLeftMotor.setPower(frontLeftPower);
//                            backLeftMotor.setPower(backLeftPower);
//                            frontRightMotor.setPower(frontRightPower);
//                            backRightMotor.setPower(backRightPower);
//                        }
//                        else {
//                            telemetry.addLine("No AprilTag Detected");
//                            frontLeftMotor.setPower(0);
//                            backLeftMotor.setPower(0);
//                            frontRightMotor.setPower(0);
//                            backRightMotor.setPower(0);
//                        }
//                        frontLeftMotor.setPower(0);
//                        backLeftMotor.setPower(0);
//                        frontRightMotor.setPower(0);
//                        backRightMotor.setPower(0);

                        if (timer.seconds() <= 1.5) {
                            outtakeMotor.auto_outtake_close();
                            intakeMotor.intake_intake();
                        }
                        else if (timer.seconds() > 4) {
                            outtakeMotor.outtake_stop();
                            spindexer.spin_stop();
                            intakeMotor.intake_stop();
                            timer_has_started = false;

                            current_state = AutoStates.teleop_standby;
                        }
                        else if(timer.seconds() > 3.5){
                            spindexer.spin_forward_2();
                        }
                        else if(timer.seconds() > 3){
                            spindexer.spin_stop();
                        }
                        else if (timer.seconds() > 1.5) {
                            outtakeMotor.outtake_close();
                            spindexer.spin_forward_2();
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