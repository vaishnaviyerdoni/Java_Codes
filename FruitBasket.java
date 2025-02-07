import java.util.*;

public class FruitBasket{
    public static void main(String[] args){
        ArrayList<String> fruits = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the Fruits you wish to buy: ");

        while(scan.hasNextLine()){
            String fruit = scan.nextLine().trim();
            if (!fruit.isEmpty()){
                fruits.add(fruit);
            }
        }
        scan.close();
        System.out.println("The fruits you bought were: ");
            for(String str : fruits){
                System.out.println(str);
            }
        }
    }
