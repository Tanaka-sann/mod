package net.rebis.rebismusket.item;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModKeyBindings {

    public static KeyMapping reloadKey;

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        reloadKey = new KeyMapping("rebismusket.reload", InputConstants.Type.KEYSYM, InputConstants.KEY_R, "category.rebismusket.firearms");
        event.register(reloadKey);
    }
}
