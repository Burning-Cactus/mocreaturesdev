package drzhark.mocreatures.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.FlaggedPathPoint;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.client.model.b3d.B3DModel;

import javax.annotation.Nullable;

public class FlyNodeProcessor extends NodeProcessor {

    public PathPoint getStart()
    {
        return this.openPoint(MathHelper.floor(this.entity.getBoundingBox().minX), MathHelper.floor(this.entity.getBoundingBox().minY + 0.5D), MathHelper.floor(this.entity.getBoundingBox().minZ));
    }

    @Override
    public FlaggedPathPoint func_224768_a(double v, double v1, double v2) {
        return null;
    }

    @Override
    public int func_222859_a(PathPoint[] pathPoints, PathPoint pathPoint) {
        return 0;
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int i, int i1, int i2, MobEntity mobEntity, int i3, int i4, int i5, boolean b, boolean b1) {
        return PathNodeType.OPEN;
    }

    @Override
    public PathNodeType getPathNodeType(IBlockReader iBlockReader, int i, int i1, int i2) {
        return PathNodeType.OPEN;
    }

    /**
     * Returns PathPoint for given coordinates
     */
    public PathPoint getPathPointToCoords(double x, double y, double z)
    {
        return this.openPoint(MathHelper.floor(x - (double)(this.entity.getWidth() / 2.0F)), MathHelper.floor(y + 0.5D), MathHelper.floor(z - (double)(this.entity.getWidth() / 2.0F)));
    }


    public int findPathOptions(PathPoint[] pathOptions, PathPoint currentPoint, PathPoint targetPoint, float maxDistance)
    {
        int i = 0;

        for (Direction enumfacing : Direction.values())
        {
            PathPoint pathpoint = this.getSafePoint(currentPoint.x + enumfacing.getXOffset(), currentPoint.y + enumfacing.getYOffset(), currentPoint.z + enumfacing.getZOffset());

            if (pathpoint != null && !pathpoint.visited && pathpoint.distanceTo(targetPoint) < maxDistance)
            {
                pathOptions[i++] = pathpoint;
            }
        }

        return i;
    }

    @Nullable
    private PathPoint getSafePoint(int p_186328_1_, int p_186328_2_, int p_186328_3_)
    {
        PathNodeType pathnodetype = this.isFree(p_186328_1_, p_186328_2_, p_186328_3_);
        return pathnodetype == PathNodeType.OPEN ? this.openPoint(p_186328_1_, p_186328_2_, p_186328_3_) : null;
    }

    
    private PathNodeType isFree(int p_186327_1_, int p_186327_2_, int p_186327_3_)
    {
        BlockPos.Mutable blockpos$mutableblockpos = new BlockPos.Mutable();

        for (int i = p_186327_1_; i < p_186327_1_ + this.entitySizeX; ++i)
        {
            for (int j = p_186327_2_; j < p_186327_2_ + this.entitySizeY; ++j)
            {
                for (int k = p_186327_3_; k < p_186327_3_ + this.entitySizeZ; ++k)
                {
                    BlockState iblockstate = this.blockaccess.getBlockState(blockpos$mutableblockpos.setPos(i, j, k));

                    if (iblockstate.getMaterial() != Material.AIR)
                    {
                        return PathNodeType.BLOCKED;
                    }
                }
            }
        }

        return PathNodeType.OPEN;
    }

}
