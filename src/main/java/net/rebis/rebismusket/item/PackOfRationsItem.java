package net.rebis.rebismusket.item;

import com.ibm.icu.impl.duration.impl.DataRecord;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rebis.rebismusket.ModSounds;

public class PackOfRationsItem extends Item{
    public PackOfRationsItem() {
        super(new Item.Properties().stacksTo(64)); // Customize properties as needed
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){
        ItemStack pack = player.getItemInHand(hand);
        if (!level.isClientSide()) {

            // Check if shifting
            if (player.isShiftKeyDown()){
                //Give the player 5 rations per pack
                int rationsCount = pack.getCount() * 5;
                ItemStack rations = new ItemStack(ModItems.RATION.get(), rationsCount);
                pack.shrink(pack.getCount());//remove all packs in stack
                if (!player.getInventory().add(rations)){
                    // If inventory is full, drop the items in the world
                    ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), rations);
                    level.addFreshEntity(itemEntity);
                }
                // Play sound
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.RATION_USE_ALL.get(), SoundSource.PLAYERS, 0.5F, 1.0F);

            } else {
                // Give the player 5 rations
                ItemStack rations = new ItemStack(ModItems.RATION.get(), 5);
                pack.shrink(1); //remove pack
                if (!player.getInventory().add(rations)) {
                    // If inventory is full, drop the items in the world
                    ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), rations);
                    level.addFreshEntity(itemEntity);
                }
                // Play sound
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        ModSounds.RATION_USE.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }
        return InteractionResultHolder.success(pack);
    }

}
