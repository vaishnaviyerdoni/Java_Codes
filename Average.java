public class Average{
    public static void main(String[] args){
        int [] arr = {20,22,18,35,48,26,87,70};
        float avg, sum = 0;
        
        int len = arr.length;

        for (int i: arr){
            sum+=i;
        }

        avg = sum / len;
        System.out.println("The average of arr " + avg);
    }
    
}
