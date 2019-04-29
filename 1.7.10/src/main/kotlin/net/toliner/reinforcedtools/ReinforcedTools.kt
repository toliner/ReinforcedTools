package net.toliner.reinforcedtools

import cpw.mods.fml.common.*
import cpw.mods.fml.common.event.FMLConstructionEvent
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.relauncher.Side
import net.toliner.reinforcedtools.ReinforcedTools.MOD_ID
import net.toliner.reinforcedtools.ReinforcedTools.MOD_NAME
import net.toliner.reinforcedtools.ReinforcedTools.MOD_VERSION
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * @author kojin15.
 */
@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, acceptedMinecraftVersions = "[1.7.10]",
        modLanguage = "kotlin", modLanguageAdapter = "net.toliner.reinforcedtools.KotlinAdapter",
        useMetadata = true)
object ReinforcedTools {
    const val MOD_ID = "reinforcedtools"
    const val MOD_NAME = "ReinforcedTools"
    const val MOD_VERSION = "3.0.0"

    @Mod.Metadata
    private lateinit var METADATA: ModMetadata

    @SidedProxy(clientSide = "net.toliner.reinforcedtools.proxy.ClientProxy",
            serverSide = "net.toliner.reinforcedtools.proxy.ServerProxy")
    lateinit var PROXY: CommonProxy

    @Mod.EventHandler
    fun construct(event: FMLConstructionEvent) {
        METADATA.modId = MOD_ID
        METADATA.name = MOD_NAME
        METADATA.version = MOD_VERSION
        METADATA.authorList.add("toliner")
        METADATA.authorList.add("kojin15")
        METADATA.description = "Add some tools better than vanilla."
        METADATA.autogenerated = false
    }

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        PROXY.preInit()
    }

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {
        PROXY.init()
    }

    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        PROXY.postInit()
    }
}

class KotlinAdapter : ILanguageAdapter {
    override fun supportsStatics(): Boolean = false

    override fun setProxy(target: Field, proxyTarget: Class<*>, proxy: Any) {
        target.set(proxyTarget.kotlin.objectInstance, proxy)
    }

    override fun getNewInstance(container: FMLModContainer, objectClass: Class<*>, classLoader: ClassLoader, factoryMarkedAnnotation: Method?): Any? {
        return objectClass.kotlin.objectInstance ?: objectClass.newInstance()
    }

    override fun setInternalProxies(mod: ModContainer?, side: Side?, loader: ClassLoader?) = Unit
}