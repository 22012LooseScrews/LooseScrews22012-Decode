package opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import abstraction.subsystems.SpinServo;
import abstraction.subsystems.OuttakeMotor;
@Autonomous
public class BigTriangleRedAutoSafe extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        SpinServo spinServo = new SpinServo(this);
        OuttakeMotor outtakeMotor = new OuttakeMotor(this);
        PathBuilder builder = new PathBuilder(follower);
        follower.setStartingPose(new Pose(120.3, 125.3, Math.toRadians(35)));

        PathChain big_triangle_red_path = builder
                .addPath(

                        new BezierLine(new Pose(120.3, 125.3), new Pose(84.217, 108.652))

                )
                .setLinearHeadingInterpolation(Math.toRadians(35), Math.toRadians(45))
 /*               .addParametricCallback(0.5, () -> spinServo.spin_forward())
                .addParametricCallback(0.99, () -> spinServo.spin_stop())
                .addParametricCallback(0.5, () -> outtakeMotor.outtake_far())
                .addParametricCallback(0.99, () -> outtakeMotor.outtake_stop())*/
                .build();

        waitForStart();
        follower.followPath(big_triangle_red_path, true);
        if(isStopRequested()){
            return;
        }
        while(opModeIsActive() && follower.isBusy()){
            follower.update();
        }
    }
}
