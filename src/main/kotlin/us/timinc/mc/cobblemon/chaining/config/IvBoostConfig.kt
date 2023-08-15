@file:Suppress("MayBeConstant")

package us.timinc.mc.cobblemon.chaining.config

import draylar.omegaconfig.api.Comment
import draylar.omegaconfig.api.Config
import net.minecraft.world.entity.player.Player
import us.timinc.mc.cobblemon.chaining.Chaining
import us.timinc.mc.cobblemon.counter.Counter

@Suppress("MemberVisibilityCanBePrivate")
class IvBoostConfig : Config {
    @Comment("The multiplier for the player's latest KO streak for a given species")
    val koStreakPoints = 0

    @Comment("The multiplier for the player's total KOs for a given species")
    val koCountPoints = 0

    @Comment("The multiplier for the player's latest capture streak for a given species")
    val captureStreakPoints = 1

    @Comment("The multiplier for the player's total captures for a given species")
    val captureCountPoints = 0

    @Comment("The distance at which a spawning Pokémon considers a player for this boost")
    val effectiveRange = 64

    @Comment("Thresholds for the points : perfect IVs")
    val thresholds: Map<Int, Int> = mutableMapOf(Pair(5, 1), Pair(10, 2), Pair(20, 3), Pair(30, 4))

    @Comment("Turn this to true to see log output")
    val debug = false

    @Suppress("KotlinConstantConditions")
    fun getPoints(player: Player, species: String): Int {
        return (Counter.getPlayerKoStreak(
            player, species
        ) * koStreakPoints) + (Counter.getPlayerKoCount(
            player, species
        ) * koCountPoints) + (Counter.getPlayerCaptureStreak(
            player, species
        ) * captureStreakPoints) + (Counter.getPlayerCaptureCount(
            player, species
        ) * captureCountPoints)
    }

    fun getThreshold(points: Int): Int {
        return thresholds.maxOfOrNull { entry -> if (entry.key < points) entry.value else 0 } ?: 0
    }

    override fun getName(): String {
        return "${Chaining.MOD_ID}/ivBoost"
    }
}