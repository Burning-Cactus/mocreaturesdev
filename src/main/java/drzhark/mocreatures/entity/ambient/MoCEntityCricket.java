package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

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
            int i = this.rand.nextInt(100);
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
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (getIsFlying() && this.rand.nextInt(50) == 0) {
                setIsFlying(false);
            }

            if (getIsFlying() || !this.onGround) {
                PlayerEntity ep = this.world.getClosestPlayer(this, 5D);
                if (ep != null && --this.soundCounter == -1) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CRICKET_FLY);
                    this.soundCounter = 10;
                }
            } else if (!world.isDaytime()) { //TODO: Formerly was Dimensionmanager.getWorld(), check if this still works!
                PlayerEntity ep = this.world.getClosestPlayer(this, 12D);
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
        if (!this.world.isRemote) {
            if (onGround && ((getMotion().x > 0.05D) || (getMotion().z > 0.05D) || (getMotion().x < -0.05D) || (getMotion().z < -0.05D)))
                if (this.jumpCounter == 0 && this.onGround
                        && ((this.getMotion().x > 0.05D) || (this.getMotion().z > 0.05D) || (this.getMotion().x < -0.05D) || (this.getMotion().z < -0.05D))) {
                    this.setMotion(this.getMotion().x*5D, 0.45D, this.getMotion().z*5D);
                    this.jumpCounter = 1;
                }
        }
    }

    @Override
    public float getAIMoveSpeed() {
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
