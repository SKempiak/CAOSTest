package org.firstinspires.ftc.teamcode.vision;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision.aprilLoc.AprilTagLocalization;

@TeleOp(name="tags", group="detect")
public class AprilLocTest extends OpMode {
    double x = 0,y = 0,h = 0;
    AprilTagLocalization aprilTagLocalization;
    @Override
    public void init() {
        aprilTagLocalization = new AprilTagLocalization(hardwareMap, new int[]{583, 584, 585, 586});
    }

    @Override
    public void loop() {
        aprilTagLocalization.localize();
        telemetry.addData("Camera defined x", aprilTagLocalization.getX());
        telemetry.addData("Camera defined y", aprilTagLocalization.getY());
        telemetry.addData("Camera defined h", aprilTagLocalization.getH());
        telemetry.update();
    }
}
