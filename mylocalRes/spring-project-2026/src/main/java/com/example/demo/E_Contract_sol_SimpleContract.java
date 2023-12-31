package com.example.demo;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.6.0.
 */
public class E_Contract_sol_SimpleContract extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b600c60008190555060cb806100256000396000f30060606040526000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680633bc5de301460465780635b4b73a914606c57600080fd5b3415605057600080fd5b6056608c565b6040518082815260200191505060405180910390f35b3415607657600080fd5b608a60048080359060200190919050506095565b005b60008054905090565b80600081905550505600a165627a7a723058209f11c3d63714a87fb681cb5e9714d6b7ee2ba432744eb671afc0a66dbeda0a3f0029";

    public static final String FUNC_GETDATA = "getData";

    public static final String FUNC_SETDATA = "setData";

    @Deprecated
    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected E_Contract_sol_SimpleContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<BigInteger> getData() {
        final Function function = new Function(FUNC_GETDATA, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setData(BigInteger newData) {
        final Function function = new Function(
                FUNC_SETDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<E_Contract_sol_SimpleContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(E_Contract_sol_SimpleContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static E_Contract_sol_SimpleContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new E_Contract_sol_SimpleContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
