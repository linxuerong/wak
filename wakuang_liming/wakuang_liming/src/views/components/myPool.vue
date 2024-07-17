<template>
    <div class="main-content">
      <div>

        <!-- My Account区 -->
        <div>
            <div class="tit jus-bet al-item">
                <div class="al-item">
                    <span class="titBefore"></span> <span>My Account</span>
                </div>
                <div ref="keframeId">
                    <i class="el-icon-refresh" style="color:#1873b9" @click="updatePage"></i>
                </div>
            </div>

            <div style="font-size: 14px;">
                <div class="jus-bet top">
                    <div style="color: #92918a;">total Output</div>
                    <div class="al-item"><span style="color:#2c5593">{{ userData.outputEth ?
                        userData.outputEth : '0' }} </span> <img src="@/assets/ETH.png" alt=""
                            style="width: 26px;height: 26px;object-fit: cover;"></div>
                </div>
                <div class="jus-bet top">
                    <div style="color: #92918a;">Exchangeable</div>
                    <div class="al-item"><span style="color:#2c5593">{{ userData.systemEth ?
                        userData.systemEth : '0' }}</span> <img src="@/assets/ETH.png" alt=""
                            style="width: 26px;height: 26px;object-fit: cover;"></div>
                </div>
                <div class="jus-bet top">
                    <div style="color: #92918a;">Wallet Balance</div>
                    <div class="al-item"><span style="color:#2c5593"> {{ userData.systemUsdt ?
                        userData.systemUsdt : '0' }} </span> <img src="@/assets/USDT.png" alt=""
                            style="width: 26px;height: 20px;object-fit: cover;"></div>
                </div>
            </div>


        </div>

        <div v-if="fzOrderData" style="margin-top: 10px">
          <div class="tit jus-bet al-item">
            <div class="al-item">
              <span class="titBefore"></span> <span>Provide Liquidity</span>
            </div>
            <div ref="keframeId">
              <i class="el-icon-refresh" style="color:#1873b9" @click="updatePage"></i>
            </div>
          </div>
          <div style="font-size: 14px;">
            <div class="jus-bet top">
              <div style="color: #92918a;">Requirement</div>
              <div class="al-item"><span style="color:#2c5593">{{ fzOrderData.fzUsdtLimit}} </span> <img src="@/assets/USDT.png" alt=""
                                                                                                         style="width: 26px;height: 20px;object-fit: cover;"></div>
            </div>
            <div class="jus-bet top">
              <div style="color: #92918a;">Liquidity</div>
              <div class="al-item"><span style="color:#2c5593">{{ fzOrderData.fzUsdt }}</span> <img src="@/assets/USDT.png" alt=""
                                                                                                    style="width: 26px;height: 20px;object-fit: cover;"></div>
            </div>
            <div class="jus-bet top">
              <div style="color: #92918a;">Mining Profit</div>
              <div class="al-item"><span style="color:#2c5593"> {{ fzOrderData.rewardRate}} </span> <img src="@/assets/ETH.png" alt=""
                                                                                                         style="width: 26px;height: 20px;object-fit: cover;"></div>
            </div>
            <div class="jus-bet top">
              <div style="color: #92918a;">Period Reward</div>
              <div class="al-item"><span style="color:#2c5593"> {{ fzOrderData.wkReward }} </span> <img src="@/assets/ETH.png" alt=""
                                                                                                        style="width: 26px;height: 20px;object-fit: cover;"></div>
            </div>
            <div class="jus-bet top">
              <div style="color: #92918a;">Period</div>
              <div class="al-item"><span style="color:#2c5593"> {{ fzOrderData.fzDay}} </span> <img src="@/assets/ETH.png" alt=""
                                                                                                    style="width: 26px;height: 20px;object-fit: cover;"></div>
            </div>
            <div class="jus-bet top">
              <div style="color: #92918a;">End Time</div>
              <div class="al-item"><span style="color:#2c5593"> {{ fzOrderData.endTime }} </span> <img src="@/assets/ETH.png" alt=""
                                                                                                       style="width: 26px;height: 20px;object-fit: cover;"></div>
            </div>
          </div>
          <div style="color: #92918a;font-size: 12px;margin: 10px">Provide liquidity to the mining pool, and you will get high
            rewards during the period. If the liquidity requirements are
            met before the end of the time, there will be additional ETH
            rewards. All rewards will be issued at the end of the time!</div>

        </div>

      </div>


        <!-- 表格区 -->
        <div class="tableBox">
            <el-table :data="tableData" style="width: 100%" header-cell-class-name="tableClass" max-height="330">
                <el-table-column prop="date" label="Time">
                    <template slot-scope="scope">
                         {{ scope.row.createtime ? formatDate(scope.row.createtime * 1000) : '' }}
<!--                        {{ scope.row.createtime ? scope.row.createtime : '' }}-->
                    </template>
                </el-table-column>
                <el-table-column prop="systemUsdt" label="USDT Power" align="center" width="110">
                </el-table-column>
                <el-table-column prop="outputEth" label="Out ETH" align="center">
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
import { indexOutput_log,getFzOrder } from '@/api/apiTrc.js'
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
          fzOrderData:undefined,
            tableData: [{
                date: 'Aug 11 00:00',
                name: '2327874.62',
                address: '25.142292'
            }, {
                date: 'Aug 10 00:00',
                name: '2282159.62',
                address: '24.649348'
            },
            {
                date: 'Aug 09 00:00',
                name: '2086814.20',
                address: '24.717144'
            },
            {
                date: 'Aug 08 00:00',
                name: '1928623.42',
                address: '21.895509'
            }],
        }
    },
    mounted () {
        this.getList();
        this.getFzOrder();

    },
    methods: {
        // 日期转换
        // 日期转换
        formatDate (millinSeconds) {
            var date = new Date(millinSeconds);
            var monthArr = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
            var suffix = new Array("st", "nd", "rd", "th");

            var year = date.getFullYear(); //年
            var month = monthArr[date.getMonth()]; //月
            var ddate = date.getDate(); //日
            var hh = date.getHours()
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

        async getList () {
            let res = await indexOutput_log({
                address: this.userData.address
            })
            if (res.code == 1) {
                this.tableData = res.data
            }
        },
        async getFzOrder () {
            let res = await getFzOrder({
                address: this.userData.address
            })
            if (res.code == 1) {
                this.fzOrderData = res.data
            }
        },
        // 刷新数据
        updatePage () {
            this.$refs.keframeId.className = "animate__animated "
            setTimeout(() => {
                this.$refs.keframeId.className = "animate__animated animate__rotateIn"
            }, 0);
            this.$emit('submitBalance')
        }
    }
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

.top {
    padding: 20px 10px 0 10px;
}

.tableClass {
    color: #133a72 !important;
    font-weight: normal;
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
</style>
