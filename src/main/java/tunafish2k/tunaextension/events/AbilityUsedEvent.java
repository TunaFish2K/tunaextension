package tunafish2k.tunaextension.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;
import tunafish2k.tunaextension.AbilityManager;

public class AbilityUsedEvent extends Event {
    public AbilityManager.AbilityType type;
    public ItemStack stack;
    public int cooldownMs;

    public AbilityUsedEvent(AbilityManager.AbilityType type, ItemStack usedItem, int cooldownMs) {
        this.type = type;
        this.stack = usedItem;
        this.cooldownMs = cooldownMs;
    }
}
