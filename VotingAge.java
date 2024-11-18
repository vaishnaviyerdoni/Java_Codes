public class VotingAge{
    public static void main(String[] args){
        int Age = 27;
        int VotingAge = 18;
        boolean ans = isOldEnough(Age, VotingAge);
        if(ans == true){
            System.out.println("Old Enough to Vote!");
        }
    }

    public static boolean isOldEnough(int myAge, int VotingAge){
        if (myAge >= VotingAge){
            return true;
        }else{
            return false;
        }
    } 
}