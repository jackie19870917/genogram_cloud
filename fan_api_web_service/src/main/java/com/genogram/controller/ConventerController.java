package com.genogram.controller;

import com.genogram.model.FileModel;
import com.genogram.service.DownloadNetFileService;
import com.genogram.service.FileConventerService;
import com.genogram.service.FileService;
import com.genogram.util.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chicheng on 2017/12/28.
 */
@Controller
@CrossOrigin(origins = "*")
public class ConventerController {

    /**
     * 读取文件信息 显示家谱信息
     *
     * @Author: yuzhou
     * @Date: 2018-12-29
     * @Time: 15:03
     * @Param:
     * @return:
     * @Description:
     */
    @RequestMapping("/family/preview")
    public void familyPreview(String familyPreview, HttpServletResponse response) throws IOException {
        //File file = new File("E:/Xshell/1"+familyPreview+".html");
        File file = new File("/file_view_temp/Xshell/1" + familyPreview + ".html");

        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        try {
            for (int n; (n = input.read(bytes)) != -1; ) {
                buffer.append(new String(bytes, 0, n, "utf-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(new String(buffer));
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(doc.outerHtml());
        buffer=null;
        printWriter.close();
        input.close();
    }

}


