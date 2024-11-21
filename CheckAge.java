public class CheckAge {
    public static void main(String[] args){
        int age = 23;
        boolean ans = isgranted(age);
        if (ans == true){
            System.out.println("Access Granted");
        }else{
            System.out.println("Access Denied");
        }
    }
    public static boolean isgranted(int age){
        if (age < 18){
            return false;
        }

        else{
            return true;
        }
    }
}
