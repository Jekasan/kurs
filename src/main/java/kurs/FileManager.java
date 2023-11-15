package kurs;

import java.util.Scanner;
import java.io.*;

public class FileManager extends Possibilities {
    public static void main(String[] args) throws IOException {
        String commands = "Доступные команды: \nls\ncd\nfinfo\nmkdir\ncp\nrn\nmv\nrm\nexit\nhelp\n";
        System.out.print(commands);
        Scanner s = new Scanner(System.in);
        String message = "Введите желаемую команду: ";
        OUTER:
        while (true) {
            System.out.println(message);
            String str = s.nextLine().toLowerCase().trim();
            if (null == str) {
                System.out.println("Команда, которую вы ввели, не существует!");
            } else if("help".equals(str)){
                System.out.println(commands);
                message = "Введите желаемую команду:";
            } else {
                switch (str) {
                    case "ls":
                        System.out.println("Введите путь к папке: ");
                        list(s.nextLine().trim());
                        break;
                    case "cd":
                        System.out.println("Введите путь к папке: ");
                        cd(s.nextLine().trim());
                        break;
                    case "finfo":
                        System.out.println("Введите путь к желаемому файлу/папке: ");
                        info(s.nextLine().trim());
                        break;
                    case "mkdir":
                        System.out.println("Введите путь к новой папке: ");
                        createDir(s.nextLine().trim());
                        break;
                    case "rn":
                    {
                        System.out.println("Введите путь к файлу/папке, которую вы хотите переименовать: ");
                        String s1 = s.nextLine().trim();
                        File f = new File(s1);
                        if(!f.exists()){
                            System.out.println("Файл не существует!");
                            return;
                        }
                        System.out.println("Введите новое имя файла/папки: ");
                        String s2 = s.nextLine().trim();
                        rename(s1, s2);
                        break;
                    }
                    case "cp":
                    case "mv":
                    {
                        System.out.println("Введите путь к файлу/папке, которую вы хотите скопировать/переместить: ");
                        String s1 = s.nextLine().trim();
                        System.out.println("Введите путь назначения: ");
                        String s2 = s.nextLine().trim();
                        File f1 = new File(s1);
                        File f2 = new File(s2);
                        if(f1.isDirectory()){
                            try{
                                copyCutDir(f1, f2, str);
                                if("move".equals(str)){
                                    System.out.println("Перемещение успешно завершено.");
                                } else {
                                    System.out.println("Копирование успешно завершено.");
                                }
                            } catch(Exception e) {
                                if("move".equals(str)){
                                    System.out.println("Ошибка перемещения файла!");
                                } else {
                                    System.out.println("Ошибка при копирований файла!");
                                }
                            }
                        } else {
                            copyCut(f1, f2, str);
                        }
                        break;
                    }
                    case "rm":
                        System.out.println("Введите путь к файлу/папке, который вы хотите удалить: ");
                        delete(s.nextLine().trim());
                        break;
                    case "exit":
                        System.out.println("Вы закрыли файловый менеджер.");
                        break OUTER;
                    default:
                        System.out.println("Команда, которую вы ввели, не существует!");
                        break;
                }
                message = "Введите желаемую команду (help для доступных команд): ";
            }
        }
    }


}
