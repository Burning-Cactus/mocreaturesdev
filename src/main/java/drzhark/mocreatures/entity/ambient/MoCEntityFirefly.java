package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MoCEntityFirefly extends MoCEntityInsect {

    private int soundCount;

    public MoCEntityFirefly(EntityType<? extends MoCEntityFirefly> type, World world) {
        super(type, world);
        this.texture = "firefly.png";
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            PlayerEntity ep = this.world.getClosestPlayer(this, 5D);
            if (ep != null && getIsFlying() && --this.soundCount == -1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CRICKET_FLY);
                this.soundCount = 20;
            }

            if (getIsFlying() && this.rand.nextInt(500) == 0) {
                setIsFlying(false);
            }
        }
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
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
