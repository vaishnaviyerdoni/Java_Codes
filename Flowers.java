import java.util.*;

class Flowers{
    public static void main(String[] args){
        ArrayList<String> flowers = new ArrayList<>();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Flower Name: ");
        String flower = scan.nextLine();
        flowers.add(flower);
        System.out.println(flowers);
        scan.close();
    }
}