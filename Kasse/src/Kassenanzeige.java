import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Kassenanzeige extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3477302782691538527L;
	
	private final Map<String, Item> items;
	private final Action resetAction;
	private final Action sumAction;
	private final Action gegebenAction;
	private JList einzelPosten;
	private DefaultListModel einzelPostenList;
	private JTextField ergebnisText;
	private ItemContainer itemContainer = ItemContainer.getItemContainer();
	private JTable statTable;
	private JTextField gegebenText;
	private JTextField rueckgeldText;
	
	public Kassenanzeige() {
		items = generateItems();
		JPanel buttonpanel = new JPanel();
		ergebnisText = new JTextField();
		ergebnisText.setEditable(false);
		ergebnisText.setColumns(10);
		sumAction = new ErgebnisAction(ergebnisText);
		
		gegebenText = new JTextField();
		gegebenText.setColumns(10);
		
		generateButtonPanelItems(items, buttonpanel, sumAction, gegebenText);
		
		JPanel ergebnisButtonPanel = new JPanel();
		
		JLayeredPane calcPane = new JLayeredPane();

		rueckgeldText = new JTextField();
		rueckgeldText.setEditable(false);
		rueckgeldText.setColumns(10);

		JButton btnErgebnis = new JButton("Ergebnis");
		btnErgebnis.addActionListener(sumAction);
		//ergebnisButtonPanel.add(btnErgebnis);
		
		JLayeredPane statPane = new JLayeredPane();

		JLabel lblGegeben = new JLabel("Gegeben");
		
		JLabel lblRckgeld = new JLabel("R\u00FCckgeld");
		
		JLabel lblSumme = new JLabel("Summe");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(buttonpanel, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(calcPane, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(statPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblGegeben)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(gegebenText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblSumme)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(ergebnisText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblRckgeld)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(rueckgeldText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(ergebnisButtonPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(statPane, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(ergebnisText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSumme))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblGegeben)
								.addComponent(gegebenText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rueckgeldText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRckgeld))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ergebnisButtonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(calcPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
						.addComponent(buttonpanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
					.addGap(150))
		);
		
		JButton btnBezahlt = new JButton("Bezahlt");
		ergebnisButtonPanel.add(btnBezahlt);
		gegebenAction = new RueckgeldGegebenAction(gegebenText, ergebnisText, rueckgeldText);
		gegebenText.addActionListener(gegebenAction);
		btnBezahlt.addActionListener(gegebenAction);
		
		JButton btnOk = new JButton("OK");
		//ergebnisButtonPanel.add(btnOk);
		
		JButton btnReset = new JButton("Reset");
		ergebnisButtonPanel.add(btnReset);
		resetAction = new ResetAction(einzelPostenList, ergebnisText, this.gegebenText, this.rueckgeldText, this.einzelPostenList);
		btnReset.setAction(resetAction);
		
		statTable = new JTable();
		statTable.setBounds(10, 11, 339, 220);
//		statPane.add(statTable);
		
		calcPane.add(einzelPosten);
		getContentPane().setLayout(groupLayout);
		
		setVisible(true);
		this.setSize(817, 638);
	}
	
	private void generateButtonPanelItems(Map<String, Item> items, JPanel buttonPanel, Action action, JTextField gegebenText) {
		this.einzelPostenList = new DefaultListModel();
		this.einzelPosten = new JList(einzelPostenList);
		
		//deleteAction
		einzelPosten.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			
			}
		});
		
		einzelPosten.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		einzelPosten.setBounds(10, 11, 248, 412);
		for (String key : items.keySet()) {
			Item item = items.get(key);
			JButton button = new JButton(item.getLabel());
			button.addActionListener(new ButtonItemAction(item, einzelPostenList, action, gegebenText));
			buttonPanel.add(button);
		}
	}
	
	private Map<String, Item> generateItems() {
		MathContext mc = new MathContext(2);
		Map<String, Item> itemMap = new HashMap<String,Item>();
		itemMap.put("Wurst", new Item("Wurst", "Wurst", new BigDecimal(2.20,mc)));
		itemMap.put("Bier", new Item("Bier", "Bier", new BigDecimal(2.20,mc)));
		itemMap.put("Pommes", new Item("Pommes", "Pommes", new BigDecimal(1.30,mc)));
		itemMap.put("Cola", new Item("Cola", "Cola", new BigDecimal(1.5,mc)));
		return itemMap;
		
	}
	
	private class RueckgeldGegebenAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4651004013756674073L;
		JTextField gegebenText;
		JTextField summeText;
		JTextField rueckgeldText;
		
		public RueckgeldGegebenAction(JTextField gegebenText, JTextField summeText, JTextField rueckgeldText) {
			this.gegebenText = gegebenText;
			this.summeText = summeText;
			this.rueckgeldText = rueckgeldText;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			MathContext mc = new MathContext(5);
			BigDecimal rueckgeld = new BigDecimal(0, mc);
			rueckgeld.setScale(5);
			BigDecimal summe = new BigDecimal(summeText.getText(), mc);
			summe.setScale(5);
			BigDecimal gegeben = new BigDecimal(gegebenText.getText(), mc);
			gegeben.setScale(5);
			
			rueckgeld = summe.negate().add(gegeben);
			
			this.rueckgeldText.setText(rueckgeld.toString());
		}
		
	}

	private class ButtonItemAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5818085178167205425L;
		Item item;
		DefaultListModel list;
		Action action;
		JTextField focusText;
		
		public ButtonItemAction(Item item, DefaultListModel list, Action action, JTextField focusText) {
			this.item = item;
			this.list = list;
			this.action = action;
			this.focusText = focusText;
			putValue(NAME, "Item Action for Item : " +  item.toString());
			putValue(SHORT_DESCRIPTION, "ItemAction" + item.getId());
		}
		public void actionPerformed(ActionEvent e) {
			list.addElement(item.getListString());
			ItemContainer.getItemContainer().addItem(item);
			action.actionPerformed(e);
			focusText.requestFocus();
		}
	}

	private class ResetAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6041331932753470419L;
		DefaultListModel list;
		JTextField ergebnisfeld;
		JTextField gegebenfeld;
		JTextField rueckgeldfeld;
		DefaultListModel ergebnislist;
		
		public ResetAction(DefaultListModel list, JTextField ergebnisfeld, JTextField gegebenfeld, JTextField rueckgeldfeld, DefaultListModel ergebnislist) {
			this.list = list;
			this.ergebnisfeld = ergebnisfeld;
			this.ergebnislist = ergebnislist;
			this.gegebenfeld = gegebenfeld;
			this.rueckgeldfeld = rueckgeldfeld;
			ItemContainer.getItemContainer().deleteItems();
			putValue(NAME, "Reset");
			putValue(SHORT_DESCRIPTION, "Resets the sum");
		}
		public void actionPerformed(ActionEvent e) {
			ergebnisfeld.setText("");
			gegebenfeld.setText("");
			rueckgeldfeld.setText("");
			ergebnislist.clear();
			ItemContainer.getItemContainer().deleteItems();
		}
	}
	
	private class ErgebnisAction extends AbstractAction {		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4078989043749753369L;
		JTextField label; 
		
		public ErgebnisAction(JTextField label) {
			this.label = label;
			putValue(NAME, "Ergebnis");
			putValue(SHORT_DESCRIPTION, "Resets the sum");
		}
		public void actionPerformed(ActionEvent e) {
			BigDecimal sum2 = ItemContainer.getItemContainer().calculateSum();
			label.setText(""+sum2);
		}
	}
}
