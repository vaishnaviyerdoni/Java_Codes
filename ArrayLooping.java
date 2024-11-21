public class ArrayLooping{
    public static void main(String[] args){
        String [] cars = {"Rolls Royce", "Volkswagen", "Ford", "BMW", "Audi"};
        for (int i = 0; i < cars.length; i++){
            System.out.println((i+1) + " - " + cars[i]);
        }
    }    
}
