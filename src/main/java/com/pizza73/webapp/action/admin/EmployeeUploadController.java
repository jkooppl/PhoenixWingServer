package com.pizza73.webapp.action.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pizza73.service.AsyncProcessorService;
import com.pizza73.webapp.base.model.SuccessMessage;
import com.pizza73.webapp.model.UploadItem;

/**
 * @author chris
 */
@Controller
@RequestMapping("/employeeUpdate.html")
public class EmployeeUploadController
{
    private Logger log = Logger.getLogger(EmployeeUploadController.class);

    private static final String FILE_DIR = "employeeUpload";

    @Autowired
    private AsyncProcessorService processingService;

    @RequestMapping(method = RequestMethod.GET)
    public String getUploadForm(Model model)
    {
        model.addAttribute(new UploadItem());
        return "employeeUploadForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@ModelAttribute("uploadItem") UploadItem uploadItem, BindingResult result,
        HttpServletRequest request)
    {
        if (result.hasErrors())
        {
            for (Object error : result.getAllErrors())
            {
                ObjectError oError = (ObjectError) error;
                System.err.println("Error: " + oError.getCode() + " - " + oError.getDefaultMessage());
            }
            return "employeeUploadForm";
        }
        File file = copyFile(request, uploadItem);
        if (null != file)
        {
            processingService.updateEmployees(file);
        }
        else
        {
            log.warn("Error with upload file.");
            result.addError(new ObjectError("uploadItem",
                "Error writing upload file, make sure it is an .XLS file (not XLSX)"));
            return "employeeUploadForm";
        }

        SuccessMessage message = new SuccessMessage("Employee Update Started");
        message.setMessage("The employee update process has started.  "
            + "You should receive an email within the next hour with the results of the upload");

        request.getSession().setAttribute("MESSAGE", message);
        return "redirect:confirmation.html";
    }

    private File copyFile(HttpServletRequest request, UploadItem uploadItem)
    {
        String prefix = request.getSession(true).getServletContext().getRealPath("/");
        File uploadDir = new File(prefix + FILE_DIR);
        if (!uploadDir.isDirectory())
            uploadDir.mkdir();

        String fileName = uploadItem.getFileData().getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index + 1);
        if (!fileType.equalsIgnoreCase("xls"))
        {
            log.warn("file is not an xls file: " + fileName);
            return null;
        }
        File file = new File(uploadDir, fileName);
        try
        {
            FileCopyUtils.copy(uploadItem.getFileData().getBytes(), file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return file;
    }

}
