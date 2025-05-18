package tunafish2k.tunaextension.mining;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tunafish2k.tunaextension.AbilityManager;
import tunafish2k.tunaextension.events.AbilityUsedEvent;

import java.util.Timer;
import java.util.TimerTask;

public class BuffItemPreUseNotification {
    public boolean notified = true;
    public long notifyTimeMs = 0;

    public static final long time2NotifyBeforeActiveMs = 30 * 1000;

    @SubscribeEvent
    public void abilityUsed(AbilityUsedEvent event) {
        if (event.type != AbilityManager.AbilityType.PICKAXE) return;

        new Timer().schedule(new TimerTask() {
            public void run() {
                //Minecraft.getMinecraft().thePlayer.sendChatMessage("\u00a7cPlace Buff Item!");
            }
        }, event.cooldownMs - time2NotifyBeforeActiveMs);
    }
}
