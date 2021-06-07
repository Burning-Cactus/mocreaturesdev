package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityFox;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelFox extends SegmentedModel<MoCEntityFox> {

    public ModelRenderer Body;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer Leg4;
    public ModelRenderer Snout;
    public ModelRenderer Head;
    public ModelRenderer Tail;
    public ModelRenderer Ears;

    public MoCModelFox() {
        byte byte0 = 8;
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 6, 12, 0.0F);
        this.Body.setPos(-4F, 10F, -6F);
        this.Head = new ModelRenderer(this, 0, 20);
        this.Head.addBox(-3F, -3F, -4F, 6, 6, 4, 0.0F);
        this.Head.setPos(-1F, 11F, -6F);
        this.Snout = new ModelRenderer(this, 20, 20);
        this.Snout.addBox(-1F, 1.0F, -7F, 2, 2, 4, 0.0F);
        this.Snout.setPos(-1F, 11F, -6F);
        this.Ears = new ModelRenderer(this, 50, 20);
        this.Ears.addBox(-3F, -6F, -2F, 6, 4, 1, 0.0F);
        this.Ears.setPos(-1F, 11F, -6F);
        this.Tail = new ModelRenderer(this, 32, 20);
        this.Tail.addBox(-5F, -5F, -2F, 3, 3, 8, 0.0F);
        this.Tail.setPos(2.5F, 15F, 5F);
        this.Tail.xRot = -0.5235988F;
        this.Leg1 = new ModelRenderer(this, 0, 0);
        this.Leg1.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg1.setPos(-2F, 24 - byte0, 5F);
        this.Leg2 = new ModelRenderer(this, 0, 0);
        this.Leg2.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg2.setPos(1.0F, 24 - byte0, 5F);
        this.Leg3 = new ModelRenderer(this, 0, 0);
        this.Leg3.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg3.setPos(-2F, 24 - byte0, -4F);
        this.Leg4 = new ModelRenderer(this, 0, 0);
        this.Leg4.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg4.setPos(1.0F, 24 - byte0, -4F);
    }

    @Override
    public void setupAnim(MoCEntityFox entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        this.Head.yRot = f3 / 57.29578F;
        this.Head.xRot = f4 / 57.29578F;
        this.Snout.yRot = this.Head.yRot;//f3 / 57.29578F;
        this.Snout.xRot = this.Head.xRot;//f4 / 57.29578F;
        //Snout.rotationPointX = 0.0F + ((f3 / 57.29578F) * 0.8F);
        this.Ears.yRot = this.Head.yRot;//f3 / 57.29578F;
        this.Ears.xRot = this.Head.xRot;//f4 / 57.29578F;
        //Ears.rotationPointX = 0.0F + ((f3 / 57.29578F) * 2.5F);
        this.Leg1.xRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.Leg2.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.Leg3.xRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.Leg4.xRot = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(Body, Leg1, Leg2, Leg3, Leg4, Head, Snout, Tail, Ears);
    }
}
