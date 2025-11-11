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
        follower.setStartingPose(new Pose(56.0, 8.0, Math.toRadians(0)));

        PathChain small_triangle_blue_path = builder
                .addPath(
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(56.461, 43.176),
                                new Pose(18.741, 35.348)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(new Pose(18.741, 35.348), new Pose(71.881, 22.774))
                )
                .setLinearHeadingInterpolation(Math.toRadians(192), Math.toRadians(118.5))
                .addPath(
                        new BezierCurve(
                                new Pose(71.881, 22.774),
                                new Pose(52.191, 63.104),
                                new Pose(20.402, 60.731)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(new Pose(20.402, 60.731), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(185), Math.toRadians(134))
                .addPath(
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(57.885, 89.437),
                                new Pose(24.672, 84.455)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
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
