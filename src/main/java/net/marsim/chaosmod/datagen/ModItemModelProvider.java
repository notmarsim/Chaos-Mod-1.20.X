package net.marsim.chaosmod.datagen;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output,  ExistingFileHelper existingFileHelper) {
        super(output, ChaosMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.SHADOW_INFUSED_COAL);
        simpleItem(ModItems.UNSTABLE_BAR);
        simpleItem(ModItems.STABLE_BAR);
        simpleItem(ModItems.DUALITY_BAR);
        simpleItem(ModItems.UNSTABLE_PARTICLE);
        simpleItem(ModItems.STABLE_PARTICLE);
        simpleItem(ModItems.VOID_DUST);
        simpleItem(ModItems.VOID_INGOT);
        simpleItem(ModItems.FUSION_CORE);

        // Tools
        handheldItem(ModItems.DUALITY_SWORD);
        handheldItem(ModItems.DUALITY_PICKAXE);
        handheldItem(ModItems.DUALITY_AXE);

        // Armor
        simpleItem(ModItems.DUALITY_HELMET);
        simpleItem(ModItems.DUALITY_CHESTPLATE);
        simpleItem(ModItems.DUALITY_LEGGINGS);
        simpleItem(ModItems.DUALITY_BOOTS);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ChaosMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(ChaosMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
