<template>
  <div>
    <el-form :rules="rules" class="login-container" label-position="left"
             label-width="0px" v-loading="loading" :model="ruleForm" ref="ruleForm">
      <h3 class="login_title">修改密码</h3>
      <el-form-item prop="password">
        <el-input type="password" v-model="ruleForm.password" auto-complete="off" placeholder="输入新密码" autocomplete="off"
                  show-password></el-input>
      </el-form-item>
      <el-form-item prop="checkPassword">
        <el-input type="password" v-model="ruleForm.checkPassword" auto-complete="off" placeholder="请确认新密码"
                  autocomplete="off"
                  show-password></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button type="text" @click="dialogVisible = true">提交</el-button>
        <!--        <el-button type="primary" @click="submitForm('ruleForm')" style="width: 100%">登录</el-button>-->
      </el-form-item>

      <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        :before-close="handleClose">
        <span>是否确认修改？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary"  @click="submitForm('ruleForm')">确 定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "ChangePassword",
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if(value.length<6){
        callback(new Error('密码长度必须大于6'));
      }
      else {
        if (this.ruleForm.checkPassword !== '') {
          this.$refs.ruleForm.validateField('checkPassword');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      id:localStorage.getItem('id'),
      dialogVisible: false,
      loading:false,
      ruleForm: {
        password: '',
        checkPassword: '',
      },
      rules: {
        password: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPassword: [
          { validator: validatePass2, trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      let _this = this;
      _this.loading = true;
      this.dialogVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          _this.loading=false;
          _this.$axios.post('http://localhost:9090/schoolUser/updatePassword', {
            id:_this.id,
            password:_this.ruleForm.password
          }).then(function (response) {
            _this.loading = false;
            let data = response.data
            if(data.code===1) {
              alert(data.message);
              _this.$refs[formName].resetFields();
            }else{
              alert(data.message);
            }
          }).catch(function (error) {
            _this.loading = false;
            console.log(error);
          });
        } else {
          _this.loading = false;
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
  }
}
</script>

<style scoped>

</style>

<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 150px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.login_remember {
  margin: 0px 0px 35px 0px;
  text-align: left;
}
</style>
