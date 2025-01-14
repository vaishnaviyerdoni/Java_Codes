public class lazy_Initialization {
    private static lazy_Initialization Obj;
    private lazy_Initialization(){
    }

    public static lazy_Initialization getInstance2(){
        if (Obj == null){
            Obj = new lazy_Initialization();                
        }
        return Obj;
    }

    public static void main(String a[]){
        lazy_Initialization obj = getInstance2();
        
    }
    
}
