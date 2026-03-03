package net.marsim.chaosmod.item.custom;

import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class AntimatterItem extends Item {
    public AntimatterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            RandomSource random = level.getRandom();

            int blocksToTransform = random.nextInt(3) + 1;
            int blocksFound = 0;


            int radius = 160;


            for (int i = 0; i < 500 && blocksFound < blocksToTransform; i++) {
                int offsetX = random.nextInt(radius * 2) - radius;
                int offsetZ = random.nextInt(radius * 2) - radius;

                int offsetY = random.nextInt(60) - 30;

                BlockPos targetPos = player.blockPosition().offset(offsetX, offsetY, offsetZ);


                if (level.getBlockState(targetPos).is(Blocks.STONE)) {
                    level.setBlockAndUpdate(targetPos, ModBlocks.ANTIMATTER_BLOCK.get().defaultBlockState());
                    blocksFound++;


//                    player.sendSystemMessage(Component.literal("§dAntimatter disturbance detected at: " +
//                            targetPos.getX() + ", " + targetPos.getY() + ", " + targetPos.getZ()));

                    player.sendSystemMessage(Component.literal("§dAntimatter disturbance detected"));
                }
            }

            if (blocksFound > 0) {

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                player.getCooldowns().addCooldown(this, 100);
                return InteractionResultHolder.success(itemstack);
            } else {
                player.sendSystemMessage(Component.translatable("No suitable stone found nearby..."));
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}