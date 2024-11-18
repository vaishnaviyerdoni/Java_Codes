public class MoreOnConditionals{
    public static void main(String[] args){
        int time = 4;
        String ans = MyFunc(time);
        System.out.println(ans);
    }

    public static String MyFunc(int time){
        if (time < 12){
            return "Its Morning!";
        }else if(time < 16){
            return "Its preEvening!";
        }else if(time < 20){
            return "Its Evening";
        }else{
            return "Its Night";
        }
    }

}
