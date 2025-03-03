
public class TitleCase{
    public static String toTitleCase(String input){
        while(true){
            try{
                String[] word = input.split(" ");
                StringBuilder title = new StringBuilder();

                for (String chars : word){
                    if (!chars.isEmpty()){
                        title.append(Character.toUpperCase(chars.charAt(0)))
                        .append(chars.substring(1).toLowerCase())
                        .append(" ");
                    }
                }
                return title.toString().trim();
            }
            catch(StringIndexOutOfBoundsException e){
                System.out.println("Oops!, Enter again!");
            }
            catch(Exception e){
                System.out.println("Error!");
            }
        }
    }
}