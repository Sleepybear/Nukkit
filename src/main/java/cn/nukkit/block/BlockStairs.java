package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.player.Player;
import cn.nukkit.utils.Faceable;
import cn.nukkit.utils.Identifier;
import com.nukkitx.math.vector.Vector3f;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public abstract class BlockStairs extends BlockTransparent implements Faceable {
    private final static int UPSIDE_DOWN_BIT = 0b0100;
    private final static int FACING_BITMASK = 0b0011; // Indicates which direction the full side is facing
    private final static int[] FACES = {2, 1, 3, 0}; // E W S N

    public BlockStairs(Identifier id) {
        super(id);
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, Vector3f clickPos, Player player) {
        this.setMeta(FACES[player != null ? player.getDirection().getHorizontalIndex() : 0]);
        if ((clickPos.getY() > 0.5 && face != BlockFace.UP) || face == BlockFace.DOWN) {
            this.setMeta(this.getMeta() | UPSIDE_DOWN_BIT); //Upside-down stairs
        }
        this.getLevel().setBlock(block.getPosition(), this, true, true);

        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= ItemTool.TIER_WOODEN) {
            return new Item[]{
                    toItem()
            };
        } else {
            return new Item[0];
        }
    }

    @Override
    public Item toItem() {
        Item item = super.toItem();
        item.setMeta(0);
        return item;
    }

    @Override
    public float calculateXOffset(AxisAlignedBB bb, float x) {
        if (bb.getMaxY() <= this.getMinY() || bb.getMinY() >= this.getMaxY()) {
            return x;
        }
        if (bb.getMaxZ() <= this.getMinZ() || bb.getMinZ() >= this.getMaxZ()) {
            return x;
        }
        AxisAlignedBB top = getTopStair();
        AxisAlignedBB bottom = getBottomStair();
        if (top == null || bottom == null) return x;

        float dx = top.calculateXOffset(bb, x);
        return bottom.calculateXOffset(bb, dx);
    }

    @Override
    public float calculateYOffset(AxisAlignedBB bb, float y) {
        if (bb.getMaxX() <= this.getMinX() || bb.getMinX() >= this.getMaxX()) {
            return y;
        }
        if (bb.getMaxZ() <= this.getMinZ() || bb.getMinZ() >= this.getMaxZ()) {
            return y;
        }
        AxisAlignedBB top = getTopStair();
        AxisAlignedBB bottom = getBottomStair();
        if (top == null || bottom == null) return y;

        if (y > 0 && bb.getMaxY() <= this.getMinY()) {
            return bottom.calculateYOffset(bb, y);
        }

        if (y < 0 && bb.getMinY() >= this.getMinY()) {
            return top.calculateYOffset(bb, y);
        }
        return y;
    }

    @Override
    public float calculateZOffset(AxisAlignedBB bb, float z) {
        if (bb.getMaxX() <= this.getMinX() || bb.getMinX() >= this.getMaxX()) {
            return z;
        }
        if (bb.getMaxY() <= this.getMinY() || bb.getMinY() >= this.getMaxY()) {
            return z;
        }
        AxisAlignedBB top = getTopStair();
        AxisAlignedBB bottom = getBottomStair();
        if (top == null || bottom == null) return z;

        float dz = top.calculateXOffset(bb, z);
        return bottom.calculateXOffset(bb, dz);
    }

    @Override
    public boolean collidesWithBB(AxisAlignedBB bb) {
        AxisAlignedBB topStep = getTopStair();
        AxisAlignedBB bottomStep = getBottomStair();

        return (topStep != null && bottomStep != null) &&
                bb.intersectsWith(topStep) || bb.intersectsWith(bottomStep);
    }

    private AxisAlignedBB getTopStair() {
        if ((this.getMeta() & UPSIDE_DOWN_BIT) > 0) {
            return new SimpleAxisAlignedBB(
                    this.getX(), this.getY() + 0.5f, this.getZ(),
                    this.getX() + 1, this.getY() + 1, this.getZ() + 1);
        } else {
            int side = this.getMeta() & FACING_BITMASK;
            if (side == 0) {
                return new SimpleAxisAlignedBB(
                        this.getX() + 0.5f, this.getY() + 0.5f, this.getZ(),
                        this.getX() + 1, this.getY() + 1, this.getZ() + 1);
            } else if (side == 1) {
                return new SimpleAxisAlignedBB(
                        this.getX(), this.getY() + 0.5f, this.getZ(),
                        this.getX() + 0.5f, this.getY() + 1, this.getZ() + 1);
            } else if (side == 2) {
                return new SimpleAxisAlignedBB(
                        this.getX(), this.getY() + 0.5f, this.getZ() + 0.5f,
                        this.getX() + 1, this.getY() + 1, this.getZ() + 1);
            } else if (side == 3) {
                return new SimpleAxisAlignedBB(
                        this.getX(), this.getY() + 0.5f, this.getZ(),
                        this.getX() + 1, this.getY() + 1, this.getZ() + 0.5f);
            }
        }
        return null;
    }

    private AxisAlignedBB getBottomStair() {
        if ((this.getMeta() & UPSIDE_DOWN_BIT) == 0) {
            new SimpleAxisAlignedBB(
                    this.getX(), this.getY(), this.getZ(),
                    this.getX() + 1, this.getY() + 0.5f, this.getZ() + 1);
        } else {
            int side = this.getMeta() & FACING_BITMASK;
            if (side == 0) {
                return new SimpleAxisAlignedBB(
                        this.getX() + 0.5f, this.getY(), this.getZ(),
                        this.getX() + 1, this.getY() + 0.5f, this.getZ() + 1);
            } else if (side == 1) {
                return new SimpleAxisAlignedBB(
                        this.getX(), this.getY(), this.getZ(),
                        this.getX() + 0.5f, this.getY() + 0.5f, this.getZ() + 1);
            } else if (side == 2) {
                return new SimpleAxisAlignedBB(
                        this.getX(), this.getY(), this.getZ() + 0.5f,
                        this.getX() + 1, this.getY() + 0.5f, this.getZ() + 1);
            } else if (side == 3) {
                return new SimpleAxisAlignedBB(
                        this.getX(), this.getY(), this.getZ(),
                        this.getX() + 1, this.getY() + 0.5f, this.getZ() + 0.5f);
            }

        }
        return null;
    }

    @Override
    public BlockFace getBlockFace() {
        return BlockFace.fromHorizontalIndex(this.getMeta() & FACING_BITMASK);
    }

    @Override
    public boolean canWaterlogSource() {
        return true;
    }
}
