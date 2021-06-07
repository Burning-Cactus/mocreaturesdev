package drzhark.mocreatures.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MoCEntityFXVanish extends SpriteTexturedParticle {

    private final double portalPosX;
    private final double portalPosY;
    private final double portalPosZ;
    private final boolean implode;

    public MoCEntityFXVanish(ClientWorld par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);

        this.rCol = 1F;
        this.gCol = 1F;
        this.bCol = 1F;
        this.xd = par8;
        this.yd = par10 * 5D;
        this.zd = par12;
        this.portalPosX = this.x = par2;
        this.portalPosY = this.y = par4;// + 0.7D;
        this.portalPosZ = this.z = par6;
        this.implode = false;
        this.lifetime = (int) (Math.random() * 10.0D) + 70;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        int speeder = 0;
        float sizeExp = 2.0F;
        if (this.implode) {
            speeder = (this.lifetime / 2);
            sizeExp = 5.0F;
        }

        float var1 = (float) (this.age + speeder) / (float) this.lifetime;
        float var2 = var1;
        var1 = -var1 + var1 * var1 * sizeExp;//5 insteaf of 2 makes an explosion
        var1 = 1.0F - var1;
        this.x = this.portalPosX + this.xd * var1;
        this.y = this.portalPosY + this.yd * var1 + (1.0F - var2);
        this.z = this.portalPosZ + this.zd * var1;

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

//    @Override
//    public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float par3, float par4, float par5, float par6, float par7) {
//        FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation("mocreatures", MoCProxy.MISC_TEXTURE + "fxvanish.png"));
//        float scale = 0.1F * this.particleScale;
//        float xPos = (float) (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
//        float yPos = (float) (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
//        float zPos = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
//        float colorIntensity = 1.0F;
//        int i = this.getBrightnessForRender(partialTicks);
//        int j = i >> 16 & 65535;
//        int k = i & 65535;
//        worldRendererIn.pos(xPos - par3 * scale - par6 * scale, yPos - par4 * scale, zPos - par5 * scale - par7 * scale).tex(0D, 1D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
//        worldRendererIn.pos(xPos - par3 * scale + par6 * scale, yPos + par4 * scale, zPos - par5 * scale + par7 * scale).tex(1D, 1D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
//        worldRendererIn.pos(xPos + par3 * scale + par6 * scale, yPos + par4 * scale, zPos + par5 * scale + par7 * scale).tex(1D, 0D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
//        worldRendererIn.pos(xPos + par3 * scale - par6 * scale, yPos - par4 * scale, zPos + par5 * scale - par7 * scale).tex(0D, 0D).color(this.particleRed * colorIntensity, this.particleGreen * colorIntensity, this.particleBlue * colorIntensity, 1.0F).lightmap(j, k).endVertex();
//    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            spriteSet = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            MoCEntityFXVanish vanishEffect = new MoCEntityFXVanish(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            vanishEffect.pickSprite(spriteSet);
            return vanishEffect;
        }
    }
}
