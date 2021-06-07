package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelWraith<T extends MoCEntityWraith> extends BipedModel<T> {

    private int attackCounter;

    public MoCModelWraith() {
        //TODO 4.1 FIX
        super(12F, 0.0F, 64, 32);
        this.leftArmPose = BipedModel.ArmPose.EMPTY;
        this.rightArmPose = BipedModel.ArmPose.EMPTY;
        this.crouching = false;
        this.head = new ModelRenderer(this, 0, 40);
        this.head.addBox(-4F, -8F, -4F, 1, 1, 1, 0.0F);
        this.head.setPos(0.0F, 0.0F, 0.0F);
        this.hat = new ModelRenderer(this, 0, 0);
        this.hat.addBox(-5F, -8F, -4F, 8, 8, 8, 0.0F);
        this.hat.setPos(0.0F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 36, 0);
        this.body.addBox(-6F, 0.0F, -2F, 10, 20, 4, 0.0F);
        this.body.setPos(0.0F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 16, 16);
        this.rightArm.addBox(-5F, -2F, -2F, 4, 12, 4, 0.0F);
        this.rightArm.setPos(-5F, 2.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 16, 16);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1F, -2F, -2F, 4, 12, 4, 0.0F);
        this.leftArm.setPos(5F, 2.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 16);
        this.rightLeg.addBox(-2F, 0.0F, -2F, 2, 2, 2, 0.0F);
        this.rightLeg.setPos(-2F, 12F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 16);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-2F, 0.0F, -2F, 2, 2, 2, 0.0F);
        this.leftLeg.setPos(2.0F, 12F, 0.0F);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.attackCounter = ((MoCEntityWraith) entityIn).attackCounter;

        float f6 = MathHelper.sin(this.attackTime * 3.141593F);
        float f7 = MathHelper.sin((1.0F - ((1.0F - this.attackTime) * (1.0F - this.attackTime))) * 3.141593F);
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightArm.yRot = -(0.1F - (f6 * 0.6F));
        this.leftArm.yRot = 0.1F - (f6 * 0.6F);
        if (this.attackCounter != 0) {
            float armMov = (MathHelper.cos((attackCounter) * 0.12F) * 4F);

            this.rightArm.xRot = -armMov;
            this.leftArm.xRot = -armMov;
        } else {
            this.rightArm.xRot = -1.570796F;
            this.leftArm.xRot = -1.570796F;
            this.rightArm.xRot -= (f6 * 1.2F) - (f7 * 0.4F);
            this.leftArm.xRot -= (f6 * 1.2F) - (f7 * 0.4F);
            this.rightArm.xRot += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.leftArm.xRot -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        }

        this.rightArm.zRot += (MathHelper.cos(ageInTicks * 0.09F) * 0.05F) + 0.05F;
        this.leftArm.zRot -= (MathHelper.cos(ageInTicks * 0.09F) * 0.05F) + 0.05F;

    }
}
