    package opmodes.testing;
    
    import com.qualcomm.hardware.limelightvision.LLResult;
    import com.qualcomm.hardware.limelightvision.Limelight3A;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    
    import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
    
    import java.lang.reflect.Method;
    import java.util.List;
    
    @TeleOp(name = "Limelight TeleOp Auto-Align (Robust)", group = "Vision")
    public class AprilTagLimelightTestTeleop extends OpMode {
    
        private Limelight3A limelight;
        private DcMotor frontRight, backRight, frontLeft, backLeft;
    
        // Tuning constants
        private static final double ALIGN_KP = 0.02;    // proportional gain
        private static final double MAX_TURN = 0.35;    // cap turn power
        private static final double TX_TOLERANCE = 1.0; // degrees tolerance
    
        @Override
        public void init() {
            // hardware mapping
            frontRight = hardwareMap.get(DcMotor.class, "frontRightMotor");
            backRight = hardwareMap.get(DcMotor.class, "backRightMotor");
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");
            backLeft = hardwareMap.get(DcMotor.class, "backLeftMotor");
    
            // reverse right side (change if your config differs)
            frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
            backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    
            // Limelight
            limelight = hardwareMap.get(Limelight3A.class, "Limelight");
            limelight.pipelineSwitch(0); // ensure AprilTag pipeline is selected (pipeline 0)
            limelight.start();
    
            telemetry.addLine("Limelight TeleOp (robust) initialized.");
            telemetry.update();
        }
    
        @Override
        public void loop() {
            // ------------- Drive (Mecanum) -------------
            double y = -gamepad1.left_stick_y;            // forward
            double x = gamepad1.left_stick_x * 1.1;      // strafing (adjust)
            double rx = gamepad1.right_stick_x;         // rotation
    
            double denom = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            double fl = (y + x + rx) / denom;
            double bl = (y - x + rx) / denom;
            double fr = (y - x - rx) / denom;
            double br = (y + x - rx) / denom;
    
            // Default manual drive - we'll overwrite if auto-aligning
            frontLeft.setPower(fl);
            backLeft.setPower(bl);
            frontRight.setPower(fr);
            backRight.setPower(br);
    
            // ------------- Limelight Read -------------
            LLResult result = limelight.getLatestResult();
            boolean hasTarget = result != null && result.isValid();
    
            String tagIdStr = "N/A";
            double tx = 0.0;
            double ty = 0.0;
            double ta = 0.0;
            Pose3D pose = null;
    
            if (hasTarget) {
                // stable methods expected to exist on all reasonable SDKs:
                try { tx = result.getTx(); } catch (Exception ignore) { tx = 0.0; }
                try { ty = result.getTy(); } catch (Exception ignore) { ty = 0.0; }
                try { ta = result.getTa(); } catch (Exception ignore) { ta = 0.0; }
                try { pose = result.getBotpose_MT2(); } catch (Exception ignore) { pose = null; }
    
                // Try multiple ways to obtain a tag ID using reflection so this compiles on many SDK versions.
                // 1) Try result.getTargets().get(0).getFiducialId()
                try {
                    Method getTargets = result.getClass().getMethod("getTargets");
                    Object targetsObj = getTargets.invoke(result);
                    if (targetsObj instanceof List) {
                        List targets = (List) targetsObj;
                        if (!targets.isEmpty()) {
                            Object firstTarget = targets.get(0);
                            Method getFiducial = firstTarget.getClass().getMethod("getFiducialId");
                            Object id = getFiducial.invoke(firstTarget);
                            tagIdStr = String.valueOf(id);
                        }
                    }
                } catch (Exception e) {
                    // 2) Try result.getFiducialId() directly
                    try {
                        Method getFid = result.getClass().getMethod("getFiducialId");
                        Object id2 = getFid.invoke(result);
                        tagIdStr = String.valueOf(id2);
                    } catch (Exception ex) {
                        // 3) Try result.getTargetID() or getTagID()
                        try {
                            Method getTargetID = result.getClass().getMethod("getTargetID");
                            Object id3 = getTargetID.invoke(result);
                            tagIdStr = String.valueOf(id3);
                        } catch (Exception ignore) {
                            // leave as N/A if none of the above exist
                        }
                    }
                }
            }
    
            // ------------- Auto-Align (X button) -------------
            if (gamepad1.x && hasTarget) {
                // Use tx for alignment
                double error = tx;
                double turn = ALIGN_KP * error;
    
                // clamp and remove very small outputs
                if (Math.abs(error) <= TX_TOLERANCE) {
                    turn = 0.0;
                } else {
                    if (turn > MAX_TURN) turn = MAX_TURN;
                    if (turn < -MAX_TURN) turn = -MAX_TURN;
                }
    
                // Apply rotation only (no translation) while aligning
                frontLeft.setPower(turn);
                backLeft.setPower(turn);
                frontRight.setPower(-turn);
                backRight.setPower(-turn);
    
                telemetry.addLine("Auto-aligning to AprilTag");
                telemetry.addData("tx (deg)", "%.2f", tx);
                telemetry.addData("turnPower", "%.3f", turn);
                telemetry.addData("TagID", tagIdStr);
            }
    
            // ------------- Telemetry -------------
            if (hasTarget) {
                telemetry.addData("Visible", "YES");
                telemetry.addData("TagID", tagIdStr);
                telemetry.addData("tx", "%.2f", tx);
                telemetry.addData("ty", "%.2f", ty);
                telemetry.addData("ta", "%.3f", ta);
                if (pose != null) {
                    telemetry.addData("BotPose X", "%.2f", pose.getPosition().x);
                    telemetry.addData("BotPose Y", "%.2f", pose.getPosition().y);
                    telemetry.addData("BotPose Z", "%.2f", pose.getPosition().z);
                }
            } else {
                telemetry.addData("Visible", "NO");
            }
            telemetry.addData("Mode", gamepad1.x ? "Auto-Align" : "Manual");
            telemetry.update();
        }
    }
