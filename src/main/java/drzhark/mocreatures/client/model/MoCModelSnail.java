package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelSnail<T extends MoCEntitySnail> extends SegmentedModel<T> {

    ModelRenderer Head;
    ModelRenderer Antenna;
    ModelRenderer Body;
    ModelRenderer ShellUp;
    ModelRenderer ShellDown;
    ModelRenderer Tail;

    boolean isHiding = false;
    int type = 0;

    public MoCModelSnail() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 6);
        this.Head.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Head.setRotationPoint(0F, 21.8F, -1F);
        setRotation(this.Head, -0.4537856F, 0F, 0F);

        this.Antenna = new ModelRenderer(this, 8, 0);
        this.Antenna.addBox(-1.5F, 0F, -1F, 3, 2, 0);
        this.Antenna.setRotationPoint(0F, 19.4F, -1F);
        setRotation(this.Antenna, 0.0523599F, 0F, 0F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-1F, 0F, -1F, 2, 2, 4);
        this.Body.setRotationPoint(0F, 22F, 0F);

        this.ShellUp = new ModelRenderer(this, 12, 0);
        this.ShellUp.addBox(-1F, -3F, 0F, 2, 3, 3);
        this.ShellUp.setRotationPoint(0F, 22.3F, -0.2F);
        setRotation(this.ShellUp, 0.2268928F, 0F, 0F);

        this.ShellDown = new ModelRenderer(this, 12, 0);
        this.ShellDown.addBox(-1F, 0F, 0F, 2, 3, 3);
        this.ShellDown.setRotationPoint(0F, 21F, 0F);

        this.Tail = new ModelRenderer(this, 1, 2);
        this.Tail.addBox(-1F, 0F, 0F, 2, 1, 3);
        this.Tail.setRotationPoint(0F, 23F, 3F);

    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        MoCEntitySnail snail = (MoCEntitySnail) entityIn;
        isHiding = snail.getIsHiding();
        type = snail.getSubType();
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(Head, Antenna, Body, ShellUp, ShellDown, Tail);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (isHiding && type < 5) {
            this.ShellDown.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.Head.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Antenna.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Body.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.ShellUp.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Tail.render(matrixStack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }


    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4) {
        float tailMov = MathHelper.cos((f2 * 0.3F)) * 0.8F;
        if (f1 < 0.1F) {
            tailMov = 0F;
        }
        this.Tail.rotationPointZ = 2F + tailMov;
        this.ShellUp.rotateAngleX = 0.2268928F + (tailMov / 10F);
    }

}
