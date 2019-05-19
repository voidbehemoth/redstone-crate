package voidbehemoth.redstonecrate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSponge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import voidbehemoth.redstonecrate.rotator.BlockRotator;

public class ModBlocks {
    @GameRegistry.ObjectHolder("redstonecrate:rotator")
    public static BlockRotator blockRotator;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockRotator.initModel();
    }
}
