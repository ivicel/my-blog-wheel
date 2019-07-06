package info.ivicel.blog.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
public class CommonUtil {
    public static String getImageRealName(String originalName) {
        String suffix = originalName.substring(originalName.lastIndexOf("."));
        return UUID.randomUUID().toString().replaceAll("-", "") + suffix;
    }

    public static File getParentPath() throws IOException {
        // 图片存放目录
        String basePath = getBaseUploadPath();
        // 按 年/月 分目录
        String subPath = getDateBasedPath();
        File parentPath = new File(basePath, subPath);
        if (!parentPath.exists() && !parentPath.mkdirs()) {
            throw new IOException("无法创建目录: " + parentPath.getPath());
        }

        return parentPath;
    }

    public static String getDateBasedPath() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("/yyyy/MM");
        return dateFormat.format(new Date());
    }

    public static String getBaseUploadPath() {
        return CommonUtil.class.getResource("/").getPath() + "/uploads";
    }

    public static void createThumbnail(File src, File dest) throws IOException {
        BufferedImage originalImage = ImageIO.read(src);
        Thumbnails.of(originalImage).scale(0.25).toFile(dest);
    }
}
