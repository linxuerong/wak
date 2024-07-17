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
<!--      <el-form-item label="质押要求" prop="fzUsdtLimit">-->
<!--        <el-input-->
<!--          v-model="queryParams.fzUsdtLimit"-->
<!--          placeholder="请输入质押要求"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="质押周期" prop="fzDay">-->
<!--        <el-input-->
<!--          v-model="queryParams.fzDay"-->
<!--          placeholder="请输入质押周期"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="已质押usdt" prop=" fzUsdt">-->
<!--        <el-input-->
<!--          v-model="queryParams. fzUsdt"-->
<!--          placeholder="请输入已质押usdt"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="挖矿收益usdt" prop="wkReward">-->
<!--        <el-input-->
<!--          v-model="queryParams.wkReward"-->
<!--          placeholder="请输入挖矿收益usdt"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="挖矿间隔" prop="wkLimit">-->
<!--        <el-input-->
<!--          v-model="queryParams.wkLimit"-->
<!--          placeholder="请输入挖矿间隔"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="产矿比例" prop="rewardRate">-->
<!--        <el-input-->
<!--          v-model="queryParams.rewardRate"-->
<!--          placeholder="请输入产矿比例"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="完成奖励" prop="finishReward">-->
<!--        <el-input-->
<!--          v-model="queryParams.finishReward"-->
<!--          placeholder="请输入完成奖励"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="自动结算" prop="isAuto">
        <el-select v-model="queryParams.isAuto" placeholder="请选择自动结算" clearable>
          <el-option
            v-for="dict in dict.type.wak_fzorder_auto"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="质押状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择质押状态" clearable>
          <el-option
            v-for="dict in dict.type.wak_fzorder_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="daterangeStartTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="daterangeEndTime"
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
          v-hasPermi="['wak:fzOrder:add']"
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
          v-hasPermi="['wak:fzOrder:edit']"
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
          v-hasPermi="['wak:fzOrder:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:fzOrder:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fzOrderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="uid" width="80"/>
      <el-table-column label="用户备注" align="center" prop="nickname" width="130"/>
      <el-table-column label="用户地址" align="center" prop="address" width="380"/>
      <el-table-column label="质押要求" align="center" prop="fzUsdtLimit" />
      <el-table-column label="质押周期" align="center" prop="fzDay" />
      <el-table-column label="已质押usdt" align="center" prop="fzUsdt" />
      <el-table-column label="挖矿收益(eth)" align="center" prop="wkReward" />
<!--      <el-table-column label="挖矿间隔" align="center" prop="wkLimit" />-->
      <el-table-column label="产矿比例" align="center" prop="rewardRate" />
      <el-table-column label="完成奖励" align="center" prop="finishReward" />
      <el-table-column label="自动结算" align="center" prop="isAuto">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_fzorder_auto" :value="scope.row.isAuto"/>
        </template>
      </el-table-column>
      <el-table-column label="质押状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_fzorder_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="邀请信息" align="center" prop="remark" />
      <el-table-column label="创建时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wak:fzOrder:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wak:fzOrder:remove']"
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

    <!-- 添加或修改质押订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="190px">
        <el-form-item label="用户id" prop="uid">
          <el-input v-model="form.uid" placeholder="请输入用户id" />
        </el-form-item>
<!--        <el-form-item label="用户地址" prop="address">-->
<!--          <el-input v-model="form.address" placeholder="请输入用户地址" />-->
<!--        </el-form-item>-->
        <el-form-item label="质押要求" prop="fzUsdtLimit">
          <el-input v-model="form.fzUsdtLimit" placeholder="请输入质押要求" />
        </el-form-item>
        <el-form-item label="质押周期（天）" prop="fzDay">
          <el-input v-model="form.fzDay" placeholder="请输入质押周期" />
        </el-form-item>
        <el-form-item label="已质押usdt" prop="fzUsdt">
          <el-input v-model="form.fzUsdt" placeholder="请输入已质押usdt" />
        </el-form-item>
        <el-form-item label="挖矿收益(eth)" prop="wkReward">
          <el-input v-model="form.wkReward" placeholder="默认为0" />
        </el-form-item>
<!--        <el-form-item label="挖矿间隔（分钟）" prop="wkLimit">-->
<!--          <el-input v-model="form.wkLimit" placeholder="请输入挖矿间隔" />-->
<!--        </el-form-item>-->
        <el-form-item label="产矿比例%(支持3位小数)" prop="rewardRate">
          <el-input v-model="form.rewardRate" placeholder="请输入产矿比例" />
        </el-form-item>
        <el-form-item label="完成奖励%(支持6位小数)" prop="finishReward">
          <el-input v-model="form.finishReward" placeholder="请输入完成奖励" />
        </el-form-item>
        <el-form-item label="自动结算" prop="isAuto">
          <el-radio-group v-model="form.isAuto">
            <el-radio
              v-for="dict in dict.type.wak_fzorder_auto"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="质押状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.wak_fzorder_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邀请信息" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入邀请信息" />
        </el-form-item>
<!--        <el-form-item label="开始时间" prop="startTime">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.startTime"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择开始时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="结束时间" prop="endTime">-->
<!--          <el-date-picker clearable-->
<!--                          v-model="form.endTime"-->
<!--                          type="date"-->
<!--                          value-format="yyyy-MM-dd"-->
<!--                          placeholder="请选择结束时间">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFzOrder, getFzOrder, delFzOrder, addFzOrder, updateFzOrder } from "@/api/wak/fzOrder";

export default {
  name: "FzOrder",
  dicts: ['wak_fzorder_status', 'wak_fzorder_auto'],
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
      // 质押订单表格数据
      fzOrderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      isAdd:true,
      // 结束时间时间范围
      daterangeCreateTime: [],
      // 结束时间时间范围
      daterangeStartTime: [],
      // 结束时间时间范围
      daterangeEndTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        uid: null,
        address: null,
        nickname: null,
        fzUsdtLimit: null,
        fzDay: null,
        fzUsdt: null,
        wkReward: null,
        wkLimit: null,
        rewardRate: null,
        finishReward: null,
        isAuto: null,
        status: null,
        startTime: null,
        endTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        uid: [
          { required: true, message: "用户id不能为空", trigger: "blur" }
        ],
        fzUsdtLimit: [
          { required: true, message: "质押要求不能为空", trigger: "blur" }
        ],
        fzDay: [
          { required: true, message: "质押周期不能为空", trigger: "blur" }
        ],
        fzUsdt: [
          { required: true, message: "已质押usdt不能为空", trigger: "blur" }
        ],
        rewardRate: [
          { required: true, message: "产矿比例不能为空", trigger: "blur" }
        ],
        finishReward: [
          { required: true, message: "完成奖励不能为空", trigger: "blur" }
        ],
        isAuto: [
          { required: true, message: "自动结算不能为空", trigger: "change" }
        ],
        status: [
          { required: true, message: "质押状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询质押订单列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      if (null != this.daterangeStartTime && '' != this.daterangeStartTime) {
        this.queryParams.params["beginStartTime"] = this.daterangeStartTime[0];
        this.queryParams.params["endStartTime"] = this.daterangeStartTime[1];
      }
      if (null != this.daterangeEndTime && '' != this.daterangeEndTime) {
        this.queryParams.params["beginEndTime"] = this.daterangeEndTime[0];
        this.queryParams.params["endEndTime"] = this.daterangeEndTime[1];
      }
      listFzOrder(this.queryParams).then(response => {
        this.fzOrderList = response.rows;
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
        uid: null,
        address: null,
        fzUsdtLimit: null,
        fzDay: null,
        fzUsdt: null,
        wkReward: null,
        wkLimit: null,
        rewardRate: null,
        finishReward: null,
        isAuto: null,
        status: null,
        remark: null,
        createTime: null,
        startTime: null,
        endTime: null
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
      this.daterangeCreateTime = [];
      this.daterangeStartTime = [];
      this.daterangeEndTime = [];
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
      this.title = "添加质押订单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFzOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改质押订单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFzOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFzOrder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除质押订单编号为"' + ids + '"的数据项？').then(function() {
        return delFzOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/fzOrder/export', {
        ...this.queryParams
      }, `fzOrder_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
