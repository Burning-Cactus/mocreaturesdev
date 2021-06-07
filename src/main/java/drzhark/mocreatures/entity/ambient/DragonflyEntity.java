package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DragonflyEntity extends MoCEntityInsect {

    private int soundCount;

    public DragonflyEntity(EntityType<? extends DragonflyEntity> type, World world) {
        super(type, world);
        this.texture = "dragonflya.png";
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.random.nextInt(4) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("dragonflya.png");
            case 2:
                return MoCreatures.getTexture("dragonflyb.png");
            case 3:
                return MoCreatures.getTexture("dragonflyc.png");
            case 4:
                return MoCreatures.getTexture("dragonflyd.png");

            default:
                return MoCreatures.getTexture("dragonflyd.png");
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            PlayerEntity ep = this.level.getNearestPlayer(this, 5D);
            if (ep != null && getIsFlying() && --this.soundCount == -1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_DRAGONFLY_AMBIENT);
                this.soundCount = 20;
            }

            if (getIsFlying() && this.random.nextInt(200) == 0) {
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
            return 0.25F;
        }
        return 0.12F;
    }

    public int maxFlyingHeight() {
        return 4;
    }
}
