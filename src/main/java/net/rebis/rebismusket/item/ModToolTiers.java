package net.rebis.rebismusket.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import net.rebis.rebismusket.RebisMusket;

import java.util.List;

public class ModToolTiers {
    public static final Tier WROUGHT_IRON = TierSortingRegistry.registerTier(
            new ForgeTier(2, 500, 6F, 2F, 14,
                    Tags.Blocks.NEEDS_GOLD_TOOL, () -> Ingredient.of(ModItems.WROUGHT_IRON_INGOT.get())),
            new ResourceLocation(RebisMusket.MOD_ID, "wrought_iron_ingot"),
            List.of(Tiers.IRON), List.of(Tiers.DIAMOND));
}
