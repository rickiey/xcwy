package com.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static com.utils.image.readImage2DB;

/**
 *商家上传图片
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/FileUp")
public class FileUp extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = (String) request.getSession().getAttribute("id");
		if (ServletFileUpload.isMultipartContent(request)) {

			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload sfu = new ServletFileUpload(factory);
				sfu.setSizeMax(10 * 1024 * 1024);
				sfu.setHeaderEncoding("utf-8");
				List<FileItem> fileItemList = sfu.parseRequest(request);
				Iterator<FileItem> fileItems = fileItemList.iterator();
				while (fileItems.hasNext()) {
					FileItem fileItem = fileItems.next();
					if (fileItem.isFormField()) {
						String name = fileItem.getFieldName();
						String value = fileItem.getString("utf-8");
						System.out.println(name + " = " + value);
					} else {
						String fileName = fileItem.getName();
						System.out.println("鍘熸枃浠跺悕锛�" + fileName);
						if (fileName.substring(fileName.lastIndexOf('.')).equals(".jpg")
								|| fileName.substring(fileName.lastIndexOf('.')).equals(".png")
								|| fileName.substring(fileName.lastIndexOf('.')).equals(".jpeg")
								|| fileName.substring(fileName.lastIndexOf('.')).equals(".gif")
								|| fileName.substring(fileName.lastIndexOf('.')).equals(".bmp")) {
							File file = new File(request.getServletContext().getRealPath("upload")
									+ "\\" + fileName);
							System.out.println(file.getAbsolutePath());
							fileItem.write(file);
							String path = fileName;
							readImage2DB(id, path);
							request.getRequestDispatcher("/information.jsp").forward(request,
									response);
						}
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}