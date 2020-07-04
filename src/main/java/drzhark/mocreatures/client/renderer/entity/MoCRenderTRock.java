package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@OnlyIn(Dist.CLIENT)
public class MoCRenderTRock extends EntityRenderer<MoCEntityThrowableRock> {

    public MoCRenderTRock(EntityRendererManager manager) {
        super(manager);
        this.shadowSize = 0.5F;
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityThrowableRock entity) {
        return null;//this.getMyTexture((MoCEntityThrowableRock) par1Entity);
    }

    public void renderMyRock(MoCEntityThrowableRock entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        matrixStackIn.push();
        //GlStateManager.translate(-0.5F, -0.55F, 0.5F);
        matrixStackIn.translate(-0.5F, 0F, 0.5F);
        matrixStackIn.translate((float) par2, (float) par4, (float) par6);
        matrixStackIn.rotate(((100 - entitytrock.acceleration) / 10F) * 36F, 0F, -1F, 0.0F);
        int i = entitytrock.getBrightnessForRender();
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float lightLevel = entitytrock.getBrightness();
        blockrendererdispatcher.renderBlockBrightness(entitytrock.getState(), lightLevel);
        matrixStackIn.pop();
    }

    @Override
    public void render(MoCEntityThrowableRock par1Entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.renderMyRock(par1Entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected ResourceLocation getMyTexture(MoCEntityThrowableRock trock) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }
}
