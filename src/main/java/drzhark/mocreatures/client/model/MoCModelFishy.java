package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MoCModelFishy<T extends MoCEntityFishy> extends EntityModel<T> {

    public ModelRenderer Body;
    public ModelRenderer Tail;

    public MoCModelFishy() {
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0.0F, 0.0F, -3.5F, 1, 5, 5, 0.0F);
        this.Body.setRotationPoint(0.0F, 18F, -1F);
        this.Body.rotateAngleX = 0.7853981F;
        this.Tail = new ModelRenderer(this, 12, 0);
        this.Tail.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.Tail.setRotationPoint(0.0F, 20.5F, 3F);
        this.Tail.rotateAngleX = 0.7853981F;
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        MoCEntityFishy smallFish = (MoCEntityFishy)entityIn;

        setRotationAngles(limbSwing, limbSwingAmount);
    }


    public void setRotationAngles(float f, float f1) {
        this.Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        float yOffset = smallFish.getAdjustedYOffset();
        float xOffset = smallFish.getAdjustedXOffset();
        float zOffset = smallFish.getAdjustedZOffset();
        matrixStackIn.push();
        matrixStackIn.translate(xOffset, yOffset, zOffset);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }
}
