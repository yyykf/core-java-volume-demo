package cn.ykf.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @author YuKaiFan <1092882580@qq.com>
 * @date 2021/2/19
 */
public class ScriptDemo {

    public static void main(String[] args) throws ScriptException {
        // global manager
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngineManager.getEngineFactories().forEach(scriptEngineFactory -> {
            scriptEngineFactory.getNames().forEach(System.out::println);
            // thread safe?
            System.out.println(scriptEngineFactory.getParameter("THREADING"));
            System.out.println("------");
        });
        // get engine
        ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");
        // eval js
        engine.eval("var a = 100");
        Object result = engine.eval("a + 100");
        System.out.println(result);
    }
}
