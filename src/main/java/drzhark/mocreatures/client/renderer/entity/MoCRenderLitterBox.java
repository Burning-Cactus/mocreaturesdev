package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderLitterBox extends LivingRenderer<MoCEntityLitterBox, MoCModelLitterBox> {

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(EntityRendererManager manager, MoCModelLitterBox modellitterbox, float f) {
        super(manager, modellitterbox, f);
        this.litterbox = modellitterbox;
    }

    @Override
    protected void preRenderCallback(MoCEntityLitterBox entitylitterbox, MatrixStack stack, float f) {
        this.litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityLitterBox entitylitterbox) {
        return entitylitterbox.getTexture();
    }
}
