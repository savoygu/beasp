package org.huel.beasp.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	private static Properties properties = new Properties();
	static{
		try {
			properties.load(FileUtils.class.getClassLoader().getResourceAsStream("arrowuploadfiletype.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证上传文件是否属于图片/flash动画/word文件/exe文件/pdf文件/txt文件/xls文件/ppt文件
	 * @return
	 */
	public static boolean validateFileType(File file, String contentType, String fileName){
		if(file != null){
			/* 获取文件扩展名 */
			String ext = getExt(fileName);
			List<String> arrowType = new ArrayList<String>();
			for(Object key : properties.keySet()){
				/* 一个扩展名对应多个类型 */
				String value = properties.getProperty((String)key);
				String[] values = value.split(",");
				for(String v : values){
					arrowType.add(v);
				}
			}
			return arrowType.contains(contentType) && properties.keySet().contains(ext);
		}
		return false;
	}
	
	/**
	 * 验证文件类型是否属于图片格式
	 * @return
	 */
	public static boolean validateImageFileType(File file, String contentType, String fileName){
		if(file != null){
			List<String> arrowType = Arrays.asList("image/bmp", "image/png", "image/gif", "image/jpg", "image/jpeg", "image/pjpeg");
			String ext = getExt(fileName);
			List<String> arrowExtension = Arrays.asList("gif", "jpg", "bmp", "png");
			return arrowType.contains(contentType) && arrowExtension.contains(ext);
		}
		return false;
	}
	
	/**
	 * 获取文件后缀
	 * @param fileName 文件名
	 * @return
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}
	
	/**
	 * 保存文件
	 * @param savedir 存放目录
	 * @param fileName 文件名称
	 * @param uploadfile 上传的文件
	 * @return 保存文件
	 * @throws Exception 
	 */
	public static File saveFile(File saveDir, String fileName, MultipartFile uploadFile) throws Exception{
		if(!saveDir.exists()) saveDir.mkdirs();//如果目录不存在就创建
		File file = new File(saveDir, fileName);
		uploadFile.transferTo(file);
		return file;
	}
	
	/**
	 * 验证文件类型是否属于图片格式(基于SpringMVC)
	 * @return
	 */
	public static boolean validateImageFileType(MultipartFile file, String contentType, String fileName){
		if(file != null){
			List<String> arrowType = Arrays.asList("image/bmp", "image/png", "image/gif", "image/jpg", "image/jpeg", "image/pjpeg");
			String ext = getExt(fileName);
			List<String> arrowExtension = Arrays.asList("gif", "jpg", "bmp", "png");
			return arrowType.contains(contentType) && arrowExtension.contains(ext);
		}
		return false;
	}
	
	/**
	 * 保存头像（基于SpringMVC）
	 * @param photoName
	 * @param id
	 * @param fileName
	 * @param request
	 * @throws Exception 
	 */
	public static void savePhotoImageFile(MultipartFile uploadFile, Integer id,
			String fileName, HttpServletRequest request) throws Exception {
		String ext = getExt(fileName);//扩展名
		String pathDir = "/resources/images/user/" + id + "/prototype";//构建文件保存目录
		//得到图片保存的目录的真实路径
		String realPathDir = request.getSession().getServletContext().getRealPath(pathDir);
		System.out.println(realPathDir);
		File saveDir = new File(realPathDir);
		File file = saveFile(saveDir, fileName, uploadFile);
		
		//文件等宽度40
		String pathDir40 = "/resources/images/user/" + id + "/40x";//140宽度的图片保存目录
		String realPathDir40 = request.getSession().getServletContext().getRealPath(pathDir40);
		File saveDir140 = new File(realPathDir40);
		if(!saveDir140.exists()) saveDir140.mkdirs();
		File file140 = new File(realPathDir40, fileName);
		ImageSizer.resize(file, file140, 40, ext);
	}
	
	/**
	 * 文件上传(基于SpringMVC)
	 * @param uploadFile
	 * @param categoryId
	 * @param id
	 * @param fileName
	 * @param request
	 * @throws Exception 
	 */
	public static void saveImageFile(MultipartFile uploadFile, Integer categoryId, Integer id, String fileName, HttpServletRequest request) throws Exception {
		String ext = getExt(fileName);
		String pathDir = "/resources/images/book/" + categoryId + "/" + id + "/prototype";//构建文件保存的目录
		//得到图片保存的目录的真实路径
		String realPathDir = request.getSession().getServletContext().getRealPath(pathDir);
		System.out.println(realPathDir);
		File saveDir = new File(realPathDir);
		File file = saveFile(saveDir, fileName, uploadFile);
		
		//文件等宽度140
		String pathDir140 = "/resources/images/book/" + categoryId + "/" + id + "/140x";//140宽度的图片保存目录
		String realPathDir140 = request.getSession().getServletContext().getRealPath(pathDir140);
		File saveDir140 = new File(realPathDir140);
		if(!saveDir140.exists()) saveDir140.mkdirs();
		File file140 = new File(realPathDir140, fileName);
		ImageSizer.resize(file, file140, 140, ext);
	}
	
	/**
	 * 保存文件
	 * @param savedir 存放目录
	 * @param fileName 文件名称
	 * @param uploadfile 上传的文件
	 * @return 保存文件
	 * @throws Exception 
	 */
	public static File saveFile(File saveDir, String fileName, File uploadFile) throws Exception{
		if(!saveDir.exists()) saveDir.mkdirs();//如果目录不存在就创建
		File file = new File(saveDir, fileName);
		FileOutputStream out = new FileOutputStream(file);
		FileInputStream in = new FileInputStream(uploadFile);
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = in.read(buffer)) != -1){
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();
		return file;
	}
	
	/**
	 * 文件上传
	 * @param uploadFile
	 * @param categoryId
	 * @param id
	 * @param fileName
	 * @param request
	 * @throws Exception 
	 */
	public static void saveImageFile(File uploadFile, Integer categoryId, Integer id, String fileName, HttpServletRequest request) throws Exception {
		String ext = getExt(fileName);
		String pathDir = "/resources/images/book/" + categoryId + "/" + id + "/prototype";//构建文件保存的目录
		//得到图片保存的目录的真实路径
		String realPathDir = request.getSession().getServletContext().getRealPath(pathDir);
		System.out.println(realPathDir);
		File saveDir = new File(realPathDir);
		File file = saveFile(saveDir, fileName, uploadFile);
		
		//文件等宽度140
		String pathDir140 = "/resources/images/book/" + categoryId + "/" + id + "/140x";//140宽度的图片保存目录
		String realPathDir140 = request.getSession().getServletContext().getRealPath(pathDir140);
		File saveDir140 = new File(realPathDir140);
		if(!saveDir140.exists()) saveDir140.mkdirs();
		File file140 = new File(realPathDir140, fileName);
		ImageSizer.resize(file, file140, 140, ext);
	}
	
	
	/**
	 * Struts文件上传
	 * @param uploadFile
	 * @param productCateId
	 * @param productId
	 * @param fileName
	 * @throws Exception
	 */
	/*public static void saveProductImageFile(File uploadFile, Integer productCateId, Integer productId, String fileName) throws Exception{
		String ext = getExt(fileName);
		String pathDir = "/images/product/" + productCateId + "/" + productId + "/prototype";//构建文件保存的目录
		//得到图片保存的目录的真实路径
		String realPathDir = ServletActionContext.getServletContext().getRealPath(pathDir);
		System.out.println(realPathDir);
		File saveDir = new File(realPathDir);
		File file = saveFile(saveDir, fileName, uploadFile);
		
		String pathDir140 = "/images/product/" + productCateId + "/" + productId + "/140x";//140宽度的图片保存目录
		String realPathDir140 = ServletActionContext.getServletContext().getRealPath(pathDir140);
		File saveDir140 = new File(realPathDir140);
		if(!saveDir140.exists()) saveDir140.mkdirs();
		File file140 = new File(realPathDir140, fileName);
		ImageSizer.resize(file, file140, 140, ext);
	}*/
	
	/**
	 * 删除文件，可以使单个文件或文件夹
	 * @param fileName 待删除的的文件名
	 * @return 文件删除成功返回 true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if(!file.exists()) {
			System.out.println("删除文件失败："+fileName+"，文件不存在");
			return false;
		} else {
			if(file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}
	
	/**
	 * 删除单个文件
	 * @param fileName 被删除的文件的文件名
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file  = new File(fileName);
		if(file.isFile() && file.exists()) {
			file.delete();
			System.out.println("删除单个文件："+fileName+",成功!");
			return true;
		} else {
			System.out.println("删除单个文件："+fileName+",失败!");
			return true;
		}
	}
	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * @param dir 被删除目录的文件路径
	 * @return
	 */
	public static boolean deleteDirectory(String dir) {
		//如果dir不已文件分隔符结尾，自动添加文件分隔符
		if(!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		//如果dir对应的文件不存在，或者不是一个目录，则退出
		if(!dirFile.exists() || !dirFile.isDirectory()) {
			System.out.println("删除目录失败："+dir+",目录不存在！");
			return false;
		}
		boolean flag = true;
		//删除文件夹下的所有文件（包括子目录）
		File[] files = dirFile.listFiles();
		for(int i=0; i<files.length; i++) {
			//删除子文件
			if(files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if(!flag) {
					break;
				}
			} else {//删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if(!flag) {
					break;
				}
			}
		}
		if(!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		
		//删除当前目录
		if(dirFile.delete()) {
			System.out.println("删除目录："+dir+"，成功！");
			return true;
		} else {
			System.out.println("删除目录："+dir+"，失败！");
			return false;
		}
	}
	
	/**
	 * 复制单个文件
	 * @param oldPath 原文件路径
	 * @param newPath 复制后路径
	 */
	public static void copyFile(String oldPath, String newPath) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			File oldFile = new File(oldPath);
			if(oldFile.exists()) {//文件存在时
				bis = new BufferedInputStream(new FileInputStream(oldPath));//读入原文件
				bos = new BufferedOutputStream(new FileOutputStream(newPath));
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len=bis.read(buffer))!=-1) {
					bos.write(buffer, 0, len);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 复制整个文件夹内容
	 * @param oldPath 原文件路径
	 * @param newPath 复制后路径
	 */
	public static void copyFolder(String oldPath, String newPath) {
		(new File(newPath)).mkdirs();//如果文件夹不存在，则建立新文件夹
		File oldFiles = new File(oldPath);
		String[] file = oldFiles.list();
		File temp = null;
		for(int i=0; i<file.length; i++) {
			if(oldPath.equals(File.separator)) {
				temp = new File(oldPath + file[i]);
			} else {
				temp = new File(oldPath + File.separator + file[i]);
			}
			if(temp.isFile()) {
				copyFile(temp.toString(), newPath+"/"+(temp.getName()).toString());
				/*BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					bis = new BufferedInputStream(new FileInputStream(temp));
					bos = new BufferedOutputStream(new FileOutputStream(newPath+"/"+(temp.getName()).toString()));
					byte[] buffer = new byte[1024];
					int len = 0;
					while((len = bis.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						bis.close();
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}*/
			}
			if(temp.isDirectory()) {//如果是子文件夹
				copyFolder(oldPath+"/"+file[i], newPath+"/"+file[i]);
			}
		}
	}
	
	public static void main(String[] args) {
		String oldPath = "G:\\temp";
		String newPath = "F:\\temp";
		//FileUtils.copyFile(oldPath, newPath);
		FileUtils.copyFolder(oldPath, newPath);
		delete(oldPath);
	}
	
}
