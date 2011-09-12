import java.awt.Color;
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
	private Color backgroundColor;
	private Color fontColor;
	
	public Item(String label, String id, BigDecimal value, Color color, Color fontColor) {
		super();
		this.label = label;
		this.id = id;
		this.value = value;
		this.backgroundColor = color;
		this.setFontColor(fontColor);
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
		
		String bdS = ""+bd;
		
		if (bdS.contains(".")) {
			return label + " " + value+"0";
		} else {
			return label + " " + value + ".00";
		}
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getFontColor() {
		return fontColor;
	}

}
