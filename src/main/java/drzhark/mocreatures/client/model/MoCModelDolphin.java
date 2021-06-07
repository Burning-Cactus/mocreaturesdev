package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelDolphin<T extends MoCEntityDolphin> extends SegmentedModel<T> {

    public ModelRenderer UHead;
    public ModelRenderer DHead;
    public ModelRenderer RTail;
    public ModelRenderer LTail;
    public ModelRenderer PTail;
    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer RTailFin;
    public ModelRenderer LTailFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;

    public MoCModelDolphin() {
        this.Body = new ModelRenderer(this, 4, 6);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        this.Body.setPos(-4F, 17F, -10F);
        this.UHead = new ModelRenderer(this, 0, 0);
        this.UHead.addBox(0.0F, 0.0F, 0.0F, 5, 7, 8, 0.0F);
        this.UHead.setPos(-3.5F, 18F, -16.5F);
        this.DHead = new ModelRenderer(this, 50, 0);
        this.DHead.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, 0.0F);
        this.DHead.setPos(-2.5F, 21.5F, -20.5F);
        this.PTail = new ModelRenderer(this, 34, 9);
        this.PTail.addBox(0.0F, 0.0F, 0.0F, 5, 5, 10, 0.0F);
        this.PTail.setPos(-3.5F, 19F, 8F);
        this.UpperFin = new ModelRenderer(this, 4, 12);
        this.UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        this.UpperFin.setPos(-1.5F, 18F, -4F);
        this.UpperFin.xRot = 0.7853981F;
        this.LTailFin = new ModelRenderer(this, 34, 0);
        this.LTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        this.LTailFin.setPos(-2F, 21.5F, 18F);
        this.LTailFin.yRot = 0.7853981F;
        this.RTailFin = new ModelRenderer(this, 34, 0);
        this.RTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        this.RTailFin.setPos(-3F, 21.5F, 15F);
        this.RTailFin.yRot = -0.7853981F;
        this.LeftFin = new ModelRenderer(this, 14, 0);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.LeftFin.setPos(2.0F, 24F, -7F);
        this.LeftFin.yRot = -0.5235988F;
        this.LeftFin.zRot = 0.5235988F;
        this.RightFin = new ModelRenderer(this, 14, 0);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.RightFin.setPos(-10F, 27.5F, -3F);
        this.RightFin.yRot = 0.5235988F;
        this.RightFin.zRot = -0.5235988F;
    }

    @Override
    public void setupAnim(T t, float limbSwing, float limbSwingAmount, float time, float headPitch, float headYaw) {
        setRotationAngles(limbSwing, limbSwingAmount);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.Body, this.PTail, this.UHead, this. DHead, this.UpperFin,
                this.LTailFin, this.RTailFin, this.LeftFin, this.RightFin);
    }

    public void setRotationAngles(float f, float f1) {
        this.RTailFin.xRot = MathHelper.cos(f * 0.4F) * f1;
        this.LTailFin.xRot = MathHelper.cos(f * 0.4F) * f1;
    }
}
