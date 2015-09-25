import com.omartech.utils.Utils;
import com.omartech.utils.vocabulary.Vocabulary;
import junit.framework.TestCase;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by OmarTech on 15-6-8.
 */
public class VocabularyTest extends TestCase {
    static Logger logger = LoggerFactory.getLogger(VocabularyTest.class);

    @Test
    public void testTFIDF() {
        ToAnalysis.parse("北京天安门上好风光");
        String[] array = {"我爱北京天安门", "天安门上太阳升", "北京天安门上好风光,北京真漂亮, 我买的苹果好吃"};
        List<String> strings = Arrays.asList(array);
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setAutoFilter(false);
        vocabulary.setDebug(true);
        for (String str : strings) {
            vocabulary.addText(str);
        }
        vocabulary.addOver();
        vocabulary.getMeta();
        Map<String, Double> weightMap = vocabulary.getWeightMap();
        for (Map.Entry<String, Double> entry : weightMap.entrySet()) {
            logger.info("词:{}, idf:{}", entry.getKey(), entry.getValue());
        }
        System.out.println("***********************");
        Map<String, Integer> posMap = vocabulary.getPosMap();
        for (Map.Entry<String, Integer> entry : posMap.entrySet()) {
            logger.info("词:{}, 位置:{}", entry.getKey(), entry.getValue());
        }

        for (String str : strings) {
            Map<Integer, Double> posVecMap = vocabulary.generatePositionVectionMap(str);
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            List<Map.Entry<Integer, Double>> list = new ArrayList<>(posVecMap.entrySet());
            Utils.sortMapIntegerAndDouble(list, true);
            for (Map.Entry<Integer, Double> entry : list) {
                sb.append(entry.getKey() + ":" + entry.getValue()+", ");
            }
            sb.append("]\n");
            logger.info("{} ==> {}", str, sb.toString());
        }
    }
}
