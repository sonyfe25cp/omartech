package com.omartech.weixin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by OmarTech on 15-7-9.
 */
public class SogouEncrypt {

    static Logger logger = LoggerFactory.getLogger(SogouEncrypt.class);

    public static String generatePostAjaxUrl(String openId) {
        String url = "";
        ScriptEngineManager scriptManager = new ScriptEngineManager();
        ScriptEngine js = scriptManager.getEngineByExtension("js");          // The script file we are going to run
//        String filename = "weixin/weixin-spider/src/test/resources/sogou.js";
        String filename = "/tmp/sogou.js";
        Bindings bindings = js.createBindings();
        bindings.put(ScriptEngine.FILENAME, filename);
        bindings.put("openId", openId);
        try {
            Reader in = new FileReader(filename);
            Object result = js.eval(in, bindings);

//            url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=" + openId + "&" + result + "&page=2";
            url = "http://weixin.sogou.com/gzhjs?cb=sogou.weixin.gzhcb&openid=" + openId + "&" + result ;
            logger.info("openId:{}, return url:{}", openId, url);
        } catch (ScriptException ex) {
            logger.error("scriptException:{}", ex.toString());
        } catch (FileNotFoundException e) {
            logger.error("js file is not found, copy sogou.js to /tmp/");
            System.exit(0);
        }
        return url;
    }

}
