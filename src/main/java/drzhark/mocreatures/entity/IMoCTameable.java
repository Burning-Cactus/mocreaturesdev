package drzhark.mocreatures.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

import javax.annotation.Nullable;

public interface IMoCTameable extends IMoCEntity {

    boolean isRiderDisconnecting();

    void setRiderDisconnecting(boolean flag);

    void playTameEffect(boolean par1);

    void setTamed(boolean par1);

    void remove();

    void writeAdditional(CompoundNBT nbttagcompound);

    void readAdditional(CompoundNBT nbttagcompound);

    void setOwnerId(@Nullable UUID uuid);

    float getPetHealth();

    void spawnHeart();

    boolean readytoBreed();
    
    String getOffspringClazz(IMoCTameable mate);

    int getOffspringTypeInt(IMoCTameable mate); 

    boolean compatibleMate(Entity mate);
    
    void setHasEaten(boolean flag);
    
    boolean getHasEaten();
    
    void setGestationTime(int time);
    
    int getGestationTime();
}
