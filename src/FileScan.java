import java.io.File;

/**
 * Created by Support on 29.08.2015.
 */
public class FileScan {

    private static ConnDB connDB = new ConnDB();

    FileScan(String str) {
        connDB.delAll();
        String startFolder = str.substring(str.lastIndexOf("\\") + 1);
        connDB.addFolder(startFolder, "NULL");
        getFile(str);
    }

    public static void getFile(String path) {
        try {
            File f = new File(path);
            for (File s : f.listFiles()) {
                if (s.isFile()) {
                    String fileName = s.getName();
                    int tmpSize = s.getAbsolutePath().length() - fileName.length();
                    String parentFolder = s.getAbsolutePath().substring(s.getAbsolutePath().lastIndexOf("\\", tmpSize - 2) + 1, tmpSize - 1);
                    connDB.addFile(fileName, parentFolder);
                } else if (s.isDirectory()) {
                    String folderName = s.getName();
                    if (!s.getName().equals(path.substring(path.lastIndexOf("\\") + 1))) {
                        int tmpSize = s.getAbsolutePath().length() - folderName.length();
                        String parentFolder = s.getAbsolutePath().substring(s.getAbsolutePath().lastIndexOf("\\", tmpSize - 2) + 1, tmpSize - 1);
                        connDB.addFolder(folderName, parentFolder);
                        getFile(s.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR! Проверьте правильность ввода пути директории для сканирования!");
        }
    }
}
