public class SmallestAge {
      public static void main(String[] args){
        int []ages = {20,22,18,35,48,26,87,70};
        int len = ages.length;
        
        int lowestAge = ages[0];

        for(int age: ages){
            if(lowestAge > age){
                lowestAge = age;
            }
        }
        System.out.println(lowestAge);
        System.out.println(len);
    }
    
}
