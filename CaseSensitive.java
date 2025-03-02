import java.util.*;

public class CaseSensitive{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Str: ");
        String str = scan.nextLine().trim();
        String MyString = toTitleCase(str);
        System.out.println(MyString);
        scan.close();

    }

    public static String toTitleCase(String input){
        while(true){
            try{
                String[] word = input.split(" ");

                StringBuilder titleCase = new StringBuilder();
                for(String chars : word){
                    titleCase.append(Character.toUpperCase(chars.charAt(0)))
                    .append(chars.substring(1).toLowerCase())
                    .append(" ");
                }
                return titleCase.toString().trim();
            }
            catch(StringIndexOutOfBoundsException e){
                System.out.println("Oops! Enter your string again!");
            }
            catch(Exception e){
                System.out.println("Unexpected Error Occured!");
            }
        }   
    }
}