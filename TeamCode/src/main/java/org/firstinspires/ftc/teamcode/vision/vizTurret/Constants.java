package org.firstinspires.ftc.teamcode.vision.vizTurret;

public class Constants {

    public static final int FOV = 55;
    public static final int CAMERA_WIDTH = 640;
    public static final int CAMERA_HEIGHT = 360;

    //x,y,z
    public static final double[] CAMERA_OFFSETS = {1.5, 2.4, 8.5};

    //in degrees. Same order as xyz but for rotations. Like a unit circle. Offset from right as zero.

    //order: Pitch, Roll, Yaw
    public static final double[] CAMERA_ROTATION_OFFSETS = new double[]{150.0, 330.0, 220.0};

    //x,y,z. Done as rectangular coords. Offset from the middle of the robot.
    public static final double[] CAMERA_POS_OFFSETS = new double[]{0,0,0};

    public static final double PIXEL_TO_DEG = (double) CAMERA_WIDTH /FOV;
}
