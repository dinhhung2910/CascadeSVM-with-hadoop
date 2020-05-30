package TTPT.BTL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import libsvm.svm;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_print_interface;
import libsvm.svm_problem;


public class SVMHelper {
    private static svm_print_interface svm_print_null = new svm_print_interface() {
        public void print(String s) {
            System.out.println(s);
        }
    };

    public static svm_problem readProblem(String filepath, svm_parameter param) throws IOException {
        BufferedReader fp = new BufferedReader(new FileReader(filepath));
        Vector<Double> vy = new Vector<Double>();
        Vector<svm_node[]> vx = new Vector<svm_node[]>();
        int max_index = 0;

        while (true) {
            String line = fp.readLine();
            if (line == null) break;

            StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:");

            vy.addElement(Double.parseDouble(st.nextToken()));
            int m = st.countTokens() / 2;
            svm_node[] x = new svm_node[m];
            for (int j = 0; j < m; j++) {
                x[j] = new svm_node();
                x[j].index = Integer.parseInt(st.nextToken());
                x[j].value = Double.parseDouble(st.nextToken());
            }
            if (m > 0) max_index = Math.max(max_index, x[m - 1].index);
            vx.addElement(x);
        }

        svm_problem prob = new svm_problem();
        prob.l = vy.size();
        prob.x = new svm_node[prob.l][];
        for (int i = 0; i < prob.l; i++)
            prob.x[i] = vx.elementAt(i);
        prob.y = new double[prob.l];
        for (int i = 0; i < prob.l; i++)
            prob.y[i] = vy.elementAt(i);

        if (param.gamma == 0 && max_index > 0)
            param.gamma = 1.0 / max_index;

        if (param.kernel_type == svm_parameter.PRECOMPUTED)
            for (int i = 0; i < prob.l; i++) {
                if (prob.x[i][0].index != 0) {
                    throw new RuntimeException("Wrong kernel matrix: first column must be 0:sample_serial_number");
                }
                if ((int) prob.x[i][0].value <= 0 || (int) prob.x[i][0].value > max_index) {
                    throw new RuntimeException("Wrong input format: sample_serial_number out of range");
                }
            }

        fp.close();
        return prob;
    }

    public static svm_parameter buildParam() {
        svm_print_interface printFunc = svm_print_null;    // default printing to stdout

        svm_parameter param = new svm_parameter();
        // default values
        param.svm_type = svm_parameter.C_SVC;
        param.kernel_type = svm_parameter.LINEAR;
        param.degree = 3;
        param.gamma = 0;    // 1/num_features
        param.coef0 = 0;
        param.nu = 0.5;
        param.cache_size = 100;
        param.C = 1;
        param.eps = 1e-3;
        param.p = 0.1;
        param.shrinking = 1;
        param.probability = 0;
        param.nr_weight = 0;
        param.weight_label = new int[0];
        param.weight = new double[0];

        svm.svm_set_print_string_function(printFunc);
        return param;
    }
}

