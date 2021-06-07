package drzhark.mocreatures.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PathNavigateFlyerMoC extends PathNavigator {

    public PathNavigateFlyerMoC(LivingEntity entitylivingIn, World worldIn) {
        super((MobEntity)entitylivingIn, worldIn);
    }

    protected PathFinder getPathFinder() {
        return new PathFinder(new FlyNodeProcessor(), 0);
    }

    /**
     * If on ground or swimming and can swim
     */
    protected boolean canUpdatePath() {
        return true;
    }

    protected Vector3d getTempMobPos() {
        return new Vector3d(this.mob.getX(), this.mob.getY() + (double) this.mob.getBbHeight() * 0.5D, this.mob.getZ());
    }

    @Override
    protected PathFinder createPathFinder(int p_179679_1_) {
        return null;
    }

    protected void followThePath() {
        Vector3d vec3 = this.getTempMobPos();
        float f = this.mob.getBbWidth() * this.mob.getBbWidth();
        byte b0 = 6;

        if (vec3.distanceToSqr(this.path.getEntityPosAtNode(this.mob, this.path.getNextNodeIndex())) < (double) f) {
            this.path.advance();
        }

        for (int i = Math.min(this.path.getNextNodeIndex() + b0, this.path.getNodeCount() - 1); i > this.path
                .getNextNodeIndex(); --i) {
            Vector3d vec31 = this.path.getEntityPosAtNode(this.mob, i);

            if (vec31.distanceToSqr(vec3) <= 36.0D && this.canMoveDirectly(vec3, vec31, 0, 0, 0)) {
                this.path.setNextNodeIndex(i);
                break;
            }
        }

        this.doStuckDetection(vec3);
    }

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    @Override
    protected boolean canMoveDirectly(Vector3d posVec31, Vector3d posVec32, int sizeX, int sizeY, int sizeZ)
    {
        Vector3d vec2 = new Vector3d(posVec32.x, posVec32.y + (double)this.mob.getBbHeight() * 0.5D, posVec32.z);
        RayTraceResult rayTrace = this.level.clip(new RayTraceContext(posVec31, vec2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, mob));
        return rayTrace.getType() == RayTraceResult.Type.MISS;
    }
}
