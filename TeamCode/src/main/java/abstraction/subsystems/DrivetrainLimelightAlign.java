package abstraction.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DrivetrainLimelightAlign {
    private Limelight3A limelight;
    private DcMotor frontRightMotor, backRightMotor, frontLeftMotor, backLeftMotor;
    private static final double kp_turn = 0.03;
    private static final double max_speed = 1.0;
    public DrivetrainLimelightAlign(OpMode opMode){
        limelight = opMode.hardwareMap.get(Limelight3A.class, "limelight");
        frontRightMotor = opMode.hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = opMode.hardwareMap.get(DcMotor.class, "backRightMotor");
        frontLeftMotor = opMode.hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = opMode.hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        limelight.pipelineSwitch(2);
    }
    public void limelight_start(){
        limelight.start();
    }
    public void auto_align_manual(OpMode opMode){
        double y = opMode.gamepad1.left_stick_y;
        double x = -opMode.gamepad1.left_stick_x * 1.1;
        double rx = -opMode.gamepad1.right_stick_x;
        double final_rx_manual = rx;

        LLResult ll_result = limelight.getLatestResult();
        if(opMode.gamepad1.dpad_up && ll_result != null && ll_result.isValid()){
            double turn_error = ll_result.getTx();
            double turn_power = turn_error * -kp_turn;
            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
            final_rx_manual = turn_power;

            opMode.telemetry.addData("Current TX Error (Deg)", turn_error);
            opMode.telemetry.update();
        }
        else {
            opMode.telemetry.addLine("No AprilTag Detected");
        }

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(final_rx_manual), 1);
        double frontLeftPower = (y + x + final_rx_manual) / denominator;
        double backLeftPower = (y - x + final_rx_manual) / denominator;
        double frontRightPower = (y - x - final_rx_manual) / denominator;
        double backRightPower = (y + x - final_rx_manual) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }
    public void auto_align(OpMode opMode){
        double final_rx_auto = 0;
        LLResult ll_result = limelight.getLatestResult();
        if(ll_result != null && ll_result.isValid()){
            double turn_error = ll_result.getTx();
            double turn_power = turn_error * -kp_turn;
            turn_power = Math.min(Math.abs(turn_power), max_speed) * Math.signum(turn_power);
            final_rx_auto = turn_power;

            opMode.telemetry.addData("Current TX Error (Deg)", turn_error);
            opMode.telemetry.update();
        }
        else {
            opMode.telemetry.addLine("No AprilTag Detected");
        }

        double denominator = Math.max(Math.abs(final_rx_auto), 1);
        double frontLeftPower = (final_rx_auto) / denominator;
        double backLeftPower = (final_rx_auto) / denominator;
        double frontRightPower = (-final_rx_auto) / denominator;
        double backRightPower = (-final_rx_auto) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }
}
