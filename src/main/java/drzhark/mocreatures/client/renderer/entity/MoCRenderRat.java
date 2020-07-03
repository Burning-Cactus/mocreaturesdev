package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderRat<T extends MoCEntityRat> extends LivingRenderer<T, MoCModelRat<T>> {

    public MoCRenderRat(EntityRendererManager manager, MoCModelRat modelbase, float f) {
        super(manager, modelbase, f);
    }

    @Override
    public void doRender(T entityrat, double d, double d1, double d2, float f, float f1) {
        super.doRender(entityrat, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(T entityrat, float f) {
        stretch(entityrat);
        return entityrat.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(T entityrat, MatrixStack stack, float f) {
        if (entityrat.climbing()) {
            rotateAnimal(entityrat);
        }
    }

    protected void rotateAnimal(T entityrat) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F); //TODO: GL calls
    }

    protected void stretch(T entityrat, MatrixStack stack) {
        float f = 0.8F;
        stack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(T entityrat) {
        return entityrat.getTexture();
    }
}
