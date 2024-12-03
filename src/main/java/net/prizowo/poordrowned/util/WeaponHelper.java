package net.prizowo.poordrowned.util;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Random;

public class WeaponHelper {
    private static final Random RANDOM = new Random();
    
    public static void handleWeaponBreak(LivingEntity entity, boolean isCrossbow) {
        if (isCrossbow) {
            dropBrokenCrossbow(entity);
        } else {
            dropBrokenBow(entity);
        }
        
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
    
    private static void dropBrokenBow(LivingEntity entity) {
        ItemStack bow = new ItemStack(Items.BOW);
        int randomDurability = RANDOM.nextInt(bow.getMaxDamage() - 10) + 10;
        bow.setDamageValue(bow.getMaxDamage() - randomDurability);
        entity.spawnAtLocation(bow);
    }
    
    private static void dropBrokenCrossbow(LivingEntity entity) {
        ItemStack crossbow = new ItemStack(Items.CROSSBOW);
        int randomDurability = RANDOM.nextInt(crossbow.getMaxDamage() - 10) + 10;
        crossbow.setDamageValue(crossbow.getMaxDamage() - randomDurability);
        entity.spawnAtLocation(crossbow);
    }
} 