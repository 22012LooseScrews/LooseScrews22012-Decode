package opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
@Autonomous
public class SmallTriangleBlueAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        PathBuilder builder = new PathBuilder(follower);
        follower.setStartingPose(new Pose(56.0, 9.0, Math.toRadians(90)));

        PathChain small_triangle_blue_path = builder
                .addPath(
                        // Path 1
                        new BezierCurve(
                                new Pose(56.000, 9.000),
                                new Pose(72.421, 46.526),
                                new Pose(22.737, 36.000)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .addPath(
                        // Path 2
                        new BezierLine(new Pose(22.737, 36.000), new Pose(71.881, 22.774))
                )
                .setLinearHeadingInterpolation(Math.toRadians(184), Math.toRadians(118.5))
             //   .setReversed()
                .addPath(
                        // Path 3
                        new BezierCurve(
                                new Pose(71.881, 22.774),
                                new Pose(52.191, 63.104),
                                new Pose(20.402, 60.731)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Path 4
                        new BezierLine(new Pose(20.402, 60.731), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(185), Math.toRadians(134))
                .addPath(
                        // Path 5
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(57.885, 89.437),
                                new Pose(24.672, 84.455)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Path 6
                        new BezierLine(new Pose(24.672, 84.455), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(189), Math.toRadians(134))
                .build();

        waitForStart();
        follower.followPath(small_triangle_blue_path);
        if(isStopRequested()){
            return;
        }
        while(opModeIsActive() && follower.isBusy()){
            follower.update();
        }
    }
}
