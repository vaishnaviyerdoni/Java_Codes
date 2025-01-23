import java.util.Arrays;
class Cars{
    String CarName;
    String CarFuel;

    public Cars(String CarName, String CarFuel){
        this.CarFuel = CarFuel;
        this.CarName = CarName;
    }
}
public class CarsComparator{
    public static void main(String[] a){
        Cars[] CarArray = new Cars[3];
        CarArray[0] = new Cars("SUV", "Petrol");
        CarArray[1] = new Cars("Sedan", "Diesel");
        CarArray[2] = new Cars("Hatchback", "Cng");

        Arrays.sort(CarArray, (Cars obj1, Cars obj2) -> obj1.CarName.compareTo(obj2.CarName));

        for(Cars car : CarArray){
            System.out.println(car.CarName + "-" + car.CarFuel);
        
        }
    }
}
