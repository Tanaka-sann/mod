package net.rebis.rebismusket.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rebis.rebismusket.RebisMusket;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RebisMusket.MOD_ID);

    public static final RegistryObject<Item> WROUGHT_IRON_INGOT = ITEMS.register("wrought_iron_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRONZE_PREMIX = ITEMS.register("bronze_premix",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TIN_NUGGET = ITEMS.register("tin_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARQUEBUS = ITEMS.register("arquebus", ArquebusItem::new);

    public static final RegistryObject<Item> MUSKET = ITEMS.register("musket",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MUSKET_WITH_BAYONET = ITEMS.register("musket_with_bayonet",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RATION = ITEMS.register("ration",
            () -> new Item(new Item.Properties().food(ModFoods.RATION)));

    public static final RegistryObject<Item> CARTRIDGE = ITEMS.register("cartridge",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PACK_OF_RATIONS = ITEMS.register("pack_of_rations",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
