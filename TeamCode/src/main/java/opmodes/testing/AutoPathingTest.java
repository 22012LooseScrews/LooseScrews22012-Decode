/*
package opmodes.testing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// PedroPathing imports
//import org.firstinspires.ftc.teamcode.pedropathing.follower.Follower;
//import org.firstinspires.ftc.teamcode.pedropathing.localization.Pose;
//import org.firstinspires.ftc.teamcode.pedropathing.pathgeneration.BezierCurve;
//import org.firstinspires.ftc.teamcode.pedropathing.pathgeneration.PathBuilder;

@Disabled
@Autonomous
public class AutoPathingTest extends OpMode {

    // Drivetrain motors
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;

    // Pedro follower
    private Follower follower;

    // State flags
    private boolean pathStarted = false;
    private boolean pathFinished = false;

    @Override
    public void init() {
        // Initialize hardware
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRightMotor");

        // Reverse the right side
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize the PedroPathing follower
        follower = new Follower(hardwareMap);

        // Build path
        PathBuilder pathBuilder = new PathBuilder();
        pathBuilder
                .addPath(
                        new BezierCurve(
                                new Pose(48.000, 135.000),
                                new Pose(43.065, 28.037),
                                new Pose(105.196, 33.196)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(360));

        follower.followPath(pathBuilder.build());

        telemetry.addLine("✅ Initialized — ready to start");
        telemetry.update();
    }

    @Override
    public void start() {
        // Runs once when "play" is pressed
        pathStarted = true;
    }

    @Override
    public void loop() {
        if (pathStarted && !pathFinished) {
            follower.update();

            // Get desired drive power from the follower
            double drivePowerX = follower.getDriveSignal().x;
            double drivePowerY = follower.getDriveSignal().y;
            double turnPower = follower.getDriveSignal().heading;

            // Apply powers to mecanum drivetrain
            double fl = drivePowerY + drivePowerX + turnPower;
            double bl = drivePowerY - drivePowerX + turnPower;
            double fr = drivePowerY - drivePowerX - turnPower;
            double br = drivePowerY + drivePowerX - turnPower;

            frontLeftMotor.setPower(fl);
            backLeftMotor.setPower(bl);
            frontRightMotor.setPower(fr);
            backRightMotor.setPower(br);

            // Telemetry
            telemetry.addData("Pose", follower.getPose());
            telemetry.addData("Path Progress", follower.getCurrentPathProgress());
            telemetry.update();

            // If done following the path
            if (!follower.isBusy()) {
                stopMotors();
                pathFinished = true;
            }
        }
        else if (pathFinished) {
            telemetry.addLine("✅ Path complete!");
            telemetry.update();
        }
    }
}
*/
