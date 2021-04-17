<template>
  <div class="table-container">
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        type="index"
        label="序号"
        :index="indexMethod"
        width="200"
        align="center"
      >
      </el-table-column>
      <el-table-column
        label="账号"
        width="400"
        align="center">
        <template slot-scope="scope">
          <i class="el-icon-cpu"></i>
          <span style="margin-left: 10px">{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="姓名"
        align="left"
        width="200">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>姓名: {{ scope.row.name }}</p>
            <p>账号: {{ scope.row.id }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">{{ scope.row.name }}</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column align="right">
        <template slot="header" slot-scope="scope">
          <el-button type="primary" icon="el-icon-circle-plus-outline" @click="addSchoolUser()"></el-button>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogFormVisible">
      <div class="dialog-content">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="修改密码" name="first">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                     class="demo-ruleForm">
              <el-form-item label="账号" prop="id">
                <el-input type="text" v-model="ruleForm.id" autocomplete="off" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="checkPassword">
                <el-input type="password" v-model="ruleForm.checkPassword" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="修改昵称" name="second">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px"
                     class="demo-ruleForm">
              <el-form-item label="账号" prop="id">
                <el-input type="text" v-model="ruleForm.id" autocomplete="off" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="昵称" prop="name">
                <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog title="添加学校管理员" :visible.sync="addFormVisible">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账号" prop="id">
          <el-input type="text" v-model="ruleForm.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input type="password" v-model="ruleForm.checkPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addSubmitForm('ruleForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "AdminSchool",
  data() {
    let validateId = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入昵称'));
      } else if (value.length !== 10) {
        callback(new Error('账号长度为10'));
      }
      callback();
    };
    let validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入昵称'));
      }
      callback();
    };
    let validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPassword !== '') {
          this.$refs.ruleForm.validateField('checkPassword');
        }
        callback();
      }
    };
    let validatePassword2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    let _this = this;
    this.$axios.get("http://localhost:9090/adminUser/schoolUserAll").then(function (response) {
      if (response.data.code === 1) {
        _this.tableData = response.data.data;
      } else {
        _this.tableData = [];
      }
    }).catch(function (error) {
      console.log(error);
    })
    return {
      addFormVisible: false,
      dialogFormVisible: false,
      activeName: 'first',
      tableData: _this.tableData,
      schoolId: '',
      schoolName: '',
      tabIndex: '',
      ruleForm: {
        id: '',
        name: '',
        password: '',
        checkPassword: '',
      },
      rules: {
        id: [
          {validator: validateId, trigger: 'blur'}
        ],
        name: [
          {validator: validateName, trigger: 'blur'}
        ],
        password: [
          {validator: validatePassword, trigger: 'blur'}
        ],
        checkPassword: [
          {validator: validatePassword2, trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    handleEdit(index, row) {
      this.dialogFormVisible = true;
      this.schoolId = row.id;
      this.schoolName = row.name;
      this.ruleForm.id = row.id;
      this.ruleForm.name = row.name;
    },
    handleDelete(index, row) {
      this.$axios.post("http://localhost:9090/adminUser/deleteSchoolUser", {
        id: row.id,
      }).then(function (response) {
        let data = response.data
        alert(data.message);
      })
    },
    indexMethod(index) {
      return index + 1;
    },
    addSchoolUser() {
      this.addFormVisible = true
    },
    handleClick(tab, event) {
      this.tabIndex = tab.index
    },
    submitForm(formName) {
      let _this = this;
      this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.tabIndex === '0') {
              _this.$axios.post("http://localhost:9090/adminUser/updateSchoolPassword", {
                id: _this.ruleForm.id,
                password: _this.ruleForm.password
              }).then(function (response) {
                let data = response.data
                if (data.code === 1) {
                  alert(data.message);
                  _this.$refs[formName].resetFields();
                } else {
                  alert(data.message);
                  _this.$refs[formName].resetFields();
                }
              })
            } else if (this.tabIndex === '1') {
              _this.$axios.post("http://localhost:9090/adminUser/updateStudentName", {
                id: _this.ruleForm.id,
                name: _this.ruleForm.name,
                password: null
              }).then(function (response) {
                let data = response.data
                if (data.code === 1) {
                  alert(data.message);
                  _this.$refs[formName].resetFields();
                } else {
                  alert(data.message);
                  _this.$refs[formName].resetFields();
                }
              })
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        },
        this.$axios.get("http://localhost:9090/adminUser/schoolUserAll").then(function (response) {
          if (response.data.code === 1) {
            _this.tableData = response.data.data;
          } else {
            _this.tableData = [];
          }
        }).catch(function (error) {
          console.log(error);
        }),
        this.dialogFormVisible = false,
        _this.$refs[formName].resetFields(),
      );
    },
    addSubmitForm(ruleForm) {
      let _this = this;
      this.$refs[ruleForm].validate((valid) => {
        if (valid) {
          _this.$axios.post("http://localhost:9090/adminUser/addSchoolUser",{
            id:_this.ruleForm.id,
            name:_this.ruleForm.name,
            password:_this.ruleForm.password
          }).then(function (response){
            let data = response.data;
            if(data.code===1){
              alert(data.message);
              _this.addFormVisible = false;
              _this.$refs[ruleForm].resetFields();
            }else{
              alert(data.message);
            }
          }).catch(function (error) {
            console.log(error);
          })

        }else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>
.table-container {
  margin-left: 50px;
  margin-right: 200px;
}
</style>
