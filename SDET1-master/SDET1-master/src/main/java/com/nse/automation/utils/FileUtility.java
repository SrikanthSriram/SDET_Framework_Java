package com.nse.automation.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.nse.automation.config.Constants;
import com.nse.automation.driver.DriverSetUp;
import com.nse.automation.exceptions.FrameworkExceptions;
import com.nse.automation.report.LoggerManager;

public class FileUtility {

	public FileUtility() {
	}

	public static String getProjectRootPath() {
		return System.getProperty("user.dir");
	}

	// Get File Separated Path
	public static String getFileSeparatedPath(String slashedPath) {
		String fullPath = getProjectRootPath() + File.separator + slashedPath;
		return fullPath.replace("/", File.separator);
	}

	// Get File Separated Path
	public static String getFileDownloadPath() {
		String fullPath = getProjectRootPath() + Constants.DOWNLOADDIR;
		return fullPath;
	}

	public static boolean isFileExists(String filePath, String fileName) {
		try {
			File file = new File(filePath + "\\" + fileName);
			return file.exists();
		} catch (Exception e) {
			LoggerManager.error(Constants.FORMATTER + " file not found");
			return false;
		}
	}

	// To edit file

	// Take screenshot of element
	public static void takeScreenshotOfElement(WebElement element, String filePath, String fileName) {
		try {
			File file = new File(filePath + "\\" + fileName);
			boolean isFileExits = isFileExists(filePath, fileName);
			if (isFileExits)
				file.delete();
			Waits.explicitWait(4);
			File screenshot = ((TakesScreenshot) DriverSetUp.getDriver()).getScreenshotAs(OutputType.FILE);
			BufferedImage fullimg = ImageIO.read(screenshot);
			Point point = element.getLocation();
			int elewidth = element.getSize().getWidth();
			int eleheight = element.getSize().getHeight();
			BufferedImage elementScreenshot = fullimg.getSubimage(point.getX(), point.getY(), elewidth, eleheight);
			ImageIO.write(elementScreenshot, "png", screenshot);
			org.apache.commons.io.FileUtils.copyFile(screenshot, file);
			Waits.explicitWait(4);
		} catch (Exception e) {
			LoggerManager.error(
					"Exception occurred in takeScreenshotOfElement method which is present in FileUtility class", e);
		}
	}

	public static void deleteFolder(File filePath) {
		try {
			for (File subFile : filePath.listFiles()) {
				if (subFile.isDirectory()) {
					deleteFolder(subFile);
				} else {
					subFile.delete();
				}
			}
			filePath.delete();
		} catch (Exception e) {
			LoggerManager.error("Floder is not exit.", e);
		}
	}

	public static int searchStringInTextFile(String folderDirectory, String verifyString) {
		int count = 0;
		try {
			FileReader fileIn = new FileReader(folderDirectory);
			BufferedReader br = new BufferedReader(fileIn);
			String line;
			while ((line = br.readLine()) != null) {
				if ((line.contains(verifyString))) {
					count++;
				}
			}
			br.close();
		} catch (Exception e) {
			new FrameworkExceptions(e);
		}
		return count;
	}

	public static boolean renameFileName(String path, String fileOldName, String fileNewName) {
		boolean flag = false;
		Waits.explicitWait(1);
		File oldName = new File(path + "\\" + fileOldName);
		File newName = new File(path + "\\" + fileNewName);
		Waits.explicitWait(1);
		if (oldName.renameTo(newName))
			flag = true;
		else
			flag = false;
		return flag;
	}

	public static boolean isFolderExits(String folderName, String path) {
		try {
			Waits.explicitWait(5);
			String homePath = path + "\\" + folderName;
			Waits.explicitWait(3);
			File fi = new File(homePath);
			if (fi.exists() || fi.isDirectory()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			new FrameworkExceptions(e);
			return false;
		}
	}

	public static long getFolderSize(File folder) {
		try {
			long length = 0;
			File[] files = folder.listFiles();
			int count = files.length;
			for (int i = 0; i < count; i++) {
				if (files[i].isFile())
					length += files[i].length();
				else
					length += getFolderSize(files[i]);
			}
			return length;
		} catch (Exception e) {
			LoggerManager.error("Exception occurred in getFolderSize method which is present in FileUtility class", e);
			return 0;
		}
	}

	/**
	 * @param path
	 * @param fileName
	 * @param SheetName
	 * @return
	 * @throws IOException
	 */
	// To check sheet is exist or not.
	public static boolean isSheetExist(String path, String fileName, String SheetName) throws IOException {
		boolean flag = false;
		String filePath = path + "\\" + fileName;
		File file = new File(filePath);
		FileInputStream fi = new FileInputStream(file);
		try {
			Workbook workbook = WorkbookFactory.create(fi);
			if (workbook.getNumberOfSheets() > 0) {
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
					if (workbook.getSheetName(i).equals(SheetName)) {
						flag = true;
						return flag;
					}
				}
			}
		} catch (IOException e) {
			LoggerManager.info("Sheet does not exist.");
			return flag;
		}
		return flag;
	}

	// Close the opened Excel
	public static void closeTheOpenedExcel() throws FrameworkExceptions {
		try {
			Runtime.getRuntime().exec("cmd /c taskkill /f /im excel.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getRequiredDataFromJSONFile(String path, String fileName, String key)
			throws FileNotFoundException {
		File file = new File(path);
		JSONObject testObject = null;
		File list[] = file.listFiles();
		String value = null;
		for (File fi : list) {
			if (fi.getName().endsWith(".json") && fi.getName().contains(fileName)) {
				path = path + "\\" + fi.getName();
				break;
			}
		}
		FileReader reader = new FileReader(path);
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			testObject = (JSONObject) jsonObject;
			value = (String) testObject.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static List<String> getArrayDataFromJson(String path, String fileName, String key)
			throws FileNotFoundException, IOException,ParseException {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(path + "\\" + fileName + ".json"));
		List<String> list = new ArrayList<String>();
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray arrayData = (JSONArray) jsonObject.get(key);
		Iterator<Object> iterator = arrayData.iterator();
		while (iterator.hasNext()) {
			list.add((String) iterator.next());
		}
		return list;
	}

	/**
	 * To get the Last modified date and Time of the file
	 * 
	 * @param filePath
	 * @param fileName
	 * @return date
	 * @throws IOException
	 */
	public static String getLastmodifiedTime(String filePath, String fileName) throws IOException {
		File file = new File(filePath + "\\" + fileName);
		return new Date(file.lastModified()).toString();
	}

	// To get the file and folders name.
	public static List<String> getFolderAndFileName(String path, String folderOrFile) {
		List<String> filesList = new ArrayList<String>();
		List<String> foldersList = new ArrayList<String>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				filesList.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				foldersList.add(listOfFiles[i].getName());
			}
		}
		if (folderOrFile.equalsIgnoreCase("folder")) {
			return foldersList;
		} else if (folderOrFile.equalsIgnoreCase("file")) {
			return filesList;
		} else
			return null;
	}

	public static String generateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}
}
