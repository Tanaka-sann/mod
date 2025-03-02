package net.rebis.rebismusket.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArquebusItem extends Item {

    public ArquebusItem() {
        super(new Item.Properties().defaultDurability(250));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 60; // (20 ticks per second)
    }

    public static boolean ammoCheck(Player player){
        for (int i = 1; i < player.getInventory().getContainerSize(); i++) {
            if (ItemStack.isSameItem(player.getInventory().getItem(i), new ItemStack(ModItems.CARTRIDGE.get()))){
                return true;
            }
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        ItemStack held = player.getItemInHand(hand);

        if (held.getItem() instanceof ArquebusItem){
            System.out.println("yup, that's a gun");
            if (ammoCheck(player)){
                System.out.println("Ammo found");
            } else {
                System.out.println("No ammo ?");
            }
        }

        return InteractionResultHolder.pass(held);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity){
        if (entity instanceof Player){
            Player player = (Player) entity;
            int useTime = stack.getUseDuration() - player.getUseItemRemainingTicks();
            if (useTime >= stack.getUseDuration()) {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("Held for 3 seconds!"), true);
            } else {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("Released too early!"), true);
            }
        }
        return stack;
    }
}