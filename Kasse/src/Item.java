import java.awt.Component;
import java.math.BigDecimal;
import java.math.MathContext;


public class Item extends Component{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String label;
	private String id;
	private BigDecimal value;
	
	public Item(String label, String id, BigDecimal value) {
		super();
		this.label = label;
		this.id = id;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value.round(new MathContext(2));
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Item [label=" + label + ", id=" + id + ", value=" + value + "]";
	}
	
	public String getListString() {
		BigDecimal bd = getValue();
		return label + " " + value+"0";
	}

}
