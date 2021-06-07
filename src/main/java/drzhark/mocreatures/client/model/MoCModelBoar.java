package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelBoar<T extends MoCEntityBoar> extends AgeableModel<T> {

    ModelRenderer Head;
    ModelRenderer Trout;
    ModelRenderer Tusks;
    ModelRenderer Jaw;
    ModelRenderer LeftEar;
    ModelRenderer RightEar;
    ModelRenderer HeadMane;
    ModelRenderer Body;
    ModelRenderer BodyMane;
    ModelRenderer Tail;
    ModelRenderer UpperLegRight;
    ModelRenderer LowerLegRight;
    ModelRenderer UpperLegLeft;
    ModelRenderer LowerLegLeft;
    ModelRenderer UpperHindLegRight;
    ModelRenderer LowerHindLegRight;
    ModelRenderer UpperHindLegLeft;
    ModelRenderer LowerHindLegLeft;

    public MoCModelBoar() {
        this.texWidth = 64;
        this.texHeight = 64;

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-3F, 0F, -5F, 6, 6, 5);
        this.Head.setPos(0F, 11F, -5F);
        setRotation(this.Head, 0.2617994F, 0F, 0F);

        this.Trout = new ModelRenderer(this, 0, 11);
        this.Trout.addBox(-1.5F, 1.5F, -9.5F, 3, 3, 5);
        this.Trout.setPos(0F, 11F, -5F);
        setRotation(this.Trout, 0.3490659F, 0F, 0F);

        this.Tusks = new ModelRenderer(this, 0, 24);
        this.Tusks.addBox(-2F, 3F, -8F, 4, 2, 1);
        this.Tusks.setPos(0F, 11F, -5F);
        setRotation(this.Tusks, 0.3490659F, 0F, 0F);

        this.Jaw = new ModelRenderer(this, 0, 19);
        this.Jaw.addBox(-1F, 4.9F, -8.5F, 2, 1, 4);
        this.Jaw.setPos(0F, 11F, -5F);
        setRotation(this.Jaw, 0.2617994F, 0F, 0F);

        this.LeftEar = new ModelRenderer(this, 16, 11);
        this.LeftEar.addBox(1F, -4F, -2F, 2, 4, 2);
        this.LeftEar.setPos(0F, 11F, -5F);
        setRotation(this.LeftEar, 0.6981317F, 0F, 0.3490659F);

        this.RightEar = new ModelRenderer(this, 16, 17);
        this.RightEar.addBox(-3F, -4F, -2F, 2, 4, 2);
        this.RightEar.setPos(0F, 11F, -5F);
        setRotation(this.RightEar, 0.6981317F, 0F, -0.3490659F);

        this.HeadMane = new ModelRenderer(this, 23, 0);
        this.HeadMane.addBox(-1F, -2F, -5F, 2, 2, 5);
        this.HeadMane.setPos(0F, 11F, -5F);
        setRotation(this.HeadMane, 0.4363323F, 0F, 0F);

        this.Body = new ModelRenderer(this, 24, 0);
        this.Body.addBox(-3.5F, 0F, 0F, 7, 8, 13);
        this.Body.setPos(0F, 11F, -5F);
        setRotation(this.Body, -0.0872665F, 0F, 0F);

        this.BodyMane = new ModelRenderer(this, 0, 27);
        this.BodyMane.addBox(-1F, -2F, -1F, 2, 2, 9);
        this.BodyMane.setPos(0F, 11.3F, -4F);
        setRotation(this.BodyMane, -0.2617994F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 60, 38);
        this.Tail.addBox(-0.5F, 0F, 0F, 1, 5, 1);
        this.Tail.setPos(0F, 13F, 7.5F);
        setRotation(this.Tail, 0.0872665F, 0F, 0F);

        this.UpperLegRight = new ModelRenderer(this, 32, 21);
        this.UpperLegRight.addBox(-1F, -2F, -2F, 1, 5, 3);
        this.UpperLegRight.setPos(-3.5F, 16F, -2.5F);
        setRotation(this.UpperLegRight, 0.1745329F, 0F, 0F);

        this.LowerLegRight = new ModelRenderer(this, 32, 29);
        this.LowerLegRight.addBox(-0.5F, 2F, -1F, 2, 6, 2);
        this.LowerLegRight.setPos(-3.5F, 16F, -2.5F);

        this.UpperLegLeft = new ModelRenderer(this, 24, 21);
        this.UpperLegLeft.addBox(0F, -2F, -2F, 1, 5, 3);
        this.UpperLegLeft.setPos(3.5F, 16F, -2.5F);
        setRotation(this.UpperLegLeft, 0.1745329F, 0F, 0F);

        this.LowerLegLeft = new ModelRenderer(this, 24, 29);
        this.LowerLegLeft.addBox(-1.5F, 2F, -1F, 2, 6, 2);
        this.LowerLegLeft.setPos(3.5F, 16F, -2.5F);

        this.UpperHindLegRight = new ModelRenderer(this, 44, 21);
        this.UpperHindLegRight.addBox(-1.5F, -2F, -2F, 1, 5, 4);
        this.UpperHindLegRight.setPos(-3F, 16F, 5.5F);
        setRotation(this.UpperHindLegRight, -0.2617994F, 0F, 0F);

        this.LowerHindLegRight = new ModelRenderer(this, 46, 30);
        this.LowerHindLegRight.addBox(-1F, 2F, 0F, 2, 6, 2);
        this.LowerHindLegRight.setPos(-3F, 16F, 5.5F);

        this.UpperHindLegLeft = new ModelRenderer(this, 54, 21);
        this.UpperHindLegLeft.addBox(0.5F, -2F, -2F, 1, 5, 4);
        this.UpperHindLegLeft.setPos(3F, 16F, 5.5F);
        setRotation(this.UpperHindLegLeft, -0.2617994F, 0F, 0F);

        this.LowerHindLegLeft = new ModelRenderer(this, 56, 30);
        this.LowerHindLegLeft.addBox(-1F, 2F, 0F, 2, 6, 2);
        this.LowerHindLegLeft.setPos(3F, 16F, 5.5F);

    }

    @Override
    public void setupAnim(T t, float legMove1, float legMove2, float v2, float pitch, float yaw) {
        float XAngle = (yaw / 57.29578F);
        float YAngle = pitch / 57.29578F;
        this.Head.xRot = 0.2617994F + XAngle;
        this.Head.yRot = YAngle;
        this.HeadMane.xRot = 0.4363323F + XAngle;
        this.HeadMane.yRot = YAngle;
        this.Trout.xRot = 0.3490659F + XAngle;
        this.Trout.yRot = YAngle;
        this.Jaw.xRot = 0.2617994F + XAngle;
        this.Jaw.yRot = YAngle;
        this.Tusks.xRot = 0.3490659F + XAngle;
        this.Tusks.yRot = YAngle;
        this.LeftEar.xRot = 0.6981317F + XAngle;
        this.LeftEar.yRot = YAngle;
        this.RightEar.xRot = 0.6981317F + XAngle;
        this.RightEar.yRot = YAngle;

        float LLegRotX = MathHelper.cos(legMove1 * 0.6662F) * 1.4F * legMove2;
        float RLegRotX = MathHelper.cos((legMove1 * 0.6662F) + 3.141593F) * 1.4F * legMove2;

        this.UpperLegLeft.xRot = LLegRotX;
        this.LowerLegLeft.xRot = LLegRotX;
        this.UpperHindLegRight.xRot = LLegRotX;
        this.LowerHindLegRight.xRot = LLegRotX;

        this.UpperLegRight.xRot = RLegRotX;
        this.LowerLegRight.xRot = RLegRotX;
        this.UpperHindLegLeft.xRot = RLegRotX;
        this.LowerHindLegLeft.xRot = RLegRotX;

        this.Tail.zRot = LLegRotX * 0.2F;

    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return ImmutableList.of(
                Head,
                Trout,
                Tusks,
                Jaw,
                LeftEar,
                RightEar,
                HeadMane
        );
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(
                Body,
                BodyMane,
                Tail,
                UpperLegRight,
                LowerLegRight,
                UpperLegLeft,
                LowerLegLeft,
                UpperHindLegRight,
                LowerHindLegRight,
                UpperHindLegLeft,
                LowerHindLegLeft
        );
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }

}
