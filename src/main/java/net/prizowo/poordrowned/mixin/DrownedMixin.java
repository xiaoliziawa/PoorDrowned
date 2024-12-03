package net.prizowo.poordrowned.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Drowned.class)
public class DrownedMixin {
    @Inject(method = "performRangedAttack", at = @At("HEAD"))
    private void onPerformRangedAttack(LivingEntity target, float velocity, CallbackInfo ci) {
        Drowned drowned = (Drowned) (Object) this;
        ItemStack mainHandItem = drowned.getMainHandItem();
        if (mainHandItem.is(Items.TRIDENT)) {
            drowned.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }
    }
}
