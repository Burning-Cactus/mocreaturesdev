package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelFirefly;
import drzhark.mocreatures.entity.ambient.FireflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderFirefly extends MoCRenderInsect<FireflyEntity, MoCModelFirefly<FireflyEntity>> {

    public MoCRenderFirefly(EntityRendererManager manager, MoCModelFirefly model, float shadowSize) {
        super(manager, model, shadowSize);
        //this.addLayer(new LayerMoCFirefly(this));
    }

    @Override
    protected void scale(FireflyEntity entityfirefly, MatrixStack stack, float par2) {
        if (entityfirefly.getIsFlying()) {
            rotateFirefly(entityfirefly, stack);
        } else if (entityfirefly.climbing()) {
            rotateAnimal(entityfirefly);
        }

    }

    protected void rotateFirefly(FireflyEntity entityfirefly, MatrixStack stack) {
        stack.mulPose(Vector3f.XN.rotationDegrees(40F));
    }

    @Override
    public ResourceLocation getTextureLocation(FireflyEntity entityfirefly) {
        return ((FireflyEntity) entityfirefly).getTexture();
    }
    /*
        private class LayerMoCFirefly implements LayerRenderer {

            private final MoCRenderFirefly mocRenderer;
            private final MoCModelFirefly mocModel = new MoCModelFirefly();

            public LayerMoCFirefly(MoCRenderFirefly p_i46112_1_) {
                this.mocRenderer = p_i46112_1_;
            }

            public void doRenderLayer(MoCEntityFirefly p_177162_1_, float p_177162_2_, float p_177162_3_, float p_177162_4_, float p_177162_5_,
                    float p_177162_6_, float p_177162_7_, float p_177162_8_) {
                this.setTailBrightness(p_177162_1_, p_177162_4_);
                this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
                this.mocModel.setLivingAnimations(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_4_);
                this.mocModel.render(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_5_, p_177162_6_, p_177162_7_, p_177162_8_);
            }

            
            protected void setTailBrightness(MoCEntityFirefly entityliving, float par3) {
                this.mocRenderer.bindTexture(MoCreatures.proxy.getTexture("fireflyglow.png"));
                float var4 = 1.0F;
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                char var5 = 61680;
                int var6 = var5 % 65536;
                int var7 = var5 / 65536;
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
            }

            @Override
            public boolean shouldCombineTextures() {
                return true;
            }

            @Override
            public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_,
                    float p_177141_6_, float p_177141_7_, float p_177141_8_) {
                this.doRenderLayer((MoCEntityFirefly) p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_,
                        p_177141_8_);
            }
        }*/
}
