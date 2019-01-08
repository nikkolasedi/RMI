package com.jcg.csv2excel;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EPowModInterface extends Remote {
	
	public String convertCsvToXls(String xlsFileLocation, String csvFilePath, String csvFilePath1, String sheetName, String sheetName1) throws RemoteException;
	public void uploadFileToServer(byte[] mybyte, String serverpath, int length) throws RemoteException;
}
