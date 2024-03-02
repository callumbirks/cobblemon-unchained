package us.timinc.mc.cobblemon.chaining.spawning

import com.cobblemon.mod.common.api.spawning.context.SpawningContext
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail
import com.cobblemon.mod.common.api.spawning.detail.SpawnDetail
import com.cobblemon.mod.common.api.spawning.influence.SpawningInfluence
import net.minecraft.server.network.ServerPlayerEntity
import us.timinc.mc.cobblemon.chaining.Chaining
import us.timinc.mc.cobblemon.chaining.config.SpawnBoostConfig
import us.timinc.mc.cobblemon.chaining.util.Util

open class ChainingSpawnInfluence(
        val player: ServerPlayerEntity, val recalculationMillis: Long = 5000L
) : SpawningInfluence {
    var lastCalculatedTime: Long = 0
    var previousScore: Int = 0
    val config: SpawnBoostConfig = Chaining.spawnBoostConfig

    private fun isPlayerInRange(ctx: SpawningContext, range: Double): Boolean {
        return player.squaredDistanceTo(ctx.position.toCenterPos()) <= range * range
    }

    private fun getPlayerScore(species: String): Int {
        return if (System.currentTimeMillis() - lastCalculatedTime > recalculationMillis) {
            lastCalculatedTime = System.currentTimeMillis()
            previousScore = Util.getPlayerScore(
                    player,
                    species,
                    config.koStreakPoints,
                    config.koCountPoints,
                    config.captureStreakPoints,
                    config.captureCountPoints
            )
            previousScore
        } else {
            previousScore
        }
    }

    override fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
        Chaining.info("affectWeight called")
        if (isPlayerInRange(ctx, config.effectiveRange.toDouble()) && detail is PokemonSpawnDetail) {
            detail.pokemon.species?.let { species ->
                return weight + weight * getPlayerScore(species)
            }
        }
        return weight
    }
}