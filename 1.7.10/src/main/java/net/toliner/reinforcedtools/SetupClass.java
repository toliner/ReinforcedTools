package net.toliner.reinforcedtools;

import cpw.mods.fml.relauncher.IFMLCallHook;

import java.util.Map;

/**
 * @author kojin15.
 */
public class SetupClass implements IFMLCallHook {

    @Override
    public void injectData(Map<String, Object> data) {
        ClassLoader loader = (ClassLoader) data.get("classLoader");
        try {
            loader.loadClass("net.toliner.reinforcedtools.KotlinAdapter");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Void call() throws Exception {
        return null;
    }
}
