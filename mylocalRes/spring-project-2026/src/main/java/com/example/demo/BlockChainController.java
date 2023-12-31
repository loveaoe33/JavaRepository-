package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.test.FixedSecureRandom.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import net.sf.json.JSONObject;
import net.sf.json.groovy.GJson;


@RestController
public class BlockChainController {
	String walletFilePath="C:/Users/loveaoe33/AppData/Local/Ethereum/node5/keystore";   //節點參數要改
    String password = "123456";
    private Web3j web3j=Web3j.build(new HttpService("http://127.0.0.1:8085"));//節點參數要改
    private Web3j web3j_RPC	=Web3j.build(new HttpService("http://127.0.0.1:8085"));//節點參數要改

	long chainId = 15;
	java.math.BigInteger gasLimit = new java.math.BigInteger("4700000");
	java.math.BigInteger gasprice = new java.math.BigInteger("4700");


	
	
	public Credentials Test_Wallet() throws IOException, CipherException {
	    File directory=new File(walletFilePath);
        File[] files = directory.listFiles();
        Credentials credentials;
        String Return_Message;
       if(files!=null  && files.length >=1 ) {
    	   File secondFile=files[0];
          
    	   credentials=Load_Wallet(secondFile.getName());
    	   Return_Message=String.format("私:%s公:%s地址%s",credentials.getEcKeyPair().getPrivateKey(),credentials.getEcKeyPair().getPublicKey(),credentials.getAddress());
    		return credentials;

       }
	return null;
	}
	
	public Credentials Test_Admin_Wallet() throws IOException, CipherException {
	    File directory=new File(walletFilePath);
        File[] files = directory.listFiles();
        Credentials credentials;
        String Return_Message;
       if(files!=null  && files.length >= 0) {
    	   File secondFile=files[0];

    	   credentials=Load_Wallet(secondFile.getName());
    	   Return_Message=String.format("私:%s公:%s地址%s",credentials.getEcKeyPair().getPrivateKey(),credentials.getEcKeyPair().getPublicKey(),credentials.getAddress());
    		return credentials;

       }
	return null;
	}
	
	public Credentials Load_Wallet(String FileName) throws IOException, CipherException {  //載入憑證
        System.out.println("FileName: " + FileName);
        String Wallet_Name = walletFilePath+"/"+FileName;
        Credentials credentials = WalletUtils.loadCredentials(password, Wallet_Name);  //憑證載入
        System.out.println("Address: " + credentials.getAddress());
        return credentials;
	}
	

	
	public void Unlock_Wallet(String Wallet_Address,String password) {
		
        System.out.println("Wallet_Unlock: " + Wallet_Address);

	}
	
	public boolean  Check_Wallet_Bool (String Wallet_Address) throws InterruptedException, ExecutionException {  //確認錢包是否正確
        try {
        	
    		EthGetBalance balace = web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync().get();
    	    String Wei=(balace.getBalance()).toString();
//    	    String Eth=Wei.substring(0,19);
            BigDecimal From_ether = Convert.fromWei(Wei, Convert.Unit.ETHER);
    		System.out.println("轉帳帳戶Wei:"+Wei);
    		System.out.println("轉帳帳戶乙太:"+From_ether);
    		return true;

        }catch(Exception e) {
        	return false;
        }

	}
	
	public String readFileAsString(String filePath) throws IOException {
		Path path=Paths.get(filePath);
		byte[] bytes=Files.readAllBytes(path);
		return new String(bytes,StandardCharsets.UTF_8);
	}
	
	ContractGasProvider customGasProvider = new ContractGasProvider() {
	

		@Override
		public java.math.BigInteger getGasPrice() {
			// TODO Auto-generated method stub
	        return java.math.BigInteger.valueOf(1000L);
		}

		@Override
		public java.math.BigInteger getGasLimit(String contractFunc) {
			// TODO Auto-generated method stub
	        return java.math.BigInteger.valueOf(1000000);
		}

		@Override
		public java.math.BigInteger getGasLimit() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public java.math.BigInteger getGasPrice(String contractFunc) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	@GetMapping("BlockChain/New__Wallet")
	public String New__Wallet() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException {
		String wallet_New_FilePath="C:/Users/loveaoe33/AppData/Local/Ethereum/node5/keystore";

		String walletFileName=WalletUtils.generateNewWalletFile(password,new File(wallet_New_FilePath),false);
   		Credentials credentials_newFile=WalletUtils.loadCredentials(password, wallet_New_FilePath+"/"+walletFileName);
   		String Return_Message=String.format("私:%s公:%s地址%s",credentials_newFile.getEcKeyPair().getPrivateKey(),credentials_newFile.getEcKeyPair().getPublicKey(),credentials_newFile.getAddress());
        System.out.println(walletFilePath+walletFileName);   
        return Return_Message;
	}
	
	
	
	@GetMapping("BlockChain/")
	public String init() throws IOException, InterruptedException, ExecutionException{
		System.out.println("初始化完成");
		this.web3j=Web3j.build(new HttpService("http://127.0.0.1:8084"));
		String Wallet_Address="0x470df28eb826acef5759c22ed78c00ba53e5169a";
		EthGetBalance balace=web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync().get();
	    String no_process=(balace.getBalance()).toString();
	    
	    no_process=no_process.substring(0,19);
		BigDecimal localhostBalance= Convert.fromWei(no_process, Convert.Unit.GWEI);  //wei單位餘額
		BigDecimal ether =Convert.fromWei(no_process, Convert.Unit.ETHER);
		


		return "沒轉換"+ no_process +"本地網路餘額:"+localhostBalance+","+"乙太餘額:"+ether;
	

	}
	
	
	@GetMapping("BlockChain/Check_Wallet")
	public String Check_Wallet(@RequestParam String Wallet_Address) throws InterruptedException, ExecutionException {//確認錢包是否正確
        try {
        	
    		EthGetBalance balace = web3j.ethGetBalance(Wallet_Address, DefaultBlockParameterName.LATEST).sendAsync().get();
    	    String Wei=(balace.getBalance()).toString();
//    	    String Eth=Wei.substring(0,19);
            BigDecimal From_ether = Convert.fromWei(Wei, Convert.Unit.ETHER);
    		String Return_Message=String.format("Wei單位%s:,乙太單位:%s", Wei,From_ether.toString());
    		return Return_Message;

        }catch(Exception e) {
        	return "找無此錢包";
        }

	}
	

	@GetMapping("BlockChain/View_Last_Brock")  //查看最後一筆交易
	public String View_Last_Brock() {
		try {
			EthBlock.Block latestBlock=web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"), true)
                    .send()
                    .getBlock();
			  if(latestBlock!=null) {
	                System.out.println("Block number: " + latestBlock.getNumber());
	                System.out.println("Block hash: " + latestBlock.getHash());
	                System.out.println("Transactions: " + latestBlock.getTransactions().size());

	                // 输出最后一个区块的交易信息

			  }
		}catch(Exception e) {
			return "查看錯誤";
		}
		return "調閱成功";
	}
	
	@GetMapping("BlockChain/View_Array_Block")  //調閱所有交易紀錄
	public ArrayList<String> View_Array_Block() throws IOException {
		String Return_Message="";
	    JSONObject jsonObject = new JSONObject();
		ArrayList<String>Block_Date=new ArrayList<String>();

	    java.math.BigInteger startBlock =   java.math.BigInteger.valueOf(0); // 起始区块号
        java.math.BigInteger endBlock=web3j.ethBlockNumber().send().getBlockNumber();
        for(java.math.BigInteger i=startBlock;i.compareTo(endBlock)<=0;i=i.add(java.math.BigInteger.ONE)) {
            EthBlock.Block blocks=web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(i), true).send().getBlock();
            if(blocks!=null&&blocks.getTransactions().size()>0)
            {
                System.out.println("Block Number: " + blocks.getNumber());
                System.out.println("Transaction size: " + blocks.getTransactions().size());
                System.out.println("Transaction hash: " + blocks.getTransactions().toString());
                System.out.println("block hash: " + blocks.getHash());
                
                for(EthBlock.TransactionResult txResult :blocks.getTransactions()) {
                    EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txResult.get();

//                    String transactionHash = tx.getHash();
//                    String from = tx.getFrom();
//                    String to = tx.getTo();
//                    java.math.BigInteger value = tx.getValue();
//                    java.math.BigInteger gasPrice = tx.getGasPrice();
//                    java.math.BigInteger gasLimit = tx.getGas();
                    
                    jsonObject.put("Transaction",  tx.getHash());
                    jsonObject.put("From", tx.getFrom());
                    jsonObject.put("To", tx.getTo());
                    jsonObject.put("Value", tx.getValue());
                    jsonObject.put("Gas", tx.getGasPrice());
                    jsonObject.put("Limit",tx.getGas());
                    Block_Date.add(jsonObject.toString());

                   
                };


            };
        	
        };

		return Block_Date;
	}
	@GetMapping("BlockChain/View_Transaction_Hash")  //調閱交易紀錄
    public String View_Transaction_Hash(@RequestParam String Hash_Code) {
	       try {
	   		    JSONObject jsonObject = new JSONObject();

	            Optional<org.web3j.protocol.core.methods.response.Transaction> transaction = web3j.ethGetTransactionByHash(Hash_Code).send().getTransaction();
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
		    		return "調閱成功:"+jsonObject.toString();
	            } else {
		    		return "查無資料";
	            }
	        } catch (IOException e) {
	    		return "查尋錯誤";
	        }
	}
	@GetMapping("BlockChain/TransFer_ETH")   //轉帳
	public String TransFer_ETH(@RequestParam String Wallet_Address) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, IOException, InterruptedException, ExecutionException {
	    File directory=new File(walletFilePath);
        File[] files = directory.listFiles();
        Credentials credentials;
        String Return_Message;
       if(files!=null  && files.length >= 2) {
    	   File secondFile=files[1];
    	   credentials=Load_Wallet(secondFile.getName());
    	   Return_Message=String.format("私:%s公:%s地址%s",credentials.getEcKeyPair().getPrivateKey(),credentials.getEcKeyPair().getPublicKey(),credentials.getAddress());
    	   boolean wallet_check=Check_Wallet_Bool(Wallet_Address);
    	   if(wallet_check)
    	   {
    		   return TransFer(credentials,Wallet_Address);
    	   }else
    	   {
    	   		return "錢包有問題或不存在";

    	   }
       }else {
   		return "請先建立錢包";

       }

	}
	@GetMapping("BlockChain/Print_Wallet")   //列出節點帳號
	   public ArrayList<String> Print_Wallet() throws IOException {
		   ArrayList<String> account=(ArrayList<String>) web3j.ethAccounts().send().getAccounts();
		   return account;
	   }
	
	
	@GetMapping("BlockChain/Contract_build")   //列出節點帳號
	 public String Contract_build() throws Exception
	{
		Credentials credentials=Test_Wallet();  //需要公私鑰才能部署合約  先使用測試
		String contractBinaryPath = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.bin";
		String contractABIPath = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.abi";
		// 读取合约字节码文件内容
		String contractBinary = readFileAsString(contractBinaryPath);
		// 读取合约 ABI 文件内容
		String contractABI = readFileAsString(contractABIPath);
        
	      TransactionManager transactionManager = new ClientTransactionManager(
	              web3j, credentials.getAddress());
	      
	      E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract.deploy(
	              web3j,
	              transactionManager,
	              customGasProvider
	          ).sendAsync().get();
	      
	      
	      
		String Contract_Address=contract.getContractAddress();
        Optional<TransactionReceipt> transactionReceipt = contract.getTransactionReceipt();
        String message=String.format("合約地址{},交易收據{}", Contract_Address,transactionReceipt.toString());
		
		return message;
	}
//	   public String Contract_build() throws IOException {
////	       Credentials credentials=Test_Wallet();  //需要公私鑰才能部署合約  先使用測試
////		   String Contract= new String(Files.readAllBytes(Paths.get("path/to/MyContract.sol")));
////		   Admin admin = Admin.build(new HttpService("http://127.0.0.1:8084"));
////           String ContractAbi="";
////           String ContractBin="";
////		   String wallet_Address=credentials.getAddress();
////		   ECKeyPair PrivateKey=credentials.getEcKeyPair().getPrivateKey();
////		   try {
////	            YourSmartContract contract = YourSmartContract.deploy(
////	                    web3j,
////	                    credentials.create(PrivateKey),  // 使用你的私钥进行交易签名
////	                    new DefaultGasProvider(),  // 使用默认的 gas 提供者
////	                    "Constructor Argument 1", "Constructor Argument 2"  // 如果你的合约有构造函数参数，需要提供
////	            ).send();	
////	          String Contract_Address=contract..getContractAddress();
////	          return "合約地址"+Contract_Address;
////			   
////		   }catch(Exception e) {
////			   return "合約部署錯誤"+e.toString();
////		   }
////	    	 return null;
////	     }
	   
	  @GetMapping("BlockChain/Contract_View")
	   public String Contract_View_Detail() {
	    	 return null;
	     }
	   
	   public String Contract_Address() {
	    	 return null;
	     }
	   @GetMapping("BlockChain/Contract_Test")
	   public String Contract_Test() throws Exception {
		   Credentials credentials=Test_Wallet(); 
		// 加载合约 ABI 和地址
		   String contractABI = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.abi";
		   String contractAddress = "0x5021671C815629CaC49a148EcE540F34F119908D"; // 合约地址
		                             
		   E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract.load(contractAddress, web3j, credentials, customGasProvider);

		   // 调用合约的 getValue 方法（示例）
		   java.math.BigInteger value =contract.getData().send();
           
		   
	    	 return value.toString();
	     }
	   
	   @GetMapping("BlockChain/Contract_Test2")
	   public String Contract_Test2() throws Exception {
		   Credentials credentials=Test_Wallet(); 
		// 加载合约 ABI 和地址
		   String contractABI = "C:/Users/loveaoe33/Desktop/blockChain/bin/E_Contract_sol_SimpleContract.abi";
		   String contractAddress = "0x5021671C815629CaC49a148EcE540F34F119908D"; // 合约地址
		   TransactionManager transactionManager = new RawTransactionManager(web3j_RPC, credentials);

		   E_Contract_sol_SimpleContract contract = E_Contract_sol_SimpleContract.load(contractAddress, web3j_RPC, credentials, customGasProvider);

		   
		   // 调用合约的 getValue 方法（示例）
			java.math.BigInteger _setData = new java.math.BigInteger("5");

		   TransactionReceipt value =contract.setData(_setData).send();
           
		   
	    	 return value.toString();
	     }
    
	
	public String TransFer(Credentials credentials,String to_Address) throws InterruptedException, IOException, CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
		BigDecimal amount=BigDecimal.valueOf(1.0);    //Trans_Money
		
		try {
            // 檢索帳戶的 nonce 值
	        EthGetTransactionCount ethGetTransactionCount=web3j.ethGetTransactionCount(
	        		credentials.getAddress(),DefaultBlockParameterName.LATEST).sendAsync().get();
	        java.math.BigInteger nonce=ethGetTransactionCount.getTransactionCount();
	        
	    
	        //交易創立
	        RawTransaction rawTransaction=RawTransaction.createEtherTransaction(
	        		nonce, Convert.toWei("18000",Convert.Unit.WEI).toBigInteger(),gasLimit,to_Address,Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger());

	        //簽署交易
	        	byte[] signMessage=TransactionEncoder.signMessage(rawTransaction,chainId,credentials);
	            String hexValue=Numeric.toHexString(signMessage);
	            
	            //發送交易
	            String transactionHash=web3j.ethSendRawTransaction(hexValue).send().getTransactionHash();
	            System.out.println("Transaction hash: " + transactionHash);
	    		return "轉帳提交成功";
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			return "轉帳提交失敗";
		}	
	}

	   
}
