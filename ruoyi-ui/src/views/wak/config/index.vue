<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="网站配置">
<!--        <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">-->
<!--          <el-form-item label="TRC20收款地址">-->
<!--            <el-input v-model="formLabelAlign.name"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="活动区域">-->
<!--            <el-input v-model="formLabelAlign.region"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="活动形式">-->
<!--            <el-input v-model="formLabelAlign.type"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
        <el-table v-loading="loading" :data="configList" @selection-change="handleSelectionChange">
<!--          <el-table-column type="selection" width="55" align="center" />-->
<!--          <el-table-column label="${comment}" align="center" prop="id" />-->
<!--          <el-table-column label="变量名" align="center" prop="name" />-->
<!--          <el-table-column label="分组" align="center" prop="group" />-->
          <el-table-column label="配置名称" align="center" prop="title" />
<!--          <el-table-column label="变量描述" align="center" prop="tip" />-->
<!--          <el-table-column label="类型:string,text,int,bool,array,datetime,date,file" align="center" prop="type" />-->
<!--          <el-table-column label="可见条件" align="center" prop="visible" />-->
          <el-table-column label="变量值" align="center" prop="value" />
<!--          <el-table-column label="变量字典数据" align="center" prop="content" />-->
<!--          <el-table-column label="验证规则" align="center" prop="rule" />-->
<!--          <el-table-column label="扩展属性" align="center" prop="extend" />-->
<!--          <el-table-column label="配置" align="center" prop="setting" />-->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['wak:config:edit']"
              >修改</el-button>

            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>



    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改平台配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="变量名" prop="name">-->
<!--          <el-input v-model="form.name" placeholder="请输入变量名" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="分组" prop="group">-->
<!--          <el-input v-model="form.group" placeholder="请输入分组" />-->
<!--        </el-form-item>-->
        <el-form-item label="变量标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入变量标题" readonly disabled/>
        </el-form-item>
<!--        <el-form-item label="变量描述" prop="tip">-->
<!--          <el-input v-model="form.tip" placeholder="请输入变量描述" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="可见条件" prop="visible">-->
<!--          <el-input v-model="form.visible" placeholder="请输入可见条件" />-->
<!--        </el-form-item>-->
        <el-form-item label="变量值" prop="value">
          <el-input v-model="form.value" type="textarea" placeholder="请输入内容" />
        </el-form-item>
<!--        <el-form-item label="变量字典数据">-->
<!--          <editor v-model="form.content" :min-height="192"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="验证规则" prop="rule">-->
<!--          <el-input v-model="form.rule" placeholder="请输入验证规则" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="扩展属性" prop="extend">-->
<!--          <el-input v-model="form.extend" placeholder="请输入扩展属性" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="配置" prop="setting">-->
<!--          <el-input v-model="form.setting" placeholder="请输入配置" />-->
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
import { listConfig, getConfig, delConfig, addConfig, updateConfig } from "@/api/wak/config";

export default {
  name: "Config",
  data() {
    return {
      labelPosition: 'right',
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
      // 平台配置表格数据
      configList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        group: null,
        title: null,
        tip: null,
        type: null,
        visible: null,
        value: null,
        content: null,
        rule: null,
        extend: null,
        setting: null
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
    /** 查询平台配置列表 */
    getList() {
      this.loading = true;
      listConfig(this.queryParams).then(response => {
        this.configList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleClick(tab, event) {
      console.log(tab, event);
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
        name: null,
        group: null,
        title: null,
        tip: null,
        type: null,
        visible: null,
        value: null,
        content: null,
        rule: null,
        extend: null,
        setting: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加平台配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getConfig(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改平台配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addConfig(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除平台配置编号为"' + ids + '"的数据项？').then(function() {
        return delConfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wak/config/export', {
        ...this.queryParams
      }, `config_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
