package net.prizowo.poordrowned.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin({AbstractSkeleton.class, Pillager.class})
public class RangedAttackMobMixin {
    @Unique
    private static final Random poordrowned$random = new Random();
    
    @Unique
    private int poordrowned$shotCount = 0;
    
    @Inject(method = "performRangedAttack", at = @At("HEAD"))
    private void onPerformRangedAttack(CallbackInfo ci) {
        Object self = this;
        if (self instanceof RangedAttackMob) {
            poordrowned$shotCount++;
            
            if (poordrowned$shotCount >= 10 + poordrowned$random.nextInt(21)) {
                if (self instanceof AbstractSkeleton skeleton) {
                    skeleton.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    skeleton.level().playSound(null, 
                        skeleton.getX(), 
                        skeleton.getY(), 
                        skeleton.getZ(), 
                        SoundEvents.ITEM_BREAK, 
                        SoundSource.HOSTILE, 
                        1.0F, 
                        1.0F);
                } else if (self instanceof Pillager pillager) {
                    pillager.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    pillager.level().playSound(null, 
                        pillager.getX(), 
                        pillager.getY(), 
                        pillager.getZ(), 
                        SoundEvents.ITEM_BREAK, 
                        SoundSource.HOSTILE, 
                        1.0F, 
                        1.0F);
                }
            }
        }
    }
} 