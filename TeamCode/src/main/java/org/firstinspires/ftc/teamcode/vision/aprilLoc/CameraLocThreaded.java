package org.firstinspires.ftc.teamcode.vision.aprilLoc;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CameraLocThreaded extends AprilTagLocalization implements Runnable {
    AprilTagLocalization aprilTagLocalization;
    public CameraLocThreaded(HardwareMap hwMap, Telemetry telemetry, int[] tags) {
        super(hwMap, tags);
        aprilTagLocalization= new AprilTagLocalization(hwMap, tags);
    }

    @Override
    public void run() {
        aprilTagLocalization.localize();
    }

    public Pose2d getPose() {
        aprilTagLocalization.localize();
        return aprilTagLocalization.getPose();
    }
}
