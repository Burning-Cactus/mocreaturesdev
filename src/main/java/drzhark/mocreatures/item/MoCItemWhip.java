package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MoCItemWhip extends MoCItem {

    public MoCItemWhip(Item.Properties builder) {
        super(builder);
    }

//    @Override
//    public boolean isFull3D() {
//        return true;
//    }

    public ItemStack onItemRightClick2(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        return itemstack;
    }

    @Override
    public ActionResultType
            onItemUse(ItemUseContext context /*PlayerEntity player, World worldIn, BlockPos pos, Hand hand, EnumFacing side, float hitX, float hitY, float hitZ*/) {

        PlayerEntity player = context.getPlayer();
        World worldIn = context.getWorld();
        Hand hand = context.getHand();
        BlockPos pos = context.getPos();
        Direction side = context.getFace();
        final ItemStack stack = player.getHeldItem(hand);
        int i1 = 0;
        Block block = worldIn.getBlockState(pos).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        if (side != Direction.DOWN && (block1 == Blocks.AIR) && (block != Blocks.AIR) && ((block != Blocks.SPRUCE_SIGN)||(block != Blocks.ACACIA_SIGN)||(block != Blocks.BIRCH_SIGN)||(block != Blocks.DARK_OAK_SIGN)||(block != Blocks.JUNGLE_SIGN)||(block != Blocks.OAK_SIGN))) {
            whipFX(worldIn, pos);
            worldIn.playSound(player, pos, MoCSoundEvents.ENTITY_GENERIC_WHIP, SoundCategory.PLAYERS, 0.5F, 0.4F / ((random.nextFloat() * 0.4F) + 0.8F)); //TODO: What range is the itemRand set to?
            stack.damageItem(1, player, d -> d.sendBreakAnimation(hand));
            List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(player, player.getBoundingBox().expand(12D, 12D, 12D));
            for (int l1 = 0; l1 < list.size(); l1++) {
                Entity entity = list.get(l1);

                if (entity instanceof MoCEntityAnimal) {
                    MoCEntityAnimal animal = (MoCEntityAnimal) entity;
                    if (MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get() && animal.getOwnerId() != null
                            && !player.getName().equals(animal.getOwnerId()) && !MoCTools.isThisPlayerAnOP(player)) {
                        continue;
                    }
                }

                if (entity instanceof MoCEntityBigCat) {
                    MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entity;
                    if (entitybigcat.getIsTamed()) {
                        entitybigcat.setSitting(!entitybigcat.getIsSitting());
                        i1++;
                    } else if ((worldIn.getDifficulty().getId() > 0) && entitybigcat.getIsAdult()) {
                        entitybigcat.setAttackTarget(player);
                    }
                }
                if (entity instanceof MoCEntityHorse) {
                    MoCEntityHorse entityhorse = (MoCEntityHorse) entity;
                    if (entityhorse.getIsTamed()) {
                        if (entityhorse.getRidingEntity() == null) {
                            entityhorse.setSitting(!entityhorse.getIsSitting());
                        } else if (entityhorse.isNightmare()) {
                            entityhorse.setNightmareInt(100);
                        } else if (entityhorse.sprintCounter == 0) {
                            entityhorse.sprintCounter = 1;
                        }
                    }
                }

                if ((entity instanceof MoCEntityKitty)) {
                    MoCEntityKitty entitykitty = (MoCEntityKitty) entity;
                    if ((entitykitty.getKittyState() > 2) && entitykitty.whipeable()) {
                        entitykitty.setSitting(!entitykitty.getIsSitting());
                    }
                }

                if ((entity instanceof MoCEntityWyvern)) {
                    MoCEntityWyvern entitywyvern = (MoCEntityWyvern) entity;
                    if (entitywyvern.getIsTamed() && entitywyvern.getRidingEntity() == null && !entitywyvern.isOnAir()) {
                        entitywyvern.setSitting(!entitywyvern.getIsSitting());
                    }
                }

                if ((entity instanceof MoCEntityPetScorpion)) {
                    MoCEntityPetScorpion petscorpion = (MoCEntityPetScorpion) entity;
                    if (petscorpion.getIsTamed() && petscorpion.getRidingEntity() == null) {
                        petscorpion.setSitting(!petscorpion.getIsSitting());
                    }
                }

                if (entity instanceof MoCEntityOstrich) {
                    MoCEntityOstrich entityostrich = (MoCEntityOstrich) entity;

                    //makes ridden ostrich sprint
                    if (entityostrich.isBeingRidden() && entityostrich.sprintCounter == 0) {
                        entityostrich.sprintCounter = 1;
                    }

                    //toggles hiding of tamed ostriches
                    if (entityostrich.getIsTamed() && entityostrich.getRidingEntity() == null) {
                        entityostrich.setHiding(!entityostrich.getHiding());
                    }
                }
                if (entity instanceof MoCEntityElephant) {
                    MoCEntityElephant entityelephant = (MoCEntityElephant) entity;

                    //makes elephants charge
                    if (entityelephant.isBeingRidden() && entityelephant.sprintCounter == 0) {
                        entityelephant.sprintCounter = 1;
                    }
                }
                
                if (entity instanceof MoCEntityBear) {
                    MoCEntityBear entitybear = (MoCEntityBear) entity;

                    if (entitybear.getIsTamed()) {
                       if (entitybear.getBearState() == 0) {
                               entitybear.setBearState(2);
                           }else {
                               entitybear.setBearState(0);
                           }
                    }
                }
            }

            if (i1 > 6) {
                //entityplayer.triggerAchievement(MoCreatures.Indiana);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    public void whipFX(World world, BlockPos pos) {
        double d = pos.getX() + 0.5F;
        double d1 = pos.getY() + 1.0F;
        double d2 = pos.getZ() + 0.5F;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;
        world.addParticle(ParticleTypes.SMOKE, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.SMOKE, d, d1, d2, 0.0D, 0.0D, 0.0D);
        world.addParticle(ParticleTypes.FLAME, d, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
