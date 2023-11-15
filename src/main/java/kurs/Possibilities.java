package kurs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Possibilities {
    public static void list(String path){
        File path1 = new File(path);
        if(path1.exists() && path1.isDirectory()){
            String[] lista = path1.list();
            if(lista.length == 0){
                System.out.println("Папка пуста.");
            } else {
                for (String s : lista) {
                    System.out.println(s);
                }
            }
        } else {
            System.out.println("Введен неверный путь!");
        }
    }

    public static void cd(String path) throws IOException {
        File path1 = new File(".");
        if(path1.exists() && path1.isDirectory()){
            System.out.println("В разработке!");
        } else {
            System.out.println("Введен неверный путь!");
        }
    }

    public static void info(String path){
        File path1 = new File(path);
        if(path1.exists()){
            System.out.println("Имя: " + path1.getName());
            System.out.println("Полный путь: " + path1.getAbsolutePath());
            System.out.println("Относительный путь: " + path1.getPath());
            System.out.println("Размер: " + path1.length());
            Path p = Paths.get(path);
            try {
                BasicFileAttributes bfa = Files.readAttributes(p, BasicFileAttributes.class);
                System.out.println("Создано: " + time(bfa.creationTime().toMillis()));
            } catch (IOException ex) {
                System.out.println(ex);
            }
            System.out.println("Последнее изменение: " + time(path1.lastModified()));
        } else {
            System.out.println("Введен неверный путь!");
        }
    }

    public static String time(long l){
        Instant instant = Instant.ofEpochMilli(l);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy. HH:mm:ss");
        return dateTime.format(dateTimeFormatter);
    }

    public static void createDir(String path){
        File folder = new File(path);
        String s = folder.getName();
        boolean bool = s.indexOf(92)*s.indexOf(47)*s.indexOf(58)*s.indexOf(63)
                *s.indexOf(42)*s.indexOf(34)*s.indexOf(60)*s.indexOf(62)
                *s.indexOf(124) < 0;
        try {
            if(!folder.exists()){
                if(bool){
                    makeDir(folder.getParentFile().exists(), folder);
                    System.out.println("Создал папку под названием " + folder.getName());
                } else {
                    System.out.println("Имя файла не может содержать ни один из следующих символов: ");
                    char[] chars = {92, 47, 58, 63, 42, 34, 60, 62, 124};
                    System.out.println(chars);
                }
            } else {
                System.out.println("Папка " + folder.getName() + " уже существует.");
            }
        } catch (Exception e) {
            System.out.println("Не удалось создать папку с названием " + folder.getName());
        }
    }

    public static void rename(String of, String nf) {
        File oldFile = new File(of);
        String[] strings = of.split("\\\\+");
        int n = strings.length;
        strings[n-1] = nf;
        StringBuilder sb = new StringBuilder();
        sb.append(strings[0]);
        sb.append("\\");
        for(int i = 1; i <strings.length; i++){
            sb.append("\\");
            sb.append(strings[i]);
        }
        File newFile = new File(sb.toString());
        if(newFile.exists()){
            System.out.println("Файл с нужным именем уже существует!");
            return;
        }
        if(oldFile.renameTo(newFile)){
            System.out.println("Переименовано успешно.");
        } else {
            System.out.println("Переименование не удалось!");
        }
    }

    public static void copyCut(File f1, File f2, String c){
        try (FileInputStream inStream = new FileInputStream(f1);
             FileOutputStream outStream = new FileOutputStream(f2);) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            if("move".equals(c)){
                f1.delete();
                System.out.println("Перемещение успешно завершено.");
            } else {
                System.out.println("Копирование успешно завершено.");
            }
        } catch (IOException e) {
            if("move".equals(c)){
                System.out.println("Ошибка перемещения файла!");
            } else {
                System.out.println("Ошибка при копирований файла!");
            }
        }
    }

    public static void delete(String path){
        File file = new File(path);
        if(file.exists()){
            if(file.isFile()){
                file.delete();
                System.out.println("Файл успешно удален!");
            } else {
                deleteDir(file);
                System.out.println("Папка успешно удалена!");
            }
        } else {
            System.out.println("Невозможно удалить " + file.getName() + " потому, что " + file.getName() + " не существует.");
        }
    }

    public static void deleteDir(File f){
        File[] files = f.listFiles();
        if(files != null){
            for(File f1 : files){
                deleteDir(f1);
            }
        }
        f.delete();
    }

    public static void copyCutDir(File f1, File f2, String c) {
        if(!f2.exists()){
            f2.mkdir();
        }
        String fs[] = f1.list();
        for (String f : fs) {
            copyCutDir(new File(f1, f), new File(f2, f), c);
        }
        if("move".equals(c)){
            deleteDir(f1);
        }
    }

    public static void makeDir(boolean b, File f){
        if(b){
            f.mkdir();
        } else {
            f.mkdirs();
        }
    }
}
