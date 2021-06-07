package drzhark.mocreatures.client.model;

import com.google.common.collect.ImmutableList;
import drzhark.mocreatures.entity.ambient.AntEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MoCModelAnt<T extends AntEntity> extends SegmentedModel<T> {

    ModelRenderer Head;
    ModelRenderer Mouth;
    ModelRenderer RightAntenna;
    ModelRenderer LeftAntenna;
    ModelRenderer Thorax;
    ModelRenderer Abdomen;
    ModelRenderer MidLegs;
    ModelRenderer FrontLegs;
    ModelRenderer RearLegs;

    public MoCModelAnt() {
        this.texWidth = 32;
        this.texHeight = 32;

        this.Head = new ModelRenderer(this, 0, 11);
        this.Head.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Head.setPos(0F, 21.9F, -1.3F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 8, 10);
        this.Mouth.addBox(0F, 0F, 0F, 2, 1, 0);
        this.Mouth.setPos(-1F, 22.3F, -1.9F);
        setRotation(this.Mouth, -0.8286699F, 0F, 0F);

        this.RightAntenna = new ModelRenderer(this, 0, 6);
        this.RightAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.RightAntenna.setPos(-0.5F, 21.7F, -2.3F);
        setRotation(this.RightAntenna, -1.041001F, 0.7853982F, 0F);

        this.LeftAntenna = new ModelRenderer(this, 4, 6);
        this.LeftAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.LeftAntenna.setPos(0.5F, 21.7F, -2.3F);
        setRotation(this.LeftAntenna, -1.041001F, -0.7853982F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-0.5F, 1.5F, -1F, 1, 1, 2);
        this.Thorax.setPos(0F, 20F, -0.5F);

        this.Abdomen = new ModelRenderer(this, 8, 1);
        this.Abdomen.addBox(-0.5F, -0.2F, -1F, 1, 2, 1);
        this.Abdomen.setPos(0F, 21.5F, 0.3F);
        setRotation(this.Abdomen, 1.706911F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 4, 8);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.MidLegs.setPos(0F, 22F, -0.7F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 8);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.FrontLegs.setPos(0F, 22F, -0.8F);
        setRotation(this.FrontLegs, -0.6192304F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 8);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setPos(0F, 22F, 0F);
        setRotation(this.RearLegs, 0.9136644F, 0F, 0F);
    }

    @Override
    public void setupAnim(T entity, float legMove1, float legMove2, float v2, float pitch, float yaw) {
        float legMov = MathHelper.cos((legMove1) + 3.141593F) * legMove2;
        float legMovB = MathHelper.cos(legMove1) * legMove2;
        this.FrontLegs.xRot = -0.6192304F + legMov;
        this.MidLegs.xRot = 0.5948578F + legMovB;
        this.RearLegs.xRot = 0.9136644F + legMov;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }


    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(
                Head,
                Mouth,
                RightAntenna,
                LeftAntenna,
                Thorax,
                Abdomen,
                MidLegs,
                FrontLegs,
                RearLegs
        );
    }
}
