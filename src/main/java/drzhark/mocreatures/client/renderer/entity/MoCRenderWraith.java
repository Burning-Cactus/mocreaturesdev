package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.MoCModelWraith;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWraith extends LivingRenderer<MoCEntityWraith, MoCModelWraith<MoCEntityWraith>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/entity/wraith.png");

    public MoCRenderWraith(EntityRendererManager manager, MoCModelWraith<MoCEntityWraith> model, float f) {
        //super(modelbiped, f, 1.0F);
        super(manager, model, f);
        //modelBipedMain = modelbiped;
    }

    @Override
    public void render(MoCEntityWraith entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        //boolean flag = wraith.isGlowing();
        boolean flag = false;

        matrixStackIn.push();
//        GL11.glEnable(3042 /* GL_BLEND */);
//        if (!flag) {
//            float transparency = 0.6F;
//            GL11.glBlendFunc(770, 771);
//            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
//        } else {
//            GL11.glBlendFunc(770, 1);
//        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//        GL11.glDisable(3042/* GL_BLEND */);
        matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWraith entitywraith) {
        return TEXTURE;
    }
}
