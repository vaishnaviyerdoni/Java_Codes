public class PrintingReverseTable {
    public static void main(String[] args){
        int no = 2;
        printingreversetable(no);
    }
    
    public static void printingreversetable(int num){
        for(int i = 10; i > 0; i--){
            System.out.println(num + " x " + i + " = " + (num*i));
        }
    }
}
