package drzhark.mocreatures.client.gui.helpers;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCGUIEntityNamer extends Screen {
    protected MoCGUIEntityNamer(ITextComponent titleIn) {
        super(titleIn);
    } //TODO: Rewrite the naming screen.

//    protected String screenTitle;
//    private final IMoCEntity NamedEntity;
//    @SuppressWarnings("unused")
//    private int updateCounter;
//    private String NameToSet;
//    protected int xSize;
//    protected int ySize;
//    private static TextureManager textureManager = Minecraft.getInstance().getTextureManager();
//    private static final ResourceLocation TEXTURE_MOCNAME = new ResourceLocation("mocreatures", MoCProxy.GUI_TEXTURE + "mocname.png");
//
//    public MoCGUIEntityNamer(IMoCEntity mocanimal, String s) {
//        this.xSize = 256;
//        this.ySize = 181;
//        this.screenTitle = "Choose your Pet's name:";
//        this.NamedEntity = mocanimal;
//        this.NameToSet = s;
//    }
//
//    @Override
//    public void initGui() {
//        this.buttonList.clear();
//        Keyboard.enableRepeatEvents(true);
//        this.buttonList.add(new GuiButton(0, (this.width / 2) - 100, (this.height / 4) + 120, "Done")); //1.5
//    }
//
//    public void updateName() {
//        this.NamedEntity.setPetName(this.NameToSet);
//        MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageUpdatePetName(((LivingEntity) this.NamedEntity).getEntityId(), this.NameToSet));
//        Minecraft.getInstance().displayGuiScreen(null);
//    }
//
//    @Override
//    protected void actionPerformed(GuiButton guibutton) {
//        if (!guibutton.enabled) {
//            return;
//        }
//        if ((guibutton.id == 0) && (this.NameToSet != null) && (!this.NameToSet.equals(""))) {
//            updateName();
//        }
//    }
//
//    @Override
//    public void drawScreen(int i, int j, float f) {
//        drawDefaultBackground();
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        textureManager.bindTexture(TEXTURE_MOCNAME);
//        int l = (this.width - this.xSize) / 2;
//        int i1 = (this.height - (this.ySize + 16)) / 2;
//        drawTexturedModalRect(l, i1, 0, 0, this.xSize, this.ySize);
//        drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 100, 0xffffff);
//        drawCenteredString(this.fontRenderer, this.NameToSet, this.width / 2, 120, 0xffffff);
//        super.drawScreen(i, j, f);
//    }
//
//    @Override
//    public void handleKeyboardInput() throws IOException {
//        if (Keyboard.getEventKeyState()) {
//            if (Keyboard.getEventKey() == 28) // Handle Enter Key
//            {
//                updateName();
//            }
//        }
//        super.handleKeyboardInput();
//    }
//
//    @Override
//    protected void keyTyped(char c, int i) {
//        if ((i == 14) && (this.NameToSet.length() > 0)) {
//            this.NameToSet = this.NameToSet.substring(0, this.NameToSet.length() - 1);
//        }
//        if (!ChatAllowedCharacters.isAllowedCharacter(c) || (this.NameToSet.length() >= 15)) {
//        } else {
//            StringBuilder name = new StringBuilder(this.NameToSet);
//            name.append(c);
//            this.NameToSet = name.toString();
//        }
//    }
//
//    @Override
//    public void onGuiClosed() {
//        Keyboard.enableRepeatEvents(false);
//        if (this.NamedEntity instanceof IMoCTameable) {
//            IMoCTameable tamedEntity = (IMoCTameable) this.NamedEntity;
//            tamedEntity.playTameEffect(true);
//        }
//    }
//
//    @Override
//    public void updateScreen() {
//        this.updateCounter++;
//    }
}
