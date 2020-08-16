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
    protected boolean canNavigate() {
        return true;
    }

    protected Vector3d getEntityPosition() {
        return new Vector3d(this.entity.getPosX(), this.entity.getPosY() + (double) this.entity.getHeight() * 0.5D, this.entity.getPosZ());
    }

    @Override
    protected PathFinder getPathFinder(int p_179679_1_) {
        return null;
    }

    protected void pathFollow() {
        Vector3d vec3 = this.getEntityPosition();
        float f = this.entity.getWidth() * this.entity.getWidth();
        byte b0 = 6;

        if (vec3.squareDistanceTo(this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex())) < (double) f) {
            this.currentPath.incrementPathIndex();
        }

        for (int i = Math.min(this.currentPath.getCurrentPathIndex() + b0, this.currentPath.getCurrentPathLength() - 1); i > this.currentPath
                .getCurrentPathIndex(); --i) {
            Vector3d vec31 = this.currentPath.getVectorFromIndex(this.entity, i);

            if (vec31.squareDistanceTo(vec3) <= 36.0D && this.isDirectPathBetweenPoints(vec3, vec31, 0, 0, 0)) {
                this.currentPath.setCurrentPathIndex(i);
                break;
            }
        }

        this.checkForStuck(vec3);
    }

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    @Override
    protected boolean isDirectPathBetweenPoints(Vector3d posVec31, Vector3d posVec32, int sizeX, int sizeY, int sizeZ)
    {
        Vector3d vec2 = new Vector3d(posVec32.x, posVec32.y + (double)this.entity.getHeight() * 0.5D, posVec32.z);
        RayTraceResult rayTrace = this.world.rayTraceBlocks(new RayTraceContext(posVec31, vec2, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.ANY, entity));
        return rayTrace.getType() == RayTraceResult.Type.MISS;
    }
}
