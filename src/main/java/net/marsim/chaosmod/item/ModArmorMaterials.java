package net.marsim.chaosmod.item;

import net.marsim.chaosmod.ChaosMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    DUALITY("duality", 26, new int[]{ 5, 10, 8, 4 }, 25,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 1f, 0.5f, () -> Ingredient.of(ModItems.DUALITY_BAR.get())),
    DARKLIGHT("darklight", 30, new int[]{ 10, 16, 12, 8 }, 30,
        SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0.7f, () -> Ingredient.of(ModItems.DARKLIGHT_BAR.get())),
    NOVA("nova", 30, new int[]{ 20, 30, 25, 17 }, 30,
            SoundEvents.ARMOR_EQUIP_DIAMOND, 6f, 0.9f, () -> Ingredient.of(ModItems.NOVA_BAR.get())),
    ZENITH("zenith", 50, new int[]{ 60, 100, 80, 45 }, 30,
    SoundEvents.ARMOR_EQUIP_DIAMOND, 10f, 1.0f, () -> Ingredient.of(ModItems.ZENITH_BAR.get()));



    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return ChaosMod.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
