package SensoryModel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.multipart.MultipartFile;

public class FileTrans {
	public static enum PathName {
		FilePath, QrPath
	};

	private static String PathName = "";
	private static String TranSupSQL = "";

	private static byte[] HashProcess(String HashString) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] byts;
		messageDigest.update(HashString.getBytes("UTF-8"));
		byts = messageDigest.digest();
		return byts;
	}

	public static String FileProcess(MultipartFile file, MultipartFile Qr, String SenSoryId, String Path,
			String UpdateSQL, PathName pathName, String NDate) throws IOException, NoSuchAlgorithmException {

		switch (pathName) {

		case FilePath:
			File FilPatchCheck = new File(Path); //判斷路徑有無存在
			if (!FilPatchCheck.exists()) {
				FilPatchCheck.mkdir();
			}
			String HasString = SenSoryId + NDate;  //hash檔名避免重複
			String FileName = HashProcess(HasString) + file.getOriginalFilename();
			File Filepath = new File(Path + File.separator + FileName); //建立檔案名稱

			file.transferTo(Filepath);  //儲存檔案
            return String.format(UpdateSQL, "FileUrl", FileName,SenSoryId);  //回傳UpdatSQL字串

		case QrPath:
			File QrPatchCheck = new File(Path);
			if (!QrPatchCheck.exists()) {
				QrPatchCheck.mkdir();
			}
			String QrString = SenSoryId + NDate;
			String QrName = HashProcess(QrString) + Qr.getOriginalFilename();
			File Qrpath = new File(Path + File.separator + QrName);
			Qr.transferTo(Qrpath);
            return String.format(UpdateSQL, "QrcodeUrl", QrName,SenSoryId);

		}
		return null;
	}
}
