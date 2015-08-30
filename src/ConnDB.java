import java.sql.*;

/**
 * Created by Support on 29.08.2015.
 */
public class ConnDB {

    private static final String url = "jdbc:mysql://localhost:3306/filesystem";
    private static final String user = "root";
    private static final String password = "14121480530832";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    private static int fileID;
    private static int folderID;
    private static int tmpMov;


    ConnDB() {
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Невозможно подключиться к БД");
        }
    }

    public void addFile(String file, String folder) {
        try {
            rs = stmt.executeQuery("SELECT id from Folders where name='" + folder + "'");
            rs.next();
            stmt.executeUpdate("INSERT INTO Files(name, folder_id) VALUES('" + file + "'," + rs.getInt(1) + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFolder(String folder, String parent) {
        if (parent.equals("NULL")) {
            try {
                stmt.executeUpdate("INSERT INTO Folders(name, parent_id) VALUES('" + folder + "'," + parent + "  )");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                rs = stmt.executeQuery("SELECT id from Folders where name='" + parent + "'");
                rs.next();
                stmt.executeUpdate("INSERT INTO Folders (name, parent_id) VALUES('" + folder + "'," + rs.getInt(1) + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delAll() {
        try {
            stmt.executeUpdate("DELETE FROM Files");
            stmt.executeUpdate("DELETE FROM Folders ORDER BY parent_id DESC");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Невозможно почистить таблицы");
        }
    }


    public boolean include(String file, String folder) {
        try {
            rs = stmt.executeQuery("SELECT id from Folders where name='" + folder + "'");
            rs.next();
            tmpMov = rs.getInt(1);
            rs = stmt.executeQuery("SELECT folder_id from Files where name='" + file + "' AND folder_id='" + tmpMov + "'");
            rs.next();
            int tmpf = rs.getInt(1);
            if (tmpMov == tmpf) {
                rs = stmt.executeQuery("SELECT id from Files where name='" + file + "'");
                rs.next();
                fileID = rs.getInt(1);
                return true;
            } else {
                folderID = tmpMov;
                return false;
            }
        } catch (SQLException e) {
            folderID = tmpMov;
            return false;
        }
    }

    public boolean include(String folder) {
        try {
            rs = stmt.executeQuery("SELECT id from Folders where name='" + folder + "'");
            rs.next();
            int tmp = rs.getInt(1);
            return true;

        } catch (SQLException e) {
            return false;
        }
    }

    public void moving() {
        try {
            stmt.executeUpdate("UPDATE Files SET folder_id='" + folderID + "' WHERE id='" + fileID + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() {
        try {
            con.close();
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Невозможно закрыть соединение");
        }

    }
}
