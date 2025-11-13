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
public class BigTriangleBlueAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        PathBuilder builder = new PathBuilder(follower);
        follower.setStartingPose(new Pose(22.5, 126, Math.toRadians(323.5)));

        PathChain big_triangle_blue_path = builder
                .addPath(
                        new BezierCurve(
                                new Pose(22.500, 126.000),
                                new Pose(64.764, 114.346),
                                new Pose(52.903, 90.623),
                                new Pose(23.486, 85.641)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(323.5), Math.toRadians(180))
                .addPath(
                        new BezierLine(new Pose(18.741, 85.641), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(196), Math.toRadians(134))
                .addPath(
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(83.269, 62.392),
                                new Pose(23.486, 63.104)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                .addPath(
                        new BezierLine(new Pose(23.486, 63.104), new Pose(71.644, 73.068))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))
                .addPath(
                        new BezierCurve(
                                new Pose(71.644, 73.068),
                                new Pose(72.830, 35.822),
                                new Pose(23.486, 37.245)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                .addPath(
                        new BezierLine(new Pose(20.639, 37.245), new Pose(71.881, 22.774))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(118.5))
                .build();

        waitForStart();
        follower.followPath(big_triangle_blue_path, true);
        if(isStopRequested()){
            return;
        }
        while(opModeIsActive() && follower.isBusy()){
            follower.update();
        }
    }
}
