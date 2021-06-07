package drzhark.mocreatures.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityFlameWraith extends MoCEntityWraith implements IMob {

    protected int burningTime;

    public MoCEntityFlameWraith(EntityType<? extends MoCEntityFlameWraith> type, World world) {
        super(type, world);
        this.texture = "flamewraith.png";
        //this.isImmuneToFire = true;
        this.burningTime = 30;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityWraith.registerAttributes().add(Attributes.MAX_HEALTH, 15.0D);
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (this.level.isDay()) {
                float f = getBrightness();
                if ((f > 0.5F)
                        && this.level.canSeeSkyFromBelowWater(new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getY()),
                                MathHelper.floor(this.getZ()))) && ((this.random.nextFloat() * 30F) < ((f - 0.4F) * 2.0F))) {
                    this.setHealth(getHealth() - 2);
                }
            }
        }
        super.aiStep();
    }

    //TODO TEST
    /*@Override
    public float getMoveSpeed() {
        return 1.1F;
    }*/

    @Override
    public void doEnchantDamageEffects(LivingEntity entityLivingBaseIn, Entity entityIn) {
        /*if (!this.world.isRemote && !this.world.getDimension().doesWaterVaporize()) {
            entityLivingBaseIn.setFire(this.burningTime);
        }*/
        super.doEnchantDamageEffects(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isOnFire() {
        return this.random.nextInt(100) == 0;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return false;
    }
}
