package drzhark.mocreatures;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageNameGUI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLServerLaunchProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MoCTools {

    /**
     * spawns tiny slimes
     */
    public static void spawnSlimes(World world, Entity entity) {
        if (!world.isRemote) {
            //changed so it only spawns 0 - 1 slime, as it now spaws also big slimes
            int var2 = 1 + world.rand.nextInt(1);

            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = (i / 2 - 0.5F) * 1 / 4.0F;
                SlimeEntity var6 = new SlimeEntity(world);
                //var6.setSlimeSize(1);  TODO FIX
                var6.setLocationAndAngles(entity.getPosX() + var4, entity.getPosY() + 0.5D, entity.getPosZ() + var5, world.rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntity(var6);
            }
        }
    }

    /**
     * Drops saddle
     */
    public static void dropSaddle(MoCEntityAnimal entity, World world) {
        if (!entity.getIsRideable() || world.isRemote) {
            return;
        }
        dropCustomItem(entity, world, new ItemStack(MoCItems.HORSESADDLE, 1));
        entity.setRideable(false);
    }

    /**
     * Drops chest block
     */
    public static void dropBags(MoCEntityAnimal entity, World world) {
        if (world.isRemote) {
            return;
        }
        dropCustomItem(entity, world, new ItemStack(Blocks.CHEST, 1));
    }

    /**
     * Drops item
     */
    public static void dropCustomItem(Entity entity, World world, ItemStack itemstack) {
        if (world.isRemote) {
            return;
        }

        ItemEntity entityitem = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), itemstack);
        float f3 = 0.05F;
        entityitem.setMotion((float) world.rand.nextGaussian() * f3,
                ((float) world.rand.nextGaussian() * f3) + 0.2F,
                (float) world.rand.nextGaussian() * f3
        );
        world.spawnEntity(entityitem);
    }

    public static void bigsmack(Entity entity, Entity entity1, float force) {
        double d = entity.getPosX() - entity1.getPosX();
        double d1;
        for (d1 = entity.getPosZ() - entity1.getPosZ(); ((d * d) + (d1 * d1)) < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D) {
            d = (Math.random() - Math.random()) * 0.01D;
        }

        float f = MathHelper.sqrt((d * d) + (d1 * d1));
        entity1.setMotion((entity1.getMotion().getX()/2D)-((d/f)*force),
                (entity1.getMotion().getY()/2D)+force,
                (entity1.getMotion().getZ()/2D)-((d1/f)*force)
        );

        if (entity1.getMotion().getY() > force) {
            entity1.setMotion(entity1.getMotion().x,
                    force,
                    entity1.getMotion().z
            );
        }
    }

    public static void buckleMobs(LivingEntity entityattacker, Double dist, World world) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.getBoundingBox().expand(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entitytarget = list.get(i);
            if (!(entitytarget instanceof LivingEntity) || (entityattacker.isBeingRidden() && entitytarget == entityattacker.getRidingEntity())) {
                continue;
            }

            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, MoCSoundEvents.ENTITY_GENERIC_TUD);
            //todo tuck sound!!
        }
    }

    public static void buckleMobsNotPlayers(LivingEntity entityattacker, Double dist, World world) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.getBoundingBox().expand(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entitytarget = list.get(i);
            if (!(entitytarget instanceof LivingEntity) || (entitytarget instanceof PlayerEntity)
                    || (entityattacker.isBeingRidden() && entitytarget == entityattacker.getRidingEntity())) {
                continue;
            }

            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, MoCSoundEvents.ENTITY_GENERIC_TUD);
            //todo tuck sound!!
        }
    }

    public static void spawnNearPlayer(PlayerEntity player, int entityId, int numberToSpawn)//, World world)
    {
        ServerWorld world =
                FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(player.world.getDimension().getType().getId());
        for (int i = 0; i < numberToSpawn; i++) {
            LivingEntity entityliving = null;
            try {
                Class<? extends LivingEntity> entityClass = MoCreatures.instaSpawnerMap.get(entityId);
                entityliving = (LivingEntity) entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (entityliving != null) {
                entityliving.setLocationAndAngles(player.getPosX() - 1, player.getPosY(), player.getPosZ() - 1, player.rotationYaw, player.rotationPitch);
                world.spawnEntity(entityliving);
            }
        }
    }

    public static void spawnNearPlayerbyName(PlayerEntity player, String eName, int numberToSpawn) {
        ServerWorld world =
                FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(player.world.getDimension().getType().getId());

        for (int i = 0; i < numberToSpawn; i++) {
            LivingEntity entityToSpawn = null;
            try {
                MoCEntityData entityData = MoCreatures.mocEntityMap.get(eName);
                Class<? extends LivingEntity> myClass = entityData.getEntityClass();
                entityToSpawn = (LivingEntity) myClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (entityToSpawn != null) {
                ILivingEntityData entitylivingdata = null;
                entityToSpawn.onInitalSpawn(player.world.getDifficultyForLocation(new BlockPos(entityToSpawn)), entitylivingdata); // onSpawnWithEgg
                entityToSpawn.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), player.rotationYaw, player.rotationPitch);
                world.spawnEntity(entityToSpawn);
            }
        }
    }

    public static void playCustomSound(Entity entity, SoundEvent customSound) {
        playCustomSound(entity, customSound, 1.0F);
    }

    public static void playCustomSound(Entity entity, SoundEvent customSound, float volume) {
        entity.playSound(customSound, volume, 1.0F + ((entity.world.rand.nextFloat() - entity.world.rand.nextFloat()) * 0.2F));
    }

    public static boolean NearMaterialWithDistance(Entity entity, Double double1, Material mat) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockState blockstate = entity.world.getBlockState(new BlockPos(k1, l1, i2));
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getMaterial() == mat)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNearBlockName(Entity entity, Double dist, String blockName) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(dist, dist / 2D, dist);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockState blockstate = entity.world.getBlockState(new BlockPos(k1, l1, i2));

                    if (blockstate.getBlock() != Blocks.AIR) {
                        String nameToCheck = "";
                        nameToCheck = blockstate.getBlock().getRegistryName().toString();//.getBlockName();
                        if (nameToCheck != null && nameToCheck != "") {
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
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(dist, dist / 2D, dist);
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
                    BlockState blockstate = entity.world.getBlockState(pos);
                    if (!entity.world.isAirBlock(pos)) {
                        if (blockstate.getBlock() instanceof JukeboxBlock) {
                            JukeboxTileEntity juky = (JukeboxTileEntity) entity.world.getTileEntity(pos);
                            return juky;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void checkForTwistedEntities(World world) {
        for (int l = 0; l < world.loadedEntityList.size(); l++) {
            Entity entity = (Entity) world.loadedEntityList.get(l);
            if (entity instanceof LivingEntity) {
                LivingEntity twisted = (LivingEntity) entity;
                if (twisted.deathTime > 0 && twisted.getRidingEntity() == null && twisted.getHealth() > 0) {
                    twisted.deathTime = 0;
                }
            }
        }
    }

    public static double getSqDistanceTo(Entity entity, double i, double j, double k) {
        double l = entity.getPosX() - i;
        double i1 = entity.getPosY() - j;
        double j1 = entity.getPosZ() - k;
        return Math.sqrt((l * l) + (i1 * i1) + (j1 * j1));
    }

    public static int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1, Double yOff) {
        double shortestDistance = -1D;
        double distance = 0D;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(double1.doubleValue(), yOff.doubleValue(), double1.doubleValue());
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
                    BlockState blockstate = entity.world.getBlockState(pos);
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

        if (entity.getPosX() > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.getPosZ() > z) {
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

        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(dist, dist, dist);
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
                    BlockState blockstate = entity.world.getBlockState(pos);
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

        if (entity.getPosX() > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.getPosZ() > z) {
            z -= 2;
        } else {
            z += 2;
        }
        return (new int[] {x, y, z});
    }

    public static void MoveCreatureToXYZ(CreatureEntity movingEntity, double x, double y, double z, float f) {
        //TODO works?
        Path pathentity = movingEntity.getNavigator().getPathToPos(x, y, z, 0);
        if (pathentity != null) {
            movingEntity.getNavigator().setPath(pathentity, f);
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
            if (entity.getPosY() < y) {
                entity.setMotion(entity.getMotion().add(0, 0.14999999999999999D, 0));
            }
            if (entity.getPosX() < x) {
                double d = x - entity.getPosX();
                if (d > 0.5D) {
                    entity.setMotion(entity.getMotion().add(0.050000000000000003D, 0, 0));
                }
            } else {
                double d1 = entity.getPosX() - x;
                if (d1 > 0.5D) {
                    entity.setMotion(entity.getMotion().subtract(0.050000000000000003D, 0, 0));
                }
            }
            if (entity.getPosZ() < z) {
                double d2 = z - entity.getPosZ();
                if (d2 > 0.5D) {
                    entity.setMotion(entity.getMotion().add(0, 0, 0.050000000000000003D));
                }
            } else {
                double d3 = entity.getPosZ() - z;
                if (d3 > 0.5D) {
                    entity.setMotion(entity.getMotion().subtract(0, 0, 0.050000000000000003D));
                }
            }
        }
    }

    public static float distanceToSurface(Entity entity) {
        int i = MathHelper.floor(entity.getPosX());
        int j = MathHelper.floor(entity.getPosY());
        int k = MathHelper.floor(entity.getPosZ());
        BlockState blockstate = entity.world.getBlockState(new BlockPos(i, j, k));
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == Material.WATER) {
            for (int x = 1; x < 64; x++) {
                blockstate = entity.world.getBlockState(new BlockPos(i, j + x, k));
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
        return waterSurfaceAtGivenPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.world);
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
        int i = MathHelper.floor(entity.getPosX());
        int j = MathHelper.floor(entity.getPosY());
        int k = MathHelper.floor(entity.getPosZ());
        for (int x = 0; x < 64; x++) {
            Block block = entity.world.getBlockState(new BlockPos(i, j - x, k)).getBlock();
            if (block != Blocks.AIR) {
                return x;
            }
        }

        return 0;
    }

    public boolean isInsideOfMaterial(Material material, Entity entity) {
        double d = entity.getPosY() + entity.getEyeHeight();
        int i = MathHelper.floor(entity.getPosX());
        int j = MathHelper.floor(MathHelper.floor(d));
        int k = MathHelper.floor(entity.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);
        BlockState blockstate = entity.world.getBlockState(pos);
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == material) {
            float f = BlockLiquid.getLiquidHeightPercent(blockstate.getBlock().getMetaFromState(blockstate)) - 0.1111111F; //TODO: Rework liquid functions
            float f1 = j + 1 - f;
            return d < f1;
        } else {
            return false;
        }
    }

    public static void disorientEntity(Entity entity) {
        double rotD = 0;
        double motD = 0;
        double d = entity.world.rand.nextGaussian();
        double d1 = 0.1D * d;
        motD = (0.2D * d1) + ((1.0D - 0.2D) * motD);
        entity.setMotion(entity.getMotion().add(motD, 0, motD));
        double d2 = 0.78D * d;
        rotD = (0.125D * d2) + ((1.0D - 0.125D) * rotD);
        entity.rotationYaw += rotD;
        entity.rotationPitch += rotD;
    }

    public static void slowEntity(Entity entity) {
        entity.setMotion(entity.getMotion().mul(0.8D, 1.0D, 0.8D));
    }

    public static int colorize(int i) {
        return ~i & 0xf;
    }

    public int countEntities(Class<? extends LivingEntity> class1, World world) {
        int i = 0;
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = world.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass())) {
                i++;
            }
        }

        return i;
    }

    public static float distToPlayer(Entity entity) {
        //TODO
        return 0.0F;
    }

    public static String biomeName(World world, BlockPos pos) {
        BiomeProvider biomeProvider = world.getBiomeProvider();
        if (biomeProvider == null) {
            return null;
        }

        Biome biome = biomeProvider.getBiome(pos);
        //TODO works?

        if (biome == null) {
            return null;
        } else {
            return biome.getRegistryName().toString();
        }
    }

    public static Biome Biomekind(World world, BlockPos pos) {
        return world.getBiome(pos);
    }

    public static void destroyDrops(Entity entity, double d) {

        if (!MoCreatures.proxy.destroyDrops) {
            return;
        }

        List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getBoundingBox().expand(d, d, d));

        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem = (ItemEntity) entity1;
            if ((entityitem != null) && (entityitem.getAge() < 50)) {
                entityitem.setdead();
            }
        }
    }

    public static void repelMobs(Entity entity1, Double dist, World world) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getBoundingBox().expand(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
            if (!(entity instanceof MobEntity)) {
                continue;
            }
            MobEntity entitymob = (MobEntity) entity;
            entitymob.setAttackTarget(null);
            //entitymob.entityLivingToAttack = null;
            entitymob.getNavigator().clearPath();
        }
    }

    /**
     * Drops the important stuff to get going fast
     *
     * @param world
     * @param entity
     */
    public static void dropGoodies(World world, Entity entity) { //TODO: This method probably won't be necessary when loot table data is in place.
        if (world.isRemote) {
            return;
        }

        ItemEntity entityitem = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Blocks.LOG, 16));
        entityitem.setPickupDelay(10);
        world.spawnEntity(entityitem);

        ItemEntity entityitem2 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.DIAMOND, 64));
        entityitem2.setPickupDelay(10);
        world.spawnEntity(entityitem2);

        ItemEntity entityitem3 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Blocks.PUMPKIN, 6));
        entityitem3.setPickupDelay(10);
        world.spawnEntity(entityitem3);

        ItemEntity entityitem4 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Blocks.COBBLESTONE, 64));
        entityitem4.setPickupDelay(10);
        world.spawnEntity(entityitem4);

        ItemEntity entityitem5 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.APPLE, 24));
        entityitem5.setPickupDelay(10);
        world.spawnEntity(entityitem5);

        ItemEntity entityitem6 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.LEATHER, 64));
        entityitem6.setPickupDelay(10);
        world.spawnEntity(entityitem6);

        ItemEntity entityitem7 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(MoCItems.RECORDSHUFFLE, 6));
        entityitem7.setPickupDelay(10);
        world.spawnEntity(entityitem7);

        ItemEntity entityitem8 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.IRON_INGOT, 64));
        entityitem8.setPickupDelay(10);
        world.spawnEntity(entityitem8);

        ItemEntity entityitem9 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.GOLD_INGOT, 12));
        entityitem9.setPickupDelay(10);
        world.spawnEntity(entityitem9);

        ItemEntity entityitem10 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.STRING, 32));
        entityitem10.setPickupDelay(10);
        world.spawnEntity(entityitem10);

        ItemEntity entityitem12 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Blocks.POPPY, 6));
        entityitem12.setPickupDelay(10);
        world.spawnEntity(entityitem12);

        ItemEntity entityitem13 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.BLAZE_ROD, 12));
        entityitem13.setPickupDelay(10);
        world.spawnEntity(entityitem13);

        ItemEntity entityitem14 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.ENDER_PEARL, 12));
        entityitem14.setPickupDelay(10);
        world.spawnEntity(entityitem14);

        ItemEntity entityitem15 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.GHAST_TEAR, 12));
        entityitem15.setPickupDelay(10);
        world.spawnEntity(entityitem15);

        ItemEntity entityitem16 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Blocks.LAPIS_BLOCK, 2));
        entityitem16.setPickupDelay(10);
        world.spawnEntity(entityitem16);

        ItemEntity entityitem17 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Items.BONE, 12));
        entityitem17.setPickupDelay(10);
        world.spawnEntity(entityitem17);

        ItemEntity entityitem18 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(MoCItems.UNICORNHORN, 16));
        entityitem18.setPickupDelay(10);
        world.spawnEntity(entityitem18);

        ItemEntity entityitem19 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Blocks.FIRE, 32));
        entityitem19.setPickupDelay(10);
        world.spawnEntity(entityitem19);

        ItemEntity entityitem20 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(MoCItems.ESSENCEDARKNESS, 6));
        entityitem20.setPickupDelay(10);
        world.spawnEntity(entityitem20);

        ItemEntity entityitem21 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(MoCItems.ESSENCEUNDEAD, 6));
        entityitem21.setPickupDelay(10);
        world.spawnEntity(entityitem21);

        ItemEntity entityitem22 = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(MoCItems.ESSENCEFIRE, 6));
        entityitem22.setPickupDelay(10);
        world.spawnEntity(entityitem22);

        ItemEntity entityitem23 =
                new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 6, 15));
        entityitem23.setPickupDelay(10);
        world.spawnEntity(entityitem23);

    }

    public static boolean mobGriefing(World world) {
        return world.getGameRules().getBoolean(GameRules.MOB_GRIEFING);
    }

    public static void DestroyBlast(Entity entity, double d, double d1, double d2, float f, boolean flag) {
        PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
        entity.world.playSound(player, d, d1, d2, MoCSoundEvents.ENTITY_GENERIC_DESTROY, SoundCategory.HOSTILE, 4F,
                (1.0F + ((entity.world.rand.nextFloat() - entity.world.rand.nextFloat()) * 0.2F)) * 0.7F);

        boolean mobGriefing = mobGriefing(entity.world);

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
                    float f2 = f * (0.7F + (entity.world.rand.nextFloat() * 0.6F));
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
                        BlockState blockstate = entity.world.getBlockState(pos);
                        if (blockstate.getBlock() != Blocks.AIR) {
                            f4 = blockstate.getBlockHardness(entity.world, pos);
                            f2 -= (blockstate.getBlock().getExplosionResistance(entity) + 0.3F) * (f3 / 10F);
                        }
                        if ((f2 > 0.0F) && (d10 > entity.getPosY()) && (f4 < 3F)) {
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
        if (FMLCommonHandler.instance().getEffectiveSide() == Dist.DEDICATED_SERVER) {
            int k = MathHelper.floor(d - f - 1.0D);
            int i1 = MathHelper.floor(d + f + 1.0D);
            int k1 = MathHelper.floor(d1 - f - 1.0D);
            int l1 = MathHelper.floor(d1 + f + 1.0D);
            int i2 = MathHelper.floor(d2 - f - 1.0D);
            int j2 = MathHelper.floor(d2 + f + 1.0D);
            List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, new AxisAlignedBB(k, k1, i2, i1, l1, j2));
            Vec3d vec3d = new Vec3d(d, d1, d2);
            for (int k2 = 0; k2 < list.size(); k2++) {
                Entity entity1 = (Entity) list.get(k2);
                double d7 = entity1.getDistance(d, d1, d2) / f;
                if (d7 > 1.0D) {
                    continue;
                }
                double d9 = entity1.getPosX() - d;
                double d11 = entity1.getPosY() - d1;
                double d13 = entity1.getPosZ() - d2;
                double d15 = MathHelper.sqrt((d9 * d9) + (d11 * d11) + (d13 * d13));
                d9 /= d15;
                d11 /= d15;
                d13 /= d15;
                double d17 = entity.world.getBlockDensity(vec3d, entity1.getBoundingBox());
                double d19 = (1.0D - d7) * d17;

                //attacks entities in server
                if (!(entity1 instanceof MoCEntityOgre)) {
                    entity1.attackEntityFrom(DamageSource.GENERIC, (int) (((((d19 * d19) + d19) / 2D) * 3D * f) + 1.0D));
                    double d21 = d19;
                    entity1.setMotion(entity1.getMotion().add(d9*d21, d11*d21, d13*d21));
                }
            }
        }

        f = f1;
        ArrayList<BlockPos> arraylist = new ArrayList<BlockPos>();
        arraylist.addAll(hashset);

        for (int l2 = arraylist.size() - 1; l2 >= 0; l2--) {
            BlockPos chunkposition = arraylist.get(l2);
            BlockState blockstate = entity.world.getBlockState(chunkposition);
            for (int j5 = 0; j5 < 5; j5++) {
                double d14 = chunkposition.getX() + entity.world.rand.nextFloat();
                double d16 = chunkposition.getY() + entity.world.rand.nextFloat();
                double d18 = chunkposition.getZ() + entity.world.rand.nextFloat();
                double d20 = d14 - d;
                double d22 = d16 - d1;
                double d23 = d18 - d2;
                double d24 = MathHelper.sqrt((d20 * d20) + (d22 * d22) + (d23 * d23));
                d20 /= d24;
                d22 /= d24;
                d23 /= d24;
                double d25 = 0.5D / ((d24 / f) + 0.10000000000000001D);
                d25 *= (entity.world.rand.nextFloat() * entity.world.rand.nextFloat()) + 0.3F;
                d25--;
                d20 *= d25;
                d22 *= d25 - 1.0D;
                d23 *= d25;

                /**
                 * shows explosion on clients!
                 */
                if (FMLCommonHandler.instance().getEffectiveSide() == Dist.CLIENT) {
                    entity.world.addParticle(ParticleTypes.EXPLOSION, (d14 + (d * 1.0D)) / 2D, (d16 + (d1 * 1.0D)) / 2D,
                            (d18 + (d2 * 1.0D)) / 2D, d20, d22, d23);
                    entity.setMotion(entity.getMotion().subtract(0.0010000000474974511D, 0.0010000000474974511D, 0));
                }

            }

            //destroys blocks on server!
            if (mobGriefing && (FMLCommonHandler.instance().getEffectiveSide() == Dist.DEDICATED_SERVER) && blockstate.getBlock() != Blocks.AIR) {
                BlockEvent.BreakEvent event = null;
                if (!entity.world.isRemote) {
                    try {
                        event =
                                new BlockEvent.BreakEvent(entity.world, chunkposition, blockstate, FakePlayerFactory.get(
                                        DimensionManager.getWorld(entity.world.provider.getDimensionType().getId()), MoCreatures.MOCFAKEPLAYER));
                    } catch (Throwable t) {
                    }
                }
                if (event != null && !event.isCanceled()) {
                    blockstate.getBlock().dropBlockAsItemWithChance(entity.world, chunkposition, blockstate, 0.3F, 1);
                    entity.world.setBlockToAir(chunkposition);
                    // pass explosion instance to fix BlockTNT NPE's
                    Explosion explosion =
                            new Explosion(entity.world, null, chunkposition.getX(), chunkposition.getY(), chunkposition.getZ(), 3f, false, Explosion.Mode.BREAK);
                    blockstate.getBlock().onBlockExploded(blockstate, entity.world, chunkposition, explosion);
                }
            }
        }

        //sets world on fire on server
        if (mobGriefing && (FMLEnvironment.dist == Dist.DEDICATED_SERVER) && flag) {
            for (int i3 = arraylist.size() - 1; i3 >= 0; i3--) {
                BlockPos chunkposition1 = arraylist.get(i3);
                BlockState blockstate = entity.world.getBlockState(chunkposition1);
                if ((blockstate.getBlock() == Blocks.AIR) && (entity.world.rand.nextInt(8) == 0)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.world.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.world, chunkposition1, blockstate, FakePlayerFactory.get(
                                        (ServerWorld) entity.world, MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.world.setBlockState(chunkposition1, Blocks.FIRE.getDefaultState(), 3);
                    }
                }
            }
        }
    }

    public static int despawnVanillaAnimals(World world) {
        return despawnVanillaAnimals(world, null);
    }

    @SuppressWarnings("rawtypes")
    public static int despawnVanillaAnimals(World world, List[] classList) {
        int count = 0;
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = (Entity) world.loadedEntityList.get(j);
            if ((entity instanceof LivingEntity)
                    && (entity instanceof CowEntity || entity instanceof SheepEntity || entity instanceof PigEntity
                            || entity instanceof ChickenEntity || entity instanceof SquidEntity || entity instanceof WolfEntity)) {
                count += entityDespawnCheck(world, (LivingEntity) entity);
            }
        }
        return count;
    }

    protected static int entityDespawnCheck(World world, LivingEntity entityliving)//to keep a copy
    {
        int count = 0;
        PlayerEntity entityplayer = world.getClosestPlayer(entityliving, -1D);
        if (entityplayer != null) //entityliving.canDespawn() &&
        {
            double d = ((Entity) (entityplayer)).getPosX() - entityliving.getPosX();
            double d1 = ((Entity) (entityplayer)).getPosY() - entityliving.getPosY();
            double d2 = ((Entity) (entityplayer)).getPosZ() - entityliving.getPosZ();
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384D) {
                entityliving.setDead();
                count++;
            }

            if (entityliving.getIdleTime() > 600 && world.rand.nextInt(800) == 0) {
                if (d3 < 1024D) {
                    //TODO test!
                    entityliving.attackEntityFrom(DamageSource.causeMobDamage(null), 0);
                } else {
                    entityliving.setDead();
                    count++;
                }
            }
        }
        return count;
    }

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
        mystack[0] = player.getItemStackFromSlot(EquipmentSlotType.FEET); //boots
        mystack[1] = player.getItemStackFromSlot(EquipmentSlotType.LEGS); //legs
        mystack[2] = player.getItemStackFromSlot(EquipmentSlotType.CHEST); //plate
        mystack[3] = player.getItemStackFromSlot(EquipmentSlotType.HEAD); //helmet

        //full scorpion cave armor set, enable night vision
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSCAVE && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSCAVE && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATECAVE
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETCAVE) {
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 300, 0));
            return;
        }

        //full scorpion nether armor set, enable fire resistance
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSNETHER && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSNETHER && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATENETHER
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETNETHER) {
            player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0));
            return;
        }

        //full scorpion frost armor set, enable water breathing
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSFROST && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSFROST && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATEFROST
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETFROST) {
            player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 300, 0));
            return;
        }

        //full scorpion armor set, regeneration effect
        if (mystack[0] != null && mystack[0].getItem() == MoCItems.SCORPBOOTSDIRT && mystack[1] != null
                && mystack[1].getItem() == MoCItems.SCORPLEGSDIRT && mystack[2] != null && mystack[2].getItem() == MoCItems.SCORPPLATEDIRT
                && mystack[3] != null && mystack[3].getItem() == MoCItems.SCORPHELMETDIRT) {
            player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 70, 0));
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
            int x = (int) (entity.getPosX() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getPosY() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 4));
            int z = (int) (entity.getPosZ() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState blockstate = entity.world.getBlockState(pos.up());
            BlockState blockstate1 = entity.world.getBlockState(pos);

            if (blockstate.getBlock() != Blocks.AIR && blockstate1.getBlock() == Blocks.AIR) {
                if (mobGriefing(entity.world)) {
                    blockstate1 = entity.world.getBlockState(pos);
                    BlockEvent.BreakEvent event = null;
                    if (!entity.world.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.world, pos.up(), blockstate, FakePlayerFactory.get((ServerWorld) entity.world,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
                return Block.getStateId(blockstate);
            }
        }
        return 0;
    }

    public static BlockState destroyRandomBlockWithIBlockState(Entity entity, double distance) {
        int l = (int) (distance * distance * distance);
        for (int i = 0; i < l; i++) {
            int x = (int) (entity.getPosX() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getPosY() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int z = (int) (entity.getPosZ() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState stateAbove = entity.world.getBlockState(pos.up());
            BlockState stateTarget = entity.world.getBlockState(pos);

            if (pos.getY() == (int) entity.getPosY() - 1D && (pos.getX() == (int) Math.floor(entity.posX) && pos.getZ() == (int) Math.floor(entity.posZ))) {
                continue;
            }
            if (stateTarget.getBlock() != Blocks.AIR && stateTarget.getBlock() != Blocks.WATER && stateTarget.getBlock() != Blocks.BEDROCK
                    && stateAbove.getBlock() == Blocks.AIR) // ignore bedrock
            {
                if (mobGriefing(entity.world)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.world.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.world, pos, stateTarget, FakePlayerFactory.get((ServerWorld) entity.world,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.world.setBlockState(pos, Blocks.AIR.getDefaultState());

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
            int x = (int) (entity.getPosX() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getPosY() + entity.world.rand.nextInt((int) (distance / 2)) - (int) (distance / 4));
            int z = (int) (entity.getPosZ() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState blockstate1 = entity.world.getBlockState(pos.up()); // +Y
            BlockState blockstate2 = entity.world.getBlockState(pos);
            BlockState blockstate3 = entity.world.getBlockState(pos.east()); // +X
            BlockState blockstate4 = entity.world.getBlockState(pos.west()); // -X
            BlockState blockstate5 = entity.world.getBlockState(pos.down()); // -Y
            BlockState blockstate6 = entity.world.getBlockState(pos.south()); // -Z
            BlockState blockstate7 = entity.world.getBlockState(pos.north()); // +Z

            float tX = x - (float) entity.getPosX();
            float tY = y - (float) entity.getPosY();
            float tZ = z - (float) entity.getPosZ();
            float spawnDist = tX * tX + tY * tY + tZ * tZ;

            if (allowedBlock(Block.getStateId(blockstate1))
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
            int x = (int) (entity.getPosX() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getPosY() + entity.world.rand.nextInt((int) (distance / 2)) - (int) (distance / 4));
            int z = (int) (entity.getPosZ() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState blockstate1 = entity.world.getBlockState(pos.up()); // +Y
            BlockState blockstate2 = entity.world.getBlockState(pos);
            BlockState blockstate3 = entity.world.getBlockState(pos.east()); // +X
            BlockState blockstate4 = entity.world.getBlockState(pos.west()); // -X
            BlockState blockstate5 = entity.world.getBlockState(pos.down()); // -Y
            BlockState blockstate6 = entity.world.getBlockState(pos.south()); // -Z
            BlockState blockstate7 = entity.world.getBlockState(pos.north()); // +Z

            float tX = x - (float) entity.getPosX();
            float tY = y - (float) entity.getPosY();
            float tZ = z - (float) entity.getPosZ();
            float spawnDist = tX * tX + tY * tY + tZ * tZ;

            if (allowedBlock(Block.getStateId(blockstate1))
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

        if (MoCreatures.proxy.enableOwnership) {
            if (storedCreature == null) {
                ep.sendMessage(new TextComponentTranslation(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE
                        + "The stored creature is NULL and could not be created. Report to admin."));
                return false;
            }
            int max = 0;
            max = MoCreatures.proxy.maxTamed;
            // only check count for new pets as owners may be changing the name
            if (!MoCreatures.instance.mapData.isExistingPet(ep.getUniqueID(), storedCreature)) {
                int count = MoCTools.numberTamedByPlayer(ep);
                if (isThisPlayerAnOP(ep)) {
                    max = MoCreatures.proxy.maxOPTamed;
                }
                if (count >= max) {
                    String message = "\2474" + ep.getName() + " can not tame more creatures, limit of " + max + " reached";
                    ep.sendMessage(new TextComponentTranslation(message));
                    return false;
                }
            }
        }

        storedCreature.setOwnerId(ep.getUniqueID()); // ALWAYS SET OWNER. Required for our new pet save system.
        MoCMessageHandler.CHANNEL.sendTo(new MoCMessageNameGUI(((Entity) storedCreature).getEntityId()), (ServerPlayerEntity) ep);
        storedCreature.setTamed(true);
        // Required to update petId data for pet amulets
        if (MoCreatures.instance.mapData != null && storedCreature.getOwnerPetId() == -1) {
            MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
        }
        return true;
    }

    public static int numberTamedByPlayer(PlayerEntity ep) {
        if (MoCreatures.instance.mapData != null) {
            if (MoCreatures.instance.mapData.getPetData(ep.getUniqueID()) != null) {
                return MoCreatures.instance.mapData.getPetData(ep.getUniqueID()).getTamedList().tagCount();
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
        double newPosX = entity.getPosX() - (distance * Math.cos((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = entity.getPosZ() - (distance * Math.sin((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosY = entity.getPosY();
        int x = MathHelper.floor(newPosX);
        int y = MathHelper.floor(newPosY);
        int z = MathHelper.floor(newPosZ);

        for (int i = 0; i < height; i++) {
            BlockPos pos = new BlockPos(x, y + i, z);
            BlockState blockstate = entity.world.getBlockState(pos);
            if (blockstate.getBlock() != Blocks.AIR) {
                if (blockstate.getBlockHardness(entity.world, pos) <= strengthF) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.world.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.world, pos, blockstate, FakePlayerFactory.get((ServerWorld) entity.world,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        blockstate.getBlock().dropBlockAsItemWithChance(entity.world, pos, blockstate, 0.20F * strengthF, 1);
                        entity.world.setBlockState(pos, Blocks.AIR.getDefaultState());
                        if (entity.world.rand.nextInt(3) == 0) {
                            playCustomSound(entity, MoCSoundEvents.ENTITY_GOLEM_WALK);
                            count++; //only counts recovered blocks
                        }
                    }
                }
            }
        }
        return count;
    }

    public static void dropInventory(Entity entity, MoCAnimalChest animalchest) {
        if (animalchest == null || entity.world.isRemote) {
            return;
        }

        int i = MathHelper.floor(entity.getPosX());
        int j = MathHelper.floor(entity.getBoundingBox().minY);
        int k = MathHelper.floor(entity.getPosZ());

        for (int l = 0; l < animalchest.getSizeInventory(); l++) {
            ItemStack itemstack = animalchest.getStackInSlot(l);
            if (itemstack.isEmpty()) {
                continue;
            }
            float f = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
            float f1 = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
            float f2 = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
            float f3 = 0.05F;

            ItemEntity entityitem = new ItemEntity(entity.world, i + f, j + f1, k + f2, itemstack);
            entityitem.setMotion((float) entity.world.rand.nextGaussian() * f3, ((float) entity.world.rand.nextGaussian() * f3) + 0.2F, (float) entity.world.rand.nextGaussian() * f3);
            entity.world.spawnEntity(entityitem);
            animalchest.setInventorySlotContents(l, ItemStack.EMPTY);
        }
    }

    /**
     * Drops an amulet with the stored information of the entity passed
     *
     * @param entity
     */
    public static void dropHorseAmulet(MoCEntityTameableAnimal entity) {
        if (!entity.world.isRemote) {
            ItemStack stack = getProperAmulet(entity);
            if (stack == null) {
                return;
            }
            if (stack.getTag() == null) {
                stack.setTag(new CompoundNBT());
            }
            CompoundNBT nbtt = stack.getTag();
            PlayerEntity epOwner = entity.world.getPlayerByUuid(entity.getOwnerId());

            try {
                nbtt.putString("SpawnClass", "WildHorse");
                nbtt.putFloat("Health", entity.getHealth());
                nbtt.putInt("Edad", entity.getEdad());
                nbtt.putString("Name", entity.getPetName());
                nbtt.putBoolean("Rideable", entity.getIsRideable());
                nbtt.putInt("Armor", entity.getArmorType());
                nbtt.putInt("CreatureType", entity.getType());
                nbtt.putBoolean("Adult", entity.getIsAdult());
                nbtt.putString("OwnerName", epOwner != null ? epOwner.getName().toString() : "");
                if (entity.getOwnerId() != null) {
                    nbtt.putUniqueId("OwnerUUID", entity.getOwnerId());
                }
                nbtt.putInt("PetId", entity.getOwnerPetId());
                int amuletType = 1;
                if (stack.getItem() == MoCItems.PETAMULETFULL) {
                    amuletType = 2;
                } else if (stack.getItem() == MoCItems.AMULETGHOSTFULL) {
                    amuletType = 3;
                }
                nbtt.putBoolean("Ghost", amuletType == 3 ? true : false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (epOwner != null && epOwner.inventory.getFirstEmptyStack() != -1) // don't attempt to set if player inventory is full
            {
                epOwner.inventory.addItemStackToInventory(stack);
            } else {
                ItemEntity entityitem = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack);
                entityitem.setPickupDelay(20);
                entity.world.spawnEntity(entityitem);
            }
        }
    }

    /**
     * Drops a new amulet/fishnet with the stored information of the entity
     * @param player 
     */
    public static void dropAmulet(IMoCEntity entity, int amuletType, PlayerEntity player) {
        if (!player.world.isRemote) {
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
                final EntityEntry entry = EntityRegistry.getEntry((Class<? extends Entity>) entity.getClass());
                final String petClass = entry.getName().replace(MoCConstants.MOD_PREFIX, "");
                nbtt.putString("SpawnClass", petClass);
                nbtt.putUniqueId("OwnerUUID", player.getUniqueID());
                nbtt.putString("OwnerName", player.getName().toString());
                nbtt.putFloat("Health", ((LivingEntity) entity).getHealth());
                nbtt.putInt("Edad", entity.getEdad());
                nbtt.putString("Name", entity.getPetName());
                nbtt.putInt("CreatureType", entity.getType());
                nbtt.putBoolean("Adult", entity.getIsAdult());
                nbtt.putInt("PetId", entity.getOwnerPetId());
                nbtt.putBoolean("Ghost", amuletType == 3 ? true : false);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!player.inventory.addItemStackToInventory(stack)) {
                ItemEntity entityitem =
                        new ItemEntity(((LivingEntity) entity).world, ((LivingEntity) entity).getPosX(), ((LivingEntity) entity).getPosY(),
                                ((LivingEntity) entity).getPosZ(), stack);
                entityitem.setPickupDelay(20);
                ((LivingEntity) entity).world.spawnEntity(entityitem);
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
            if (entity.getType() == 26 || entity.getType() == 27 || entity.getType() == 28) {
                return new ItemStack(MoCItems.AMULETBONEFULL, 1);
            }
            if (entity.getType() > 47 && entity.getType() < 60) {
                return new ItemStack(MoCItems.AMULETFAIRYFULL., 1);
            }
            if (entity.getType() == 39 || entity.getType() == 40) {
                return new ItemStack(MoCItems.AMULETPEGASUSFULL, 1);
            }
            if (entity.getType() == 21 || entity.getType() == 22) {
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
            if (entity.getType() == 26 || entity.getType() == 27 || entity.getType() == 28) {
                return new ItemStack(MoCItems.AMULETBONE, 1);
            }
            if (entity.getType() > 49 && entity.getType() < 60) {
                return new ItemStack(MoCItems.AMULETFAIRY, 1);
            }
            if (entity.getType() == 39 || entity.getType() == 40) {
                return new ItemStack(MoCItems.AMULETPEGASUS, 1);
            }
            if (entity.getType() == 21 || entity.getType() == 22) {
                return new ItemStack(MoCItems.AMULETGHOST, 1);
            }
        }
        return null;
    }

    public static int countPlayersInDimension(ServerWorld world, int dimension) {
        int playersInDimension = 0;
        for (int j = 0; j < world.getPlayers().size(); ++j) {
            ServerPlayerEntity entityplayermp = (ServerPlayerEntity) world.getPlayers().get(j);

            if (entityplayermp.dimension == dimension) {
                playersInDimension++;
            }
        }
        return playersInDimension;
    }

    public static boolean isThisPlayerAnOP(PlayerEntity player) {
        if (player.world.isRemote) {
            return false;
        }

        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().canSendCommands(player.getGameProfile());
    }

    public static void spawnMaggots(World world, Entity entity) {
        if (!world.isRemote) {
            int var2 = 1 + world.rand.nextInt(4);
            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = (i / 2 - 0.5F) * 1 / 4.0F;
                MoCEntityMaggot maggot = new MoCEntityMaggot(world);
                maggot.setLocationAndAngles(entity.getPosX() + var4, entity.getPosY() + 0.5D, entity.getPosZ() + var5, world.rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntity(maggot);
            }
        }
    }

    public static void getPathToEntity(LivingEntity creatureToMove, Entity entityTarget, float f) {
        Path pathentity = ((MobEntity)creatureToMove).getNavigator().getPathToEntity(entityTarget, 0);
        if (pathentity != null && f < 12F) {
            ((MobEntity)creatureToMove).getNavigator().setPath(pathentity, 1.0D); //TODO check if 1.0D is proper speed.
        }
    }

    public static void runLikeHell(LivingEntity runningEntity, Entity boogey) {
        double d = runningEntity.getPosX() - boogey.getPosX();
        double d1 = runningEntity.getPosZ() - boogey.getPosZ();
        double d2 = Math.atan2(d, d1);
        d2 += (runningEntity.world.rand.nextFloat() - runningEntity.world.rand.nextFloat()) * 0.75D;
        double d3 = runningEntity.getPosX() + (Math.sin(d2) * 8D);
        double d4 = runningEntity.getPosZ() + (Math.cos(d2) * 8D);
        int i = MathHelper.floor(d3);
        int j = MathHelper.floor(runningEntity.getBoundingBox().minY);
        int k = MathHelper.floor(d4);
        int l = 0;
        do {
            if (l >= 16) {
                break;
            }
            int i1 = (i + runningEntity.world.rand.nextInt(4)) - runningEntity.world.rand.nextInt(4);
            int j1 = (j + runningEntity.world.rand.nextInt(3)) - runningEntity.world.rand.nextInt(3);
            int k1 = (k + runningEntity.world.rand.nextInt(4)) - runningEntity.world.rand.nextInt(4);
            BlockPos pos = new BlockPos(i1, j1, k1);
            if ((j1 > 4)
                    && ((runningEntity.world.getBlockState(pos).getBlock() == Blocks.AIR) || (runningEntity.world.getBlockState(pos).getBlock() == Blocks.SNOW))
                    && (runningEntity.world.getBlockState(pos.down()).getBlock() != Blocks.AIR)) {
                ((MobEntity) runningEntity).getNavigator().tryMoveToXYZ(i1, j1, k1, 1.0D);//TODO check if 1D speed is ok
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
        PlayerEntity entityplayertarget = poisoner.world.getClosestPlayer(poisoner, 2D);
        if (entityplayertarget != null && ((needsToBeInWater && entityplayertarget.isInWater()) || !needsToBeInWater)
                && poisoner.getDistance(entityplayertarget) < 2.0F && !entityplayertarget.getCapabilities.disableDamage) {
            if (entityplayertarget.getRidingEntity() != null && entityplayertarget.getRidingEntity() instanceof BoatEntity) {
                //don't poison players on boats
            } else {
                MoCreatures.poisonPlayer(entityplayertarget);
                entityplayertarget.addPotionEffect(new EffectInstance(Effects.POISON, 120, 0));
                return true;
            }
        }
        return false;
    }

    public static boolean isTamed(Entity entity) {
        if (entity instanceof TameableEntity) {
            if (((TameableEntity) entity).isTamed()) {
                return true;
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        entity.writeWithoutTypeId(nbt);
        if (nbt != null) {
            if (nbt.contains("Owner") && !nbt.getString("Owner").equals("")) {
                return true; // ignore
            }
            if (nbt.contains("Tamed") && nbt.getBoolean("Tamed") == true) {
                return true; // ignore
            }
        }
        return false;
    }

    /**
     * Throws stone at entity
     *
     * @param targetEntity
     * @param rocktype
     * @param metadata
     */
    public static void ThrowStone(Entity throwerEntity, Entity targetEntity, BlockState state, double speedMod, double height) {
        ThrowStone(throwerEntity, (int) targetEntity.getPosX(), (int) targetEntity.getPosY(), (int) targetEntity.getPosZ(), state, speedMod, height);
    }

    /**
     * Throws stone at X,Y,Z coordinates
     *
     * @param X
     * @param Y
     * @param Z
     * @param rocktype
     * @param metadata
     */
    public static void ThrowStone(Entity throwerEntity, int X, int Y, int Z, BlockState state) {
        ThrowStone(throwerEntity, X, Y, Z, state, 10D, 0.25D);
        /*MoCEntityThrowableRock etrock = new MoCEntityThrowableRock(throwerEntity.world, throwerEntity, throwerEntity.posX, throwerEntity.posY + 0.5D, throwerEntity.posZ);//, false, false);
        throwerEntity.world.spawnEntity(etrock);
        etrock.setState(state);
        etrock.setBehavior(0);
        etrock.motionX = ((X - throwerEntity.posX) / 10.0D);
        etrock.motionY = ((Y - throwerEntity.posY) / 10.0D + 0.25D);
        etrock.motionZ = ((Z - throwerEntity.posZ) / 10.0D);*/
    }

    public static void ThrowStone(Entity throwerEntity, int X, int Y, int Z, BlockState state, double speedMod, double height) {
        MoCEntityThrowableRock etrock =
                new MoCEntityThrowableRock(throwerEntity.world, throwerEntity, throwerEntity.posX, throwerEntity.posY + 0.5D, throwerEntity.posZ);//, false, false);
        throwerEntity.world.spawnEntity(etrock);
        etrock.setState(state);
        etrock.setBehavior(0);
        double x = ((X - throwerEntity.getPosX()) / speedMod);
        double y = ((Y - throwerEntity.getPosY()) / speedMod + height);
        double z = ((Z - throwerEntity.getPosZ()) / speedMod);
        etrock.setMotion(x, y, z);
    }

    /**
     * Calculates the moving speed of the entity
     * @param entity
     * @return
     */
    public static float getMyMovementSpeed(Entity entity) {
        return MathHelper.sqrt((entity.getMotion().x * entity.getMotion().x) + (entity.getMotion().z * entity.getMotion().z));
    }

    public static ItemEntity getClosestFood(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = (Entity) list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if (!isItemEdible(entityitem1.getItem().getItem())) {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
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
        return (item1 instanceof ItemFood) || (item1 instanceof ItemSeeds) || item1 == Items.WHEAT || item1 == Items.SUGAR || item1 == Items.CAKE
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
        List<Entity> list = mountEntity.world.getEntitiesWithinAABBExcludingEntity(mountEntity, mountEntity.getBoundingBox().expand(4D, 2D, 4D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (!(entity instanceof MobEntity)) {
                continue;
            }
            MobEntity entitymob = (MobEntity) entity;
            if (entitymob.getRidingEntity() == null
                    && (entitymob instanceof SkeletonEntity || entitymob instanceof ZombieEntity || entitymob instanceof MoCEntitySilverSkeleton)) {
                if (!mountEntity.world.isRemote) {
                    entitymob.startRiding(mountEntity);
                }
                break;
            }
        }
    }

    public static void copyDataFromOld(Entity source, Entity target) {
        CompoundNBT nbttagcompound = target.writeWithoutTypeId(new CompoundNBT());
        nbttagcompound.remove("Dimension");
        source.read(nbttagcompound);
    }

    public static boolean dismountSneakingPlayer(LivingEntity entity) {
        if (!entity.isPassenger()) return false;
        Entity entityRidden = entity.getRidingEntity();
        if (entityRidden instanceof LivingEntity && ((LivingEntity)entityRidden).isCrouching()) {
            entity.stopRiding();
            double dist = (-1.5D);
            double newPosX = entityRidden.getPosX() + (dist * Math.sin(((LivingEntity)entityRidden).renderYawOffset / 57.29578F));
            double newPosZ = entityRidden.getPosZ() - (dist * Math.cos(((LivingEntity)entityRidden).renderYawOffset / 57.29578F));
            entity.setPosition(newPosX, entityRidden.getPosY() + 2D, newPosZ);
            MoCTools.playCustomSound(entity, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }
        return false;
    }
}
