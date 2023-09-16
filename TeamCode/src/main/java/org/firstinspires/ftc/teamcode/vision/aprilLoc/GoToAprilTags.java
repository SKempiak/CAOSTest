package org.firstinspires.ftc.teamcode.vision.aprilLoc;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utilities.Side;

public class GoToAprilTags {

    //TODO: Eventually account for the placing system. If it goes out and in we need to make sure it
    //TODO: is accounted for by the offset(instead of 10).
    public static Pose2d goToTag(int tagNum) {
        if (Side.getSide()=="red") {
            if(tagNum==AprilTags.BACKDROP_LEFT_BLUE.tagNum) {
                return new Pose2d(FieldLocConstants.RED_BLUE_BACKDROP_LEFT[0], FieldLocConstants.RED_BLUE_BACKDROP_LEFT[1] - 10, Rotation2d.fromDegrees(FieldLocConstants.RED_BLUE_BACKDROP_LEFT[2]));
            } else if(tagNum==AprilTags.BACKDROP_MIDDLE_BLUE.tagNum) {
                return new Pose2d(FieldLocConstants.RED_BLUE_BACKDROP_MIDDLE[0], FieldLocConstants.RED_BLUE_BACKDROP_MIDDLE[1] - 10, Rotation2d.fromDegrees(FieldLocConstants.RED_BLUE_BACKDROP_MIDDLE[2]));
            } else if(tagNum==AprilTags.BACKDROP_RIGHT_BLUE.tagNum) {
                return new Pose2d(FieldLocConstants.RED_BLUE_BACKDROP_RIGHT[0], FieldLocConstants.RED_BLUE_BACKDROP_RIGHT[1] - 10, Rotation2d.fromDegrees(FieldLocConstants.RED_BLUE_BACKDROP_RIGHT[2]));
            } else if(tagNum==AprilTags.BACKDROP_LEFT_RED.tagNum) {
                return new Pose2d(FieldLocConstants.RED_RED_BACKDROP_LEFT[0], FieldLocConstants.RED_RED_BACKDROP_LEFT[1] - 10, Rotation2d.fromDegrees(FieldLocConstants.RED_RED_BACKDROP_LEFT[2]));
            } else if(tagNum==AprilTags.BACKDROP_MIDDLE_RED.tagNum) {
                return new Pose2d(FieldLocConstants.RED_RED_BACKDROP_MIDDLE[0], FieldLocConstants.RED_RED_BACKDROP_MIDDLE[1] - 10, Rotation2d.fromDegrees(FieldLocConstants.RED_RED_BACKDROP_MIDDLE[2]));
            } else if(tagNum==AprilTags.BACKDROP_RIGHT_RED.tagNum) {
                return new Pose2d(FieldLocConstants.RED_RED_BACKDROP_RIGHT[0], FieldLocConstants.RED_RED_BACKDROP_RIGHT[1] - 10, Rotation2d.fromDegrees(FieldLocConstants.RED_RED_BACKDROP_RIGHT[2]));
            }
        } else {
            if (tagNum==AprilTags.BACKDROP_LEFT_BLUE.tagNum) {
                return new Pose2d(FieldLocConstants.BLUE_RED_BACKDROP_LEFT[0], FieldLocConstants.BLUE_RED_BACKDROP_LEFT[1] + 10, Rotation2d.fromDegrees(FieldLocConstants.BLUE_RED_BACKDROP_LEFT[2]));
            } else if (tagNum==AprilTags.BACKDROP_MIDDLE_BLUE.tagNum) {
                return new Pose2d(FieldLocConstants.BLUE_RED_BACKDROP_MIDDLE[0], FieldLocConstants.BLUE_RED_BACKDROP_MIDDLE[1] + 10, Rotation2d.fromDegrees(FieldLocConstants.BLUE_RED_BACKDROP_MIDDLE[2]));
            } else if (tagNum==AprilTags.BACKDROP_RIGHT_BLUE.tagNum) {
                return new Pose2d(FieldLocConstants.BLUE_RED_BACKDROP_RIGHT[0], FieldLocConstants.BLUE_RED_BACKDROP_RIGHT[1] + 10, Rotation2d.fromDegrees(FieldLocConstants.BLUE_RED_BACKDROP_RIGHT[2]));
            } else if (tagNum==AprilTags.BACKDROP_LEFT_RED.tagNum) {
                return new Pose2d(FieldLocConstants.BLUE_BLUE_BACKDROP_LEFT[0], FieldLocConstants.BLUE_BLUE_BACKDROP_LEFT[1] + 10, Rotation2d.fromDegrees(FieldLocConstants.BLUE_BLUE_BACKDROP_LEFT[2]));
            } else if (tagNum==AprilTags.BACKDROP_MIDDLE_RED.tagNum) {
                return new Pose2d(FieldLocConstants.BLUE_BLUE_BACKDROP_MIDDLE[0], FieldLocConstants.BLUE_BLUE_BACKDROP_MIDDLE[1] + 10, Rotation2d.fromDegrees(FieldLocConstants.BLUE_BLUE_BACKDROP_MIDDLE[2]));
            } else if (tagNum==AprilTags.BACKDROP_RIGHT_RED.tagNum) {
                return new Pose2d(FieldLocConstants.BLUE_BLUE_BACKDROP_RIGHT[0], FieldLocConstants.BLUE_BLUE_BACKDROP_RIGHT[1] + 10, Rotation2d.fromDegrees(FieldLocConstants.BLUE_BLUE_BACKDROP_RIGHT[2]));
            }
        }
        return new Pose2d(0,0, Rotation2d.fromDegrees(0));
    }
}
