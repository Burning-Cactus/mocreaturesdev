package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MoCModelMaggot extends EntityModel<MoCEntityMaggot> {

    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer Tailtip;

    public MoCModelMaggot() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.Head = new ModelRenderer(this, 0, 11);
        this.Head.addBox(-1F, -1F, -2F, 2, 2, 2);
        this.Head.setPos(0F, 23F, -2F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-1.5F, -2F, 0F, 3, 3, 4);
        this.Body.setPos(0F, 23F, -2F);

        this.Tail = new ModelRenderer(this, 0, 7);
        this.Tail.addBox(-1F, -1F, 0F, 2, 2, 2);
        this.Tail.setPos(0F, 23F, 2F);

        this.Tailtip = new ModelRenderer(this, 8, 7);
        this.Tailtip.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Tailtip.setPos(0F, 23F, 4F);
    }

    @Override
    public void setupAnim(MoCEntityMaggot entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @SuppressWarnings("unused")
    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        //super.render(entity, f, f1, f2, f3, f4, f5);

        //f1 = movement speed!
        //f2 = timer!
        //System.out.println("f2 = " + f2);

//        GL11.glPushMatrix();
//        GL11.glEnable(3042 /* GL_BLEND */);
//        //float transparency = 0.9F;
//        GL11.glBlendFunc(770, 771);
//        //GL11.glColor4f(1.2F, 1.2F, 1.2F, transparency);
//        float f9 = -(MathHelper.cos(f * 3F)) * f1 * 2F;
//        //GL11.glScalef(1.0F, 1.0F, 1.0F + (f1 * 3F));
//        GL11.glScalef(1.0F, 1.0F, 1.0F + (f9));
//
//        GL11.glDisable(3042/* GL_BLEND */);
//        GL11.glPopMatrix();

        matrixStackIn.pushPose();
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Tailtip.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        matrixStackIn.popPose();
    }
}
