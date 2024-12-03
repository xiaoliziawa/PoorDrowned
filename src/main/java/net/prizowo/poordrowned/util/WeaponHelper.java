package net.prizowo.poordrowned.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WeaponHelper {
    public static void handleWeaponBreak(LivingEntity entity) {
        entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        entity.level().playSound(null, 
            entity.getX(), 
            entity.getY(), 
            entity.getZ(), 
            SoundEvents.ITEM_BREAK, 
            SoundSource.HOSTILE, 
            1.0F, 
            1.0F);
    }
} 