package drzhark.mocreatures.dimension;

import drzhark.mocreatures.dimension.feature.MoCWorldGenPortal;
import drzhark.mocreatures.init.MoCDimensions;
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

public class MoCDirectTeleporter implements ITeleporter {

    private boolean portalDone;
    protected final ServerWorld world;
    protected final Random random;

    public MoCDirectTeleporter(ServerWorld worldIn) {
        world = worldIn;
        random = new Random(worldIn.getSeed());
    }

    public void placeInPortal(Entity par1Entity, float rotationYaw) {
        int var9 = MathHelper.floor(par1Entity.getPosX());
        int var10 = MathHelper.floor(par1Entity.getPosY()) - 1;
        int var11 = MathHelper.floor(par1Entity.getPosZ());
        par1Entity.setLocationAndAngles(var9, var10, var11, par1Entity.rotationYaw, 0.0F);
        par1Entity.setMotion(Vec3d.ZERO);
    }

    public void makePortal(World par1World, Random par2Random) {
        MoCWorldGenPortal myPortal =
                new MoCWorldGenPortal(Blocks.QUARTZ_BLOCK, 2, Blocks.QUARTZ_STAIRS, 0, Blocks.QUARTZ_BLOCK, 1, Blocks.QUARTZ_BLOCK, 0);
        for (int i = 0; i < 14; i++) {
            if (!this.portalDone) {
                int randPosY = 58 + i;//par2Random.nextInt(8);
                this.portalDone = myPortal.place(par1World, , par2Random, new BlockPos(0, randPosY, 0), new NoFeatureConfig());
            }
        }
    }
}
