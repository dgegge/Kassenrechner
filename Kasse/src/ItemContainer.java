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
	
	private ItemContainer() {
		super();
		items = new ArrayList<Item>();
	}
	
	public static ItemContainer getItemContainer() {
		if (instance == null) 
			instance = new ItemContainer();
		
		return instance;
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
}
