package org.firstinspires.ftc.teamcode.vision.aprilLoc;

public enum AprilTags {

    BACKDROP_LEFT_BLUE(1),
    BACKDROP_MIDDLE_BLUE(2),
    BACKDROP_RIGHT_BLUE(3),
    BACKDROP_LEFT_RED(4),
    BACKDROP_MIDDLE_RED(5),
    BACKDROP_RIGHT_RED(6);

    public final int tagNum;

    AprilTags(int tagNum)
    {
        this.tagNum = tagNum;
    }


    public static boolean contains(int tagNum) {
        for (AprilTagReviewer.TagFamily tag : AprilTagReviewer.TagFamily.values()) {
            if (tag.tagNum == tagNum) {
                return true;
            }
        }
        return false;
    }
}
