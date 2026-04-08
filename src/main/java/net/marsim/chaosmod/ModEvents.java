package net.marsim.chaosmod;

import net.marsim.chaosmod.block.ModBlocks;
import net.marsim.chaosmod.event.MeteorGenerator;
import net.marsim.chaosmod.entity.ChaosSpider;
import net.marsim.chaosmod.entity.ChaosZombie;
import net.marsim.chaosmod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChaosMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onBonemeal(BonemealEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);

        if (blockState.is(Blocks.GRASS_BLOCK) && level instanceof ServerLevel serverLevel) {

            // 20% modded bone meal
            if (serverLevel.getRandom().nextFloat() < 0.20f) {

                boolean placedSomething = false;

                for (int i = 0; i < 80; ++i) {


                    BlockPos randomPos = pos.offset(
                            serverLevel.getRandom().nextInt(15) - 7, // -7 to +7 (total 15)
                            serverLevel.getRandom().nextInt(5) - 2,  // -2 to +2 (total 5)
                            serverLevel.getRandom().nextInt(15) - 7   //  -7 to +7 (total 15)
                    );


                    BlockPos groundPos = findGround(serverLevel, randomPos);


                    if (serverLevel.getBlockState(groundPos).is(Blocks.GRASS_BLOCK) && serverLevel.getBlockState(groundPos.above()).isAir()) {

                        BlockState plantToPlace = getPlantToPlace(serverLevel.getRandom());
                        serverLevel.setBlock(groundPos.above(), plantToPlace, 3);
                        placedSomething = true;
                    }
                }

                if (placedSomething) {
                    level.levelEvent(1505, pos, 0);
                    event.setResult(Event.Result.ALLOW);
                }
            }
        }
    }
    // meteor
    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {

        if (event.phase != TickEvent.Phase.END || event.level.isClientSide) return;

        ServerLevel serverLevel = (ServerLevel) event.level;

        // 0.0001f +- 20 minutes
        // 0.01f always
        if (serverLevel.getRandom().nextFloat() < 0.000001f && !serverLevel.players().isEmpty()) {


            var player = serverLevel.players().get(serverLevel.getRandom().nextInt(serverLevel.players().size()));


            int range = 320;
            int x = player.blockPosition().getX() + (serverLevel.getRandom().nextInt(range * 2) - range);
            int z = player.blockPosition().getZ() + (serverLevel.getRandom().nextInt(range * 2) - range);


            BlockPos impactPos = serverLevel.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE, new BlockPos(x, 0, z));

            MeteorGenerator.generate(serverLevel, impactPos);
        }
    }
    private static BlockPos findGround(Level level, BlockPos startPos) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(startPos.getX(), startPos.getY(), startPos.getZ());
        for (int y = 0; y < 10 && level.getBlockState(mutablePos).isAir(); ++y) {
            mutablePos.move(0, -1, 0);
        }
        return mutablePos;
    }

    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.FinalizeSpawn event) {

        LevelAccessor accessor = event.getLevel();

        if (!accessor.isClientSide() && accessor instanceof ServerLevelAccessor serverAccessor) {
            Level level = serverAccessor.getLevel();

            if (ChaosSavedData.get(level).chaosModeActive) {


                if (event.getSpawnType() == MobSpawnType.SPAWNER) return;


                if (level.random.nextFloat() < 0.40f) {
                    Mob oldMob = event.getEntity();
                    EntityType<?> targetType = null;

                    if (oldMob instanceof Zombie && !(oldMob instanceof ChaosZombie)) targetType = ModEntities.CHAOS_ZOMBIE.get();
                    else if (oldMob instanceof Spider && !(oldMob instanceof ChaosSpider)) targetType = ModEntities.CHAOS_SPIDER.get();
                    else if (oldMob instanceof Creeper) targetType = ModEntities.CHAOS_CREEPER.get();
                    else if (oldMob instanceof Skeleton) targetType = ModEntities.CHAOS_SKELETON.get();

                    if (targetType != null) {
                        Mob chaosMob = (Mob) targetType.create(level);
                        if (chaosMob != null) {
                            chaosMob.moveTo(oldMob.getX(), oldMob.getY(), oldMob.getZ(), oldMob.getYRot(), oldMob.getXRot());

                            chaosMob.finalizeSpawn(serverAccessor, level.getCurrentDifficultyAt(chaosMob.blockPosition()),
                                    MobSpawnType.CONVERSION, null, null);

                            level.addFreshEntity(chaosMob);
                            event.setSpawnCancelled(true);
                        }
                    }
                }
            }
        }
    }
    private static BlockState getPlantToPlace(RandomSource random) {
        float chance = random.nextFloat();
        if (chance < 0.10f) { // 10% stellar flower
            return ModBlocks.STELLAR_FLOWER.get().defaultBlockState();
        } else if (chance < 0.85f) { // 75% grass
            return Blocks.GRASS.defaultBlockState();
        } else { // 15% other flowers
            return Blocks.POPPY.defaultBlockState();
        }
    }
}