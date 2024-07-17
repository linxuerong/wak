<template >
    <div class="main">
        <div>
            <img src="@/assets/noWallet.png" alt="" style="width: 234px;">
        </div>
        <h4 style="font-size: 20px;"> Remind </h4>
        <p style="font-weight: 100;">
            Please visit in the wallet You are currently visiting a decentralized website, please run it in the wallet
            application
        </p>
        <div style="margin: 32px 0;">
            <el-button type="primary" round @click="copyMethods">copy Link</el-button>
        </div>
        <!-- <div class="submitBtn">
            <img src="@/assets/noWalletIcon.svg" alt="" style="width: 18px;height: 18px;">
            <span>
                Open In Coinbase
            </span>
        </div> -->
    </div>
</template>
<script>
import Web3 from "web3";
let pageWeb3;
export default {
    data () {
        return {

        }
    },
    mounted () {
        this.linkWallet()
    },
    methods: {
        linkWallet () {
            if (window.tronWeb) {
                this.tronWeb = window.tronWeb;
                this.walletAddress = this.tronWeb.defaultAddress.base58;
                this.$router.push('/')
            } else if (window.ethereum) {
                this.$router.push('/')
            }
        },
        copyMethods () {
            let val = window.location.href // 后台接口返回的链接地址
            let str = val.split('#')[0]
            let str1 = str + '#/'
            let inputNode = document.createElement('input')  // 创建input
            inputNode.value = str1 // 赋值给 input 值
            document.body.appendChild(inputNode) // 插入进去
            inputNode.select() // 选择对象
            document.execCommand('Copy') // 原生调用执行浏览器复制命令
            inputNode.className = 'oInput'
            inputNode.style.display = 'none' // 隐藏
            this.$message.success('Copy Successful', 1000)
        }
    }
}
</script>
<style lang="scss" scoped>
.main {
    background-color: #fbfbfb;
    padding: 0 32px;
    text-align: center;
    margin-top: 25vh;
}

.submitBtn {
    margin: 10px auto;
    width: 180px;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    border-radius: 30px;
    background-color: #fff;
    border: 1px solid #ebedf0;
    padding: 10px 20px;

}
</style>