package org.firstinspires.ftc.teamcode.vision.ImageDetection;

import org.opencv.core.Point;

public class DnnObject {

    private int objectClassId;
    private String objectName;
    private Point leftBottom;
    private Point rightTop;
    private Point centerCoordinate;

    public DnnObject(int objectClassId, String objectName, Point leftBottom, Point rightTop, Point centerCoordinate) {
        this.objectClassId = objectClassId;
        this.objectName = objectName;
        this.leftBottom = leftBottom;
        this.rightTop = rightTop;
        this.centerCoordinate = centerCoordinate;
    }

    public int getObjectClassId() {
        return objectClassId;
    }

    public String getObjectName() {
        return objectName;
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public Point getCenterCoordinate() {
        return centerCoordinate;
    }
}