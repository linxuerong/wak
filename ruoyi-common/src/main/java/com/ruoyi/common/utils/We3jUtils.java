package com.ruoyi.common.utils;

import com.google.gson.Gson;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class We3jUtils {

//    public static String node = "https://rinkeby.infura.io/v3/7d00bf84530c4264969a4f0f231de8b6";
    public static String node = "https://mainnet.infura.io/v3/6ddc27676466483b8d5b7e63691ac8e3";
    Web3j web3j;
    Credentials credentials;
    {
        web3j = Web3j.build(new HttpService(node));
        credentials = Credentials.create("ec17ee80ddcc15d13e4f21023fddef8c459cd534ef365037e86cb55333f0ad86");
    }

    public static final String usdtcontractAddress = "0xdAC17F958D2ee523a2206206994597C13D831ec7";
    public static final String contractAddress = "0x95Ff08306774f9C6c8a2aaED92dfe4Ab13C86574";

    public static void main(String[] args) throws Exception {
/*        We3jTest2 we3jTest2 = new We3jTest2();
//        String str = we3jTest2.balanceOf(contractAddress,"0xfEbe55439ce97A18Fb40e8e9F07da7F0b64fd252");
        String str = we3jTest2.allowance("0xdac17f958d2ee523a2206206994597c13d831ec7","0xfEbe55439ce97A18Fb40e8e9F07da7F0b64fd252");
        System.out.println(str);*/

        We3jUtils we3jTest2 = new We3jUtils();
        BigDecimal res = we3jTest2.getAllowance("0x0B0ffF45aF88Cf764192363724C8E011A8729D62", "0xCb042e87cF117f33feDa394CbDb09373D3D54dA5");
        res = res.divide(new BigDecimal(1000000));
        System.out.println(res);


//        we3jTest2.getTransaction();
        Uint256 balanceRes = we3jTest2.balanceOf2(usdtcontractAddress,"0xAcE52aa8b67eD260306572F997fBA22bd5b1d4F5");
//        balanceRes = balanceRes.substring(2);
        System.out.println("余额="+new BigDecimal(balanceRes.getValue()).divide(new BigDecimal(1000000)));
//        returnValue = returnValue.substring(2);

//        Web3j web3j = Web3j.build(new HttpService(node));
//
//        Function function = new Function(
//                "allowance",
//                Arrays.asList(new Address("0xAcE52aa8b67eD260306572F997fBA22bd5b1d4F5"),new Address("0x95Ff08306774f9C6c8a2aaED92dfe4Ab13C86574")),  // Solidity Types in smart contract functions
//                Arrays.asList(new TypeReference<Type>() {
//                }));
//
//        String encodedFunction = FunctionEncoder.encode(function);
//        org.web3j.protocol.core.methods.response.EthCall response = web3j.ethCall(
//                        org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction("0xfEbe55439ce97A18Fb40e8e9F07da7F0b64fd252", "0x95Ff08306774f9C6c8a2aaED92dfe4Ab13C86574", encodedFunction),
//                        DefaultBlockParameterName.LATEST)
//                .sendAsync().get();
//
//        String returnValue = response.getValue(); //返回16进制余额
//        returnValue = returnValue.substring(2);
//        BigInteger balance = new BigInteger(returnValue, 16);
//        System.out.println(balance);

    }

//    public BigInteger getAllowance(String fromAddr) throws Exception {
//        Web3j web3j = Web3j.build(new HttpService(node));
//
//        Function function = new Function(
//                "allowance",
//                Arrays.asList(new Address(fromAddr),new Address("0xAcE52aa8b67eD260306572F997fBA22bd5b1d4F5")),  // Solidity Types in smart contract functions
//                Arrays.asList(new TypeReference<Type>() {
//                }));
//
//        String encodedFunction = FunctionEncoder.encode(function);
//        EthCall response = web3j.ethCall(
//                        Transaction.createEthCallTransaction(fromAddr, "0xAcE52aa8b67eD260306572F997fBA22bd5b1d4F5", encodedFunction),
//                        DefaultBlockParameterName.LATEST)
//                .sendAsync().get();
//
//        String returnValue = response.getValue(); //返回16进制余额
//        returnValue = returnValue.substring(2);
//        BigInteger balance = new BigInteger(returnValue, 16);
//        return balance;
//    }

    public void getTransaction(){
        //合约
        String txid="0x29d96b351be4ab1c29912a1c26c1c8f9205fc35fb9ea2395c53c5c2e1884c421";
        //eth
        String txid2="0xb22eda6328392b3f749befc210de81cf704459c538953aae751287a91a913770";
        EthTransaction tx = EthUtils.getTransaction(web3j, txid2);
        Gson gson = new Gson();
        System.out.println("查询交易:"+ gson.toJson(tx));
    }


    /**
     * 获取某个代币的余额
     *
     * @param contractAddress 代币合约地址
     * @param address         查询用户地址
     * @return 余额：单位ether
     * @throws Exception 与节点交互失败
     */
    public String balanceOf(String contractAddress, String address) throws Exception {
        List input = Arrays.asList(new Address(address));
        List output = Arrays.asList(new TypeReference<Uint256>() {
        });
        List<Type> result = readContract(contractAddress, "balanceOf", input, output);
        Uint256 balance = (Uint256) result.get(0);
        return Convert.fromWei(balance.getValue().toString(), Convert.Unit.ETHER).toString();
    }

    /**
     * 获取某个代币的余额
     *
     * @param contractAddress 代币合约地址
     * @param address         查询用户地址
     * @return 余额：单位ether
     * @throws Exception 与节点交互失败
     */
    public Uint256 balanceOf2(String contractAddress, String address) throws Exception {
        List input = Arrays.asList(new Address(address));
        List output = Arrays.asList(new TypeReference<Uint256>() {
        });
        List<Type> result = readContract(contractAddress, "balanceOf", input, output);
        Uint256 balance = (Uint256) result.get(0);
        return balance;
    }

    /**
     * 获取usdt授权额度
     *
     * @param contractAddress 代币合约地址
     * @param address         查询用户地址
     * @return 余额：单位ether
     * @throws Exception 与节点交互失败
     */
    public String allowance(String contractAddress, String address) throws Exception {
        List input = Arrays.asList(new Address(address));
        List output = Arrays.asList(new TypeReference<Uint256>() {
        });
        // 生成需要调用函数的data
        Function function = new Function("allowance", input, output);
        String data = FunctionEncoder.encode(function);
        // 组建请求的参数   调用者地址(可以为空)，合约地址、参数
        EthCall response = web3j.ethCall(
                        Transaction.createEthCallTransaction(address, contractAddress, data),
                        DefaultBlockParameterName.LATEST)
                .send();
        // 解析返回结果
        List<Type> result = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        Uint256 balance = (Uint256) result.get(0);
        return Convert.fromWei(balance.getValue().toString(), Convert.Unit.ETHER).toString();
    }

    /**
     * 读取合约状态
     *
     * @param contractAddress 合约地址
     * @param functionName    合约函数名称
     * @param input           输入参数
     * @param output          返回变量类型
     * @return 合约函数返回值
     * @throws Exception 与节点交互失败
     */
    public List<Type> readContract(String contractAddress, String functionName, List<Type> input, List<TypeReference<?>> output) throws Exception {
        // 生成需要调用函数的data
        Function function = new Function(functionName, input, output);
        String data = FunctionEncoder.encode(function);
        // 组建请求的参数   调用者地址(可以为空)，合约地址、参数
        EthCall response = web3j.ethCall(
                        Transaction.createEthCallTransaction(null, contractAddress, data),
                        DefaultBlockParameterName.LATEST)
                .send();
        // 解析返回结果
        return FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
    }

    public void test() throws Exception {
        Function function = new Function(
                "balanceOf",
                Collections.emptyList(),
                Arrays.asList(new TypeReference<Utf8String>(){}));

        String encodedFunction = FunctionEncoder.encode(function);
        EthCall response = web3j.ethCall(
                        Transaction.createEthCallTransaction(null, contractAddress, encodedFunction),
                        DefaultBlockParameterName.LATEST)
                .sendAsync().get();

        List<Type> results = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        Utf8String preValue = (Utf8String)results.get(0);
        System.out.println(preValue.getValue());

        Web3j web3 = Web3j.build(new HttpService("https://mainnet.infura.io/v3/6ddc27676466483b8d5b7e63691ac8e3"));

        Credentials credentials = Credentials.create("填入自己账户的私钥");

        // 加载已经部署在链上的合约

    }


    /**
     * 调用合约的只读方法，无需gas
     * @throws Exception
     */
    public void getName() throws Exception {
        Function function = new Function(
                "getName",
                Collections.emptyList(),
                Arrays.asList(new TypeReference<Utf8String>(){}));

        String encodedFunction = FunctionEncoder.encode(function);
        EthCall response = web3j.ethCall(
                        Transaction.createEthCallTransaction(null, contractAddress, encodedFunction),
                        DefaultBlockParameterName.LATEST)
                .sendAsync().get();

        List<Type> results = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        Utf8String preValue = (Utf8String)results.get(0);
        System.out.println(preValue.getValue());
    }

    /**
     * 需要支付gas的方法
     * @throws Exception
     */
    public void setName() throws Exception {
        Function function = new Function(
                "setName",
                Arrays.asList(new Utf8String("Tom")),
                Collections.emptyList());
        BigInteger nonce = getNonce(credentials.getAddress());
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger gasLimit = new BigInteger("300000");
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, DefaultGasProvider.GAS_PRICE,gasLimit, contractAddress, encodedFunction);

        org.web3j.protocol.core.methods.response.EthSendTransaction response =
                web3j.ethSendRawTransaction(Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction, credentials)))
                        .sendAsync()
                        .get();

        String transactionHash = response.getTransactionHash();
        System.out.println(transactionHash);
    }

    /**
     * 需要支付gas和value的合约方法调用
     * @throws Exception
     */
    public void payETH() throws Exception {
        BigInteger nonce = getNonce(credentials.getAddress());
        Function function = new Function("payETH",
                Collections.EMPTY_LIST,
                Collections.EMPTY_LIST);

        String functionEncode = FunctionEncoder.encode(function);
        BigInteger value = new BigInteger("200");
        // 与不需要支付的value的方法调用，差别就在于多传一个eth数量的value参数
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, DefaultGasProvider.GAS_PRICE,DefaultGasProvider.GAS_LIMIT, contractAddress, value,functionEncode);
        org.web3j.protocol.core.methods.response.EthSendTransaction response =
                web3j.ethSendRawTransaction(Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction, credentials)))
                        .sendAsync()
                        .get();
        String transactionHash = response.getTransactionHash();
        System.out.println(transactionHash);
    }


    private BigInteger getNonce(String address) throws Exception {
        EthGetTransactionCount ethGetTransactionCount =
                web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST)
                        .sendAsync()
                        .get();
        return ethGetTransactionCount.getTransactionCount();
    }


    /**
     * 获取用户的授权额度
     *
     * @param owner   授权人地址
     * @param spender 被授权人地址
     * @return 授权额度
     * @throws Exception 与节点交互失败
     */
    public BigDecimal getAllowance(String owner, String spender) throws Exception {
        Web3j web3j = Web3j.build(new HttpService(node));

        Function function = new Function(
                "allowance",
                Arrays.asList(new Address(owner), new Address(spender)),  // Solidity Types in smart contract functions
                Arrays.asList(new TypeReference<Uint256>() {
                }));

        String encodedFunction = FunctionEncoder.encode(function);
        EthCall response = web3j.ethCall(
                    Transaction.createEthCallTransaction(owner, usdtcontractAddress, encodedFunction),
                    DefaultBlockParameterName.LATEST)
            .sendAsync().get();

        String returnValue = response.getValue(); //返回16进制余额
        returnValue = returnValue.substring(2);
        BigInteger balance = new BigInteger(returnValue, 16);
        return new BigDecimal(balance);
    }


    /**
     * 调用合约的transfer方法
     *
     * @param fromAddr 调用者地址
     * @param toAddr   接收者地址
     * @param amount   转账金额
     * @return 交易哈希
     * @throws Exception 与节点交互失败
     */
    public String transform(String fromAddr, String toAddr, BigDecimal amount) throws Exception {
        Function function = new Function(
                "transfer",
                Arrays.asList(new Address(toAddr), new Uint256(amount.multiply(new BigDecimal("1000000")).toBigInteger())),  // Solidity Types in smart contract functions
                Collections.emptyList());
        BigInteger nonce = getNonce(fromAddr);
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger gasLimit = new BigInteger("300000");
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, DefaultGasProvider.GAS_PRICE, gasLimit, contractAddress, encodedFunction);

        org.web3j.protocol.core.methods.response.EthSendTransaction response =
                web3j.ethSendRawTransaction(Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction, credentials)))
                    .sendAsync()
                    .get();

        String transactionHash = response.getTransactionHash();
        return transactionHash;
    }


}


