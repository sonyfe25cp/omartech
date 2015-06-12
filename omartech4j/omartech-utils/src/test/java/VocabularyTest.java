import com.omartech.utils.vocabulary.Vocabulary;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by OmarTech on 15-6-8.
 */
public class VocabularyTest {
    public static void main(String[] args) {
        ToAnalysis.parse("北京天安门上好风光");
        String[] array = {"我爱北京天安门", "天安门上太阳升", "北京天安门上好风光"};
        List<String> strings = Arrays.asList(array);
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setAutoFilter(false);
        for (String str : strings) {
            vocabulary.addText(str);
        }
        vocabulary.addOver();
        vocabulary.getMeta();
        Map<String, Double> weightMap = vocabulary.getWeightMap();
        for(Map.Entry<String, Double> entry : weightMap.entrySet()){
            System.out.println(entry.getKey()+"  --  "+entry.getValue());
        }
        System.out.println("***********************");
        Map<String, Integer> posMap = vocabulary.getPosMap();
        for(Map.Entry<String, Integer> entry : posMap.entrySet()){
            System.out.println(entry.getKey()+"  --  "+entry.getValue());
        }
    }
}
