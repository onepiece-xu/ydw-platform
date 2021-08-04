package com.ydw.recharge.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRBarCodeUtil {
	
	private static Logger logger = LoggerFactory.getLogger(QRBarCodeUtil.class);

    public static class LogoConfig {
        public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;    // logo默认边框颜色
        public static final int DEFAULT_BORDER = 0; // logo默认边框宽度
        public static final int DEFAULT_LOGOPART = 5;   // logo大小默觉得照片的1/5
        private final int border = DEFAULT_BORDER;  // 默认边框宽度
        private final Color borderColor;    // 边框颜色
        private final int logoPart; //  边框外围宽度

        /**
         * 二维码无參构造函数 默认设置Logo图片底色白色宽度2
         */
        public LogoConfig() {
            this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
        }

        /**
         * 二维码有參构造函数
         *
         * @param borderColor 边框颜色
         * @param logoPart 边框宽度
         */
        public LogoConfig(Color borderColor, int logoPart) {
            // 设置边框
            this.borderColor = borderColor;
            // 设置边框宽度
            this.logoPart = logoPart;
        }

        /**
         * 获取边框颜色
         *
         * @return 获取边框颜色
         */
        public Color getBorderColor() {
            return borderColor;
        }

        /**
         * 获取边框
         *
         * @return 获取边框
         */
        public int getBorder() {
            return border;
        }

        /**
         * 外围边宽
         *
         * @return 外围边宽
         */
        public int getLogoPart() {
            return logoPart;
        }
    }
 
    /**
     * CODE_WIDTH：二维码宽度，单位像素
     * CODE_HEIGHT：二维码高度，单位像素
     * FRONT_COLOR：二维码前景色，0x000000 表示黑色
     * BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
     * 演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
     */
    private static final int CODE_WIDTH = 400;
    private static final int CODE_HEIGHT = 400;
    private static final int FRONT_COLOR = 0x000000;
    private static final int BACKGROUND_COLOR = 0xFFFFFF;


    /**
     * 生成二维码 并 输出到输出流————通常用于输出到网页上进行显示
     * 输出到网页与输出到磁盘上的文件中，区别在于最后一句 ImageIO.write
     * write(RenderedImage im,String formatName,File output)：写到文件中
     * write(RenderedImage im,String formatName,OutputStream output)：输出到输出流中
     *
     * @param codeContent  ：二维码内容
     * @param outputStream ：输出流，比如 HttpServletResponse 的 getOutputStream
     */
    public static void createCodeToOutputStream(String codeContent, File logo, OutputStream outputStream) {
        BufferedImage bufferedImage = createCode(codeContent, logo);
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCodeToOutputStream(String codeContent, OutputStream outputStream) {
        BufferedImage bufferedImage = createCode(codeContent);
        try {
            ImageIO.write(bufferedImage, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码 并 输出到文件
     * 输出到网页与输出到磁盘上的文件中，区别在于最后一句 ImageIO.write
     * write(RenderedImage im,String formatName,File output)：写到文件中
     * write(RenderedImage im,String formatName,OutputStream output)：输出到输出流中
     *
     * @param codeContent  ：二维码内容
     * @param file ：输出流，比如 HttpServletResponse 的 getOutputStream
     */
    public static void createCodeToFile(String codeContent, File logo, File file) {
        BufferedImage bufferedImage = createCode(codeContent, logo);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCodeToFile(String codeContent, File file) {
        BufferedImage bufferedImage = createCode(codeContent);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage createCode(String codeContent, File logo) {
        BufferedImage bufferedImage = createCode(codeContent);
        addLogoToQRCode(bufferedImage,logo,new LogoConfig());
        return bufferedImage;
    }

    public static BufferedImage createCode(String codeContent) {
        BufferedImage bufferedImage = null;
        try {
            /** 参数检验*/
            if (codeContent == null || "".equals(codeContent.trim())) {
                System.out.println("二维码内容为空，不进行操作...");
            }else{
                codeContent = codeContent.trim();

                /**com.google.zxing.EncodeHintType：编码提示类型,枚举类型
                 * EncodeHintType.CHARACTER_SET：设置字符编码类型
                 * EncodeHintType.ERROR_CORRECTION：设置误差校正
                 *      ErrorCorrectionLevel：误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
                 *      不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
                 * EncodeHintType.MARGIN：设置二维码边距，单位像素，值越小，二维码距离四周越近
                 * */
                Map<EncodeHintType, Object> hints = new HashMap<>();
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
                hints.put(EncodeHintType.MARGIN, 1);

                /**
                 * MultiFormatWriter:多格式写入，这是一个工厂类，里面重载了两个 encode 方法，用于写入条形码或二维码
                 *      encode(String contents,BarcodeFormat format,int width, int height,Map<EncodeHintType,?> hints)
                 *      contents:条形码/二维码内容
                 *      format：编码类型，如 条形码，二维码 等
                 *      width：码的宽度
                 *      height：码的高度
                 *      hints：码内容的编码类型
                 * BarcodeFormat：枚举该程序包已知的条形码格式，即创建何种码，如 1 维的条形码，2 维的二维码 等
                 * BitMatrix：位(比特)矩阵或叫2D矩阵，也就是需要的二维码
                 */
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = multiFormatWriter.encode(codeContent, BarcodeFormat.QR_CODE, CODE_WIDTH, CODE_HEIGHT, hints);

                /**java.awt.image.BufferedImage：具有图像数据的可访问缓冲图像，实现了 RenderedImage 接口
                 * BitMatrix 的 get(int x, int y) 获取比特矩阵内容，指定位置有值，则返回true，将其设置为前景色，否则设置为背景色
                 * BufferedImage 的 setRGB(int x, int y, int rgb) 方法设置图像像素
                 *      x：像素位置的横坐标，即列
                 *      y：像素位置的纵坐标，即行
                 *      rgb：像素的值，采用 16 进制,如 0xFFFFFF 白色
                 */
                bufferedImage = new BufferedImage(CODE_WIDTH, CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
                for (int x = 0; x < CODE_WIDTH; x++) {
                    for (int y = 0; y < CODE_HEIGHT; y++) {
                        bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? FRONT_COLOR : BACKGROUND_COLOR);
                    }
                }
                logger.info("二维码图片生成到输出流成功...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    /**
     * 二维码图片加入Logo
     *
     * @param bim 图片流
     * @param logoPic Logo图片物理位置
     * @param logoConfig Logo图片设置參数
     * @throws Exception 异常上抛
     */
    private static void addLogoToQRCode(BufferedImage bim, File logoPic, LogoConfig logoConfig){
        try {
            // 对象流传输
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();

            // 读取Logo图片
            BufferedImage logo = ImageIO.read(logoPic);

            // 设置logo的大小,本人设置为二维码图片的20%,由于过大会盖掉二维码
            int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth(null), heightLogo = logo
                    .getHeight(null) > image.getHeight() * 2 / 10 ?
                    (image.getHeight() * 2 / 10) : logo.getWidth(null);

            // 计算图片放置位置
            // logo放在中心
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            // 開始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(logoConfig.getBorderColor());
            g.drawRect(x, y, widthLogo, heightLogo);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));// 1.0f为透明度 ，值从0-1.0，依次变得不透明
            g.dispose();
            logo.flush();
            image.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
