package us.timinc.mc.cobblemon.chaining

import com.cobblemon.mod.common.api.spawning.context.SpawningContext
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import us.timinc.mc.cobblemon.chaining.config.*
import us.timinc.mc.cobblemon.chaining.modules.HiddenBooster
import us.timinc.mc.cobblemon.chaining.modules.IvBooster
import us.timinc.mc.cobblemon.chaining.modules.ShinyBooster
import us.timinc.mc.cobblemon.chaining.modules.SynchronizedNatures

object Chaining : ModInitializer {
    const val MOD_ID = "cobblemon_chaining"

    private val logger = LoggerFactory.getLogger("spawnBooster")

    private lateinit var shinyBoostConfig: ShinyBoostConfig
    private lateinit var ivBoostConfig: IvBoostConfig
    private lateinit var hiddenBoostConfig: HiddenBoostConfig
    private lateinit var synchronizedNaturesConfig: SynchronizedNaturesConfig
    public lateinit var spawnBoostConfig: SpawnBoostConfig

    private lateinit var ivBooster: IvBooster
    private lateinit var shinyBooster: ShinyBooster
    private lateinit var hiddenBooster: HiddenBooster
    private lateinit var synchronizedNatures: SynchronizedNatures

    override fun onInitialize() {
        shinyBoostConfig = ShinyBoostConfig.Builder.load()
        ivBoostConfig = IvBoostConfig.Builder.load()
        hiddenBoostConfig = HiddenBoostConfig.Builder.load()
        synchronizedNaturesConfig = SynchronizedNaturesConfig.Builder.load()
        spawnBoostConfig = SpawnBoostConfig.Builder.load()

        shinyBooster = ShinyBooster(shinyBoostConfig)
        ivBooster = IvBooster(ivBoostConfig)
        hiddenBooster = HiddenBooster(hiddenBoostConfig)
        synchronizedNatures = SynchronizedNatures(synchronizedNaturesConfig)
    }

    fun possiblyMakeShiny(entity: PokemonEntity, ctx: SpawningContext) {
        shinyBooster.act(entity, ctx)
    }

    fun possiblyModifyIvs(entity: PokemonEntity, ctx: SpawningContext) {
        ivBooster.act(entity, ctx)
    }

    fun possiblyAddHiddenAbility(entity: PokemonEntity, ctx: SpawningContext) {
        hiddenBooster.act(entity, ctx)
    }

    fun possiblySynchronizeNatures(entity: PokemonEntity, ctx: SpawningContext) {
        synchronizedNatures.act(entity, ctx)
    }

    fun info(message: String) {
        logger.info(message)
    }
}