import java.util.*;
public class CaseSensitive{
    public static String toTitleCase(String input){
        
        Scanner scan = new Scanner(System.in);

        String[] word = input.split(" ");
        StringBuilder titleCase = new StringBuilder();
        for (String chars : word){
            titleCase.append(Character.toUpperCase(chars.charAt(0)))
            .append(chars.substring(1).toLowerCase())
            .append(" ");
        }
        
        scan.close();
        return titleCase.toString().trim();
    }
}