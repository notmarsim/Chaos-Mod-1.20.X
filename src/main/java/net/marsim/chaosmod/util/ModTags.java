package net.marsim.chaosmod.util;

import net.marsim.chaosmod.ChaosMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;



public class ModTags {
    public static final TagKey<Block> NEEDS_UNSTABLE_TOOL = Blocks.tag("needs_unstable_tool");
    public static final TagKey<Block> NEEDS_STABLE_TOOL = Blocks.tag("needs_stable_tool");
    public static final TagKey<Block> NEEDS_DUALITY_TOOL = Blocks.tag("needs_duality_tool");
    public static final TagKey<Block> NEEDS_STELLAR_TOOL = Blocks.tag("needs_stellar_tool");
    public static final TagKey<Block> NEEDS_DARKLIGHT_TOOL = Blocks.tag("needs_darklight_tool");
    public static final TagKey<Block> NEEDS_NOVA_TOOL = Blocks.tag("needs_nova_tool");
    public static final TagKey<Block> NEEDS_ZENITH_TOOL = Blocks.tag("needs_zenith_tool");
    public static final TagKey<Block> NEEDS_CHAOS_TOOL = Blocks.tag("needs_chaos_tool");


    public static class Blocks{
        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(ChaosMod.MOD_ID,name));
        }
    }

    public static class Items{
        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(ChaosMod.MOD_ID,name));
        }
    }
}