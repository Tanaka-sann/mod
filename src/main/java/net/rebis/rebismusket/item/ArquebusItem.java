package net.rebis.rebismusket.item;

import com.mojang.datafixers.kinds.IdF;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.rebis.rebismusket.item.ModKeyBindings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class ArquebusItem extends Item {

    public ArquebusItem() {
        super(new Item.Properties().defaultDurability(250));
    }

    public void handleReload(Player player, ItemStack stack, Level world) {

        if (ModKeyBindings.reloadKey.isDown()) {
            CompoundTag tag = stack.getOrCreateTag();

            if (!tag.getBoolean("loaded")) {
                if (ItemStack.isSameItem(player.getMainHandItem(), new ItemStack(ModItems.ARQUEBUS.get()))
                        || ItemStack.isSameItem(player.getOffhandItem(), new ItemStack(ModItems.ARQUEBUS.get()))) {
                    for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                        ItemStack inventoryStack = player.getInventory().getItem(i);
                        if (ItemStack.isSameItem(inventoryStack, new ItemStack(ModItems.CARTRIDGE.get()))) {
                            player.getInventory().removeItem(i, 1);
                            tag.putBoolean("loaded", true);
                            if (world.isClientSide()) {
                                player.displayClientMessage(net.minecraft.network.chat.Component.literal("loaded !"), true);
                            }
                            return;
                        }
                    }
                    if (world.isClientSide()) {
                        player.displayClientMessage(net.minecraft.network.chat.Component.literal(":("), true);
                    }
                }
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            ItemStack mainHandStack = player.getMainHandItem();
            ItemStack offHandStack = player.getOffhandItem();

            if (ItemStack.isSameItem(mainHandStack, new ItemStack(ModItems.ARQUEBUS.get()))
                    || ItemStack.isSameItem(offHandStack,new ItemStack(ModItems.ARQUEBUS.get()))) {
                handleReload(player, stack, world);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            CompoundTag tag = stack.getOrCreateTag();

            if (tag.contains("loaded")) {
                if (ItemStack.isSameItem(player.getMainHandItem(), new ItemStack(ModItems.ARQUEBUS.get()))
                        || ItemStack.isSameItem(player.getOffhandItem(), new ItemStack(ModItems.ARQUEBUS.get()))) {

                    tag.remove("loaded");
                    player.displayClientMessage(net.minecraft.network.chat.Component.literal("Bang !"), true);
                }
            }
        }
        return stack;
    }

    public boolean isLoaded(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains("loaded");
    }

    public void setUnloaded(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.remove("loaded");
    }
}