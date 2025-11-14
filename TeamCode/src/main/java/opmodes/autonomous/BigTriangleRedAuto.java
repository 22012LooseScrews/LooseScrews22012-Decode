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
public class BigTriangleRedAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        PathBuilder builder = new PathBuilder(follower);
        follower.setStartingPose(new Pose(121.5, 126, Math.toRadians(216.5)));

        PathChain big_triangle_red_path = builder
                .addPath(
                        // Path 1
                        new BezierCurve(
                                new Pose(121.500, 126.000),
                                new Pose(63.158, 86.737),
                                new Pose(125.259, 85.641)
                        )
                )
                .setTangentHeadingInterpolation()
                .addPath(
                        // Path 2
                        new BezierLine(new Pose(125.259, 85.641), new Pose(72.356, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(46))
                .addPath(
                        // Path 3
                        new BezierCurve(
                                new Pose(72.356, 73.068),
                                new Pose(60.731, 62.392),
                                new Pose(120.514, 63.104)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(46), Math.toRadians(0))
                .addPath(
                        // Path 4
                        new BezierLine(new Pose(120.514, 63.104), new Pose(72.356, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(46))
                .addPath(
                        // Path 5
                        new BezierCurve(
                                new Pose(72.356, 73.068),
                                new Pose(71.170, 35.822),
                                new Pose(123.579, 34.947)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(46), Math.toRadians(0))
                .addPath(
                        // Path 6
                        new BezierLine(new Pose(123.579, 34.947), new Pose(72.119, 22.774))
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(61.5))
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
