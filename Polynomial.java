
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial{

	public double[] coef;
	public int[] expo;
	public HashMap<Integer,Double> dict;

	public Polynomial() {
		this.coef = new double[]{0};
		this.expo = new int[]{0};
		this.dict = new HashMap<Integer, Double>();
	}

	public Polynomial(double[] input_coef, int[] input_expo) {
		this.coef = input_coef;
		this.expo = input_expo;
		this.dict = new HashMap<Integer, Double>();
		
		for(int i=0; i < coef.length; i++) {
			dict.put(this.expo[i],this.coef[i]);
		}
	}
	
	public Polynomial(File f) throws FileNotFoundException {
		Scanner input = new Scanner(f);
		String line = "" ;
		Double c;
		Integer e;

		this.dict = new HashMap<Integer, Double>();		
		this.coef = new double[]{0};
		this.expo = new int[]{0};
		
		List<Double> tempCoef = new ArrayList<>();
		List<Integer> tempExpo = new ArrayList<>();

		while (input.hasNextLine()) {
			line = input.nextLine();
        }
        input.close();
		System.out.println("the line is:" + line);

        
        line = line.replace("-", "+-");
        if(!line.startsWith("-")) {
        	line = "+" + line;
        }

        String[] split1 = line.split("\\+");
        
        for(int i = 0; i < split1.length; i++) {
    		
    		if(split1[i].isEmpty()) {
    			continue;
    		}
   
        	if(split1[i].contains("x")) {
        		
            	String[] split2 = split1[i].split("x");

            	// degree 1
            	if(split2.length == 1) {
            		e = 1;
            		c = Double.parseDouble(split2[0]);
            		this.dict.put(e,c);
            		tempCoef.add(c);
            		tempExpo.add(e);
            	}
            	// degree greater than 1
            	else if(split2.length == 2) {
            		e = Integer.parseInt(split2[1]);
            		c = Double.parseDouble(split2[0]);
            		this.dict.put(e,c);
            		tempCoef.add(c);
            		tempExpo.add(e);
            	}
        	}
        	// degree 0
        	else {
        		c = Double.parseDouble(split1[i]);
        		this.dict.put(0, c);
        		tempCoef.add(c);
        		tempExpo.add(0);
        	}
        }
        
        this.coef = new double[tempCoef.size()];
        this.expo = new int[tempExpo.size()];
        
        for(int i = 0 ; i < this.coef.length; i++) {
        	this.coef[i] = tempCoef.get(i);
        	this.expo[i] = tempExpo.get(i);
        }
        
	}
	
	Polynomial convertDictToPoly(HashMap<Integer, Double> dict) {
		
		ArrayList<Integer> keys = new ArrayList<Integer>(dict.keySet());
		Collections.sort(keys);
		
		int[] expo = new int[keys.size()];
		double[] coef = new double[keys.size()];
		int idx = 0;
		
		for(Map.Entry<Integer, Double> terms : dict.entrySet()) {
			expo[idx] = terms.getKey();
			coef[idx] = terms.getValue();
			idx++;
		}

		Polynomial result = new Polynomial(coef, expo);
		return result;
	}
	

	Polynomial add(Polynomial input) {
		Double value = 0.0;
		for(Map.Entry<Integer, Double> terms : input.dict.entrySet()) {
			System.out.println("term is:" + terms.getKey());
			if(this.dict.containsKey(terms.getKey())) {
				value = this.dict.get(terms.getKey()) + terms.getValue();
				System.out.println("value is:"+ value);
				if(value == 0) {
					this.dict.remove(terms.getKey());
					input.dict.remove(terms.getKey());
					continue;
				}
				input.dict.replace(terms.getKey(), value);
				System.out.println(input.dict.get(terms.getKey()));

			} else {
				input.dict.put(terms.getKey(),input.dict.get(terms.getKey()));
			}
		}
		
		for(Map.Entry<Integer, Double> terms : this.dict.entrySet()) {
			//System.out.println(terms.getKey());
			if(!input.dict.containsKey(terms.getKey())) {
				input.dict.put(terms.getKey(),this.dict.get(terms.getKey()));
			} 
		}
		return convertDictToPoly(input.dict);
	}

	double evaluate(double num){
		double ans = 0;
		for(int i = 0; i < coef.length; i++) {
			ans += coef[i] * Math.pow(num, expo[i]);
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

	Polynomial multiply(Polynomial input) {
		
		int input_length = input.coef.length;
		int length = this.coef.length;
		
		Polynomial result = new Polynomial();

		for(int i = 0; i < length; i++) {
			for(int j = 0; j < input_length; j++) {				
				double tempc = this.coef[i] * input.coef[j];
				int tempe = this.expo[i] + input.expo[j];

				double[] cArr = {tempc};
				int[] eArr = {tempe};
				Polynomial temp = new Polynomial(cArr,eArr);
				result = result.add(temp);
			}
		}
		return result;
	}

	
	public void saveToFile(String name) throws IOException {
		//PrintStream output = new PrintStream(name);
		FileWriter output = new FileWriter(name);
		String poly = "";
		String exponent = "";
		String coefficient = "";


		for(int i = 0; i < this.coef.length; i++) {

			coefficient = Double.toString(this.coef[i]);
			//add plus signs
			if((i != 0) && (!coefficient.contains("-"))) {
				poly += '+';
			}
			poly += coefficient;

			//add x case degree 1
			if(this.expo[i] == 1) {
				poly += "x";
			}
			//add x case degree greater than 1
			else if(this.expo[i] > 1) {
				poly += "x";
				exponent = Integer.toString(this.expo[i]);
				poly += exponent;
			}
			//case degree 0 no x needed
		}

		output.write(poly);
        output.close();
	}
}
