package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityAnt extends MoCEntityInsect {

    private static final DataParameter<Boolean> FOUND_FOOD = EntityDataManager.defineId(MoCEntityAnt.class, DataSerializers.BOOLEAN);
    
    public MoCEntityAnt(EntityType<? extends MoCEntityAnt> type, World world) {
        super(type, world);
        this.texture = "ant.png";
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EntityAIWanderMoC2(this, 1.2D));
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FOUND_FOOD, Boolean.FALSE);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityInsect.registerAttributes().add(Attributes.MOVEMENT_SPEED, 0.28D);
    }

    public boolean getHasFood() {
        return this.entityData.get(FOUND_FOOD);
    }

    public void setHasFood(boolean flag) {
        this.entityData.set(FOUND_FOOD, flag);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            if (!getHasFood()) {
                ItemEntity entityitem = MoCTools.getClosestFood(this, 8D);
                if (entityitem == null || !entityitem.isAlive()) {
                    return;
                }
                if (entityitem.getVehicle() == null) {
                    float f = entityitem.distanceTo(this);
                    if (f > 1.0F) {
                        int i = MathHelper.floor(entityitem.getX());
                        int j = MathHelper.floor(entityitem.getY());
                        int k = MathHelper.floor(entityitem.getZ());
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if (f < 1.0F) {
                        exchangeItem(entityitem);
                        setHasFood(true);
                        return;
                    }
                }
            }

        }

        if (getHasFood()) {
            if (!this.isVehicle()) {
                ItemEntity entityitem = MoCTools.getClosestFood(this, 2D);
                if (entityitem != null && entityitem.getVehicle() == null) {
                    entityitem.startRiding(this);
                    return;

                }

                if (!this.isVehicle()) {
                    setHasFood(false);
                }
            }
        }
    }

    private void exchangeItem(ItemEntity entityitem) {
        ItemEntity cargo = new ItemEntity(this.level, this.getX(), this.getY() + 0.2D, this.getZ(), entityitem.getItem());
        entityitem.remove();
        if (!this.level.isClientSide) {
            this.level.addFreshEntity(cargo);
        }
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem());
    }

    @Override
    public boolean isFlyer() {
        return false;
    }

    @Override
    public float getSpeed() {
        if (getHasFood()) {
            return 0.05F;
        }
        return 0.15F;
    }
}
