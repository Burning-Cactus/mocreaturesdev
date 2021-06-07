package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelCricket;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderCricket<T extends MoCEntityCricket, M extends MoCModelCricket<T>> extends MoCRenderMoC<T, M> {

    public MoCRenderCricket(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);
    }


    @Override
    protected void scale(T entitycricket, MatrixStack matrixStack, float par2) {
        rotateCricket(entitycricket, matrixStack);
    }

    protected void rotateCricket(MoCEntityCricket entitycricket, MatrixStack matrixStackIn) {
        if (entitycricket.hasImpulse) {
            if (entitycricket.getDeltaMovement().y > 0.5D) {
                GL11.glRotatef(35F, -1F, 0.0F, 0.0F); //TODO: Move away from GL calls
            } else if (entitycricket.getDeltaMovement().y < -0.5D) {
                GL11.glRotatef(-35F, -1F, 0.0F, 0.0F);
            } else {
                GL11.glRotatef((float) (entitycricket.getDeltaMovement().y * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T par1Entity) {
        return (par1Entity).getTexture();
    }
}
