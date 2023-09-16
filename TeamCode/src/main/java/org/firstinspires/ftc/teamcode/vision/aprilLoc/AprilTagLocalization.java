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
import org.firstinspires.ftc.teamcode.Utilities.Side;
import org.firstinspires.ftc.teamcode.vision.vizTurret.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.List;
//TODO: write offset methods for the different tags.
//TODO: Write a class/subclass/method that searches for the tag we want to correlate spikes to the pixel on the board.
public class AprilTagLocalization {
    //current robot position.
    double x = 0;
    double y = 0;
    double h = 0;


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
        allX.clear();
        allY.clear();
        allH.clear();
        locFrom = apriltag.getCameraDetections();
        for (AprilTagDetection detection : locFrom) {
            if (AprilTagReviewer.TagFamily.contains(detection.id)) {
                //NOTE: ftcPose = camera centric.
                Orientation rot = Orientation.getOrientation(detection.rawPose.R, AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                //position like odometry
                //removed the z axis to streamline performance. Also with the assumption its unnecessary.

//                x=detection.ftcPose.x;
//                y=detection.ftcPose.y;
//                h=detection.ftcPose.yaw;

                allX.add(detection.ftcPose.x);
                allY.add(detection.ftcPose.y);
                //rotation? pitch, roll, yaw
                //y is assumed as the yaw(heading)
                allH.add(detection.ftcPose.yaw);
                accountOffsets(detection.id);
            }

        }

        calculateAverage();
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
    private void accountOffsets(int tagNum) {
        //checks if side is red. If not, it is blue.
        if (Side.getSide().equals("red")) {
            switch (tagNum) {
                //blue backdrop
                case 1:
                    x += FieldLocConstants.RED_BLUE_BACKDROP_LEFT[0];
                    y += FieldLocConstants.RED_BLUE_BACKDROP_LEFT[1];
                    h += FieldLocConstants.RED_BLUE_BACKDROP_LEFT[2];
                    break;
                case 2:
                    x += FieldLocConstants.RED_BLUE_BACKDROP_MIDDLE[0];
                    y += FieldLocConstants.RED_BLUE_BACKDROP_MIDDLE[1];
                    h += FieldLocConstants.RED_BLUE_BACKDROP_MIDDLE[2];
                    break;
                case 3:
                    x += FieldLocConstants.RED_BLUE_BACKDROP_RIGHT[0];
                    y += FieldLocConstants.RED_BLUE_BACKDROP_RIGHT[1];
                    h += FieldLocConstants.RED_BLUE_BACKDROP_RIGHT[2];
                    break;

                //red backdrop
                case 4:
                    x += FieldLocConstants.RED_RED_BACKDROP_LEFT[0];
                    y += FieldLocConstants.RED_RED_BACKDROP_LEFT[1];
                    h += FieldLocConstants.RED_RED_BACKDROP_LEFT[2];
                    break;
                case 5:
                    x += FieldLocConstants.RED_RED_BACKDROP_MIDDLE[0];
                    y += FieldLocConstants.RED_RED_BACKDROP_MIDDLE[1];
                    h += FieldLocConstants.RED_RED_BACKDROP_MIDDLE[2];
                    break;
                case 6:
                    x += FieldLocConstants.RED_RED_BACKDROP_RIGHT[0];
                    y += FieldLocConstants.RED_RED_BACKDROP_RIGHT[1];
                    h += FieldLocConstants.RED_RED_BACKDROP_RIGHT[2];
                    break;

                //blue Atag
                case 9:
                    x += FieldLocConstants.RED_BLUE_APRILTAG_SMALL[0];
                    y += FieldLocConstants.RED_BLUE_APRILTAG_SMALL[1];
                    h += FieldLocConstants.RED_BLUE_APRILTAG_SMALL[2];
                    break;
                case 10:
                    x += FieldLocConstants.RED_BLUE_APRILTAG_LARGE[0];
                    y += FieldLocConstants.RED_BLUE_APRILTAG_LARGE[1];
                    h += FieldLocConstants.RED_BLUE_APRILTAG_LARGE[2];
                    break;

                //red Atag
                case 7:
                    x += FieldLocConstants.RED_RED_APRILTAG_SMALL[0];
                    y += FieldLocConstants.RED_RED_APRILTAG_SMALL[1];
                    h += FieldLocConstants.RED_RED_APRILTAG_SMALL[2];
                    break;
                case 8:
                    x += FieldLocConstants.RED_RED_APRILTAG_LARGE[0];
                    y += FieldLocConstants.RED_RED_APRILTAG_LARGE[1];
                    h += FieldLocConstants.RED_RED_APRILTAG_LARGE[2];
                    break;
            }
        } else {
            switch (tagNum) {
                //blue backdrop
                case 1:
                    x += FieldLocConstants.BLUE_BLUE_BACKDROP_LEFT[0];
                    y += FieldLocConstants.BLUE_BLUE_BACKDROP_LEFT[1];
                    h += FieldLocConstants.BLUE_BLUE_BACKDROP_LEFT[2];
                    break;
                case 2:
                    x += FieldLocConstants.BLUE_BLUE_BACKDROP_MIDDLE[0];
                    y += FieldLocConstants.BLUE_BLUE_BACKDROP_MIDDLE[1];
                    h += FieldLocConstants.BLUE_BLUE_BACKDROP_MIDDLE[2];
                    break;
                case 3:
                    x += FieldLocConstants.BLUE_BLUE_BACKDROP_RIGHT[0];
                    y += FieldLocConstants.BLUE_BLUE_BACKDROP_RIGHT[1];
                    h += FieldLocConstants.BLUE_BLUE_BACKDROP_RIGHT[2];
                    break;

                //red backdrop
                case 4:
                    x += FieldLocConstants.BLUE_RED_BACKDROP_LEFT[0];
                    y += FieldLocConstants.BLUE_RED_BACKDROP_LEFT[1];
                    h += FieldLocConstants.BLUE_RED_BACKDROP_LEFT[2];
                    break;
                case 5:
                    x += FieldLocConstants.BLUE_RED_BACKDROP_MIDDLE[0];
                    y += FieldLocConstants.BLUE_RED_BACKDROP_MIDDLE[1];
                    h += FieldLocConstants.BLUE_RED_BACKDROP_MIDDLE[2];
                    break;
                case 6:
                    x += FieldLocConstants.BLUE_RED_BACKDROP_RIGHT[0];
                    y += FieldLocConstants.BLUE_RED_BACKDROP_RIGHT[1];
                    h += FieldLocConstants.BLUE_RED_BACKDROP_RIGHT[2];
                    break;

                //blue Atag
                case 9:
                    x += FieldLocConstants.BLUE_BLUE_APRILTAG_SMALL[0];
                    y += FieldLocConstants.BLUE_BLUE_APRILTAG_SMALL[1];
                    h += FieldLocConstants.BLUE_BLUE_APRILTAG_SMALL[2];
                    break;
                case 10:
                    x += FieldLocConstants.BLUE_BLUE_APRILTAG_LARGE[0];
                    y += FieldLocConstants.BLUE_BLUE_APRILTAG_LARGE[1];
                    h += FieldLocConstants.BLUE_BLUE_APRILTAG_LARGE[2];
                    break;

                //red Atag
                case 7:
                    x += FieldLocConstants.BLUE_RED_APRILTAG_SMALL[0];
                    y += FieldLocConstants.BLUE_RED_APRILTAG_SMALL[1];
                    h += FieldLocConstants.BLUE_RED_APRILTAG_SMALL[2];
                    break;
                case 8:
                    x += FieldLocConstants.BLUE_RED_APRILTAG_LARGE[0];
                    y += FieldLocConstants.BLUE_RED_APRILTAG_LARGE[1];
                    h += FieldLocConstants.BLUE_RED_APRILTAG_LARGE[2];
                    break;
            }
        }
    }

    public List<AprilTagDetection> getDetections() {
        return locFrom;
    }

    public Pose2d getPose () {
        return new Pose2d(x, y, new Rotation2d(h));
    }
    public double getX () {
        return x;
    }

    public double getY () {
        return y;
    }

    public double getH () {
        return h;
    }

    }