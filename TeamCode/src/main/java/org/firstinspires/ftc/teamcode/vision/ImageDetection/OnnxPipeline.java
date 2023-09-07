package org.firstinspires.ftc.teamcode.vision.ImageDetection;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;


public class OnnxPipeline extends OpenCvPipeline {
        DeepNeuralNetworkProcessor processor = new DeepNeuralNetworkProcessor();
        public static List<DnnObject> detectObject = new ArrayList<>();

    @Override
        public Mat processFrame(Mat input) {
        detectObject = processor.getObjectsInFrame(input, 0.2);
        for (DnnObject obj: detectObject)
        {
            Imgproc.rectangle(input,obj.getLeftBottom(),obj.getRightTop(),new Scalar(255,0,0),1);
        }

            return input;
        }


}
