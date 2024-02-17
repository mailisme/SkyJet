package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;


public class Skill extends ItemStack implements Listener {

    public Material material;//important
    public String name;
    public Map<Player, Long> DelayMap = new HashMap<Player, Long>();
    public Long delay;


    public Skill(Material material, String name) {
        this.material = material;
        this.name = name;
        this.setType(material);

        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);

        getServer().getPluginManager().registerEvents(this, MinecraftPvpPlugin.instance);
    }


    protected void onActivate(PlayerInteractEvent event){

    }

    @EventHandler
    public void Click(PlayerInteractEvent event){
        long time = Instant.now().getEpochSecond();
        Player player = (Player) event.getPlayer();
        if (event.getItem().equals(null)) {
            if (event.getAction() != Action.PHYSICAL) {
                if (DelayMap.containsKey(player)){
                    if (time - DelayMap.get(player) > delay){
                        DelayMap.put(player, time);
                        onActivate(event);
                    }
                }
                else {
                    DelayMap.put(player, time);
                    onActivate(event);
                }
            }
        }
    }
}