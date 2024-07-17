<template>
  <div>
    <div class="home">
      <div class="header">
        <div class="nav jus-bet">
          <img src="@/assets/2.svg" alt="">
          <div class="midImg">
            <img src="@/assets/damm-logo.jpg" alt="" style="width: 114px;height: 24px;">
          </div>
          <div class="">
            <div class="flex al-item addBox">

              <img src="@/assets/1.svg" alt="dAMM Finance">
              <div class="white userAddress">{{ 'Connect Wallet' }}</div>
            </div>
          </div>
        </div>
        <div class="midLog">
          <img src="@/assets/Compound2.jpg" alt="">
        </div>
      </div>
      <div class="main">
        <div class="tab">
          <div class="tab_item" v-for="(item, index) in tabList" :key="index" :class="current == index ? 'tabl' : ''"
            @click="tabLine(index)">
            <div class="topimg"><img :src="item.image" alt=""></div>
            <div class="bomTxt" :class="item.isChecked ? 'fw' : ''">{{ item.text }}</div>
          </div>
        </div>

      </div>
      <div class="main-content">
        <!-- receive -->
        <div class="receive al-item">
          <div style="padding-right: 20px;">
            <div class="tit"><span class="titBefore"></span> <span>Receive voucher</span> </div>
            <p style="font-size: 12px;font-weight: 100;color: rgba(0,0,0,.5);">Receive voucher to join the mining pool and
              start non-destrucive
              mining</p>
          </div>
          <div>
            <el-button icon="el-icon-plus" @click="submitReceive">Receive</el-button>
          </div>
        </div>
        <div class="bottomLine"></div>
        <!-- receive end -->
        <div class="tit"><span class="titBefore"></span> <span>Pool Data</span> </div>
        <div class="main-content-data al-item jus-arn f-warp">
          <div class="data_item" v-for="(item1, index) in dataList" :key="index">
            <div class="data_item_text">
              <numberScorll :value="item1.num" :time="1"></numberScorll>
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

    </div>
    <!-- 客服抽屉 -->
    <div class="kefuBox">
      <el-drawer title="我是标题" :visible.sync="drawer" :direction="direction" size="100%">
        <template slot="title">
          <div style="text-align: center;">Chat</div>
        </template>
        <div style="height: 100vh;">
          <iframe src="https://www.baidu.com/" frameborder="0" width="100%" height="100%"></iframe>
        </div>
      </el-drawer>
    </div>


    <div style="position: fixed;bottom: 20px;right: 10px;" @click="drawer = true">
      <img src="@/assets/kefu.svg" alt="">
    </div>
  </div>
</template>

<script>
// import { test } from '@/api/test.js'
import Web3 from "web3";
let pageWeb3;
import { indexGetaddress } from '@/api/apiTrc.js'
import vueSeamless from 'vue-seamless-scroll'
import down from '@/components/dropDown.vue'
import { Message } from 'element-ui'
import tools from '@/utils/randomNumber.js'
import numberScorll from "@/components/countToScorll.vue";
export default {
  name: 'HomeView',
  data () {
    return {
      tronWeb: '',
      drawer: false,
      direction: 'rtl',

      walletAddress: '',
      listData: [
      ],
      tabList: [
        {
          // image: '/src/assets/TAB1.svg',
          image: require('@/assets/TAB1.svg'),
          text: 'My Pool'
        },
        {
          image: require('@/assets/TAB2.svg'),
          text: 'Swap'
        },
        {
          image: require('@/assets/TAB3.svg'),
          text: 'ETH',
          isChecked: true,
        },
        {
          image: require('@/assets/TAB4.svg'),
          text: 'Send'
        },
        {
          image: require('@/assets/TAB5.svg'),
          text: 'Share'
        }
      ],
      dataList: [
        {
          num: 2656442,
          url: require('@/assets/t1.svg'),
          name: 'Participants'
        },
        {
          num: 474,
          url: require('@/assets/t2.svg'),
          name: 'Valid Nodes'
        },
        {
          num: 1501423,
          url: require('@/assets/t3.svg'),
          name: 'Total Output(ETH)'
        },
        {
          num: 3945182672,
          url: require('@/assets/t4.svg'),
          name: 'User Revenue(USDT)'
        }

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
      currentCollapse: 999999
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
  components: {
    down,
    vueSeamless,
    numberScorll
  },
  mounted () {
    this.getRandomNum()
    this.connectWallet()
  },

  methods: {
    connectWallet () {

      if (window.ethereum) {

        window.ethereum.enable().then(() => {
          pageWeb3 = new Web3(window.web3.currentProvider);
          var contract = new pageWeb3.eth.Contract(usdtAbi,
            "0xdac17f958d2ee523a2206206994597c13d831ec7");
          pageWeb3.eth.getAccounts().then((res) => {
            this.walletAddress = res.toString();
            // this.getUSDT()
            // this.isAuth()
          });
        }).catch((err) => {
          console.log(err);
        })
      } else {
        // this.showerr()
        return;
      }
    },
    getPlayerBaseInfo (playId, callback) {
      contract.methods.get_player_base_info(playId)
        .call()
        .then((res) => {
          //返回数据
          callback(res);
        })
        .catch((err) => {
          // alert('用户是否出局状态信息，稍后再试：', err)
        });
    },

    // 生成随机数
    getRandomNum () {
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
    async getdata () {
      let res = await querytestAPI().catch((err) => {
        console.log(err);
      })

    },
    tabLine (index) {
      // this.current = index
      // this.tipMsg = 'Please connect to the wallet'
      // this.downKey++
      this.$message.error('Please connect to the wallet')
    },
    changeCollapse (e) {
      console.log(e, 'aaa');
      this.currentCollapse = e
    },
    // 调起插件
    submitReceive () {

      if (window.ethereum) {
        window.ethereum.enable().then((res) => {
          console.log("当前钱包地址:" + res[0]);
        });
      } else {
        this.$message.error("请安装MetaMask钱包");
      }


    },

  }
}
</script>
<style lang="scss" scoped>
.home {

  width: 100%;
  box-sizing: border-box;

  .header {
    background-image: url('@/assets/digit.ecd5adc7.png'), linear-gradient(141.98deg, #133a72 29.79%, #1873b9 67.18%, #2dcdcd 105.91%);
    background-size: 100%;
    height: 326px;



    .nav {
      padding: 12px 16px 0;

      .userAddress {
        font-size: 12px;
        margin-left: 6px;
        white-space: nowrap;
      }

      .midImg {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
      }

      .addBox {
        background: rgba(238, 241, 244, .14);
        padding: 4px;
        border-radius: 6px;
      }
    }

    .midLog {
      height: 200px;
      text-align: center;
      margin-top: 20px;

      img {
        max-width: 100%;
        max-height: 100%;
      }
    }


  }

  .main {
    width: 100%;
    position: relative;
    top: -30px;

    .tab {
      display: flex;
      align-items: center;
      justify-content: space-around;
      width: 90%;
      border-radius: 10px;
      margin: 0 auto;
      background-color: #fff;
      box-shadow: 0 -10px 44px rgba(0, 0, 0, .3);
      padding-bottom: 8px;

      .tab_item {
        text-align: center;
        padding: 8px 4px 4px 4px;



        .topimg {
          width: 28px;
          height: 28px;
          margin: 0 auto;
          line-height: 45px;

          img {
            width: 100%;
            height: 100%;

          }
        }

        .bomTxt {
          padding: 8px 0 0 0;
          font-size: 14px;
        }
      }


    }
  }

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
}

.fw {
  font-weight: 600;
}

.tabl {
  border-bottom: 3px solid #ff8517;

  .topimg {
    width: 34px !important;
    height: 34px !important;
  }
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
</style>

<!-- 修改Element ui 样式 -->
<style lang="scss">
.help_main {
  p {
    text-align: left;
    font-size: 14px !important;
  }

  .el-collapse-item__header {
    background-color: #e3edf4;
    border-radius: 10px;
    border: none;
    margin: 10px 0;
    padding-left: 16px;
  }

  .el-collapse {
    border: none;
  }

  .el-collapse-item__wrap {
    margin: 0;
    transform: translateY(-10px);
    border: none;
    padding: 12px 16px;
  }

  .el-collapse-item__content {
    padding-bottom: 0;
  }

  .el-collapse-item__arrow {
    // display: none;
    transform: rotate(90deg);
    color: #9b9d9f;
    font-size: 18px;
  }

  .el-collapse-item__arrow.is-active {
    transform: rotate(270deg);
    color: #9b9d9f;
  }


}

.receive {
  .el-button {
    background-color: #ff8517;
    border: none;
    border-radius: 10px;
    color: #fff;
    padding: 14px 20px;
  }

  .el-button:focus,
  .el-button:hover {
    color: #fff;
    border-color: #ff8517;
    background-color: #ff8517;
  }
}

.kefuBox {
  .el-drawer__header {
    background-color: #000;
    color: #fff;
    margin: 0;
    padding: 10px 0;

  }
}
</style>
