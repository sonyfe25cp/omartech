import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class RunScript {
    public static void main(String[] args) throws IOException {
        String openId = "oIWsFtyI7gottu7fXSIwXIt2uHQw";
        long l1 = System.currentTimeMillis();
        ScriptEngineManager scriptManager = new ScriptEngineManager();
        ScriptEngine js = scriptManager.getEngineByExtension("js");          // The script file we are going to run
        String filename = "/Users/omar/workspace/omartech/omartech4j/weixin/weixin-spider/src/test/resources/sogou.js";
        Bindings bindings = js.createBindings();
        bindings.put(ScriptEngine.FILENAME, filename);
        bindings.put("openId", openId);
        Reader in = new FileReader(filename);
        long l2 = System.currentTimeMillis();
        try {
            Object result = js.eval(in, bindings);
            System.out.println(result);
            String r = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=" + openId + "&" + result + "&page=2";
            System.out.println(r);
            long l3 = System.currentTimeMillis();
            System.out.println(l2 - l1);
            System.out.println(l3 - l2);
        } catch (ScriptException ex) {
            System.out.println(ex);
        }
    }
}
//http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=oIWsFt_xgSnrJ84TI6H0FI0LbWro&eqs=K8sWoYegX32RouKUq7%2FIRubm4maEfSGWfoeFeg746qXo8x4b%2BkrkwTGp7fGmyHjUTKcNk&ekv=7&page=2&t=1436431249872
