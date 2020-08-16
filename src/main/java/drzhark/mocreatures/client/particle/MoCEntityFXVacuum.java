package drzhark.mocreatures.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXVacuum extends SpriteTexturedParticle {

    private final float portalParticleScale;
    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;

    public MoCEntityFXVacuum(ClientWorld par1World, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(par1World, x, y, z, xSpeed, ySpeed, zSpeed);

        this.particleRed = 1F;
        this.particleGreen = 1F;
        this.particleBlue = 1F;

        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.portalPosX = this.posX = x;
        this.portalPosY = this.posY = y;// + 0.7D;
        this.portalPosZ = this.posZ = z;
        this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
//        this.setParticleTextureIndex(partTexture);
        this.maxAge = (int) (Math.random() * 10.0D) + 30;
    }

    @Override
    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
        float var8 = (this.age + partialTicks) / this.maxAge;
        var8 = 1.0F - var8;
        var8 *= var8;
        var8 = 1.0F - var8;
        this.particleScale = this.portalParticleScale * var8;
        super.renderParticle(buffer, renderInfo, partialTicks);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float par1) {
        int var2 = super.getBrightnessForRender(par1);
        float var3 = (float) this.age / (float) this.maxAge;
        var3 *= var3;
        var3 *= var3;
        int var4 = var2 & 255;
        int var5 = var2 >> 16 & 255;
        var5 += (int) (var3 * 15.0F * 16.0F);

        if (var5 > 240) {
            var5 = 240;
        }

        return var4 | var5 << 16;
    }

    /*@Override
    public float getBrightness(float par1) {
        float var2 = super.getBrightness(par1);
        float var3 = (float) this.particleAge / (float) this.particleMaxAge;
        var3 = var3 * var3 * var3 * var3;
        return var2 * (1.0F - var3) + var3;
    }*/

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float var1 = (float) this.age / (float) this.maxAge;
        float var2 = var1;
        var1 = -var1 + var1 * var1 * 2.0F;
        var1 = 1.0F - var1;
        this.posX = this.portalPosX + this.motionX * var1;
        this.posY = this.portalPosY + this.motionY * var1 + (1.0F - var2);
        this.posZ = this.portalPosZ + this.motionZ * var1;

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        }
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            spriteSet = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MoCEntityFXVacuum vacuumParticle = new MoCEntityFXVacuum(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            return null;
        }
    }
}
