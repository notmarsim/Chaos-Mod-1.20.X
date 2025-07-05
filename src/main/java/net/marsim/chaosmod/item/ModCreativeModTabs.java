package net.marsim.chaosmod.item;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChaosMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CHAOS_TAB = CREATIVE_MODE_TABS.register("chaos_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.PINK_PARTICLE.get()))
                    .title(Component.translatable("creativetab.chaos_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.PINK_PARTICLE.get());
                        output.accept(ModItems.BLUE_PARTICLE.get());



                        output.accept(ModBlocks.UNSTABLE_BLOCK.get());
                        output.accept(ModBlocks.PINK_PARTICLE_ORE.get());
                    })
                    .build());
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
