package net.marsim.chaosmod.item.custom;

import net.marsim.chaosmod.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BlackholeItem extends Item {
    public BlackholeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity blackholeEntity) {
        var level = blackholeEntity.level();
        var pos = blackholeEntity.position();

        if (!level.isClientSide) {

            double range = 5.0;
            AABB area = blackholeEntity.getBoundingBox().inflate(range);
            List<ItemEntity> nearbyItems = level.getEntitiesOfClass(ItemEntity.class, area);

            for (ItemEntity targetItem : nearbyItems) {

                if (targetItem == blackholeEntity) continue;

                Vec3 targetPos = targetItem.position();
                double distance = targetPos.distanceTo(pos);

                // suck-in
                if (distance > 0.2) {
                    Vec3 pullDir = pos.subtract(targetPos).normalize();

                    double strength = 0.07;
                    targetItem.setDeltaMovement(targetItem.getDeltaMovement().add(pullDir.scale(strength)));
                    targetItem.hasImpulse = true;
                }


                if (distance <= 0.5) {
                    ItemStack targetStack = targetItem.getItem();

                    if (targetStack.is(ModItems.DARKLIGHT_BAR.get()) && targetStack.getCount() >= 64) {


                        targetStack.shrink(64);
                        targetItem.setItem(targetStack);
                        if (targetStack.isEmpty()) {
                            targetItem.discard();
                        }

                        stack.shrink(1);
                        blackholeEntity.setItem(stack);
                        if (stack.isEmpty()) {
                            blackholeEntity.discard();
                        }


                        ItemEntity novaFragment = new ItemEntity(level, pos.x, pos.y + 0.2, pos.z,
                                new ItemStack(ModItems.NOVA_FRAGMENT.get(), 1));
                        novaFragment.setDeltaMovement(0, 0.1, 0);
                        level.addFreshEntity(novaFragment);


                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(ParticleTypes.PORTAL, pos.x, pos.y, pos.z, 60, 0.2, 0.2, 0.2, 0.5);
                            serverLevel.sendParticles(ParticleTypes.REVERSE_PORTAL, pos.x, pos.y, pos.z, 30, 0.1, 0.1, 0.1, 0.1);
                        }

                        level.playSound(null, blackholeEntity.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.NEUTRAL, 1.0f, 1.2f);
                        level.playSound(null, blackholeEntity.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 0.4f, 0.1f);

                        return true;
                    }
                }
            }

            if (level.getGameTime() % 4 == 0 && level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SQUID_INK, pos.x, pos.y, pos.z, 2, 0.05, 0.05, 0.05, 0.01);
            }
        }

        return false;
    }
}