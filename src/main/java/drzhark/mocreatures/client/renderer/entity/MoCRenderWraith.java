package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelWraith;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWraith extends LivingRenderer<MoCEntityWraith, MoCModelWraith<MoCEntityWraith>> {

    public MoCRenderWraith(EntityRendererManager manager, MoCModelWraith model, float f) {
        //super(modelbiped, f, 1.0F);
        super(manager, model, f);
        //modelBipedMain = modelbiped;
    }

    @Override
    public void render(MoCEntityWraith entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        //boolean flag = wraith.isGlowing();
        boolean flag = false;

        GL11.glPushMatrix();
        GL11.glEnable(3042 /* GL_BLEND */);
        if (!flag) {
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        } else {
            GL11.glBlendFunc(770, 1);
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        GL11.glDisable(3042/* GL_BLEND */);
        GL11.glPopMatrix();
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWraith entitywraith) {
        return entitywraith.getTexture();
    }
}
