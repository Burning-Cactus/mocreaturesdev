package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelCrocodile;
import drzhark.mocreatures.entity.passive.MoCEntityCrocodile;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderCrocodile<T extends MoCEntityCrocodile, M extends MoCModelCrocodile<T>> extends LivingRenderer<T, M> {

    public MoCRenderCrocodile(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);
        this.croc = modelbase;
    }

    @Override
    public ResourceLocation getEntityTexture(T entitycrocodile) {
        return (entitycrocodile).getTexture();
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(MoCEntityCrocodile entitycrocodile, MatrixStack matrixStack, float f) {
        this.croc.biteProgress = entitycrocodile.biteProgress;
        this.croc.swimming = entitycrocodile.isSwimming();
        this.croc.resting = entitycrocodile.getIsSitting();
        if (entitycrocodile.isSpinning()) {
            spinCroc(entitycrocodile, (LivingEntity) entitycrocodile.getRidingEntity());
        }
        stretch(entitycrocodile);
        if (entitycrocodile.getIsSitting()) {
            if (!entitycrocodile.isInWater()) {
                adjustHeight(entitycrocodile, 0.2F);
            } else {
                //adjustHeight(entitycrocodile, 0.1F);
            }

        }
        // if(!entitycrocodile.getIsAdult()) { }
    }

    protected void rotateAnimal(MoCEntityCrocodile entitycrocodile) {

        //float f = entitycrocodile.swingProgress *10F *entitycrocodile.getFlipDirection();
        //float f2 = entitycrocodile.swingProgress /30 *entitycrocodile.getFlipDirection();
        //GL11.glRotatef(180F + f, 0.0F, 0.0F, -1.0F);
        //GL11.glTranslatef(0.0F-f2, 0.5F, 0.0F);
    }

    protected void adjustHeight(MoCEntityCrocodile entitycrocodile, float FHeight) {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    protected void spinCroc(MoCEntityCrocodile entitycrocodile, LivingEntity prey) {
        int intSpin = entitycrocodile.spinInt;
        int direction = 1;
        if (intSpin > 40) {
            intSpin -= 40;
            direction = -1;
        }
        int intEndSpin = intSpin;
        if (intSpin >= 20) {
            intEndSpin = (20 - (intSpin - 20));
        }
        if (intEndSpin == 0) {
            intEndSpin = 1;
        }
        float f3 = (((intEndSpin) - 1.0F) / 20F) * 1.6F;
        f3 = MathHelper.sqrt(f3);
        if (f3 > 1.0F) {
            f3 = 1.0F;
        }
        f3 *= direction;
        GL11.glRotatef(f3 * 90F, 0.0F, 0.0F, 1.0F);

        if (prey != null) {
            prey.deathTime = intEndSpin;
        }
    }

    protected void stretch(MoCEntityCrocodile entitycrocodile) {
        // float f = 1.3F;
        float f = entitycrocodile.getEdad() * 0.01F;
        // if(!entitycrocodile.getIsAdult()) { f = entitycrocodile.edad; }
        GL11.glScalef(f, f, f);
    }

    public MoCModelCrocodile croc;

}
