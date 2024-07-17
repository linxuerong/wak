<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户id" prop="uid">
        <el-input
          v-model="queryParams.uid"
          placeholder="请输入用户id"
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
      <el-form-item label="下级的id" prop="rewardUid">
        <el-input
          v-model="queryParams.rewardUid"
          placeholder="请输入下级的id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下级的地址" prop="rewardAddress">
        <el-input
          v-model="queryParams.rewardAddress"
          placeholder="请输入下级的地址"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产出时间">
        <el-date-picker
          v-model="daterangeCreatetime"
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
          v-hasPermi="['wak:outputlogReward:add']"
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
          v-hasPermi="['wak:outputlogReward:edit']"
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
          v-hasPermi="['wak:outputlogReward:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:outputlogReward:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="outputlogRewardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="uid" />
      <el-table-column label="用户地址" align="center" prop="address" />
      <el-table-column label="奖励的eth" align="center" prop="rewardEth" />
      <el-table-column label="当日等值usdt" align="center" prop="beUsdt" />
      <el-table-column label="产出时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="下级的id" align="center" prop="rewardUid" />
      <el-table-column label="下级的地址" align="center" prop="rewardAddress" />
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['wak:outputlogReward:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['wak:outputlogReward:remove']"-->
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

    <!-- 添加或修改产出分润表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入用户地址" />
        </el-form-item>
        <el-form-item label="被邀请者的地址" prop="rewardAddress">
          <el-input v-model="form.rewardAddress" placeholder="请输入被邀请者的地址" />
        </el-form-item>
        <el-form-item label="奖励的eth" prop="rewardEth">
          <el-input v-model="form.rewardEth" placeholder="请输入奖励的eth" />
        </el-form-item>
        <el-form-item label="被邀请者的id" prop="rewardUid">
          <el-input v-model="form.rewardUid" placeholder="请输入被邀请者的id" />
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
import { listOutputlogReward, getOutputlogReward, delOutputlogReward, addOutputlogReward, updateOutputlogReward } from "@/api/wak/outputlogReward";

export default {
  name: "OutputlogReward",
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
      // 产出分润表表格数据
      outputlogRewardList: [],
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
        rewardAddress: null,
        rewardEth: null,
        rewardUid: null,
        createtime: null,
        uid: null
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
    /** 查询产出分润表列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreatetime && '' != this.daterangeCreatetime) {
        this.queryParams.params["beginCreatetime"] = this.daterangeCreatetime[0];
        this.queryParams.params["endCreatetime"] = this.daterangeCreatetime[1];
      }
      listOutputlogReward(this.queryParams).then(response => {
        this.outputlogRewardList = response.rows;
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
        rewardAddress: null,
        rewardEth: null,
        rewardUid: null,
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
      this.title = "添加产出分润表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOutputlogReward(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改产出分润表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOutputlogReward(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOutputlogReward(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除产出分润表编号为"' + ids + '"的数据项？').then(function() {
        return delOutputlogReward(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/outputlogReward/export', {
        ...this.queryParams
      }, `outputlogReward_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
