<template>
  <div class="home">
    <el-container>
      <!--      <Header/>-->
      <!--      <el-main>首页</el-main>-->
      <el-button @click="exit">退出登录</el-button>
      <el-button @click="test">携带token的测试请求</el-button>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "test",
  methods:{
    exit(){
      //退出登录，清空token
      localStorage.removeItem('token');
      this.$router.push('/schoolLogin');
    },
    test(){
      this.$axios({
        method: 'get',
        url: 'http://localhost:9090/schoolUser/test',
      }).then(function(res){
        console.log("res",res);
      }).catch(function(err){
        console.log("err",err);
      })
    }
  }
}
</script>

<style scoped>

</style>
