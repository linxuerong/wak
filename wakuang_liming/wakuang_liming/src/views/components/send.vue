<template>
    <div class="main-content">
        <!-- My Account区 -->
        <div>
            <div class="tit jus-bet al-item">
                <div class="al-item">
                    <span class="titBefore"></span> <span>Wallet Balance</span>
                </div>
                <div class="al-item">
                    <span style="color:#285da4">
                        {{ userData.systemUsdt ? userData.systemUsdt : '0' }}

                    </span>

                    <img src="@/assets/USDT.png" alt="" style="width: 26px;height: 26px;object-fit: cover;">
                </div>
            </div>

            <div style="font-size: 14px;" class="ExchangeMain">
                <div class="changeBox ">
                    <div class="top jus-bet al-item">
                        <div style="width:40%;">
                            <div class="al-item">
                                <el-input v-model="money" placeholder="0"></el-input>
                            </div>
                            <div class="exchangeText">
                                Total balance
                            </div>
                        </div>

                        <div class="line">

                        </div>
                        <div style="width:40%;">
                            <div class="al-item" style=" justify-content: flex-end;">
                                <img src="@/assets/USDTQKL.png" alt="" style="width: 30px;height:30px">
                                <el-input v-model="money1" placeholder="USDT" style="width: 60px;"></el-input>
                            </div>
                        </div>

                    </div>

                </div>
                <div style="text-align: center;">
                    <el-button @click="submit">Confirm</el-button>
                </div>
                <p style="color:#ccc">Your withdrawal will be sent to your USDT wallet address within 24
                    hours in the future</p>
            </div>


        </div>
        <!-- 表格区 -->
        <div class="tableBox">
            <el-table :data="tableData" style="width: 100%" header-cell-class-name="tableClass" max-height="225">
                <el-table-column prop="date" label="Time">
                    <template slot-scope="scope">
                         {{ formatDate(scope.row.createtime * 1000) }}
<!--                        {{  scope.row.createtime ? scope.row.createtime : '' }}-->
                    </template>
                </el-table-column>
                <el-table-column prop="usdt" label="USDT" align="center">
                </el-table-column>
                <el-table-column prop="status" label="Status" align="center">
                    <template slot-scope="scope">
                        {{ changeStatus(scope.row.status) }}
                    </template>
                </el-table-column>
                <el-table-column prop="remark" label="Remark" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.remark}}
                    </template>
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
import { indexWithdraw, indexWithdraw_log } from '@/api/apiTrc.js'
export default {
    props: {
        userData: {
            default () {
                return {}
            }
        },

    },
    data () {
        return {
            tableData: [],
            money: '',
            money1: ''
        }
    },
    mounted () {
        this.getList()
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

        changeStatus (num) {
            let arr = ['Under Review', 'Success', 'Fail']
            return arr[num]
        },
        async getList () {
            let res = await indexWithdraw_log({
                address: this.userData.address
            })
            if (res.code == 1) {
                this.tableData = res.data
            } else {
                this.$message.error(res.msg)
            }

        },
        async submit () {
            let res = await indexWithdraw({
                address: this.userData.address,
                usdt: this.money
            })
            if (res.code == 1) {

                this.$message.success(res.msg)
                this.money = 0
                this.$emit('submitBalance')
            }
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


.tableClass {
    color: #133a72 !important;
    font-weight: normal;
}

.changeBox {
    background-color: #dff1f3;
    padding: 16px;
    border-radius: 8px;
    margin: 16px 0;

    .exchangeText {
        padding-top: 16px;
        color: #245da0;

    }
}

.line {
    border-left: 1px solid #ccc;
    height: 30px;
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
        background-color: #ec7b2a !important;
        color: #fff;
        border: none;
        padding: 12px 40px;
    }
}
</style>
