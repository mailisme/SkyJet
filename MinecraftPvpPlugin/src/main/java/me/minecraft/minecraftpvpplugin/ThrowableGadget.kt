package me.minecraft.minecraftpvpplugin

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.inventory.ItemStack

abstract class ThrowableGadget(var material: Material, name: String) : ItemStack(), Listener {
    private var projectileType: EntityType? = null

    protected fun onThrow(event: ProjectileLaunchEvent) {}
    protected fun onHitEntity(event: EntityDamageByEntityEvent) {}
    protected open fun onHitObject(event: ProjectileHitEvent) {}


    init {
        projectileType = when (material) {
            Material.SNOW_BALL -> EntityType.SNOWBALL
            Material.EGG -> EntityType.EGG
            Material.EXP_BOTTLE -> EntityType.THROWN_EXP_BOTTLE
            Material.ARROW -> EntityType.ARROW
            Material.FISHING_ROD -> EntityType.FISHING_HOOK
            Material.FIREBALL -> EntityType.FIREBALL
            Material.POTION -> EntityType.SPLASH_POTION
            Material.ENDER_PEARL -> EntityType.ENDER_PEARL
            else -> throw IllegalArgumentException("no this item")
        }
        this.type = material
        this.amount = 1

        val meta = this.itemMeta
        meta.displayName = name
        this.setItemMeta(meta)

        Bukkit.getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance)
    }

    @EventHandler
    fun handleThrow(event: ProjectileLaunchEvent) {
        if (event.entity.shooter is Player) {
            if (event.entity.type == projectileType) {
                onThrow(event)
            }
        }
    }

    @EventHandler
    fun handleHitEntity(event: EntityDamageByEntityEvent) {
        if (event.damager is Entity) {
            if (event.damager.type == projectileType) {
                onHitEntity(event)
            }
        }
    }

    @EventHandler
    fun handleHitObject(event: ProjectileHitEvent) {
        val damager: Entity = event.entity
        if (damager.type == projectileType) {
            onHitObject(event)
        }
    }
}
