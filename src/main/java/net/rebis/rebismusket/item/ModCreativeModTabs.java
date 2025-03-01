package net.rebis.rebismusket.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.rebis.rebismusket.RebisMusket;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RebisMusket.MOD_ID);

    public static final RegistryObject<CreativeModeTab> REBISMUSKET_TAB = CREATIVE_MODE_TABS.register("rebismusket_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MUSKET.get()))
                    .title(Component.translatable("creativetab.rebismusket_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ARQUEBUS.get());
                        output.accept(ModItems.MUSKET.get());
                        output.accept(ModItems.MUSKET_WITH_BAYONET.get());
                        output.accept(ModItems.RATION.get());
                        output.accept(ModItems.WROUGHT_IRON_INGOT.get());
                        output.accept(ModItems.BRONZE_INGOT.get());
                        output.accept(ModItems.TIN_NUGGET.get());
                        output.accept(ModItems.BRONZE_PREMIX.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
