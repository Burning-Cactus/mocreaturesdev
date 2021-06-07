package drzhark.mocreatures.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXStar extends SpriteTexturedParticle {

    public MoCEntityFXStar(ClientWorld world, double posX, double posY, double posZ) {
        super(world, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        this.xd *= 0.8D;
        this.yd *= 0.8D;
        this.zd *= 0.8D;
        this.yd = this.random.nextFloat() * 0.4F + 0.05F;

        this.rCol = 1F;
        this.gCol = 1F;
        this.bCol = 1F;

        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.lifetime = (int) (64.0D / (Math.random() * 0.8D + 0.2D));
        this.quadSize *= 0.6F; //it was 0.8 for the old star //0.4 if I'm not using the shrinking
    }

    /**
     * sets which texture to use (2 = items.png)
     */
//    @Override
//    public int getFXLayer() {
//        return 1;
//    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.quadSize *= 0.995F; //slowly shrinks it

        this.yd -= 0.03D;
        this.move(this.xd, this.yd, this.zd);
        this.xd *= 0.9D;
        this.yd *= 0.2D;
        this.zd *= 0.9D;

        if (this.onGround) {
            this.xd *= 0.7D;
            this.zd *= 0.7D;
        }

        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

//    @Override
//    public void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks) {
//        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("mocreatures", MoCProxy.MISC_TEXTURE + "fxstar.png"));
//        float sizeFactor = 0.1F * this.particleScale;
//        float var13 = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
//        float var14 = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
//        float var15 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
//        float var16 = 1.2F - ((float) Math.random() * 0.5F);
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

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MoCEntityFXStar starParticle = new MoCEntityFXStar(worldIn, x, y, z);
            starParticle.pickSprite(spriteSet);
            return starParticle;
        }
    }
}
