package net.rebis.rebismusket.item;

import com.mojang.datafixers.kinds.IdF;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.rebis.rebismusket.item.ModKeyBindings;

public class ArquebusItem extends Item {

    private boolean loaded = false;

    public ArquebusItem() {
        super(new Item.Properties().defaultDurability(250));
    }

    public void handleReload(Player player, ItemStack stack, Level world) {
        if (ModKeyBindings.reloadKey.isDown() && !loaded) { //If reload key is pressed, iterate thru inventory
            for (int i = 0; i < Inventory.INVENTORY_SIZE; i++) {
                if (ItemStack.isSameItem(stack, new ItemStack(ModItems.CARTRIDGE.get()))){ //if ammo found, remove 1 and set loaded state
                    player.getInventory().removeItem(i, 1);
                    loaded = true;
                    player.displayClientMessage(net.minecraft.network.chat.Component.literal("loaded !"), true);
                    return;
                }
            }
            player.displayClientMessage(net.minecraft.network.chat.Component.literal(":("), true);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            handleReload(player, stack, world);
        }
    }
}