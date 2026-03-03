package net.marsim.chaosmod.screen;

import net.marsim.chaosmod.ChaosMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ChaosMod.MOD_ID);

    public static final RegistryObject<MenuType<VoidRefinerMenu>> VOID_REFINER_MENU =
            registerMenuType("void_refiner_menu", VoidRefinerMenu::new);

    public static final RegistryObject<MenuType<DarklightRefinerMenu>> DARKLIGHT_REFINER_MENU =
            registerMenuType("darklight_refiner_menu", DarklightRefinerMenu::new);

    public static final RegistryObject<MenuType<VoidStationMenu>> VOID_STATION_MENU =
            registerMenuType("chaotic_station_menu", VoidStationMenu::new);

    public static final RegistryObject<MenuType<StellarGeneratorMenu>> STELLAR_GENERATOR_MENU =
            registerMenuType("star_generator_menu", StellarGeneratorMenu::new);

    public static final RegistryObject<MenuType<DarklightGeneratorMenu>> DARKLIGHT_GENERATOR_MENU =
            registerMenuType("darklight_generator_menu", DarklightGeneratorMenu::new);


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
