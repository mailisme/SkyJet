package me.minecraft.minecraftpvpplugin

import org.bukkit.World
import java.util.*

class GameLoop {
    var GameLoops: MutableMap<World, Timer> = HashMap()

    fun AddGameLoopToWorld(world: World) {
        val t = Timer()

        world.pvp = false
        val SetPvp: TimerTask = object : TimerTask() {
            override fun run() {
                val _t = Timer()

                val CountDown: TimerTask = object : TimerTask() {
                    var LeftSeconds: Int = 3
                    override fun run() {
                        for (player in world.players) {
                            if (LeftSeconds <= 0) {
                                player.sendTitle("START", ":D")
                                world.pvp = true

                                _t.cancel()
                            } else {
                                player.sendTitle("PVP will start in", LeftSeconds.toString())
                            }
                        }

                        LeftSeconds -= 1
                    }
                }

                _t.schedule(CountDown, 0, 1000)
            }
        }

        val SetNoPvp: TimerTask = object : TimerTask() {
            override fun run() {
                val _t = Timer()

                val CountDown: TimerTask = object : TimerTask() {
                    var LeftSeconds: Int = 3
                    override fun run() {
                        for (player in world.players) {
                            if (LeftSeconds <= 0) {
                                player.sendTitle("SEARCH FOR GADGETS!", ":D")
                                world.pvp = false

                                _t.cancel()
                            } else {
                                player.sendTitle("PVP will end in", LeftSeconds.toString())
                            }
                        }

                        LeftSeconds -= 1
                    }
                }

                _t.schedule(CountDown, 0, 1000)
            }
        }

        t.schedule(SetPvp, (PvpTime - 3) * 1000, (PvpTime + NoPvpTime) * 1000)
        t.schedule(SetNoPvp, (PvpTime + NoPvpTime - 3) * 1000, (PvpTime + NoPvpTime) * 1000)

        GameLoops[world] = t
    }

    fun DeleteGameLoopFromWorld(world: World) {
        if (GameLoops.containsKey(world)) {
            GameLoops[world]!!.cancel()
            GameLoops.remove(world)
        }
    }

    companion object {
        var PvpTime: Long = 30
        var NoPvpTime: Long = 30
    }
}
