package net.marsim.chaosmod.block.entity;

import net.marsim.chaosmod.block.entity.IOSide;
import net.minecraft.core.Direction;

public interface ISideConfigurable {
    IOSide getSideConfig(Direction side);
    void setSideConfig(Direction side, IOSide ioSide);
}