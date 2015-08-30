import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Support on 29.08.2015.
 */
public class VirtualFileSystem {

    public static void main(String[] args) throws IOException {

        ConnDB test = new ConnDB();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String console = " ";
        String command = " ";

        while (!command.equals("exit")) {
            System.out.print(">");
            console = reader.readLine() + " ";
            command = console.substring(0, console.indexOf(" ")).toLowerCase();
            switch (command) {
                case "scan":
                    try {
                        String path = console.substring(5, console.indexOf(" ", 5));
                        FileScan fs = new FileScan(path);
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("ERROR! Неправильный синтаксис команды. Введите [help] для помощи.");
                    }
                    break;

                case "mv":
                    try {
                        int i = console.indexOf(" ", 3);
                        String file1 = getFile(console.substring(3, i));
                        String folder1 = getFolder(console.substring(3, i));
                        String file2 = getFile(console.substring(i + 1, console.indexOf(" ", i + 1)));
                        String folder2 = getFolder(console.substring(i + 1, console.indexOf(" ", i + 1)));
                        if (test.include(file1, folder1)) {
                            if (!test.include(file2, folder2)) {
                                if (test.include(folder2)) {
                                    test.moving();
                                } else System.out.println("ERROR! Конечной папки не существует!");
                            } else System.out.println("ERROR! Файл с таким именем уже существует!");
                        } else System.out.println("ERROR! Неправильно указан искомый файл!");
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("ERROR! Неправильный синтаксис команды. Введите [help] для помощи.");
                    }
                    break;

                case "cp":
                    try {
                        int j = console.indexOf(" ", 3);
                        String file1cp = getFile(console.substring(3, j));
                        String folder1cp = getFolder(console.substring(3, j));
                        String file2cp = getFile(console.substring(j + 1, console.indexOf(" ", j + 1)));
                        String folder2cp = getFolder(console.substring(j + 1, console.indexOf(" ", j + 1)));
                        if (test.include(file1cp, folder1cp)) {
                            if (!test.include(file2cp, folder2cp)) {
                                if (test.include(folder2cp)) {
                                    test.addFile(file2cp, folder2cp);
                                } else System.out.println("ERROR! Конечной папки не существует!");
                            } else System.out.println("ERROR! Файл с таким именем уже существует!");
                        } else System.out.println("ERROR! Неправильно указан искомый файл!");
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("ERROR! Неправильный синтаксис команды. Введите [help] для помощи.");
                    }
                    break;

                case "rm":
                    String pathDell = console.substring(3, console.indexOf(" ", 3));
                    //TODO  Выяснить папка это или файл и запустить удаление.

                    System.out.println("Удаляем путь: " + pathDell);
                    break;

                case "help":
                    System.out.println("Для работы вам необходимо ввести одну из команд:");
                    System.out.println("scan #path – сканирует путь и загружает содержимое папки в базу");
                    System.out.println("mv #filename #filename2 – перемещает файл из одной директории в другую");
                    System.out.println("cp #filename #filename2 – копирует файл из одной директории в другую");
                    System.out.println("rm [#filename или #foldername] - удаляет папку со всем содержимым или файл");
                    System.out.println("exit - выход из приложения");

                case "exit":
                    break;

                default:
                    System.out.println("ERROR! Неправильный синтаксис команды. Введите [help] для помощи.");
            }
        }
    }

    private static String getFile(String path) {
        String fileName = path.substring(path.lastIndexOf("\\") + 1);

        return fileName;
    }

    private static String getFolder(String path) {
        String fileName = getFile(path);
        int tmpSize = path.length() - fileName.length();
        String folderName = path.substring(path.lastIndexOf("\\", tmpSize - 2) + 1, tmpSize - 1);

        return folderName;
    }
}
