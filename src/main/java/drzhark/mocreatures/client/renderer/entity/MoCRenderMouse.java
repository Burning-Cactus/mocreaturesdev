package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelMouse;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderMouse extends MoCRenderMoC<MoCEntityMouse, MoCModelMouse> {

    public MoCRenderMouse(EntityRendererManager manager, MoCModelMouse modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    protected float handleRotationFloat(MoCEntityMouse entitymouse, float f) {
        stretch(entitymouse);
        return entitymouse.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityMouse entitymouse, MatrixStack stack, float f) {
        if (entitymouse.upsideDown()) {
            upsideDown(entitymouse, stack);

        }
        if (entitymouse.climbing()) {
            rotateAnimal(entitymouse, stack);
        }
    }

    protected void rotateAnimal(MoCEntityMouse entitymouse, MatrixStack stack) {
        stack.rotate(Vector3f.XN.rotationDegrees(90F));
    }

    protected void stretch(MoCEntityMouse entitymouse) {
        float f = 0.6F;
        GL11.glScalef(f, f, f);
    }

    protected void upsideDown(MoCEntityMouse entitymouse, MatrixStack stack) {
        stack.rotate(Vector3f.XN.rotationDegrees(-90F));
        //GL11.glTranslatef(-0.55F, 0F, -0.7F);
        stack.translate(-0.55F, 0F, 0F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityMouse entitymouse) {
        return entitymouse.getTexture();
    }
}
