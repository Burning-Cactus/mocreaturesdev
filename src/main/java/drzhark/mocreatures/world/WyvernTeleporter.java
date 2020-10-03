package drzhark.mocreatures.world;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Random;
import java.util.function.Function;

public class WyvernTeleporter implements ITeleporter {
    private boolean portalDone;
    protected final ServerWorld world;
    protected final Random random;

    public WyvernTeleporter(ServerWorld worldIn) {
        world = worldIn;
        random = new Random(worldIn.getSeed());
    }

    public boolean placeInPortal(Entity par1Entity, float rotationYaw) {
        int var9 = MathHelper.floor(par1Entity.getPosX());
        int var10 = MathHelper.floor(par1Entity.getPosY()) - 1;
        int var11 = MathHelper.floor(par1Entity.getPosZ());
        par1Entity.setLocationAndAngles(var9, var10, var11, par1Entity.rotationYaw, 0.0F);
        par1Entity.setMotion(Vector3d.ZERO);
        return true;
    }

    public void makePortal(ServerWorld worldIn, Random par2Random) {
        for (int i = 0; i < 14; i++) {
            if (!this.portalDone) {
                int randPosY = 58 + i;//par2Random.nextInt(8);
                this.portalDone = buildFrame(worldIn, new BlockPos(0, randPosY, 0));
            }
        }
    }

    private boolean buildFrame(ServerWorld worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).getBlock() == Blocks.QUARTZ_BLOCK || worldIn.getBlockState(pos.down()).getBlock() == Blocks.QUARTZ_BLOCK
                || worldIn.getBlockState(pos.up()).getBlock() == Blocks.QUARTZ_BLOCK) {
            return true;
        }

        if (worldIn.isAirBlock(pos) || !worldIn.isAirBlock(pos.up())) {
            return false;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        //System.out.println("GENERATING Portal @ " + pos);
        for (int nZ = z - 3; nZ < z + 3; nZ = nZ + 5) {
            for (int nX = x - 2; nX < x + 2; nX++) {

                worldIn.setBlockState(new BlockPos(nX, y + 1, nZ), Blocks.QUARTZ_STAIRS.getDefaultState(), 2);
            }
        }

        for (int nX = x - 2; nX < x + 2; nX++) {
            for (int nZ = z - 2; nZ < z + 2; nZ++) {
                worldIn.setBlockState(new BlockPos(nX, y + 1, nZ), Blocks.CHISELED_QUARTZ_BLOCK.getDefaultState(), 2);
            }
        }

        for (int nX = x - 1; nX < x + 1; nX++) {
            for (int nZ = z - 1; nZ < z + 1; nZ++) {
                worldIn.setBlockState(new BlockPos(nX, y + 1, nZ), Blocks.QUARTZ_BLOCK.getDefaultState(), 2);
            }
        }

        for (int j = x - 3; j < x + 3; j = j + 5) {
            for (int nZ = z - 3; nZ < z + 3; nZ++) {
                worldIn.setBlockState(new BlockPos(j, y + 6, nZ), Blocks.CHISELED_QUARTZ_BLOCK.getDefaultState(), 2);
            }
        }

        generatePillar(worldIn, new BlockPos(x - 3, y, z - 3));
        generatePillar(worldIn, new BlockPos(x - 3, y, z + 2));
        generatePillar(worldIn, new BlockPos(x + 2, y, z - 3));
        generatePillar(worldIn, new BlockPos(x + 2, y, z + 2));

        return true;
    }

    private void generatePillar(ServerWorld worldIn, BlockPos pos) {
        for(int nY = pos.getY(); nY < pos.getY() + 6; nY++) {
            worldIn.setBlockState(new BlockPos(pos.getX(), nY, pos.getZ()), Blocks.QUARTZ_PILLAR.getDefaultState(), 2);
        }
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return null;
    }
}
