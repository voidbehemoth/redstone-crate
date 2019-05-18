package voidbehemoth.redstonecrate;
// RedstoneCrate


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import voidbehemoth.redstonecrate.proxy.CommonProxy;

@Mod(modid = RedstoneCrate.MODID, name = RedstoneCrate.MODNAME, version = RedstoneCrate.MODVERSION, dependencies = "required-after:forge@[14.23.5.2768,)", useMetadata = true)
public class RedstoneCrate {


    public static final String MODID = "redstonecrate";
    public static final String MODNAME = "RedstoneCrate";
    public static final String MODVERSION= "0.0.1";

    @SidedProxy(clientSide = "voidbehemoth.redstonecrate.proxy.ClientProxy", serverSide = "voidbehemoth.redstonecrate.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static RedstoneCrate instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
