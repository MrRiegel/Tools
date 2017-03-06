package mrriegel.tools;

import mrriegel.limelib.item.CommonItem;
import mrriegel.tools.item.ItemAxe;
import mrriegel.tools.item.ItemAxpickvel;
import mrriegel.tools.item.ItemFoodBag;
import mrriegel.tools.item.ItemLifter;
import mrriegel.tools.item.ItemPick;
import mrriegel.tools.item.ItemShovel;
import mrriegel.tools.item.ItemToolUpgrade;
import mrriegel.tools.item.ItemTorchLauncher;

public class ModItems {

	public static final CommonItem pick = new ItemPick();
	public static final CommonItem axe = new ItemAxe();
	public static final CommonItem shovel = new ItemShovel();
	public static final CommonItem multi = new ItemAxpickvel();
	public static final CommonItem foodbag = new ItemFoodBag();
	public static final CommonItem upgrade = new ItemToolUpgrade();
	public static final CommonItem torch = new ItemTorchLauncher();
	public static final CommonItem lifter = new ItemLifter();

	public static void init() {
		pick.registerItem();
		axe.registerItem();
		shovel.registerItem();
		multi.registerItem();
		foodbag.registerItem();
		upgrade.registerItem();
		torch.registerItem();
		lifter.registerItem();
	}

	public static void initClient() {
		pick.initModel();
		axe.initModel();
		shovel.initModel();
		multi.initModel();
		foodbag.initModel();
		upgrade.initModel();
		torch.initModel();
		lifter.initModel();
	}

	//	private static void register(Item item) {
	//		GameRegistry.register(item);
	//	}
	//
	//	private static void initModel(Item item) {
	//		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	//	}
}
