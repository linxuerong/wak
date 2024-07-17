<template >
    <div class="shareBox">
        <div class="tit">My invitation code</div>
        <div class="al-item jus-bet copyCss">
            <div style="color:#aca5a4;width: 90%;white-space: nowrap;font-size: 12px; ">
                {{ localHref }}
            </div>
            <div style="color:#1873b9;width: 10%" @click="copyMethods(localHref)">Copy</div>
        </div>
        <p style="color:#aca5a4;font-size: 12px;">
            Send your invitation link,and friends will join the node through your link,and you will get generous token
            rewards
        </p>
        <div class="al-item jus-bet" style="text-align:center">
            <div>
                <div class="imgW">
                    <img src="@/assets/aa.png" alt="">
                </div>
                <div class="imgF">
                    1.Get Code
                </div>
            </div>
            <div>
                <div class="imgW">
                    <img src="@/assets/aaa.png" alt="">
                </div>
                <div class="imgF">
                    2.Invite Friends
                </div>
            </div>
            <div>
                <div class="imgW">
                    <img src="@/assets/a.png" alt="">
                </div>
                <div class="imgF">
                    3.Earn Crypto
                </div>
            </div>
        </div>
        <div style="text-align: center;padding:10px 0">
            <el-button @click=" copyMethods(localHref)">Share</el-button>
        </div>

      <!-- 表格区 -->
      <div class="tableBox">
        <el-table :data="tableData" style="width: 100%;word-break:normal" header-cell-class-name="tableClass" max-height="330">
          <el-table-column prop="rewardAddress" label="Recommender" align="center" width="100">
          </el-table-column>
          <el-table-column prop="rewardEth" label="Reward ETH" align="center">
          </el-table-column>
          <el-table-column prop="beUsdt" label="≈usdt" align="center" width="100">
          </el-table-column>
          <el-table-column prop="date" label="Time" width="100">
            <template slot-scope="scope">
              <a style="width: 50px;display: block"> {{ scope.row.createtime ? formatDate(scope.row.createtime * 1000) : '' }}</a>
<!--              {{ scope.row.createtime ? formatDate(scope.row.createtime * 1000) : '' }}-->
              <!--                        {{ scope.row.createtime ? scope.row.createtime : '' }}-->
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
import {indexOutputReward_log} from "@/api/apiTrc";

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
            localHref: '',
          tableData: [],
        }
    },
    mounted () {
      this.getList();
        this.getLocation();
    },
    methods: {
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
        // 获取路径地址
        getLocation () {
            let str = window.location.href.split('#')
            let nStr = str[0] + '#/?invite='
            this.localHref = nStr + this.userData.inviteCode
        },
      async getList () {
        let res = await indexOutputReward_log({
          address: this.userData.address
        })
        if (res.code == 1) {
          this.tableData = res.data
        }
      },
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
    }
}
</script>
<style lang="scss" scoped>
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
.shareBox {
    padding: 0 24px;

    .tit {
        text-align: center;
        font-weight: 800;
    }

    .copyCss {
        background-color: #dff1f3;
        padding: 10px;
        border-radius: 6px;
        margin: 16px 0;
        font-size: 14px;
    }

    .imgW {
        padding-top: 26px;
    }

    .imgF {
        margin: 16px 0;
        color: #666;
        font-size: 12px;
    }
}
</style>
<style lang="scss">
.shareBox {
    .el-button {
        border: none;
        background-color: #37a791;
        color: #fff;
        padding: 16px 60px;

    }
}
</style>
