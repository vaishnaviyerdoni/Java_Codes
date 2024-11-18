public class MoreOnVariables{
    public static void main(String[] args){
        String first = "Vaishnavi ";
        String Last = "Yerdoni";
        int Age = 24;
        boolean isMarried = false;
        double cgpa = 7.28d;
        String MyName = first + Last;
        System.out.println(MyName);
        System.out.println("The marital status of " + MyName + " is " + isMarried+ ".");
        System.out.println(MyName +  " is " + Age +" years old and scored "+ cgpa + " cgpa in her masters degree.");

        int x = 9;
        float y = 20.1f;
        double z = 300.1d;
        System.out.println(x + y + z);
        
        int m,n,l;
        m = n = l = 90;
        float ans = m+n+l;
        System.out.println(ans);

    }

}