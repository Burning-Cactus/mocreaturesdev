package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityStingRay extends MoCEntityRay {

    private int poisoncounter;
    private int tailCounter;

    public MoCEntityStingRay(EntityType<? extends MoCEntityStingRay> type, World world) {
        super(type, world);
        setEdad(50 + (this.random.nextInt(40)));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityRay.registerAttributes().add(Attributes.MAX_HEALTH, 10D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("stingray.png");
    }

    @Override
    public boolean isPoisoning() {
        return this.tailCounter != 0;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (!getIsTamed() && ++this.poisoncounter > 250 && (this.level.getDifficulty().getId() > 0) && this.random.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
//                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
//                            new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
                    this.poisoncounter = 0;
                }
            }
        } else //client stuff
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 50) {
                this.tailCounter = 0;
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //attacking with tail
        {
            this.tailCounter = 1;
        }
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            if ((this.level.getDifficulty().getId() == 0)) {
                return true;
            }
            Entity entity = damagesource.getEntity();
            if (entity instanceof LivingEntity) {
                if (entity != this) {
                    setTarget((LivingEntity) entity);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
