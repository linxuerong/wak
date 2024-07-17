<template >
    <div>
        <div class="main-content">
            <!-- receive -->
            <div v-if="userData && userData.status == 0 || !Badd">
                <div class="receive al-item">
                    <div style="padding-right: 20px;">
                        <div class="tit"><span class="titBefore"></span> <span>Receive voucher </span>
                        </div>
                        <p style="font-size: 12px;font-weight: 100;color: rgba(0,0,0,.5);">Receive voucher to join the
                            mining
                            pool and
                            start non-destrucive
                            mining</p>
                    </div>
                    <div>
                        <el-button icon="el-icon-plus" @click="submitReceive">Receive</el-button>
                    </div>
                </div>
                <div class="bottomLine"></div>
            </div>
            <!-- receive end -->
            <div class="tit jus-bet">
                <div>
                    <span class="titBefore"></span> <span>Pool Data </span>
                </div>
                <div>
                    <el-button size="mini" type="primary" @click="rechargeDialog">recharge</el-button>
                </div>

            </div>
            <div class="main-content-data al-item jus-arn f-warp">
                <div class="data_item" v-for="(item1, index) in dataList" :key="index">
                    <div class="data_item_text">
<!--                        <span>{{item1.num}}</span>-->
                        <numberScorll :value="item1.num" :time="1" />
                    </div>
                    <div class="al-item" style="margin-bottom: 14px;justify-content: center;">
                        <img :src="item1.url" alt="">
                        <span class="icontext"> {{ item1.name }}</span>
                    </div>
                </div>

            </div>
            <div style="height: 20px;"></div>
            <div class="data_list">
                <div class="tit"><span class="titBefore"></span> <span>User Output</span> </div>
                <div class="jus-bet data_list_name">
                    <span>Address</span>
                    <span>Quantity</span>
                </div>
                <vueSeamless :data="listData" :class-option="optionSingleHeight" class="seamless-warp">
                    <div v-for="(item3, index) in listData" :key="item3[0]" class="listData_item">
                        <span class="title" style="color: #468fc6;">{{ item3[0] }}</span>
                        <span class="title">{{ item3[1] }}</span>
                    </div>
                </vueSeamless>
            </div>
            <div class="help_center">
                <h4 style="font-weight: 700;">Help Center</h4>
                <p>Hope it helps you</p>

                <div class="help_main">
                    <el-collapse accordion @change="changeCollapse">
                        <el-collapse-item v-for="(collapsedata, index) in collapsedataList" :key="index" :name="index">
                            <template slot="title">
                                <div class="collapseF">
                                    {{ collapsedata.title }}
                                    <!-- <i class="el-icon-arrow-right collapseI"></i> -->
                                </div>
                            </template>
                            <p>
                                {{ collapsedata.content }}
                            </p>
                        </el-collapse-item>


                    </el-collapse>

                </div>
                <!-- logo -->
                <div>
                    <h4 style="font-weight: 700;">Regulator</h4>
                    <p style="text-align: center;">We have a secure audit report</p>
                </div>
                <div class="logo al-item jus-evn">
                    <img src="@/assets/l1.png" alt="">
                    <img src="@/assets/l2.png" alt="">
                    <img src="@/assets/l3.png" alt="">
                </div>
                <div>
                    <h4 style="font-weight: 700;">Partner</h4>
                    <p style="text-align: center;">Our business partner</p>
                </div>
                <div><img src="@/assets/l4.png" alt=""></div>
                <div class="al-item jus-evn">
                    <img src="@/assets/l5.png" alt="" class="pad-10">
                    <img src="@/assets/l6.png" alt="" class="pad-10">
                </div>
                <div class="al-item jus-evn">
                    <img src="@/assets/l7.png" alt="" class="pad-10">
                    <img src="@/assets/l8.png" alt="" class="pad-10">
                    <img src="@/assets/l9.png" alt="" class="pad-10">
                </div>
                <div class="al-item jus-evn">
                    <img src="@/assets/l10.png" alt="" class="pad-10">
                    <img src="@/assets/l11.png" alt="" class="pad-10">
                </div>
                <div style="height: 20px;"></div>
            </div>
        </div>
        <!-- 充值地址对话框 -->
        <div class="rechargeDialog">
            <el-dialog title="Recharge Address" :visible.sync="dialogVisibleRecharge" width="90%">
                <div v-for="(item, index) in reAddress" :key="index">
                    <div>{{ item.name }}:</div>
                    <div class="al-item jus-bet copyCss">
                        <div style="color:#aca5a4;width: 80%;font-size: 12px; ">
                            {{ item.add }}

                        </div>
                        <div style="color:#1873b9;text-align:center;width: 20%;" @click=" copyMethods(item.add)">
                            <el-button type="primary" size="mini">Copy</el-button>
                        </div>
                    </div>
                </div>


            </el-dialog>
        </div>
    </div>
</template>
<script>
import numberScorll from "@/components/countToScorll.vue";
import { indexGetrechargeaddress ,getPlatformInfo} from '@/api/apiTrc.js'
import vueSeamless from 'vue-seamless-scroll'
import tools from '@/utils/randomNumber.js'
export default {
    props: {
        userData: {
            default () {
                return {}
            }
        },
        Badd: {
            default () {
                return ''
            }
        }

    },
    components: {
        vueSeamless,
        numberScorll,

    },
    data () {
        return {
            // 充值地址
            reAddress: [],
            dialogVisibleRecharge: false,
            direction: 'rtl',
            listData: [
                [
                    "0x69cf09d9********b321253263",
                    "0.731358"
                ], [
                    "0xa23f2b5b********2843a54f02",
                    "0.377717"
                ], [
                    "0x930d8c16********36923ce07a",
                    "1.116198"
                ], [
                    "0x930d1116********36923ce07a",
                    "1.116198"
                ], [
                    "0x93035876********36923ce07a",
                    "1.116198"
                ]
            ],

            dataList: [

            ],
            dataList1: [

                {
                    num: '1,501,423',
                    url: require('@/assets/t3.svg'),
                    name: 'Total Output(ETH)'
                },
                {
                    num: '3,945,182,672',
                    url: require('@/assets/t4.svg'),
                    name: 'User Revenue(USDT)'
                },
            ],
            collapsedataList: [
                {
                    title: ' How do i need to join ?',
                    content: 'To participate in non-destructive and non-guaranteed liquidity mining, you need to pay an ETH minersfee to receive the voucher, and an ETH wallet address only needs to be claimed once.Automatically openmining permissions after success'
                },
                {
                    title: 'How do i withdraw money ?',
                    content: ' You can convert the coins produced daily into USDT, and then initiate a withdrawal. The USDT withdrawalwill be automatically sent to the wallet address you added to the node, and other addresses are not supported.'
                }, {
                    title: ' How to calculate income ?',
                    content: ' When you join successfully, the smart contract starts to calculate your address through the node andstarts to calculate the income. 10-49, 999USDT, 1.3 per day; 50,000-99, 999 USDT, 1.5 per day; 100,000USDT - 499, 999, 1.7 per day; 500,000 USDT and above, 2.0 per day.Please note that the dividend token is Ethereum.'
                },
            ],
            current: 2,
            // tipMsg: 'Please connect to the wallet'
            tipMsg: 6,
            downKey: 1,
            currentCollapse: 999999,
            sysType: 'trc'

        }
    },
    computed: {
        optionSingleHeight () {
            return {
                singleHeight: 32,
                step: 2
            }
        }
    },
    mounted () {
        this.getRechargeAddress();
        this.getPlatformInfo();
        if (window.ethereum) {
            this.sysType = 'erc'
            this.getRandomNumEth()

        } else {
            this.sysType = 'trc'
            this.getRandomNum()
        }

    },
    methods: {
        async getRechargeAddress () {
            let res = await indexGetrechargeaddress({
                t: this.sysType
            })
            if (res.code == 1) {
                // this.reAddress = res.data
                Object.keys(res.data).forEach((item) => {
                    let obj = {
                        name: item,
                        add: res.data[item]
                    }
                    this.reAddress.push(obj)
                })
            }
        },
      getPlatformInfo () {
        getPlatformInfo().then((res) => {
          if (res.code == 1) {
            let data = new Object();
            data.num = res.data.participants;
            data.url = require('@/assets/t1.svg');
            data.name = "Participants";
            this.dataList.push(data);

            let data2 = new Object();
            data2.num = res.data.validnodes;
            data2.url = require('@/assets/t2.svg');
            data2.name = "Valid Nodes";
            this.dataList.push(data2);

            let data3 = new Object();
            data3.num = res.data.outputeth;
            data3.url = require('@/assets/t3.svg');
            data3.name = "Total Output(ETH)";
            this.dataList.push(data3);

            let data4 = new Object();
            data4.num = res.data.revenuelusdt;
            data4.url = require('@/assets/t4.svg');
            data4.name = "User Revenue(USDT)";
            this.dataList.push(data4);

            console.log("datalist",this.dataList);
          }
        }).catch((err) => {
          this.$message.error(err)
        })

      },
        changeCollapse (e) {
            console.log(e, 'aaa');
            this.currentCollapse = e
        },
        // 生成随机数
        getRandomNum () {
            this.listData = []
            for (let i = 0; i < 20; i++) {
                let arr = []
                let str = 'T' + tools.randomWord(false, 13) + '*******' + tools.randomWord(false, 13)
                let str1 = Math.floor(Math.random() * 2) + '.' + tools.randomNumberPrice(true, false, 6)
                arr[0] = str
                arr[1] = str1
                this.listData.push(arr)
            }

        },
        // 生成随机数ETH
        getRandomNumEth () {
            this.listData = []
            for (let i = 0; i < 20; i++) {
                let num = tools.randomWord(false, 8)
                let num1 = tools.randomWord(false, 10)
                let arr = []
                let str = '0x' + num + '********' + num1
                let str1 = tools.randomNumberPrice(true, false, 6)
                arr[0] = str
                arr[1] = '0.' + str1
                this.listData.push(arr)
            }

        },
        submitReceive () {
            this.$emit('receive')
        },
        // 弹窗处理充值地址
        rechargeDialog () {
            this.dialogVisibleRecharge = true
        },
        // 复制地址
        copyMethods (val) {
            // 后台接口返回的链接地址
            let inputNode = document.createElement('input')  // 创建input
            inputNode.value = val // 赋值给 input 值
            document.body.appendChild(inputNode) // 插入进去
            inputNode.select() // 选择对象
            document.execCommand('Copy') // 原生调用执行浏览器复制命令
            inputNode.className = 'oInput'
            inputNode.style.display = 'none' // 隐藏
            this.$message.success('Copy Successful', 1000)
        }
    },
}
</script>
<style lang="scss" scoped>
.main-content {
    padding: 0 24px;

    .titBefore {
        display: inline-block;
        margin-right: 5px;
        height: 20px;
        width: 4px;
        border-radius: 6px;
        background: linear-gradient(141.98deg, #133a72 29.79%, #1873b9 67.18%, #2dcdcd 105.91%);
    }

    .tit {
        display: flex;
        align-items: center;
        font-weight: 700;
        line-height: 20px;
    }

    .main-content-data {
        background-color: #e3edf4;
        border-radius: 10px;
        padding: 16px;
        margin-top: 20px;

        .data_item {
            text-align: center;
            width: 50%;

            img {
                width: 14px;
                height: 14px;
            }

            .data_item_text {
                font-weight: 700;
                color: #469494;
                line-height: 24px;
                padding-bottom: 8px;
            }

            .icontext {
                color: rgba(40, 43, 58, .3);
                font-size: 14px;
                margin-left: 6px;
                white-space: nowrap;
                text-align: center;
            }
        }
    }
}

.fw {
    font-weight: 600;
}



.seamless-warp {
    height: 300px;
    overflow: hidden;
}

.listData_item {
    font-size: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 8px 0;
}

.collapseF {
    position: relative;
    width: 100%;
    text-align: left;
}

.collapseI {
    position: absolute;
    right: 10px;
    top: 16px;
    color: #adb1b4;
    font-size: 18px;

}

.rotateCollapes {
    transition: all 0.5s ease;
    transform: rotate(270deg);
}

.rotateCollapesback {
    transition: all 0.5s ease;
    transform: rotate(90deg);
}

.bottomLine {
    border-bottom: 1px solid #bbbcc0;
    width: 140px;
    height: 20px;
    margin: 10px auto;
}

.data_list {

    .data_list_name {
        margin: 10px 0;

        span {
            color: #bfc7d0;
            font-size: 12px;
        }
    }
}

.help_center {
    text-align: center;

    h4 {
        margin: 6px 0;
    }

    p {
        margin: 6px 0;
        font-size: 12px;
        color: #7d7d7d;
    }


}

.copyCss {
    background-color: #dff1f3;
    padding: 6px;
    border-radius: 6px;
    margin: 6px 0;
    font-size: 14px;
}
</style>
<style lang="scss">
.rechargeDialog {


    .el-dialog__header {
        padding: 16px 10px 10px 10px;
        text-align: center;
    }
}
</style>
