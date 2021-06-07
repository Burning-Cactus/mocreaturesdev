package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityCricket extends MoCEntityInsect

{

    private int jumpCounter;
    private int soundCounter;

    public MoCEntityCricket(EntityType<? extends MoCEntityCricket> type, World world) {
        super(type, world);
        this.texture = "cricketa.png";
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            int i = this.random.nextInt(100);
            if (i <= 50) {
                setType(1);
            } else {
                setType(2);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getSubType() == 1) {
            return MoCreatures.getTexture("cricketa.png");
        } else {
            return MoCreatures.getTexture("cricketb.png");
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (getIsFlying() && this.random.nextInt(50) == 0) {
                setIsFlying(false);
            }

            if (getIsFlying() || !this.onGround) {
                PlayerEntity ep = this.level.getNearestPlayer(this, 5D);
                if (ep != null && --this.soundCounter == -1) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CRICKET_FLY);
                    this.soundCounter = 10;
                }
            } else if (!level.isDay()) { //TODO: Formerly was Dimensionmanager.getWorld(), check if this still works!
                PlayerEntity ep = this.level.getNearestPlayer(this, 12D);
                if (ep != null && --this.soundCounter == -1) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CRICKET_AMBIENT);
                    this.soundCounter = 20;
                }
            }

            if (this.jumpCounter > 0 && ++this.jumpCounter > 30) {
                this.jumpCounter = 0;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide) {
            if (onGround && ((getDeltaMovement().x > 0.05D) || (getDeltaMovement().z > 0.05D) || (getDeltaMovement().x < -0.05D) || (getDeltaMovement().z < -0.05D)))
                if (this.jumpCounter == 0 && this.onGround
                        && ((this.getDeltaMovement().x > 0.05D) || (this.getDeltaMovement().z > 0.05D) || (this.getDeltaMovement().x < -0.05D) || (this.getDeltaMovement().z < -0.05D))) {
                    this.setDeltaMovement(this.getDeltaMovement().x*5D, 0.45D, this.getDeltaMovement().z*5D);
                    this.jumpCounter = 1;
                }
        }
    }

    @Override
    public float getSpeed() {
        if (getIsFlying()) {
            return 0.12F;
        }
        return 0.15F;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }
}
