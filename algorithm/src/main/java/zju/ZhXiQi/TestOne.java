package zju.ZhXiQi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestOne {
    public static void main(String[] args) throws IOException, InterruptedException {

        String pathOne = "/Users/ZhXiQi/OneDrive - zju.edu.cn/Music";
        String pathTwo = "/Users/ZhXiQi/Music";

        File fileOne = new File(pathOne);
        int fileNums = getFileNums(pathOne);
        List<File> fileList = new ArrayList<>(fileNums+10);
        File[] listFilesOne = fileOne.listFiles();
        Stream.of(listFilesOne).forEach(file -> {
            findFile(file,fileList);
        });

        File fileTwo = new File(pathTwo);
        File[] filesTwo = fileTwo.listFiles();
        int fileNumsTwo = getFileNums(pathTwo);
        List<File> targetFileList = new ArrayList<>(fileNumsTwo+10);
        Stream.of(filesTwo).parallel().forEach(file -> {
            findFile(file,targetFileList);
        });

        deleteDuplicate(targetFileList,fileList);
    }

    /**
     * 递归找文件，因为找的时候是开并行流找，找到文件添加到一个列表中，所以需要预先设置足够大的列表，否则会列表来不及扩容导致数组越界问题
     * @param file
     * @param fileList
     */
    public static void findFile(File file,List<File> fileList){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            Stream.of(files).parallel().forEach(f -> {
                findFile(f,fileList);
            });
        }else {
            fileList.add(file);
        }
    }

    /**
     * 利用shell指令来计算指定文件夹下的文件数
     * @param path 指定目录
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    public static int getFileNums(String path) throws InterruptedException, IOException {
        String replace = path.replace(" ", "\\ ");
        //Java具有使用Runtime.exec对本地程序调用进行重定向的能力，但是用重定向或者管道进行命令调用将会出错。解决这一问题的办法是通过命令shell运行命令
        String[] cmd = { "/bin/sh", "-c", "ls -lR "+replace+" | grep ^- | wc -l" };
        Process exec = Runtime.getRuntime().exec(cmd);
        exec.waitFor();
        //获取 shell 指令执行结果
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "UTF-8"));
        //shell指令执行错误流
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream(), "UTF-8"));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine())!=null){
            result.append(line);
        }
        return Integer.parseInt(result.toString().trim());
    }


    /**
     * 删除重复文件
     * @param origin
     * @param target
     */
    public static void deleteDuplicate(List<File> origin, List<File> target){
        target.parallelStream().forEach(file -> {
            origin.parallelStream().forEach(f -> {
                if (f != null){
                    //只是单纯的按照文件名来删除，如果有需要可以使用 文件hash等来确定是否同一份文件
                    if (f.getName().equals(file.getName())){
                        System.out.println(f.getAbsolutePath());
                        f.deleteOnExit();
                    }
                }
            });

        });
    }
}
