package drzhark.mocreatures.block;

import static net.minecraftforge.common.util.ForgeDirection.UP;
import cpw.mods.fml.common.registry.GameRegistry;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class MoCBlock extends Block {
    public MoCBlock(String name, Material material) {
        super(material);
        this.setBlockName(name);
        this.setCreativeTab(MoCreatures.tabMoC);
        GameRegistry.registerBlock(this, MultiItemBlock.class, name);
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
        Block block = plant.getPlant(world, x, y + 1, z);
        EnumPlantType plantType = plant.getPlantType(world, x, y + 1, z);

        if (plant instanceof BlockFlower) {
            return true;
        }

        switch (plantType) {
            case Desert:
            case Nether:
            case Crop:
            case Water:
            	return false;
            case Cave:
            	return isSideSolid(world, x, y, z, UP);
            case Plains:
            	return this == MoCreatures.mocGrass || this == MoCreatures.mocDirt;
            case Beach:
                boolean isBeach = (this == MoCreatures.mocGrass || this == MoCreatures.mocDirt);
                boolean hasWater = (world.getBlock(x - 1, y, z    ).getMaterial() == Material.water ||
                                    world.getBlock(x + 1, y, z    ).getMaterial() == Material.water ||
                                    world.getBlock(x,     y, z - 1).getMaterial() == Material.water ||
                                    world.getBlock(x,     y, z + 1).getMaterial() == Material.water);
                return isBeach && hasWater;
        }
        return false;
    }

}
