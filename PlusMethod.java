public class PlusMethod {
    public static void main(String [] args){
        int ans_1 = plusfunc(3,6);
        double ans_2 = plusfunc(3.3, 6.6);
        System.out.println("Double ans is " + ans_2);
        System.out.println("The integer ans is " + ans_1);
    }
    
    public static double plusfunc(double x, double y){
        return x + y;
    }

    public static int plusfunc(int x , int y){
        return x + y;
    }
}


// This is an example of method overloading.
/* Multiple methods can have the same name as long as
 the number and/ or type of their parameters are different.
 */