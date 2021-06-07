package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.IPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MoCEntityGrizzlyBear extends MoCEntityBear{

    public MoCEntityGrizzlyBear(EntityType<? extends MoCEntityGrizzlyBear> type, World world) {
        super(type, world);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
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
        return MoCreatures.getTexture("bearbrowm.png");
    }

    @Override
    public float getBearSize() {
        return 1.2F;
    }
    
    @Override
    public int getMaxEdad() {
        return 120;
    }
    
    @Override
    public float calculateMaxHealth() {
        return 40;
    }
    
    public double getAttackRange() {
        int factor = 1;
        if (this.level.getDifficulty().getId() > 1) {
            factor = 2;
        }
        return 6D * factor;
    }
    
    @Override
    public int getAttackStrength() {
        int factor = (this.level.getDifficulty().getId());
        return 3 * factor;
    }
    
    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness() < 0.4F) && super.shouldAttackPlayers();
    }
    
    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && this.getEdad() < 80 && MoCTools.isItemEdibleforCarnivores(stack.getItem())) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            if (!getIsTamed() && !this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (!this.world.isRemote && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.WHIP)) {
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
    }*/
    
    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "GrizzlyBear";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof MoCEntityGrizzlyBear;
    }
}
