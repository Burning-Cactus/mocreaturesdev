package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelSnake;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderSnake extends MoCRenderMoC<MoCEntitySnake, MoCModelSnake<MoCEntitySnake>> {

    public MoCRenderSnake(EntityRendererManager manager, MoCModelSnake modelbase, float f) {
        super(manager, modelbase, 0.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntitySnake par1Entity) {
        return par1Entity.getTexture();
    }

    protected void adjustHeight(MoCEntitySnake entitysnake, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void scale(MoCEntitySnake entitysnake, MatrixStack stack, float f) {
        stretch(entitysnake, stack);

        /*
         * if(mod_mocreatures.mc.isMultiplayerWorld() &&
         * (entitysnake.pickedUp())) { GL11.glTranslatef(0.0F, 1.4F, 0.0F); }
         */

        if (entitysnake.pickedUp())// && entitysnake.getSizeF() < 0.6F)
        {
            float xOff = (entitysnake.getSizeF() - 1.0F);
            if (xOff > 0.0F) {
                xOff = 0.0F;
            }
            if (entitysnake.level.isClientSide) {
                stack.translate(xOff, 0.0F, 0F);
            } else {
                stack.translate(xOff, 0F, 0.0F);
                //-0.5 puts it in the right shoulder
            }
            /*
             * //if(small) //works for small snakes GL11.glRotatef(20F, 1F, 0F,
             * 0F); if(mod_mocreatures.mc.isMultiplayerWorld()) {
             * GL11.glTranslatef(-0.5F, 1.4F, 0F); } else {
             * GL11.glTranslatef(0.7F, 0F, 1.2F); }
             */
        }

        if (entitysnake.isInWater()) {
            adjustHeight(entitysnake, -0.25F);
        }

        super.scale(entitysnake, stack, f);
    }

    protected void stretch(MoCEntitySnake entitysnake, MatrixStack stack) {
        float f = entitysnake.getSizeF();
        stack.scale(f, f, f);
    }

    /*
     * @Override protected void preRenderCallback(EntityLiving entityliving,
     * float f) { MoCEntitySnake entitysnake = (MoCEntitySnake) entityliving;
     * //tempSnake.textPos = entitysnake.type - 1; if (entitysnake.type <4) {
     * tempSnake.textPos = 0; }else { tempSnake.textPos = 1; }
     * super.preRenderCallback(entityliving, f); } private MoCModelSnake
     * tempSnake;
     */
}
