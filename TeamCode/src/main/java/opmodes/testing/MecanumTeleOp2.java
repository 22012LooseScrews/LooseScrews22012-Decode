//package opmodes.teleop;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import abstraction.subsystems.DrivetrainLimelightAlign;
//import abstraction.subsystems.IntakeMotor;
//import abstraction.subsystems.OuttakeMotor;
//import abstraction.subsystems.SpinServo;
//import abstraction.subsystems.VectorServo;
//@TeleOp
//public class MecanumTeleOp2 extends OpMode {
//    DrivetrainLimelightAlign limelight;
//    OuttakeMotor outtake_motor;
//    VectorServo vector_servo;
//    SpinServo spindexer;
//    IntakeMotor intake_motor;
//
//    @Override
//    public void init() {
//        spindexer = new SpinServo(this);
//        vector_servo = new VectorServo(this);
//        outtake_motor = new OuttakeMotor(this);
//        intake_motor = new IntakeMotor(this);
//        limelight = new DrivetrainLimelightAlign(this);
//    }
//
//    public void start() {
//        limelight.limelight_start();
//    }
//
//    @Override
//    public void loop() {
//        limelight.auto_align_manual(this);
//
//        if (gamepad1.left_bumper) {
//            intake_motor.intake_intake();
//            vector_servo.vector_intake();
//        } else if (gamepad1.right_bumper) {
//            intake_motor.intake_outtake();
//            vector_servo.vector_outtake();
//        } else {
//            intake_motor.intake_stop();
//            vector_servo.vector_stop();
//        }
//
//        if(gamepad1.circleWasPressed() || gamepad1.bWasPressed()){
//            spindexer.spin_forward_2();
//        }
//        else if(gamepad1.triangleWasPressed() || gamepad1.yWasPressed()){
//            spindexer.spin_backward();
//        }
//        else if(gamepad1.circleWasReleased() || gamepad1.bWasReleased() || gamepad1.triangleWasReleased() || gamepad1.yWasReleased()){
//            spindexer.spin_stop();
//        }
//
//        if (gamepad1.left_trigger > 0.1) {
//            outtake_motor.outtake_close();
//        } else if (gamepad1.right_trigger > 0.1) {
//            outtake_motor.outtake_far();
//        } else {
//            outtake_motor.outtake_stop();
//        }
//
//        telemetry.addData("Outtake Velocity (ticks/s)", outtake_motor.getVel());
//        telemetry.addData("Battery Voltage", outtake_motor.getVol());
//        //telemetry.addData("what it actually is ",ll_result.getTx());
//        telemetry.update();
//    }
//}