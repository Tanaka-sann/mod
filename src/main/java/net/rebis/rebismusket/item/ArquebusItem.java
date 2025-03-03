package net.rebis.rebismusket.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.rebis.rebismusket.RebisMusket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

public class ArquebusItem extends Item {

    public ArquebusItem(Properties properties) {
        super(properties);
        ItemProperties.register(this, new ResourceLocation(RebisMusket.MOD_ID, "loaded"),
                (itemStack, clientWorld, livingEntity, seed) -> {
                    CompoundTag tag = itemStack.getTag();
                    return (tag != null && tag.getBoolean("loaded")) ? 1.0F : 0.0F;
                });
    }

    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CROSSBOW; // or UseAnim.CROSSBOW depending on which pose you prefer
    }

    private static final int RELOAD_TIME = 40; // 3 seconds (20 ticks per second)

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        CompoundTag tag = held.getOrCreateTag();

        if (held.getItem() instanceof ArquebusItem && !tag.getBoolean("loaded")) {
            if (ammoCheck(player) || player.isCreative()) {
                // Start the use animation
                player.startUsingItem(hand);
                tag.putBoolean("loaded", false);
                tag.putInt("reloadTimer", RELOAD_TIME);
                return InteractionResultHolder.consume(held);
            } else {
                System.out.println("No ammo ?");
            }
        }
        return InteractionResultHolder.pass(held);
    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int useDuration) {
        if (entity instanceof Player player) {
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("reloadTimer")) {
                int timer = tag.getInt("reloadTimer");
                timer--;
                tag.putInt("reloadTimer", timer);

                if (timer <= 0) {
                    tag.putBoolean("loaded", true);
                    tag.remove("reloadTimer");
                    player.displayClientMessage(Component.literal("Reloaded !"), true);
                    useAmmo(player);
                    player.stopUsingItem();  // Stop using when reload completes
                }
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        // This gets called when player releases right-click
        stack.getOrCreateTag().remove("reloadTimer");
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, net.minecraft.world.entity.Entity entity, int slotId, boolean isSelected) {
        // Only keep this if you need to reset reload timer when the item is not held
        if (entity instanceof Player player) {
            boolean isHeld = player.getMainHandItem() == stack || player.getOffhandItem() == stack;
            if (!isHeld) {
                stack.getOrCreateTag().remove("reloadTimer");
            }
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