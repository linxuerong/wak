<template>
    <div class="main-content">
        <!-- My Account区 -->
        <div>
            <div class="tit jus-bet al-item ">
                <div class="al-item">
                    <span class="titBefore"></span> <span>Exchangeable</span>
                </div>
                <div class="al-item">
                    <span style="color:#285da4">
                        {{ userData.systemEth ? userData.systemEth : '0' }}
                    </span>

                    <img src="@/assets/ETH.png" alt="" style="width: 26px;height: 26px;object-fit: cover;">
                </div>
            </div>

            <div style="font-size: 14px;" class="ExchangeMain">
                <div class="changeBox ">
                    <div class="top jus-evn al-item">
                        <transition-group appear name="animate__animated animate__bounce"
                            enter-active-class="animate__rubberBand" leave-active-class="animate__rotateOut">
                            <!-- <div v-if="!isSHow" key="1" style="height:100px">
                                <div> <img src="@/assets/USDTQKL.png" alt="" style="width: 25px;height:25px"></div>
                                <div v-if="!isFouse">
                                    <el-input v-model="money1" placeholder="USDT" @focus="changeNum1"></el-input>
                                </div>
                                <div v-else>
                                    <el-input v-model="moneyF" placeholder="USDT" @focus="changeNum1"></el-input>
                                </div>
                                <div class="exchangeText">
                                    ETH 1845.66 <i class="el-icon-refresh" style="color:#1873b9"></i>
                                </div>
                            </div> -->
                            <div v-if="isSHow" key="2" style="height:100px">
                                <div>
                                    <img src="@/assets/ETH1.png" alt="" style="width: 25px;height:25px">
                                </div>
                                <div class="al-item jus-evn">
                                    <el-input v-if="!isFouse" v-model="money" placeholder="0" @focus="changeNum"></el-input>
                                    <el-input v-else v-model="moneyF1" placeholder="0" @focus="changeNum"></el-input>
                                </div>
                                <div class="exchangeText" @click="redeemAll">
                                    redeem all
                                </div>
                            </div>
                        </transition-group>


                        <transition-group appear name="animate__animated animate__bounce"
                            enter-active-class="animate__rotateIn" leave-active-class="animate__rotateOut">
                            <!-- <div @click="reserveItem" key="1" v-show="!isSHow">
                                <img src="@/assets/zh.png" alt="" style="width: 22px;height:22px">
                            </div> -->
                            <div @click="reserveItem" key="2" v-show="isSHow" ref="keyframe1">
                                <img src="@/assets/zh.png" alt="" style="width: 22px;height:22px">
                            </div>
                        </transition-group>

                        <transition-group appear name="animate__animated animate__bounce"
                            enter-active-class="animate__rubberBand" leave-active-class="animate__rotateOut">
                            <div v-if="isSHow" key="1" style="height:100px">
                                <div> <img src="@/assets/USDTQKL.png" alt="" style="width: 25px;height:25px"></div>
                                <div v-if="!isFouse">
                                    <el-input v-model="money1" placeholder="USDT" @focus="changeNum1" readonly></el-input>
                                </div>
                                <div v-else>
                                    <el-input v-model="moneyF" placeholder="USDT" @focus="changeNum1" readonly></el-input>
                                </div>
                                <div class="exchangeText">
                                    ETH {{ EthBl }}
                                    <span ref="keyframeId">
                                        <i class="el-icon-refresh" style="color:#1873b9" @click="updateExchange"></i>
                                    </span>

                                </div>
                            </div>
                            <!-- <div v-if="!isSHow" key="2" style="height:100px">
                                <div>
                                    <img src="@/assets/ETH1.png" alt="" style="width: 25px;height:25px">
                                </div>
                                <div class="al-item jus-evn">
                                    <el-input v-if="!isFouse" v-model="money" placeholder="0" @focus="changeNum"
                                        readonly></el-input>
                                    <el-input v-else v-model="moneyF1" placeholder="0" @focus="changeNum"
                                        readonly></el-input>
                                </div>
                                <div class="exchangeText" @click="redeemAll">
                                    redeem all
                                </div>
                            </div> -->
                        </transition-group>



                    </div>

                </div>
                <div style="text-align: center;">
                    <el-button @click="exchangeSubmit">Exchange</el-button>
                </div>
                <p style="text-align:center;color:#ccc">Convert the ETH coins you dug into USDT</p>
            </div>


        </div>
        <!-- 表格区 -->
        <div class="tableBox">
            <el-table :data="tableData" style="width: 100%" header-cell-class-name="tableClass" max-height="180">
                <el-table-column prop="createtime" label="Time">
                     <template slot-scope="scope">{{ formatDate(scope.row.createtime * 1000) }}</template>
<!--                    <template slot-scope="scope"> {{ scope.row.createtime ? scope.row.createtime : '' }}</template>-->
                </el-table-column>
                <el-table-column prop="ethNum" label="ETH" align="center" width="110">
                </el-table-column>
                <el-table-column prop="usdtNum" label="USDT" align="center">
                </el-table-column>
                <template slot="empty">
                    <div style="margin-top:20px">
                        <img src="@/assets/nodata.png" alt="">
                    </div>
                </template>
            </el-table>
        </div>


    </div>
</template>
<script>
import { indexGetethprice, indexExchange, indexExchange_log } from '@/api/apiTrc.js'
import 'animate.css'
export default {
    props: {
        userData: {
            default () {
                return {}
            }
        }
    },
    data () {
        return {
            tableData: [],
            money: 0,
            // money1: 0
            moneyF: '',
            isSHow: true,
            isFouse: false,
            EthBl: '',

        }
    },
    computed: {

        money1 () {

            return parseFloat(this.money * this.EthBl).toFixed(6)

        },
        // moneyF1 () {

        //     return this.moneyF / this.EthBl
        // }
    },
    mounted () {
        this.getData()
        this.getListData()
    },
    methods: {
        // 日期转换
        formatDate (millinSeconds) {
            var date = new Date(millinSeconds);
            var monthArr = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
            var suffix = new Array("st", "nd", "rd", "th");

            var year = date.getFullYear(); //年
            var month = monthArr[date.getMonth()]; //月
            var ddate = date.getDate(); //日
            var hh = date.getHours() <= 9 ? '0' + date.getHours() : date.getHours()
            var mm = date.getMinutes() <= 9 ? '0' + date.getMinutes() : date.getMinutes()
            var ss = date.getSeconds()
            // if (ddate % 10 < 1 || ddate % 10 > 3) {
            //     ddate = ddate + suffix[3];
            // } else if (ddate % 10 == 1) {
            //     ddate = ddate + suffix[0];
            // } else if (ddate % 10 == 2) {
            //     ddate = ddate + suffix[1];
            // } else {
            //     ddate = ddate + suffix[2];
            // }
            return month + " " + ddate + " " + "" + hh + ":" + mm;
        },

        // 获取转换列表
        async getListData () {
            let res = await indexExchange_log({
                address: this.userData.address
            })
            if (res.code == 1) {
                this.tableData = res.data
            } else {
                this.$message.error(res.msg)
            }
        },
        // 转换TRC
        async exchangeSubmit () {
            if (!this.money || this.money == 0) {
                this.$message.warning('no money')
                return
            }
            let res = await indexExchange({
                eth: this.money,
                address: this.userData.address
            })
            if (res.code == 1) {
                this.$message.success(res.msg)
                this.$emit('submitBalance')
                this.getListData()
            } else {
                this.$message.error(res.msg)
            }
        },
        // 转换全部
        redeemAll () {
            this.money = this.userData.systemEth ? this.userData.systemEth : ''

        },
        // 刷新ETH
        updateExchange () {
            this.$refs.keyframeId.className = " animate__animated "
            setTimeout(() => {
                this.$refs.keyframeId.className = " animate__animated animate__rotateIn"
            }, 0);
            this.getData()
        },
        async getData () {
            let res = await indexGetethprice()
            this.EthBl = res.data
        },
        changeNum () {
            // this.money = 0
            // this.moneyF = 0
            this.isFouse = false
        },
        changeNum1 () {
            // this.money = 0
            // this.moneyF = 0
            // this.isFouse = true
        },
        reserveItem () {
            this.$refs.keyframe1.className = " animate__animated "
            setTimeout(() => {
                this.$refs.keyframe1.className = " animate__animated animate__jello"
            }, 0);
            // 控制动画开关
            // this.isSHow = !this.isSHow

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
}


.tableClass {
    color: #133a72 !important;
    font-weight: normal;
}

.changeBox {
    background-color: #dff1f3;
    padding: 16px;
    border-radius: 8px;
    margin: 26px 0;
    text-align: center;
    height: 110px;
    overflow: hidden;

    .exchangeText {
        padding-top: 16px;
        color: #245da0;

    }
}
</style>
<style lang="scss">
.tableBox {
    margin-top: 20px;

    .el-table td.el-table__cell,
    .el-table th.el-table__cell.is-leaf {
        border: none;
    }

    .el-table__footer-wrapper,
    .el-table__header-wrapper {
        border-bottom: 1px solid #adb6ab;
    }

    .el-table {
        padding: 8px;
        box-shadow: 0px 0px 2px 1px #e7e5db;
    }

    .el-table th.el-table__cell>.cell {
        color: #1873b9;
    }

    .el-table .el-table__cell {
        padding: 6px 0;
    }
}

.changeBox {
    .el-input__inner {
        background-color: #dff1f3;
        border: none;
        padding: 0 6px;
    }


}

.ExchangeMain {
    .el-button {
        background-color: #261e98 !important;
        color: #fff;
        border: none;
        padding: 12px 40px;
    }

    .changeBox .el-input__inner {
        text-align: center;
    }
}
</style>
