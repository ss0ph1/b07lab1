public class Driver {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Test polynomial addition
        Polynomial p1 = new Polynomial(new double[]{5, -3, 7}, new int[]{0, 1, 8}); 
        Polynomial p2 = new Polynomial(new double[]{1, 2, 3}, new int[]{0, 1, 2}); 
        Polynomial resultAdd = p1.add(p2);  
        System.out.println("Add: " + Arrays.toString(resultAdd.coef) + ", " + Arrays.toString(resultAdd.expo));

        // Test multiplication
        Polynomial resultMultiply = p1.multiply(p2);  
        System.out.println("Multi: " + Arrays.toString(resultMultiply.coef) + ", " + Arrays.toString(resultMultiply.expo));

        // Test saving and reading from file
        p1.saveToFile("poly_output.txt");
        Polynomial p3 = new Polynomial(new File("poly_output.txt"));
        System.out.println("Read from file: " + Arrays.toString(p3.coef) + ", " + Arrays.toString(p3.expo));
    }
}