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

                    if(!isValidDate(d, m, y)){
                        throw new IllegalArgumentException("Dates should be between 1 to 31");
                    }
                    if (m < 1 || m > 12){
                        throw new IllegalArgumentException("Month should be between 1 to 12");
                    }

                    System.out.println(y + "-" + String.format("%02d",m) + "-" + String.format("%02d",d));
                    break;
                }
                else if (date.contains(",")){
                    String myDate = date.replace(",", "");
                    String[] myDates = myDate.split(" ");
                    
                    int Mon = getMonth(myDates[0]);
                    int Date = Integer.parseInt(myDates[1]);
                    int Year = Integer.parseInt(myDates[2]);

                    if (!isValidDate(Date, Mon, Year)){
                        throw new IllegalArgumentException("Dates should be between 1 to 31");
                    }
                    if(Mon < 1 || Mon > 12){
                        throw new IllegalArgumentException();
                    }

                    System.out.println(Year + "-" + String.format("%02d", Mon) + "-" + String.format("%02d", Date));
                    break;
                }
            }
            catch(NumberFormatException e){
                System.out.println("Unexpected Error occured!");
            }
            catch(IllegalArgumentException e){
                System.out.println("Oops! Date format is not correct!");
            }
        }
        scan.close();
    }
    public static boolean isValidDate(int d, int m, int y){
            try{
                if (m < 1 || m > 12){
                    return false;
                }

                int[] ValidDaysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                if ((m == 2) && isLeapYear(y)){
                    ValidDaysInMonth[2] = 29;
                }
                return d >= 1 && d <= ValidDaysInMonth[m]; 
            }
            catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Improper Month!");
            }
            return false;
        }

    public static boolean isLeapYear(int year){
        return ((year% 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }

    public static int getMonth(String month){
        try{
            switch (month){
                case "January": return 1;
                case "February": return 2;
                case "March": return 3;
                case "April": return 4;
                case "May": return 5;                    
                case "June": return 6;
                case "July": return 7;
                case "August": return 8;
                case "September": return 9;
                case "October": return 10;
                case "November": return 11;
                case "December": return 12;

                default: return 0;
            }
        }
        catch(Exception  e){
            System.out.println("Unexpected Error Occured!");
        }
        return 0;
    }
}
