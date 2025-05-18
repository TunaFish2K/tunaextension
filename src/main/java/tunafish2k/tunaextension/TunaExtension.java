package tunafish2k.tunaextension;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import tunafish2k.tunaextension.callback.CallbackServer;
import tunafish2k.tunaextension.callback.CommandCallback;
import tunafish2k.tunaextension.persistent.PersistentDatas;
import tunafish2k.tunaextension.persistent.PersistentSaveThread;
import tunafish2k.tunaextension.puppet.CallbackMessageHandler;
import tunafish2k.tunaextension.puppet.CallbackMessageRenderer;
import tunafish2k.tunaextension.puppet.CommandPuppet;

import java.util.Timer;
import java.util.TimerTask;

@Mod(modid = "tunaextension", useMetadata=true)
public class TunaExtension {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new AbilityManager());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandPuppet());
        ClientCommandHandler.instance.registerCommand(new CommandCallback());

        PersistentDatas.load();
        new Timer().scheduleAtFixedRate( new TimerTask() {
            @Override
            public void run() {
                PersistentDatas.save();
            };
        }, 5 * 1000, 30 *1000);
        Runtime.getRuntime().addShutdownHook(new PersistentSaveThread());
        CallbackServer.INSTANCE.start();
        CallbackMessageRenderer.start();
    }
}
