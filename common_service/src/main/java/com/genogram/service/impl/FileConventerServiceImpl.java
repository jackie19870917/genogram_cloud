package com.genogram.service.impl;

import com.genogram.conventer.*;
import com.genogram.entity.FileModel;
import com.genogram.service.FileConventerService;
import com.genogram.unit.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by chicheng on 2017/12/28.
 */
@Component
public class FileConventerServiceImpl implements FileConventerService {
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

    @Autowired
    private CompressedFileConventer compressedFileConventer;

    @Autowired
    private ImageFileConventer imageFileConventer;

    @Autowired
    private OfficeFileConventer officeFileConventer;

    @Autowired
    private PdfFileConventer pdfFileConventer;

    @Autowired
    private TextFileConventer textFileConventer;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void conventer(FileModel fileModel) {
        if (fileModel.getState() != FileModel.STATE_YXZ) {
            throw new RuntimeException("the file state:" + fileModel.getState()
                    + " is not 2.");
        }
        try {
            String subfix = FileUtil.getFileSufix(fileModel.getOriginalFile());

            if (this.pdfType.contains(subfix.toLowerCase())) {
                this.pdfFileConventer.conventer(fileModel);
            } else if (this.textType.contains(subfix.toLowerCase())) {
                this.textFileConventer.conventer(fileModel);
            } else if (this.imgType.contains(subfix.toLowerCase())) {
                //this.imageFileConventer.conventer(fileModel);
                this.officeFileConventer.conventerToPdf(fileModel);
            } else if (this.compressType.contains(subfix.toLowerCase())) {
                this.compressedFileConventer.conventer(fileModel);
            } else if (this.officeType.contains(subfix.toLowerCase())) {
                /*if("xlsx".equals(subfix.toLowerCase()) || "xls".equals(subfix.toLowerCase())
                        || "pptx".equals(subfix.toLowerCase()) || "ppt".equals(subfix.toLowerCase())) {
                    this.officeFileConventer.conventerToHtml(fileModel);
                }else {*/
                this.officeFileConventer.conventerToPdf(fileModel);
            }
            // }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("不支持该类型文件的转换");
            throw new RuntimeException(e);
        }
    }

}
