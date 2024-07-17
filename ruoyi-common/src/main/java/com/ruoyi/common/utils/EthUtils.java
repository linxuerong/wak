package com.ruoyi.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 以太坊工具类
 *
 * @Autor Tricky
 * @Date 2021-04-01 21:02:11
 */
public class EthUtils {

    private static final Logger logger = LoggerFactory.getLogger(EthUtils.class);

    public static final BigDecimal ETH_DECIMALS = new BigDecimal(1_000_000_000_000_000_000L);

    public static final BigInteger ETH_GAS_LIMIT = new BigInteger("100000");

    public static String node = "https://mainnet.infura.io/v3/6ddc27676466483b8d5b7e63691ac8e3";

    public static final String usdtcontractAddress = "0xdAC17F958D2ee523a2206206994597C13D831ec7";



    /**
     * 获取区块数据
     *
     * @param web3j
     * @param block                  块高
     * @param fullTransactionObjects 是否需要交易数据
     * @return
     */
    public static EthBlock getBlock(Web3j web3j, long block, boolean fullTransactionObjects) {
        try {
            return web3j.ethGetBlockByNumber(new DefaultBlockParameterNumber(block), fullTransactionObjects).send();
        } catch (Throwable t) {
            logger.error(String.format("Get Block Error %d", block), t);
        }
        return null;
    }

    /**
     * 获取当前块高
     *
     * @param web3j
     * @return
     */
    public static long getNowBlockNumber(Web3j web3j) {
        try {
            EthBlockNumber send = web3j.ethBlockNumber().send();
            return send.getBlockNumber().longValue();
        } catch (Throwable t) {
            logger.error("GetBlockNumberError", t);
        }
        return -1;
    }

    /**
     * 发送erc20
     *
     * @param web3j
     * @param contractAddress 合约地址
     * @param privateKey      私钥
     * @param to              收款地址
     * @param value           额度
     * @return
     */
    public static String sendErc20(Web3j web3j, String contractAddress, String privateKey,
                                   String to, BigInteger value, BigInteger gasLimit) {
        String from = getAddressByPrivateKey(privateKey);
        logger.info(String.format("Start:SendErc20 from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
        try {
            //加载转账所需的凭证，用私钥
            Credentials credentials = Credentials.create(privateKey);
            //获取nonce，交易笔数
            BigInteger nonce = getNonce(web3j, from);
            if (nonce == null) {
                logger.error(String.format("END:GetNonceError from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
                return null;
            }
            //gasPrice和gasLimit 都可以手动设置
            BigInteger gasPrice = getGasPrice(web3j);
            if (gasPrice == null) {
                logger.error(String.format("END:GetGasPriceError from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
                return null;
            }
            //BigInteger.valueOf(4300000L) 如果交易失败 很可能是手续费的设置问题
            if (gasLimit==null || new BigDecimal(gasLimit).compareTo(BigDecimal.ZERO)==0){
                gasLimit = BigInteger.valueOf(60000L);
            }
            //ERC20代币合约方法
            Function function = new Function(
                    "transfer",
                    Arrays.asList(new Address(to), new Uint256(value)),
                    Collections.singletonList(new TypeReference<Type>() {
                    }));
            //创建RawTransaction交易对象
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                    contractAddress, encodedFunction);

            //签名Transaction
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //发送交易
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            String hash = ethSendTransaction.getTransactionHash();
            if (hash != null) {
                return hash;
            }
            logger.error("error:"+ ethSendTransaction.getError().getMessage());
            logger.error(String.format("END:HashIsNull from:%s to:%s amount:%s erc20:%s", from, to, value.toString(), contractAddress));
        } catch (Throwable t) {
            logger.error(String.format("发送ERC20失败 from=%s to=%s erc20=%s amount=%s",
                    from, to, contractAddress, value.toString()), t);
        }
        return null;
    }

    /**
     * 列出交易信息
     *
     * @param block  区块高度
     * @param filter 过滤器
     * @return
     */
    public static List<EthBlock.TransactionResult> getTransactions(Web3j web3j, long block, java.util.function.Function<EthBlock.TransactionResult, Boolean> filter) {
        EthBlock send = getBlock(web3j, block, true);
        if (send == null) {
            logger.error(String.format("GetBlockDataError:%d", block));
            return Collections.emptyList();
        }
        List<EthBlock.TransactionResult> transactions = send.getBlock().getTransactions();
        if (filter != null) {
            List<EthBlock.TransactionResult> result = new ArrayList<>();
            for (EthBlock.TransactionResult e : transactions) {
                try {
                    if (filter.apply(e)) {
                        result.add(e);
                    }
                } catch (Throwable t) {
                    logger.error(t.getMessage(), t);
                }
            }
            return result;
        }
        return transactions;

    }

    /**
     * 根据私钥获取地址
     *
     * @param privateKey
     * @return
     */
    public static String getAddressByPrivateKey(String privateKey) {
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        return "0x" + Keys.getAddress(ecKeyPair).toLowerCase();
    }


//    /**
//     * 创建地址
//     *
//     * @return
//     */
//    public static EthAddress createAddress() {
//        try {
//            String seed = UUID.randomUUID().toString();
//            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
//            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
//
//            String sPrivatekeyInHex = privateKeyInDec.toString(16);
//
//            WalletFile aWallet = Wallet.createLight(seed, ecKeyPair);
//            String sAddress = aWallet.getAddress();
//
//            EthAddress address = new EthAddress();
//            address.setAddress("0x" + sAddress);
//            address.setPrivateKey(sPrivatekeyInHex);
//            return address;
//        } catch (Throwable t) {
//            logger.error("创建地址失败", t);
//        }
//        return null;
//    }

    /**
     * 查询地址以太坊数量
     *
     * @param web3j
     * @param address 查询地址
     * @return
     */
    public static BigDecimal balanceOf(Web3j web3j, String address) {
        try {
            EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            BigInteger amount = balance.getBalance();
            if (amount == null || amount.compareTo(BigInteger.ZERO) <= 0) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(amount).divide(ETH_DECIMALS, 18, RoundingMode.FLOOR);
        } catch (Throwable t) {
            logger.error(String.format("获取以太坊数量出错 %s", address), t);
        }
        return BigDecimal.ZERO;
    }


    /**
     * 转换成最小单位 Wei
     *
     * @param ethAmount
     * @return
     */
    public static BigInteger toWei(BigDecimal ethAmount) {
        return ethAmount.multiply(ETH_DECIMALS).toBigInteger();
    }

    /**
     * wei to eth
     *
     * @param wei
     * @return
     */
    public static BigDecimal toEth(BigInteger wei) {
        return new BigDecimal(wei).divide(ETH_DECIMALS, 18, RoundingMode.FLOOR);
    }

    /**
     * 查询erc20的余额
     *
     * @param web3j
     * @param contract 合约地址
     * @param address  查询地址
     * @return
     */
    public static BigInteger balanceOfErc20(Web3j web3j, String contract, String address) {
        try {
            final String DATA_PREFIX = "0x70a08231000000000000000000000000";
            String value = web3j.ethCall(org.web3j.protocol.core.methods.request.Transaction.createEthCallTransaction(address,
                    contract, DATA_PREFIX + address.substring(2)), DefaultBlockParameterName.PENDING).send().getValue();
            if (StringUtils.isBlank(value)) {
                return BigInteger.ZERO;
            }
            return new BigInteger(value.substring(2), 16);
        } catch (Throwable t) {
            logger.error(String.format("查询ERC20失败 contract:%s address:%s", contract, address), t);
        }
        return BigInteger.ZERO;
    }

    /**
     * 获取gas-price
     *
     * @param web3j
     * @return
     */
    public static BigInteger getGasPrice(Web3j web3j) {
        try {
            EthGasPrice ethGasPrice = web3j.ethGasPrice().sendAsync().get();
            if (ethGasPrice == null) {
                logger.error("GetGasPriceError");
                return null;
            }
            return ethGasPrice.getGasPrice();
        } catch (Throwable t) {
            logger.error(t.getMessage(), t);
        }
        return null;
    }

    /**
     * 获取nonce
     *
     * @param web3j
     * @param address
     * @return
     */
    public static BigInteger getNonce(Web3j web3j, String address) {
        try {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
            if (ethGetTransactionCount == null) {
                logger.error("GetNonceError:" + address);
                return null;
            }
            return ethGetTransactionCount.getTransactionCount();
        } catch (Throwable t) {
            logger.error("GetNonceError:" + address);
        }
        return null;
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param wei        wei为单位的数量
     * @param gasPrice   gas-price
     * @param gasLimit   gas-limit
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigInteger wei, BigInteger gasPrice, BigInteger gasLimit) {
        String from = getAddressByPrivateKey(privateKey);
        try {
            //加载转账所需的凭证，用私钥
            Credentials credentials = Credentials.create(privateKey);
            //获取nonce，交易笔数
            BigInteger nonce = getNonce(web3j, from);
            //创建RawTransaction交易对象
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, wei);
            //签名Transaction，这里要对交易做签名
            byte[] signMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signMessage);
            //发送交易
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
            return ethSendTransaction.getTransactionHash();
        } catch (Throwable t) {
            logger.error(String.format("发送ETH失败 from:%s to:%s amount-eth:%s", from, to, toEth(wei).toString()));
        }
        return null;
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param wei        wei为单位的数量
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigInteger wei) {
        return sendEth(web3j, privateKey, to, wei, getGasPrice(web3j), ETH_GAS_LIMIT);
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param eth        wei为单位的数量
     * @param gasPrice   gas-price
     * @param gasLimit   gas-limit
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigDecimal eth, BigInteger gasPrice, BigInteger gasLimit) {
        return sendEth(web3j, privateKey, to, toWei(eth), gasPrice, gasLimit);
    }

    /**
     * 发送以太坊
     *
     * @param web3j
     * @param privateKey 发送者私钥
     * @param to         收款地址
     * @param eth        wei为单位的数量
     * @return
     */
    public static String sendEth(Web3j web3j, String privateKey, String to, BigDecimal eth) {
        return sendEth(web3j, privateKey, to, toWei(eth), getGasPrice(web3j), ETH_GAS_LIMIT);
    }

    /**
     * 根据hash获取交易信息
     * @param web3j
     * @param hash
     * @return
     */
    public static EthTransaction getTransaction(Web3j web3j, String hash) {
        try {
            EthTransaction tx = web3j.ethGetTransactionByHash(hash).send();
            return tx;
        } catch (Throwable t) {
            logger.error("GetTransactionError:" + hash, t);
        }
        return null;
    }

    /**
     * 生成一个普通交易对象
     *
     * @param fromAddress 放款方
     * @param toAddress   收款方
     * @param nonce       交易序号
     * @param gasPrice    gas 价格
     * @param gasLimit    gas 数量
     * @param value       金额
     * @return 交易对象
     */
    public static Transaction makeTransaction(String fromAddress, String toAddress,
                                              BigInteger nonce, BigInteger gasPrice,
                                              BigInteger gasLimit, BigInteger value) {
        Transaction transaction;
        transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);
        return transaction;
    }


    /**
     * 获取普通交易的gas上限
     *
     * @param transaction 交易对象
     * @return gas 上限
     */
    public static BigInteger getTransactionGasLimit(Web3j web3j, Transaction transaction) {
        long blockNo = getNowBlockNumber(web3j);
        EthBlock ethBlock = getBlock(web3j, blockNo, true);
        List<EthBlock.TransactionResult> transactions = ethBlock.getBlock().getTransactions();
        for (EthBlock.TransactionResult transactionResult:
        transactions) {
            System.out.println(transactionResult.toString());
        }
        BigInteger gasLimit = BigInteger.ZERO;
        try {
            EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
            gasLimit = ethEstimateGas.getAmountUsed();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gasLimit;
    }


}


