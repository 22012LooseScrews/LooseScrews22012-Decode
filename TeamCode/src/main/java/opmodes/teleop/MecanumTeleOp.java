package opmodes.teleop;
//import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Config
public class MecanumTeleOp extends OpMode {
    DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor, intakeMotor;
    DcMotorEx outtakeMotor;
    CRServo spinServo;
    double P, I, D, F;
    double max_outtake_power;
    @Override
    public void init() {
        //FtcDashboard dashboard = FtcDashboard.getInstance();
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "outtakeMotor");

        spinServo = hardwareMap.get(CRServo.class, "spinServo");

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        P = 10.0;
        I = 0.0;
        D = 0.5;
        F = 15.0;
        outtakeMotor.setVelocityPIDFCoefficients(P, I, D, F);
    }

    @Override
    public void loop() {
        //max_outtake_power = 1.0 + 0.85;
        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        double close_rpm = 5700;
        double far_rpm = 6000;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        if (gamepad1.left_bumper) {
            intakeMotor.setPower(1.0);
        } else if(gamepad1.right_bumper){
            intakeMotor.setPower(-1.0);
        } else{
            intakeMotor.setPower(0.0);
        }

        if (gamepad1.circleWasPressed() || gamepad1.bWasPressed()) {
            spinServo.setPower(1.0);
        }
        else if(gamepad1.circleWasReleased() || gamepad1.bWasReleased()){
            spinServo.setPower(0.0);
        }
        else if(gamepad1.triangleWasPressed() || gamepad1.aWasPressed()){
            spinServo.setPower(-1.0);
        }
        else if(gamepad1.triangleWasReleased() || gamepad1.aWasReleased()){
            spinServo.setPower(0.0);
        }

        if(gamepad1.left_trigger > 0.2){
            outtakeMotor.setVelocity(-close_rpm);
        }
        else if(gamepad1.right_trigger > 0.2){
            outtakeMotor.setVelocity(-far_rpm);
        }
        else{
            outtakeMotor.setVelocity(0);
        }

        telemetry.addData("Y",-gamepad1.left_stick_y);
        telemetry.addData("X",-gamepad1.left_stick_x * 1.1);
        telemetry.addData("RX",gamepad1.right_stick_x);
        telemetry.addData("Left Bumper", gamepad1.left_bumper);
        telemetry.addData("Right Bumper", gamepad1.right_bumper);
        telemetry.addData("Outtake Motor RPM", outtakeMotor.getVelocity());
    }
}