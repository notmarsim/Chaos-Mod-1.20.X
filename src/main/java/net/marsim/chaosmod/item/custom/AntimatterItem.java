package net.marsim.chaosmod.item.custom;

import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class AntimatterItem extends Item {
    public AntimatterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);


        if (!level.isClientSide()) {

            ServerLevel serverLevel = (ServerLevel) level;
            RandomSource random = level.getRandom();
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 0.5F);

            serverLevel.sendParticles(ParticleTypes.PORTAL,
                    player.getX(), player.getY() + 1, player.getZ(),
                    40, 0.5, 0.5, 0.5, 0.1);

            int blocksToTransform = random.nextInt(3) + 1;
            int blocksFound = 0;
            int radius = 160;

            for (int i = 0; i < 1000 && blocksFound < blocksToTransform; i++) {
                int offsetX = random.nextInt(radius * 2) - radius;
                int offsetZ = random.nextInt(radius * 2) - radius;
                int offsetY = random.nextInt(70) - 60;

                BlockPos targetPos = player.blockPosition().offset(offsetX, offsetY, offsetZ);
                BlockState state = level.getBlockState(targetPos);


                boolean isStone = state.is(BlockTags.BASE_STONE_OVERWORLD);
                boolean isDeepslate = state.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

                if (isStone || isDeepslate) {
                    level.setBlockAndUpdate(targetPos, ModBlocks.ANTIMATTER_BLOCK.get().defaultBlockState());
                    blocksFound++;

                    player.sendSystemMessage(Component.literal("§dAntimatter disturbance detected"));


                    level.playSound(null, targetPos, SoundEvents.AMETHYST_BLOCK_CHIME,
                            SoundSource.BLOCKS, 1.5F, 0.5F);

                    serverLevel.sendParticles(ParticleTypes.WITCH,
                            targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5,
                            15, 0.2, 0.2, 0.2, 0.05);
                }
            }


            if (blocksFound > 0) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                player.getCooldowns().addCooldown(this, 100);
                return InteractionResultHolder.success(itemstack);
            } else {
                player.sendSystemMessage(Component.literal("§cThe pulse failed to stabilize..."));
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}