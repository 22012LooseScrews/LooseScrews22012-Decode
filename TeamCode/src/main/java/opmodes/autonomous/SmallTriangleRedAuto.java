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
public class SmallTriangleRedAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Follower follower = Constants.createFollower(hardwareMap);
        PathBuilder builder = new PathBuilder(follower, Constants.path_constraints);

        PathChain small_triangle_red_path = builder
                .addPath(new BezierLine(
                        new Pose(88.0, 8.0),
                        new Pose(88.0, 36.0)
                ))
                .setLinearHeadingInterpolation(
                        Math.toRadians(90.0),
                        Math.toRadians(90.0)
                )
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
