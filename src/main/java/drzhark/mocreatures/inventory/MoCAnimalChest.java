package drzhark.mocreatures.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class MoCAnimalChest extends InventoryBasic implements ILockableContainer {

    private LockCode lockCode = LockCode.EMPTY_CODE;

    public MoCAnimalChest(String name, int size) {
        super(name, true, size);
    }

    public void loadInventoryFromNBT(ListNBT par1NBTTagList) {
        int var2;

        for (var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            this.setInventorySlotContents(var2, ItemStack.EMPTY);
        }

        for (var2 = 0; var2 < par1NBTTagList.size(); ++var2) {
            CompoundNBT var3 = par1NBTTagList.getCompound(var2);
            int var4 = var3.getByte("Slot") & 255;

            if (var4 >= 0 && var4 < this.getSizeInventory()) {
                this.setInventorySlotContents(var4, new ItemStack(var3));
            }
        }
    }

    public ListNBT saveInventoryToNBT() {
        ListNBT var1 = new ListNBT();

        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            ItemStack var3 = this.getStackInSlot(var2);

            if (var3 != null) {
                CompoundNBT var4 = new CompoundNBT();
                var4.putByte("Slot", (byte) var2);
                var3.writeToNBT(var4);
                var1.add(var4);
            }
        }

        return var1;
    }

    @Override
    public Container createContainer(PlayerInventory playerInventory, PlayerEntity playerIn) {
        return new ChestContainer(playerInventory, this, playerIn);
    }

    @Override
    public String getGuiID() {
        return "";
    }

    @Override
    public boolean isLocked() {
        return this.lockCode != null && !this.lockCode.isEmpty();
    }

    @Override
    public void setLockCode(LockCode code) {
        this.lockCode = code;
    }

    @Override
    public LockCode getLockCode() {
        return this.lockCode;
    }
}
