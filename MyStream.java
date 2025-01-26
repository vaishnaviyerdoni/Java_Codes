import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class MyStream {
    public static void main(String[] a){
        List<Integer> nos = Arrays.asList(2,1,4,7,10);
        Stream<Integer> nosStream = nos.stream()
            .filter((Integer val) -> val>= 3)
            .peek((Integer val) -> System.out.println("Filtered: " + val))
            .map((Integer val) -> (val * val))
            .peek((Integer val) -> System.out.println("mapped: " + val))
            .sorted()
            .peek((Integer val) -> System.out.println("Sorted: " + val));

        List<Integer> filteredMappedStream = nosStream.collect(Collectors.toList());
        System.out.println(filteredMappedStream);
    }
    
}
