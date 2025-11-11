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
public class SmallTriangleRedAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        PathBuilder builder = new PathBuilder(follower);
        follower.setStartingPose(new Pose(88.0, 8.0, Math.toRadians(0)));

        PathChain small_triangle_red_path = builder
                .addPath(
                        new BezierCurve(
                                new Pose(88.000, 8.000),
                                new Pose(87.539, 43.176),
                                new Pose(125.259, 35.348)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(new Pose(125.259, 35.348), new Pose(71.881, 22.774))
                )
                .setLinearHeadingInterpolation(Math.toRadians(348), Math.toRadians(61.5))
                .addPath(
                        new BezierCurve(
                                new Pose(71.881, 22.774),
                                new Pose(91.809, 63.104),
                                new Pose(123.598, 60.731)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(new Pose(123.598, 60.731), new Pose(72.356, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(355), Math.toRadians(46))
                .addPath(
                        new BezierCurve(
                                new Pose(72.356, 73.068),
                                new Pose(86.115, 89.437),
                                new Pose(119.328, 84.455)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        new BezierLine(new Pose(119.328, 84.455), new Pose(72.356, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(351), Math.toRadians(46))
                .build();

        waitForStart();
        follower.followPath(small_triangle_red_path);
        if(isStopRequested()){
            return;
        }
        while(opModeIsActive() && follower.isBusy()){
            follower.update();
        }
    }
}
