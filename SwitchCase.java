public class SwitchCase {
    public static void main(String[] args){
        int day  = 3;
        String ans = Whatdayitis(day);
        System.out.println("It is " + ans + " today!");
    }

    public static String Whatdayitis(int day){
        switch (day){
            case 1:
                return "Monday";
            
            case 2:
                return "Tuesday";

            case 3:
                return "Wednesday";

            case 4:
                return "Thursday";
            
            case 5:
                return "Friday";

            case 6:
                return "Saturday";

            case 7:
                return "Sunday";
            
            default:
                return "This isnt a correct day";
            
        }
    }
}
