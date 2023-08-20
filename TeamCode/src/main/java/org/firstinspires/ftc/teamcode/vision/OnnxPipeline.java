package org.firstinspires.ftc.teamcode.vision;

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
        processor.setMat(input);
            return input;
        }


}
