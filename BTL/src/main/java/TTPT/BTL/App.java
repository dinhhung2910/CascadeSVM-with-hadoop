package TTPT.BTL;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import libsvm.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	String fileName = "a1a.t";
        String fileTest = "data-d.txt";
    	String trainFilepath = "";
        String testFilepath = "";
        
        try {
            trainFilepath = App.class.getResource("/"+fileName).getPath().replaceAll("%20", " ");;
            testFilepath = App.class.getResource("/"+fileTest).getPath().replaceAll("%20", " ");;
        }catch (Exception e) {
        	 System.out.println(e);
			return;
		}        
        
        svm_problem trainProblem = SVMHelper.readProblem(trainFilepath, new svm_parameter());
        svm_parameter param = new svm_parameter();
        
        svm_parameter trainParam = SVMHelper.buildParam();
        trainParam.C = 2;
        String error_msg = svm.svm_check_parameter(trainProblem, trainParam);
        
        if (error_msg != null) {
            System.err.print("ERROR: " + error_msg + "\n");
            System.exit(1);
        }
        long start = System.currentTimeMillis();
        
        svm_model model = svm.svm_train(trainProblem, trainParam);
        
        long end = System.currentTimeMillis();
        
        System.out.printf("Train Time: %d", end - start);
        svm_problem testProblem = SVMHelper.readProblem(testFilepath, trainParam);

        int count = 0;
        int success = 0;
        for (int i = 0; i < testProblem.x.length; ++i) {
            svm_node[] node = testProblem.x[i];
            double label = svm.svm_predict(model, node);
            count++;
            if (label - testProblem.y[i] == 0) {
                success++;
//                System.out.printf("%d=>labelPredict=%f,label=%f, true\n", count, label, testProblem.y[i]);
            } else {
//                System.out.printf("%d=>labelPredict=%f,label=%f, false\n", count, label, testProblem.y[i]);
            }
        }
        System.out.printf("\n Accuracy (%d/%d), %f\n", success, count, success * 1.000 / count);
    }
}
