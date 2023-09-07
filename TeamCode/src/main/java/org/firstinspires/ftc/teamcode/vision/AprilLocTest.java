package org.firstinspires.ftc.teamcode.vision;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision.aprilLoc.AprilTagLocalization;

@TeleOp(name="tags", group="detect")
public class AprilLocTest extends OpMode {
    Pose2d currentPose = new Pose2d(0,0, Rotation2d.fromDegrees(0));
    AprilTagLocalization aprilTagLocalization;
    @Override
    public void init() {
        aprilTagLocalization = new AprilTagLocalization(hardwareMap, new int[]{583, 584, 585, 586});
    }

    @Override
    public void loop() {
        aprilTagLocalization.localize();
        currentPose = aprilTagLocalization.getPose();
        telemetry.addData("Camera defined x", currentPose.getX());
        telemetry.addData("Camera defined y", currentPose.getY());
        telemetry.addData("Camera defined h", currentPose.getHeading());
        telemetry.update();
    }
}
