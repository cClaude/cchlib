package xxx;

public interface RowModel {

    Class<?> getColumnClass( int column );

    int getColumnCount();

    String getColumnName( int column );

    Object getValueFor( Object node, int column );

    boolean isCellEditable( Object node, int column );

    void setValueFor( Object node, int column, Object value );

}
