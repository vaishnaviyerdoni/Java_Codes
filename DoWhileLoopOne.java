public class DoWhileLoopOne{
    public static void main(String[]args){
        int no = 10;
        do {
            if (no == 15){
                no++;
                continue;
            }
            System.out.println(no);
            no++;  
        }while (no < 25);
    }
}
