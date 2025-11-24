package opmodes.testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;

@TeleOp
public class AnishPleaseDontDisownMe extends OpMode {

    // ---- Drive Motors ----
    private DcMotor frontLeft, frontRight, backLeft, backRight;

    // ---- Limelight ----
    private Limelight3A limelight;

    // ---- Auto-Align Settings ----
    private final double kP = 0.02;      // proportional turn gain
    private final double deadband = 1.0; // deg where we consider aligned
    private final double maxTurn = 0.35; // limit turn power

    @Override
    public void init() {

        // Motor mapping
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        frontRight = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backLeft = hardwareMap.get(DcMotor.class, "backLeftMotor");
        backRight = hardwareMap.get(DcMotor.class, "backRightMotor");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        // Limelight init
        limelight = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight.pipelineSwitch(0);
        limelight.start();

    }

    @Override
    public void loop() {


        double y = -gamepad1.left_stick_y;   // forward/back
        double x = gamepad1.left_stick_x;    // strafe left/right
        double turn = gamepad1.right_stick_x; // normal turning


        boolean autoAlignActive = gamepad1.dpad_down;

        if (autoAlignActive) {

            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {

                double tx = result.getTx(); // horizontal offset

                // apply deadband
                if (Math.abs(tx) > deadband) {
                    turn = kP * tx;
                } else {
                    turn = 0;
                }

                // clamp turn
                turn = Math.max(-maxTurn, Math.min(maxTurn, turn));

                telemetry.addData("AUTO ALIGN", "ACTIVE (DPAD DOWN)");
                telemetry.addData("tx", tx);
                telemetry.addData("turnCmd", turn);

            } else {
                telemetry.addData("AUTO ALIGN", "NO TARGET");
            }
        } else {
            telemetry.addData("AUTO ALIGN", "INACTIVE");
        }

        // ---- Mecanum Drive Calculation ----
        double fl = y + x + turn;
        double fr = y - x - turn;
        double bl = y - x + turn;
        double br = y + x - turn;

        // Normalize output
        double max = Math.max(1.0, Math.max(Math.abs(fl),
                Math.max(Math.abs(fr),
                        Math.max(Math.abs(bl), Math.abs(br)))));

        frontLeft.setPower(fl / max);
        frontRight.setPower(fr / max);
        backLeft.setPower(bl / max);
        backRight.setPower(br / max);

        telemetry.update();
    }
}
