package drzhark.mocreatures.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class MoCAnimalChestBig /*extends InventoryLargeChest*/ {

   /* private final int mySize; //either 27 or 56

    public MoCAnimalChestBig(String name, ILockableContainer p_i45905_2_, ILockableContainer p_i45905_3_, int size) {
        super(name, p_i45905_2_, p_i45905_3_);
        this.mySize = size;
    }

    @Override
    public int getSizeInventory() {
        return this.mySize;
    }

    public void loadInventoryFromNBT(ListNBT par1NBTTagList) {
        int var2;

        for (var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            this.setInventorySlotContents(var2, (ItemStack) null);
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
    }*/
}
