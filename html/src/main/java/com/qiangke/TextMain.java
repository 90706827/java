package com.qiangke;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangguoq
 * @description
 * @date 2024/9/12 19:29
 **/
public class TextMain {
    public static void main(String[] args) throws IOException {
        String fileName = "临时文件";

        File file = ResourceUtils.getFile("classpath:text.txt");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String content = new String(bytes, StandardCharsets.UTF_8);
        Document document = Jsoup.parse(content);
        Element element = document.body();
        Elements elements = element.getElementsByClass("st_content_txt");
        List<JianDa> list = new ArrayList<>();
        for (Element el : elements) {
            JianDa jianDa = new JianDa();
            Elements titleElements = el.getElementsByClass("st_content_txt_tm");
            Elements imgs = titleElements.get(0).getElementsByTag("img");
            StringBuffer title = new StringBuffer();
            String titleHtml = titleElements.html();
            titleHtml = titleHtml.replaceAll("<(/)?span>|<(/)?em>|<br>.*|\\n", "");
            titleHtml = titleHtml.replaceAll("<[^>]+>", "");
            title.append(hanZiAfter(titleHtml)).append(getImage(imgs));
            jianDa.setTitle(title.toString());
            System.out.println(jianDa.getTitle());
//            System.out.println(titleText.replaceAll("<(/)?span>|<(/)?em>|\\n", ""));

            Elements selectElements = el.getElementsByClass("jxxq_jx_txt");
            jianDa.setText( selectElements.get(2).html().replaceAll("<(/)?span>|<(/)?em>|<br>.*|\\n", ""));

            System.out.println(jianDa.getText());

            jianDa.setDesc(selectElements.get(3).html().replaceAll("<(/)?span>|<(/)?em>|<br>.*|\\n", ""));

            System.out.println(jianDa.getDesc());
            list.add(jianDa);
        }
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName(fileName);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, JianDa.class, list);
        try {
            File subject = new File("/users/jimmy/work/高项/" + fileName + ".xls");
            OutputStream outputStream = Files.newOutputStream(subject.toPath());
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    public static StringBuffer getImage(Elements imgs) {
        StringBuffer imageUrl = new StringBuffer();
        for (Element img : imgs) {
            imageUrl.append("\r\n").append(img.attributes().get("src"));
        }
        return imageUrl;
    }


    public static String select(String text) {
        String text1 = "";
        if (!text.contains(">")) {
            text1 = hanZiAfter(text);

        } else {
            text1 = text.substring(text.lastIndexOf(">") + 1, text.length());
        }
        return hanZiAfter(text1);
    }

    public static String hanZiAfter(String hanzi) {

        String regex = "[\\u4e00-\\u9fa5]";

        if (!hanzi.matches(".*[\\u4E00-\\u9FA5]+.*")) {
            return hanzi;
        }

        int lastIndex = -1;
        for (int i = 0; i < hanzi.length(); i++) {
            if (Character.toString(hanzi.charAt(i)).matches(regex)) {
                lastIndex = i;
            }
        }
        if (lastIndex == (hanzi.length() - 1)) {
            return hanzi;
        } else {
            return hanzi.substring(0, lastIndex + 1);
        }
    }
}
