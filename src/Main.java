import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {

        try {
//            System.setOut(new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"/output.txt", false)));
        } catch (Exception e) {
            System.out.println(e);
        }

        // initializing Semaphore
        Semaphore semaphore = new Semaphore(1,true);

        // initializing the supplier
        Supplier supplier = new Supplier(semaphore);

        // initializing the smokers
        Smoker[] smokers = new Smoker[3];
        smokers[0] = new Smoker(0, 0, supplier, semaphore);
        smokers[1] = new Smoker(1, 1, supplier, semaphore);
        smokers[2] = new Smoker(2, 2, supplier, semaphore);

        // starting
        System.out.println("----------- WELCOME TO TOBACCO SHOP -----------");
        supplier.start();
        smokers[0].start();
        smokers[1].start();
        smokers[2].start();
    }
}

