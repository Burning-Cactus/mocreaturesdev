package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityTiger extends MoCEntityBigCat {

    public MoCEntityTiger(EntityType<? extends MoCEntityTiger> type, World world) {
        super(type, world);
    }

    @Override
    public void selectType() {

        if (getSubType() == 0) {
            if (this.rand.nextInt(20) == 0) {
                setType(2);
            } else {
                setType(1);
            }
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("bctiger.png");
            case 2:
                return MoCreatures.getTexture("bcwhitetiger.png");
            case 3:
                return MoCreatures.getTexture("bcwhitetiger.png"); //winged tiger
            /*case 4:
                return MoCreatures.proxy.getTexture("bcleoger.png"); // Tiger x Leopard
            */default:
                return MoCreatures.getTexture("bctiger.png");
        }
    }

    @Override
    public boolean isFlyer() {
        return this.getSubType() == 3;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && getSubType() == 2 && (stack.getItem() == MoCItems.ESSENCELIGHT)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(3);
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isCrouching()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2) {
            return "Liger";
        }
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1) {
            return "Panthger";
        }
        if (mate instanceof MoCEntityLeopard && ((MoCEntityPanther) mate).getSubType() == 1) {
            return "Leoger";
        }
        return "Tiger";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2) {
            return 1;//4; //liger
        }
        if (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1) {
            return 1;//4; //leoger
        }
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1) {
            return 1;//4; //panthger
        }
        return this.getSubType();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() < 3)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2)
                || (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1)
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1);
    }

    @Override
    public boolean readytoBreed() {
        return this.getSubType() < 3 && super.readytoBreed();
    }

    @Override
    public float calculateMaxHealth() {
        if (this.getSubType() == 2) {
            return 40F;
        }
        return 35F;
    }

    @Override
    public double calculateAttackDmg() {
        if (this.getSubType() == 2) {
            return 8D;
        }
        return 7D;
    }

    @Override
    public double getAttackRange() {
        return 8D;
    }

    @Override
    public int getMaxEdad() {
        if (getSubType() > 2) {
            return 130;
        }
        return 120;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityTiger) {
            return false;
        }
        return entity.getHeight() < 2F && entity.getWidth() < 2F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.5F;
    }
}
