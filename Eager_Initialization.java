public class Eager_Initialization {
    private static Eager_Initialization ConObj = new Eager_Initialization();
    private Eager_Initialization(){
    }

    public static Eager_Initialization getInstance1(){
        return ConObj;
    }
    
    public static void main(String[] args) {
        Eager_Initialization Obj = Eager_Initialization.getInstance1();
        System.out.println(Obj + " Established");
    }
}
