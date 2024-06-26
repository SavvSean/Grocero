import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class MainProgram extends JFrame implements ActionListener {
    
    private JLabel lblLocation, lblItemName, lblType, lblQuantity, lblPrice, lblSearch, lblSearchBy;
    private JTextField txtLocation, txtItemName, txtQuantity, txtPrice, txtSearch;
    private JComboBox<String> cboType, cboSearchBy;
    private JTable tblItem;
    private DefaultTableModel modelItem;
    private TableRowSorter<DefaultTableModel> tblSorter;
    private JButton btnAdd, btnClear, btnUpdate, btnDelete, btnClose, btnSave, btnLoad;

    private Font f = new Font("Arial", Font.BOLD, 16);
    // constructor
    public MainProgram() {
        initializeComponents();
        setupUI();
        addTableSelectionListener();

        btnSave.addActionListener(e -> Save("inventory.dat"));
        btnLoad.addActionListener(e -> Load("inventory.dat") );
    }
    // initializing components
    private void initializeComponents() {
        lblLocation = new JLabel("Location:");
        lblItemName = new JLabel("Item name:");
        lblType = new JLabel("Type:");
        lblQuantity = new JLabel("Quantity:");
        lblPrice = new JLabel("Price:");
        lblSearch = new JLabel("Search:");
        lblSearchBy = new JLabel("Search by:");

        txtLocation = new JTextField(20);
        txtItemName = new JTextField(20);
        txtQuantity = new JTextField(20);
        txtPrice = new JTextField(20);
        txtSearch = new JTextField(20);
        // type
        cboType = new JComboBox<>();
        cboType.addItem("Meat");
        cboType.addItem("Canned goods");
        cboType.addItem("Food");
        cboType.addItem("Beverages");
        cboType.addItem("Fresh Produce");
        cboType.addItem("Bakery Items");
        cboType.addItem("Dairy Products");
        cboType.addItem("Seafood");
        cboType.addItem("Grains");
        cboType.addItem("Household items");
        cboType.addItem("Personal Care");
        cboType.addItem("Condiments");
        cboType.addItem("Others");
        // searchby
        cboSearchBy = new JComboBox<>();
        cboSearchBy.addItem("Location");
        cboSearchBy.addItem("Item Name");
        cboSearchBy.addItem("Type");
        cboSearchBy.addItem("Quantity");
        cboSearchBy.addItem("Price");
        // buttons
        btnAdd = new JButton("Add New", new ImageIcon("IMAGES/icon/add_user.png"));
        btnClear = new JButton("Clear", new ImageIcon("IMAGES/icon/clear.png"));
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClose = new JButton("Close");
        btnSave = new JButton("Save");
        btnLoad = new JButton("Load");
        // actions listener
        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClose.addActionListener(this);
        btnSave.addActionListener(this);
        btnLoad.addActionListener(this);

        tblItem = new JTable();
        modelItem = new DefaultTableModel();
        tblItem.setModel(modelItem);
        tblSorter = new TableRowSorter<>(modelItem);
        tblItem.setRowSorter(tblSorter);
        tblItem.setAutoCreateRowSorter(true);

        String[] cols = {"Location", "Item Name", "Type", "Quantity", "Price"};
        modelItem.setColumnIdentifiers(cols);
    }

    private void setupUI() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);

        // Panels
        JPanel panelItemInfo = new JPanel();
        panelItemInfo.setBorder(BorderFactory.createTitledBorder("Item Registration Form"));
        panelItemInfo.setLayout(new GridLayout(5, 2, 10, 10));
        panelItemInfo.setFont(f);
        panelItemInfo.setOpaque(false);
        panelItemInfo.setBounds(10, 10, 300, 160);
        contentPane.add(panelItemInfo);

        panelItemInfo.add(lblLocation);
        panelItemInfo.add(txtLocation);
        panelItemInfo.add(lblItemName);
        panelItemInfo.add(txtItemName);
        panelItemInfo.add(lblType);
        panelItemInfo.add(cboType);
        panelItemInfo.add(lblQuantity);
        panelItemInfo.add(txtQuantity);
        panelItemInfo.add(lblPrice);
        panelItemInfo.add(txtPrice);

        JPanel panelButtonsTop = new JPanel();
        panelButtonsTop.setLayout(new GridLayout(2, 3, 15, 5));
        panelButtonsTop.setBounds(320, 20, 300, 60);
        contentPane.add(panelButtonsTop);

        panelButtonsTop.add(btnAdd);
        panelButtonsTop.add(btnClear);
        panelButtonsTop.add(btnSave);
        panelButtonsTop.add(btnLoad);

        JPanel panelSearch = new JPanel();
        panelSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelSearch.setBorder(BorderFactory.createTitledBorder("Search Items"));
        panelSearch.setFont(f);
        panelSearch.setBounds(620, 10, 300, 100);
        contentPane.add(panelSearch);

        panelSearch.add(lblSearchBy);
        panelSearch.add(cboSearchBy);
        panelSearch.add(lblSearch);
        panelSearch.add(txtSearch);

        JPanel panelItemList = new JPanel();
        panelItemList.setLayout(new BorderLayout());
        panelItemList.setBounds(320, 120, 600,230);
        contentPane.add(panelItemList);
        panelItemList.add(new JScrollPane(tblItem), BorderLayout.CENTER);

        JPanel panelButtonsBottom = new JPanel();
        panelButtonsBottom.setLayout(new GridLayout(1, 3, 10, 5));
        panelButtonsBottom.setBounds(320, 360, 600, 30);
        contentPane.add(panelButtonsBottom);

        panelButtonsBottom.add(btnUpdate);
        panelButtonsBottom.add(btnDelete);
        panelButtonsBottom.add(btnClose);

        // Event listeners
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                search();
            }
        });

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Grocero");
        setSize(950, 450);
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);
        setVisible(true);
    }

    private void search() {
        String searchText = txtSearch.getText().trim();
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tblItem.getRowSorter();
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Build filters based on selected criteria
        String searchBy = (String) cboSearchBy.getSelectedItem();
        switch (searchBy) {
        	case "Location":
        		filters.add(RowFilter.regexFilter("(?i)" + searchText, 0));
        		break;
            case "Item Name":
                filters.add(RowFilter.regexFilter("(?i)" + searchText, 1));
                break;
            case "Type":
                filters.add(RowFilter.regexFilter("(?i)" + searchText, 2));
                break;
            case "Quantity":
                try {
                    filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, Integer.parseInt(searchText), 3));
                } catch (NumberFormatException ex) {}
                break;
            case "Price":
                try {
                    filters.add(RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, Double.parseDouble(searchText), 4));
                } catch (NumberFormatException ex) {
                    // Handle non-double input
                }
                break;
        }

        // Combine filters with AND operator
        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(combinedFilter);
    }

    private void displayItemDetails(int rowIndex) {
        // Fetch item details from the table model
        String location = (String) modelItem.getValueAt(rowIndex, 0);
        String itemName = (String) modelItem.getValueAt(rowIndex, 1);
        String type = (String) modelItem.getValueAt(rowIndex, 2);
        String quantity = (String) modelItem.getValueAt(rowIndex, 3);
        String price = (String) modelItem.getValueAt(rowIndex, 4);

        // Create and configure the popup window
        JFrame popup = new JFrame("Item Details");
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Location:"));
        panel.add(new JLabel(location));
        panel.add(new JLabel("Item Name:"));
        panel.add(new JLabel(itemName));
        panel.add(new JLabel("Type:"));
        panel.add(new JLabel(type));
        panel.add(new JLabel("Quantity:"));
        panel.add(new JLabel(quantity));
        panel.add(new JLabel("Price:"));
        panel.add(new JLabel(price));

        popup.add(panel);
        popup.pack();
        popup.setLocationRelativeTo(this); // Position relative to the main frame
        popup.setVisible(true);
    }

    private void addTableSelectionListener() {
        ListSelectionModel selectionModel = tblItem.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblItem.getSelectedRow();
                if (selectedRow >= 0) {
                    displayItemDetails(selectedRow);
                }
            }
        });
    }

    private boolean validateInput() {
        if (txtLocation.getText().trim().isEmpty() || txtItemName.getText().trim().isEmpty() ||
                txtQuantity.getText().trim().isEmpty() || txtPrice.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            if (quantity < 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be a non-negative number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "Price must be a non-negative number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a valid number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void Save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int row = 0; row < modelItem.getRowCount(); row++) {
                String location = (String) modelItem.getValueAt(row, 0);
                String name = (String) modelItem.getValueAt(row, 1);
                String type = (String) modelItem.getValueAt(row, 2);
                String quantity = (String) modelItem.getValueAt(row, 3);
                String price = (String) modelItem.getValueAt(row, 4);

                writer.write(location + "," + name + "," + type + "," + quantity + "," + price);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Inventory data saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving inventory data: " + e.getMessage());
        }
    }




    private void Load(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "File not found: " + filename);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            modelItem.setRowCount(0); // Clear existing rows

            String line;
            while ((line = reader.readLine()) != null) {
                // Split by comma, trim whitespace, and handle unexpected formats
                String[] parts = line.split(",", -1); // -1 to handle trailing empty fields
                if (parts.length == 5) {
                    String location = parts[0].trim();
                    String name = parts[1].trim();
                    String type = parts[2].trim();
                    String quantity = parts[3].trim();
                    String price = parts[4].trim();
                    modelItem.addRow(new Object[]{location, name, type, quantity, price});
                }
            }
            JOptionPane.showMessageDialog(this, "Inventory data loaded successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading inventory data: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            if (validateInput()) {
                Vector<String> rowData = new Vector<>();
                rowData.add(txtLocation.getText());
                rowData.add(txtItemName.getText());
                rowData.add((String) cboType.getSelectedItem());
                rowData.add(txtQuantity.getText());
                rowData.add(txtPrice.getText());
                modelItem.addRow(rowData);
                clearInputFields();
                JOptionPane.showMessageDialog(this, "Item added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == btnClear) {
            clearInputFields();
        } else if (e.getSource() == btnUpdate) {
            int selectedRow = tblItem.getSelectedRow();
            if (selectedRow >= 0 && validateInput()) {
                modelItem.setValueAt(txtLocation.getText(), selectedRow, 0);
                modelItem.setValueAt(txtItemName.getText(), selectedRow, 1);
                modelItem.setValueAt((String) cboType.getSelectedItem(), selectedRow, 2);
                modelItem.setValueAt(txtQuantity.getText(), selectedRow, 3);
                modelItem.setValueAt(txtPrice.getText(), selectedRow, 4);
                JOptionPane.showMessageDialog(this, "Item updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == btnDelete) {
            int selectedRow = tblItem.getSelectedRow();
            if (selectedRow >= 0) {
                modelItem.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Item deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == btnClose) {
            System.exit(0);
        }
    }


    private void clearInputFields() {
        txtLocation.setText("");
        txtItemName.setText("");
        cboType.setSelectedIndex(0);
        txtQuantity.setText("");
        txtPrice.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainProgram program = new MainProgram();
            program.setVisible(true);
        });
    }
}
