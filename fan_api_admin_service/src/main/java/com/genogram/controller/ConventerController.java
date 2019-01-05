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

    @Autowired
    private FileService fileService;

    @Autowired
    private DownloadNetFileService downloadNetFileService;

    @Autowired
    private FileConventerService fileConventerService;

    @Value("${tmp.root}")
    private String rootPath;

    @Value("${text.type}")
    private String textType;

    @Value("${img.type}")
    private String imgType;

    @Value("${office.type}")
    private String officeType;

    @Value("${compress.type}")
    private String compressType;

    @Value("${pdf.type}")
    private String pdfType;

    private Map<String, String> pptMap = new HashMap<>();

    /**
     * 文件转换：1、从url地址下载文件 2、转换文件
     *
     * @param model
     * @param filePath
     * @return String
     * @throws UnsupportedEncodingException
     */
    //@RequestMapping("/fileConventer")
    //@ResponseBody
    public String fileConventer(String filePath, Model model,
                                HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // 先去查询,如果存在,不需要转化文档,为找到有效安全的url编码,所以这里使用循环来判断当前文件是否存在
        FileModel oldFileModel = null;
        List<String> keys = this.fileService.findAllKeys();
        for (String key : keys) {
            FileModel tmp = this.fileService.findFileModelByHashCode(key);
            if (tmp != null && tmp.getOriginal().equals(filePath)) {
                oldFileModel = tmp;
                break;
            }
        }
        // 文件已下载，不需要转换
        if (oldFileModel != null) {
            //String s = previewUrl(oldFileModel, model, request,response);
            //return ResponseUtlis.success(s);
            return previewUrl(oldFileModel, model, request, response);
            //return  previewUrl(oldFileModel, model, request,pc,response);
        } else {
            FileModel fileModel = new FileModel();
            // 文件来源url
            fileModel.setOriginal(filePath);
            // 创建时间,使用毫秒数
            fileModel.setCreateMs(System.currentTimeMillis());
            // 文件有效时间 10分钟
            fileModel.setLimitMs(10 * 60 * 1000);
            // 文件新建 未下载状态
            fileModel.setState(FileModel.STATE_WXZ);
            // 下载文件
            this.downloadNetFileService.download(fileModel);
            // 转换文件
            this.fileConventerService.conventer(fileModel);
            // 文件展现到前端
            if (fileModel.getState() != FileModel.STATE_YZH) {
                throw new RuntimeException("convert fail.");
            }
            return previewUrl(fileModel, model, request, response);
            //String s = previewUrl(fileModel, model, request,response);
            //return ResponseUtlis.success(s);
        }
    }

    /**
     * 获取重定向路径
     *
     * @param fileModel
     * @param model
     * @return String
     * @throws UnsupportedEncodingException
     */
    private String previewUrl(FileModel fileModel, Model model, HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException {

        String fileFullPath = null;

        StringBuffer previewUrl = new StringBuffer();
        previewUrl.append("/viewer/document/");
        // pathId确定预览文件
        previewUrl.append(fileModel.getPathId());
        previewUrl.append(File.separator);

        // 判断转换后的文件是否存在,不存在则跳到error页面
        File file = new File(rootPath + File.separator + fileModel.getPathId()
                + File.separator + "resource" + File.separator + fileModel.getConventedFileName());
        String subfix = FileUtil.getFileSufix(fileModel.getOriginalFile());
        model.addAttribute("pathId", fileModel.getPathId());
        model.addAttribute("fileType", subfix);
        //区分是pc端还是移动端
        //model.addAttribute("pc",pc);
        System.out.println(subfix.toLowerCase());
        if (file.exists()) {
            // 判断文件类型，不同的文件做不同的展示
            if (this.pdfType.contains(subfix.toLowerCase())) {
                //return "html";
                return onlinePreview(fileModel.getPathId(), fileFullPath, response);
            } else if (this.textType.contains(subfix.toLowerCase())) {
                //return "txt";
                return onlinePreview(fileModel.getPathId(), fileFullPath, response);
            } else if (this.imgType.contains(subfix.toLowerCase())) {
                return "picture";
            } else if (this.compressType.contains(subfix.toLowerCase())) {
                model.addAttribute("fileTree", fileModel.getFileTree());
                System.out.println(fileModel.getFileTree());
                return "compress";
            } else if (this.officeType.contains(subfix.toLowerCase())) {
                if ("pptx".equalsIgnoreCase(subfix.toLowerCase()) ||
                        "ppt".equalsIgnoreCase(subfix.toLowerCase())) {
                    List<String> imgFiles = fileService.getImageFilesOfPPT(fileModel.getPathId());
                    String imgPaths = "";
                    for (String s : imgFiles) {
                        imgPaths += (fileModel.getPathId() + "/resource/"
                                + s.substring(s.lastIndexOf("\\"), s.length()) + ",");
                    }
                    model.addAttribute("imgPaths", imgPaths);
                    return "ppt";
                } else {
                    //return "office";
                    return onlinePreview(fileModel.getPathId(), fileFullPath, response);
                }
            }
        } else {
            return "forward:/fileNotSupported";
        }
        return null;
    }

    /**
     * 获取预览文件
     *
     * @param pathId
     * @param response
     * @param fileFullPath 此参数主要针对压缩文件,利用该参数获取解压后的文件
     * @return
     */
    @RequestMapping(value = "/viewer/document", method = RequestMethod.GET)
    @ResponseBody
    public String onlinePreview(String pathId, String fileFullPath, HttpServletResponse response) {
        // 根据pathId获取fileModel
        FileModel fileModel = this.fileService.findFileModelByHashCode(pathId);
        if (fileModel == null) {
            throw new RuntimeException("fileModel 不能为空");
        }
        if (fileModel.getState() != FileModel.STATE_YZH) {
            throw new RuntimeException("convert fail.");
        }

        // 得到转换后的文件地址
        String fileUrl = "";

        if (fileFullPath != null) {
            fileUrl = rootPath + File.separator + fileFullPath;
        } else {
            fileUrl = rootPath + File.separator + fileModel.getPathId() + File.separator + "resource" + File.separator + fileModel.getConventedFileName();
        }
        return pdfToImage(fileUrl, response);
    }


    public String pdfToImage(String pdfurl, HttpServletResponse response) {
        StringBuffer buffer = new StringBuffer();
        FileOutputStream fos;
        PDDocument document;
        File pdfFile;
        int size;
        BufferedImage image;
        FileOutputStream out;
        Long randStr = 0L;
        //PDF转换成HTML保存的文件夹
        //本地
        //String path = "E:/Xshell/1";
        //阿里云
        String path = "/file_view_temp/Xshell/1";

        String string = path.substring(0, path.lastIndexOf("/"));
        //删除文件
        delAllFile(path);
        delAllFile(string);

        File htmlsDir = new File(path);
        if (!htmlsDir.exists()) {
            htmlsDir.mkdirs();
        }
        File htmlDir = new File(path + "/");
        if (!htmlDir.exists()) {
            htmlDir.mkdirs();
        }
        try {
            //遍历处理pdf附件
            randStr = System.currentTimeMillis();
            buffer.append("<!doctype html>\r\n");
            buffer.append("<head>\r\n");
            buffer.append("<meta charset=\"UTF-8\">\r\n");
            buffer.append("<title>家谱文件预览</title>\r\n");
            buffer.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n\r\n");
            buffer.append("</head>\r\n");
            //可以禁止鼠标右键
            //buffer.append("<body oncontextmenu = \"return false\"; style=\"background-color:gray;\">\r\n");
            //可以禁止鼠标右键 键盘ctrl shift alt
            buffer.append("<body onmousemove=/HideMenu()/ oncontextmenu=\"return false\" \n" +
                    "ondragstart=\"return false\" onselectstart =\"return false\" \n" +
                    "onselect=\"document.selection.empty()\" \n" +
                    "oncopy=\"document.selection.empty()\" onbeforecopy=\"return false\" \n" +
                    "onmouseup=\"document.selection.empty()\" style=\"background-color:gray;\">\r\n");
            buffer.append("<style>\r\n");
            // 2代表pc 其它  代表手机
                /*if("2".equals(pc)){
                    buffer.append("img {background-color:#fff; text-align:center;margin-left: auto;display: block;margin-right: auto; width:50%; max-width:100%;margin-top:6px;}\r\n");
                }else {
                buffer.append("img {background-color:#fff; text-align:center; width:100%; max-width:100%;margin-top:6px;}\r\n");
                }*/
            buffer.append("img {background-color:#fff;text-align:center;margin-left: auto;display: block;margin-right: auto;width: 900px;max-width: 90%;margin-top:6px;}\r\n");
            buffer.append("</style>\r\n");
            document = new PDDocument();
            //pdf附件
            pdfFile = new File(pdfurl);
            document = PDDocument.load(pdfFile, (String) null);
            size = document.getNumberOfPages();
            Long start = System.currentTimeMillis(), end = null;
            System.out.println("===>pdf : " + pdfFile.getName() + " , size : " + size);
            PDFRenderer reader = new PDFRenderer(document);
            File file = new File(path + "/" + randStr);
            //如果文件夹不存在
            if (!file.exists()) {
                //创建文件夹
                file.mkdir();
            }

            for (int i = 0; i < size; i++) {
                //image = new PDFRenderer(document).renderImageWithDPI(i,130,ImageType.RGB);
                image = reader.renderImage(i, 1.5f);
                //生成图片,保存位置
                out = new FileOutputStream(path + "/" + randStr + "/image" + "_" + i + ".jpg");
                ImageIO.write(image, "png", out); //使用png的清晰度
                //ImageIO.write(image, "bmp", out); //使用bmp的清晰度
                //将图片路径追加到网页文件里
                //buffer.append("<img src=\"" + path +"/"+ "image" + "_" + i + ".jpg\"/>\r\n");
                buffer.append("<img src=/jpgImg/" + randStr + "/image_" + i + ".jpg>\r\n");
                image = null;
                out.flush();
                out.close();
            }

            reader = null;
            document.close();
            buffer.append("</body>\r\n");
            buffer.append("</html>");
            end = System.currentTimeMillis() - start;
            start = end = null;
            //生成网页文件
            fos = new FileOutputStream(path + randStr + ".html");
            System.out.println(path + randStr + ".html");
            fos.write(buffer.toString().getBytes());
            fos.flush();
            fos.close();

            return "" + randStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除指定文件夹下所有文件
    //param path 文件夹完整绝对路径
    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                //delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

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
    }

}


