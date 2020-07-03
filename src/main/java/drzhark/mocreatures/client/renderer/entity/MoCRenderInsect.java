package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCRenderInsect<T extends MoCEntityInsect, M extends EntityModel<T>> extends MoCRenderMoC<T, M> {

    public MoCRenderInsect(EntityRendererManager manager, M modelbase, float f) {
        super(manager, modelbase, f);

    }

    @Override
    protected void preRenderCallback(T entityinsect, MatrixStack stack, float par2) {
        if (entityinsect.climbing()) {
            rotateAnimal(entityinsect);
        }
        stretch(entityinsect, stack);
    }

    protected void rotateAnimal(T entityinsect) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(T entityinsect, MatrixStack stack) {
        float sizeFactor = entityinsect.getSizeFactor();
        stack.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
