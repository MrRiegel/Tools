package mrriegel.tools;

import java.util.List;

import mrriegel.limelib.LimeLib;
import mrriegel.limelib.helper.WorldHelper;
import mrriegel.tools.proxy.CommonProxy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

@Mod(modid = Tools.MODID, name = Tools.MODNAME, version = Tools.VERSION, dependencies = "required-after:limelib@[1.4.0,)")
public class Tools {
	public static final String MODID = "tools";
	public static final String VERSION = "1.0.0";
	public static final String MODNAME = "Tools";

	@Instance(Tools.MODID)
	public static Tools instance;

	@SidedProxy(clientSide = "mrriegel.tools.proxy.ClientProxy", serverSide = "mrriegel.tools.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@SubscribeEvent
	public void render(DrawBlockHighlightEvent event) {
		if (event.getTarget().typeOfHit != Type.BLOCK)
			return;
		List<BlockPos> lis = WorldHelper.getCuboid(event.getTarget().getBlockPos(), 2);
		lis.remove(event.getTarget().getBlockPos());
		if (LimeLib.proxy.getClientPlayer().getHeldItemMainhand().getItem() == ModItems.pick)
			for (BlockPos p : lis) {
				event.getContext().drawSelectionBox(LimeLib.proxy.getClientPlayer(), new RayTraceResult(Vec3d.ZERO, EnumFacing.DOWN, p), 0, event.getPartialTicks());
			}
	}

	@SubscribeEvent
	public void line(RenderWorldLastEvent event) {
		boolean line = true;
		if (line) {
			//			BlockPos p1 = LimeLib.proxy.getClientRayTrace().getBlockPos().down();
			//			BlockPos p2 = p1.up(3).north();
			EntityPlayer player = LimeLib.proxy.getClientPlayer();
			Vec3d eye = player.getPositionEyes(event.getPartialTicks());
			Vec3d look = player.getLook(event.getPartialTicks());
			Vec3d v1 = eye.add(look.scale(5));
			Vec3d v2 = v1.addVector(.3, 3.1, .89);

			double playerX = TileEntityRendererDispatcher.staticPlayerX;
			double playerY = TileEntityRendererDispatcher.staticPlayerY;
			double playerZ = TileEntityRendererDispatcher.staticPlayerZ;

			GlStateManager.pushMatrix();
			GlStateManager.pushAttrib();
			//        GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			//        GL11.glDisable(GL11.GL_DEPTH_TEST);

			GL11.glTranslated(-playerX, -playerY, -playerZ);
			GL11.glColor4ub((byte) 0, (byte) 145, (byte) 229, (byte) 200);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
			//			GL11.glLineWidth(4f);
			GL11.glBegin(GL11.GL_LINES);

			GL11.glVertex3d(v1.xCoord + 0.5, v1.yCoord + 0.5, v1.zCoord + 0.5);
			GL11.glVertex3d(v2.xCoord + 0.5, v2.yCoord + 0.5, v2.zCoord + 0.5);
			GL11.glEnd();
			//        GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			//        GL11.glEnable(GL11.GL_DEPTH_TEST);
			GlStateManager.popAttrib();
			GlStateManager.popMatrix();

		}
	}

}