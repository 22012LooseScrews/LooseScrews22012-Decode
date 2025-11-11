package opmodes.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import mechanisms.Constants;
@Autonomous
public class BigTriangleBlueAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        PathBuilder builder = new PathBuilder(follower, Constants.path_constraints);

        PathChain big_triangle_blue_path = builder
                .addPath(new BezierLine(
                        new Pose(22.5, 126),
                        new Pose(22.5, 99)
                ))
                .setLinearHeadingInterpolation(
                        Math.toRadians(323.5),
                        Math.toRadians(270)
                )
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
