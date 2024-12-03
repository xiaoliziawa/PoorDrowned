package net.prizowo.poordrowned.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.portal.PortalShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PortalShape.class)
public class PortalShapeMixin {
    @Shadow @Final @Mutable
    private static final BlockBehaviour.StatePredicate FRAME = (state, level, pos) -> {
        return state.is(Blocks.OBSIDIAN) || state.is(Blocks.CRYING_OBSIDIAN);
    };
} 