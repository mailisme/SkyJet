package me.minecraft.minecraftpvpplugin;

import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;

class Container<T> {
    TypeToken<T> tokenOfContainedType = new TypeToken<T>(getClass()) {};

    public Type getContainedType() {
        return tokenOfContainedType.getType();
    }
}