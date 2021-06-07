package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelKittyBed extends EntityModel<MoCEntityKittyBed> {

    ModelRenderer TableL;

    ModelRenderer TableR;

    ModelRenderer Table_B;

    ModelRenderer FoodT;

    ModelRenderer FoodTraySide;

    ModelRenderer FoodTraySideB;

    ModelRenderer FoodTraySideC;

    ModelRenderer FoodTraySideD;

    ModelRenderer Milk;

    ModelRenderer PetFood;

    ModelRenderer Bottom;

    public boolean hasMilk;

    public boolean hasFood;

    public boolean pickedUp;

    public float milklevel;

    public MoCModelKittyBed() {
        float f = 0.0F;
        this.TableL = new ModelRenderer(this, 30, 8);
        this.TableL.addBox(-8F, 0.0F, 7F, 16, 6, 1, f);
        this.TableL.setPos(0.0F, 18F, 0.0F);
        this.TableR = new ModelRenderer(this, 30, 8);
        this.TableR.addBox(-8F, 18F, -8F, 16, 6, 1, f);
        this.TableR.setPos(0.0F, 0.0F, 0.0F);
        this.Table_B = new ModelRenderer(this, 30, 0);
        this.Table_B.addBox(-8F, -3F, 0.0F, 16, 6, 1, f);
        this.Table_B.setPos(8F, 21F, 0.0F);
        this.Table_B.yRot = 1.5708F;
        this.FoodT = new ModelRenderer(this, 14, 0);
        this.FoodT.addBox(1.0F, 1.0F, 1.0F, 4, 1, 4, f);
        this.FoodT.setPos(-16F, 22F, 0.0F);
        this.FoodTraySide = new ModelRenderer(this, 0, 0);
        this.FoodTraySide.addBox(-16F, 21F, 5F, 5, 3, 1, f);
        this.FoodTraySide.setPos(0.0F, 0.0F, 0.0F);
        this.FoodTraySideB = new ModelRenderer(this, 0, 0);
        this.FoodTraySideB.addBox(-15F, 21F, 0.0F, 5, 3, 1, f);
        this.FoodTraySideB.setPos(0.0F, 0.0F, 0.0F);
        this.FoodTraySideC = new ModelRenderer(this, 0, 0);
        this.FoodTraySideC.addBox(-3F, -1F, 0.0F, 5, 3, 1, f);
        this.FoodTraySideC.setPos(-16F, 22F, 2.0F);
        this.FoodTraySideC.yRot = 1.5708F;
        this.FoodTraySideD = new ModelRenderer(this, 0, 0);
        this.FoodTraySideD.addBox(-3F, -1F, 0.0F, 5, 3, 1, f);
        this.FoodTraySideD.setPos(-11F, 22F, 3F);
        this.FoodTraySideD.yRot = 1.5708F;
        this.Milk = new ModelRenderer(this, 14, 9);
        this.Milk.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, f);
        this.Milk.setPos(-15F, 21F, 1.0F);
        this.PetFood = new ModelRenderer(this, 0, 9);
        this.PetFood.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, f);
        this.PetFood.setPos(-15F, 21F, 1.0F);
        this.Bottom = new ModelRenderer(this, 16, 15);
        this.Bottom.addBox(-10F, 0.0F, -7F, 16, 1, 14, f);
        this.Bottom.setPos(2.0F, 23F, 0.0F);
    }

    @Override
    public void setupAnim(MoCEntityKittyBed entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.TableL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.TableR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Table_B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.Bottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        if (!this.pickedUp) {
            this.FoodT.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.FoodTraySide.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.FoodTraySideB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.FoodTraySideC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.FoodTraySideD.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            if (this.hasMilk) {
                this.Milk.y = 21F + this.milklevel;
                this.Milk.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
            if (this.hasFood) {
                this.PetFood.y = 21F + this.milklevel;
                this.PetFood.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
        }
    }
}
