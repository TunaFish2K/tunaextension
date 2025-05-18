package tunafish2k.tunaextension.puppet;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import java.util.Timer;
import java.util.TimerTask;

public class CallbackMessageRenderer {
    public static void start() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (CallbackMessageHandler.messages.isEmpty()) return;
                    if (Minecraft.getMinecraft().thePlayer == null) return;
                    CallbackMessageHandler.messages.forEach(value -> {
                        System.out.println(Minecraft.getMinecraft());
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(value));
                    });
                    CallbackMessageHandler.messages.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 100, 200);
    }
}
