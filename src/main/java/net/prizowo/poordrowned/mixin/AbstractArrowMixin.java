package net.prizowo.poordrowned.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {
    @Shadow protected boolean inGround;
    
    @Unique
    private boolean poordrowned$isHostileArcher(LivingEntity entity) {
        if (entity instanceof Player) return false;
        if (!(entity instanceof RangedAttackMob)) return false;
        
        ItemStack mainHand = entity.getMainHandItem();
        return mainHand.getItem() instanceof BowItem
            || mainHand.getItem() instanceof CrossbowItem
            || mainHand.getItem() instanceof TridentItem;
    }
    
    @Inject(method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;)V", at = @At("TAIL"))
    private void onInit(EntityType p_36717_, LivingEntity p_36718_, Level p_36719_, CallbackInfo ci) {
        if (poordrowned$isHostileArcher(p_36718_)) {
            AbstractArrow arrow = (AbstractArrow) (Object) this;
            arrow.getPersistentData().putBoolean("poordrowned:fromHostile", true);
        }
    }
    
    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void onPlayerTouch(Player player, CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        if (arrow.getPersistentData().getBoolean("poordrowned:fromHostile") && this.inGround) {
            if (!arrow.level().isClientSide) {
                ItemStack arrowStack = new ItemStack(Items.ARROW);
                if (player.getInventory().add(arrowStack)) {
                    player.take(arrow, 1);
                    arrow.discard();
                }
            }
            ci.cancel();
        }
    }
}
