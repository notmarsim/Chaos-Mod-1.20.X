package net.marsim.chaosmod.event;

import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MeteorGenerator {

    public static void generate(ServerLevel level, BlockPos impactPos) {
        int craterRadius = 7;
        int meteorRadius = 3;

        for (int x = -craterRadius; x <= craterRadius; x++) {
            for (int y = -craterRadius; y <= craterRadius; y++) {
                for (int z = -craterRadius; z <= craterRadius; z++) {
                    double dist = Math.sqrt(x*x + y*y + z*z);
                    if (dist <= craterRadius) {
                        level.setBlock(impactPos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }

        // offset
        int yOffset = -(craterRadius - meteorRadius + 1);
        BlockPos meteorCenter = impactPos.below(Math.abs(yOffset));

        for (int x = -meteorRadius; x <= meteorRadius; x++) {
            for (int y = -meteorRadius; y <= meteorRadius; y++) {
                for (int z = -meteorRadius; z <= meteorRadius; z++) {
                    double dist = Math.sqrt(x*x + y*y + z*z);
                    if (dist <= meteorRadius) {
                        BlockPos currentPos = meteorCenter.offset(x, y, z);

                        if (level.getBlockState(currentPos).isAir()) {
                            BlockState state = level.random.nextFloat() < 0.7f ?
                                    ModBlocks.ZENITH_CRYSTAL_BLOCK.get().defaultBlockState() :
                                    Blocks.MAGMA_BLOCK.defaultBlockState();

                            level.setBlock(currentPos, state, 3);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 15; i++) {
            BlockPos randomFloor = impactPos.offset(level.random.nextInt(5) - 2, -craterRadius, level.random.nextInt(5) - 2);
            if (level.getBlockState(randomFloor.above()).isAir()) {
                level.setBlock(randomFloor, Blocks.BASALT.defaultBlockState(), 3);
            }
        }

        level.explode(null, impactPos.getX(), impactPos.getY(), impactPos.getZ(), 5.0f, true, Level.ExplosionInteraction.BLOCK);

        level.players().forEach(p ->
                p.sendSystemMessage(Component.literal("§dAnomaly detected:... something has landed!"))
        );

        level.sendParticles(ParticleTypes.LARGE_SMOKE, impactPos.getX(), impactPos.getY(), impactPos.getZ(), 100, 2.0, 2.0, 2.0, 0.1);
    }
}
