package net.prizowo.poordrowned.mixin;

import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.prizowo.poordrowned.util.WeaponHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin({AbstractSkeleton.class, Pillager.class, Piglin.class, Illusioner.class})
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
                    WeaponHelper.handleWeaponBreak(skeleton, false);
                } else if (self instanceof Pillager pillager) {
                    WeaponHelper.handleWeaponBreak(pillager, true);
                } else if (self instanceof Piglin piglin) {
                    WeaponHelper.handleWeaponBreak(piglin, true);
                } else if (self instanceof Illusioner illusioner) {
                    WeaponHelper.handleWeaponBreak(illusioner, false);
                }
            }
        }
    }
} 