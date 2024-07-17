<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="用户id" prop="uid">
        <el-input
          v-model="queryParams.uid"
          placeholder="请输入用户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户备注" prop="nickname">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入用户备注"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入用户地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="当前挖矿USDT" prop="systemUsdt">-->
<!--        <el-input-->
<!--          v-model="queryParams.systemUsdt"-->
<!--          placeholder="请输入当前挖矿USDT"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="产出时间">
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
<!--          v-hasPermi="['wak:outputlog:add']"-->
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
<!--          v-hasPermi="['wak:outputlog:edit']"-->
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
<!--          v-hasPermi="['wak:outputlog:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:outputlog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outputlogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="uid" width="80"/>
      <el-table-column label="用户备注" align="center" prop="nickname" width="130"/>
      <el-table-column label="用户地址" align="center" prop="address" width="380"/>
      <el-table-column label="当前挖矿USDT" align="center" prop="systemUsdt" />
      <el-table-column label="产出eth" align="center" prop="outputEth" />
      <el-table-column label="盈利比例" align="center" prop="rate" />
      <el-table-column label="产出时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['wak:outputlog:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['wak:outputlog:remove']"-->
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

    <!-- 添加或修改用户产出收益日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入用户地址" />
        </el-form-item>
        <el-form-item label="当前挖矿USDT" prop="systemUsdt">
          <el-input v-model="form.systemUsdt" placeholder="请输入当前挖矿USDT" />
        </el-form-item>
        <el-form-item label="产出eth" prop="outputEth">
          <el-input v-model="form.outputEth" placeholder="请输入产出eth" />
        </el-form-item>
        <el-form-item label="盈利比例" prop="rate">
          <el-input v-model="form.rate" placeholder="请输入盈利比例%" />
        </el-form-item>
        <el-form-item label="用户id" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入用户id" />
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
import { listOutputlog, getOutputlog, delOutputlog, addOutputlog, updateOutputlog } from "@/api/wak/outputlog";

export default {
  name: "Outputlog",
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
      // 用户产出收益日志表格数据
      outputlogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户id时间范围
      daterangeCreatetime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        address: null,
        systemUsdt: null,
        outputEth: null,
        rate: null,
        createtime: null,
        uid: null,
        nickname: null,
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
    /** 查询用户产出收益日志列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreatetime && '' != this.daterangeCreatetime) {
        this.queryParams.params["beginCreatetime"] = this.daterangeCreatetime[0]/1000;
        this.queryParams.params["endCreatetime"] = this.daterangeCreatetime[1]/1000;
      }
      listOutputlog(this.queryParams).then(response => {
        this.outputlogList = response.rows;
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
        systemUsdt: null,
        outputEth: null,
        rate: null,
        createtime: null,
        uid: null
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
      this.title = "添加用户产出收益日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOutputlog(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用户产出收益日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOutputlog(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutputlog(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除用户产出收益日志编号为"' + ids + '"的数据项？').then(function() {
        return delOutputlog(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/outputlog/export', {
        ...this.queryParams
      }, `outputlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
