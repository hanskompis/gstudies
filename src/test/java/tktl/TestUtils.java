package tktl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.springframework.stereotype.Service;

@Service
public class TestUtils {

    public boolean equalityOfHashMaps(HashMap<Integer, Integer> first, HashMap<Integer, Integer> second) {
//        System.out.println(first.keySet());
//        System.out.println(second.keySet());
//        System.out.println("keys: " + first.keySet().equals(second.keySet()));
//        System.out.println("vals: " + equalValueCollection(first.values(), second.values()));
        return first.keySet().equals(second.keySet()) && equalValueCollection(first.values(), second.values());
    }

    private boolean equalValueCollection(Collection<Integer> first, Collection<Integer> second) {
        Iterator i1 = first.iterator();
        Iterator i2 = second.iterator();
        while (i1.hasNext()) {
            int int1 = (Integer) i1.next();
            int int2 = (Integer) i2.next();
            if(int1 != int2) {
                return false;
            }
        }
        return true;
    }

    public boolean equivalentArrays(int[][] first, int[][] second) {
        
        for (int i = 0; i < first.length; i++) {
            if(first.length != second.length || first[i][0] != second[i][0] || first[i][1] != second[i][1]){
                return false;
            }
        }
        return true;
    }
    
    public void printArray(int[][] arr){
        System.out.println("printArray");
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i][0]+" "+arr[i][1]+"\n");
        }
    }
}
