package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import net.sf.json.JSONObject;

public class BlockChainServe {
	String walletFilePath = "C:/Users/loveaoe33/AppData/Local/Ethereum/node3/keystore";
	String password = "123456";
	private Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8084"));
	long chainId = 15;
	java.math.BigInteger gasLimit = new java.math.BigInteger("4700000");
	// 載入憑證
	public Credentials Load_Wallet(String FileName) throws IOException, CipherException { 
		System.out.println("FileName: " + FileName);
		String Wallet_Name = walletFilePath + "/" + FileName;
		Credentials credentials = WalletUtils.loadCredentials(password, Wallet_Name);
		System.out.println("Address: " + credentials.getAddress());
		return credentials;
	}

	public String New__Wallet() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchProviderException, CipherException, IOException {
		String walletFileName = WalletUtils.generateNewWalletFile(password, new File(walletFilePath), false);
		Credentials credentials_newFile = WalletUtils.loadCredentials(password, walletFilePath + "/" + walletFileName);
		String Return_Message = String.format("私:%s公:%s地址%s", credentials_newFile.getEcKeyPair().getPrivateKey(),
				credentials_newFile.getEcKeyPair().getPublicKey(), credentials_newFile.getAddress());
		System.out.println(walletFilePath + walletFileName);
		return Return_Message;
	}
	// 確認錢包是否正確內用
	public boolean Check_Wallet_Bool(String Wallet_Address) throws InterruptedException, ExecutionException { 
		try {

			EthGetBalance balace = web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			String Wei = (balace.getBalance()).toString();
//    	    String Eth=Wei.substring(0,19);
			BigDecimal From_ether = Convert.fromWei(Wei, Convert.Unit.ETHER);
			System.out.println("轉帳帳戶Wei:" + Wei);
			System.out.println("轉帳帳戶乙太:" + From_ether);
			return true;

		} catch (Exception e) {
			return false;
		}

	}
	// 確認錢包外用
	public String Check_Wallet(@RequestParam String Wallet_Address) throws InterruptedException, ExecutionException {
		try {

			EthGetBalance balace = web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			String Wei = (balace.getBalance()).toString();
//    	    String Eth=Wei.substring(0,19);
			BigDecimal From_ether = Convert.fromWei(Wei, Convert.Unit.ETHER);
			String Return_Message = String.format("Wei單位%s:,乙太單位:%s", Wei, From_ether.toString());
			return Return_Message;

		} catch (Exception e) {
			return "找無此錢包";
		}

	}
	// 調閱最後一筆紀錄
	public String View_Last_Brock() { 
		try {
			EthBlock.Block latestBlock = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true).send()
					.getBlock();
			if (latestBlock != null) {
				System.out.println("Block number: " + latestBlock.getNumber());
				System.out.println("Block hash: " + latestBlock.getHash());
				System.out.println("Transactions: " + latestBlock.getTransactions().size());

				// 输出最后一个区块的交易信息

			}
		} catch (Exception e) {
			return "查看錯誤";
		}
		return "調閱成功";
	}
	// 調閱所有有交易紀錄
	@GetMapping("BlockChain/View_Array_Block") 
	public ArrayList<String> View_Array_Block() throws IOException {
		String Return_Message = "";
		JSONObject jsonObject = new JSONObject();
		ArrayList<String> Block_Date = new ArrayList<String>();

		java.math.BigInteger startBlock = java.math.BigInteger.valueOf(0); // 起始区块号
		java.math.BigInteger endBlock = web3j.ethBlockNumber().send().getBlockNumber();
		for (java.math.BigInteger i = startBlock; i.compareTo(endBlock) <= 0; i = i.add(java.math.BigInteger.ONE)) {
			EthBlock.Block blocks = web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), true).send()
					.getBlock();
			if (blocks != null && blocks.getTransactions().size() > 0) {
				System.out.println("Block Number: " + blocks.getNumber());
				System.out.println("Transaction size: " + blocks.getTransactions().size());
				System.out.println("Transaction hash: " + blocks.getTransactions().toString());
				System.out.println("block hash: " + blocks.getHash());

				for (EthBlock.TransactionResult txResult : blocks.getTransactions()) {
					EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txResult.get();

//                    String transactionHash = tx.getHash();
//                    String from = tx.getFrom();
//                    String to = tx.getTo();
//                    java.math.BigInteger value = tx.getValue();
//                    java.math.BigInteger gasPrice = tx.getGasPrice();
//                    java.math.BigInteger gasLimit = tx.getGas();

					jsonObject.put("Transaction", tx.getHash());
					jsonObject.put("From", tx.getFrom());
					jsonObject.put("To", tx.getTo());
					jsonObject.put("Value", tx.getValue());
					jsonObject.put("Gas", tx.getGasPrice());
					jsonObject.put("Limit", tx.getGas());
					Block_Date.add(jsonObject.toString());

				}
				;

			}
			;

		}
		;

		return Block_Date;
	}
	// 調閱交易紀錄
	public String View_Transaction_Hash(@RequestParam String Hash_Code) { 
		try {
			JSONObject jsonObject = new JSONObject();

			Optional<org.web3j.protocol.core.methods.response.Transaction> transaction = web3j
					.ethGetTransactionByHash(Hash_Code).send().getTransaction();
			if (transaction != null) {
				jsonObject.put("From", transaction.get().getFrom());
				jsonObject.put("To", transaction.get().getTo());
				jsonObject.put("Value", transaction.get().getValue());
				jsonObject.put("Gas Price", transaction.get().getGasPrice());
				jsonObject.put("Gas Limit", transaction.get().getGas());
//	            	
//	                System.out.println("From: " + transaction.get().getFrom());
//	                System.out.println("To: " + transaction.get().getTo());
//	                System.out.println("Value: " + transaction.get().getValue());
//	                System.out.println("Gas Price: " + transaction.get().getGasPrice());
//	                System.out.println("Gas Limit: " + transaction.get().getGas());
				// 其他交易信息的输出
				return "調閱成功:" + jsonObject.toString();
			} else {
				return "查無資料";
			}
		} catch (IOException e) {
			return "查尋錯誤";
		}
	}

	// 交易主檔
	public String TransFer_ETH(@RequestParam String Wallet_Address)
			throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException,
			CipherException, IOException, InterruptedException, ExecutionException {
		File directory = new File(walletFilePath);
		File[] files = directory.listFiles();
		Credentials credentials;
		String Return_Message;
		if (files != null && files.length >= 2) {
			File secondFile = files[1];
			credentials = Load_Wallet(secondFile.getName());
			Return_Message = String.format("私:%s公:%s地址%s", credentials.getEcKeyPair().getPrivateKey(),
					credentials.getEcKeyPair().getPublicKey(), credentials.getAddress());
			boolean wallet_check = Check_Wallet_Bool(Wallet_Address);
			if (wallet_check) {
				return TransFer(credentials, Wallet_Address);
			} else {
				return "錢包有問題或不存在";

			}
		} else {
			return "請先建立錢包";

		}

	}

	// 交易副檔
	public String TransFer(Credentials credentials, String to_Address) throws InterruptedException, IOException,
			CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		BigDecimal amount = BigDecimal.valueOf(1.0); // Trans_Money

		try {
			// 檢索帳戶的 nonce 值
			EthGetTransactionCount ethGetTransactionCount = web3j
					.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync()
					.get();
			java.math.BigInteger nonce = ethGetTransactionCount.getTransactionCount();

			// 交易創立
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce,
					Convert.toWei("18000", Convert.Unit.WEI).toBigInteger(), gasLimit, to_Address,
					Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger());

			// 簽署交易
			byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
			String hexValue = Numeric.toHexString(signMessage);

			// 發送交易
			String transactionHash = web3j.ethSendRawTransaction(hexValue).send().getTransactionHash();
			System.out.println("Transaction hash: " + transactionHash);
			return "轉帳提交成功";
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			return "轉帳提交失敗";
		}
	}

	// 列出節點帳號
	public ArrayList<String> Print_Wallet() throws IOException {
		ArrayList<String> account = (ArrayList<String>) web3j.ethAccounts().send().getAccounts();
		return account;
	}

}
