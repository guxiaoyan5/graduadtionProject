<template>
  <div>
    <el-form :rules="rules" class="login-container" label-position="left"
             label-width="0px" v-loading="loading" :model="ruleForm" ref="ruleForm">
      <h3 class="login_title">修改昵称</h3>
      <el-form-item prop="name">
        <el-input type="text" v-model="ruleForm.name" auto-complete="off" placeholder="输入新昵称" autocomplete="off"></el-input>
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
        <span>是否确认修改为{{username}}？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary"  @click="submitForm('ruleForm')">确 定</el-button>
        </span>
      </el-dialog>
    </el-form>
  </div>
</template>

<script>
import {mapMutations} from 'vuex'
export default {
  name: "ChangeName",
  data() {
    const validateName = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入昵称'));
      }
        callback();
    };
    return {
      id:localStorage.getItem('id'),
      dialogVisible: false,
      loading:false,
      ruleForm: {
        name: '',
      },
      rules: {
        name: [
          { validator: validateName, trigger: 'blur' }
        ],
      }
    };
  },
  computed:{
    username:function () {
      return this.ruleForm.name
    }
  },
  methods: {
    ...mapMutations([ 'setSchoolUser']),
    submitForm(formName) {
      let _this = this;
      _this.loading = true;
      this.dialogVisible = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          _this.loading=false;
          _this.$axios.post('http://localhost:9090/schoolUser/updateName', {
            id:localStorage.getItem('id'),
            name:_this.ruleForm.name,
            password:null,
          }).then(function (response) {
            console.log(response)
            _this.loading = false;
            let data = response.data
            if(data.code===1) {
              alert(data.message);
              _this.setSchoolUser({id: data.data.id, name: data.data.name}, {id: data.data.id, name: data.data.name})
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
