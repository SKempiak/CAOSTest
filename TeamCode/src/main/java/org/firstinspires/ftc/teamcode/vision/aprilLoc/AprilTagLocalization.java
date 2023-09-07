package org.firstinspires.ftc.teamcode.vision.aprilLoc;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.vision.vizTurret.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTagLocalization {
    //current robot position.
    double x = 0;
    double y = 0;
    double h = 0;

    //account for the cameras position, subtract as necessary.
    double cameraX = 1;
    double cameraY = 0;
    double cameraH = 0;

    ArrayList<Double> allX = new ArrayList<>();
    ArrayList<Double> allY = new ArrayList<>();
    ArrayList<Double> allH = new ArrayList<>();
    //    Pose2d currentPose;
    private List<AprilTagDetection> locFrom;
    AprilTagReviewer apriltag;

    public AprilTagLocalization(HardwareMap hwMap, int[] tags) {
        apriltag = new AprilTagReviewer(hwMap, tags);
        locFrom = apriltag.getCameraDetections();
    }

    public void localize() {
        locFrom = apriltag.getCameraDetections();
        for (AprilTagDetection detection : locFrom) {
            if (AprilTagReviewer.TagFamily.contains(detection.id)) {
                //NOTE: ftcPose = camera centric.
                Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                //position like odometry
                //removed the z axis to streamline performance. Also with the assumption its unnecessary.
                allX.add(detection.ftcPose.x);
                allY.add(detection.ftcPose.y);
                //rotation? pitch, roll, yaw
                //y is assumed as the yaw(heading)
                allH.add(detection.ftcPose.yaw);
            }

        }
        calculateAverage();
        setCorrectedPos();
    }

    private void calculateAverage() {
        if (allX.size() != 0 && allY.size() != 0 && allH.size() != 0) {
            x = allX.stream()
                    .mapToDouble(d -> d)
                    .average()
                    .orElse(0.0);
            y = allY.stream()
                    .mapToDouble(d -> d)
                    .average()
                    .orElse(0.0);
            h = allH.stream()
                    .mapToDouble(d -> d)
                    .average()
                    .orElse(0.0);
        }
    }
    private void accountCamLocation() {
        x -= cameraX;
        y -= cameraY;
        h -= cameraH;
    }

    public Pose2d getPose() {
        return new Pose2d(x, y, new Rotation2d(h));
    }


    private void setCorrectedPos() {
        //correct for camera location
        x += Constants.CAMERA_OFFSETS[0];
        y += Constants.CAMERA_OFFSETS[1];
        h += Constants.CAMERA_ROTATION_OFFSETS[2];
        //TODO: When rotator class has been made, do the same for the turret.
        //h += turret.getHeading();

    }

//causing a npe. Assumed that we can only use telemetry methods in opmode?
//    public void telemetryUpdate() {
//        localize();
//        telemetry.addData("Camera defined x", x);
//        telemetry.addData("Camera defined y", y);
//        telemetry.addData("Camera defined h", h);
//        telemetry.update();
//    }

}