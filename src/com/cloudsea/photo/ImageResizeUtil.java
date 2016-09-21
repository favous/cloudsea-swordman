package com.cloudsea.photo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片缩放
 */
public class ImageResizeUtil {

    /**
     * 
     * 〈以宽为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newWidth:图的缩放宽
     * @param newFilePath:缩放图输出路径
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void resizeByWithToFile(InputStream oldIn, int newWidth, String newFilePath)
            throws InterruptedException, FileNotFoundException, IOException {
        // 加载图片
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newHeight = oldHeight * newWidth / oldWidth;

        OutputStream out = createOutputStream(newFilePath);
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        out.close();
    }

    /**
     * 
     * 〈以宽为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newWidth:图的缩放宽
     * @return
     * @throws IOException
     */
    public static InputStream resizeByWith(InputStream oldIn, int newWidth) throws IOException {
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newHeight = oldHeight * newWidth / oldWidth;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        InputStream newIn = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return newIn;
    }

    /**
     * 
     * 〈以高为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newHeight:图的缩放高
     * @param newFilePath:缩放图输出路径
     * 
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void resizeByHeightToFile(InputStream oldIn, int newHeight, String newFilePath)
            throws InterruptedException, FileNotFoundException, IOException {
        // 加载图片
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newWidth = oldWidth * newHeight / oldHeight;

        OutputStream out = createOutputStream(newFilePath);
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        out.close();
    }

    /**
     * 
     * 〈以高为准，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newHeight:图的缩放高
     * @return
     * @throws IOException
     */
    public static InputStream resizeByHeight(InputStream oldIn, int newHeight) throws InterruptedException,
            FileNotFoundException, IOException {
        // 加载图片
        Image oldImage = ImageIO.read(oldIn);
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        int newWidth = oldWidth * newHeight / oldHeight;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        InputStream newIn = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return newIn;
    }

    /**
     * 
     * 〈以宽高大小适应性补边，等比例缩放图片〉
     *
     * @param oldIn:源图片流
     * @param newWidth:缩放图的宽
     * @param newHeight:缩放图的高
     * @param quality:图片质量，取值0到1之间
     * @param newFilePath:缩放图输出路径
     * @return
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static InputStream resize(InputStream oldIn, int newWidth, int newHeight, float quality, OutputStream newOut)
            throws InterruptedException, FileNotFoundException, IOException {

        Image oldImage = ImageIO.read(oldIn);

        // 确定缩略图大小的宽度和高度
        int oldWidth = oldImage.getWidth(null);
        int oldHeight = oldImage.getHeight(null);
        double newRatio = (double) newWidth / (double) newHeight;
        double oldRatio = (double) oldWidth / (double) oldHeight;

        if (newRatio < oldRatio) {
            newHeight = (int) (newWidth / oldRatio);
        } else if (newRatio > oldRatio) {
            newWidth = (int) (newHeight * oldRatio);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        resizeToStream(oldImage, newWidth, newHeight, 1, out);
        InputStream newIn = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return newIn;
    }

    private static void resizeToStream(Image oldImage, int newWidth, int newHeight, float quality, OutputStream out)
            throws ImageFormatException, IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        newImage.getGraphics().drawImage(oldImage, 0, 0, newWidth, newHeight, null);

        // 保存图像
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(newImage);
        quality = Math.max(0, Math.min(quality, 1));
        param.setQuality(quality, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(newImage);
    }

    private static OutputStream createOutputStream(String newFilePath) throws FileNotFoundException {
        File newFile = new File(newFilePath);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        return new BufferedOutputStream(new FileOutputStream(newFile));
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, IOException {
        resizeByWithToFile(new FileInputStream("C:/Users/15041997/Desktop/pic/a.jpg"), 100, "C:/pic/a0.jpg");
        resizeByHeightToFile(new FileInputStream("C:/Users/15041997/Desktop/pic/a.jpg"), 100, "C:/pic/a1.jpg");

    }
}