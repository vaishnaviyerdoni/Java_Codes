import java.util.*;

public class Calories {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Map<String, Integer> calories = new LinkedHashMap<>();

        calories.put("Apple", 130);
        calories.put("Avocado", 50);
        calories.put("Banana", 110);
        calories.put("Cantaloupe", 50);
        calories.put("Grapefruit", 60);
        calories.put("Grapes", 90);
        calories.put("Honeydew Melon", 50);
        calories.put("Kiwi", 90);
        calories.put("Lime", 20);
        calories.put("Lemon", 15);
        
        while(true){
            try{
                System.out.println("Enter the name of fruit: ");
                String fruit = scan.nextLine();

                if (calories.containsKey(fruit)){
                    System.out.println(fruit + " : " + calories.get(fruit));
                    break;
                }
                else{
                    System.out.println("Item not found in calories chart.");
                }
            }catch(InputMismatchException e){
                System.out.println("String Input Expedted!");
            }
        }
        scan.close();
    }
}
