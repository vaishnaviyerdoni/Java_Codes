import java.util.*;

public class Taqueria {
    public static void main(String[] args){
        Map<String, Double> Menu = new LinkedHashMap<>();
        Scanner scan = new Scanner(System.in);

        Menu.put("Baja Taco", 4.25);
        Menu.put("Burrito", 7.50);
        Menu.put("Bowl", 8.50);
        Menu.put("Nachos", 11.00);
        Menu.put("Quesadilla", 8.50);
        Menu.put("Super Burrito", 8.50);
        Menu.put("Super Quesadilla", 9.5);
        Menu.put("Taco", 3.00);
        Menu.put("Tortilla Salad", 8.00);

        int total_Bill = 0;
        System.out.println("Welcome to Taqueria!");
        System.out.println("What would you like to have?");

        while(scan.hasNextLine()){
            try{
                String Order = scan.nextLine();
                if (Order.equalsIgnoreCase("Done")){
                    break;
                }

                if (Menu.containsKey(Order)){
                    total_Bill += Menu.get(Order);
                }else{
                    System.out.println("No such item found in menu.");
                }
                
            }catch(NoSuchElementException e){
                System.out.println("Error Occured");
                scan.next();
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        scan.close();
        String Bill = String.format("%.2f", (double)total_Bill);
        System.out.println("The total bill is: " + Bill + "$");
    }
}
