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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangguoq
 * @description
 * @date 2024/10/17 17:00
 **/
public class LiuMain {
    public static void main(String[] args) throws IOException {

        String fileName = "冲刺题二刷";

        File file = ResourceUtils.getFile("classpath:liuhtml.txt");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String content = new String(bytes, StandardCharsets.UTF_8);
        Document document = Jsoup.parse(content);
        Element element = document.body();
        Elements elements = element.getElementsByClass("question-item");
        List<Subject> list = new ArrayList<>();
        for (Element el : elements) {
            Subject subject = new Subject();
            Elements titleElements = el.getElementsByClass("xe-preview__content");
            String titleHtml = pHandler(titleElements.get(0).html());
            subject.setTitle(titleHtml);
            System.out.println(titleHtml);

            StringBuffer sbXuan = new StringBuffer();
            for (int i = 1; i < titleElements.size() - 1; i++) {
                String xuan = titleElements.get(i).html();
                xuan = abcd(i) + pHandler(xuan);
                sbXuan.append(xuan).append("\r\n");
                System.out.println(xuan);
            }
            subject.setSelected(sbXuan.toString());
            Elements dcEls = el.getElementsByClass("correct-answer-result");

            String answer_right = dcEls.get(0).html();
            subject.setAnswerRight(answer_right);
            String answer_wrong = dcEls.get(1).html();
            subject.setAnswerMe(answer_wrong);
            System.out.println("正确：" + answer_right + " 我的：" + answer_wrong);

            String des = titleElements.get(titleElements.size() - 1).html();
            System.out.println(pHandler(des));
            subject.setText(pHandler(des));
            list.add(subject);
        }
        ExportParams exportParams = new ExportParams();
        exportParams.setSheetName(fileName);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Subject.class, list);
        try {
            File subject = new File("/users/jimmy/work/PMP考试/subject/" + fileName + ".xls");
            OutputStream outputStream = Files.newOutputStream(subject.toPath());
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
    }

    public static String pHandler(String title) {
        title = title.replace("\n", "");
        Pattern pattern = Pattern.compile("<p>(.*?)</p>");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            String extractedText = matcher.group(1);
            return extractedText;
        } else {
            return  "没找到";
        }
    }

    public static String titleHandler(String title) {
        title = title.replaceAll("\n", "");
        Pattern pattern = Pattern.compile("<br>([^<]+)</p>");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            String extractedText = matcher.group(1);
            return extractedText;
        } else {
            return  "没找到";
        }
    }

    public static String abcd(int index) {
        switch (index) {
            case 1:
                return "A、";
            case 2:
                return "B、";
            case 3:
                return "C、";
            case 4:
                return "D、";
            case 5:
                return "E、";
            case 6:
                return "F、";
        }
        return "空";
    }

}
