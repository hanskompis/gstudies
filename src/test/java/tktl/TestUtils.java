package tktl;

import java.util.Collection;
import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class TestUtils {

    public boolean equalityOfHashMaps(HashMap<Integer, Integer> first, HashMap<Integer, Integer> second) {
        System.out.println(first);
        System.out.println(second);
        System.out.println("huh" + first.keySet().equals(second.keySet()));
        System.out.println("hoh" + first.values().equals(second.values()));
        System.out.println(first.values()+" "+second.values());
        return first.keySet().equals(second.keySet()) && first.values().equals(second.values());
    }
    
//    private boolean equalValueCollection(Collection first, Collection second){
//        for(first.)
//    }
}
