package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityPandaBear extends MoCEntityBear{

    public MoCEntityPandaBear(EntityType<? extends MoCEntityPandaBear> type, World world) {
        super(type, world);
    }
    
    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(1);
        }
        super.selectType();
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture("bearpanda.png");
    }
    
    @Override
    public float getBearSize() {
        return 0.8F;
    }
    
    @Override
    public int getMaxEdad() {
        return 80;
    }
        
    @Override
    public float calculateMaxHealth() {
        return 20;
    }
    
    @Override
    public boolean isReadyToHunt() {
        return false;
    }

    @Override
    public int getAttackStrength() {
        return 1;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public boolean shouldAttackPlayers() {
        return false;
    }
    
    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return this.getSubType() == 3 && !stack.isEmpty() && stack.getItem() == Items.REEDS;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return this.getSubType() == 3 && !stack.isEmpty() && stack.getItem() == Items.REEDS;
    }
    
    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (stack.getItem() == MoCItems.SUGARLUMP || stack.getItem() == Items.SUGAR_CANE)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (!this.world.isRemote && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }

            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && stack.getItem() == MoCItems.WHIP) {
            if (getBearState() == 0) {
                setBearState(2);
            }else {
                setBearState(0);
            }
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isCrouching()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setBearState(0);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }
    
    @Override
    public void livingTick() {
        super.livingTick();
        /**
         * panda bears and cubs will sit down every now and then
         */
        if (!this.world.isRemote && !getIsTamed() && this.rand.nextInt(300) == 0) {
            setBearState(2);
        }
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "PandaBear";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof MoCEntityPandaBear;
    }
}
