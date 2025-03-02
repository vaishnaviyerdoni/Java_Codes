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
        
        System.out.print("Enter the name of fruit: ");
        while(scan.hasNextLine()){
            try{
                String item = scan.nextLine();
                if (calories.containsKey(item)){
                    System.out.println(item + " : " + calories.get(item));
                    break;
                }
                else{
                    System.out.println("No Such Item Found!");
                }
            }catch(NoSuchElementException e){
                System.out.println("Item not found");
                scan.next();
            }
            catch(Exception e){
                System.out.println("Unexpected Error Occured");
            }
        }
        scan.close();
    }    
}
