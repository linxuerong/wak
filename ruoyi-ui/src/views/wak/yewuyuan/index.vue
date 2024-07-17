<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="业务员" prop="yewuyuan">
        <el-input
          v-model="queryParams.yewuyuan"
          placeholder="请输入业务员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务组" prop="status">
        <el-select v-model="queryParams.yewuzuid" placeholder="请选择业务组" clearable>
          <el-option
            v-for="dict in yewuyuanzuList"
            :key="dict.id"
            :label="dict.yewuzu"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
<!--      <el-form-item label="业务组id" prop="yewuzuid">-->
<!--        <el-input-->
<!--          v-model="queryParams.yewuzuid"-->
<!--          placeholder="请输入业务组id"-->
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
          v-hasPermi="['wak:yewuyuan:add']"
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
          v-hasPermi="['wak:yewuyuan:edit']"
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
          v-hasPermi="['wak:yewuyuan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['wak:yewuyuan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="yewuyuanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="业务员" align="center" prop="yewuyuan" />
      <el-table-column label="业务组" align="center" prop="yewuzu" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['wak:yewuyuan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['wak:yewuyuan:remove']"
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

    <!-- 添加或修改业务员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="业务员" prop="yewuyuan">
          <el-input v-model="form.yewuyuan" placeholder="请输入业务员" />
        </el-form-item>
        <el-form-item label="业务组" prop="status">
          <el-select v-model="form.yewuzuid" placeholder="请选择状态">
            <el-option
              v-for="dict in yewuyuanzuList"
              :key="dict.id"
              :label="dict.yewuzu"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="业务组id" prop="yewuzuid">-->
<!--          <el-input v-model="form.yewuzuid" placeholder="请输入业务组id" />-->
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
import { listYewuyuan, getYewuyuan, delYewuyuan, addYewuyuan, updateYewuyuan } from "@/api/wak/yewuyuan";
import { listYewuzu} from "@/api/wak/yewuzu";

export default {
  name: "Yewuyuan",
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
      // 业务员表格数据
      yewuyuanList: [],
      // 业务组表格数据
      yewuyuanzuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        yewuyuan: null,
        yewuzuid: null
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
    this.getYewuzuList();
  },
  methods: {
    /** 查询业务员列表 */
    getList() {
      this.loading = true;
      listYewuyuan(this.queryParams).then(response => {
        this.yewuyuanList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询业务员列表 */
    getYewuzuList() {
      let param={
        pageSize:30
      }
      listYewuzu(param).then(response => {
        this.yewuyuanzuList = response.rows;
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
        yewuyuan: null,
        yewuzuid: null
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
      this.queryParams.yewuzuid = undefined;
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
      this.title = "添加业务员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getYewuyuan(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改业务员";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateYewuyuan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addYewuyuan(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除业务员编号为"' + ids + '"的数据项？').then(function() {
        return delYewuyuan(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/yewuyuan/export', {
        ...this.queryParams
      }, `yewuyuan_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
