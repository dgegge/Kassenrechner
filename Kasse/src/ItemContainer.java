import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ItemContainer {
	
	private static ItemContainer instance;
	private List <Item> items;
	private List <List> allItems;
	private Set<String> itemIds;
	
	private ItemContainer() {
		super();
		items = new ArrayList<Item>();
		allItems = new ArrayList<List>();
	}
	
	public static ItemContainer getItemContainer() {
		if (instance == null) 
			instance = new ItemContainer();
		
		return instance;
	}
	
	public void setItemTypes(Set<String> itemIds) {
		this.itemIds = itemIds;
	}
	
	public void addList(List<Item> items) {
		this.items.addAll(items);
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}
	
	public void deleteItems() {
		this.items.clear();
	}
	
	public BigDecimal calculateSum() {
		BigDecimal d = new BigDecimal(0.0);
		d.setScale(5);
		for (Item item : items) {
			d = d.add(item.getValue());
		}
		return d;
	}
	
	public Map<String, List<Item>> getStats() {
		Map<String, List<Item>> map = new HashMap<String, List<Item>>();
		for (String key : this.itemIds) {
			List<Item> keyItems = getItemsOfListWithKey(this.items, key);
			map.put(key, keyItems);
		}
		return map;
	}
	
	private List<Item> getItemsOfListWithKey( List<Item> items, String key) {
		List<Item> retItems = new ArrayList<Item>();
		for (Item item : items) {
			if (key.equals(item.getId())) {
				retItems.add(item);
			}
		}
		return retItems;
	}

}
