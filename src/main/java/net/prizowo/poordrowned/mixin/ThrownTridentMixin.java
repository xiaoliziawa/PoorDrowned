package net.prizowo.poordrowned.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin extends AbstractArrow {
    @Unique
    private static final Random poordrowned$random = new Random();
    
    private ThrownTridentMixin() {
        super(null, null);
    }
    
    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
    private void onInit(Level level, LivingEntity shooter, ItemStack stack, CallbackInfo ci) {
        if (shooter instanceof Drowned) {
            ThrownTrident trident = (ThrownTrident) (Object) this;
            trident.getPersistentData().putBoolean("poordrowned:fromDrowned", true);
        }
    }
    
    @Inject(method = "playerTouch", at = @At("HEAD"), cancellable = true)
    private void onPlayerTouch(Player player, CallbackInfo ci) {
        ThrownTrident trident = (ThrownTrident) (Object) this;
        if (trident.getPersistentData().getBoolean("poordrowned:fromDrowned") && this.inGround) {
            if (!trident.level().isClientSide) {
                ItemStack stack = new ItemStack(Items.TRIDENT);
                int randomDurability = poordrowned$random.nextInt(251);
                stack.setDamageValue(stack.getMaxDamage() - randomDurability);
                
                if (player.getInventory().add(stack)) {
                    player.take(trident, 1);
                    trident.discard();
                }
            }
            ci.cancel();
        }
    }
}
