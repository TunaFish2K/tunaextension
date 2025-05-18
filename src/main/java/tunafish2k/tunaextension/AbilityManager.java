package tunafish2k.tunaextension;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tunafish2k.tunaextension.events.AbilityUsedEvent;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbilityManager {
    public enum AbilityType {
        PICKAXE
    }

    public static final Pattern pickaxeAbilityPattern = Pattern.compile("\\u00a7r\\u00a7aYou used your \\u00a7r\\u00a7..+ \\u00a7r\\u00a7aPickaxe Ability!\\u00a7r");
    private static final Pattern cooldownLorePattern = Pattern.compile("\\u00a78Cooldown: \\u00a7a(\\d+)s");

    public static long pickaxeAbilityReadyTimeMs = 0;
    public static long pickaxeAbilityCooldownMs = -1;

    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
        String message = event.message.getFormattedText();

        if (pickaxeAbilityPattern.matcher(message).matches()) {
            ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            int cooldownMs = getCooldownFromItemStack(itemStack) * 1000;
            pickaxeAbilityCooldownMs = cooldownMs;
            pickaxeAbilityReadyTimeMs = new Date().getTime() + pickaxeAbilityCooldownMs;

            MinecraftForge.EVENT_BUS.post(new AbilityUsedEvent(AbilityType.PICKAXE, itemStack, cooldownMs));
            return;
        }
    }

    public int getCooldownFromItemStack(ItemStack stack) {
        NBTTagList loreNBT = stack.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
        for (int i = 0; i < loreNBT.tagCount(); i++) {
            String line = loreNBT.getStringTagAt(i);
            Matcher matcher = cooldownLorePattern.matcher(line);
            if (!matcher.matches()) continue;
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (Exception ignored) {
            }
        }
        return -1;
    }
}
