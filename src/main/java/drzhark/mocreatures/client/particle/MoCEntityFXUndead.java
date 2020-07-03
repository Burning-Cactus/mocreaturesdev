package drzhark.mocreatures.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXUndead extends SpriteTexturedParticle {

    public MoCEntityFXUndead(World par1World, double x, double y, double z) {
        super(par1World, x, y, z, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.8D;
        this.motionY *= 0.8D;
        this.motionZ *= 0.8D;
        this.motionY = this.rand.nextFloat() * 0.4F + 0.05F;

        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.maxAge = (int) (32.0D / (Math.random() * 0.8D + 0.2D));
        this.particleScale *= 0.8F;
    }

    /**
     * sets which texture to use (2 = items.png)
     */
//    @Override
//    public int getFXLayer() {
//        if (this.onGround) {
//            return 1;
//        }
//        return 2;
//    } TODO: Select textures properly

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.motionY -= 0.03D;
        this.move(this.motionX, this.motionY, this.motionZ);

        this.motionX *= 0.8D;
        this.motionY *= 0.5D;
        this.motionZ *= 0.8D;

        if (this.onGround) {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
        }

        if (this.maxAge-- <= 0) {
            this.setExpired();
        }
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    private String getCurrentTexture() {
        if (this.onGround) {
            return "fxundead1.png";
        }
        return "fxundead2.png";
    }

//    @Override
//    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
//        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("mocreatures", MoCProxy.MISC_TEXTURE
//                + getCurrentTexture()));
//        float sizeFactor = 0.1F * this.particleScale;
//        float var13 = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
//        float var14 = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
//        float var15 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
//        float var16 = 1F;
//        int i = this.getBrightnessForRender(partialTicks);
//        int j = i >> 16 & 65535;
//        int k = i & 65535;
//        worldRendererIn.pos(var13 - par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 - par5 * sizeFactor - par7
//                * sizeFactor).tex(0D, 1D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
//        worldRendererIn.pos(var13 - par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 - par5 * sizeFactor + par7
//                * sizeFactor).tex(1D, 1D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
//        worldRendererIn.pos(var13 + par3 * sizeFactor + par6 * sizeFactor, var14 + par4 * sizeFactor, var15 + par5 * sizeFactor + par7
//                * sizeFactor).tex(1D, 0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
//        worldRendererIn.pos(var13 + par3 * sizeFactor - par6 * sizeFactor, var14 - par4 * sizeFactor, var15 + par5 * sizeFactor - par7
//                * sizeFactor).tex(0D, 0D).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, 1.0F).lightmap(j, k).endVertex();
//    }


    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            spriteSet = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MoCEntityFXUndead undeadParticle = new MoCEntityFXUndead(worldIn, x, y, z);
            undeadParticle.selectSpriteRandomly(spriteSet);
            return undeadParticle;
        }
    }

}
