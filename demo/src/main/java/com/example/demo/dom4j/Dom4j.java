package com.example.demo.dom4j;

import org.dom4j.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Dom4j
 * @Description TEST
 * @Author Mr.jimmy
 * @Date 2018/12/15 16:10
 * @Version 1.0
 **/
public class Dom4j {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Map<String,String> jobContext = new HashMap<String,String>();
        StringBuilder req = new StringBuilder();
        req.append("<xml>" )
                .append("<appid>").append("wxd678efh567hg6787").append("</appid>")
                .append("<mch_id>").append("1230000109").append("</mch_id>")
                .append("<device_info>").append("013467007045764").append("</device_info>")
                .append("<nonce_str>").append("5K8264ILTKCH16CQ2502SI8ZNMTM67VS").append("</nonce_str>")
                .append("<sign>").append("C380BEC2BFD727A4B6845133519F3AD6").append("</sign>")
                .append("<sign_type>").append("MD5").append("</sign_type>")
                .append("<body>").append("腾讯充值中心-QQ会员充值").append("</body>")
                .append("<detail>").append("json").append("</detail>")
                .append("<attach>").append("附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用").append("</attach>")
                .append("<out_trade_no>").append("20150806125346").append("</out_trade_no>")
                .append("<fee_type>").append("CNY").append("</fee_type>")
                .append("<total_fee>").append("8800").append("</total_fee>")
                .append("<spbill_create_ip>").append("123.12.12.123").append("</spbill_create_ip>")
                .append("<time_start>").append("20091225091010").append("</time_start>")
                .append("<time_expire>").append("20091227091010").append("</time_expire>")
                .append("<goods_tag>").append("WXG").append("</goods_tag>")
                .append("<notify_url>").append("http://www.weixin.qq.com/wxpay/pay.php").append("</notify_url>")
                .append("<trade_type>").append("NATIVE").append("</trade_type>")
                .append("<product_id>").append("12235413214070356458058").append("</product_id>")
                .append("<limit_pay>").append("no_credit").append("</limit_pay>")
                .append("<openid>").append("oUpF8uMuAJO_M2pxb1Q9zNjWeS6o").append("</openid>")
                .append("<receipt>").append("Y").append("</receipt>")
                .append("<scene_info>").append("123").append("</scene_info>")
                .append("</xml>");

        Document document = DocumentHelper.parseText(req.toString());
        //获取根节点
        Element root = document.getRootElement();
        List<Node> list = root.elements();
        for (Node node : list) {
            jobContext.put(node.getName().trim(), node.getText().trim());
        }
        System.out.println(jobContext.size());
//        // 解析books.xml文件
//        // 创建SAXReader的对象reader
//        SAXReader reader = new SAXReader();
//        try {
//            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
//            Document document = reader.read(new File("src/res/books.xml"));
//            // 通过document对象获取根节点bookstore
//            Element bookStore = document.getRootElement();
//            // 通过element对象的elementIterator方法获取迭代器
//            Iterator it = bookStore.elementIterator();
//            // 遍历迭代器，获取根节点中的信息（书籍）
//            while (it.hasNext()) {
//                System.out.println("=====开始遍历某一本书=====");
//                Element book = (Element) it.next();
//                // 获取book的属性名以及 属性值
//                List<Attribute> bookAttrs = book.attributes();
//                for (Attribute attr : bookAttrs) {
//                    System.out.println("属性名：" + attr.getName() + "--属性值："
//                            + attr.getValue());
//                }
//                Iterator itt = book.elementIterator();
//                while (itt.hasNext()) {
//                    Element bookChild = (Element) itt.next();
//                    System.out.println("节点名：" + bookChild.getName() + "--节点值：" + bookChild.getStringValue());
//                }
//                System.out.println("=====结束遍历某一本书=====");
//            }
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
