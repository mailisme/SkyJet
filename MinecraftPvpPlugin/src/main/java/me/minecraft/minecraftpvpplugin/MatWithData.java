package me.minecraft.minecraftpvpplugin;

import org.bukkit.Material;

public class MatWithData {
    public Material material;
    public byte data;

    public MatWithData(Material material, byte itemMeta) {
        this.material = material;
        this.data = itemMeta;
    }
}
