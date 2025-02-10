import java.util.*;

public class GroceryList{
    public static void main(String[] args){
        Map<Integer, String> grocerylist = new HashMap<>();

        Scanner scan = new Scanner(System.in);

        Integer num = 1;
        while(scan.hasNextLine()){
            String item = scan.nextLine().trim();

            if (!item.isEmpty()){
                grocerylist.put(num, item);
                num++;
            }else{
                break;
            }
        }
        scan.close();
        System.out.println(grocerylist);
    }
}