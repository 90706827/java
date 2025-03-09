package com.qiangke;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {


    public static void main(String[] args) throws IOException {
        String fileName = "PMP必刷二刷";

        File file = ResourceUtils.getFile("classpath:html.txt");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String content = new String(bytes, StandardCharsets.UTF_8);
        Document document = Jsoup.parse(content);
        Element element = document.body();
        Elements elements = element.getElementsByClass("st_content_txt");
        List<Subject> list = new ArrayList<>();
        for (Element el : elements) {
            Subject subject = new Subject();
            Elements titleElements = el.getElementsByClass("st_content_txt_tm");
            Elements imgs = titleElements.get(0).getElementsByTag("img");
            StringBuffer title = new StringBuffer();
            String titleHtml = titleElements.html();
            titleHtml = titleHtml.replaceAll("<(/)?span>|<(/)?em>|<br>.*|\\n", "");
            titleHtml = titleHtml.replaceAll("<[^>]+>", "");
            title.append(hanZiAfter(titleHtml)).append(getImage(imgs));
            subject.setTitle(title.toString());
            System.out.println(subject.getTitle());
//            System.out.println(titleText.replaceAll("<(/)?span>|<(/)?em>|\\n", ""));

            Elements selectElements = el.getElementsByClass("st_content_txt_xx");
            Elements selects = selectElements.get(0).getElementsByTag("li");
            StringBuffer selectedText = new StringBuffer();
            for (Element select : selects) {
                Elements duo_xuan = select.getElementsByClass("st_xx_txt");
                if (duo_xuan != null && duo_xuan.size() > 0) {
                    String selectText = select(duo_xuan.get(0).html());
                    System.out.println(selectText);
                    selectedText.append(selectText).append("\r\n");
                } else {
                    String selectText = select(select.html());
                    System.out.println(selectText);
                    selectedText.append(selectText).append("\r\n");
                }
            }
            subject.setSelected(selectedText.toString());
            Elements answer_right = el.getElementsByClass("answer_right");
            subject.setAnswerRight(answer_right.get(0).html());
            Elements answer_wrong = el.getElementsByClass("answer_wrong");
            subject.setAnswerMe(answer_wrong.get(0).html());
            System.out.println("正确：" + answer_right.get(0).html() + " 我的：" + answer_wrong.get(0).html());
            Elements text = el.getElementsByClass("jxxq_jx_txt");

            StringBuffer textBuffer = new StringBuffer();
            String testStr = text.get(0).html();
            testStr = testStr.replaceAll("<br>.*|\\n", "");
            testStr = testStr.replaceAll("<[^>]+>", "");
            Elements textImages = text.get(0).getElementsByTag("img");
            textBuffer.append(testStr).append(getImage(textImages));
            subject.setText(textBuffer.toString());
            System.out.println(textBuffer.toString());
            list.add(subject);
        }
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName(fileName);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Subject.class, list);
        try {
            File subject = new File("/users/jimmy/work/pmp/模拟考试/" + fileName + ".xls");
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