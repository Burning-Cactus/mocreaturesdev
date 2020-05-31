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

public class MoCEntityLion extends MoCEntityBigCat {

    public MoCEntityLion(EntityType<? extends MoCEntityLion> type, World world) {
        super(type, world);
    }

    @Override
    public void selectType() {

        if (getSubType() == 0) {
            if (rand.nextInt(20) == 0)
            {
                setType(rand.nextInt(2)+6);//white lions
            }else
            {
            setType(this.rand.nextInt(2) + 1);
        }
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("bcfemalelion.png");//lioness
            case 2:
                return MoCreatures.getTexture("bcmalelion.png");//lion
            case 3:
                return MoCreatures.getTexture("bcmalelion.png");//winged lion
            /*case 4:
                return MoCreatures.proxy.getTexture("bcliger.png");//liger
            case 5:
                return MoCreatures.proxy.getTexture("bcliger.png");//winged liger
            */case 6:
                return MoCreatures.getTexture("bcwhitelion.png");//female white
            case 7:
                return MoCreatures.getTexture("bcwhitelion.png");//male white
            case 8:
                return MoCreatures.getTexture("bcwhitelion.png");//winged male white
            /*case 9:
                return MoCreatures.proxy.getTexture("bcliard.png");// Male Lion X leopard
            */default:
                return MoCreatures.getTexture("bcfemalelion.png");
        }
    }

    @Override
    public boolean hasMane() {
        return getIsAdult() && ((this.getSubType() >= 2 && getSubType() < 4) || this.getSubType() == 7);
    }

    @Override
    public boolean isFlyer() {
        return this.getSubType() == 3 || this.getSubType() == 5 || this.getSubType() == 8;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && (getSubType() == 2 || getSubType() == 7)
                && (stack.getItem() == MoCItems.ESSENCELIGHT)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(getSubType() + 1);
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
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() < 3) {
            return "Liger";//return 4; //liger"
        }
        if (getSubType() == 2 && mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1) {
            return "Liard";//return 9; //liard
        }
        if (getSubType() == 2 && mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1) {
            return "Lither";//return 5; //lither
        }
        return "Lion";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        int x = 0;
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() < 3) {
            return 1;//4; //liger
        }
        if (getSubType() == 2 && mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1) {
            return 1;//9; //liard
        }
        if (getSubType() == 2 && mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1) {
            return 1;//5; //lither
        }
        if (mate instanceof MoCEntityLion) {
            int lionMateType = ((MoCEntityLion) mate).getSubType();
            if (this.getSubType() == 1 && lionMateType == 2) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getSubType() == 2 && lionMateType == 1) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getSubType() == 6 && lionMateType == 7) {
                x = this.rand.nextInt(2) + 6;
            }
            if (this.getSubType() == 7 && lionMateType == 6) {
                x = this.rand.nextInt(2) + 6;
            }
            if (this.getSubType() == 7 && lionMateType == 1) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getSubType() == 6 && lionMateType == 2) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getSubType() == 1 && lionMateType == 7) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getSubType() == 2 && lionMateType == 6) {
                x = this.rand.nextInt(2) + 1;
            }
        }
        return x;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        if (this.getSubType() == 2 && mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getSubType() < 3) {
            return true;
        }
        if (this.getSubType() == 2 && mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getSubType() == 1) {
            return true;
        }
        if (this.getSubType() == 2 && mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getSubType() == 1) {
            return true;
        }
        if (mate instanceof MoCEntityLion) {
            return (getOffspringTypeInt((MoCEntityLion) mate) != 0);
        }
        return false;
    }

    @Override
    public boolean readytoBreed() {
        return (this.getSubType() < 3 || this.getSubType() == 6 || this.getSubType() == 7) && super.readytoBreed();
    }

    @Override
    public float calculateMaxHealth() {
        if (this.getSubType() == 2 || this.getSubType() == 7) {
            return 35F;
        }
        if (this.getSubType() == 4) {
            return 40F;
        }
        return 30F;
    }

    @Override
    public double calculateAttackDmg() {
        return 7D;
    }

    @Override
    public double getAttackRange() {
        if (this.getSubType() == 1 || this.getSubType() == 6) {
            return 12D;
        }
        return 8D;
    }

    @Override
    public int getMaxEdad() {
        if (getSubType() == 1 || getSubType() == 6) {
            return 110;
        }
        if (getSubType() == 9) {
            return 100;
        }
        return 120;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLion) {
            return false;
        }
        return entity.getHeight() < 2F && entity.getWidth() < 2F;
    }
}
