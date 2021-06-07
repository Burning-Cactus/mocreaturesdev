package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class FireflyEntity extends MoCEntityInsect {

    private int soundCount;

    public FireflyEntity(EntityType<? extends FireflyEntity> type, World world) {
        super(type, world);
        this.texture = "firefly.png";
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            PlayerEntity ep = this.level.getNearestPlayer(this, 5D);
            if (ep != null && getIsFlying() && --this.soundCount == -1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CRICKET_FLY);
                this.soundCount = 20;
            }

            if (getIsFlying() && this.random.nextInt(500) == 0) {
                setIsFlying(false);
            }
        }
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getSpeed() {
        if (getIsFlying()) {
            return 0.12F;
        }
        return 0.10F;
    }

    /* @Override
     protected float getFlyingSpeed() {
         return 0.3F;
     }

     @Override
     protected float getWalkingSpeed() {
         return 0.2F;
     }*/
}
