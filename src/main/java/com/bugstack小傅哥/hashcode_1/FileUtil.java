package com.bugstack小傅哥.hashcode_1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class FileUtil {

    /**
     * 读取本地文件，单词表
     * @param url 单词表.txt文件
     * @return 单词集合(去重)
     */
    public static Set<String> readWordList(String url) {
        Set<String> list = new HashSet<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(url), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] ss = line.split("\t");
                list.add(ss[1]); // 从第二列开始，是所需数据
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
