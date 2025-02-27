import java.util.*;

public class GroceryList{
    public static void main(String[] args){
        Map<String, Integer> grocerylist = new LinkedHashMap<>();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the items you'd like: ");

        while(scan.hasNextLine()){
            String item = scan.nextLine().trim();

            grocerylist.put(item, grocerylist.getOrDefault(item,0) + 1);

    }
        scan.close();
        System.out.println(grocerylist);
    }
}