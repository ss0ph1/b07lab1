import java.util.*; 
import java.util.Arrays;

public class Polynomial{

	public double[] data;

	public Polynomial() {
		data = new double[]{0};
	}

	public Polynomial(double[] arr) {
		data = arr;
	}

	Polynomial add(Polynomial element) {
		int length = Math.min(element.data.length, data.length);
		for(int i = 0; i < length; i++) {
			element.data[i] += data[i];
		}
		return element;
	}

	double evaluate(double num){
		double ans = 0;
		for(int i = 0; i < data.length; i++) {
			ans += data[i] * Math.pow(num, i);
		}
		return ans;
	}

	boolean hasRoot(double root) {
		boolean isRoot = false;
		double ans = evaluate(root);
		if(ans == 0) {
			isRoot = true;
		}
		return isRoot;
	}
}