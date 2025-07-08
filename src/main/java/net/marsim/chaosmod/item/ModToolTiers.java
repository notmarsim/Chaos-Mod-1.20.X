package net.marsim.chaosmod.item;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier DUALITY = TierSortingRegistry.registerTier(
            new ForgeTier(5,5000,10f,6f,30, ModTags.NEEDS_DUALITY_TOOL,
                    ()-> Ingredient.of(ModItems.DUALITY_BAR.get())),
                    new ResourceLocation(ChaosMod.MOD_ID, "duality"), List.of(Tiers.NETHERITE), List.of());




}
