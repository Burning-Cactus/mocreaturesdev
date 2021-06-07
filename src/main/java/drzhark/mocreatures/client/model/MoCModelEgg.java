package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelEgg<T extends MoCEntityEgg> extends EntityModel<T> {

    public MoCModelEgg() {
        //textureWidth = 64;
        //textureHeight = 32;

        this.Egg1 = new ModelRenderer(this, 0, 0);
        this.Egg1.addBox(0F, 0F, 0F, 3, 3, 3);
        this.Egg1.setPos(0F, 20F, 0F);

        this.Egg2 = new ModelRenderer(this, 10, 0);
        this.Egg2.addBox(0F, 0F, 0F, 2, 1, 2);
        this.Egg2.setPos(0.5F, 19.5F, 0.5F);

        this.Egg3 = new ModelRenderer(this, 30, 0);
        this.Egg3.addBox(0F, 0F, 0F, 2, 1, 2);
        this.Egg3.setPos(0.5F, 22.5F, 0.5F);

        this.Egg4 = new ModelRenderer(this, 24, 0);
        this.Egg4.addBox(0F, 0F, 0F, 1, 2, 2);
        this.Egg4.setPos(-0.5F, 20.5F, 0.5F);

        this.Egg5 = new ModelRenderer(this, 18, 0);
        this.Egg5.addBox(0F, 0F, 0F, 1, 2, 2);
        this.Egg5.setPos(2.5F, 20.5F, 0.5F);

    }

    @Override
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {

    }

    public ModelRenderer Egg;
    ModelRenderer Egg1;
    ModelRenderer Egg2;
    ModelRenderer Egg3;
    ModelRenderer Egg4;
    ModelRenderer Egg5;

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int i, int i1, float v, float v1, float v2, float v3) {
        this.Egg1.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.Egg2.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.Egg3.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.Egg4.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
        this.Egg5.render(matrixStack, iVertexBuilder, i, i1, v, v1, v2, v3);
    }
}
