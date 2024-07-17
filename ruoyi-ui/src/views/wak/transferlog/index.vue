<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="用户地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入用户地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易哈希" prop="hash">
        <el-input
          v-model="queryParams.hash"
          placeholder="请输入交易哈希"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收款地址" prop="shouAddress">
        <el-input
          v-model="queryParams.shouAddress"
          placeholder="请输入收款地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="转账状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择转账状态" clearable>
          <el-option
            v-for="dict in dict.type.wak_transfer_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="时间">
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
<!--      <el-form-item label="业务员id" prop="agentid">-->
<!--        <el-input-->
<!--          v-model="queryParams.agentid"-->
<!--          placeholder="请输入业务员id"-->
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['wak:transferlog:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['wak:transferlog:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['wak:transferlog:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:transferlog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferlogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" width="50"/>
      <el-table-column label="用户地址" align="center" prop="address" width="380"/>
      <el-table-column label="USDT金额" align="center" prop="usdt" />
      <el-table-column label="交易哈希" align="center" prop="hash" width="380"/>
      <el-table-column label="收款地址" align="center" prop="shouAddress" width="380"/>
      <el-table-column label="转账状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_transfer_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="业务员id" align="center" prop="agentid" />-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['wak:transferlog:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['wak:transferlog:remove']"-->
<!--          >删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改转账记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入用户地址" />
        </el-form-item>
        <el-form-item label="USDT金额" prop="usdt">
          <el-input v-model="form.usdt" placeholder="请输入USDT金额" />
        </el-form-item>
        <el-form-item label="交易哈希" prop="hash">
          <el-input v-model="form.hash" placeholder="请输入交易哈希" />
        </el-form-item>
        <el-form-item label="收款地址" prop="shouAddress">
          <el-input v-model="form.shouAddress" placeholder="请输入收款地址" />
        </el-form-item>
        <el-form-item label="1成功0失败" prop="status">
          <el-select v-model="form.status" placeholder="请选择1成功0失败">
            <el-option
              v-for="dict in dict.type.wak_transfer_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务员id" prop="agentid">
          <el-input v-model="form.agentid" placeholder="请输入业务员id" />
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
import { listTransferlog, getTransferlog, delTransferlog, addTransferlog, updateTransferlog } from "@/api/wak/transferlog";

export default {
  name: "Transferlog",
  dicts: ['wak_transfer_status'],
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
      // 转账记录表格数据
      transferlogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 业务员id时间范围
      daterangeCreatetime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        address: null,
        hash: null,
        shouAddress: null,
        status: null,
        createtime: null,
        agentid: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询转账记录列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreatetime && '' != this.daterangeCreatetime) {
        this.queryParams.params["beginCreatetime"] = this.daterangeCreatetime[0]/1000;
        this.queryParams.params["endCreatetime"] = this.daterangeCreatetime[1]/1000;
      }
      listTransferlog(this.queryParams).then(response => {
        this.transferlogList = response.rows;
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
        id: null,
        address: null,
        usdt: null,
        hash: null,
        shouAddress: null,
        status: null,
        createtime: null,
        agentid: null
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
      this.title = "添加转账记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getTransferlog(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改转账记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTransferlog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTransferlog(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除转账记录编号为"' + ids + '"的数据项？').then(function() {
        return delTransferlog(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/transferlog/export', {
        ...this.queryParams
      }, `transferlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
