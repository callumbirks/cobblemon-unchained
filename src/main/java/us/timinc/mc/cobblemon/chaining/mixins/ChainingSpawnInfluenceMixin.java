package us.timinc.mc.cobblemon.chaining.mixins;

import com.cobblemon.mod.common.api.spawning.spawner.PlayerSpawnerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import us.timinc.mc.cobblemon.chaining.spawning.ChainingSpawnInfluence;

@Mixin(value = PlayerSpawnerFactory.class, remap = false)
public abstract class ChainingSpawnInfluenceMixin {
    @Inject(method = "init()V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void addSpawnBoostInfluence(CallbackInfo ci) {
        PlayerSpawnerFactory thisFactory = (PlayerSpawnerFactory) (Object) this;
        var influenceBuilders = ((PlayerSpawnerFactory) (Object) this).getInfluenceBuilders();
        influenceBuilders.add((player) -> new ChainingSpawnInfluence(player, 5000L));
    }

}
