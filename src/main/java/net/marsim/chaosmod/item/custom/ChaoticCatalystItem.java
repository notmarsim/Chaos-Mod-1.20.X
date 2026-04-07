package net.marsim.chaosmod.item.custom;

import net.marsim.chaosmod.ChaosSavedData;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class ChaoticCatalystItem extends Item {
    public ChaoticCatalystItem(Properties pProperties) {
        super(pProperties.stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            ChaosSavedData data = ChaosSavedData.get(level);

            if (!data.chaosModeActive) {
                data.chaosModeActive = true;
                data.setDirty();


                level.players().forEach(p ->
                        p.sendSystemMessage(Component.literal("§4[Chaos] The ancient seals have shattered... Chaos Mode is now active!"))
                );


                level.playSound(null, player.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.AMBIENT, 1.0f, 0.5f);

                player.getItemInHand(hand).shrink(1);
            } else {
                player.sendSystemMessage(Component.literal("§cChaos is already infesting this world."));
            }
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}
