public class MoreOnFuncParameters {
    public static void main(String[] args){
        fun1("HighLady","Feyre");
        fun1("HighLord", "Rhys");
        fun1("Valkyrie", "Nesta");
        fun1("General", "Cassian");
        fun1("SpyMaster", "Azriel");
        fun1("Emissary", "Lucien");
    }

    public static void fun1(String position, String name){
        System.out.println(name + " is " + position + " Of Night Court!");
    }
    
}
