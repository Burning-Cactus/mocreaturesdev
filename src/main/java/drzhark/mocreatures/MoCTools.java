package drzhark.mocreatures;

import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.JukeboxTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MoCTools {

    /**
     * spawns tiny slimes
     */
    public static void spawnSlimes(World world, Entity entity) {
        if (!world.isClientSide) {
            //changed so it only spawns 0 - 1 slime, as it now spaws also big slimes
            int var2 = 1 + world.random.nextInt(1);

            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = (i / 2F - 0.5F) * 1 / 4.0F;
                SlimeEntity var6 = new SlimeEntity(EntityType.SLIME, world);
                //var6.setSlimeSize(1);  TODO FIX
                var6.moveTo(entity.getX() + var4, entity.getY() + 0.5D, entity.getZ() + var5, world.random.nextFloat() * 360.0F, 0.0F);
                world.addFreshEntity(var6);
            }
        }
    }

    /**
     * Drops saddle
     */
    public static void dropSaddle(MoCEntityAnimal entity, World world) {
        if (!entity.getIsRideable() || world.isClientSide) {
            return;
        }
        dropCustomItem(entity, world, new ItemStack(MoCItems.HORSESADDLE, 1));
        entity.setRideable(false);
    }

    /**
     * Drops chest block
     */
    public static void dropBags(MoCEntityAnimal entity, World world) {
        if (world.isClientSide) {
            return;
        }
        dropCustomItem(entity, world, new ItemStack(Blocks.CHEST, 1));
    }

    /**
     * Drops item
     */
    public static void dropCustomItem(Entity entity, World world, ItemStack itemstack) {
        if (world.isClientSide) {
            return;
        }

        ItemEntity entityitem = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), itemstack);
        float f3 = 0.05F;
        entityitem.setDeltaMovement((float) world.random.nextGaussian() * f3,
                ((float) world.random.nextGaussian() * f3) + 0.2F,
                (float) world.random.nextGaussian() * f3
        );
        world.addFreshEntity(entityitem);
    }

    public static void bigsmack(Entity entity, Entity entity1, float force) {
        double d = entity.getX() - entity1.getX();
        double d1;
        for (d1 = entity.getZ() - entity1.getZ(); ((d * d) + (d1 * d1)) < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D) {
            d = (Math.random() - Math.random()) * 0.01D;
        }

        float f = MathHelper.sqrt((d * d) + (d1 * d1));
        entity1.setDeltaMovement((entity1.getDeltaMovement().x()/2D)-((d/f)*force),
                (entity1.getDeltaMovement().y()/2D)+force,
                (entity1.getDeltaMovement().z()/2D)-((d1/f)*force)
        );

        if (entity1.getDeltaMovement().y() > force) {
            entity1.setDeltaMovement(entity1.getDeltaMovement().x,
                    force,
                    entity1.getDeltaMovement().z
            );
        }
    }

    public static void buckleMobs(LivingEntity entityattacker, Double dist, World world) {
        List<Entity> list = world.getEntities(entityattacker, entityattacker.getBoundingBox().expandTowards(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entitytarget = list.get(i);
            if (!(entitytarget instanceof LivingEntity) || (entityattacker.isVehicle() && entitytarget == entityattacker.getVehicle())) {
                continue;
            }

            entitytarget.hurt(DamageSource.mobAttack(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, MoCSoundEvents.ENTITY_GENERIC_TUD);
            //todo tuck sound!!
        }
    }

    public static void buckleMobsNotPlayers(LivingEntity entityattacker, Double dist, World world) {
        List<Entity> list = world.getEntities(entityattacker, entityattacker.getBoundingBox().expandTowards(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entitytarget = list.get(i);
            if (!(entitytarget instanceof LivingEntity) || (entitytarget instanceof PlayerEntity)
                    || (entityattacker.isVehicle() && entitytarget == entityattacker.getVehicle())) {
                continue;
            }

            entitytarget.hurt(DamageSource.mobAttack(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, MoCSoundEvents.ENTITY_GENERIC_TUD);
            //todo tuck sound!!
        }
    }

//    public static void spawnNearPlayer(PlayerEntity player, int entityId, int numberToSpawn)//, World world)
//    {
//        ServerWorld world = player.world.getServer().getWorld(player.world.dimension.getType());
//        for (int i = 0; i < numberToSpawn; i++) {
//            LivingEntity entityliving = null;
//            try {
//                Class<? extends LivingEntity> entityClass = MoCreatures.instaSpawnerMap.get(entityId);
//                entityliving = (LivingEntity) entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (entityliving != null) {
//                entityliving.setLocationAndAngles(player.getPosX() - 1, player.getPosY(), player.getPosZ() - 1, player.rotationYaw, player.rotationPitch);
//                world.addEntity(entityliving);
//            }
//        }
//    }

//    public static void spawnNearPlayerbyName(PlayerEntity player, String eName, int numberToSpawn) {
//        ServerWorld world = player.world.getServer().getWorld(player.world.dimension.getType());
//
//        for (int i = 0; i < numberToSpawn; i++) {
//            LivingEntity entityToSpawn = null;
//            try {
//                MoCEntityData entityData = MoCreatures.mocEntityMap.get(eName);
//                Class<? extends LivingEntity> myClass = entityData.getEntityClass();
//                entityToSpawn = (LivingEntity) myClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (entityToSpawn != null) {
//                ILivingEntityData entitylivingdata = null;
//                entityToSpawn.onInitialSpawn(player.world.getDifficultyForLocation(new BlockPos(entityToSpawn)), entitylivingdata); // onSpawnWithEgg
//                entityToSpawn.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), player.rotationYaw, player.rotationPitch);
//                world.addEntity(entityToSpawn);
//            }
//        }
//    }

    public static void playCustomSound(Entity entity, SoundEvent customSound) {
        playCustomSound(entity, customSound, 1.0F);
    }

    public static void playCustomSound(Entity entity, SoundEvent customSound, float volume) {
        entity.playSound(customSound, volume, 1.0F + ((entity.level.random.nextFloat() - entity.level.random.nextFloat()) * 0.2F));
    }

    public static boolean NearMaterialWithDistance(Entity entity, Double double1, Material mat) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockState blockstate = entity.level.getBlockState(new BlockPos(k1, l1, i2));
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getMaterial() == mat)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNearBlockName(Entity entity, Double dist, String blockName) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(dist, dist / 2D, dist);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockState blockstate = entity.level.getBlockState(new BlockPos(k1, l1, i2));

                    if (blockstate.getBlock() != Blocks.AIR) {
                        String nameToCheck = "";
                        nameToCheck = blockstate.getBlock().getRegistryName().toString();//.getBlockName();
                        if (!nameToCheck.equals( "")) {
                            if (nameToCheck.equals(blockName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static JukeboxTileEntity nearJukeBoxRecord(Entity entity, Double dist) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(dist, dist / 2D, dist);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = entity.level.getBlockState(pos);
                    if (!entity.level.isEmptyBlock(pos)) {
                        if (blockstate.getBlock() instanceof JukeboxBlock) {
                            JukeboxTileEntity juky = (JukeboxTileEntity) entity.level.getBlockEntity(pos);
                            return juky;
                        }
                    }
                }
            }
        }
        return null;
    }

//    public static void checkForTwistedEntities(World world) {
//        for (int l = 0; l < world.loadedEntityList.size(); l++) {
//            Entity entity = (Entity) world.loadedEntityList().get(l);
//            if (entity instanceof LivingEntity) {
//                LivingEntity twisted = (LivingEntity) entity;
//                if (twisted.deathTime > 0 && twisted.getRidingEntity() == null && twisted.getHealth() > 0) {
//                    twisted.deathTime = 0;
//                }
//            }
//        }
//    }

    public static double getSqDistanceTo(Entity entity, double i, double j, double k) {
        double l = entity.getX() - i;
        double i1 = entity.getY() - j;
        double j1 = entity.getZ() - k;
        return Math.sqrt((l * l) + (i1 * i1) + (j1 * j1));
    }

    public static int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1, Double yOff) {
        double shortestDistance = -1D;
        double distance = 0D;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(double1.doubleValue(), yOff.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = entity.level.getBlockState(pos);
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getMaterial() == material)) {
                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }
                    }
                }
            }
        }

        if (entity.getX() > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.getZ() > z) {
            z -= 2;
        } else {
            z += 2;
        }
        return (new int[] {x, y, z});
    }

    public static int[] ReturnNearestBlockCoord(Entity entity, Block block1, Double dist) {
        double shortestDistance = -1D;
        double distance = 0D;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(dist, dist, dist);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = entity.level.getBlockState(pos);
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getBlock() == block1)) {
                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }
                    }
                }
            }
        }

        if (entity.getX() > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.getZ() > z) {
            z -= 2;
        } else {
            z += 2;
        }
        return (new int[] {x, y, z});
    }

    public static void MoveCreatureToXYZ(CreatureEntity movingEntity, double x, double y, double z, float f) {
        //TODO works?
        Path pathentity = movingEntity.getNavigation().createPath(x, y, z, 0);
        if (pathentity != null) {
            movingEntity.getNavigation().moveTo(pathentity, f);
        }
    }

    public static void MoveToWater(CreatureEntity entity) {
        int ai[] = MoCTools.ReturnNearestMaterialCoord(entity, Material.WATER, Double.valueOf(20D), 2D);
        if (ai[0] > -1000) {
            MoCTools.MoveCreatureToXYZ(entity, ai[0], ai[1], ai[2], 24F);
        }
    }

    /**
     * Gives angles in the range 0-360 i.e. 361 will be returned like 1
     *
     * @param origAngle
     * @return
     */
    public static float realAngle(float origAngle) {
        return origAngle % 360F;
    }

    public static void SlideEntityToXYZ(Entity entity, int x, int y, int z) {
        if (entity != null) {
            if (entity.getY() < y) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.14999999999999999D, 0));
            }
            if (entity.getX() < x) {
                double d = x - entity.getX();
                if (d > 0.5D) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0.050000000000000003D, 0, 0));
                }
            } else {
                double d1 = entity.getX() - x;
                if (d1 > 0.5D) {
                    entity.setDeltaMovement(entity.getDeltaMovement().subtract(0.050000000000000003D, 0, 0));
                }
            }
            if (entity.getZ() < z) {
                double d2 = z - entity.getZ();
                if (d2 > 0.5D) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0, 0.050000000000000003D));
                }
            } else {
                double d3 = entity.getZ() - z;
                if (d3 > 0.5D) {
                    entity.setDeltaMovement(entity.getDeltaMovement().subtract(0, 0, 0.050000000000000003D));
                }
            }
        }
    }

    public static float distanceToSurface(Entity entity) {
        int i = MathHelper.floor(entity.getX());
        int j = MathHelper.floor(entity.getY());
        int k = MathHelper.floor(entity.getZ());
        BlockState blockstate = entity.level.getBlockState(new BlockPos(i, j, k));
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == Material.WATER) {
            for (int x = 1; x < 64; x++) {
                blockstate = entity.level.getBlockState(new BlockPos(i, j + x, k));
                if (blockstate.getBlock() == Blocks.AIR || blockstate.getMaterial() != Material.WATER) {
                    return x;
                }
            }
        }
        return 0F;
    }

    public static double waterSurfaceAtGivenPosition(double posX, double posY, double posZ, World worldIn) {
        int i = MathHelper.floor(posX);
        int j = MathHelper.floor(posY);
        int k = MathHelper.floor(posZ);
        BlockState blockstate = worldIn.getBlockState(new BlockPos(i, j, k));
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == Material.WATER) {
            for (int x = 1; x < 64; x++) {
                blockstate = worldIn.getBlockState(new BlockPos(i, j + x, k));
                if (blockstate.getBlock() == Blocks.AIR || blockstate.getMaterial() != Material.WATER) {
                    return j + x;
                }
            }
        }
        return 0F;
    }

    public static double waterSurfaceAtGivenEntity(Entity entity) {
        return waterSurfaceAtGivenPosition(entity.getX(), entity.getY(), entity.getZ(), entity.level);
    }

    public static float distanceToSurface(double posX, double posY, double posZ, World worldIn) {
        int i = MathHelper.floor(posX);
        int j = MathHelper.floor(posY);
        int k = MathHelper.floor(posZ);
        BlockState blockstate = worldIn.getBlockState(new BlockPos(i, j, k));
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == Material.WATER) {
            for (int x = 1; x < 64; x++) {
                blockstate = worldIn.getBlockState(new BlockPos(i, j + x, k));
                if (blockstate.getBlock() == Blocks.AIR || blockstate.getMaterial() != Material.WATER) {
                    return x;
                }
            }
        }
        return 0F;
    }

    public static int distanceToFloor(Entity entity) {
        int i = MathHelper.floor(entity.getX());
        int j = MathHelper.floor(entity.getY());
        int k = MathHelper.floor(entity.getZ());
        for (int x = 0; x < 64; x++) {
            Block block = entity.level.getBlockState(new BlockPos(i, j - x, k)).getBlock();
            if (block != Blocks.AIR) {
                return x;
            }
        }
        return 0;
    }

//    public boolean isInsideOfMaterial(Material material, Entity entity) {
//        double d = entity.getPosY() + entity.getEyeHeight();
//        int i = MathHelper.floor(entity.getPosX());
//        int j = MathHelper.floor(MathHelper.floor(d));
//        int k = MathHelper.floor(entity.getPosZ());
//        BlockPos pos = new BlockPos(i, j, k);
//        BlockState blockstate = entity.world.getBlockState(pos);
//        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == material) {
//            float f = Block.LiquidgetLiquidHeightPercent(blockstate.getBlock().getMetaFromState(blockstate)) - 0.1111111F; //TODO: Rework liquid functions
//            float f1 = j + 1 - f;
//            return d < f1;
//        } else {
//            return false;
//        }
//    }

    public static void disorientEntity(Entity entity) {
        double rotD = 0;
        double motD = 0;
        double d = entity.level.random.nextGaussian();
        double d1 = 0.1D * d;
        motD = (0.2D * d1) + ((1.0D - 0.2D) * motD);
        entity.setDeltaMovement(entity.getDeltaMovement().add(motD, 0, motD));
        double d2 = 0.78D * d;
        rotD = (0.125D * d2) + ((1.0D - 0.125D) * rotD);
        entity.yRot += rotD;
        entity.xRot += rotD;
    }

    public static void slowEntity(Entity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.8D, 1.0D, 0.8D));
    }

    public static int colorize(int i) {
        return ~i & 0xf;
    }

//    public int countEntities(Class<? extends LivingEntity> class1, World world) {
//        int i = 0;
//        for (int j = 0; j < world.loadedEntityList.size(); j++) {
//            Entity entity = world.loadedEntityList.get(j);
//            if (class1.isAssignableFrom(entity.getClass())) {
//                i++;
//            }
//        }
//
//        return i;
//    }

    public static float distToPlayer(Entity entity) {
        //TODO
        return 0.0F;
    }

    public static String biomeName(World world, BlockPos pos) { //TODO: Rewrite this after doing world gen
        BiomeManager biomeProvider = world.getBiomeManager();

        Biome biome = biomeProvider.getBiome(pos);
        //TODO works?

//        return biome.getRegistryName().toString();
        return "null";
    }

    public static Biome Biomekind(World world, BlockPos pos) {
        return world.getBiome(pos);
    }

    public static void destroyDrops(Entity entity, double d) {

        if (!MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.destroyDrops.get()) {
            return;
        }

        List<Entity> list = entity.level.getEntities(entity, entity.getBoundingBox().expandTowards(d, d, d));

        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem = (ItemEntity) entity1;
            if ((entityitem.getAge() < 50)) {
                entityitem.remove();
            }
        }
    }

    public static void repelMobs(Entity entity1, Double dist, World world) {
        List<Entity> list = world.getEntities(entity1, entity1.getBoundingBox().expandTowards(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
            if (!(entity instanceof MobEntity)) {
                continue;
            }
            MobEntity entitymob = (MobEntity) entity;
            entitymob.setTarget(null);
            //entitymob.entityLivingToAttack = null;
            entitymob.getNavigation().stop();
        }
    }

    /**
     * Drops the important stuff to get going fast
     *
     * @param world
     * @param entity
     */
    public static void dropGoodies(World world, Entity entity) { //TODO: This method probably won't be necessary when loot table data is in place.
        if (world.isClientSide) {
            return;
        }

        ItemEntity entityitem = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Blocks.OAK_LOG, 16));
        entityitem.setPickUpDelay(10);
        world.addFreshEntity(entityitem);

        ItemEntity entityitem2 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.DIAMOND, 64));
        entityitem2.setPickUpDelay(10);
        world.addFreshEntity(entityitem2);

        ItemEntity entityitem3 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Blocks.PUMPKIN, 6));
        entityitem3.setPickUpDelay(10);
        world.addFreshEntity(entityitem3);

        ItemEntity entityitem4 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Blocks.COBBLESTONE, 64));
        entityitem4.setPickUpDelay(10);
        world.addFreshEntity(entityitem4);

        ItemEntity entityitem5 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.APPLE, 24));
        entityitem5.setPickUpDelay(10);
        world.addFreshEntity(entityitem5);

        ItemEntity entityitem6 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.LEATHER, 64));
        entityitem6.setPickUpDelay(10);
        world.addFreshEntity(entityitem6);

        ItemEntity entityitem7 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(MoCItems.RECORDSHUFFLE, 6));
        entityitem7.setPickUpDelay(10);
        world.addFreshEntity(entityitem7);

        ItemEntity entityitem8 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.IRON_INGOT, 64));
        entityitem8.setPickUpDelay(10);
        world.addFreshEntity(entityitem8);

        ItemEntity entityitem9 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.GOLD_INGOT, 12));
        entityitem9.setPickUpDelay(10);
        world.addFreshEntity(entityitem9);

        ItemEntity entityitem10 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.STRING, 32));
        entityitem10.setPickUpDelay(10);
        world.addFreshEntity(entityitem10);

        ItemEntity entityitem12 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Blocks.POPPY, 6));
        entityitem12.setPickUpDelay(10);
        world.addFreshEntity(entityitem12);

        ItemEntity entityitem13 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.BLAZE_ROD, 12));
        entityitem13.setPickUpDelay(10);
        world.addFreshEntity(entityitem13);

        ItemEntity entityitem14 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.ENDER_PEARL, 12));
        entityitem14.setPickUpDelay(10);
        world.addFreshEntity(entityitem14);

        ItemEntity entityitem15 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.GHAST_TEAR, 12));
        entityitem15.setPickUpDelay(10);
        world.addFreshEntity(entityitem15);

        ItemEntity entityitem16 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Blocks.LAPIS_BLOCK, 2));
        entityitem16.setPickUpDelay(10);
        world.addFreshEntity(entityitem16);

        ItemEntity entityitem17 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.BONE, 12));
        entityitem17.setPickUpDelay(10);
        world.addFreshEntity(entityitem17);

        ItemEntity entityitem18 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(MoCItems.UNICORNHORN, 16));
        entityitem18.setPickUpDelay(10);
        world.addFreshEntity(entityitem18);

        ItemEntity entityitem19 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Blocks.FIRE, 32));
        entityitem19.setPickUpDelay(10);
        world.addFreshEntity(entityitem19);

        ItemEntity entityitem20 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(MoCItems.ESSENCEDARKNESS, 6));
        entityitem20.setPickUpDelay(10);
        world.addFreshEntity(entityitem20);

        ItemEntity entityitem21 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(MoCItems.ESSENCEUNDEAD, 6));
        entityitem21.setPickUpDelay(10);
        world.addFreshEntity(entityitem21);

        ItemEntity entityitem22 = new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(MoCItems.ESSENCEFIRE, 6));
        entityitem22.setPickUpDelay(10);
        world.addFreshEntity(entityitem22);

        ItemEntity entityitem23 =
                new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Blocks.WHITE_WOOL, 6));
        entityitem23.setPickUpDelay(10);
        world.addFreshEntity(entityitem23);

    }

    public static boolean mobGriefing(World world) {
        return world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    public static void DestroyBlast(Entity entity, double d, double d1, double d2, float f, boolean flag) {
        PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
        entity.level.playSound(player, d, d1, d2, MoCSoundEvents.ENTITY_GENERIC_DESTROY, SoundCategory.HOSTILE, 4F,
                (1.0F + ((entity.level.random.nextFloat() - entity.level.random.nextFloat()) * 0.2F)) * 0.7F);

        boolean mobGriefing = mobGriefing(entity.level);

        HashSet<BlockPos> hashset = new HashSet<BlockPos>();
        float f1 = f;
        int i = 16;
        for (int j = 0; j < i; j++) {
            for (int l = 0; l < i; l++) {
                label0: for (int j1 = 0; j1 < i; j1++) {
                    if ((j != 0) && (j != (i - 1)) && (l != 0) && (l != (i - 1)) && (j1 != 0) && (j1 != (i - 1))) {
                        continue;
                    }
                    double d3 = ((j / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d4 = ((l / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d5 = ((j1 / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d6 = Math.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    float f2 = f * (0.7F + (entity.level.random.nextFloat() * 0.6F));
                    double d8 = d;
                    double d10 = d1;
                    double d12 = d2;
                    float f3 = 0.3F;
                    float f4 = 5F;
                    do {
                        if (f2 <= 0.0F) {
                            continue label0;
                        }
                        int k5 = MathHelper.floor(d8);
                        int l5 = MathHelper.floor(d10);
                        int i6 = MathHelper.floor(d12);
                        BlockPos pos = new BlockPos(k5, l5, i6);
                        BlockState blockstate = entity.level.getBlockState(pos);
                        if (blockstate.getBlock() != Blocks.AIR) {
                            f4 = blockstate.getDestroySpeed(entity.level, pos);
                            f2 -= (blockstate.getBlock().getExplosionResistance() + 0.3F) * (f3 / 10F);
                        }
                        if ((f2 > 0.0F) && (d10 > entity.getY()) && (f4 < 3F)) {
                            hashset.add(pos);
                        }
                        d8 += d3 * f3;
                        d10 += d4 * f3;
                        d12 += d5 * f3;
                        f2 -= f3 * 0.75F;
                    } while (true);
                }

            }

        }

        f *= 2.0F;
        if (!entity.level.isClientSide()) {
            int k = MathHelper.floor(d - f - 1.0D);
            int i1 = MathHelper.floor(d + f + 1.0D);
            int k1 = MathHelper.floor(d1 - f - 1.0D);
            int l1 = MathHelper.floor(d1 + f + 1.0D);
            int i2 = MathHelper.floor(d2 - f - 1.0D);
            int j2 = MathHelper.floor(d2 + f + 1.0D);
            List<Entity> list = entity.level.getEntities(entity, new AxisAlignedBB(k, k1, i2, i1, l1, j2));
            Vector3d vec3d = new Vector3d(d, d1, d2);
            for (Entity value : list) {
                Entity entity1 = (Entity) value;
                double d7 = entity1.distanceTo(entity) / f;
                if (d7 > 1.0D) {
                    continue;
                }
                double d9 = entity1.getX() - d;
                double d11 = entity1.getY() - d1;
                double d13 = entity1.getZ() - d2;
                double d15 = MathHelper.sqrt((d9 * d9) + (d11 * d11) + (d13 * d13));
                d9 /= d15;
                d11 /= d15;
                d13 /= d15;
                double d17 =  1.0D; //entity.world.getBlockDensity(vec3d, entity1.getBoundingBox()); TODO: Find replacement method
                double d19 = (1.0D - d7) * d17;

                //attacks entities in server
                if (!(entity1 instanceof MoCEntityOgre)) {
                    entity1.hurt(DamageSource.GENERIC, (int) (((((d19 * d19) + d19) / 2D) * 3D * f) + 1.0D));
                    double d21 = d19;
                    entity1.setDeltaMovement(entity1.getDeltaMovement().add(d9 * d21, d11 * d21, d13 * d21));
                }
            }
        }

        f = f1;
        ArrayList<BlockPos> arraylist = new ArrayList<BlockPos>();
        arraylist.addAll(hashset);

        for (int l2 = arraylist.size() - 1; l2 >= 0; l2--) {
            BlockPos chunkposition = arraylist.get(l2);
            BlockState blockstate = entity.level.getBlockState(chunkposition);
            for (int j5 = 0; j5 < 5; j5++) {
                double d14 = chunkposition.getX() + entity.level.random.nextFloat();
                double d16 = chunkposition.getY() + entity.level.random.nextFloat();
                double d18 = chunkposition.getZ() + entity.level.random.nextFloat();
                double d20 = d14 - d;
                double d22 = d16 - d1;
                double d23 = d18 - d2;
                double d24 = MathHelper.sqrt((d20 * d20) + (d22 * d22) + (d23 * d23));
                d20 /= d24;
                d22 /= d24;
                d23 /= d24;
                double d25 = 0.5D / ((d24 / f) + 0.10000000000000001D);
                d25 *= (entity.level.random.nextFloat() * entity.level.random.nextFloat()) + 0.3F;
                d25--;
                d20 *= d25;
                d22 *= d25 - 1.0D;
                d23 *= d25;

                /**
                 * shows explosion on clients!
                 */
                if (entity.level.isClientSide()) {
                    entity.level.addParticle(ParticleTypes.EXPLOSION, (d14 + (d * 1.0D)) / 2D, (d16 + (d1 * 1.0D)) / 2D,
                            (d18 + (d2 * 1.0D)) / 2D, d20, d22, d23);
                    entity.setDeltaMovement(entity.getDeltaMovement().subtract(0.0010000000474974511D, 0.0010000000474974511D, 0));
                }

            }

            //destroys blocks on server!
            if (mobGriefing && (!entity.level.isClientSide()) && blockstate.getBlock() != Blocks.AIR) {
                BlockEvent.BreakEvent event = null;
                if (!entity.level.isClientSide) {
                    /*try {
                        event =
                                new BlockEvent.BreakEvent(entity.world, chunkposition, blockstate, FakePlayerFactory.get(
                                        DimensionManager.getWorld(entity.world.getServer(), entity.world.dimension.getType(), false, false), MoCreatures.MOCFAKEPLAYER));
                    } catch (Throwable t) {
                    }*/
                }
                if (event != null && !event.isCanceled()) {
//                    blockstate.getBlock().dropBlockAsItemWithChance(entity.world, chunkposition, blockstate, 0.3F, 1);
                    entity.level.setBlockAndUpdate(chunkposition, Blocks.AIR.defaultBlockState());
                    // pass explosion instance to fix BlockTNT NPE's
                    Explosion explosion =
                            new Explosion(entity.level, null, chunkposition.getX(), chunkposition.getY(), chunkposition.getZ(), 3f, false, Explosion.Mode.BREAK);
                    blockstate.getBlock().onBlockExploded(blockstate, entity.level, chunkposition, explosion);
                }
            }
        }

        //sets world on fire on server
        if (mobGriefing && (FMLEnvironment.dist == Dist.DEDICATED_SERVER) && flag) {
            for (int i3 = arraylist.size() - 1; i3 >= 0; i3--) {
                BlockPos chunkposition1 = arraylist.get(i3);
                BlockState blockstate = entity.level.getBlockState(chunkposition1);
                if ((blockstate.getBlock() == Blocks.AIR) && (entity.level.random.nextInt(8) == 0)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.level.isClientSide) {
                        event =
                                new BlockEvent.BreakEvent(entity.level, chunkposition1, blockstate, FakePlayerFactory.get(
                                        (ServerWorld) entity.level, MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.level.setBlock(chunkposition1, Blocks.FIRE.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

//    public static int despawnVanillaAnimals(World world) {
//        return despawnVanillaAnimals(world, null);
//    }

//    @SuppressWarnings("rawtypes")
//    public static int despawnVanillaAnimals(World world, List[] classList) {
//        int count = 0;
//        for (int j = 0; j < world.loadedEntityList.size(); j++) {
//            Entity entity = (Entity) world.loadedEntityList.get(j);
//            if ((entity instanceof LivingEntity)
//                    && (entity instanceof CowEntity || entity instanceof SheepEntity || entity instanceof PigEntity
//                            || entity instanceof ChickenEntity || entity instanceof SquidEntity || entity instanceof WolfEntity)) {
//                count += entityDespawnCheck(world, (LivingEntity) entity);
//            }
//        }
//        return count;
//    }
            //TODO: Fix these despawn methods
//    protected static int entityDespawnCheck(World world, LivingEntity entityliving)//to keep a copy
//    {
//        int count = 0;
//        PlayerEntity entityplayer = world.getClosestPlayer(entityliving, -1D);
//        if (entityplayer != null) //entityliving.canDespawn() &&
//        {
//            double d = ((Entity) (entityplayer)).getPosX() - entityliving.getPosX();
//            double d1 = ((Entity) (entityplayer)).getPosY() - entityliving.getPosY();
//            double d2 = ((Entity) (entityplayer)).getPosZ() - entityliving.getPosZ();
//            double d3 = d * d + d1 * d1 + d2 * d2;
//            if (d3 > 16384D) {
//                entityliving.remove();
//                count++;
//            }
//
//            if (entityliving.getIdleTime() > 600 && world.rand.nextInt(800) == 0) {
//                if (d3 < 1024D) {
//                    //TODO test!
//                    entityliving.attackEntityFrom(DamageSource.causeMobDamage(null), 0);
//                } else {
//                    entityliving.remove();
//                    count++;
//                }
//            }
//        }
//        return count;
//    }

    //apparently not needed anymore since vanilla does sync mounts?
    /**
     * Forces a data sync between server/client. currently used to syncrhonize
     * mounts
     */
    /*public static void forceDataSync(IMoCEntity entityMoCreature) {
        if (entityMoCreature.updateMount() && ((Entity) entityMoCreature).getRidingEntity() != null) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAttachedEntity(((Entity) entityMoCreature).getEntityId(),
                    ((Entity) entityMoCreature).getRidingEntity().getEntityId()), new TargetPoint(
                    ((Entity) entityMoCreature).getRidingEntity().world.provider.getDimensionType().getId(), ((Entity) entityMoCreature).getRidingEntity().posX,
                    ((Entity) entityMoCreature).getRidingEntity().posY, ((Entity) entityMoCreature).getRidingEntity().posZ, 64));
        }
    }*/

    public static void updatePlayerArmorEffects(PlayerEntity player) {
        ItemStack mystack[] = new ItemStack[4];
        mystack[0] = player.getItemBySlot(EquipmentSlotType.FEET); //boots
        mystack[1] = player.getItemBySlot(EquipmentSlotType.LEGS); //legs
        mystack[2] = player.getItemBySlot(EquipmentSlotType.CHEST); //plate
        mystack[3] = player.getItemBySlot(EquipmentSlotType.HEAD); //helmet

        //full scorpion cave armor set, enable night vision
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSCAVE && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSCAVE && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATECAVE
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETCAVE) {
            player.addEffect(new EffectInstance(Effects.NIGHT_VISION, 300, 0));
            return;
        }

        //full scorpion nether armor set, enable fire resistance
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSNETHER && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSNETHER && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATENETHER
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETNETHER) {
            player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0));
            return;
        }

        //full scorpion frost armor set, enable water breathing
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSFROST && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSFROST && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATEFROST
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETFROST) {
            player.addEffect(new EffectInstance(Effects.WATER_BREATHING, 300, 0));
            return;
        }

        //full scorpion armor set, regeneration effect
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSDIRT && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSDIRT && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATEDIRT
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETDIRT) {
            player.addEffect(new EffectInstance(Effects.REGENERATION, 70, 0));
            return;
        }
    }

    /**
     * Finds a random block around the entity and returns the block's ID will
     * destroy the block in the process the block will be the top one of that
     * layer, without any other block around it
     *
     * @param entity = the Entity around which the block is searched
     * @param distance = the distance around the entity used to look for the
     *        block
     * @return
     */
    public static int destroyRandomBlock(Entity entity, double distance) {
        int l = (int) (distance * distance * distance);
        for (int i = 0; i < l; i++) {
            int x = (int) (entity.getX() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getY() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 4));
            int z = (int) (entity.getZ() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState blockstate = entity.level.getBlockState(pos.above());
            BlockState blockstate1 = entity.level.getBlockState(pos);

            if (blockstate.getBlock() != Blocks.AIR && blockstate1.getBlock() == Blocks.AIR) {
                if (mobGriefing(entity.level)) {
                    blockstate1 = entity.level.getBlockState(pos);
                    BlockEvent.BreakEvent event = null;
                    if (!entity.level.isClientSide) {
                        event =
                                new BlockEvent.BreakEvent(entity.level, pos.above(), blockstate, FakePlayerFactory.get((ServerWorld) entity.level,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                }
                return Block.getId(blockstate);
            }
        }
        return 0;
    }

    public static BlockState destroyRandomBlockWithIBlockState(Entity entity, double distance) {
        int l = (int) (distance * distance * distance);
        for (int i = 0; i < l; i++) {
            int x = (int) (entity.getX() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getY() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            int z = (int) (entity.getZ() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState stateAbove = entity.level.getBlockState(pos.above());
            BlockState stateTarget = entity.level.getBlockState(pos);

            if (pos.getY() == (int) entity.getY() - 1D && (pos.getX() == (int) Math.floor(entity.getX()) && pos.getZ() == (int) Math.floor(entity.getZ()))) {
                continue;
            }
            if (stateTarget.getBlock() != Blocks.AIR && stateTarget.getBlock() != Blocks.WATER && stateTarget.getBlock() != Blocks.BEDROCK
                    && stateAbove.getBlock() == Blocks.AIR) // ignore bedrock
            {
                if (mobGriefing(entity.level)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.level.isClientSide) {
                        event =
                                new BlockEvent.BreakEvent(entity.level, pos, stateTarget, FakePlayerFactory.get((ServerWorld) entity.level,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

                    } else {
                        stateTarget = null;
                    }
                }
                if (stateTarget != null) {
                    return stateTarget;
                }
            }
        }
        return null;
    }

    /**
     * Finds a random block around the entity and returns the coordinates the
     * block will be the top one of that layer, without any other block around
     * it
     *
     * @param entity = the Entity around which the block is searched
     * @param distance = the distance around the entity used to look for the
     *        block
     * @return
     */
    public static int[] getRandomBlockCoords(Entity entity, double distance) {
        int tempX = -9999;
        int tempY = -1;
        int tempZ = -1;
        int ii = (int) (distance * distance * (distance / 2));
        for (int i = 0; i < ii; i++) {
            int x = (int) (entity.getX() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getY() + entity.level.random.nextInt((int) (distance / 2)) - (int) (distance / 4));
            int z = (int) (entity.getZ() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState blockstate1 = entity.level.getBlockState(pos.above()); // +Y
            BlockState blockstate2 = entity.level.getBlockState(pos);
            BlockState blockstate3 = entity.level.getBlockState(pos.east()); // +X
            BlockState blockstate4 = entity.level.getBlockState(pos.west()); // -X
            BlockState blockstate5 = entity.level.getBlockState(pos.below()); // -Y
            BlockState blockstate6 = entity.level.getBlockState(pos.south()); // -Z
            BlockState blockstate7 = entity.level.getBlockState(pos.north()); // +Z

            float tX = x - (float) entity.getX();
            float tY = y - (float) entity.getY();
            float tZ = z - (float) entity.getZ();
            float spawnDist = tX * tX + tY * tY + tZ * tZ;

            if (allowedBlock(Block.getId(blockstate1))
                    && (blockstate2.getBlock() == Blocks.AIR || blockstate3.getBlock() == Blocks.AIR || blockstate4.getBlock() == Blocks.AIR
                            || blockstate5.getBlock() == Blocks.AIR || blockstate6.getBlock() == Blocks.AIR || blockstate7.getBlock() == Blocks.AIR)
                    & spawnDist > 100F) {
                tempX = x;
                tempY = y;
                tempZ = z;
                break;
            }
        }
        return (new int[] {tempX, tempY, tempZ});
    }

    public static BlockPos getRandomBlockPos(Entity entity, double distance) {
        int tempX = -9999;
        int tempY = -1;
        int tempZ = -1;
        int ii = (int) (distance * distance * (distance / 2));
        for (int i = 0; i < ii; i++) {
            int x = (int) (entity.getX() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getY() + entity.level.random.nextInt((int) (distance / 2)) - (int) (distance / 4));
            int z = (int) (entity.getZ() + entity.level.random.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState blockstate1 = entity.level.getBlockState(pos.above()); // +Y
            BlockState blockstate2 = entity.level.getBlockState(pos);
            BlockState blockstate3 = entity.level.getBlockState(pos.east()); // +X
            BlockState blockstate4 = entity.level.getBlockState(pos.west()); // -X
            BlockState blockstate5 = entity.level.getBlockState(pos.below()); // -Y
            BlockState blockstate6 = entity.level.getBlockState(pos.south()); // -Z
            BlockState blockstate7 = entity.level.getBlockState(pos.north()); // +Z

            float tX = x - (float) entity.getX();
            float tY = y - (float) entity.getY();
            float tZ = z - (float) entity.getZ();
            float spawnDist = tX * tX + tY * tY + tZ * tZ;

            if (allowedBlock(Block.getId(blockstate1))
                    && (blockstate2.getBlock() == Blocks.AIR || blockstate3.getBlock() == Blocks.AIR || blockstate4.getBlock() == Blocks.AIR
                            || blockstate5.getBlock() == Blocks.AIR || blockstate6.getBlock() == Blocks.AIR || blockstate7.getBlock() == Blocks.AIR)
                    & spawnDist > 100F) {
                tempX = x;
                tempY = y;
                tempZ = z;
                break;
            }
        }
        return new BlockPos(MathHelper.floor(tempX), MathHelper.floor(tempY), MathHelper.floor(tempZ));
    }

    public static boolean allowedBlock(int ID) {
        return ID != 0 && ID != 7 //bedrock
                && ID != 8 //water
                && ID != 9 //water
                && ID != 10//lava
                && ID != 11//lava
                && ID != 23//dispenser
                && ID != 37//plant
                && ID != 38//plant
                && ID != 50//torch
                && ID != 51//fire
                && ID != 54//chest
                && (ID < 63 || ID > 77) && ID != 95//lockedchest
                && ID != 90//portal
                && ID != 93//redstone
                && ID != 94//redstone
                && ID < 134;//the rest
    }

    /**
     * Method called to tame an entity, it will check that the player has slots
     * for taming, increase the taming count of the player, add the
     * player.getName() as the owner of the entity, and name the entity.
     *
     * @param ep
     * @param storedCreature
     * @return
     */
    public static boolean tameWithName(PlayerEntity ep, IMoCTameable storedCreature) {
        if (ep == null) {
            return false;
        }

        if (MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get()) {
            if (storedCreature == null) {
                ep.sendMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                        + "The stored creature is NULL and could not be created. Report to admin."), Util.NIL_UUID);
                return false;
            }
            int max = 0;
            max = MoCConfig.COMMON_CONFIG.OWNERSHIP.maxTamedPerPlayer.get();
            // only check count for new pets as owners may be changing the name
            if (!MoCreatures.instance.mapData.isExistingPet(ep.getUUID(), storedCreature)) {
                int count = MoCTools.numberTamedByPlayer(ep);
                if (isThisPlayerAnOP(ep)) {
                    max = MoCConfig.COMMON_CONFIG.OWNERSHIP.maxTamedPerOP.get();
                }
                if (count >= max) {
                    String message = "\2474" + ep.getName() + " can not tame more creatures, limit of " + max + " reached";
                    ep.sendMessage(new TranslationTextComponent(message), Util.NIL_UUID);
                    return false;
                }
            }
        }

        storedCreature.setOwnerId(ep.getUUID()); // ALWAYS SET OWNER. Required for our new pet save system.
//        MoCMessageHandler.INSTANCE.sendTo(new MoCMessageNameGUI(((Entity) storedCreature).getEntityId()), (ServerPlayerEntity) ep, NetworkDirection.PLAY_TO_CLIENT);
        storedCreature.setTamed(true);
        // Required to update petId data for pet amulets
        if (MoCreatures.instance.mapData != null && storedCreature.getOwnerPetId() == -1) {
            MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
        }
        return true;
    }

    public static int numberTamedByPlayer(PlayerEntity ep) {
        if (MoCreatures.instance.mapData != null) {
            if (MoCreatures.instance.mapData.getPetData(ep.getUUID()) != null) {
                return MoCreatures.instance.mapData.getPetData(ep.getUUID()).getTamedList().size();
            }
        }
        return 0;
    }

    /**
     * Destroys blocks in front of entity
     *
     * @param entity
     * @param distance: used to calculate the distance where the target block is
     *        located
     * @param strength: int 1 - 3. Checked against block hardness, also used to
     *        calculate how many blocks are recovered
     * @param height: how many rows of blocks are destroyed in front of the
     *        entity
     * @return the count of blocks destroyed
     */
    public static int destroyBlocksInFront(Entity entity, double distance, int strength, int height) {
        if (strength == 0) {
            return 0;
        }
        int count = 0;
        float strengthF = strength;
        double newPosX = entity.getX() - (distance * Math.cos((MoCTools.realAngle(entity.yRot - 90F)) / 57.29578F));
        double newPosZ = entity.getZ() - (distance * Math.sin((MoCTools.realAngle(entity.yRot - 90F)) / 57.29578F));
        double newPosY = entity.getY();
        int x = MathHelper.floor(newPosX);
        int y = MathHelper.floor(newPosY);
        int z = MathHelper.floor(newPosZ);

        for (int i = 0; i < height; i++) {
            BlockPos pos = new BlockPos(x, y + i, z);
            BlockState blockstate = entity.level.getBlockState(pos);
            if (blockstate.getBlock() != Blocks.AIR) {
                if (blockstate.getDestroySpeed(entity.level, pos) <= strengthF) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.level.isClientSide) {
                        event =
                                new BlockEvent.BreakEvent(entity.level, pos, blockstate, FakePlayerFactory.get((ServerWorld) entity.level,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
//                        blockstate.getBlock().dropBlockAsItemWithChance(entity.world, pos, blockstate, 0.20F * strengthF, 1);
                        entity.level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                        if (entity.level.random.nextInt(3) == 0) {
                            playCustomSound(entity, MoCSoundEvents.ENTITY_GOLEM_WALK);
                            count++; //only counts recovered blocks
                        }
                    }
                }
            }
        }
        return count;
    }
    //TODO: Re-implement after adding animal chests back
//    public static void dropInventory(Entity entity, MoCAnimalChest animalchest) {
//        if (animalchest == null || entity.world.isRemote) {
//            return;
//        }
//
//        int i = MathHelper.floor(entity.getPosX());
//        int j = MathHelper.floor(entity.getBoundingBox().minY);
//        int k = MathHelper.floor(entity.getPosZ());
//
//        for (int l = 0; l < animalchest.getSizeInventory(); l++) {
//            ItemStack itemstack = animalchest.getStackInSlot(l);
//            if (itemstack.isEmpty()) {
//                continue;
//            }
//            float f = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
//            float f1 = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
//            float f2 = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
//            float f3 = 0.05F;
//
//            ItemEntity entityitem = new ItemEntity(entity.world, i + f, j + f1, k + f2, itemstack);
//            entityitem.setMotion((float) entity.world.rand.nextGaussian() * f3, ((float) entity.world.rand.nextGaussian() * f3) + 0.2F, (float) entity.world.rand.nextGaussian() * f3);
//            entity.world.addEntity(entityitem);
//            animalchest.setInventorySlotContents(l, ItemStack.EMPTY);
//        }
//    }

    /**
     * Drops an amulet with the stored information of the entity passed
     *
     * @param entity
     */
    public static void dropHorseAmulet(MoCEntityTameableAnimal entity) {
        if (!entity.level.isClientSide) {
            ItemStack stack = getProperAmulet(entity);
            if (stack == null) {
                return;
            }
            if (stack.getTag() == null) {
                stack.setTag(new CompoundNBT());
            }
            CompoundNBT nbtt = stack.getTag();
            PlayerEntity epOwner = entity.level.getPlayerByUUID(entity.getOwnerId());

            try {
                nbtt.putString("SpawnClass", "WildHorse");
                nbtt.putFloat("Health", entity.getHealth());
                nbtt.putInt("Edad", entity.getEdad());
                nbtt.putString("Name", entity.getPetName());
                nbtt.putBoolean("Rideable", entity.getIsRideable());
                nbtt.putInt("Armor", entity.getArmorType());
                nbtt.putInt("CreatureType", entity.getSubType());
                nbtt.putBoolean("Adult", entity.getIsAdult());
                nbtt.putString("OwnerName", epOwner != null ? epOwner.getName().toString() : "");
                if (entity.getOwnerId() != null) {
                    nbtt.putUUID("OwnerUUID", entity.getOwnerId());
                }
                nbtt.putInt("PetId", entity.getOwnerPetId());
                int amuletType = 1;
                if (stack.getItem() == MoCItems.PETAMULETFULL) {
                    amuletType = 2;
                } else if (stack.getItem() == MoCItems.AMULETGHOSTFULL) {
                    amuletType = 3;
                }
                nbtt.putBoolean("Ghost", amuletType == 3);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (epOwner != null && epOwner.inventory.getFreeSlot() != -1) // don't attempt to set if player inventory is full
            {
                epOwner.inventory.add(stack);
            } else {
                ItemEntity entityitem = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
                entityitem.setPickUpDelay(20);
                entity.level.addFreshEntity(entityitem);
            }
        }
    }

    /**
     * Drops a new amulet/fishnet with the stored information of the entity
     * @param player 
     */
    public static void dropAmulet(IMoCEntity entity, int amuletType, PlayerEntity player) {
        if (!player.level.isClientSide) {
            ItemStack stack = new ItemStack(MoCItems.FISHNETFULL, 1);
            if (amuletType == 2) {
                stack = new ItemStack(MoCItems.PETAMULETFULL, 1);
            }
            if (amuletType == 3) {
                stack = new ItemStack(MoCItems.AMULETGHOSTFULL, 1);
            }
            if (stack.getTag() == null) {
                stack.setTag(new CompoundNBT());
            }
            CompoundNBT nbtt = stack.getTag();
            try {
//                final EntityEntry entry = EntityRegistry.getEntry((Class<? extends Entity>) entity.getClass());
//                final String petClass = entry.getName().replace(MoCConstants.MOD_PREFIX, "");
//                nbtt.putString("SpawnClass", petClass);
                nbtt.putUUID("OwnerUUID", player.getUUID());
                nbtt.putString("OwnerName", player.getName().toString());
                nbtt.putFloat("Health", ((LivingEntity) entity).getHealth());
                nbtt.putInt("Edad", entity.getEdad());
                nbtt.putString("Name", entity.getPetName());
                nbtt.putInt("CreatureType", entity.getSubType());
                nbtt.putBoolean("Adult", entity.getIsAdult());
                nbtt.putInt("PetId", entity.getOwnerPetId());
                nbtt.putBoolean("Ghost", amuletType == 3 ? true : false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!player.inventory.add(stack)) {
                ItemEntity entityitem =
                        new ItemEntity(((LivingEntity) entity).level, ((LivingEntity) entity).getX(), ((LivingEntity) entity).getY(),
                                ((LivingEntity) entity).getZ(), stack);
                entityitem.setPickUpDelay(20);
                ((LivingEntity) entity).level.addFreshEntity(entityitem);
            }
        }
    }

    /**
     * Returns the right full amulet based on the MoCEntityAnimal passed
     *
     * @param entity
     * @return
     */
    public static ItemStack getProperAmulet(MoCEntityAnimal entity) {
        if (entity instanceof MoCEntityHorse) {
            if (entity.getSubType() == 26 || entity.getSubType() == 27 || entity.getSubType() == 28) {
                return new ItemStack(MoCItems.AMULETBONEFULL, 1);
            }
            if (entity.getSubType() > 47 && entity.getSubType() < 60) {
                return new ItemStack(MoCItems.AMULETFAIRYFULL, 1);
            }
            if (entity.getSubType() == 39 || entity.getSubType() == 40) {
                return new ItemStack(MoCItems.AMULETPEGASUSFULL, 1);
            }
            if (entity.getSubType() == 21 || entity.getSubType() == 22) {
                return new ItemStack(MoCItems.AMULETGHOSTFULL, 1);
            }
        }
        return null;
    }

    /**
     * Returns the right full empty based on the MoCEntityAnimal passed. Used
     * when the amulet empties its contents
     *
     * @param entity
     * @return
     */
    public static ItemStack getProperEmptyAmulet(MoCEntityAnimal entity) {
        if (entity instanceof MoCEntityHorse) {
            if (entity.getSubType() == 26 || entity.getSubType() == 27 || entity.getSubType() == 28) {
                return new ItemStack(MoCItems.AMULETBONE, 1);
            }
            if (entity.getSubType() > 49 && entity.getSubType() < 60) {
                return new ItemStack(MoCItems.AMULETFAIRY, 1);
            }
            if (entity.getSubType() == 39 || entity.getSubType() == 40) {
                return new ItemStack(MoCItems.AMULETPEGASUS, 1);
            }
            if (entity.getSubType() == 21 || entity.getSubType() == 22) {
                return new ItemStack(MoCItems.AMULETGHOST, 1);
            }
        }
        return null;
    }

    /*public static int countPlayersInDimension(ServerWorld world, DimensionType dimension) {
        int playersInDimension = 0;
        for (int j = 0; j < world.getPlayers().size(); ++j) {
            ServerPlayerEntity entityplayermp = (ServerPlayerEntity) world.getPlayers().get(j);

            if (entityplayermp.dimension.equals(dimension)) {
                playersInDimension++;
            }
        }
        return playersInDimension;
    }*/

    public static boolean isThisPlayerAnOP(PlayerEntity player) {
        if (player.level.isClientSide) {
            return false;
        }

        return player.level.getServer().getPlayerList().isOp(player.getGameProfile());
    }

    public static void spawnMaggots(World world, Entity entity) {
        if (!world.isClientSide) {
            int var2 = 1 + world.random.nextInt(4);
            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = (((float)(i / 2)) - 0.5F) * 1 / 4.0F;
                MoCEntityMaggot maggot = new MoCEntityMaggot(MoCEntities.MAGGOT, world);
                maggot.moveTo(entity.getX() + var4, entity.getY() + 0.5D, entity.getZ() + var5, world.random.nextFloat() * 360.0F, 0.0F);
                world.addFreshEntity(maggot);
            }
        }
    }

    public static void getPathToEntity(LivingEntity creatureToMove, Entity entityTarget, float f) {
        Path pathentity = ((MobEntity)creatureToMove).getNavigation().createPath(entityTarget, 0);
        if (pathentity != null && f < 12F) {
            ((MobEntity)creatureToMove).getNavigation().moveTo(pathentity, 1.0D); //TODO check if 1.0D is proper speed.
        }
    }

    public static void runLikeHell(LivingEntity runningEntity, Entity boogey) {
        double d = runningEntity.getX() - boogey.getX();
        double d1 = runningEntity.getZ() - boogey.getZ();
        double d2 = Math.atan2(d, d1);
        d2 += (runningEntity.level.random.nextFloat() - runningEntity.level.random.nextFloat()) * 0.75D;
        double d3 = runningEntity.getX() + (Math.sin(d2) * 8D);
        double d4 = runningEntity.getZ() + (Math.cos(d2) * 8D);
        int i = MathHelper.floor(d3);
        int j = MathHelper.floor(runningEntity.getBoundingBox().minY);
        int k = MathHelper.floor(d4);
        int l = 0;
        do {
            if (l >= 16) {
                break;
            }
            int i1 = (i + runningEntity.level.random.nextInt(4)) - runningEntity.level.random.nextInt(4);
            int j1 = (j + runningEntity.level.random.nextInt(3)) - runningEntity.level.random.nextInt(3);
            int k1 = (k + runningEntity.level.random.nextInt(4)) - runningEntity.level.random.nextInt(4);
            BlockPos pos = new BlockPos(i1, j1, k1);
            if ((j1 > 4)
                    && ((runningEntity.level.getBlockState(pos).getBlock() == Blocks.AIR) || (runningEntity.level.getBlockState(pos).getBlock() == Blocks.SNOW))
                    && (runningEntity.level.getBlockState(pos.below()).getBlock() != Blocks.AIR)) {
                ((MobEntity) runningEntity).getNavigation().moveTo(i1, j1, k1, 1.0D);//TODO check if 1D speed is ok
                break;
            }
            l++;
        } while (true);
    }

    /**
     * Finds a near vulnerable player and poisons it if the player is in the
     * water and not riding anything
     *
     * @param poisoner
     * @param needsToBeInWater: the target needs to be in water for poison to be
     *        successful?
     * @return true if was able to poison the player
     */
    public static boolean findNearPlayerAndPoison(Entity poisoner, boolean needsToBeInWater) {
        PlayerEntity entityplayertarget = poisoner.level.getNearestPlayer(poisoner, 2D);
        if (entityplayertarget != null && (!needsToBeInWater || entityplayertarget.isInWater())
                && poisoner.distanceTo(entityplayertarget) < 2.0F && !entityplayertarget.abilities.invulnerable) {
            if (entityplayertarget.getVehicle() != null && entityplayertarget.getVehicle() instanceof BoatEntity) {
                //don't poison players on boats
            } else {
                MoCreatures.poisonPlayer(entityplayertarget);
                entityplayertarget.addEffect(new EffectInstance(Effects.POISON, 120, 0));
                return true;
            }
        }
        return false;
    }

    public static boolean isTamed(Entity entity) {
        if (entity instanceof TameableEntity) {
            if (((TameableEntity) entity).isTame()) {
                return true;
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        entity.saveWithoutId(nbt);
        if (nbt != null) {
            if (nbt.contains("Owner") && !nbt.getString("Owner").equals("")) {
                return true; // ignore
            }
            if (nbt.contains("Tamed") && nbt.getBoolean("Tamed")) {
                return true; // ignore
            }
        }
        return false;
    }

    /**
     * Throws stone at entity
     *
     * @param targetEntity
     */
    public static void throwStone(Entity throwerEntity, Entity targetEntity, BlockState state, double speedMod, double height) {
        throwStone(throwerEntity, (int) targetEntity.getX(), (int) targetEntity.getY(), (int) targetEntity.getZ(), state, speedMod, height);
    }

    /**
     * Throws stone at X,Y,Z coordinates
     *
     * @param X
     * @param Y
     * @param Z
     */
    public static void throwStone(Entity throwerEntity, int X, int Y, int Z, BlockState state) {
        throwStone(throwerEntity, X, Y, Z, state, 10D, 0.25D);
        /*MoCEntityThrowableRock etrock = new MoCEntityThrowableRock(throwerEntity.world, throwerEntity, throwerEntity.posX, throwerEntity.posY + 0.5D, throwerEntity.posZ);//, false, false);
        throwerEntity.world.spawnEntity(etrock);
        etrock.setState(state);
        etrock.setBehavior(0);
        etrock.motionX = ((X - throwerEntity.posX) / 10.0D);
        etrock.motionY = ((Y - throwerEntity.posY) / 10.0D + 0.25D);
        etrock.motionZ = ((Z - throwerEntity.posZ) / 10.0D);*/
    }

    public static void throwStone(Entity throwerEntity, int X, int Y, int Z, BlockState state, double speedMod, double height) {
        MoCEntityThrowableRock etrock =
                new MoCEntityThrowableRock(throwerEntity.level, throwerEntity, throwerEntity.getX(), throwerEntity.getY() + 0.5D, throwerEntity.getZ());//, false, false);
        throwerEntity.level.addFreshEntity(etrock);
        etrock.setState(state);
        etrock.setBehavior(0);
        double x = ((X - throwerEntity.getX()) / speedMod);
        double y = ((Y - throwerEntity.getY()) / speedMod + height);
        double z = ((Z - throwerEntity.getZ()) / speedMod);
        etrock.setDeltaMovement(x, y, z);
    }

    /**
     * Calculates the moving speed of the entity
     * @param entity
     * @return
     */
    public static float getMyMovementSpeed(Entity entity) {
        return MathHelper.sqrt((entity.getDeltaMovement().x * entity.getDeltaMovement().x) + (entity.getDeltaMovement().z * entity.getDeltaMovement().z));
    }

    public static ItemEntity getClosestFood(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = entity.level.getEntities(entity, entity.getBoundingBox().expandTowards(d, d, d));
        for (Entity value : list) {
            Entity entity1 = (Entity) value;
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if (!isItemEdible(entityitem1.getItem().getItem())) {
                continue;
            }
            double d2 = entityitem1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    /**
     * List of edible foods
     *
     * @param item1
     * @return
     */
    public static boolean isItemEdible(Item item1) {
        return (item1.isEdible()) || (item1.getTags().contains(Tags.Items.SEEDS)) || item1 == Items.WHEAT || item1 == Items.SUGAR || item1 == Items.CAKE
                || item1 == Items.EGG;
    }
    
    public static boolean isItemEdibleforCarnivores(Item item1) {
        return item1 == Items.BEEF || item1 == Items.CHICKEN || item1 == Items.COOKED_BEEF 
                || item1 == Items.COOKED_CHICKEN || item1 == Items.COOKED_COD || item1 == Items.COOKED_SALMON || item1 == Items.RABBIT
                || item1 == Items.COOKED_MUTTON || item1 == Items.COOKED_PORKCHOP || item1 == Items.MUTTON
                || item1 == Items.COOKED_RABBIT || item1 == Items.COD || item1 == Items.SALMON || item1 == Items.PORKCHOP;
    }

    public static CompoundNBT getEntityData(Entity entity) {
        if (!entity.getPersistentData().contains(MoCConstants.MOD_ID)) {
            entity.getPersistentData().put(MoCConstants.MOD_ID, new CompoundNBT());
        }

        return entity.getPersistentData().getCompound(MoCConstants.MOD_ID);
    }

    public static void findMobRider(Entity mountEntity) {
        List<Entity> list = mountEntity.level.getEntities(mountEntity, mountEntity.getBoundingBox().expandTowards(4D, 2D, 4D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (!(entity instanceof MobEntity)) {
                continue;
            }
            MobEntity entitymob = (MobEntity) entity;
            if (entitymob.getVehicle() == null
                    && (entitymob instanceof SkeletonEntity || entitymob instanceof ZombieEntity || entitymob instanceof MoCEntitySilverSkeleton)) {
                if (!mountEntity.level.isClientSide) {
                    entitymob.startRiding(mountEntity);
                }
                break;
            }
        }
    }

    public static void copyDataFromOld(Entity source, Entity target) {
        CompoundNBT nbttagcompound = target.saveWithoutId(new CompoundNBT());
        nbttagcompound.remove("Dimension");
        source.load(nbttagcompound);
    }

    public static boolean dismountSneakingPlayer(LivingEntity entity) {
        if (!entity.isPassenger()) return false;
        Entity entityRidden = entity.getVehicle();
        if (entityRidden instanceof LivingEntity && ((LivingEntity)entityRidden).isCrouching()) {
            entity.stopRiding();
            double dist = (-1.5D);
            double newPosX = entityRidden.getX() + (dist * Math.sin(((LivingEntity)entityRidden).yBodyRot / 57.29578F));
            double newPosZ = entityRidden.getZ() - (dist * Math.cos(((LivingEntity)entityRidden).yBodyRot / 57.29578F));
            entity.setPos(newPosX, entityRidden.getY() + 2D, newPosZ);
            MoCTools.playCustomSound(entity, SoundEvents.CHICKEN_EGG);
            return true;
        }
        return false;
    }
}
