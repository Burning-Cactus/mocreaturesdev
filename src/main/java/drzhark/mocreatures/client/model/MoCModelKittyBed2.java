package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelKittyBed2 extends EntityModel<MoCEntityKittyBed> {

    ModelRenderer Sheet;

    public MoCModelKittyBed2() {
        float f = 0.0F;
        this.Sheet = new ModelRenderer(this, 0, 15);
        this.Sheet.addBox(0.0F, 0.0F, 0.0F, 16, 3, 14, f);
        this.Sheet.setRotationPoint(-8F, 21F, -7F);
    }

    @Override
    public void setRotationAngles(MoCEntityKittyBed entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Sheet.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}
