package drzhark.mocreatures.dimension;

import drzhark.mocreatures.dimension.feature.MoCWorldGenPortal;
import drzhark.mocreatures.registry.MoCDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Random;

//TODO: Fix the wyvern dimension teleporter
public class MoCDirectTeleporter implements ITeleporter {
//
//    private boolean portalDone;
//    protected final ServerWorld world;
//    protected final Random random;
//
//    public MoCDirectTeleporter(ServerWorld worldIn) {
//        world = worldIn;
//        random = new Random(worldIn.getSeed());
//    }
//
//    public boolean placeInPortal(Entity par1Entity, float rotationYaw) {
//        int var9 = MathHelper.floor(par1Entity.getPosX());
//        int var10 = MathHelper.floor(par1Entity.getPosY()) - 1;
//        int var11 = MathHelper.floor(par1Entity.getPosZ());
//        par1Entity.setLocationAndAngles(var9, var10, var11, par1Entity.rotationYaw, 0.0F);
//        par1Entity.setMotion(Vec3d.ZERO);
//        return true;
//    }
//
//    public void makePortal(World par1World, Random par2Random) {
//        MoCWorldGenPortal myPortal =
//                new MoCWorldGenPortal(Blocks.QUARTZ_BLOCK, 2, Blocks.QUARTZ_STAIRS, 0, Blocks.QUARTZ_BLOCK, 1, Blocks.QUARTZ_BLOCK, 0);
//
//
//
//        for (int i = 0; i < 14; i++) {
//            if (!this.portalDone) {
//                int randPosY = 58 + i;//par2Random.nextInt(8);
//                this.portalDone = myPortal.place(par1World,
//                        , par2Random, new BlockPos(0, randPosY, 0), new NoFeatureConfig());
//            }
//        }
//    }
//
//    private void buildFrame(World worldIn, BlockPos pos) {
//        if (world.getBlockState(pos).getBlock() == this.centerBlock || world.getBlockState(pos.down()).getBlock() == this.centerBlock
//                || world.getBlockState(pos.up()).getBlock() == this.centerBlock) {
//            return true;
//        }
//
//        if (world.isAirBlock(pos) || !world.isAirBlock(pos.up())) {
//            return false;
//        }
//
//        int x = pos.getX();
//        int y = pos.getY();
//        int z = pos.getZ();
//        //System.out.println("GENERATING Portal @ " + pos);
//        for (int nZ = z - 3; nZ < z + 3; nZ = nZ + 5) {
//            for (int nX = x - 2; nX < x + 2; nX++) {
//                if (nZ > z) {
////                    this.stairMetadata = 3;
//                }
//
//                world.setBlockState(new BlockPos(nX, y + 1, nZ), this.stairBlock.getStateFromMeta(this.stairMetadata), 2);
//            }
//        }
//
//        for (int nX = x - 2; nX < x + 2; nX++) {
//            for (int nZ = z - 2; nZ < z + 2; nZ++) {
//                world.setBlockState(new BlockPos(nX, y + 1, nZ), this.wallBlock.getStateFromMeta(this.wallMetadata), 2);
//            }
//        }
//
//        for (int nX = x - 1; nX < x + 1; nX++) {
//            for (int nZ = z - 1; nZ < z + 1; nZ++) {
//                world.setBlockState(new BlockPos(nX, y + 1, nZ), this.centerBlock.getStateFromMeta(this.centerMetadata), 2);
//            }
//        }
//
//        for (int j = x - 3; j < x + 3; j = j + 5) {
//            for (int nZ = z - 3; nZ < z + 3; nZ++) {
//                world.setBlockState(new BlockPos(j, y + 6, nZ), this.wallBlock.getStateFromMeta(this.wallMetadata), 2);
//            }
//        }
//
//        generatePillar(world, new BlockPos(x - 3, y, z - 3));
//        generatePillar(world, new BlockPos(x - 3, y, z + 2));
//        generatePillar(world, new BlockPos(x + 2, y, z - 3));
//        generatePillar(world, new BlockPos(x + 2, y, z + 2));
//    }
//
//    private boolean generatePillar(World world, BlockPos pos) {
//        for (int nY = pos.getY(); nY < pos.getY() + 6; nY++) {
//            world.setBlockState(new BlockPos(pos.getX(), nY, pos.getZ()), this.pillarBlock.getDefaultState(), 2);
//        }
//        return true;
//    }
}
