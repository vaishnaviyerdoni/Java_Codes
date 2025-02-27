import java.util.*;
public class CokePrice {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        List<Integer> price = new ArrayList<>();

        price.add(5);
        price.add(10);
        price.add(25);

        int cost = 50;

        while(cost > 0){
            System.out.println("Amount Due: "+ cost);

            int coin = scan.nextInt();

            if (coin == price.get(0)){
                cost -= price.get(0);
            }
            else if (coin ==  price.get(1)){
                cost -= price.get(1);
            }
            else if (coin == price.get(2)){
                cost -= price.get(2);
            }
            else{
                System.out.println("Improper Denomination!");
            }   
        }

        int change = -1 * cost;
        System.out.println("Change Owed: "+ change);
        scan.close();
    }
}
