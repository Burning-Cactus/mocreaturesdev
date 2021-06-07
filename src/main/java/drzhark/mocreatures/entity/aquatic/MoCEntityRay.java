package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class MoCEntityRay extends MoCEntityTameableAquatic {

    public MoCEntityRay(EntityType<? extends MoCEntityRay> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MoCAlternateWanderGoal(this, 1.0D, 80));
    }
    
    public boolean isPoisoning() {
        return false;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (!this.isBeingRidden() && getSubType() == 1) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                player.setPosition(player.getPosX(), this.getPosY(), player.getPosZ());
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return 0.09F;
        }
        return 0.15F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.15D * getSizeFactor();
    }

    @Override
    public float getSizeFactor() {
        float f = getEdad() * 0.01F;
        if (f > 1.5F) {
            f = 1.5F;
        }
        return f;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.06F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 3D;
    }

    @Override
    protected double maxDivingDepth() {
        return 6.0D;
    }

    @Override
    public int getMaxEdad() {
        return 90;
    }

    public boolean isMantaRay() {
        return false;
    }
}
