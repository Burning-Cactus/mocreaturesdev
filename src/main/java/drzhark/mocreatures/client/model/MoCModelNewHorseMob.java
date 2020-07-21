package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelNewHorseMob<T extends MoCEntityHorseMob> extends MoCModelNewHorse<T> {
    public MoCModelNewHorseMob() {
        super();
    }

//    @Override
//    public void setRotationAngles(T entity, float v, float v1, float v2, float v3, float v4) {
//        MoCEntityHorseMob entityhorse = entity;
//
//        int type = entityhorse.getSubType();
//        boolean wings = (entityhorse.isFlyer());
//        boolean eating = (entityhorse.eatingCounter != 0);//entityhorse.getEating();
//        boolean standing = (entityhorse.standCounter != 0 && entityhorse.getRidingEntity() == null);
//        boolean openMouth = (entityhorse.mouthCounter != 0);
//        boolean moveTail = (entityhorse.tailCounter != 0);
//        boolean flapwings = (entityhorse.wingFlapCounter != 0);
//        boolean rider = (entityhorse.isBeingRidden());
//        boolean floating = (entityhorse.isFlyer() && entityhorse.isOnAir());
//
//        setRotationAngles(v, v1, v2, v3, v4, eating, rider, floating, standing, false, moveTail, wings, flapwings, false, 0);
//
//    }
//
//    @Override
//    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
//        this.Ear1.render(f5);
//        this.Ear2.render(f5);
//        this.Neck.render(f5);
//        this.Head.render(f5);
//        if (openMouth) {
//            this.UMouth2.render(f5);
//            this.LMouth2.render(f5);
//        } else {
//            this.UMouth.render(f5);
//            this.LMouth.render(f5);
//        }
//        this.Mane.render(f5);
//        this.Body.render(f5);
//        this.TailA.render(f5);
//        this.TailB.render(f5);
//        this.TailC.render(f5);
//
//        this.Leg1A.render(f5);
//        this.Leg1B.render(f5);
//        this.Leg1C.render(f5);
//
//        this.Leg2A.render(f5);
//        this.Leg2B.render(f5);
//        this.Leg2C.render(f5);
//
//        this.Leg3A.render(f5);
//        this.Leg3B.render(f5);
//        this.Leg3C.render(f5);
//
//        this.Leg4A.render(f5);
//        this.Leg4B.render(f5);
//        this.Leg4C.render(f5);
//
//        if (entityhorse.isUnicorned()) {
//            this.Unicorn.render(f5);
//        }
//        if (entityhorse.isFlyer() && type != 34 && type != 36)//pegasus
//        {
//            this.MidWing.render(f5);
//            this.InnerWing.render(f5);
//            this.OuterWing.render(f5);
//            this.InnerWingR.render(f5);
//            this.MidWingR.render(f5);
//            this.OuterWingR.render(f5);
//        }
//    }
}
