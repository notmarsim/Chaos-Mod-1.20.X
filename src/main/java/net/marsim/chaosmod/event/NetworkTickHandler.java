package net.marsim.chaosmod.event;

import net.marsim.chaosmod.energy.network.EnergyNetworkManager;
import net.marsim.chaosmod.energy.network.EnergyNetworkSavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class NetworkTickHandler {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel level)) return;

        EnergyNetworkManager manager =
                EnergyNetworkSavedData.get(level).getManager();

        manager.tick(level);
    }
}
