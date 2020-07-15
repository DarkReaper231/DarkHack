package bleach.hack.mixin;

import bleach.hack.module.ModuleManager;
import bleach.hack.module.mods.NoRender;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BackgroundRenderer.class)
public class MixinBackgroundRenderer {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z", ordinal = 0), method = {"renderBackground(Lnet/minecraft/client/render/Camera;F)V", "applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZ)V"})
    public boolean hasStatusEffect(LivingEntity entity, StatusEffect effect) {
        if(effect == StatusEffects.BLINDNESS && ModuleManager.getModule(NoRender.class).isToggled() && ModuleManager.getModule(NoRender.class).getSettings().get(0).asToggle().state)
            return false;

        return entity.hasStatusEffect(effect);
    }
}
