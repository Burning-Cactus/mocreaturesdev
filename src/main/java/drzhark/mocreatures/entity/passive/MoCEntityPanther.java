package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityPanther extends MoCEntityBigCat {

    public MoCEntityPanther(EntityType<? extends MoCEntityPanther> type, World world) {
        super(type, world);
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("bcpuma.png");
            case 2:
                return MoCreatures.getTexture("bcpuma.png"); //winged panther
            /*case 3:
                return MoCreatures.proxy.getTexture("bcpanthard.png"); //panther X leopard
            case 4:
                return MoCreatures.proxy.getTexture("bcpanthger.png"); //panther X tiger
            case 5:
                return MoCreatures.proxy.getTexture("bclither.png"); //panther X lion
            */default:
                return MoCreatures.getTexture("bcpuma.png");
        }
    }

    @Override
    public void selectType() {

        if (getSubType() == 0) {
            setType(1);
        }
        super.selectType();
    }

    @Override
    public boolean isFlyer() {
        return this.getSubType() == 2;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && getSubType() == 1 && (stack.getItem() == MoCItems.ESSENCEDARKNESS)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(2);
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
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1) {
            return "Panthard";//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() == 1) {
            return "Panthger";//4; //panthger
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2) {
            return "Lither";//5; //lither
        }
        
        return "Panther";
        
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1) {
            return 1;//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() == 1) {
            return 1;//4; //panthger
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2) {
            return 1;//5; //lither
        }
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1)
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1)
                || (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() == 1)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getSubType() == 2);
    }

    @Override
    public float calculateMaxHealth() {
        return 25F;
    }

    @Override
    public double calculateAttackDmg() {
        return 6D;
    }

    @Override
    public double getAttackRange() {
        return 8D;
    }

    @Override
    public int getMaxEdad() {
        if (getSubType() >= 4) return 110;
        return 100;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityPanther) {
            return false;
        }
        return entity.getHeight() < 1.5F && entity.getWidth() < 1.5F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.6F;
    }
}
