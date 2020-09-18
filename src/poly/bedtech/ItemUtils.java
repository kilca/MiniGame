package poly.bedtech;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtils {

	
	public static void setItemTag(ItemStack itemStack, String tag, String content) {
		

		
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		itemMeta.getPersistentDataContainer().set(key(tag), PersistentDataType.STRING, content);
		
		itemStack.setItemMeta(itemMeta);
		
	}
	
	
	public static String getItemTag(ItemStack itemStack, String tag) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		return itemMeta.getPersistentDataContainer().get(key(tag), PersistentDataType.STRING);
		
	}
	
    private static NamespacedKey key(String key) {
        return new NamespacedKey(MinGame.INSTANCE, key);
    }
	
	
	
}
