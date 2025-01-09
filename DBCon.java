public class DBCon {
    private static volatile DBCon ConObj;
    private DBCon(){
    }

    public static DBCon getInstance(){
        if (ConObj == null){
            synchronized (DBCon.class){
                if (ConObj == null){
                    ConObj = new DBCon();
                }
            }
        }
        return ConObj;
    }

    public void showMessage(){
        System.out.println("This is Lazy, Double Checked Locking Initialization Singleton Instance");
    }
    public static void main(String a[]){
        DBCon singleton = DBCon.getInstance();
        singleton.showMessage();
}
}
