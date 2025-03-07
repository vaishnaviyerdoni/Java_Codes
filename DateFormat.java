import java.util.*;

public class DateFormat {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        while (true){
            try{
                System.out.print("Enter the Date: ");
                String date = scan.nextLine().trim();
                if (date.contains("/")){
                    String dates[] = date.split("/");

                    if (dates.length != 3){
                        throw new IllegalArgumentException("Date Format should be DD/MM/YYYY");
                    }

                    int d = Integer.parseInt(dates[0]);
                    int m = Integer.parseInt(dates[1]);
                    int y = Integer.parseInt(dates[2]);

                    if(d < 1  || d > 31){
                        throw new IllegalArgumentException("Dates should be between 1 to 31");
                    }
                    if (m < 1 || m > 12){
                        throw new IllegalArgumentException("Month should be between 1 to 12");
                    }

                    System.out.println(y + "-" + String.format("%02d",m) + "-" + String.format("%02d",d));
                    break;
                }
            }
            catch(NumberFormatException e){
                System.out.println("Unexpected Error occured!");
            }
            catch(IllegalArgumentException e){
                System.out.println("Improper date format!");
            }
        }
        scan.close();
    }
}
