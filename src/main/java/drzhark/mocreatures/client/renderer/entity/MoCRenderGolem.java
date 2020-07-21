package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGolem extends MoCRenderMoC<MoCEntityGolem, MoCModelGolem> {

    public MoCRenderGolem(EntityRendererManager manager, MoCModelGolem modelbase, float f) {
        super(manager, modelbase, f);
        this.addLayer(new LayerMoCGolem(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityGolem par1Entity) {
        return ((MoCEntityGolem) par1Entity).getTexture();
    }

    private class LayerMoCGolem extends LayerRenderer<MoCEntityGolem, MoCModelGolem> {

        private final MoCRenderGolem mocRenderer;
        private final MoCModelGolem mocModel = new MoCModelGolem();

        public LayerMoCGolem(MoCRenderGolem render) {
            super(render);
            this.mocRenderer = render;
        }

//        @Override
//        public boolean shouldCombineTextures() {
//            return true;
//        }

        @Override //TODO: Fix Golem rendering
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityGolem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

            ResourceLocation effectTexture = entitylivingbaseIn.getEffectTexture();
            if (effectTexture != null) {
//                GL11.glDepthMask(false);

                float var4 = entitylivingbaseIn.ticksExisted + limbSwingAmount;
//                bindTexture(effectTexture);
//                GL11.glMatrixMode(GL11.GL_TEXTURE);
//                GL11.glLoadIdentity();
                float var5 = var4 * 0.01F;
                float var6 = var4 * 0.01F;
                matrixStackIn.translate(var5, var6, 0.0F);
                // TODO
                //this.setRenderPassModel(this.MoCModelG);
//                GL11.glMatrixMode(GL11.GL_MODELVIEW);
//                GL11.glEnable(GL11.GL_BLEND);
                float var7 = 0.5F;
//                GL11.glColor4f(var7, var7, var7, 1.0F);
//                GL11.glDisable(GL11.GL_LIGHTING);
//                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

//                this.mocModel.setModelAttributes(this.mocRenderer.getEntityModel());
//                this.mocModel.setLivingAnimations(entitylivingbaseIn, packedLightIn, getPackedOverlay(entitylivingbaseIn, 0.0F), partialTicks);
//                this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
//                GL11.glMatrixMode(GL11.GL_TEXTURE);
//                GL11.glLoadIdentity();
//                GL11.glMatrixMode(GL11.GL_MODELVIEW);
//                GL11.glEnable(GL11.GL_LIGHTING);
//                GL11.glDisable(GL11.GL_BLEND);
            }
        }
    }
}
