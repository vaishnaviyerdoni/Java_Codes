public class PritingTable {
    public static void main(String[] args){
        int no = 2;
        printTable(no);
    }

    public static void printTable(int num){
        for(int i = 1; i <= 10; i++){
            System.out.println(num + " x " + i + " = " + (num*i));
        }
    }
}
