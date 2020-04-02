import java.util.Random;
import java.util.concurrent.Semaphore;

// ################### SUPPLIER ###################
class Supplier extends Thread {
    private int[] table;
    Semaphore semaphore;

    // get and set methods are used by smoker to access the shop table
    public int[] getTable() {
        return table;
    }
    public void setTable(int i, int value) {
        this.table[i] = value;
    }

    public Supplier(Semaphore semaphore) {
        this.semaphore = semaphore;
        this.table = new int[2];
        for (int i = 0; i < 2; i++) {
            table[i] = -2; // initialize the table with none ingredients
        }
    }

    // returns true only when table state are WAITING (-2)
    private boolean waiting() {
        return ( (table[0] == -2) && (table[1] == -2) );
    }

    // put new random ingredients on shop table
    private void putIngredient() {
        try {
            Random random = new Random();
            // put on table random ingredient
            table[0] = random.nextInt(3);
            table[1] = random.nextInt(3);
            // change case equal
            while(table[0] == table[1]) { table[1] = random.nextInt(3); }
            // inform which ingredient has putted
            System.out.println("The Supplier put on table " + showIngredient(0));
            System.out.println("The Supplier put on table " + showIngredient(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // returns the correspondent name to ingredient number
    private String showIngredient(int i) {
        switch (table[i]) {
            case 0: return "TOBACCO";
            case 1: return "PAPER";
            case 2: return "MATCHES";
            default: return "";
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                while ( !waiting() ) { Thread.sleep(2000); }
                semaphore.acquire();
                putIngredient();
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
