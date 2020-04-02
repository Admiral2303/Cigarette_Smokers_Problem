import java.util.concurrent.Semaphore;

// ################### SMOKER ###################
class Smoker extends Thread{
    private int id;
    private int ingredient;
    private Supplier supplier;
    private Semaphore semaphore;

    public Smoker(int id, int ingredient, Supplier supplier, Semaphore semaphore) {
        this.id = id;
        this.ingredient = ingredient;
        this.supplier = supplier;
        this.semaphore = semaphore;
    }

    private String showIngredient(int i) {
        switch (i) {
            case 0: return "TOBACCO";
            case 1: return "PAPER";
            case 2: return "MATCHES";
            default: return "";
        }
    }

    private boolean checkTable() {
        return (supplier.getTable()[0] != ingredient) &&
                (supplier.getTable()[1] != ingredient) &&
                (supplier.getTable()[0] > -1) &&
                (supplier.getTable()[1] > -1);
    }

    private void takesIngredient() {
        try {
            System.out.println("Smoker #" + id + " takes " + showIngredient(supplier.getTable()[0]));
            supplier.setTable(0,-1);
            System.out.println("Smoker #" + id + " takes " + showIngredient(supplier.getTable()[1]));
            supplier.setTable(1,-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void smokes() {
        try {
            System.out.println("Smoker #" + id + " are SMOKING");
            Thread.sleep(2000+(int)(Math.random()*((5000-2000)+1))); // between 2 and 5 seconds
            supplier.setTable(0,-2);
            supplier.setTable(1,-2);
            System.out.println("Smoker #" + id + " are FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Smoker #" + id + " have " + showIngredient(ingredient));
            while (true) {
                while ( !checkTable() ) {
                    System.out.println("Smoker #" + id + " tried to check table and hadn't lucky");
                    Thread.sleep(5000);
                }
                semaphore.acquire();
                takesIngredient();
                smokes();
                semaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
