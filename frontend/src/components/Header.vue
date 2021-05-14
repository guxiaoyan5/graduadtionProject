<template>
  <div class="header">
    <div class="left">
      <!--      <i v-if="hasBack" class="el-icon-back" @click="back"></i>-->
      <span style="font-size: 20px">{{ this.$route.name }}</span>
    </div>
    <div class="right">
      <el-popover
        placement="bottom"
        :width="320"
        trigger="click"
        popper-class="popper-user-box"
      >
        <template #reference>
          <div class="author">
            <i class="icon el-icon-s-custom"/>
            <!--            {{ userInfo && userInfo.nickName || '' }}-->
            {{ name }}
            <i class="el-icon-caret-bottom"/>
          </div>
        </template>
        <div class="nickname">
          <!--          <p>登录名：{{ userInfo && userInfo.loginUserName || '' }}</p>-->
          <!--          <p>昵称：{{ userInfo && userInfo.nickName || '' }}</p>-->
          <p>登录名：{{ id }}</p>
          <p>昵称：{{ name }}</p>
          <el-tag size="small" effect="dark" class="logout" @click="back">退出</el-tag>
        </div>
      </el-popover>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  name: "Header",
  data() {
    return {
      id: localStorage.getItem('id'),
      name: localStorage.getItem('name'),
    };
  },
  computed: {
    ...mapGetters([
      'getSchoolUser'
    ])
  },
  watch: {
    getSchoolUSer: function (state) {
      let _this = this;
      this.id = state.id;
      this.name = state.name
    },
  },
  methods: {
    back() {
      localStorage.removeItem('id');
      localStorage.removeItem('name');
      localStorage.removeItem('token');
      this.$router.push('/');
    },
  },
}
</script>

<style scoped>
.header {
  height: 50px;
  border-bottom: 1px solid #e9e9e9;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.el-icon-back {
  border: 1px solid #e9e9e9;
  padding: 4px;
  border-radius: 50px;
  margin-right: 10px;
}

.right > div > .icon {
  font-size: 18px;
  margin-right: 6px;
}

.author {
  margin-left: 10px;
  cursor: pointer;
}
</style>
<style>
.popper-user-box {
  background: url('../assets/image/account-banner-bg.png') 50% 50% no-repeat !important;
  background-size: cover !important;
  border-radius: 0 !important;
}

.popper-user-box .nickname {
  position: relative;
  color: #ffffff;
}

.popper-user-box .nickname .logout {
  position: absolute;
  right: 0;
  top: 0;
  cursor: pointer;
}
</style>
