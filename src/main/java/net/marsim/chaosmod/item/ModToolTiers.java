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
    public static final Tier STELLAR = TierSortingRegistry.registerTier(
            new ForgeTier(7,7500,15f,9f,45, ModTags.NEEDS_STELLAR_TOOL,
                    ()-> Ingredient.of(ModItems.STELLAR_BAR.get())),
            new ResourceLocation(ChaosMod.MOD_ID, "stellar"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier DARKLIGHT = TierSortingRegistry.registerTier(
            new ForgeTier(14,15000,20f,12f,60, ModTags.NEEDS_DARKLIGHT_TOOL,
                    ()-> Ingredient.of(ModItems.DARKLIGHT_BAR.get())),
            new ResourceLocation(ChaosMod.MOD_ID, "darklight"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier NOVA = TierSortingRegistry.registerTier(
            new ForgeTier(35,37500,50f,30f,150, ModTags.NEEDS_NOVA_TOOL,
                    ()-> Ingredient.of(ModItems.NOVA_BAR.get())),
            new ResourceLocation(ChaosMod.MOD_ID, "nova"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier CHAOS = TierSortingRegistry.registerTier(
            new ForgeTier(105,112500,150f,90f,450, ModTags.NEEDS_CHAOS_TOOL,
                    ()-> Ingredient.of(ModItems.CHAOS_BAR.get())),
            new ResourceLocation(ChaosMod.MOD_ID, "chaos"), List.of(Tiers.NETHERITE), List.of());




}