package com.googlecode.cchlib.net.dhcp.server0;
//
//import java.util.ArrayList;
//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.table.TableModel;
//
//public class _DHCPTable extends JTable
//{
//    private static final long serialVersionUID = 1L;
//
////
////static Object[][] data_ = {
////                   {"Mary", "Campione",
////                    "Snowboarding", new Integer(5), new Boolean(false)},
////                   {"Alison", "Huml",
////                    "Rowing", new Integer(3), new Boolean(true)},
////                   {"Kathy", "Walrath",
////                    "Knitting", new Integer(2), new Boolean(false)},
////                   {"Sharon", "Zakhour",
////                    "Speed reading", new Integer(20), new Boolean(true)},
////                   {"Philip", "Milne",
////                    "Pool", new Integer(10), new Boolean(false)}
////               };
////       
//
//       private ArrayList<Object[]> _list = new ArrayList<Object[]>();
//
//       public _DHCPTable() {
//               init();
//       }
//       
//       public _DHCPTable(Object[][] data, Object[] columnNames) {
//               super(data,columnNames);
//               init();
//       }
//       
//
//
//       public _DHCPTable(TableModel dataModel) {
//               super(dataModel);
//               init();
//       }
//       
//       private void init() {
//               JFrame frame = new JFrame("DHCP Table");
//               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//               
//               //JTable table = new JTable(dataModel);
//               JScrollPane scrollPane = new JScrollPane(this);
//               this.setFillsViewportHeight(true);
//               
//               frame.add(scrollPane);
//               //frame.add(table);
//               
//               //Display the window.
//       frame.pack();
//       frame.setVisible(true);
//               
//       }
//       
//}