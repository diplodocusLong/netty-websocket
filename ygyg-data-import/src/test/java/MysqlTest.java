import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlTest {

    /**
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql:10.38.77.61:3306/ygyg-portal", "mod_all", "mod_all");
//        System.out.println(getTables(conn));
//        System.out.println("=============================");
//        Statement st = conn.createStatement();
//        ResultSet rs = st.executeQuery("select * from user");
//        List<String> colNames = getColNames(rs);
//        while(rs.next()){
//            for (int i = 0; i < colNames.size(); i++) {
//                System.out.print(rs.getObject(colNames.get(i)));
//                if(i!=colNames.size()-1)
//                    System.out.print("\t");
//            }
//            System.out.println();
//        }
//        rs.close();
//        st.close();
//        conn.close();

        String s = "分布式供暖系统信息模型";
        s = s.substring(0, s.length() - 4);
        System.out.println(s.length());
        System.out.println(s);
    }

    /**
     * 获取数据库中所有表名称
     *
     * @param conn
     * @return
     * @throws SQLException
     */
    private static List<String> getTables(Connection conn) throws SQLException {
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        ResultSet tables = databaseMetaData.getTables(null, null, "%", null);
        ArrayList<String> tablesList = new ArrayList<String>();
        while (tables.next()) {
            tablesList.add(tables.getString("TABLE_NAME"));
        }
        return tablesList;
    }

    /**
     * 获取表中所有字段名称
     *
     * @param rs
     * @throws SQLException
     */
    private static List<String> getColNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount();
        System.out.println("getCatalogName(int column) 获取指定列的表目录名称。" + metaData.getCatalogName(1));
        System.out.println("getColumnClassName(int column) 构造其实例的 Java 类的完全限定名称。" + metaData.getColumnClassName(1));
        System.out.println("getColumnCount()  返回此 ResultSet 对象中的列数。" + metaData.getColumnCount());
        System.out.println("getColumnDisplaySize(int column) 指示指定列的最大标准宽度，以字符为单位. " + metaData.getColumnDisplaySize(1));
        System.out.println("getColumnLabel(int column) 获取用于打印输出和显示的指定列的建议标题。 " + metaData.getColumnLabel(1));
        System.out.println("getColumnName(int column)  获取指定列的名称。" + metaData.getColumnName(1));
        System.out.println("getColumnType(int column) 获取指定列的 SQL 类型。 " + metaData.getColumnType(1));
        System.out.println("getColumnTypeName(int column) 获取指定列的数据库特定的类型名称。 " + metaData.getColumnTypeName(1));
        System.out.println("getPrecision(int column)  获取指定列的指定列宽。 " + metaData.getPrecision(1));
        System.out.println("getScale(int column) 获取指定列的小数点右边的位数。 " + metaData.getScale(1));
        System.out.println("getSchemaName(int column) 获取指定列的表模式。 " + metaData.getSchemaName(1));
        System.out.println("getTableName(int column) 获取指定列的名称。 " + metaData.getTableName(1));
        List<String> colNameList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            colNameList.add(metaData.getColumnName(i));
        }
        System.out.println(colNameList);
//		rs.close();
        rs.first();
        return colNameList;
    }

}
