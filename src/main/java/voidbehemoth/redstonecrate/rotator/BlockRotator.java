package voidbehemoth.redstonecrate.rotator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryDefaulted;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import voidbehemoth.redstonecrate.RedstoneCrate;

import java.util.Random;

import static net.minecraft.util.EnumFacing.*;

public class BlockRotator extends Block {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool TRIGGERED;

    public static final ResourceLocation ROTATOR = new ResourceLocation(RedstoneCrate.MODID, "rotator");

    public BlockRotator() {
        super(Material.ROCK);
        setRegistryName(ROTATOR);
        setTranslationKey(RedstoneCrate.MODID + ".rotator");
        setCreativeTab(CreativeTabs.REDSTONE);
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TRIGGERED, false));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(TRIGGERED, false);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING, TRIGGERED});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7)).withProperty(TRIGGERED, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();
        if ((Boolean)state.getValue(TRIGGERED)) {
            i |= 8;
        }

        return i;
    }

    //@Override
    //public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    //    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    //}

    //@Overri
    //public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    //    super.onBlockAdded(worldIn, pos, state);
    //}

    public int tickRate(World worldIn) {
        return 4;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        boolean powered = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
        boolean triggered = (Boolean)state.getValue(TRIGGERED);
        if (powered && !triggered) {
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            worldIn.setBlockState(pos, state.withProperty(TRIGGERED, true), 4);
        } else if (!powered && triggered) {
            worldIn.setBlockState(pos, state.withProperty(TRIGGERED, false), 4);
        }
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            this.doRotate(worldIn, pos, state);
        }
    }

    public void doRotate(World world, BlockPos pos, IBlockState state) {
        EnumFacing newFacing = UP;
        rotateBlock(world, pos, newFacing);
        EnumFacing direction = (EnumFacing)state.getValue(FACING);
        System.out.println("Rotator is currently facing the block at " + pos.offset(direction));
    }

    static {
        // FACING = BlockDirectional.FACING;
        TRIGGERED = PropertyBool.create("triggered");
    }

}