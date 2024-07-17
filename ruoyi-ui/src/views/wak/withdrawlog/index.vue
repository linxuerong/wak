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
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.wak_withdraw_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户身份" prop="usertype">
        <el-select v-model="queryParams.usertype" placeholder="请选择用户身份" clearable>
          <el-option
            v-for="dict in dict.type.wak_user_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务员id" prop="agentid">
        <el-input
          v-model="queryParams.agentid"
          placeholder="请输入业务员id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时间">
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
      <el-form-item label="审核时间">
        <el-date-picker
          v-model="daterangeUpdatetime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="用户id" prop="uid">
        <el-input
          v-model="queryParams.uid"
          placeholder="请输入用户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户昵称" prop="nickname">
        <el-input
          v-model="queryParams.nickname"
          placeholder="请输入用户昵称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['wak:withdrawlog:add']"
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
          v-hasPermi="['wak:withdrawlog:edit']"
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
          v-hasPermi="['wak:withdrawlog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:withdrawlog:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="withdrawlogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="uid" width="80"/>
      <el-table-column label="用户昵称" align="center" prop="nickname" width="130"/>
      <el-table-column label="用户地址" align="center" prop="address" width="380"/>
      <el-table-column label="提现USDT数量" align="center" prop="usdt" />
      <el-table-column label="系统usdt" align="center" prop="systemUsdt" width="100"/>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_withdraw_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="用户身份" align="center" prop="usertype">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wak_user_type" :value="scope.row.usertype"/>
        </template>
      </el-table-column>
      <el-table-column label="业务员id" align="center" prop="agentid" />
      <el-table-column label="时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核时间" align="center" prop="updatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime, '{y}-{m}-{d} {hh}:{mm}:{ss}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status == 0"
            size="mini"
            type="text"
            icon="el-icon-caret-right"
            @click="handlePass(scope.row)"
            v-hasPermi="['wak:withdrawlog:edit']"
          >通过</el-button>
          <el-button
            v-if="scope.row.status == 0"
            size="mini"
            type="text"
            icon="el-icon-caret-left"
            @click="handleRefund(scope.row)"
            v-hasPermi="['wak:withdrawlog:edit']"
          >驳回</el-button>
          <el-button
            v-if="scope.row.status == 0"
            size="mini"
            type="text"
            icon="el-icon-caret-right"
            @click="handlePass2(scope.row)"
            v-hasPermi="['wak:withdrawlog:edit']"
          >手动通过</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wak:withdrawlog:edit']"
          >备注</el-button>
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

    <!-- 添加或修改用户提现日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="用户地址" prop="address">-->
<!--          <el-input v-model="form.address" placeholder="请输入用户地址" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="提现USDT数量" prop="usdt">-->
<!--          <el-input v-model="form.usdt" placeholder="请输入提现USDT数量" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="状态" prop="status">-->
<!--          <el-select v-model="form.status" placeholder="请选择状态">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.wak_withdraw_status"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--              :value="parseInt(dict.value)"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="用户身份:1=正式,2=试玩" prop="usertype">-->
<!--          <el-select v-model="form.usertype" placeholder="请选择用户身份:1=正式,2=试玩">-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.wak_user_type"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--              :value="parseInt(dict.value)"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="业务员id" prop="agentid">-->
<!--          <el-input v-model="form.agentid" placeholder="请输入业务员id" />-->
<!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
<!--        <el-form-item label="用户id" prop="uid">-->
<!--          <el-input v-model="form.uid" placeholder="请输入用户id" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="用户昵称" prop="nickname">-->
<!--          <el-input v-model="form.nickname" placeholder="请输入用户昵称" />-->
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
import {
  listWithdrawlog,
  getWithdrawlog,
  delWithdrawlog,
  addWithdrawlog,
  updateWithdrawlog,
  passWithdrawlog
} from "@/api/wak/withdrawlog";
import {changeJobStatus} from "@/api/monitor/job";

export default {
  name: "Withdrawlog",
  dicts: ['wak_user_type', 'wak_withdraw_status'],
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
      // 用户提现日志表格数据
      withdrawlogList: [],
      withdrawStatus:undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户昵称时间范围
      daterangeCreatetime: [],
      // 用户昵称时间范围
      daterangeUpdatetime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        address: null,
        usdt: null,
        status: null,
        usertype: null,
        agentid: null,
        createtime: null,
        updatetime: null,
        uid: null,
        nickname: null
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
    /** 查询用户提现日志列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreatetime && '' != this.daterangeCreatetime) {
        this.queryParams.params["beginCreatetime"] = this.daterangeCreatetime[0];
        this.queryParams.params["endCreatetime"] = this.daterangeCreatetime[1];
      }
      if (null != this.daterangeUpdatetime && '' != this.daterangeUpdatetime) {
        this.queryParams.params["beginUpdatetime"] = this.daterangeUpdatetime[0];
        this.queryParams.params["endUpdatetime"] = this.daterangeUpdatetime[1];
      }
      listWithdrawlog(this.queryParams).then(response => {
        this.withdrawlogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handlePass(row){
      var data = {};
      data.id = row.id;
      data.status = 1;
      this.$modal.confirm('确认要通过用户id['+row.uid+']的提现单吗').then(function() {
        return updateWithdrawlog(data);
      }).then(() => {
        this.$modal.msgSuccess("通过成功");
        this.open = false;
        this.getList();
      }).catch(function() {
      });
      // updateWithdrawlog(data).then(response => {
      //   this.$modal.msgSuccess("通过成功");
      //   this.open = false;
      //   this.getList();
      // });
    },
    handlePass2(row){
      var data = {};
      data.id = row.id;
      data.status = 1;
      this.$modal.confirm('确认要强制通过用户id['+row.uid+']的提现单吗').then(function() {
        return passWithdrawlog(data);
      }).then(() => {
        this.$modal.msgSuccess("通过成功");
        this.open = false;
        this.getList();
      }).catch(function() {
      });
    },
    handleRefund(row){
      this.open = true;
      this.form.id = row.id;
      this.withdrawStatus = 2;
      this.title = "驳回用户id为"+row.uid+"的提现订单";
      // this.$modal.confirm('确认要驳回吗').then(function() {
      //   this.open = true;
      //   this.withdrawStatus = 2;
      // }).then(() => {
      // }).catch(function() {
      // });

      // var data = {};
      // data.id = row.id;
      // data.status = 2;
      // updateWithdrawlog(data).then(response => {
      //   this.$modal.msgSuccess("驳回成功");
      //   this.open = false;
      //   this.getList();
      // });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.withdrawStatus = undefined;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        address: null,
        usdt: null,
        status: null,
        usertype: null,
        agentid: null,
        createtime: null,
        updatetime: null,
        remark: null,
        uid: null,
        nickname: null
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
      this.daterangeUpdatetime = [];
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
      this.title = "添加用户提现日志";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWithdrawlog(id).then(response => {
        this.form.id = response.data.id;
        this.form.remark = response.data.remark;
        this.open = true;
        this.title = "修改用户提现日志";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.status = this.withdrawStatus;
            updateWithdrawlog(this.form).then(response => {
              this.$modal.msgSuccess("驳回成功");
              this.open = false;
              this.withdrawStatus=undefined;
              this.getList();
            });
          } else {
            addWithdrawlog(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.withdrawStatus=undefined;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户提现日志编号为"' + ids + '"的数据项？').then(function() {
        return delWithdrawlog(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/withdrawlog/export', {
        ...this.queryParams
      }, `withdrawlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
