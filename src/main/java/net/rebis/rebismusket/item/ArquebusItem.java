package net.rebis.rebismusket.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rebis.rebismusket.ClientEventHandler;

public class ArquebusItem extends Item {

    public ArquebusItem() {
        super(new Item.Properties().defaultDurability(250));
    }

    private static final int RELOAD_TIME = 60; // 3 seconds (20 ticks per second)

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        CompoundTag tag = held.getOrCreateTag();

        if (held.getItem() instanceof ArquebusItem && !tag.getBoolean("loaded")) {
            if (ammoCheck(player)) {
                if (tag.contains("reloadTimer")) {
                    tag.remove("reloadTimer"); // Cancel reload on right-click release
                    ClientEventHandler.isReloading = false; // Reset reload flag
                    return InteractionResultHolder.pass(held); // Stop further processing
                } else {
                    tag.putBoolean("loaded", false);
                    tag.putInt("reloadTimer", RELOAD_TIME);
                    ClientEventHandler.isReloading = true; // Reloading started
                }
            } else {
                System.out.println("No ammo ?");
            }
        }

        return InteractionResultHolder.pass(held);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player) {
            if (player.getMainHandItem() == stack || player.getOffhandItem() == stack) {
                CompoundTag tag = stack.getOrCreateTag();
                if (tag.contains("reloadTimer")) {
                    if (!ClientEventHandler.isMousePressed && ClientEventHandler.isReloading) { // Mouse released
                        tag.remove("reloadTimer"); // Cancel reload
                        ClientEventHandler.isReloading = false; // Reset reload flag
                        System.out.println("reload cancelled");
                    } else {
                        int timer = tag.getInt("reloadTimer");
                        timer--;
                        tag.putInt("reloadTimer", timer);
                        System.out.println(timer);

                        if (timer <= 0) {
                            tag.putBoolean("loaded", true);
                            tag.remove("reloadTimer");
                            player.displayClientMessage(net.minecraft.network.chat.Component.literal("Reloaded !"), true);
                            ClientEventHandler.isReloading = false; // Reset reload flag
                            System.out.println("loaded");
                        }
                    }
                }
            } else {
                stack.getOrCreateTag().remove("reloadTimer");
                ClientEventHandler.isReloading = false; // Reset reload flag
            }
        } else {
            stack.getOrCreateTag().remove("reloadTimer");
            ClientEventHandler.isReloading = false; // Reset reload flag
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40; // (20 ticks per second)
    }

    public static boolean ammoCheck(Player player){
        for (int i = 1; i < player.getInventory().getContainerSize(); i++) {
            if (ItemStack.isSameItem(player.getInventory().getItem(i), new ItemStack(ModItems.CARTRIDGE.get()))){
                return true;
            }
        }
        return false;
    }

    public static void useAmmo(Player player){
        for (int i = 1; i < player.getInventory().getContainerSize(); i++) {
            if (ItemStack.isSameItem(player.getInventory().getItem(i), new ItemStack(ModItems.CARTRIDGE.get()))){
                player.getInventory().getItem(i).shrink(1);
            }
        }
    }
}