package drzhark.mocreatures.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;

import java.util.Random;

import javax.annotation.Nullable;

public class RandomPositionGeneratorMoCFlyer
{
    /**
     * used to store a driection when the user passes a point to move towards or away from. WARNING: NEVER THREAD SAFE.
     * MULTIPLE findTowards and findAway calls, will share this var
     */
    private static Vector3d staticVector = Vector3d.ZERO;

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks
     */
    @Nullable
    public static Vector3d findRandomTarget(CreatureEntity entitycreatureIn, int xz, int y)
    {
        /**
         * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction
         * of par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
         */
        return findRandomTargetBlock(entitycreatureIn, xz, y, (Vector3d)null);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the direction of the point par3
     */
    @Nullable
    public static Vector3d findRandomTargetBlockTowards(CreatureEntity entitycreatureIn, int xz, int y, Vector3d targetVec3)
    {
        staticVector = targetVec3.subtract(entitycreatureIn.getX(), entitycreatureIn.getY(), entitycreatureIn.getZ());
        /**
         * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction
         * of par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
         */
        return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
    }

    /**
     * finds a random target within par1(x,z) and par2 (y) blocks in the reverse direction of the point par3
     */
    @Nullable
    public static Vector3d findRandomTargetBlockAwayFrom(CreatureEntity entitycreatureIn, int xz, int y, Vector3d targetVec3)
    {
        staticVector = (new Vector3d(entitycreatureIn.getX(), entitycreatureIn.getY(), entitycreatureIn.getZ())).subtract(targetVec3);
        /**
         * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction
         * of par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
         */
        return findRandomTargetBlock(entitycreatureIn, xz, y, staticVector);
    }

    /**
     * searches 10 blocks at random in a within par1(x,z) and par2 (y) distance, ignores those not in the direction of
     * par3Vec3, then points to the tile for which creature.getBlockPathWeight returns the highest number
     */
    @Nullable
    private static Vector3d findRandomTargetBlock(CreatureEntity entitycreatureIn, int xz, int y, @Nullable Vector3d targetVec3)
    {
        PathNavigator pathnavigate = entitycreatureIn.getNavigation();
        Random random = entitycreatureIn.getRandom();
        boolean flag = false;
        int i = 0;
        int j = 0;
        int k = 0;
        float f = -99999.0F;
        boolean flag1;

        if (false/*entitycreatureIn.hasHome()*/)
        {
            double d0 = entitycreatureIn.getRestrictCenter().distSqr(new Vector3i(MathHelper.floor(entitycreatureIn.getX()), MathHelper.floor(entitycreatureIn.getY()), MathHelper.floor(entitycreatureIn.getZ()))) + 4.0D;
            double d1 = (double)(entitycreatureIn.getRestrictRadius() + (float)xz);
            flag1 = d0 < d1 * d1;
        }
        else
        {
            flag1 = false;
        }

        for (int j1 = 0; j1 < 10; ++j1)
        {
            int l = random.nextInt(2 * xz + 1) - xz;
            int k1 = random.nextInt(2 * y + 1) - y;
            int i1 = random.nextInt(2 * xz + 1) - xz;

            if (targetVec3 == null || (double)l * targetVec3.x + (double)i1 * targetVec3.z >= 0.0D)
            {
                if (false/*entitycreatureIn.hasHome() && xz > 1*/) //TODO: Fix entity AI
                {
                    BlockPos blockpos = entitycreatureIn.getRestrictCenter();

                    if (entitycreatureIn.getX() > (double)blockpos.getX())
                    {
                        l -= random.nextInt(xz / 2);
                    }
                    else
                    {
                        l += random.nextInt(xz / 2);
                    }

                    if (entitycreatureIn.getZ() > (double)blockpos.getZ())
                    {
                        i1 -= random.nextInt(xz / 2);
                    }
                    else
                    {
                        i1 += random.nextInt(xz / 2);
                    }
                }

                BlockPos blockpos1 = new BlockPos((double)l + entitycreatureIn.getX(), (double)k1 + entitycreatureIn.getY(), (double)i1 + entitycreatureIn.getZ());

                if ((!flag1 || entitycreatureIn.isWithinRestriction(blockpos1)))// && pathnavigate.canEntityStandOnPos(blockpos1))
                {
                    float f1 = entitycreatureIn.getWalkTargetValue(blockpos1);

                    if (f1 > f)
                    {
                        f = f1;
                        i = l;
                        j = k1;
                        k = i1;
                        flag = true;
                    }
                }
            }
        }

        if (flag)
        {
            return new Vector3d((double)i + entitycreatureIn.getX(), (double)j + entitycreatureIn.getY(), (double)k + entitycreatureIn.getZ());
        }
        else
        {
            return null;
        }
    }
}